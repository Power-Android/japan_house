package com.example.administrator.japanhouse.fragment.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.japanhouse.R;
import com.example.administrator.japanhouse.base.BaseActivity;
import com.example.administrator.japanhouse.fragment.home.NewHouseActivity;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;

public class ShangpuDetailsActivity extends BaseActivity {

    @BindView(R.id.vp_vidio)
    ViewPager vpVidio;
    @BindView(R.id.tv_to_num)
    TextView tvToNum;
    @BindView(R.id.tv_all_num)
    TextView tvAllNum;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.img_start)
    ImageView imgStart;
    @BindView(R.id.love_Recycler)
    RecyclerView loveRecycler;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.tv_suiyi)
    TextView tvSuiyi;
    private LoveAdapter loveAdapter;
    private List<String> mList = new ArrayList();
    private List<Fragment> mBaseFragmentList = new ArrayList<>();
    private FragmentManager fm;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //拉伸图片覆盖标题栏
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar(false);
        setContentView(R.layout.activity_shangpu_details);
        ButterKnife.bind(this);
        //banner
        initViewPager();
        //户型图
        initData();
        //猜你喜欢
        initLoveRecycler();
    }

    private void initViewPager() {
        if (mBaseFragmentList.size() <= 0) {
//            mBaseFragmentList.add(new VidioFragment());
            mBaseFragmentList.add(new BannerFragment());
            mBaseFragmentList.add(new BannerFragment());
            mBaseFragmentList.add(new BannerFragment());
        }
        tvAllNum.setText(mBaseFragmentList.size() + "");
        fm = getSupportFragmentManager();
        myAdapter = new MyAdapter(fm);
        vpVidio.setAdapter(myAdapter);
        vpVidio.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvToNum.setText((position + 1) + "");
                if (position == 1) {
                    JZVideoPlayer.releaseAllVideos();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    private void initData() {
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");


    }

    private void initLoveRecycler() {
        if (loveAdapter == null) {
            loveAdapter = new LoveAdapter(R.layout.item_zuijin, mList);
        }
        loveRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        loveRecycler.setNestedScrollingEnabled(false);
        loveRecycler.setAdapter(loveAdapter);
        loveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ShangpuDetailsActivity.this, NewHouseActivity.class);
                startActivity(intent);
            }
        });
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

    @OnClick({R.id.img_share, R.id.img_start, R.id.back_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_start:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back_img:
                finish();
                break;
        }
    }


    class LoveAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LoveAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
        }
    }
}