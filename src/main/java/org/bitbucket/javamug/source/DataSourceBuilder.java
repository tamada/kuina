package org.bitbucket.javamug.source;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Builder class for {@link DataSource <code>DataSource</code>}.
 * This class is designed singleton.
 * <pre> DataSource source = DataSourceBuilder().getBuilder().build(
 *   "path of data source"
 * );
 * </pre>
 * 
 * @author Haruaki Tamada
 */
public class DataSourceBuilder {
    private static final DataSourceBuilder builder = new DataSourceBuilder();

    private DataSourceBuilder(){
    }

    public static DataSourceBuilder getBuilder(){
        return builder;
    }

    public DataSource build(String name) throws SourceException{
        Path path = FileSystems.getDefault().getPath(name);

        if(Files.isRegularFile(path)){
            if(name.endsWith(".class")){
                return new ClassFileDataSource(name);
            }
            else if(name.endsWith(".jar") || name.endsWith(".zip")){
                return new JarFileDatasource(name);
            }
            else if(name.endsWith(".war")){
                return new WarFileDataSource(name);
            }
            throw new UnknownDataSourceException(name + ": unknown type");
        }
        else if(Files.isDirectory(path)){
            return new DirectoryDataSource(name);
        }
        else if(!Files.exists(path)){
            throw new DataSourceNotFoundException(name + ": not found");
        }
        else{
            throw new SourceException(name + ": unknown exception");
        }
    }
}
