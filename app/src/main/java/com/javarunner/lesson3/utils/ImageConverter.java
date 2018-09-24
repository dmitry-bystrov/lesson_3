package com.javarunner.lesson3.utils;

import java.io.File;

public interface ImageConverter {
    File createNewFile();
    void convertImage(String inFileUri, File outFile) throws Exception ;
}
