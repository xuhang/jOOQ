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

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.jooq.SQLDialect;
import org.jooq.SQLDialectNotSupportedException;

class DefaultArray implements Array {

    private final SQLDialect dialect;
    private final Object[] array;
    private final Class<?> type;

    public DefaultArray(SQLDialect dialect, Object[] array, Class<?> type) {
        this.dialect = dialect;
        this.array = array;
        this.type = type;
    }

    @Override
    public String getBaseTypeName() throws SQLException {
        return FieldTypeHelper.getDataType(dialect, type.getComponentType()).getTypeName();
    }

    @Override
    public int getBaseType() throws SQLException {
        throw new SQLDialectNotSupportedException("Array.getBaseType()");
    }

    @Override
    public Object getArray() throws SQLException {
        return array;
    }

    @Override
    public Object getArray(Map<String, Class<?>> map) throws SQLException {
        return array;
    }

    @Override
    public Object getArray(long index, int count) throws SQLException {
        throw new SQLDialectNotSupportedException("Array.getArray(long, int)");
    }

    @Override
    public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
        throw new SQLDialectNotSupportedException("Array.getArray(long, int, Map)");
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        throw new SQLDialectNotSupportedException("Array.getResultSet()");
    }

    @Override
    public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
        throw new SQLDialectNotSupportedException("Array.getResultSet(Map)");
    }

    @Override
    public ResultSet getResultSet(long index, int count) throws SQLException {
        throw new SQLDialectNotSupportedException("Array.getResultSet(long, int)");
    }

    @Override
    public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
        throw new SQLDialectNotSupportedException("Array.getResultSet(long, int, Map)");
    }

    @Override
    public void free() throws SQLException {
    }
}
