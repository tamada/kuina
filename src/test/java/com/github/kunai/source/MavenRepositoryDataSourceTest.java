package com.github.kunai.source;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import com.github.kunai.ArtifactEntry;
import org.junit.Before;
import org.junit.Test;

public class MavenRepositoryDataSourceTest {
    private MavenRepositoryDataSource source;

    @Before
    public void setUp() throws Exception{
        source = new MavenRepositoryDataSource("target/test-classes/resources/m2/repository");
    }

    @Test
    public void testBasic() throws Exception{
        assertThat(source.getType(), is(DataSource.Type.MAVEN_REPOSITORY));
        assertThat(source.getBase(), is("target/test-classes/resources/m2/repository"));

        Iterator<ArtifactEntry> iterator = source.entries();
        while(iterator.hasNext()){
            iterator.hasNext();
        }
    }
}
