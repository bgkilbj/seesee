package com.seesee.app.util;

import android.view.View;

/** 
 * item����ص��ӿ� 
 *  
 * @author wen_er 
 *  
 */  
public interface ItemClickListener {  
  
    /** 
     * Item ��ͨ��� 
     */  
  
    public void onItemClick(View view, int position);  
  
    /** 
     * Item ���� 
     */  
  
    public void onItemLongClick(View view, int position);  
  
    /** 
     * Item �ڲ�View��� 
     */  
  
    public void onItemSubViewClick(View view, int position);  
}  
