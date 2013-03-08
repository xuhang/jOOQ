/**
 * This class is generated by jOOQ
 */
package org.jooq.test.hsqldb.generatedclasses.tables.interfaces;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked" })
public interface IConnection extends java.io.Serializable {

	/**
	 * Setter for <code>PUBLIC.CONNECTION.ID</code>. 
	 */
	public void setId(java.lang.Integer value);

	/**
	 * Getter for <code>PUBLIC.CONNECTION.ID</code>. 
	 */
	public java.lang.Integer getId();

	// -------------------------------------------------------------------------
	// FROM and INTO
	// -------------------------------------------------------------------------

	/**
	 * Load data from another generated Record/POJO implementing the common interface IConnection
	 */
	public void from(org.jooq.test.hsqldb.generatedclasses.tables.interfaces.IConnection from);

	/**
	 * Copy data into another generated Record/POJO implementing the common interface IConnection
	 */
	public <E extends org.jooq.test.hsqldb.generatedclasses.tables.interfaces.IConnection> E into(E into);
}
