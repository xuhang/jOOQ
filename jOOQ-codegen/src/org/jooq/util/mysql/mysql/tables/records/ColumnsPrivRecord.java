/**
 * This class is generated by jOOQ
 */
package org.jooq.util.mysql.mysql.tables.records;

import java.sql.Timestamp;
import org.jooq.Result;
import org.jooq.impl.RecordImpl;
import org.jooq.util.mysql.mysql.tables.ColumnsPriv;


/**
 * This class is generated by jOOQ.
 *
 * Column privileges
 */
public class ColumnsPrivRecord extends RecordImpl {

	private static final long serialVersionUID = 1L;

	/**
	 * An uncommented item
	 */
	public void setHost(String value) {
		setValue(ColumnsPriv.HOST, value);
	}

	/**
	 * An uncommented item
	 */
	public String getHost() {
		return getValue(ColumnsPriv.HOST);
	}

	/**
	 * An uncommented item
	 */
	public void setDb(String value) {
		setValue(ColumnsPriv.DB, value);
	}

	/**
	 * An uncommented item
	 */
	public String getDb() {
		return getValue(ColumnsPriv.DB);
	}

	/**
	 * An uncommented item
	 */
	public void setUser(String value) {
		setValue(ColumnsPriv.USER, value);
	}

	/**
	 * An uncommented item
	 */
	public String getUser() {
		return getValue(ColumnsPriv.USER);
	}

	/**
	 * An uncommented item
	 */
	public void setTableName(String value) {
		setValue(ColumnsPriv.TABLE_NAME, value);
	}

	/**
	 * An uncommented item
	 */
	public String getTableName() {
		return getValue(ColumnsPriv.TABLE_NAME);
	}

	/**
	 * An uncommented item
	 */
	public void setColumnName(String value) {
		setValue(ColumnsPriv.COLUMN_NAME, value);
	}

	/**
	 * An uncommented item
	 */
	public String getColumnName() {
		return getValue(ColumnsPriv.COLUMN_NAME);
	}

	/**
	 * An uncommented item
	 */
	public void setTimestamp(Timestamp value) {
		setValue(ColumnsPriv.TIMESTAMP, value);
	}

	/**
	 * An uncommented item
	 */
	public Timestamp getTimestamp() {
		return getValue(ColumnsPriv.TIMESTAMP);
	}

	/**
	 * An uncommented item
	 */
	public void setColumnPriv(String value) {
		setValue(ColumnsPriv.COLUMN_PRIV, value);
	}

	/**
	 * An uncommented item
	 */
	public String getColumnPriv() {
		return getValue(ColumnsPriv.COLUMN_PRIV);
	}

	public ColumnsPrivRecord(Result result) {
		super(result);
	}
}
