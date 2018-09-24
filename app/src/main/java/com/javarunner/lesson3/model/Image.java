package com.javarunner.lesson3.model;

import com.javarunner.lesson3.utils.ImageConverter;
import com.javarunner.lesson3.utils.ImageConverterImpl;

import java.io.File;

import io.reactivex.Completable;

public class Image {
    private ImageConverter imageConverter;
    private File file;

    public Image() {
        this.imageConverter = new ImageConverterImpl();
        this.file = imageConverter.createNewFile();
    }

    public Completable convert(String fileUri) {
        return Completable.create(emitter -> {
            try {
                imageConverter.convertImage(fileUri, file);
                Thread.sleep(5000);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onComplete();
            }
        });
    }

    public File getFile() {
        return file;
    }
}
