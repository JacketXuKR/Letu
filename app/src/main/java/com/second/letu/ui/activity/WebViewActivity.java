package com.second.letu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.second.letu.ConstantValue.ConstantValues;
import com.second.letu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发布界面
 * Created by Jacket_Xu on 2017/4/14.
 */

public class WebViewActivity extends Activity {
    @BindView(R.id.webview)
    WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String jam = intent.getStringExtra(ConstantValues.JAM);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setSupportZoom(true);//设定支持缩放
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if("NavigateActivity".equals(jam)) {
            mWebview.loadUrl("http://155.94.144.153/by/wp-admin/post-new.php");
        } else if("MainActivity".equals(jam)) {
            mWebview.loadUrl("http://155.94.144.153/by/");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}
