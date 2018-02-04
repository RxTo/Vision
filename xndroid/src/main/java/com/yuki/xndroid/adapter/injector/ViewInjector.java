package com.yuki.xndroid.adapter.injector;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuki.xndroid.adapter.SlimViewHolder;


public class ViewInjector implements IViewInjector<ViewInjector> {
	
	private SlimViewHolder viewHolder;
	
	public ViewInjector(SlimViewHolder viewHolder) {
		this.viewHolder = viewHolder;
	}
	
	@Override
	public final <T extends View> T findViewById(int id) {
		return (T) viewHolder.id(id);
	}
	
	@Override
	public ViewInjector tag(int id, Object object) {
		findViewById(id).setTag(object);
		return this;
	}
	
	@Override
	public ViewInjector text(int id, int res) {
		TextView view = findViewById(id);
		view.setText(res);
		return this;
	}
	
	@Override
	public ViewInjector text(int id, CharSequence charSequence) {
		TextView view = findViewById(id);
		view.setText(charSequence);
		return this;
	}
	
	@Override
	public ViewInjector typeface(int id, Typeface typeface, int style) {
		TextView view = findViewById(id);
		view.setTypeface(typeface, style);
		return this;
	}
	
	@Override
	public ViewInjector typeface(int id, Typeface typeface) {
		TextView view = findViewById(id);
		view.setTypeface(typeface);
		return this;
	}
	
	@Override
	public ViewInjector textColor(int id, int color) {
		TextView view = findViewById(id);
		view.setTextColor(color);
		return this;
	}
	
	@Override
	public ViewInjector textSize(int id, int sp) {
		TextView view = findViewById(id);
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
		return this;
	}
	
	@Override
	public ViewInjector alpha(int id, float alpha) {
		View view = findViewById(id);
		view.setAlpha(alpha);
		return this;
	}
	
	@Override
	public ViewInjector image(int id, int res) {
		ImageView view = findViewById(id);
		view.setImageResource(res);
		return this;
	}
	
	@Override
	public ViewInjector image(int id, Drawable drawable) {
		ImageView view = findViewById(id);
		view.setImageDrawable(drawable);
		return this;
	}
	
	@Override
	public ViewInjector background(int id, int res) {
		View view = findViewById(id);
		view.setBackgroundResource(res);
		return this;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public ViewInjector background(int id, Drawable drawable) {
		View view = findViewById(id);
		view.setBackground(drawable);
		return this;
	}
	
	@Override
	public ViewInjector visible(int id) {
		findViewById(id).setVisibility(View.VISIBLE);
		return this;
	}
	
	
	@Override
	public ViewInjector invisible(int id) {
		findViewById(id).setVisibility(View.INVISIBLE);
		return this;
	}
	
	@Override
	public ViewInjector gone(int id) {
		findViewById(id).setVisibility(View.GONE);
		return this;
	}
	
	@Override
	public ViewInjector visibility(int id, int visibility) {
		findViewById(id).setVisibility(visibility);
		return this;
	}
	
	
	@Override
	public ViewInjector clicked(int id, View.OnClickListener listener) {
		findViewById(id).setOnClickListener(listener);
		return this;
	}
	
	@Override
	public ViewInjector longClicked(int id, View.OnLongClickListener listener) {
		findViewById(id).setOnLongClickListener(listener);
		return this;
	}
	
	@Override
	public ViewInjector enable(int id, boolean enable) {
		findViewById(id).setEnabled(enable);
		return this;
	}
	
	@Override
	public ViewInjector enable(int id) {
		findViewById(id).setEnabled(true);
		return this;
	}
	
	@Override
	public ViewInjector disable(int id) {
		findViewById(id).setEnabled(false);
		return this;
	}
	
	@Override
	public ViewInjector checked(int id, boolean checked) {
		Checkable view = findViewById(id);
		view.setChecked(checked);
		return this;
	}
	
	@Override
	public ViewInjector selected(int id, boolean selected) {
		findViewById(id).setSelected(selected);
		return this;
	}
	
	@Override
	public ViewInjector pressed(int id, boolean pressed) {
		findViewById(id).setPressed(pressed);
		return this;
	}
	
	@Override
	public ViewInjector adapter(int id, RecyclerView.Adapter adapter) {
		RecyclerView view = findViewById(id);
		view.setAdapter(adapter);
		return this;
	}
	
	@Override
	public ViewInjector adapter(int id, Adapter adapter) {
		AdapterView view = findViewById(id);
		view.setAdapter(adapter);
		return this;
	}
	
	@Override
	public ViewInjector layoutManager(int id, RecyclerView.LayoutManager layoutManager) {
		RecyclerView view = findViewById(id);
		view.setLayoutManager(layoutManager);
		return this;
	}
	
	@Override
	public ViewInjector addView(int id, View... views) {
		ViewGroup viewGroup = findViewById(id);
		for (View view : views) {
			viewGroup.addView(view);
		}
		return this;
	}
	
	@Override
	public ViewInjector addView(int id, View view, ViewGroup.LayoutParams params) {
		ViewGroup viewGroup = findViewById(id);
		viewGroup.addView(view, params);
		return this;
	}
	
	@Override
	public ViewInjector removeAllViews(int id) {
		ViewGroup viewGroup = findViewById(id);
		viewGroup.removeAllViews();
		return this;
	}
	
	@Override
	public ViewInjector removeView(int id, View view) {
		ViewGroup viewGroup = findViewById(id);
		viewGroup.removeView(view);
		return this;
	}
}
