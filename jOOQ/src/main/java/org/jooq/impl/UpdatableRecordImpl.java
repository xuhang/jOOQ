/**
 * Copyright (c) 2009-2014, Data Geekery GmbH (http://www.datageekery.com)
 * All rights reserved.
 *
 * This work is dual-licensed
 * - under the Apache Software License 2.0 (the "ASL")
 * - under the jOOQ License and Maintenance Agreement (the "jOOQ License")
 * =============================================================================
 * You may choose which license applies to you:
 *
 * - If you're using this work with Open Source databases, you may choose
 *   either ASL or jOOQ License.
 * - If you're using this work with at least one commercial database, you must
 *   choose jOOQ License
 *
 * For more information, please visit http://www.jooq.org/licenses
 *
 * Apache Software License 2.0:
 * -----------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * jOOQ License and Maintenance Agreement:
 * -----------------------------------------------------------------------------
 * Data Geekery grants the Customer the non-exclusive, timely limited and
 * non-transferable license to install and use the Software under the terms of
 * the jOOQ License and Maintenance Agreement.
 *
 * This library is distributed with a LIMITED WARRANTY. See the jOOQ License
 * and Maintenance Agreement for more details: http://www.jooq.org/licensing
 */
package org.jooq.impl;

import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
// ...
import static org.jooq.SQLDialect.SQLITE;
import static org.jooq.conf.SettingsTools.updatablePrimaryKeys;
import static org.jooq.impl.RecordDelegate.delegate;
import static org.jooq.impl.RecordDelegate.RecordLifecycleType.DELETE;
import static org.jooq.impl.RecordDelegate.RecordLifecycleType.REFRESH;
import static org.jooq.impl.RecordDelegate.RecordLifecycleType.STORE;
import static org.jooq.impl.RecordDelegate.RecordLifecycleType.UPDATE;
import static org.jooq.impl.Utils.settings;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.DeleteQuery;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableRecord;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.UpdateQuery;
import org.jooq.exception.DataChangedException;
import org.jooq.exception.InvalidResultException;
import org.jooq.tools.StringUtils;

/**
 * A record implementation for a record holding a primary key
 * <p>
 * This type is for JOOQ INTERNAL USE only. Do not reference directly
 *
 * @author Lukas Eder
 */
