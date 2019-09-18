package edu.binghamton.khanson3.statplotter;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class MyGraph extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;

    public MyGraph(Context context) {
        super(context);

        surfaceHolder = getHolder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void addDataPoints(List<List<Double>> dataPoints) {

    }
}
