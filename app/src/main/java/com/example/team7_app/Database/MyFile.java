package com.example.team7_app.Database;

public class MyFile {
    private String mFile;
    private String originalPath;


    public MyFile( String file, String path)
    {
        this.mFile = file;
        this.originalPath = path;
    }
    public String getmFile() {
        return mFile;
    }

    public void setmFile(String mFile) {
        this.mFile = mFile;
    }
    public String getOriginalPath() {
        return originalPath;
    }
    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }
}