public class UpdatableRecordImpl<R extends UpdatableRecord<R>> extends TableRecordImpl<R> implements UpdatableRecord<R> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID      = -1012420583600561579L;

    public UpdatableRecordImpl(Table<R> table) {
        super(table);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Record key() {
        RecordImpl result = new RecordImpl(getPrimaryKey().getFields());
        result.setValues(result.fields.fields.fields, this);
        return result;
    }

    @Override
    public final <O extends TableRecord<O>> O fetchChild(ForeignKey<O, R> key) {
        return Utils.filterOne(fetchChildren(key));
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> key) {
        return key.fetchChildren((R) this);
    }

    @Override
    final UniqueKey<R> getPrimaryKey() {
        return getTable().getPrimaryKey();
    }

    @Override
    public final int store() {
        final int[] result = new int[1];

        delegate(configuration(), (Record) this, STORE)
        .operate(new RecordOperation<Record, RuntimeException>() {

            @Override
            public Record operate(Record record) throws RuntimeException {
                result[0] = store0();
                return record;
            }
        });

        return result[0];
    }

    @Override
    public final int update() {
        return storeUpdate(getPrimaryKey().getFieldsArray());
    }

    private final int store0() {
        TableField<R, ?>[] keys = getPrimaryKey().getFieldsArray();
        boolean executeUpdate = false;

        for (TableField<R, ?> field : keys) {

            // [#2764] If primary key values are allowed to be changed,
            // inserting is only possible without prior loading of pk values
            if (updatablePrimaryKeys(settings(this))) {
                if (original(field) == null) {
                    executeUpdate = false;
                    break;
                }
            }

            // [#2764] Primary key value changes are interpreted as record copies
            else {

                // If any primary key value is null or changed, execute an insert
                if (getValue(field) == null || getValue0(field).isChanged()) {
                    executeUpdate = false;
                    break;
                }
            }

            // Otherwise, updates are possible
            executeUpdate = true;
        }

        int result = 0;

        if (executeUpdate) {
            result = storeUpdate(keys);
        }
        else {
            result = storeInsert();
        }

        return result;
    }

    private final int storeUpdate(final TableField<R, ?>[] keys) {
        final int[] result = new int[1];

        delegate(configuration(), (Record) this, UPDATE)
        .operate(new RecordOperation<Record, RuntimeException>() {

            @Override
            public Record operate(Record record) throws RuntimeException {
                result[0] = storeUpdate0(keys);
                return record;
            }
        });

        return result[0];

    }

    private final int storeUpdate0(TableField<R, ?>[] keys) {
        UpdateQuery<R> update = create().updateQuery(getTable());
        addChangedValues(update);
        Utils.addConditions(update, this, keys);

        // Don't store records if no value was set by client code
        if (!update.isExecutable()) return 0;

        // [#1596] Set timestamp and/or version columns to appropriate values
        BigInteger version = addRecordVersion(update);
        Timestamp timestamp = addRecordTimestamp(update);

        if (isExecuteWithOptimisticLocking()) {

            // [#1596] Add additional conditions for version and/or timestamp columns
            if (isTimestampOrVersionAvailable()) {
                addConditionForVersionAndTimestamp(update);
            }

            // [#1547] Try fetching the Record again first, and compare this
            // Record's original values with the ones in the database
            else {
                checkIfChanged(keys);
            }
        }

        // [#1596] Check if the record was really changed in the database
        int result = update.execute();
        checkIfChanged(result, version, timestamp);

        if (result > 0) {
            changed(false);
        }

        return result;
    }

    @Override
    public final int delete() {
        final int[] result = new int[1];

        delegate(configuration(), (Record) this, DELETE)
        .operate(new RecordOperation<Record, RuntimeException>() {

            @Override
            public Record operate(Record record) throws RuntimeException {
                result[0] = delete0();
                return record;
            }
        });

        return result[0];
    }

    private final int delete0() {
        TableField<R, ?>[] keys = getPrimaryKey().getFieldsArray();

        try {
            DeleteQuery<R> delete1 = create().deleteQuery(getTable());
            Utils.addConditions(delete1, this, keys);

            if (isExecuteWithOptimisticLocking()) {

                // [#1596] Add additional conditions for version and/or timestamp columns
                if (isTimestampOrVersionAvailable()) {
                    addConditionForVersionAndTimestamp(delete1);
                }

                // [#1547] Try fetching the Record again first, and compare this
                // Record's original values with the ones in the database
                else {
                    checkIfChanged(keys);
                }
            }

            int result = delete1.execute();
            checkIfChanged(result, null, null);
            return result;
        }

        // [#673] If store() is called after delete(), a new INSERT should
        // be executed and the record should be recreated
        finally {
            changed(true);
        }
    }

    @Override
    public final void refresh() {
        refresh(fields.fields.fields);
    }

    @Override
    public final void refresh(final Field<?>... f) {
        SelectQuery<Record> select = create().selectQuery();
        select.addSelect(f);
        select.addFrom(getTable());
        Utils.addConditions(select, this, getPrimaryKey().getFieldsArray());

        if (select.execute() == 1) {
            final AbstractRecord source = (AbstractRecord) select.getResult().get(0);

            delegate(configuration(), (Record) this, REFRESH)
                .operate(new RecordOperation<Record, RuntimeException>() {
                    @Override
                    public Record operate(Record record) throws RuntimeException {
                        setValues(f, source);
                        return record;
                    }
                });
        }
        else {
            throw new InvalidResultException("Exactly one row expected for refresh. Record does not exist in database.");
        }
    }

    @Override
    public final R copy() {
        return Utils.newRecord(getTable(), configuration())
                    .operate(new RecordOperation<R, RuntimeException>() {

        	@Override
            public R operate(R copy) throws RuntimeException {
                // Copy all fields. This marks them all as isChanged, which is important
                List<TableField<R, ?>> key = getPrimaryKey().getFields();
                for (Field<?> field : fields.fields.fields) {

                    // Don't copy key values
                    if (!key.contains(field)) {
                        setValue(copy, field);
                    }
                }

                return copy;
            }

            /**
             * Extracted method to ensure generic type safety.
             */
            private final <T> void setValue(Record record, Field<T> field) {
                record.setValue(field, getValue(field));
            }
        });
    }

    private final boolean isExecuteWithOptimisticLocking() {
        Configuration configuration = configuration();

        // This can be null when the current record is detached
        if (configuration != null) {
            return TRUE.equals(configuration.settings().isExecuteWithOptimisticLocking());
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    private final void addConditionForVersionAndTimestamp(org.jooq.ConditionProvider query) {
        TableField<R, ?> v = getTable().getRecordVersion();
        TableField<R, ?> t = getTable().getRecordTimestamp();

        if (v != null) Utils.addCondition(query, this, v);
        if (t != null) Utils.addCondition(query, this, t);
    }

    /**
     * Perform an additional SELECT .. FOR UPDATE to check if the underlying
     * database record has been changed compared to this record.
     */
    private final void checkIfChanged(TableField<R, ?>[] keys) {
        SelectQuery<R> select = create().selectQuery(getTable());
        Utils.addConditions(select, this, keys);

        // [#1547] MS Access and SQLite doesn't support FOR UPDATE. CUBRID and SQL Server
        // can simulate it, though!
        if (!asList(SQLITE).contains(create().configuration().dialect().family())) {
            select.setForUpdate(true);
        }

        R record = select.fetchOne();

        if (record == null) {
            throw new DataChangedException("Database record no longer exists");
        }

        for (Field<?> field : fields.fields.fields) {
            Value<?> thisValue = getValue0(field);
            Value<?> thatValue = ((AbstractRecord) record).getValue0(field);

            Object thisObject = thisValue.getOriginal();
            Object thatObject = thatValue.getOriginal();

            if (!StringUtils.equals(thisObject, thatObject)) {
                throw new DataChangedException("Database record has been changed");
            }
        }
    }

    /**
     * Check if a database record was changed in the database.
     */
    private final void checkIfChanged(int result, BigInteger version, Timestamp timestamp) {

        // [#1596] If update/delete was successful, update version and/or
        // timestamp columns.
        // [#673] Do this also for deletions, in case a deleted record is re-added
        if (result > 0) {
            setRecordVersionAndTimestamp(version, timestamp);
        }

        // [#1596] No records were updated due to version and/or timestamp change
        else if (isExecuteWithOptimisticLocking()) {
            throw new DataChangedException("Database record has been changed or doesn't exist any longer");
        }
    }
}
