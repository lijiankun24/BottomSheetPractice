package com.lijiankun24.bottomsheetpractice;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * CustomETBottomSheetDialogFragment.java
 * <p>
 * Created by lijiankun on 17/6/5.
 */

public class CustomETBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

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
                .inflate(R.layout.fragment_custom_bottom_sheet, null);
        dialog.setContentView(view);

        View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(Utils.getHeight(getContext()));

        // https://stackoverflow.com/questions/37104960/bottomsheetdialog-with-transparent-background
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll_fragment_root);
        ((View) layout.getParent())
                .setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));

        view.findViewById(R.id.view_blank).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_send).setOnClickListener(this);

        final TextView textView = (TextView) view.findViewById(R.id.tv_send);
        mEditText = (EditText) view.findViewById(R.id.et_input_msg);
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
                    textView.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.colorPrimary));
                } else {
                    textView.setTextColor(Color.parseColor("#C7C7C7"));
                }
            }
        });
        Utils.toggleSoftInput(getContext(), mEditText);
    }


    interface OnSendCommentClickListener {
        void onClick(String comment);
    }
}
