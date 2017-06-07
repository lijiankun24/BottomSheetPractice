package com.lijiankun24.bottomsheetpractice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
        CustomETBottomSheetDialogFragment.OnSendCommentClickListener, Toolbar.OnMenuItemClickListener {

    private static final String TAG_BOTTOM_SHEET_DIALOG_FRAGMENT = "CustomETBottomSheetDialogFragment";

    private static final String TAG_SHARE_BS_DIALOG_FRAGMENT = "CustomShareBottomSheetDialogFragment";

    private static final String TAG_DIALOG_FRAGMENT = "CustomDialogFragment";

    private BottomSheetDialog mDialog = null;

    private EditText mEditText = null;

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
            case R.id.btn_dialog_fragment:
                showDialogFragment();
                break;
            case R.id.view_blank:
            case R.id.tv_cancel:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
            case R.id.tv_send:
                sendComment();
                break;
            case R.id.btn_share_bottom_sheet_dialog_fragment:
                showShareBSDialogFragment();
                break;
        }
    }

    @Override
    public void onClick(String comment) {
        Toast.makeText(MainActivity.this, comment, Toast.LENGTH_SHORT).show();
        // TODO: upload comment to server
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
        mDialog = new BottomSheetDialog(MainActivity.this);
        View view = LayoutInflater
                .from(MainActivity.this)
                .inflate(R.layout.fragment_custom_bottom_sheet, null);

        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_send).setOnClickListener(this);
        view.findViewById(R.id.view_blank).setOnClickListener(this);
        mEditText = (EditText) view.findViewById(R.id.et_input_msg);
        final TextView tvSend = (TextView) view.findViewById(R.id.tv_send);

        mDialog.setContentView(view);
        mDialog.show();

        View bottomSheet = mDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(Utils.getHeight(MainActivity.this));

        ((View) view.getParent()).setBackgroundColor(ContextCompat
                .getColor(MainActivity.this, android.R.color.transparent));

        Utils.toggleSoftInput(MainActivity.this, mEditText);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {
                    tvSend.setTextColor(ContextCompat.getColor(MainActivity.this,
                            R.color.colorPrimary));
                } else {
                    tvSend.setTextColor(Color.parseColor("#C7C7C7"));
                }
            }
        });
    }

    private void sendComment() {
        if (mEditText == null) {
            return;
        }
        String comment = mEditText.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            return;
        }
        this.onClick(comment);
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private void showBottomSheetDialogFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomETBottomSheetDialogFragment mSheetDialogFragment =
                (CustomETBottomSheetDialogFragment) fragmentManager
                        .findFragmentByTag(TAG_BOTTOM_SHEET_DIALOG_FRAGMENT);

        if (mSheetDialogFragment == null) {
            mSheetDialogFragment = new CustomETBottomSheetDialogFragment();
        }
        mSheetDialogFragment.show(fragmentManager, TAG_BOTTOM_SHEET_DIALOG_FRAGMENT);
    }

    private void showShareBSDialogFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomShareBottomSheetDialogFragment fragment =
                (CustomShareBottomSheetDialogFragment) fragmentManager
                        .findFragmentByTag(TAG_SHARE_BS_DIALOG_FRAGMENT);

        if (fragment == null) {
            fragment = new CustomShareBottomSheetDialogFragment();
        }
        fragment.show(fragmentManager, TAG_SHARE_BS_DIALOG_FRAGMENT);
    }

    private void showDialogFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomDialogFragment dialogFragment = (CustomDialogFragment) fragmentManager
                .findFragmentByTag(TAG_DIALOG_FRAGMENT);
        if (dialogFragment == null) {
            dialogFragment = new CustomDialogFragment();
        }

        Dialog dialog = dialogFragment.getDialog();
        if (dialog == null || !dialog.isShowing()) {
            dialogFragment.show(fragmentManager, TAG_DIALOG_FRAGMENT);
        }
    }

    private void initView() {
        findViewById(R.id.btn_bottom_sheet).setOnClickListener(this);
        findViewById(R.id.btn_bottom_sheet_dialog).setOnClickListener(this);
        findViewById(R.id.btn_bottom_sheet_dialog_fragment).setOnClickListener(this);
        findViewById(R.id.btn_dialog_fragment).setOnClickListener(this);
        findViewById(R.id.btn_share_bottom_sheet_dialog_fragment).setOnClickListener(this);
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

    static class CustomSheetAdapter extends RecyclerView.Adapter<CustomSheetAdapter.SheetViewHolder> {

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


            SheetViewHolder(View itemView) {
                super(itemView);
                mTVTitle = (TextView) itemView.findViewById(R.id.tv_sheet_item);
                mIVIcon = (ImageView) itemView.findViewById(R.id.iv_sheet_item);
            }

            TextView getTVTitle() {
                return mTVTitle;
            }

            ImageView getIVIcon() {
                return mIVIcon;
            }
        }
    }
}
