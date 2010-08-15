/**
 * This class is generated by jOOQ
 */
package org.jooq.util.mysql.mysql.tables;

import org.jooq.TableField;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.mysql.Mysql;
import org.jooq.util.mysql.mysql.tables.records.TimeZoneTransitionTypeRecord;


/**
 * This class is generated by jOOQ.
 *
 * Time zone transition types
 */
public class TimeZoneTransitionType extends TableImpl {

	private static final long serialVersionUID = 1L;

	/**
	 * The singleton instance of time_zone_transition_type
	 */
	public static final TimeZoneTransitionType TIME_ZONE_TRANSITION_TYPE = new TimeZoneTransitionType();

	/**
	 * The class holding records for this table
	 */
	private static final Class<TimeZoneTransitionTypeRecord> RECORD_TYPE = TimeZoneTransitionTypeRecord.class;

	/**
	 * The class holding records for this table
	 */
	@Override
	public Class<TimeZoneTransitionTypeRecord> getRecordType() {
		return RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 */
	public static final TableField<Integer> TIME_ZONE_ID = new TableFieldImpl<Integer>("Time_zone_id", Integer.class, TIME_ZONE_TRANSITION_TYPE);

	/**
	 * An uncommented item
	 */
	public static final TableField<Integer> TRANSITION_TYPE_ID = new TableFieldImpl<Integer>("Transition_type_id", Integer.class, TIME_ZONE_TRANSITION_TYPE);

	/**
	 * An uncommented item
	 */
	public static final TableField<Integer> OFFSET = new TableFieldImpl<Integer>("Offset", Integer.class, TIME_ZONE_TRANSITION_TYPE);

	/**
	 * An uncommented item
	 */
	public static final TableField<Byte> IS_DST = new TableFieldImpl<Byte>("Is_DST", Byte.class, TIME_ZONE_TRANSITION_TYPE);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> ABBREVIATION = new TableFieldImpl<String>("Abbreviation", String.class, TIME_ZONE_TRANSITION_TYPE);

	/**
	 * No further instances allowed
	 */
	private TimeZoneTransitionType() {
		super("time_zone_transition_type", Mysql.MYSQL);
	}
}
