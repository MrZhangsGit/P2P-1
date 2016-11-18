package com.atguigu.p2pinvest.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.common.BaseFragment;
import com.atguigu.p2pinvest.utils.UIUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 颜银 on 2016/11/11.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class InvestFrangment extends BaseFragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;

//    @Bind(R.id.tab_indicator)
//    TabPageIndicator tabIndicator;

    @Bind(R.id.viewpager_invest)
    ViewPager viewpagerInvest;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    private List<Fragment> fragmentList;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        initFragment();

        //设置适配器
        InvestAdapter adapter = new InvestAdapter(getFragmentManager());
        viewpagerInvest.setAdapter(adapter);
        //关联TabPagerIndicator 和 ViewPager  在设置适配器后设置
        tabLayout.setupWithViewPager(viewpagerInvest);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//次样式只有一般在左边
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    /**
     * 初始化fragment的集合
     */
    private void initFragment() {
        fragmentList = new ArrayList<>();

        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommondFragment productRecommondFragment = new ProductRecommondFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();

        fragmentList.add(productListFragment);
        fragmentList.add(productRecommondFragment);
        fragmentList.add(productHotFragment);
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.INVISIBLE);
        ivTopSetting.setVisibility(View.INVISIBLE);
        tvTopName.setText("投资");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invest;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * FragmentStatePagerAdapter  在Fragment比较多的情况下采用，内部封装了系统销毁回收长时间不用的fragment界面，防止内存溢出
     * FragmentPagerAdapter   如果viewPager中加载显示的Fragment较少情况。系统不会做回收操作
     */
    class InvestAdapter extends FragmentPagerAdapter {

        //提供TabPagerIndicator显示的文本
        @Override
        public CharSequence getPageTitle(int position) {
            //方式一
//            if(position == 0){
//                return "全部理财";
//            }else if(position == 1){
//                return "推荐理财";
//            }else if(position == 2){
//                return "热门理财";
//            }
            //方式二
            return UIUtils.getStringArr(R.array.invest_tab)[position];
        }

        public InvestAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}
