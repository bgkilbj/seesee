package com.seesee.app.util;

import android.view.View;

/** 
 * item点击回调接口 
 *  
 * @author wen_er 
 *  
 */  
public interface ItemClickListener {  
  
    /** 
     * Item 普通点击 
     */  
  
    public void onItemClick(View view, int position);  
  
    /** 
     * Item 长按 
     */  
  
    public void onItemLongClick(View view, int position);  
  
    /** 
     * Item 内部View点击 
     */  
  
    public void onItemSubViewClick(View view, int position);  
}  
