package edu.binghamton.khanson3.statplotter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class MyGraph extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private List<List<Float>> dataPoints;
    private float scaleFactor;
    private float translationFactorX;
    private float translationFactorY;
    private static int screenHeight;
    private static int screenWidth;
    private float xCoordinate;
    private float yCoordinate;

    public MyGraph(Context context) {
        super(context);

        this.dataPoints = ScatterPlot.getDataPoints();
        MyGraph.screenHeight = ScatterPlot.getHeight()-500;
        MyGraph.screenWidth = ScatterPlot.getWidth();

        paint = new Paint();
        paint.setColor(Color.BLACK);

        scaleFactor = findScaleFactor(dataPoints);

        translationFactorX = findTranslationFactorX(dataPoints);
        translationFactorY = findTranslationFactorY(dataPoints);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        this.setZOrderOnTop(true);
    }

    public static float findScaleFactor(List<List<Float>> dataPoints) {
        float xRange = sortDataByX(dataPoints).get(dataPoints.size()-1).get(0) - sortDataByX(dataPoints).get(0).get(0);
        float yRange = sortDataByY(dataPoints).get(dataPoints.size()-1).get(1) - sortDataByY(dataPoints).get(0).get(1);

        if(screenWidth - xRange >= screenHeight - yRange && yRange != 0)
            return (screenHeight-50)/yRange;
        else if(screenWidth - xRange < screenHeight - yRange && xRange != 0)
            return (screenWidth-50)/xRange;
        else
            return 1;
    }

    public static float findTranslationFactorX(List<List<Float>> dataPoints) {
        //x midpoint of data
        float mpx = (sortDataByX(dataPoints).get(0).get(0)*findScaleFactor(dataPoints) + sortDataByX(dataPoints).get(dataPoints.size()-1).get(0)*findScaleFactor(dataPoints)) / 2;

        return (screenWidth/2) - mpx;
    }

    public static float findTranslationFactorY(List<List<Float>> dataPoints) {
        //y midpoint of data
        float mpy = (sortDataByY(dataPoints).get(0).get(1)*findScaleFactor(dataPoints) + sortDataByY(dataPoints).get(dataPoints.size()-1).get(1)*findScaleFactor(dataPoints)) / 2;

        return (screenHeight/2) - mpy;
    }

    public static List<List<Float>> sortDataByX(List<List<Float>> dataPoints) {
        int n = dataPoints.size();

        for(int i = 1; i < n; ++i) {
            List<Float> key = dataPoints.get(i);
            int j = i - 1;

            while(j >= 0 && dataPoints.get(j).get(0) > key.get(0)) {
                dataPoints.set(j+1, dataPoints.get(j));
                j = j - 1;
            }
            dataPoints.set(j+1, key);
        }
        return dataPoints;
    }

    public static List<List<Float>> sortDataByY(List<List<Float>> dataPoints) {
        int n = dataPoints.size();

        for(int i = 1; i < n; ++i) {
            List<Float> key = dataPoints.get(i);
            int j = i - 1;

            while(j >= 0 && dataPoints.get(j).get(1) > key.get(1)) {
                dataPoints.set(j+1, dataPoints.get(j));
                j = j - 1;
            }
            dataPoints.set(j+1, key);
        }
        return dataPoints;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //screenWidth = getWidth();
        //screenHeight = getHeight();
        plotPoints(dataPoints);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //screenHeight = i2;
        //screenWidth = i1;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void plotPoints(List<List<Float>> dataPoints) {
        Canvas canvas = surfaceHolder.lockCanvas();


        //scaleFactor = 1;
        for(List<Float> point : dataPoints) {
            //scale points
            float correctedPointX = point.get(0)*scaleFactor;
            float correctedPointY = point.get(1)*scaleFactor;

            //center points
            correctedPointX += translationFactorX;
            correctedPointY += translationFactorY;

            //reflect over x-axis
            if(correctedPointY > screenHeight/2)
                correctedPointY -= 2*(correctedPointY - (screenHeight/2));
            else
                correctedPointY += 2*((screenHeight/2) - correctedPointY);

            canvas.drawCircle(correctedPointX, correctedPointY, 10, paint);
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public List<Float> closestPoint(float x, float y) {
        if(y > screenHeight/2)
            y -= 2*(y - (screenHeight/2));
        else
            y += 2*((screenHeight/2) - y);

        x -= translationFactorX;
        y -= translationFactorY;

        x /= scaleFactor;
        y /= scaleFactor;

        List<Float> closestPoint = dataPoints.get(0);
        float closestDist = (float) Math.abs(Math.sqrt(x*x + y*y) - Math.sqrt(closestPoint.get(0)*closestPoint.get(0) + closestPoint.get(1)*closestPoint.get(1)));
        for(List<Float> point : dataPoints) {
            float dist = (float) Math.abs(Math.sqrt(x*x + y*y) - Math.sqrt(point.get(0)*point.get(0) + point.get(1)*point.get(1)));
            if(dist < closestDist) {
                closestDist = dist;
                closestPoint = point;
            }
        }
        return closestPoint;
    }

    public float getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
