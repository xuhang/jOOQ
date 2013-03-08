/**
 * This class is generated by jOOQ
 */
package org.jooq.test.hsqldb.generatedclasses.tables;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked" })
public class Object extends org.jooq.impl.TableImpl<org.jooq.test.hsqldb.generatedclasses.tables.records.ObjectRecord> {

	private static final long serialVersionUID = -128048898;

	/**
	 * The singleton instance of <code>PUBLIC.OBJECT</code>
	 */
	public static final org.jooq.test.hsqldb.generatedclasses.tables.Object OBJECT = new org.jooq.test.hsqldb.generatedclasses.tables.Object();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.test.hsqldb.generatedclasses.tables.records.ObjectRecord> getRecordType() {
		return org.jooq.test.hsqldb.generatedclasses.tables.records.ObjectRecord.class;
	}

	/**
	 * The column <code>PUBLIC.OBJECT.ID</code>. 
	 */
	public final org.jooq.TableField<org.jooq.test.hsqldb.generatedclasses.tables.records.ObjectRecord, java.lang.Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * The column <code>PUBLIC.OBJECT.OTHER</code>. 
	 */
	public final org.jooq.TableField<org.jooq.test.hsqldb.generatedclasses.tables.records.ObjectRecord, java.lang.Object> OTHER = createField("OTHER", org.jooq.impl.SQLDataType.OTHER, this);

	/**
	 * Create a <code>PUBLIC.OBJECT</code> table reference
	 */
	public Object() {
		super("OBJECT", org.jooq.test.hsqldb.generatedclasses.Public.PUBLIC);
	}

	/**
	 * Create an aliased <code>PUBLIC.OBJECT</code> table reference
	 */
	public Object(java.lang.String alias) {
		super(alias, org.jooq.test.hsqldb.generatedclasses.Public.PUBLIC, org.jooq.test.hsqldb.generatedclasses.tables.Object.OBJECT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.test.hsqldb.generatedclasses.tables.Object as(java.lang.String alias) {
		return new org.jooq.test.hsqldb.generatedclasses.tables.Object(alias);
	}
}
