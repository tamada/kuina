package org.bitbucket.javamug;

import org.bitbucket.javamug.source.DataSource;

public class ClassEntry extends AbstractEntry {
    private String className;

    public ClassEntry(DataSource source, String className){
        super(Type.CLASS, source, className.replace('.', '/') + ".class");
        this.className = className;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
