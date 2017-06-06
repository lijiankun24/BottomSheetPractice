package com.lijiankun24.bottomsheetpractice;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * CustomBottomSheetDialogFragment.java
 * <p>
 * Created by lijiankun on 17/6/5.
 */

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private OnSendCommentClickListener mCommentClickListener = null;

    private EditText mEditText = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSendCommentClickListener) {
            mCommentClickListener = (OnSendCommentClickListener) context;
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
        if (!isAdded() || mCommentClickListener == null) {
            return;
        }
        String comment = mEditText.getText().toString();
        mCommentClickListener.onClick(comment);
        dismiss();
    }

    private void initView(Dialog dialog) {
        if (dialog == null || !isAdded()) {
            return;
        }

        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.fragment_custom_bottom_sheet, null);
        dialog.setContentView(view);

        View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(Utils.getHeight(getContext()));

        // https://stackoverflow.com/questions/37104960/bottomsheetdialog-with-transparent-background
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll_fragment_root);
        ((View) layout.getParent())
                .setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));

        mEditText = (EditText) view.findViewById(R.id.et_input_msg);
        view.findViewById(R.id.view_blank).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_send).setOnClickListener(this);
        Utils.toggleSoftInput(getContext(), mEditText);
    }


    interface OnSendCommentClickListener {
        void onClick(String comment);
    }
}
