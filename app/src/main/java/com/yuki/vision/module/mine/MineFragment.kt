package com.yuki.vision.module.mine

import android.os.Bundle
import com.yuki.vision.R
import com.yuki.xndroid.base.XFragment
import com.yuki.xndroid.base.mvp.IPresenter


class MineFragment : XFragment<IPresenter>() {
	
	public override fun initLayoutId() : Int {
		return R.layout.fragment_mine
	}
	
	public override fun initView(savedInstanceState : Bundle?) {
	
	}
	
	companion object {
		
		fun newInstance() : MineFragment {
			val fragment = MineFragment()
			val args = Bundle()
			fragment.arguments = args
			return fragment
		}
	}
	
	
}
