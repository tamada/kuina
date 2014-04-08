package org.bitbucket.javamug;

import java.io.Serializable;

/**
 * This class represents an artifact information of a item in a maven repository.
 * This class is immutable object, and contains group id, artifact id, and its version. 
 * 
 * @author Haruaki Tamada
 */
public class Artifact implements Serializable {
    private static final long serialVersionUID = -5983634186608572593L;

    private String artifactId;
    private String groupId;
    private String version;

    public Artifact(String groupId, String artifactId, String version){
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getArtifactId(){
        return artifactId;
    }

    public String getGroupId(){
        return groupId;
    }

    public String getVersion(){
        return version;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Artifact){
            Artifact artifact = (Artifact)object;

            return artifactId.equals(artifact.getArtifactId()) &&
                    groupId.equals(artifact.getGroupId()) &&
                    version.equals(artifact.getVersion());
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hashCode = 1;
        hashCode = hashCode * 31 + artifactId.hashCode();
        hashCode = hashCode * 31 + groupId.hashCode();
        hashCode = hashCode * 31 + version.hashCode();
        return hashCode;
    }
}
