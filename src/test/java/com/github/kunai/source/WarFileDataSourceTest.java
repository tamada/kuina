package com.github.kunai.source;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import com.github.kunai.Entry;
import com.github.kunai.source.DataSource;
import com.github.kunai.source.DataSourceBuilder;
import org.junit.Before;
import org.junit.Test;

public class WarFileDataSourceTest {
    private DataSource source;

    @Before
    public void setUp() throws Exception{
        source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello.war"
        );
    }

    @Test
    public void testBasic() throws Exception{
        assertThat(source.getType(), is(DataSource.Type.JAR_FILE));
        assertThat(source.getBase(), is("target/test-classes/resources/hello.war"));

        Iterator<Entry> iterator = source.iterator();
        assertTrue(iterator.hasNext());
        Entry entry1 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry2 = iterator.next();
        assertFalse(iterator.hasNext());

        assertThat(entry1.getType(), is(Entry.Type.SOURCE_FILE));
        assertThat(entry1.getResourcePath(), is("hello/src/hello/HelloWorld.java"));

        assertThat(entry2.getType(), is(Entry.Type.CLASS_FILE));
        assertThat(entry2.getClassName(), is("hello.HelloWorld"));
        assertThat(entry2.getResourcePath(), is("hello/WEB-INF/classes/hello/HelloWorld.class"));
    }
}
