package org.bitbucket.javamug;

/**
 * Builder class of {@link Entry <code>Entry</code>}.
 * This class is designed in singleton.
 * 
 * Ordinary, this class is not used.
 * This class is used for building {@link Entry <code>Entry</code>} object
 * which is not included in a {@link org.bitbucket.javamug.source.DataSource
 * <code>DataSource</code>} object.
 *
 * <pre> Entry entry = EntryBuilder.getBuilder().build(
 *   "resource name",
 *   getSomeByteSequence()
 * ); </pre>
 * 
 * @author Haruaki Tamada
 */
public class EntryBuilder {
    private static final EntryBuilder builder = new EntryBuilder();

    private EntryBuilder(){
    }

    public static final EntryBuilder getBuilder(){
        return builder;
    }

    public Entry build(String resourceName, byte[] data){
        return new MemoryEntry(resourceName, data);
    }
}
