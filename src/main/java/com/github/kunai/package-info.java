/**
 * <p>
 * This product supports extracting/sinking class files and resources.
 * We can use class files same way from directories, jar files, and war
 * files.
 * </p><p>
 * This product is composed 3 parts; data source, data sink, and
 * entries.  Data source is to read class files and resources from
 * directories, jar files, and war files.  Data sink is to write class
 * files and resources to directories, and jar files.  Entries are the
 * elements of data source and data sink.  Additionally, entries are
 * bridge object between data source and data sink.
 * </p><p>
 * This package contains entries classes.  Generally, instantiation of
 * entries classes are not required.  The instance of entry class is
 * build from data source class. Note that, we can use EntryBuilder class
 * for sinking new class file (for example, generated class file, or
 * loaded from different source).
 * </p>
 * 
 * @author Haruaki Tamada
 */
package com.github.kunai;
