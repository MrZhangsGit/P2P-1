package com.atguigu.p2pinvest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.bean.ProInfo;
import com.atguigu.p2pinvest.ui.RoundProgress;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 颜银 on 2016/11/15.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class ProductListAdapter1 extends MyBaseAdapter1<ProInfo> {

    public ProductListAdapter1(List<ProInfo> list) {
        super(list);
    }

    @Override
    protected View myGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //获得数据
        ProInfo proInfo = list.get(position);
        //装配数据
        viewHolder.pMinnum.setText(proInfo.getMemberNum());
        viewHolder.pMinzouzi.setText(proInfo.getMinTouMoney());
        viewHolder.pMoney.setText(proInfo.getMoney());
        viewHolder.pName.setText(proInfo.getName());
        viewHolder.pProgresss.setProgress(Integer.parseInt(proInfo.getProgress()));
        viewHolder.pSuodingdays.setText(proInfo.getSuodingDays());
        viewHolder.pYearlv.setText(proInfo.getYearRate());
        //返回数据
        return convertView;
    }

    static class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
