package com.juns.wechat.view.activity;

import java.lang.reflect.InvocationTargetException;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.juns.wechat.Constants;
import com.juns.wechat.R;
import com.juns.wechat.common.Utils;
import com.juns.wechat.view.BaseActivity;

//浏览器
public class WebViewActivity extends BaseActivity {
	private ImageView img_back;
	private TextView txt_title;
	private WebView mWebView;
	private ProgressBar progressbar;
	private String strurl = "";
	private MyTimer mTimer;
	private int progress = 0;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_web);
		super.onCreate(savedInstanceState);
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			mWebView.getClass().getMethod("onResume")
					.invoke(mWebView, (Object[]) null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			mWebView.getClass().getMethod("onPause")
					.invoke(mWebView, (Object[]) null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void initControl() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setVisibility(View.VISIBLE);
		mWebView = (WebView) findViewById(R.id.mwebview);
		progressbar = (ProgressBar) findViewById(R.id.progressbar);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void initView() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.getString(Constants.URL) != null) {
			strurl = "";
			strurl = bundle.getString(Constants.URL);
		}
		if (bundle != null && bundle.getString(Constants.Title) != null) {
			txt_title.setText(bundle.getString(Constants.Title));
		}
		if (!TextUtils.isEmpty(strurl)) {
			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.setWebViewClient(new WeiboWebViewClient());
			mWebView.setWebChromeClient(new WebChromeClient());

			if (Build.VERSION.SDK_INT >= 19) {
				mWebView.getSettings().setLoadsImagesAutomatically(true);
			} else {
				mWebView.getSettings().setLoadsImagesAutomatically(false);
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			}

			mWebView.loadUrl(strurl);
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"WebView Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.juns.wechat.view.activity/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"WebView Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.juns.wechat.view.activity/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}


	private class WeiboWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}


		@Override
		public void onReceivedError(WebView view, int errorCode,
									String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
			// TODO 显示错误404
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			if (mTimer == null) {
				mTimer = new MyTimer(15000, 50);
			}
			mTimer.start();
			progressbar.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
				mWebView.getSettings().setLoadsImagesAutomatically(true);
			}
			mTimer.cancel();
			progress = 0;
			progressbar.setProgress(100);
			progressbar.setVisibility(View.GONE);
		}
	}

	@Override
	protected void initData() {

	}



	/* 定义一个倒计时的内部类 */
	private class MyTimer extends CountDownTimer {
		public MyTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			progress = 100;
			progressbar.setVisibility(View.GONE);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			if (progress == 100) {
				progressbar.setVisibility(View.GONE);
			} else {
				progressbar.setProgress(progress++);
			}
		}
	}

	@Override
	protected void setListener() {
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mWebView.canGoBack())
					mWebView.goBack();
				else
					Utils.finish(WebViewActivity.this);
			}
		});
	}

}
