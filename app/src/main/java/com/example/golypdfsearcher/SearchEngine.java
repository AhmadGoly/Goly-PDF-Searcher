package com.example.golypdfsearcher;

import java.io.File;
import java.util.ArrayList;

public class SearchEngine {
    public SearchEngine() {
    }
    public static ArrayList<File> searchForFilesOnly
            (File path, String name, boolean isWord, boolean isPDF, boolean isTxt){
        ArrayList<File> filesFound = new ArrayList<>();
        File[] list = path.listFiles();
        for(int i = 0 ; i<list.length ; i++){
            if ( list[i].isDirectory() ) {
                filesFound.addAll(searchForFilesOnly(list[i],name,isWord,isPDF,isTxt));
            }
            else {
                if(isWord){
                    String x = list[i].getAbsoluteFile().toString().toLowerCase();
                    if(x.endsWith(".doc") || x.endsWith(".docx"))
                        if(x.toLowerCase().contains(name))filesFound.add(list[i]);
                }
                if(isPDF)
                    if(list[i].getAbsoluteFile().toString().toLowerCase().endsWith(".pdf"))
                        if(list[i].getAbsoluteFile().toString().toLowerCase().contains(name))
                        filesFound.add(list[i]);
                if(isTxt)
                    if(list[i].getAbsoluteFile().toString().toLowerCase().endsWith(".txt"))
                        if(list[i].getAbsoluteFile().toString().toLowerCase().contains(name))
                        filesFound.add(list[i]);
            }
        }
        return filesFound;
    }
    public ArrayList<File> searchForQuery(String path,String name,String Query,boolean isWord,boolean isPDF,boolean isTxt){





        return null;
    }

    public void walk(String path ) {
        File root = new File( path );
        File[] list = root.listFiles();
        for ( File f : list ) {
            if ( f.isDirectory() ) {
                //walk( f.getAbsolutePath() );
                //System.err.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                if(f.getAbsoluteFile().toString().endsWith(".pdf"))
                    System.err.println( "File:" + f.getAbsoluteFile() );
            }
        }
    }
}
