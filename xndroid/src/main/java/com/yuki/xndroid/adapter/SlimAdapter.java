package com.yuki.xndroid.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.yuki.xndroid.adapter.injector.IViewInjector;
import com.yuki.xndroid.adapter.loadmore.LoadMoreView;
import com.yuki.xndroid.adapter.loadmore.SlimMoreLoader;

import java.util.List;


public class SlimAdapter<T> extends RecyclerView.Adapter<SlimViewHolder> {
	private interface IViewHolderCreator {
		SlimViewHolder create(ViewGroup parent);
	}
	
	public interface MultiItm<T> {
		int setMultiType(T item);
	}
	
	private final int TYPE_LOAD_MORE = -1;
	private SlimMoreLoader moreLoader;
	//加载更多view
	private LoadMoreView mLoadMoreView = new SimpleLoadMoreView();
	private List<SlimViewHolder> headerItems;
	private List<SlimViewHolder> footerItems;
	
	private MultiItm<T> multiItm;
	
	private SparseArray<IViewHolderCreator> creators = new SparseArray<>();
	
	private SlimAdapter() {
	}
	
	public static <T> SlimAdapter<T> create() {
		return new SlimAdapter<>();
	}
	
	private List<T> data;
	
	public void updateData(List<T> data) {
		
		this.data = data;
		notifyDataSetChanged();
	}
	
	public List<T> getData() {
		return data;
	}
	
	
	@Nullable
	public T getItem(@IntRange(from = 0) int position) {
		if (position >= 0 && position < data.size())
			return data.get(position);
		else
			return null;
	}
	
	@Override
	public int getItemCount() {
		return data == null ? 0 : data.size() + (moreLoader == null ? 0 : 1);
	}
	
	@Override
	public int getItemViewType(int position) {
		if (moreLoader != null && position == data.size()) {
			return TYPE_LOAD_MORE;
		}
		return getDataType(data.get(position));
	}
	
	private int getDataType(T item) {
		if (multiItm != null)
			return multiItm.setMultiType(item);
		return 0;
	}
	
	
	@Override
	public SlimViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case TYPE_LOAD_MORE:
				return new SlimViewHolder(mLoadMoreView) {
					@Override
					protected void onBind(int data, IViewInjector injector) {
					
					}
				};
			default:
				System.out.println(viewType);
				return creators.get(viewType).create(parent);
		}
	}
	
	@Override
	public final void onBindViewHolder(SlimViewHolder holder, int position) {
		holder.bind(position);
	}
	
	
	public SlimAdapter<T> multiItem(MultiItm<T> multiItm) {
		if (this.multiItm == null)
			this.multiItm = multiItm;
		return this;
	}
	
	/*
	* 将数据源与布局文件进行注册
	* */
	public SlimAdapter<T> register(final int layoutRes, final SlimInjector<T> slimInjector) {
		System.out.println(layoutRes);
		creators.put(layoutRes, new IViewHolderCreator() {
			@Override
			public SlimViewHolder create(ViewGroup parent) {
				return new SlimViewHolder(parent, layoutRes) {
					@Override
					protected void onBind(int position, IViewInjector injector) {
						slimInjector.onInject(data.get(position), injector);
					}
				};
			}
		});
		return this;
	}
	
	public SlimAdapter<T> setLoadMoreView(LoadMoreView loadingView) {
		this.mLoadMoreView = loadingView;
		
	}
	
	public SlimAdapter<T> enableLoadMore(SlimMoreLoader slimMoreLoader) {
		this.moreLoader = slimMoreLoader;
		slimMoreLoader.setSlimAdapter(this);
		return this;
	}
	
	public SlimAdapter<T> attachTo(RecyclerView... recyclerViews) {
		
		for (RecyclerView recyclerView : recyclerViews) {
			recyclerView.setAdapter(this);
		}
		return this;
	}
	
	/*
	* 在绑定时添加滚动监听
	* */
	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		if (moreLoader != null) {
			recyclerView.addOnScrollListener(moreLoader);
		}
	}
	
	/*
	* 在解绑时移除滚动监听
	* */
	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		if (moreLoader != null) {
			recyclerView.removeOnScrollListener(moreLoader);
		}
		super.onDetachedFromRecyclerView(recyclerView);
	}
	
	
}
