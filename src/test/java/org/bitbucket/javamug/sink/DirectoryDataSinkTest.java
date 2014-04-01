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

public class DirectoryDataSinkTest {
    @Test
    public void testFromJar() throws Exception{
        DataSource source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello.jar"
        );
        DataSink sink = DataSinkBuilder.getBuilder().build("hoge1");

        Iterator<Entry> iterator = source.iterator();
        Entry entry1 = iterator.next();
        Entry entry2 = iterator.next();
        Entry entry3 = iterator.next();

        sink.putEntry(entry1);
        sink.putEntry(entry2);
        sink.putEntry(entry3);
        sink.close();

        File file1 = new File("hoge1/META-INF/MANIFEST.MF");
        File file2 = new File("hoge1/hello/HelloWorld.class");
        File file3 = new File("hoge1/hello/HelloWorld.java");

        Assert.assertTrue(file1.exists());
        Assert.assertTrue(file2.exists());
        Assert.assertTrue(file3.exists());

        file1.delete();
        file1.getParentFile().delete();
        file2.delete();
        file3.delete();
        file2.getParentFile().delete();
        file2.getParentFile().getParentFile().delete();
    }

    @Test
    public void testFromDir() throws Exception{
        DataSource source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources"
        );
        DataSink sink = DataSinkBuilder.getBuilder().build("hoge2");

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

        File file1 = new File("hoge2/hello/HelloWorld.class");
        File file2 = new File("hoge2/hello/HelloWorld.java");
        File file3 = new File("hoge2/hello.jar");
        File file4 = new File("hoge2/hello.war");

        Assert.assertTrue(file1.exists());
        Assert.assertTrue(file2.exists());
        Assert.assertTrue(file3.exists());
        Assert.assertTrue(file4.exists());
    }

    @Test
    public void testFromFile() throws Exception{
        DataSource source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello/HelloWorld.class"
        );
        DataSink sink = DataSinkBuilder.getBuilder().build("hoge3");

        Iterator<Entry> iterator = source.iterator();
        Entry entry1 = iterator.next();
        entry1.resetResourcePath();

        sink.putEntry(entry1);
        sink.close();

        File file1 = new File("hoge3/hello/HelloWorld.class");

        Assert.assertTrue(file1.exists());
    }

    @After
    public void tearDown() throws Exception{
        new File("hoge1/META-INF/MANIFEST.MF").delete();
        new File("hoge1/hello/HelloWorld.class").delete();
        new File("hoge1/hello/HelloWorld.java").delete();
        new File("hoge1/META-INF").delete();
        new File("hoge1/hello").delete();
        new File("hoge1").delete();

        new File("hoge2/hello/HelloWorld.class").delete();
        new File("hoge2/hello/HelloWorld.java").delete();
        new File("hoge2/hello.jar").delete();
        new File("hoge2/hello.war").delete();
        new File("hoge2/hello").delete();
        new File("hoge2").delete();

        new File("hoge3/hello/HelloWorld.class").delete();
        new File("hoge3/hello").delete();
        new File("hoge3").delete();
    }
}
