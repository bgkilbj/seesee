package com.seesee.app.activity;


import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.seesee.app.R;
import com.seesee.app.fragment.CommonFragment;
import com.seesee.app.fragment.MainFragment;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity  
{  
	public TabPageIndicator mIndicator ;  
    public ViewPager mViewPager ;  
    public FragmentStatePagerAdapter mAdapter ;  
    public ArrayList<Fragment> mFragments;
    private MainFragment mMainFragment;
    public ArrayList<String> mDatas;//存储首页的图标名

    @Override  
    protected void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        initData();
        mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.pager);  
        mViewPager.setOffscreenPageLimit(4);
        mMainFragment=new MainFragment();
        mFragments=new ArrayList<Fragment>();
        mDatas=new ArrayList<String>();
        mAdapter = new TabAdapter(getSupportFragmentManager());  
        mViewPager.setAdapter(mAdapter);  
        mIndicator.setViewPager(mViewPager, 0); 
        
        mDatas.add("更多");
        mFragments.add(mMainFragment);
        loadSharedPreference();
 
          
    }  
    
    private void loadSharedPreference()
    {
    	SharedPreferences prefs = getSharedPreferences("main", 0);
    	int count=prefs.getInt("count", 0);
    	if(count!=0)
    	{
    		for (int i = 0; i < count; i++) 
    		{
    			int temp=i+1;
    			int temp_typeid=prefs.getInt(""+temp, 0);
    	    	SharedPreferences sprefs = getSharedPreferences("articletypename", 0);
    	    	String temp_typename=sprefs.getString(""+temp_typeid, "");
    			addFragment(-1,temp_typeid,temp_typename);//-1表示初始化加载
			}
    	}
    	else {
    		mAdapter.notifyDataSetChanged();
        	mIndicator.notifyDataSetChanged();
		}
    	

    }
    
    public void deleteFragment(int position)
    {
    	Log.d("Fragment", "delete Fragment "+position);
		mDatas.remove(position);//主页存在mDatas的最后一个元素
		mFragments.remove(position+1);//主页存在mFragments的第一个元素
		SharedPreferences prefs = getSharedPreferences("main", 0);
    	SharedPreferences.Editor editor=prefs.edit();
    	int temp_count=prefs.getInt("count", 0);
    	editor.putInt("count", temp_count-1);
    	for (int i = (position+1); i < mDatas.size(); i++) {
    		int tmp=i+1;
    		editor.putInt(""+i, prefs.getInt(""+tmp, -1));
        	Log.d("Fragment", tmp+"位的"+prefs.getInt(""+tmp, -1)+"移到 "+i);

		}
		editor.commit();
		//删除最后1位重复
		SharedPreferences.Editor editor2 = getSharedPreferences("main", 0).edit();
		editor2.remove(String.valueOf(mDatas.size()));
		editor2.commit();

    	Log.d("Fragment", mDatas.size()+"位的"+prefs.getInt(""+mDatas.size(), -1)+"被删除");
    	Log.d("Fragment", "1位的"+prefs.getInt(""+1, -1));
    	Log.d("Fragment", "2位的"+prefs.getInt(""+2, -1));
    	Log.d("Fragment", "3位的"+prefs.getInt(""+3, -1));

    	
		mAdapter.notifyDataSetChanged();
        mIndicator.notifyDataSetChanged();

    }
    
    public void addFragment(int position,int typeId,String typeName)
    {
    	Log.d("Fragment", "add Fragment "+typeId + typeName);
		mDatas.add(mDatas.size() - 1,typeName);
    	CommonFragment mCommonFragment=new CommonFragment(typeId,typeName);
    	mFragments.add(mCommonFragment);
    	mAdapter.notifyDataSetChanged();
        mIndicator.notifyDataSetChanged();

    	if (position!=-1) {
    		SharedPreferences prefs = getSharedPreferences("main", 0);
        	SharedPreferences.Editor editor=prefs.edit();
        	editor.putInt(""+position, typeId);
        	int temp_count=prefs.getInt("count", 0);
        	editor.putInt("count", ++temp_count);
        	editor.commit();
		}
    }
    

    
    protected void initData()//初次运行的话,初始化typeid与typename的对应
	  {
 
    	SharedPreferences sprefs = getSharedPreferences("articletypename", 0);
    	if (sprefs.getBoolean("isfirst", true)) {
    		SharedPreferences.Editor editor=sprefs.edit();
    		editor.putString("0", "热点");
    		editor.putString("1", "推荐");
    		editor.putString("2", "段子");
    		editor.putString("17", "学习");
    		editor.putString("18", "星座");
    		editor.putString("19", "体育");
    		editor.putBoolean("isfirst",false);
    		editor.commit();   		
		}

	  }
    
    class TabAdapter extends FragmentStatePagerAdapter  
    {        
      
        public TabAdapter(FragmentManager fm)  
        {  
            super(fm);  

        }  
      
      


		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			return super.instantiateItem(container, position);
		}


		@Override  
        public Fragment getItem(int arg0)  
        {  
        	
             return mFragments.get(arg0);
        }  
		@Override
	    public void destroyItem(View collection, int position, Object o) {
	        View view = (View)o;
	        ((ViewPager) collection).removeView(view);
	        view = null;
	    }
      
        @Override  
        public CharSequence getPageTitle(int position)  
        {  
//            if ((mFragments.get(position))instanceof MainFragment) {
//            	return ((MainFragment)(mFragments.get(position))).TYPE_NAME;
//			}
            if (position==0) {
				return "主页";
			}
            else {
            	Log.d("Fragment", "TITLE is "+((CommonFragment)(mFragments.get(position))).TYPE_NAME);
				return ((CommonFragment)(mFragments.get(position))).TYPE_NAME;
			}
        }  
      
      
        @Override  
        public int getCount()  
        {  
            return mFragments.size();  
        }


		@Override
		public int getItemPosition(Object object) {

			if (object instanceof MainFragment) {
	            return POSITION_UNCHANGED;    

			}
			else {
				 return POSITION_NONE;

			}			
		}  
      
      
    }  
    
}