/**
 * Copyright (c) 2009-2011, Lukas Eder, lukas.eder@gmail.com
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;

import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Parameter;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.StoredFunction;

/**
 * @author Lukas Eder
 */
public class StoredFunctionImpl<T> extends AbstractStoredObject implements StoredFunction<T> {

    private static final long  serialVersionUID = -2938795269169609664L;

    private transient T        result;
    private transient Field<T> function;
    private final DataType<T>  type;

    public StoredFunctionImpl(SQLDialect dialect, String name, Schema schema, DataType<T> type) {
        this(Factory.getFactory(dialect), name, schema, type);
    }

    public StoredFunctionImpl(Configuration configuration, String name, Schema schema, DataType<T> type) {
        super(configuration, name, schema);

        this.type = type;
    }

    @Override
    public final T getReturnValue() {
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Field<T> asField() {
        if (function == null) {
            String name = getName();

            if (getSchema() != null) {
                name = getSchema().getQueryPart().toSQLReference(getConfiguration()) + "." + name;
            }

            Field<?>[] array = new Field<?>[getInValues().size()];

            int i = 0;
            for (Entry<Parameter<?>, Field<?>> e : getInValues().entrySet()) {

                // Disambiguate overloaded function signatures
                if (SQLDialect.POSTGRES == getDialect() && isOverloaded()) {
                    array[i] = e.getValue().cast(e.getKey().getType());
                }
                else {
                    array[i] = e.getValue();
                }

                i++;
            }

            function = new Function<T>(getConfiguration(), name, type, array);
        }

        return function;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Field<T> asField(String alias) {
        return asField().as(alias);
    }

    @Override
    public final int execute(Connection connection) throws SQLException {
        // Cannot re-use create() here, as create() does not hold any connection
        // TODO: refactor this to use create(), when #176 is implemented
        // see https://sourceforge.net/apps/trac/jooq/ticket/176

        result = new Factory(connection, getDialect()).select(asField()).fetch(asField()).get(0);
        return 0;
    }

    @Override
    public final int bind(Configuration configuration, PreparedStatement stmt, int initialIndex) throws SQLException {
        int index = initialIndex;

        for (Parameter<?> parameter : getParameters()) {
            if (getInValues().get(parameter) != null) {
                index = getInValues().get(parameter).getQueryPart().bind(configuration, stmt, index);
            }
        }

        return index;
    }

    @Override
    public String toSQLReference(Configuration configuration, boolean inlineParameters) {
        return create().select(asField()).getQueryPart().toSQLReference(configuration, inlineParameters);
    }

    @Override
    public final List<Parameter<?>> getParameters() {
        return getInParameters();
    }
}
