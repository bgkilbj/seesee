package com.seesee.app.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.seesee.app.R;
import com.seesee.app.activity.ArticleActivity;
import com.seesee.app.adapter.ArticleListAdapter;
import com.seesee.app.model.ArticleItem;
import com.seesee.app.util.HttpCallbackListener;
import com.seesee.app.util.HttpUtil;
import com.seesee.app.util.JsonUtility;

public class CommonFragment extends Fragment {

	//private int type = 0;
	//private Button mSendButton;
	public int TYPE_ID = 3;
	public  String TYPE_NAME="";

	private int PAGE=1;
	//private String KEY = "长寿";
	private String address;

	private ListView mArticleItemListView;
	public ArrayList<ArticleItem> mArticles = new ArrayList<ArticleItem>();
	private ArticleListAdapter mAdapter;
	public static ImageLoader imageLoader;
	public static DisplayImageOptions options; // 设置图片显示相关参数
	private SwipeRefreshLayout mSwipeRefreshWidget;

	public CommonFragment(int typeId,String typeName) {
		this.TYPE_ID = typeId;
		this.TYPE_NAME=typeName;
	}
	

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);
		SharedPreferences prefs = getActivity().getSharedPreferences(""+TYPE_ID,0);
		SharedPreferences.Editor editor = prefs.edit();
		if (prefs.getBoolean("isfirst", true)) {
			queryFromServer();
			editor.putBoolean("isfirst", false);
			editor.commit();
		}
		else {
			showArticle();
			mAdapter.notifyDataSetChanged();
			
		}

	}


	  @Override
	public void onDestroyView() {
		    Log.d("Fragment", "CommonFragment "+TYPE_ID+" onDestroyView");
		super.onDestroyView();}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common, null);
		imageLoader = ImageLoader.getInstance();
		initData();
	    Log.d("Fragment", "CommonFragment "+TYPE_ID+" onCreateView" );

		mSwipeRefreshWidget = (SwipeRefreshLayout)view. findViewById(R.id.fragment_common);

		mSwipeRefreshWidget.setSize(SwipeRefreshLayout.LARGE);
		mSwipeRefreshWidget.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
        		PAGE++;
        	    Log.d("Fragment", "CommonFragment "+PAGE+" onRefresh" );

            	queryFromServer();
            	mSwipeRefreshWidget.setRefreshing(false);

            }});



		mArticleItemListView = (ListView) view.findViewById(R.id.list);

		mAdapter = new ArticleListAdapter(getActivity(),
				R.layout.listview_item, mArticles);

		mArticleItemListView.setAdapter(mAdapter);
		mArticleItemListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ArticleItem mArticleItem = mAdapter.getItem(position);

				Intent intent = new Intent(getActivity(), ArticleActivity.class);
				intent.putExtra("url", mArticleItem.getUrl());
				startActivity(intent);
			}
		});


		return view;
	}

	public void initData() {

		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageForEmptyUri(R.drawable.load)
				.showImageOnFail(R.drawable.load).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 构建完成

	}

	private void queryFromServer() {

//		String eStr = "";
//		try {
//			eStr = URLEncoder.encode(KEY, "utf-8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		address = "http://route.showapi.com/582-2?showapi_appid=18772&typeId="
				+ TYPE_ID + "&key=" 
				//+ eStr
				+ "&page="+PAGE+"&showapi_sign=24b15a6931ba4586a8227d452e9e209e";
		Log.d("Fragment","TYPE_ID"+TYPE_ID+" is query");
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(final String response) {

				// 处理服务器返回的天气信息
				try {
					JsonUtility.handleResponse(getActivity(), response,TYPE_ID);

				} catch (Exception e) {
					e.printStackTrace();
				}
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showArticle();
						mAdapter.notifyDataSetChanged();

						//

					}
				});

			}

			@Override
			public void onError(Exception e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//mSendButton.setText("fail");
					}
				});
			}
		});
	}

	private void showArticle() {
		SharedPreferences prefs = getActivity().getSharedPreferences(
				""+TYPE_ID, 0);
		mArticles.clear();

		for (int i = 0; i < 20; i++) {
			ArticleItem mArticle0 = new ArticleItem();
			mArticles.add(mArticle0);
			mArticles.get(i).setContentImg(
					prefs.getString("contentImg" + i, ""));

			mArticles.get(i).setTitle(prefs.getString("title" + i, ""));
			mArticles.get(i).setType(prefs.getString("typeName" + i, ""));
			mArticles.get(i).setDate(prefs.getString("date" + i, ""));
			mArticles.get(i).setUrl(prefs.getString("url" + i, ""));
			mArticles.get(i).setUsername(
					"来自:" + prefs.getString("userName" + i, ""));

		}
	}
}