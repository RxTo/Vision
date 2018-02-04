package com.yuki.xndroid.adapter.loadmore;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yuki.xndroid.adapter.SlimAdapter;

import static android.support.v7.widget.ListViewCompat.NO_POSITION;

public abstract class SlimMoreLoader extends RecyclerView.OnScrollListener {

    private SlimAdapter slimAdapter;
    private boolean isLoading;
    
    
    public void setSlimAdapter(SlimAdapter slimAdapter) {
        this.slimAdapter = slimAdapter;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if (NO_POSITION == last) {
                    break;
                }
                if (slimAdapter.getItem(last) == this && !isLoading) {
                    slimAdapter.seton
                }
                break;
            default:
                break;
        }
    }



}
