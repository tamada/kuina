package org.bitbucket.javamug;

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
