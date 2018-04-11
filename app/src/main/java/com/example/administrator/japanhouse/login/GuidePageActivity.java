package com.example.administrator.japanhouse.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.japanhouse.MainActivity;
import com.example.administrator.japanhouse.R;
import com.example.administrator.japanhouse.base.BaseActivity;
import com.example.administrator.japanhouse.utils.SharedPreferencesUtils;


/**
 * Created by lxk on 2017/6/30.
 */

public class GuidePageActivity extends BaseActivity {
    private ViewPager vp;
    private GuidePagerAdapter mGuidePagerAdapter;
    private int[] imgurls = {R.drawable.yindaoye1, R.drawable.yindaoye2, R.drawable.yindaoye3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        vp = (ViewPager) findViewById(R.id.vp);
        if (mGuidePagerAdapter == null) {
            mGuidePagerAdapter = new GuidePagerAdapter();
        }
        vp.setAdapter(mGuidePagerAdapter);
    }


    private class GuidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(GuidePageActivity.this, R.layout.item_guide, null);
            TextView tv_jinru = (TextView) view.findViewById(R.id.tv_jinru);
            if (position == 2) {
                tv_jinru.setVisibility(View.VISIBLE);
            } else {
                tv_jinru.setVisibility(View.GONE);
            }
            tv_jinru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferencesUtils.getInstace(GuidePageActivity.this).setBooleanPreference( "guide", true);
                    Intent intent = new Intent(GuidePageActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            ImageView iv_guide = (ImageView) view.findViewById(R.id.iv_guide);
            if (imgurls!=null) {
                Glide.with(GuidePageActivity.this).load(imgurls[position]).into(iv_guide);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}