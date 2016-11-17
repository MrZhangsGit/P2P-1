package com.atguigu.p2pinvest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.bean.ProInfo;
import com.atguigu.p2pinvest.ui.RoundProgress;
import com.atguigu.p2pinvest.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 颜银 on 2016/11/15.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class ProductListAdapter extends BaseAdapter {

    private List<ProInfo> list;

    public ProductListAdapter(List<ProInfo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //分类型listView
        int itemViewType = getItemViewType(position);
        if (itemViewType == 0) {
            TextView textView = new TextView(parent.getContext());
            textView.setText("与子同有，动辄覆舟");
            textView.setTextSize(UIUtils.dp2px(10));
            textView.setTextColor(UIUtils.getColor(R.color.home_back_selected));
            return textView;
        }

        //没有的话。position==3的那条信息会消失
        if (position > 3) {
            position--;
        }

        //获得数据
        ProInfo proInfo = list.get(position);
        //装配数据
        holder.pMinnum.setText(proInfo.getMemberNum());
        holder.pMinzouzi.setText(proInfo.getMinTouMoney());
        holder.pMoney.setText(proInfo.getMoney());
        holder.pName.setText(proInfo.getName());
        holder.pProgresss.setProgress(Integer.parseInt(proInfo.getProgress()));
        holder.pSuodingdays.setText(proInfo.getSuodingDays());
        holder.pYearlv.setText(proInfo.getYearRate());
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

    @Override
    public int getItemViewType(int position) {
        if (position == 3) {
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
