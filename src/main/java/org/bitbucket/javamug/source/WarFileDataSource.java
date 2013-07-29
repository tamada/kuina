package org.bitbucket.javamug.source;

import org.bitbucket.javamug.Entry;

/**
 * Concrete class of data source for reading from war file.
 *
 * @author Haruaki TAMADA
 */
class WarFileDataSource extends JarFileDatasource {
    private String prefix = null;
    WarFileDataSource(String jarfile) throws SourceException{
        super(jarfile);
    }

    @Override
    String parseClassName(String className){
        int index = className.indexOf("classes");
        prefix = className.substring(0, index - 1);
        String name = className.substring(
            index + "classes/".length(),
            className.length() - Type.CLASS_FILE.getExtension().length()
        );
        name = name.replace('/', '.');
        return name;
    }

    @Override
    String getPrefix(Entry entry){
        if(entry.getType() == Entry.Type.CLASS){
            return prefix + "/classes/";
        }
        return super.getPrefix(entry);
    }
}
