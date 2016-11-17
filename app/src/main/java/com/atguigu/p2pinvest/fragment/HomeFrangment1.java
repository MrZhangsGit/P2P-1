package com.atguigu.p2pinvest.fragment;

/**
 * Created by 颜银 on 2016/11/11.
 * QQ:443098360
 * 微信：y443098360
 * 作用：使用ViewPager+ViewpagerIndeictor加载顶部轮播图
 */
//public class HomeFrangment1 extends Fragment {
//
//    @Bind(R.id.iv_top_back)
//    ImageView ivTopBack;
//    @Bind(R.id.tv_top_name)
//    TextView tvTopName;
//    @Bind(R.id.iv_top_setting)
//    ImageView ivTopSetting;
//    @Bind(R.id.vp_home)
//    ViewPager vpHome;
//    @Bind(R.id.cp_home)
//    CirclePageIndicator cpHome;
//    @Bind(R.id.tv_home_interest)
//    TextView tvHomeInterest;
//    @Bind(R.id._btn_home_join)
//    Button BtnHomeJoin;
//    private Index index;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = UIUtils.getView(R.layout.fragment_home);
//        ButterKnife.bind(this, view);
//        initTop();
//        //初始化数据
//        initTitle();
//        return view;
//    }
//
//    //初始化数据
//    private void initTitle() {
//        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        asyncHttpClient.post(AppNetConfig.INDEX, new AsyncHttpResponseHandler() {
//            //联网下载成功
//            @Override
//            public void onSuccess(String content) {
//                Log.e("TAG", "联网成功");
//                //1.使用fastJson解析得到的json数据,并封装数据到java对象中
//                JSONObject jsonObject = JSON.parseObject(content);
//
//                //获得下面圆环的json信息
//                String proInfo = jsonObject.getString("proInfo");
//                ProInfo mProInfo = JSON.parseObject(proInfo, ProInfo.class);
//
//                //获得轮播图的信息
//                String imags = jsonObject.getString("imageArr");
//                List<Imags> mImags = JSON.parseArray(imags, Imags.class);
//
//                index = new Index();
//                index.proInfo = mProInfo;
//                index.imags = mImags;
//
//
//                //设置轮播图显示
//                ViewPagerAdapter adapter = new ViewPagerAdapter();
//                vpHome.setAdapter(adapter);
//                cpHome.setViewPager(vpHome);
//                //3.根据得到的产品的数据，更新界面中的产品展示
//                String yearRate = index.proInfo.getYearRate();
//                tvHomeInterest.setText(yearRate + "%");
//            }
//
//            //联网下载失败
//            @Override
//            public void onFailure(Throwable error, String content) {
//                Log.e("TAG", "联网失败");
//            }
//
//        });
//    }
//
//    private void initTop() {
//        //隐藏左右的ImageView
//        ivTopBack.setVisibility(View.INVISIBLE);
//        ivTopSetting.setVisibility(View.INVISIBLE);
//        tvTopName.setText("首页");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//
//    class ViewPagerAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return index.imags == null ? 0 : index.imags.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            ImageView imageView = new ImageView(getActivity());
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            //加载具体图片
//            String imaurl = index.imags.get(position).getIMAURL();
//
//            Picasso.with(getActivity()).load(imaurl).into(imageView);
//            //添加到当前的container中
//            container.addView(imageView);
//            return imageView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
////            super.destroyItem(container, position, object);
//            container.removeView((View) object);
//        }
//    }
//}
