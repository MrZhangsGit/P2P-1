package com.atguigu.p2pinvest.adapter;

import android.view.View;
import android.widget.TextView;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.bean.ProInfo;
import com.atguigu.p2pinvest.ui.RoundProgress;

import java.util.List;

/**
 * Created by 颜银 on 2016/11/15.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class ProductListAdapter2 extends MyBaseAdapter2<ProInfo> {

    public ProductListAdapter2(List<ProInfo> list) {
        super(list);
    }

    @Override
    protected void setData(View convertView, ProInfo proInfo) {
        ((TextView)convertView.findViewById(R.id.p_name)).setText(proInfo.getName());
        ((TextView)convertView.findViewById(R.id.p_minnum)).setText(proInfo.getMemberNum());
        ((TextView)convertView.findViewById(R.id.p_minzouzi)).setText(proInfo.getMinTouMoney());
        ((TextView)convertView.findViewById(R.id.p_money)).setText(proInfo.getMoney());
        ((RoundProgress)convertView.findViewById(R.id.p_progresss)).setProgress(Integer.parseInt(proInfo.getProgress()));
        ((TextView)convertView.findViewById(R.id.p_suodingdays)).setText(proInfo.getSuodingDays());
        ((TextView)convertView.findViewById(R.id.p_yearlv)).setText(proInfo.getYearRate());
    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_product_list;
    }

}
