package com.lijiankun24.bottomsheetpractice;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * CustomETBottomSheetDialogFragment.java
 * <p>
 * Created by lijiankun on 17/6/5.
 */

public class CustomShareBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private CustomETBottomSheetDialogFragment.OnSendCommentClickListener mCommentClickListener = null;

    private EditText mEditText = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CustomETBottomSheetDialogFragment.OnSendCommentClickListener) {
            mCommentClickListener = (CustomETBottomSheetDialogFragment.OnSendCommentClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSendCommentClickListener");
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        initView(dialog);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCommentClickListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_blank:
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_send:
                sendComment();
                break;
        }
    }

    public void sendComment() {
        String comment = mEditText.getText().toString();
        if (!isAdded() || mCommentClickListener == null || TextUtils.isEmpty(comment)) {
            return;
        }
        mCommentClickListener.onClick(comment);
        dismiss();
    }

    private void initView(Dialog dialog) {
        if (dialog == null || !isAdded()) {
            return;
        }

        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.fragment_share, null);
        dialog.setContentView(view);

        View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(Utils.getHeight(getContext()));

        // https://stackoverflow.com/questions/37104960/bottomsheetdialog-with-transparent-background
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll_sheet_root);
        ((View) layout.getParent())
                .setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));

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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_sheet);
        MainActivity.CustomSheetAdapter sheetAdapter = new MainActivity.CustomSheetAdapter(mTitles, mIcons, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(sheetAdapter);
    }
}
