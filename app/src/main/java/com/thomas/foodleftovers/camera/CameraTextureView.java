package com.thomas.foodleftovers.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import com.thomas.foodleftovers.MainActivity;

import java.util.Collections;

public class CameraTextureView extends TextureView implements TextureView.SurfaceTextureListener
{
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mCaptureRequest;
    private CameraCaptureSession mPreviewSession;

    public CameraTextureView(Context context)
    {
        super(context);
    }

    public CameraTextureView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CameraTextureView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        /* Ajout du listener de la texture */
        setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1)
    {
        /* Texture dispo, ouverture de la caméra */
        openCamera();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1)
    {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture)
    {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture)
    {

    }

    /**
     * Ouvre la caméra dorsale
     */
    public void openCamera()
    {
        CameraManager manager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        try
        {
            /* Récupération de la caméra */
            assert manager != null;
            String cameraId = manager.getCameraIdList()[0];

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                Log.e(MainActivity.APP_TAG, "Pas de permission pour ouvrir la caméra");
                return;
            }

            Log.i(MainActivity.APP_TAG, "Ouverture de la caméra en cours");
            manager.openCamera(cameraId, new CameraDevice.StateCallback()
            {
                @Override
                public void onOpened(@NonNull CameraDevice camera)
                {
                    /* Ouverture ok, démarrage de la caméra */
                    mCameraDevice = camera;
                    startCamera();
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera)
                {
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error)
                {
                }
            }, null);
        }
        catch (Exception e)
        {
            Log.e("ERROR", "Erreur lors de l'ouverture de la caméra: " + e.getMessage());
        }
    }

    /**
     * Démarre la caméra
     */
    private void startCamera()
    {
        Log.i(MainActivity.APP_TAG, "Starting camera");
        if (mCameraDevice == null || !isAvailable())
            return;

        SurfaceTexture texture = getSurfaceTexture();
        if (texture == null)
            return;

        Surface surface = new Surface(texture);
        try
        {
            mCaptureRequest = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        }
        catch (Exception e)
        {
            Log.e(MainActivity.APP_TAG, "Erreur lors de la création de la requête de la caméra");
        }

        mCaptureRequest.addTarget(surface);
        try
        {
            mCameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback()
            {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session)
                {
                    mPreviewSession = session;
                    changePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session)
                {
                }
            }, null);
        }
        catch (Exception e)
        {
            Log.e(MainActivity.APP_TAG, "Error lors de la création de la session de la caméra");
        }
    }

    /**
     * Met à jour le flux
     */
    private void changePreview()
    {
        if (mCameraDevice == null)
            return;

        mCaptureRequest.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        HandlerThread thread = new HandlerThread("Change preview");
        thread.start();
        Handler handler = new Handler(thread.getLooper());
        try
        {
            mPreviewSession.setRepeatingRequest(mCaptureRequest.build(), null, handler);
        }
        catch (Exception e)
        {
            Log.e(MainActivity.APP_TAG, "Erreur lors de la mise à jour du flux");
        }
    }
}
