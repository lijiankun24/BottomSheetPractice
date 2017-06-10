package com.lijiankun24.bottomsheetpractice;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * CustomDialogFragment.java
 * <p>
 * Created by lijiankun on 17/6/7.
 */

public class CustomDialogFragment extends DialogFragment implements View.OnClickListener {

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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog mDialog = new Dialog(getContext(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);              // 在 setContentView 方法前设置
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        View view = mInflater.inflate(R.layout.fragment_dialog_custom, null);
        initView(view);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);                            // 外部点击取消

        Window window = mDialog.getWindow();                                // 设置宽度为屏宽, 靠近屏幕底部。
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;                                    // 紧贴底部
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;             // 宽度持平
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setAttributes(lp);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        return mDialog;
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

    private void sendComment() {
        String comment = mEditText.getText().toString();
        if (!isAdded() || mCommentClickListener == null || TextUtils.isEmpty(comment)) {
            return;
        }
        mCommentClickListener.onClick(comment);
        dismiss();
    }

    private void initView(View view) {
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
    }
}
