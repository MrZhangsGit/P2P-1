package com.atguigu.p2pinvest.adapter;

import android.view.View;
import android.widget.TextView;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.bean.ProInfo;
import com.atguigu.p2pinvest.ui.RoundProgress;
import com.atguigu.p2pinvest.utils.UIUtils;

import butterknife.Bind;

/**
 * Created by 颜银 on 2016/11/15.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class MyHolder extends BaseHolder<ProInfo> {
    @Bind(R.id.p_name)
    TextView pName;
    @Bind(R.id.p_money)
    TextView pMoney;
    @Bind(R.id.p_yearlv)
    TextView pYearlv;
    @Bind(R.id.p_suodingdays)
    TextView pSuodingdays;
    @Bind(R.id.p_minzouzi)
    TextView pMinzouzi;
    @Bind(R.id.p_minnum)
    TextView pMinnum;
    @Bind(R.id.p_progresss)
    RoundProgress pProgresss;

    @Override
    protected void refreshData() {
        ProInfo data = getData();
        pName.setText(data.getName());
        pMinnum.setText(data.getMemberNum());
        pMoney.setText(data.getMoney());
        pYearlv.setText(data.getYearRate());
        pSuodingdays.setText(data.getSuodingDays());
        pMinzouzi.setText(data.getMinTouMoney());
        pProgresss.setProgress(Integer.parseInt(data.getProgress()));
    }

    @Override
    public View initView() {
        return UIUtils.getView(R.layout.item_product_list);
    }
}
