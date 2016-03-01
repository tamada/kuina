package com.github.kunai.source;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import com.github.kunai.Entry;
import com.github.kunai.source.DataSource;
import com.github.kunai.source.DataSourceBuilder;
import com.github.kunai.source.DataSourceNotFoundException;
import com.github.kunai.source.UnknownDataSourceException;
import org.junit.Before;
import org.junit.Test;

public class ClassFileDataSourceTest {
    private DataSource source;

    @Before
    public void setUp() throws Exception{
        source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello/HelloWorld.class"
        );
    }

    @Test
    public void testBasic() throws Exception{
        assertThat(source.getType(), is(DataSource.Type.CLASS_FILE));
        assertThat(source.getBase(), is("target/test-classes/resources/hello/HelloWorld.class"));

        Iterator<Entry> iterator = source.iterator();
        assertTrue(iterator.hasNext());
        Entry entry1 = iterator.next();

        assertThat(entry1.getType(), is(Entry.Type.CLASS_FILE));
        assertThat(entry1.getClassName(), is("hello.HelloWorld"));
        assertThat(entry1.getResourcePath(), is("target/test-classes/resources/hello/HelloWorld.class"));
    }

    @Test(expected=UnknownDataSourceException.class)
    public void testUnknownDataSource() throws Exception{
        DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello/HelloWorld.java"
        );
    }

    @Test(expected=DataSourceNotFoundException.class)
    public void testFileNotfound() throws Exception{
        DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello2.jar"
        );
    }
}
