/**
 * This class is generated by jOOQ
 */
package org.jooq.test.firebird.generatedclasses.tables.interfaces;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
@javax.persistence.Entity
@javax.persistence.Table(name = "T_TRIGGERS")
public interface ITTriggers extends java.io.Serializable {

	/**
	 * Setter for <code>T_TRIGGERS.ID_GENERATED</code>.
	 */
	public void setIdGenerated(java.lang.Integer value);

	/**
	 * Getter for <code>T_TRIGGERS.ID_GENERATED</code>.
	 */
	@javax.persistence.Id
	@javax.persistence.Column(name = "ID_GENERATED", unique = true, nullable = false, length = 4)
	@javax.validation.constraints.NotNull
	public java.lang.Integer getIdGenerated();

	/**
	 * Setter for <code>T_TRIGGERS.ID</code>.
	 */
	public void setId(java.lang.Integer value);

	/**
	 * Getter for <code>T_TRIGGERS.ID</code>.
	 */
	@javax.persistence.Column(name = "ID", length = 4)
	public java.lang.Integer getId();

	/**
	 * Setter for <code>T_TRIGGERS.COUNTER</code>.
	 */
	public void setCounter(java.lang.Integer value);

	/**
	 * Getter for <code>T_TRIGGERS.COUNTER</code>.
	 */
	@javax.persistence.Column(name = "COUNTER", length = 4)
	public java.lang.Integer getCounter();

	// -------------------------------------------------------------------------
	// FROM and INTO
	// -------------------------------------------------------------------------

	/**
	 * Load data from another generated Record/POJO implementing the common interface ITTriggers
	 */
	public void from(org.jooq.test.firebird.generatedclasses.tables.interfaces.ITTriggers from);

	/**
	 * Copy data into another generated Record/POJO implementing the common interface ITTriggers
	 */
	public <E extends org.jooq.test.firebird.generatedclasses.tables.interfaces.ITTriggers> E into(E into);
}
