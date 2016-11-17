package com.atguigu.p2pinvest.fragment;


import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.adapter.ProductListAdapter;
import com.atguigu.p2pinvest.bean.BaseFragment;
import com.atguigu.p2pinvest.bean.ProInfo;
import com.atguigu.p2pinvest.common.AppNetConfig;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.Bind;


public class ProductListFragment extends BaseFragment {


    @Bind(R.id.lv_product_list)
    ListView lvProductList;
    private List<ProInfo> proInfos;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    protected void initData(String content) {//content即为响应成功情况下，返回的product.json数据
        //解析json数据
        JSONObject jsonObject = JSON.parseObject(content);
        boolean isSuccess = jsonObject.getBoolean("success");
        if(isSuccess){//加载成功
            String data = jsonObject.getString("data");
            //解析得到集合数据
            proInfos = JSON.parseArray(data, ProInfo.class);
        }

        //方法一
        ProductListAdapter productListAdapter = new ProductListAdapter(proInfos);
        lvProductList.setAdapter(productListAdapter);
        //方法二
//        ProductListAdapter1 productListAdapter1 = new ProductListAdapter1(proInfos);
//        lvProductList.setAdapter(productListAdapter1);
        //方法三
//        ProductListAdapter2 productListAdapter2 = new ProductListAdapter2(proInfos);
//        lvProductList.setAdapter(productListAdapter2);
        //方法四
//        ProductListAdapter3 productListAdapter3 = new ProductListAdapter3(proInfos);
//        lvProductList.setAdapter(productListAdapter3);
    }
    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_list;
    }

}
