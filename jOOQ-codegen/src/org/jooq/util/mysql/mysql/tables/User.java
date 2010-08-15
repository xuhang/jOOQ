/**
 * This class is generated by jOOQ
 */
package org.jooq.util.mysql.mysql.tables;

import org.jooq.TableField;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.mysql.Mysql;
import org.jooq.util.mysql.mysql.tables.records.UserRecord;


/**
 * This class is generated by jOOQ.
 *
 * Users and global privileges
 */
public class User extends TableImpl {

	private static final long serialVersionUID = 1L;

	/**
	 * The singleton instance of user
	 */
	public static final User USER = new User();

	/**
	 * The class holding records for this table
	 */
	private static final Class<UserRecord> RECORD_TYPE = UserRecord.class;

	/**
	 * The class holding records for this table
	 */
	@Override
	public Class<UserRecord> getRecordType() {
		return RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 */
	public static final TableField<String> HOST = new TableFieldImpl<String>("Host", String.class, USER);

	/**
	 * An uncommented item
	 * 
	 * This item has the same name as its container. That is why an underline character was appended to the Java field name
	 */
	public static final TableField<String> USER_ = new TableFieldImpl<String>("User", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> PASSWORD = new TableFieldImpl<String>("Password", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> SELECT_PRIV = new TableFieldImpl<String>("Select_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> INSERT_PRIV = new TableFieldImpl<String>("Insert_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> UPDATE_PRIV = new TableFieldImpl<String>("Update_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> DELETE_PRIV = new TableFieldImpl<String>("Delete_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> CREATE_PRIV = new TableFieldImpl<String>("Create_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> DROP_PRIV = new TableFieldImpl<String>("Drop_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> RELOAD_PRIV = new TableFieldImpl<String>("Reload_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> SHUTDOWN_PRIV = new TableFieldImpl<String>("Shutdown_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> PROCESS_PRIV = new TableFieldImpl<String>("Process_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> FILE_PRIV = new TableFieldImpl<String>("File_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> GRANT_PRIV = new TableFieldImpl<String>("Grant_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> REFERENCES_PRIV = new TableFieldImpl<String>("References_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> INDEX_PRIV = new TableFieldImpl<String>("Index_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> ALTER_PRIV = new TableFieldImpl<String>("Alter_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> SHOW_DB_PRIV = new TableFieldImpl<String>("Show_db_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> SUPER_PRIV = new TableFieldImpl<String>("Super_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> CREATE_TMP_TABLE_PRIV = new TableFieldImpl<String>("Create_tmp_table_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> LOCK_TABLES_PRIV = new TableFieldImpl<String>("Lock_tables_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> EXECUTE_PRIV = new TableFieldImpl<String>("Execute_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> REPL_SLAVE_PRIV = new TableFieldImpl<String>("Repl_slave_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> REPL_CLIENT_PRIV = new TableFieldImpl<String>("Repl_client_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> CREATE_VIEW_PRIV = new TableFieldImpl<String>("Create_view_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> SHOW_VIEW_PRIV = new TableFieldImpl<String>("Show_view_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> CREATE_ROUTINE_PRIV = new TableFieldImpl<String>("Create_routine_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> ALTER_ROUTINE_PRIV = new TableFieldImpl<String>("Alter_routine_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> CREATE_USER_PRIV = new TableFieldImpl<String>("Create_user_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> EVENT_PRIV = new TableFieldImpl<String>("Event_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> TRIGGER_PRIV = new TableFieldImpl<String>("Trigger_priv", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<String> SSL_TYPE = new TableFieldImpl<String>("ssl_type", String.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<byte[]> SSL_CIPHER = new TableFieldImpl<byte[]>("ssl_cipher", byte[].class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<byte[]> X509_ISSUER = new TableFieldImpl<byte[]>("x509_issuer", byte[].class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<byte[]> X509_SUBJECT = new TableFieldImpl<byte[]>("x509_subject", byte[].class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<Integer> MAX_QUESTIONS = new TableFieldImpl<Integer>("max_questions", Integer.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<Integer> MAX_UPDATES = new TableFieldImpl<Integer>("max_updates", Integer.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<Integer> MAX_CONNECTIONS = new TableFieldImpl<Integer>("max_connections", Integer.class, USER);

	/**
	 * An uncommented item
	 */
	public static final TableField<Integer> MAX_USER_CONNECTIONS = new TableFieldImpl<Integer>("max_user_connections", Integer.class, USER);

	/**
	 * No further instances allowed
	 */
	private User() {
		super("user", Mysql.MYSQL);
	}
}
