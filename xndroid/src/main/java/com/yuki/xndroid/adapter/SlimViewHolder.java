package com.yuki.xndroid.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuki.xndroid.adapter.injector.IViewInjector;
import com.yuki.xndroid.adapter.injector.ViewInjector;


public abstract class SlimViewHolder extends RecyclerView.ViewHolder {
	
	private SparseArray<View> viewCache;
	
	private IViewInjector injector;
	
	public SlimViewHolder(ViewGroup parent, int itemLayoutRes) {
		this(LayoutInflater.from(parent.getContext()).inflate(itemLayoutRes, parent, false));
	}
	
	public SlimViewHolder(View itemView) {
		super(itemView);
		viewCache = new SparseArray<>();
	}
	
	public void bind(int position) {
		if (injector == null) {
			injector = new ViewInjector(this);
		}
		onBind(position, injector);
	}
	protected abstract void onBind(int data, IViewInjector injector);
	
	public final <V extends View> V id(int id) {
		View view = viewCache.get(id);
		if (view == null) {
			view = itemView.findViewById(id);
			viewCache.put(id, view);
		}
		return (V) view;
	}
	
}
