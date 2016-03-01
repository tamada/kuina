package com.github.kunai;

import com.github.kunai.source.DataSource;
import com.github.kunai.source.DataSourceBuilder;

public class Main{
    public Main(String[] args) throws Exception{
        DataSourceBuilder builder = DataSourceBuilder.getBuilder();

        for(String arg: args){
            DataSource source = builder.build(arg);

            for(Entry entry: source){
                System.out.print(entry.getResourcePath() + " [" + entry.getType() + "]: ");
                if(entry.getType() == Entry.Type.CLASS_FILE){
                    System.out.print(entry.getClassName());
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        new Main(args);
    }
}
