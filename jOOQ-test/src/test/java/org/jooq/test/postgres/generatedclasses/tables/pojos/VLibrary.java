/**
 * This class is generated by jOOQ
 */
package org.jooq.test.postgres.generatedclasses.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VLibrary implements org.jooq.test.postgres.generatedclasses.tables.interfaces.IVLibrary {

	private static final long serialVersionUID = 1709828093;

	private final java.lang.String author;
	private final java.lang.String title;

	public VLibrary(
		java.lang.String author,
		java.lang.String title
	) {
		this.author = author;
		this.title = title;
	}

	@Override
	public java.lang.String getAuthor() {
		return this.author;
	}

	@Override
	public java.lang.String getTitle() {
		return this.title;
	}
}
