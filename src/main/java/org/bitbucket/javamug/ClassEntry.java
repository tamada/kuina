package org.bitbucket.javamug;

import org.bitbucket.javamug.source.DataSource;

/**
 * The concrete class of {@link Entry <code>Entry</code>} for representing
 * resource type.
 *
 * @author Haruaki Tamada
 */
public class ClassEntry extends AbstractEntry {
    private String className;

    public ClassEntry(DataSource source, String className){
        super(Type.CLASS_FILE, source, className.replace('.', '/') + ".class");
        this.className = className;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
