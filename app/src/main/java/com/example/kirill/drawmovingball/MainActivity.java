package com.example.kirill.drawmovingball;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;


public class MainActivity extends Activity {
    private int startX;
    private int startY;
    private DrawScene drawScene;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        startX = preferences.getInt("startX", DrawScene.PADDING);
        startY = preferences.getInt("startY", DrawScene.PADDING);

        drawScene = new DrawScene(this, startX, startY);
        setContentView(drawScene);
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("startX", drawScene.getCurrentX());
        editor.putInt("startY", drawScene.getCurrentY());
        editor.commit();
    }
}
