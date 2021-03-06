package com.example.administrator.japanhouse.fragment.home;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.japanhouse.MainActivity;
import com.example.administrator.japanhouse.R;
import com.example.administrator.japanhouse.base.BaseActivity;
import com.example.administrator.japanhouse.bean.OneCheckBean;
import com.example.administrator.japanhouse.fragment.comment.ZuHousedetailsActivity;
import com.example.administrator.japanhouse.fragment.mine.LiShiJiLuActivity;
import com.example.administrator.japanhouse.utils.TUtils;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZufangListActivity extends BaseActivity implements MyItemClickListener{

    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.img_dingwei)
    ImageView imgDingwei;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @BindView(R.id.search_tv)
    TextView searchTv;
    private String headers[] = {"售价", "楼层", "车站距离", "更多"};
    private List<View> popupViews = new ArrayList<>();
    private RecyclerView mrecycler;
    private List<String> mList = new ArrayList();
    private LiebiaoAdapter liebiaoAdapter;
    private List<OneCheckBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zufang_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        /**
         * 第一个界面
         * */
        list = new ArrayList<>();
        list.add(new OneCheckBean(false, "不限"));
        list.add(new OneCheckBean(false, "3-10万"));
        list.add(new OneCheckBean(false, "6-15万"));
        list.add(new OneCheckBean(false, "10万以上"));
        FirstView firstView = new FirstView(ZufangListActivity.this);
        popupViews.add(firstView.firstView());
        firstView.insertData(list, dropDownMenu);
        firstView.setListener(this);

        /**
         * 第二个界面
         * */
        List<OneCheckBean> list1 = new ArrayList<>();
        list1.add(new OneCheckBean(false, "不限"));
        list1.add(new OneCheckBean(false, "地下室"));
        list1.add(new OneCheckBean(false, "一层"));
        list1.add(new OneCheckBean(false, "二层"));
        list1.add(new OneCheckBean(false, "三层"));
        list1.add(new OneCheckBean(false, "四层"));
        SecView secView = new SecView(ZufangListActivity.this);
        popupViews.add(secView.secView());
        secView.setListener(this);
        secView.insertData(list1, dropDownMenu);

        /**
         * 第三个界面
         * */
        List<OneCheckBean> list2 = new ArrayList<>();
        list2.add(new OneCheckBean(false, "不限"));
        list2.add(new OneCheckBean(false, "100米以内"));
        list2.add(new OneCheckBean(false, "200米以内"));
        list2.add(new OneCheckBean(false, "500米以内"));
        list2.add(new OneCheckBean(false, "1000米以内"));
        list2.add(new OneCheckBean(false, "2000米以内"));
        SecView threeView = new SecView(ZufangListActivity.this);
        popupViews.add(threeView.secView());
        threeView.insertData(list2, dropDownMenu);
        threeView.setListener(this);
        /**
         * 第四个界面
         * */
        List<OneCheckBean> list3 = new ArrayList<>();
        list3.add(new OneCheckBean(false, "格局"));
        list3.add(new OneCheckBean(false, "洋室"));
        list3.add(new OneCheckBean(false, "和室"));
        list3.add(new OneCheckBean(false, "朝向"));
        list3.add(new OneCheckBean(false, "面积（平米）"));
        MoreView fourView = new MoreView(ZufangListActivity.this);
        popupViews.add(fourView.secView());
        fourView.insertData(list3, dropDownMenu);
        fourView.setListener(this);

        /**
         * Dropdownmenu下面的主体部分
         * */
        View fifthView = LayoutInflater.from(ZufangListActivity.this).inflate(R.layout.activity_zufang_view, null);
        mrecycler = (RecyclerView) fifthView.findViewById(R.id.mrecycler);
        TextView shipinTv = (TextView) fifthView.findViewById(R.id.shipin_tv);
        TextView tongqinTv = (TextView) fifthView.findViewById(R.id.tongqin_tv);
        TextView jiluTv = (TextView) fifthView.findViewById(R.id.jilu_tv);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, fifthView);
        shipinTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TUtils.showShort(mContext,"视频房源");
            }
        });
        tongqinTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,TongqinActivity.class));
            }
        });
        jiluTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, LiShiJiLuActivity.class));
            }
        });
        initData();
    }

    private void initData() {
        final String type = getIntent().getStringExtra("type");

        if (mList.size() <= 0) {
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
        }
        liebiaoAdapter = new LiebiaoAdapter(R.layout.item_home_xinfang, mList);
        mrecycler.setNestedScrollingEnabled(false);
        mrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mrecycler.setAdapter(liebiaoAdapter);
        liebiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, ZuHousedetailsActivity.class));
                /*switch (type){
                    case "0":
                        startActivity(new Intent(mContext, ShangpuDetailsActivity.class));
                        break;
                    case "1":
                        startActivity(new Intent(mContext, ShangpuDetailsActivity.class));
                        break;
                    case "2":
                        startActivity(new Intent(mContext, XiezilouDetailsActivity.class));
                        break;
                    case "3":
                        startActivity(new Intent(mContext, ShangpuDetailsActivity.class));
                        break;
                    case "4":
                        startActivity(new Intent(mContext, GaoerfuDetailsActivity.class));
                        break;
                    case "5":
                        startActivity(new Intent(mContext, JiudianDetailsActivity.class));
                        break;
                }*/
            }
        });

    }

    class LiebiaoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LiebiaoAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    @OnClick({R.id.back_img, R.id.img_dingwei, R.id.img_message, R.id.search_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            //地图
            case R.id.img_dingwei:
                startActivity(new Intent(mContext,HomeMapActivity.class));
                break;
            //消息
            case R.id.img_message:
                startActivity(new Intent(mContext, MainActivity.class));
                break;
            case R.id.search_tv:
                startActivity(new Intent(mContext,HomeSearchActivity.class));
        }
    }

    @Override
    public void onItemClick(View view, int postion, String string) {

    }
}
