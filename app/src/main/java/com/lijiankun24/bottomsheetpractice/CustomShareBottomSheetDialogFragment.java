package com.lijiankun24.bottomsheetpractice;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * CustomETBottomSheetDialogFragment.java
 * <p>
 * Created by lijiankun on 17/6/5.
 */

public class CustomShareBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @Override
    public void setupDialog(Dialog dialog, int style) {
        initView(dialog);
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
        CustomSheetAdapter sheetAdapter = new CustomSheetAdapter(mTitles, mIcons, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
