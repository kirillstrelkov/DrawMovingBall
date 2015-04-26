package com.example.kirill.drawmovingball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;

public class DrawScene extends View {
    public static final int PADDING = 100;

    private final int FRAME_RATE = 30;
    private int x;
    private int y;
    private int dx = 5;
    private int dy = 5;
    private int imgWidth;
    private int imgHeight;
    private int screenWidth;
    private int screenHeight;

    private Paint canvasPaint;
    private Paint paint;
    private Bitmap bitmap;
    private Rect rect;


    private Handler h;
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public DrawScene(Context context, int startX, int startY) {
        super(context);
        x = startX;
        y = startY;

        canvasPaint = new Paint();
        canvasPaint.setStyle(Paint.Style.FILL);
        canvasPaint.setColor(Color.YELLOW);

        paint = new Paint();
        paint.setColor(Color.GREEN);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.football);
        double scalingFactor = 0.25;
        imgWidth = (int) (bitmap.getWidth() * scalingFactor);
        imgHeight = (int) (bitmap.getHeight() * scalingFactor);
        bitmap = Bitmap.createScaledBitmap(bitmap, imgWidth, imgHeight, false);

        h = new Handler();
    }

    public int getCurrentX() {
        return x;
    }

    public int getCurrentY() {
        return y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPaint(canvasPaint);

        if (screenWidth == 0) {
            screenWidth = this.getWidth();
        }
        if (screenHeight == 0) {
            screenHeight = this.getHeight();
        }

        if (screenWidth < screenHeight) {
            rect = new Rect(PADDING, PADDING, screenWidth - PADDING, screenWidth - PADDING * 2);
        } else {
            rect = new Rect(PADDING, PADDING, screenHeight - PADDING, screenHeight - PADDING * 2);
        }

        paint.setColor(Color.CYAN);
        canvas.drawRect(rect, paint);

        canvas.drawBitmap(bitmap, x, y, paint);

        x += dx;
        y += dy;

        if ((x > rect.right - imgWidth) || (x < rect.left)) {
            dx *= -1;
        }

        if ((y > rect.bottom - imgHeight) || (y < rect.top)) {
            dy *= -1;
        }

        h.postDelayed(r, FRAME_RATE);
    }
}
