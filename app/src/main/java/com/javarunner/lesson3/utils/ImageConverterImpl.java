package com.javarunner.lesson3.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.javarunner.lesson3.MainClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageConverterImpl implements ImageConverter {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.ROOT);
    private ContentResolver contentResolver;
    private File externalFilesDir;

    public ImageConverterImpl() {
        this.contentResolver = MainClass.getContext().getContentResolver();
        this.externalFilesDir = MainClass.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    public File createNewFile() {
        String fileName = String.format("%s%s%s", "IMG_", simpleDateFormat.format(new Date()), ".png");
        return new File(externalFilesDir, fileName);
    }

    public void convertImage(String inFileUri, File outFile) throws Exception {
        InputStream inputStream = contentResolver.openInputStream(Uri.parse(inFileUri));
        OutputStream outputStream = new FileOutputStream(outFile);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
