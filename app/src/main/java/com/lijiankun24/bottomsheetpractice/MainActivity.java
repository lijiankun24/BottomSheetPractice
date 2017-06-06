package com.lijiankun24.bottomsheetpractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lijiankun24.bottomsheetpractice.about.AboutActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        CustomBottomSheetDialogFragment.OnSendCommentClickListener, Toolbar.OnMenuItemClickListener {

    private static final String TAG_DIALOG_FRAGMENT = "CustomBottomSheetDialogFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bottom_sheet:
                toggleBottomSheet();
                break;
            case R.id.btn_bottom_sheet_dialog:
                showBottomSheetDialog();
                break;
            case R.id.btn_bottom_sheet_dialog_fragment:
                showBottomSheetDialogFragment();
                break;
        }
    }

    @Override
    public void onClick(String comment) {
        Toast.makeText(MainActivity.this, comment, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void toggleBottomSheet() {
        BottomSheetBehavior behavior = BottomSheetBehavior.from(findViewById(R.id.ll_sheet_root));

        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
        EditText editText;
        View view = LayoutInflater
                .from(MainActivity.this)
                .inflate(R.layout.fragment_custom_bottom_sheet, null);
        editText = (EditText) view.findViewById(R.id.et_input_msg);
        dialog.setContentView(view);
        dialog.show();

        View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(Utils.getHeight(MainActivity.this));

        ((View) view.getParent()).setBackgroundColor(ContextCompat
                .getColor(MainActivity.this, android.R.color.transparent));

        Utils.toggleSoftInput(MainActivity.this, editText);
    }

    private void showBottomSheetDialogFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomBottomSheetDialogFragment mSheetDialogFragment =
                (CustomBottomSheetDialogFragment) fragmentManager
                        .findFragmentByTag(TAG_DIALOG_FRAGMENT);

        if (mSheetDialogFragment == null) {
            mSheetDialogFragment = new CustomBottomSheetDialogFragment();
        }
        mSheetDialogFragment.show(fragmentManager, TAG_DIALOG_FRAGMENT);
    }

    private void initView() {
        findViewById(R.id.btn_bottom_sheet).setOnClickListener(this);
        findViewById(R.id.btn_bottom_sheet_dialog).setOnClickListener(this);
        findViewById(R.id.btn_bottom_sheet_dialog_fragment).setOnClickListener(this);
        initToolbar();
        initBottomSheet();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void initBottomSheet() {
        int[] mIcons = {R.drawable.ic_share_wechat,
                R.drawable.ic_share_wechat_timeline,
                R.drawable.ic_share_sina_weibo,
                R.drawable.ic_share_tencent_qq,
                R.drawable.ic_share_twitter,
                R.drawable.ic_share_pocket,
                R.drawable.ic_share_evernote,
                R.drawable.ic_share_instapaper};
        String[] mTitles = {"微信好友",
                "朋友圈",
                "新浪微博",
                "QQ",
                "Twitter",
                "Pocket",
                "印象笔记",
                "Instapaper"
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_sheet);
        CustomSheetAdapter sheetAdapter = new CustomSheetAdapter(mTitles, mIcons, MainActivity.this);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        recyclerView.setAdapter(sheetAdapter);
    }

    class CustomSheetAdapter extends RecyclerView.Adapter<CustomSheetAdapter.SheetViewHolder> {

        private String[] mTitles = null;

        private int[] mIcons = null;

        private Context mContext = null;

        private LayoutInflater mInflater = null;

        CustomSheetAdapter(String[] titles, int[] icons, Context context) {
            mTitles = titles;
            mIcons = icons;
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public SheetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SheetViewHolder(mInflater.inflate(R.layout.bottom_sheet_item, parent, false));
        }

        @Override
        public void onBindViewHolder(SheetViewHolder holder, int position) {
            holder.getTVTitle().setText(mTitles[position]);
            holder.getIVIcon().setImageResource(mIcons[position]);
        }

        @Override
        public int getItemCount() {
            return mTitles == null ? 0 : mTitles.length;
        }

        class SheetViewHolder extends RecyclerView.ViewHolder {
            private TextView mTVTitle = null;

            private ImageView mIVIcon = null;


            public SheetViewHolder(View itemView) {
                super(itemView);
                mTVTitle = (TextView) itemView.findViewById(R.id.tv_sheet_item);
                mIVIcon = (ImageView) itemView.findViewById(R.id.iv_sheet_item);
            }

            public TextView getTVTitle() {
                return mTVTitle;
            }

            public ImageView getIVIcon() {
                return mIVIcon;
            }
        }
    }
}
