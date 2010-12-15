package jena;



import java.io.*;

public class Filtro implements FilenameFilter{
    String extension;
    Filtro(String extension){
        this.extension=extension;
    }
    public boolean accept(File dir, String name){
        return name.toLowerCase().endsWith(extension);
    }

}
