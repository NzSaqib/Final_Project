package com.example.geogram.utils;

import android.Manifest;

public class Permissions {

    public static final String[] PERMISSIONS  = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    public static final String[] CAMERA_PERMISSIONS  = {
            Manifest.permission.CAMERA
    };

    public static final String[] WRITE_PERMISSIONS  = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final String[] READ_PERMISSIONS  = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
}
