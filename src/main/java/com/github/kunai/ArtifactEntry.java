package com.github.kunai;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class is an entry class with artifact information in a maven repository. 
 * 
 * @author Haruaki Tamada
 */
public class ArtifactEntry extends AbstractEntry {
    private Artifact artifact;
    private Entry entry;

    public ArtifactEntry(Entry entry, Artifact artifact) {
        super(entry.getType(), entry.getSource(), entry.getResourcePath());
        this.entry = entry;
        this.artifact = artifact;
    }

    public Artifact getArtifact(){
        return artifact;
    }

    @Override
    public String getClassName(){
        return entry.getClassName();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return entry.getInputStream();
    }
}
