package com.seesee.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.seesee.app.R;

public class ArticleActivity extends Activity {

	private String urlString;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		urlString = getIntent().getStringExtra("url");
		setContentView(R.layout.activity_article);
		webView = (WebView) findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url); // 根据传入的参数再去加载新的网页
				return true; // 表示当前WebView可以处理打开新网页的请求，不用借助系统浏览器

			}
		});
		webView.loadUrl(urlString);

	}

}
