package com.seesee.app.fragment;

import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.seesee.app.R;
import com.seesee.app.activity.MainActivity;
import com.seesee.app.adapter.MainRecyclerViewAdapter;
import com.seesee.app.util.ItemClickListener;

public class MainFragment extends Fragment {

	private RecyclerView mRecyclerView;

	public String TYPE_NAME = "主页";

	private MainRecyclerViewAdapter mAdapter;
	private PopupWindow mPopWindow;//
	private CheckBox cb0, cb1, cb2, cb3, cb4, cb5;
	private Button mButton;
	private boolean[] isType = new boolean[6];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, null);
		initPopupWindow();
		mRecyclerView = (RecyclerView) view
				.findViewById(R.id.main_recyclerview);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		Log.d("Fragment", "MainFragment onCreateView");
		mAdapter = new MainRecyclerViewAdapter(getContext(),
				((MainActivity) getActivity()).mDatas);
		mRecyclerView.setAdapter(mAdapter);

		mAdapter.setItemClickListener(new ItemClickListener() {

			@Override
			public void onItemSubViewClick(View view, int position) {

			}

			@Override
			public void onItemLongClick(View view, int position) {
				Toast.makeText(getActivity(), position + " long click",
						Toast.LENGTH_SHORT).show();
				if (position != (((MainActivity) getActivity()).mDatas.size() - 1)) {//
					//((MainActivity) getActivity()).mDatas.remove(position);
					((MainActivity) getActivity()).deleteFragment(position);

					mAdapter.notifyItemRemoved(position);
				}

			}

			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(getActivity(), position + " click",
						Toast.LENGTH_SHORT).show();

				if (position != (((MainActivity) getActivity()).mDatas.size() - 1)) {
					((MainActivity) getActivity()).mViewPager
							.setCurrentItem(position + 1);

				} else {
					if (mPopWindow.isShowing()) {

						mPopWindow.dismiss();// 关闭
					} else {
						initPopupWindow();

						View parent = getActivity().getWindow().getDecorView();// 高度为手机实际的像素高度
						mPopWindow.showAtLocation(parent, Gravity.CENTER, 0,
								-200);
						// mPopWindow.showAsDropDown(view);// 显示
					}
				}
			}
		});
		return view;
	}

	@Override
	public void onDestroyView() {
		Log.d("Fragment", " MainFragment onDestroyView");
		super.onDestroyView();
	}

	private void initPopupWindow() {
		final boolean[] isChoosed = new boolean[6];
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View v = inflater.inflate(R.layout.fragment_main_popupwindow, null);
		mButton = (Button) v.findViewById(R.id.pupup_btn);
		cb0 = (CheckBox) v.findViewById(R.id.pupup_cb0);
		cb1 = (CheckBox) v.findViewById(R.id.pupup_cb1);
		cb2 = (CheckBox) v.findViewById(R.id.pupup_cb2);
		cb3 = (CheckBox) v.findViewById(R.id.pupup_cb3);
		cb4 = (CheckBox) v.findViewById(R.id.pupup_cb4);
		cb5 = (CheckBox) v.findViewById(R.id.pupup_cb5);

		cb0.setChecked(false);
		cb1.setChecked(false);
		cb2.setChecked(false);
		cb3.setChecked(false);
		cb4.setChecked(false);
		cb5.setChecked(false);

		mPopWindow = new PopupWindow(v);
		mPopWindow.setFocusable(true);//
		cb0.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					isChoosed[0] = true;
				} else {
					isChoosed[0] = false;
				}
			}
		});
		cb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					isChoosed[1] = true;
				} else {
					isChoosed[1] = false;
				}
			}
		});
		cb2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					isChoosed[2] = true;
				} else {
					isChoosed[2] = false;
				}
			}
		});
		cb3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					isChoosed[3] = true;
				} else {
					isChoosed[3] = false;
				}
			}
		});
		cb4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					isChoosed[4] = true;
				} else {
					isChoosed[4] = false;
				}
			}
		});
		cb5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					isChoosed[5] = true;
				} else {
					isChoosed[5] = false;
				}
			}
		});

		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				isType = isChoosed;
				for (int i = 0; i < isType.length; i++) {
					int temp_id = -1;
					String temp_name = "";
					if (isType[i]) {
						SharedPreferences sprefs = getActivity()
								.getSharedPreferences("articletypename", 0);
						if (i < 3) {
							temp_id = i;
						} else {
							temp_id = i + 14;
						}
						temp_name = sprefs.getString("" + temp_id, "");
						
						((MainActivity) getActivity()).addFragment(((MainActivity) getActivity()).mDatas
								.size(),temp_id,
								temp_name);

						mAdapter.notifyItemInserted(((MainActivity) getActivity()).mDatas
								.size() - 2);
//						if (((MainActivity) getActivity()).mFragments.size()==1) {
//							((MainActivity) getActivity()).addFragment(((MainActivity) getActivity()).mDatas
//									.size(),temp_id,
//									temp_name);
//
//							mAdapter.notifyItemInserted(((MainActivity) getActivity()).mDatas
//									.size() - 2);
//						}
//						else {
//							for(int j=1;j<((MainActivity) getActivity()).mFragments.size();i++)
//							{
//								if(temp_id==(((CommonFragment)(((MainActivity) getActivity()).mFragments).get(j)).TYPE_ID))
//								{
//									Toast.makeText(getActivity(), "页面 "+ temp_id+" 已存在", Toast.LENGTH_SHORT).show();
//									break;
//
//								}
//								if(j==((MainActivity) getActivity()).mFragments.size()-1)
//								{
//									((MainActivity) getActivity()).addFragment(((MainActivity) getActivity()).mDatas
//											.size(),temp_id,
//											temp_name);
//
//									mAdapter.notifyItemInserted(((MainActivity) getActivity()).mDatas
//											.size() - 2);
//								}
//						}
//						}


				}
				mPopWindow.dismiss();// 关闭

			}
		}});

		// 控制popupwindow的宽度和高度自适应
		// mButton.measure(View.MeasureSpec.UNSPECIFIED,
		// View.MeasureSpec.UNSPECIFIED);
		mPopWindow.setWidth(700);
		mPopWindow.setHeight(600);

		// 控制popupwindow点击屏幕其他地方消失
		mPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.title_bar));// 设置背景图片，不能在布局中设置，要通过代码来设置
		mPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上

	}

}
