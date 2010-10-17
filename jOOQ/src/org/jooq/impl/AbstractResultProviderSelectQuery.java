/**
 * Copyright (c) 2010, Lukas Eder, lukas.eder@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * . Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * . Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * . Neither the name of the "jOOQ" nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jooq.impl;

import static org.jooq.impl.TrueCondition.TRUE_CONDITION;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.jooq.CombineOperator;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.ExistsOperator;
import org.jooq.Field;
import org.jooq.FieldList;
import org.jooq.Join;
import org.jooq.Limit;
import org.jooq.OrderByFieldList;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.ResultProviderSelectQuery;
import org.jooq.SQLDialect;
import org.jooq.SortOrder;
import org.jooq.SubQueryOperator;
import org.jooq.Table;

/**
 * @author Lukas Eder
 */
abstract class AbstractResultProviderSelectQuery<Q extends ResultProviderSelectQuery<Q, R>, R extends Record> extends
    AbstractQuery<R> implements ResultProviderSelectQuery<Q, R> {

    /**
     * Generated UID
     */
    private static final long           serialVersionUID = 1646393178384872967L;

    private ResultImpl<R>               result;
    private final FieldList             select;
    private final TableList             from;
    private final JoinList              join;
    private final ConditionProviderImpl condition;
    private final FieldList             groupBy;
    private final ConditionProviderImpl having;
    private final OrderByFieldList      orderBy;
    private final LimitImpl             limit;

    AbstractResultProviderSelectQuery(Configuration configuration) {
        this(configuration, null);
    }

    AbstractResultProviderSelectQuery(Configuration configuration, Table<R> from) {
        super(configuration);
        final SQLDialect dialect = configuration.getDialect();

        this.select = new SelectFieldListImpl(dialect);
        this.from = new TableList(dialect);
        this.join = new JoinList(dialect);
        this.condition = new ConditionProviderImpl(dialect);
        this.groupBy = new FieldListImpl(dialect);
        this.having = new ConditionProviderImpl(dialect);
        this.orderBy = new OrderByFieldListImpl(dialect);
        this.limit = new LimitImpl(dialect);

        if (from != null) {
            this.from.add(from);
        }
    }

    abstract Q newSelect(Table<R> from);

    @Override
    public final int bind(PreparedStatement stmt, int initialIndex) throws SQLException {
        int result = initialIndex;

        result = getSelect0().bind(stmt, result);
        result = getFrom().bind(stmt, result);
        result = getJoin().bind(stmt, result);
        result = getWhere().bind(stmt, result);
        result = getGroupBy().bind(stmt, result);
        result = getHaving().bind(stmt, result);
        result = getOrderBy().bind(stmt, result);

        return result;
    }

    @Override
    public final String toSQLReference(boolean inlineParameters) {
        StringBuilder sb = new StringBuilder();

        sb.append("select ");
        sb.append(getSelect0().toSQLDeclaration(inlineParameters));

        if (!getFrom().toSQLDeclaration(inlineParameters).isEmpty()) {
            sb.append(" from ");
            sb.append(getFrom().toSQLDeclaration(inlineParameters));
        }

        if (!getJoin().isEmpty()) {
            sb.append(" ");
            sb.append(getJoin().toSQLDeclaration(inlineParameters));
        }

        if (getWhere().getWhere() != TRUE_CONDITION) {
            sb.append(" where ");
            sb.append(getWhere().toSQLReference(inlineParameters));
        }

        if (!getGroupBy().isEmpty()) {
            sb.append(" group by ");
            sb.append(getGroupBy().toSQLReference(inlineParameters));
        }

        if (getHaving().getWhere() != TRUE_CONDITION) {
            sb.append(" having ");
            sb.append(getHaving().toSQLReference(inlineParameters));
        }

        if (!getOrderBy().isEmpty()) {
            sb.append(" order by ");
            sb.append(getOrderBy().toSQLReference(inlineParameters));
        }

        if (getLimit().isApplicable()) {
            sb.append(" ");
            sb.append(getLimit().toSQLReference(inlineParameters));
        }

        return sb.toString();
    }

    @Override
    public final void addSelect(Collection<Field<?>> fields) {
        getSelect0().addAll(fields);
    }

    @Override
    public final void addSelect(Field<?>... fields) {
        addSelect(Arrays.asList(fields));
    }

    @Override
    public final void addLimit(int numberOfRows) {
        addLimit(1, numberOfRows);
    }

    @Override
    public final void addLimit(int lowerBound, int numberOfRows) {
        limit.setLowerBound(lowerBound);
        limit.setNumberOfRows(numberOfRows);
    }

    final FieldList getSelect0() {
        return select;
    }

    @Override
    public final FieldList getSelect() {
        if (getSelect0().isEmpty()) {
            FieldList result = new SelectFieldListImpl(getDialect());

            for (Table<?> table : getFrom()) {
                for (Field<?> field : table.getFields()) {
                    result.add(field);
                }
            }

            for (Join join : getJoin()) {
                for (Field<?> field : join.getTable().getFields()) {
                    result.add(field);
                }
            }

            return result;
        }

        return getSelect0();
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Class<? extends R> getRecordType() {
        if (getTables().size() == 1) {
            return (Class<? extends R>) getTables().get(0).getRecordType();
        }
        else {
            return (Class<? extends R>) RecordImpl.class;
        }
    }

    @Override
    public R newRecord() {
        return newRecord(getConfiguration());
    }

    @Override
    public R newRecord(Configuration configuration) {
        return JooqUtil.newRecord(getRecordType(), getSelect(), configuration);
    }

    TableList getTables() {
        TableList result = new TableList(getDialect(), getFrom());

        for (Join join : getJoin()) {
            result.add(join.getTable());
        }

        return result;
    }

    @Override
    protected final int execute(PreparedStatement statement) throws SQLException {
        ResultSet rs = null;

        try {
            rs = statement.executeQuery();
            result = new ResultImpl<R>(getSelect());

            while (rs.next()) {
                R record = newRecord();

                for (Field<?> field : getSelect()) {
                    setValue(record, field, rs);
                }

                result.addRecord(record);
            }
        }
        finally {
            SQLUtils.safeClose(rs);
        }

        return result.getNumberOfRecords();
    }

    /**
     * Utility method to prevent unnecessary unchecked conversions
     */
    private final <T> void setValue(Record record, Field<T> field, ResultSet rs) throws SQLException {
        T value = FieldTypeHelper.getFromResultSet(rs, field);
        record.setValue(field, new ValueImpl<T>(value));
    }

    @Override
    public final Result<R> getResult() {
        return result;
    }

    final TableList getFrom() {
        return from;
    }

    final FieldList getGroupBy() {
        return groupBy;
    }

    final JoinList getJoin() {
        return join;
    }

    final Limit getLimit() {
        return limit;
    }

    final ConditionProviderImpl getWhere() {
        return condition;
    }

    final ConditionProviderImpl getHaving() {
        return having;
    }

    final OrderByFieldList getOrderBy() {
        return orderBy;
    }

    @Override
    public final void addOrderBy(Field<?> field, SortOrder order) {
        getOrderBy().add(field, order);
    }

    @Override
    public final void addOrderBy(Collection<Field<?>> fields) {
        for (Field<?> field : fields) {
            addOrderBy(field, null);
        }
    }

    @Override
    public final void addOrderBy(OrderByFieldList fields) {
        for (Field<?> field : fields) {
            addOrderBy(field, fields.getOrdering().get(field));
        }
    }

    @Override
    public final void addOrderBy(Field<?>... fields) {
        addOrderBy(Arrays.asList(fields));
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <T> Field<T> asField() {
        if (getSelect().size() != 1) {
            throw new IllegalStateException("Can only use single-column ResultProviderQuery as a field");
        }

        return new SelectQueryAsField<T>(getDialect(), this, (Class<? extends T>) getSelect().get(0).getType());
    }

    @Override
    public final Condition asInCondition(Field<?> field) {
        return asSubQueryCondition(field, SubQueryOperator.IN);
    }

    @Override
    public final Condition asNotInCondition(Field<?> field) {
        return asSubQueryCondition(field, SubQueryOperator.NOT_IN);
    }

    @Override
    public final Condition asCompareCondition(Field<?> field) {
        return asSubQueryCondition(field, SubQueryOperator.EQUALS);
    }

    @Override
    public final Condition asSubQueryCondition(Field<?> field, SubQueryOperator operator) {
        if (getSelect().size() != 1) {
            throw new IllegalStateException("Can only use single-column ResultProviderQuery as an InCondition");
        }

        return new SelectQueryAsSubQueryCondition(getDialect(), this, field, operator);
    }

    @Override
    public final Condition asExistsCondition() {
        return asExistsCondition(ExistsOperator.EXISTS);
    }

    @Override
    public final Condition asNotExistsCondition() {
        return asExistsCondition(ExistsOperator.NOT_EXISTS);
    }

    private final Condition asExistsCondition(ExistsOperator operator) {
        if (getSelect().size() != 1) {
            throw new IllegalStateException("Can only use single-column ResultProviderQuery as an InCondition");
        }

        return new SelectQueryAsExistsCondition(getDialect(), this, operator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Q getQuery() {
        return (Q) this;
    }

    @Override
    public final void addConditions(Condition... conditions) {
        condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Collection<Condition> conditions) {
        condition.addConditions(conditions);
    }

    @Override
    public final <T> void addBetweenCondition(Field<T> field, T minValue, T maxValue) {
        condition.addBetweenCondition(field, minValue, maxValue);
    }

    @Override
    public final <T> void addCompareCondition(Field<T> field, T value, Comparator comparator) {
        condition.addCompareCondition(field, value, comparator);
    }

    @Override
    public final <T> void addCompareCondition(Field<T> field, T value) {
        condition.addCompareCondition(field, value);
    }

    @Override
    public final void addNullCondition(Field<?> field) {
        condition.addNullCondition(field);
    }

    @Override
    public final void addNotNullCondition(Field<?> field) {
        condition.addNotNullCondition(field);
    }

    @Override
    public final <T> void addInCondition(Field<T> field, Collection<T> values) {
        condition.addInCondition(field, values);
    }

    @Override
    public final <T> void addInCondition(Field<T> field, T... values) {
        condition.addInCondition(field, values);
    }

    @Override
    public final Q combine(Q other) {
        return combine(CombineOperator.UNION, other);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Q combine(CombineOperator operator, Q other) {
        return newSelect(asTable(operator, getQuery(), other));
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Table<R> asTable() {
        return asTable(CombineOperator.UNION, getQuery());
    }

    private final Table<R> asTable(CombineOperator operator, Q... queries) {
        Table<R> result = new SelectQueryAsTable<R>(getDialect(), operator, queries);

        // Some dialects require derived tables to provide an alias
        switch (getDialect()) {
            case MYSQL:
            case POSTGRES:
                result = result.as("gen_" + (int) (Math.random() * 1000000));
                break;
        }

        return result;
    }
}
