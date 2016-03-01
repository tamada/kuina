package com.github.kunai;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import com.github.kunai.Entry;
import com.github.kunai.EntryBuilder;
import com.github.kunai.source.DataSource;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import static org.junit.Assert.assertThat;

public class MemoryEntryTest {
    private byte[] data;

    @Before
    public void setUp() throws Exception{
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                FileInputStream in = new FileInputStream(
                    "target/test-classes/resources/hello/HelloWorld.class")
                ){
            int read;
            while((read = in.read()) != -1){
                out.write(read);
            }
            data = out.toByteArray();
        }
    }

    @Test
    public void testBasic() throws Exception{
        Entry entry = EntryBuilder.getBuilder().build("hello/HelloWorld.class", data);
        DataSource source = entry.getSource();

        assertThat(entry.getClassName(), is(("hello.HelloWorld")));
        assertThat(entry.getResourcePath(), is("hello/HelloWorld.class"));
        assertThat(entry.getType(), is(Entry.Type.CLASS_FILE));
        assertThat(source.getBase(), is(nullValue()));
    }
}
