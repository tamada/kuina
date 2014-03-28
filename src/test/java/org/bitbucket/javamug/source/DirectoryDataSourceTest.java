package org.bitbucket.javamug.source;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.bitbucket.javamug.Entry;
import org.bitbucket.javamug.source.DataSource;
import org.bitbucket.javamug.source.DataSourceBuilder;
import org.junit.Before;
import org.junit.Test;

public class DirectoryDataSourceTest {
    private DataSource source;

    @Before
    public void setUp() throws Exception{
        source = DataSourceBuilder.getBuilder().build("target/test-classes/resources");
    }

    @Test
    public void testBasic() throws Exception{
        assertThat(source.getType(), is(DataSource.Type.DIRECTORY));
        assertThat(source.getBase(), is("target/test-classes/resources"));

        Iterator<Entry> iterator = source.iterator();
        assertTrue(iterator.hasNext());
        Entry entry1 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry2 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry3 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry4 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry5 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry6 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry7 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry8 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry9 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry10 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry11 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry12 = iterator.next();
        assertFalse(iterator.hasNext());

        assertThat(entry1.getResourcePath(), is("hello.jar"));
        assertThat(entry1.getType(), is(Entry.Type.JAR_FILE));

        assertThat(entry2.getResourcePath(), is("hello.war"));
        assertThat(entry2.getType(), is(Entry.Type.WAR_FILE));

        assertThat(entry3.getResourcePath(), is("hello/HelloWorld.class"));
        assertThat(entry3.getType(), is(Entry.Type.CLASS_FILE));
        assertThat(entry3.getClassName(), is("hello.HelloWorld"));

        assertThat(entry4.getResourcePath(), is("hello/HelloWorld.java"));
        assertThat(entry4.getType(), is(Entry.Type.SOURCE_FILE));

        assertThat(entry5.getResourcePath(), is("m2/repository/org/ow2/asm/asm/4.1/_maven.repositories"));
        assertThat(entry5.getType(), is(Entry.Type.RESOURCE));

        assertThat(entry6.getResourcePath(), is("m2/repository/org/ow2/asm/asm/4.1/asm-4.1-sources.jar"));
        assertThat(entry6.getType(), is(Entry.Type.SOURCE_JAR_FILE));

        assertThat(entry7.getResourcePath(), is("m2/repository/org/ow2/asm/asm/4.1/asm-4.1-sources.jar.sha1"));
        assertThat(entry7.getType(), is(Entry.Type.SHA1));

        assertThat(entry8.getResourcePath(), is("m2/repository/org/ow2/asm/asm/4.1/asm-4.1.jar"));
        assertThat(entry8.getType(), is(Entry.Type.JAR_FILE));

        assertThat(entry9.getResourcePath(), is("m2/repository/org/ow2/asm/asm/4.1/asm-4.1.jar.sha1"));
        assertThat(entry9.getType(), is(Entry.Type.SHA1));

        assertThat(entry10.getResourcePath(), is("m2/repository/org/ow2/asm/asm/4.1/asm-4.1.pom"));
        assertThat(entry10.getType(), is(Entry.Type.POM));

        assertThat(entry11.getResourcePath(), is("m2/repository/org/ow2/asm/asm/4.1/asm-4.1.pom.sha1"));
        assertThat(entry11.getType(), is(Entry.Type.SHA1));

        assertThat(entry12.getResourcePath(), is("m2/repository/org/ow2/asm/asm/4.1/m2e-lastUpdated.properties"));
        assertThat(entry12.getType(), is(Entry.Type.PROPERTY_FILE));
    }
}
