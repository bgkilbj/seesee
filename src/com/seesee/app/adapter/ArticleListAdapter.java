package com.seesee.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.seesee.app.R;
import com.seesee.app.fragment.CommonFragment;
import com.seesee.app.model.ArticleItem;

public class ArticleListAdapter extends ArrayAdapter<ArticleItem> {
	private List<ArticleItem> mArticleItems;
	private int resourceId;
	private Context mContext;

	public ArticleListAdapter(Context context, int textViewResourceId,
			List<ArticleItem> objects) {
		super(context, textViewResourceId, objects);
		this.mContext=context;
		this.mArticleItems=objects;
		resourceId = textViewResourceId;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArticleItems.size();
	}
	

	@Override
	public ArticleItem getItem(int position) {
		// TODO Auto-generated method stub
		return mArticleItems.get(position);
	}

	/**
	 * 获取Item的ID
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ArticleItem mArticleItem = mArticleItems.get(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView=(ImageView)view.findViewById(R.id.item_image);
			viewHolder.mTitle = (TextView) view.findViewById(R.id.item_title);
			viewHolder.mUsername = (TextView) view.findViewById(R.id.item_username);
			viewHolder.mDate = (TextView) view.findViewById(R.id.item_date);
			viewHolder.mType = (TextView) view.findViewById(R.id.item_type);



			view.setTag(viewHolder); // 将ViewHolder存储在View中
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		final String imageUrl=mArticleItem.getContentImg();
		if (mArticleItem.getContentImg().equals(viewHolder.mImageView.getTag())) {
			
		}else {
			ImageLoader.getInstance().displayImage(imageUrl,viewHolder.mImageView,CommonFragment.options);
			viewHolder.mImageView.setTag(imageUrl);
		}

//		displayImage(mArticleItem.getContentImg(),
//				viewHolder.mImageView, MainActivity.options);
		viewHolder.mTitle.setText(mArticleItem.getTitle());
		 viewHolder.mUsername.setText(mArticleItem.getUsername());//
		 viewHolder.mDate.setText(mArticleItem.getDate());
		 viewHolder.mType.setText(mArticleItem.getType());

		return view;
	}
	

	class ViewHolder {
		ImageView mImageView;
		 TextView mTitle;
		 TextView mUsername;
		 TextView mDate;
		 TextView mType;	}

	public void refreshData(List<ArticleItem> array) {
		this.mArticleItems = array;
		notifyDataSetChanged();
	}
}