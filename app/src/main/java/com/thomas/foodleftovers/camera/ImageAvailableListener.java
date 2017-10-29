package com.thomas.foodleftovers.camera;

import android.media.Image;
import android.media.ImageReader;
import android.util.Log;

import com.thomas.foodleftovers.MainActivity;

/**
 * Gère la récupération des images
 */
class ImageAvailableListener implements ImageReader.OnImageAvailableListener
{
    @Override
    public void onImageAvailable(ImageReader imageReader)
    {
        Image image = imageReader.acquireLatestImage();
        Log.d(MainActivity.APP_TAG, "Image available");

        image.close();
    }
}
