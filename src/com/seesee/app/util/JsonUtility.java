package com.seesee.app.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;

import com.seesee.app.model.ArticleItem;


public class JsonUtility {

	public static void handleResponse(Context context, String response,int TYPE_ID)throws Exception  {
		try {
			JSONObject jsonObject0=new JSONObject(response);//整个是一个JSONObject
			JSONObject jsonObject1=jsonObject0.getJSONObject("showapi_res_body");//第三层是第二层的唯一一个元素，即一个JSONObject
			JSONObject jsonObject2=jsonObject1.getJSONObject("pagebean");//第三层是第二层的唯一一个元素，即一个JSONObject
			JSONArray jsonArrayt0=jsonObject2.getJSONArray("contentlist");
			ArrayList<ArticleItem> items = new ArrayList<ArticleItem>();

			for(int i=0;i<20;i++)
			{
			
				JSONObject jsonObject20=jsonArrayt0.getJSONObject(i);
				ArticleItem item=new ArticleItem();
				item.setContentImg(jsonObject20.getString("contentImg"));
				item.setTitle(jsonObject20.getString("title"));
				item.setType(jsonObject20.getString("typeName"));
				item.setDate(jsonObject20.getString("date"));
				item.setUrl(jsonObject20.getString("url"));
				item.setUsername(jsonObject20.getString("userName"));
				items.add(item);
			
			}

			saveWeatherInfo(context, items,TYPE_ID);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


public static void saveWeatherInfo(Context context,ArrayList<ArticleItem> items,int TYPE_ID) 
{
	SharedPreferences prefs = context.getSharedPreferences(""+TYPE_ID,0);
	SharedPreferences.Editor editor = prefs.edit();
	for (int i = 0; i < 20;i++) {
		editor.putString("contentImg"+i, items.get(i).getContentImg());

		editor.putString("title"+i, items.get(i).getTitle());
		editor.putString("typeName"+i, items.get(i).getType());
		editor.putString("date"+i, items.get(i).getDate());
		editor.putString("url"+i, items.get(i).getUrl());
		editor.putString("userName"+i,items.get(i).getUsername());
		
	}
	

	editor.commit();
}
}
