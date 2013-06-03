package org.bitbucket.javamug.sink;

import java.io.File;
import java.util.Iterator;

import org.bitbucket.javamug.Entry;
import org.bitbucket.javamug.sink.DataSink;
import org.bitbucket.javamug.sink.DataSinkBuilder;
import org.bitbucket.javamug.source.DataSource;
import org.bitbucket.javamug.source.DataSourceBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class JarFileDataSinkTest {
    @Test
    public void testFromJar() throws Exception{
        DataSource source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello.jar"
        );
        DataSink sink = DataSinkBuilder.getBuilder().build("hello1.jar");

        Iterator<Entry> iterator = source.iterator();
        Entry entry1 = iterator.next();
        Entry entry2 = iterator.next();
        Entry entry3 = iterator.next();

        sink.putEntry(entry1);
        sink.putEntry(entry2);
        sink.putEntry(entry3);
        sink.close();

        File file = new File("hello1.jar");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testFromDir() throws Exception{
        DataSource source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/"
        );
        DataSink sink = DataSinkBuilder.getBuilder().build("hello2.jar");

        Iterator<Entry> iterator = source.iterator();
        Entry entry1 = iterator.next();
        Entry entry2 = iterator.next();
        Entry entry3 = iterator.next();
        Entry entry4 = iterator.next();

        sink.putEntry(entry1);
        sink.putEntry(entry2);
        sink.putEntry(entry3);
        sink.putEntry(entry4);
        sink.close();

        File file = new File("hello2.jar");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testFromFile() throws Exception{
        DataSource source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello/HelloWorld.class"
        );
        DataSink sink = DataSinkBuilder.getBuilder().build("hello3.jar");

        Iterator<Entry> iterator = source.iterator();
        Entry entry1 = iterator.next();

        sink.putEntry(entry1);
        sink.close();

        File file = new File("hello3.jar");
        Assert.assertTrue(file.exists());
    }

    @After
    public void tearDown() throws Exception{
        new File("hello1.jar").delete();
        new File("hello2.jar").delete();
        new File("hello3.jar").delete();
    }
}
