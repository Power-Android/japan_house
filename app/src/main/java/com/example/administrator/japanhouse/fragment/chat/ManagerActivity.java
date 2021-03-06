package com.example.administrator.japanhouse.fragment.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.japanhouse.R;
import com.example.administrator.japanhouse.base.BaseActivity;
import com.example.administrator.japanhouse.fragment.comment.NewHousedetailsActivity;
import com.example.administrator.japanhouse.view.BaseDialog;
import com.example.administrator.japanhouse.view.NoCacheViewPager;
import com.example.administrator.japanhouse.view.RatingBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerActivity extends BaseActivity {

    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.ratingBarView)
    RatingBarView ratingBarView;
    @BindView(R.id.Mrecycler)
    RecyclerView mrecycler;
    private LiebiaoAdapter mLiebiaoAdapter;
    private List<String> mList=new ArrayList();
    @BindView(R.id.all_shop)
    RadioButton allShop;
    @BindView(R.id.rb_pifu)
    RadioButton rbPifu;
    @BindView(R.id.rg_look)
    RadioGroup rgLook;
    @BindView(R.id.view_shoufang)
    View viewShoufang;
    @BindView(R.id.view_zufang)
    View viewZufang;
    @BindView(R.id.vp_look)
    NoCacheViewPager vpLook;
    private List<Fragment> mBaseFragmentList = new ArrayList<>();
    private FragmentManager fm;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        ButterKnife.bind(this);
        initRatingBar();
        //第一种样式
        initRecycler();
        //第二种样式
        initData();
        initListener();
    }
    private void initData() {
        if (mBaseFragmentList.size() <= 0) {
            mBaseFragmentList.add(new ShoufangFragment());
            mBaseFragmentList.add(new ZufangFragment());
        }
    }
    private void initListener() {
        vpLook.setOnPageChangeListener(new NoCacheViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rgLook.getChildAt(position)).setChecked(true);
                if (position==0){
                    viewZufang.setVisibility(View.INVISIBLE);
                    viewShoufang.setVisibility(View.VISIBLE);
                }else if (position==1){
                    viewZufang.setVisibility(View.VISIBLE);
                    viewShoufang.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rgLook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.all_shop:
                        vpLook.setCurrentItem(0);
//                        vp_look.removeAllViews();
                        break;
                    case R.id.rb_pifu:
                        vpLook.setCurrentItem(1);
                        break;
                    default:
                        vpLook.setCurrentItem(0);
                        break;
                }
            }
        });


        fm = getSupportFragmentManager();
        myAdapter = new MyAdapter(fm);
        vpLook.setAdapter(myAdapter);
    }



    class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mBaseFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mBaseFragmentList.size();
        }
    }

    private void initRecycler() {
        if (mList.size()<=0){
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
        }
        if (mLiebiaoAdapter == null) {
            mLiebiaoAdapter = new LiebiaoAdapter(R.layout.item_zuijin,mList);
        }
        mrecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mrecycler.setNestedScrollingEnabled(false);
        mrecycler.setAdapter(mLiebiaoAdapter);
        mLiebiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(mContext,NewHousedetailsActivity.class);
                startActivity(intent);
//                Toast.makeText(mContext, "点击了第" + position+"条", Toast.LENGTH_SHORT).show();
            }
        });
    }
    class LiebiaoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LiebiaoAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId,data);
        }

        @Override
        protected void convert(BaseViewHolder helper,String item) {
        }
    }

    private void initRatingBar() {
        ratingBarView = (RatingBarView) findViewById(R.id.ratingBarView);
        ratingBarView.setRatingCount(5);//设置RatingBarView总数
        ratingBarView.setSelectedCount(2);//设置RatingBarView选中数
        ratingBarView.setSelectedIconResId(R.drawable.start_check);//设置RatingBarView选中的图片id
        ratingBarView.setNormalIconResId(R.drawable.start_nocheck);//设置RatingBarView正常图片id
        ratingBarView.setClickable(false);//设置RatingBarView是否可点击
        ratingBarView.setChildPadding(2);//设置RatingBarView的子view的padding
        ratingBarView.setChildMargin(2);//设置RatingBarView的子view左右之间的margin
        ratingBarView.setChildDimension(22);//设置RatingBarView的子view的宽高尺寸
        ratingBarView.setOnRatingBarClickListener(new RatingBarView.RatingBarViewClickListener() {
            @Override
            public void onRatingBarClick(LinearLayout parent, View childView, int position) {
                Log.e("tag", String.valueOf(childView instanceof ImageView) + "," + position);
            }
        });
    }

    @OnClick({R.id.back_img, R.id.bg_layout,R.id.img_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.img_share:
                showDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
                break;

        }
    }
    private void showDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        //设置触摸dialog外围是否关闭
        //设置监听事件
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_share)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.show();
        LinearLayout weiliao_layout = (LinearLayout) dialog.findViewById(R.id.weiliao_layout);
        LinearLayout weixin_layout = (LinearLayout) dialog.findViewById(R.id.weixin_layout);
        LinearLayout pengyouquan_layout = (LinearLayout) dialog.findViewById(R.id.pengyouquan_layout);
        LinearLayout weibo_layout = (LinearLayout) dialog.findViewById(R.id.weibo_layout);
        TextView tv_dismiss = (TextView) dialog.findViewById(R.id.tv_dismiss);
        //微聊分享
        weiliao_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //微信分享
        weixin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //朋友圈分享
        pengyouquan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //微博分享
        weibo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //取消
        tv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    @OnClick(R.id.Mrecycler)
    public void onClick() {
    }
}
