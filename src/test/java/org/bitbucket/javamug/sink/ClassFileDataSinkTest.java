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
import org.junit.Before;
import org.junit.Test;

public class ClassFileDataSinkTest {
    private DataSink sink;
    private DataSource source;

    @Before
    public void setUp() throws Exception{
        sink = DataSinkBuilder.getBuilder().build("hoge1/hello/HelloWorld.class");
    }

    @Test
    public void testFromJar() throws Exception{
        source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello.jar"
        );

        Iterator<Entry> iterator = source.iterator();
        iterator.next();
        Entry entry2 = iterator.next();
        iterator.next();

        sink.putEntry(entry2);
        sink.close();

        File file = new File("hoge1/hello/HelloWorld.class");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testFromDir() throws Exception{
        source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources"
        );

        Iterator<Entry> iterator = source.iterator();
        Entry entry = iterator.next();

        sink.putEntry(entry);
        sink.close();

        File file = new File("hoge1/hello/HelloWorld.class");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testFromWar() throws Exception{
        source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello.war"
        );

        Iterator<Entry> iterator = source.iterator();
        iterator.next();
        Entry entry = iterator.next();

        sink.putEntry(entry);
        sink.close();

        File file = new File("hoge1/hello/HelloWorld.class");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testFromClass() throws Exception{
        source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello/HelloWorld.class"
        );

        Iterator<Entry> iterator = source.iterator();
        Entry entry = iterator.next();

        sink.putEntry(entry);
        sink.close();

        File file = new File("hoge1/hello/HelloWorld.class");
        Assert.assertTrue(file.exists());
    }

    @After
    public void tearDown() throws Exception{
        new File("hoge1/hello/HelloWorld.class").delete();        
        new File("hoge1/hello").delete();        
        new File("hoge1").delete();        
    }
}
