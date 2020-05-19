package com.example.closepanel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    private WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            loadSystem();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void loadSystem() throws MalformedURLException {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        hideNavigationBar();
        wb = findViewById(R.id.wb);
        wb.setWebViewClient(new MyWebViewClient());
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setAllowContentAccess(true);
        wb.getSettings().setAllowFileAccess(true);
        wb.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wb.getSettings().setLoadsImagesAutomatically(true);
        wb.getSettings().setBlockNetworkImage(false);

        wb.getSettings().setBuiltInZoomControls(false);
        wb.getSettings().setSupportZoom(false);
        File f = new File(Environment.getExternalStorageDirectory(), "Download/musseum/index.html");
        wb.loadUrl(String.valueOf(f.toURI().toURL()));
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    private void hideNavigationBar() {
        final View decorView = this.getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
    }


    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);

        File f = new File(Environment.getExternalStorageDirectory(), "Download/musseum/index.html");
        try {
            wb.loadUrl(String.valueOf(f.toURI().toURL()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}