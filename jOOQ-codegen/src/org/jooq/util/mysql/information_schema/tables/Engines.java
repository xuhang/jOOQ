/**
 * This class is generated by jOOQ
 */
package org.jooq.util.mysql.information_schema.tables;

import org.jooq.TableField;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.information_schema.InformationSchema;
import org.jooq.util.mysql.information_schema.tables.records.EnginesRecord;


/**
 * This class is generated by jOOQ.
 */
public class Engines extends TableImpl {

	private static final long serialVersionUID = 1L;

	/**
	 * The singleton instance of ENGINES
	 */
	public static final Engines ENGINES = new Engines();

	/**
	 * The class holding records for this table
	 */
	private static final Class<EnginesRecord> RECORD_TYPE = EnginesRecord.class;

	/**
	 * The class holding records for this table
	 */
	@Override
	public Class<EnginesRecord> getRecordType() {
		return RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 */
	public static final TableField<String> ENGINE = new TableFieldImpl<String>("ENGINE", String.class, ENGINES);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> SUPPORT = new TableFieldImpl<String>("SUPPORT", String.class, ENGINES);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> COMMENT = new TableFieldImpl<String>("COMMENT", String.class, ENGINES);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> TRANSACTIONS = new TableFieldImpl<String>("TRANSACTIONS", String.class, ENGINES);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> XA = new TableFieldImpl<String>("XA", String.class, ENGINES);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> SAVEPOINTS = new TableFieldImpl<String>("SAVEPOINTS", String.class, ENGINES);

	/**
	 * No further instances allowed
	 */
	private Engines() {
		super("ENGINES", InformationSchema.INFORMATION_SCHEMA);
	}
}
