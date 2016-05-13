package com.seesee.app.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seesee.app.R;
import com.seesee.app.util.ItemClickListener;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder>
{
	private Context mContext;
	private List<String> mTypes;
    private ItemClickListener mItemClickListener;  

	
	public MainRecyclerViewAdapter(Context context, List<String> mList) {  
        super();  
        this.mContext = context;  
        this.mTypes = mList;  
    }  
	
  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
    		parent.getContext()).inflate(R.layout.fragment_main_item, parent,
        false));
    return holder;
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, int position)
  {
    holder.tv.setText(mTypes.get(position));
  }

  @Override
  public int getItemCount()
  {
    return mTypes.size();
  }

  class MyViewHolder extends ViewHolder
  {

    TextView tv;

    public MyViewHolder(final  View view)
    {
      super(view);
      tv = (TextView) view.findViewById(R.id.item_type_name);
      //为item添加普通点击回调       
      view.setOnClickListener(new OnClickListener() {  

          @Override  
          public void onClick(View v) {  

              if (null != mItemClickListener) {  
                  mItemClickListener.onItemClick(view, getPosition());  
              }  

          }  
      });  

      //为item添加长按回调     
      view.setOnLongClickListener(new OnLongClickListener() {  

          @Override  
          public boolean onLongClick(View v) {  
              if (null != mItemClickListener) {  
                  mItemClickListener.onItemLongClick(view, getPosition());  
              }  
              return true;  
          }  
      });  
    }
  }
  
  public void setItemClickListener(ItemClickListener mItemClickListener) {  
	  
      this.mItemClickListener = mItemClickListener;  
  } 
}
