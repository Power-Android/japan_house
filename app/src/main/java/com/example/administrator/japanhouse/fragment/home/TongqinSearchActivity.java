package com.example.administrator.japanhouse.fragment.home;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.japanhouse.R;
import com.example.administrator.japanhouse.base.BaseActivity;
import com.example.administrator.japanhouse.utils.TUtils;
import com.example.administrator.japanhouse.view.CommonPopupWindow;
import com.example.administrator.japanhouse.view.FluidLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TongqinSearchActivity extends BaseActivity {

    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.cancle_tv)
    TextView cancleTv;
    @BindView(R.id.history_recycler)
    RecyclerView historyRecycler;
    @BindView(R.id.view_rl)
    RelativeLayout view_rl;
    @BindView(R.id.history_clear)
    ImageView historyClear;
    private CommonPopupWindow popupWindow;
    private List<String> historyList;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongqin_search);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        historyList = new ArrayList<>();
        historyList.add("东京");
        historyList.add("澳大利亚");
        historyList.add("中国");
        historyList.add("美国");
        historyList.add("缅甸");
        historyList.add("阿富汗");
        historyRecycler.setNestedScrollingEnabled(false);
        historyRecycler.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter(R.layout.item_history_search, historyList);
        historyRecycler.setAdapter(historyAdapter);
    }

    @OnClick({R.id.cancle_tv, R.id.history_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancle_tv:
                finish();
                break;
            case R.id.history_clear:
                historyList.clear();
                historyAdapter.notifyDataSetChanged();
                break;
        }
    }

    private class HistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public HistoryAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_name_tv, item);
        }
    }
}
