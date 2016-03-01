package com.github.kunai;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import com.github.kunai.Entry;
import com.github.kunai.EntryBuilder;
import com.github.kunai.source.DataSource;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertArrayEquals;

public class MemoryEntryTest {
    private byte[] data1;
    private byte[] data2;

    @Before
    public void setUp() throws Exception{
        data1 = readData(
            new FileInputStream("target/test-classes/resources/hello/HelloWorld.class")
        );
        data2 = readData(
            new FileInputStream("target/test-classes/resources/hello/HelloWorld.java")
        );
    }

    private byte[] readData(InputStream is) throws Exception{
        try(ByteArrayOutputStream out = new ByteArrayOutputStream();
                InputStream in = is){
            int read;
            while((read = in.read()) != -1){
                out.write(read);
            }
            return out.toByteArray();
        }
    }

    @Test
    public void testBasic() throws Exception{
        Entry entry = EntryBuilder.getBuilder().build("hello/HelloWorld.class", data1);
        DataSource source = entry.getSource();

        assertThat(entry.getClassName(), is(("hello.HelloWorld")));
        assertThat(entry.getResourcePath(), is("hello/HelloWorld.class"));
        assertThat(entry.getType(), is(Entry.Type.CLASS_FILE));
        assertThat(source.getBase(), is(nullValue()));

    }

    @Test
    public void testSourceResource() throws Exception{
        Entry entry = EntryBuilder.getBuilder().build("hello/HelloWorld.java", data2);
        DataSource source = entry.getSource();

        assertThat(entry.getClassName(), is(nullValue()));
        assertThat(entry.getResourcePath(), is("hello/HelloWorld.java"));
        assertThat(entry.getType(), is(Entry.Type.SOURCE_FILE));
        assertThat(source.getBase(), is(nullValue()));

    }

    @Test
    public void testResetResourcePath() throws Exception{
        Entry entry = EntryBuilder.getBuilder().build("hello/HelloWorld.class", data1);

        entry.setResourcePath("HelloWorld.class");
        entry.resetResourcePath();
        assertThat(entry.getResourcePath(), is("HelloWorld.class"));
    }

    @Test
    public void testInputStream() throws Exception{
        Entry entry = EntryBuilder.getBuilder().build("hello/HelloWorld.class", data1);

        byte[] readData = readData(entry.getInputStream());
        assertArrayEquals(readData, data1);
    }
}
