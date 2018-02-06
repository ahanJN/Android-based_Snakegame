package com.fcmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private static final String TAG = Changliang.DEBUG_TAG + "GameView";

    public GameView(Context context)
    {
        super(context);
        getHolder().addCallback(this);
    }

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
    }
}
