package com.thomas.foodleftovers.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Gère le layout de la caméra, contient le TextureView
 */
public class CameraLayout extends RelativeLayout
{

    public CameraLayout(Context context)
    {
        super(context);
    }

    public CameraLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CameraLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
}
