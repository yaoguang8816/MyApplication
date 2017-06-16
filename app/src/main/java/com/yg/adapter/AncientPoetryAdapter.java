package com.yg.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yg.R;
import com.yg.utils.PoetryCardContent;

import java.util.List;

/**
 * Created by baijunfeng on 17/3/17.
 */

public class AncientPoetryAdapter extends RecyclerView.Adapter<AncientPoetryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
//    private List<Integer> mDatas;

    private CardItemClickListener mOnItemClickLitener;

    private List<PoetryCardContent> mDatas;

    public AncientPoetryAdapter(Context context, List<PoetryCardContent> data) {
        mInflater = LayoutInflater.from(context);
        mDatas = data;
    }

    public void updateDatas(List<PoetryCardContent> data) {
        mDatas = data;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;

        //诗词标题
        TextView mTitleView;

        //作者名称
        TextView mAuthorView;

        //缩略语-名句
        TextView mSentencesView;

        public ViewHolder(View arg0)
        {
            super(arg0);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.ancient_poetry_adapter_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mCardView = (CardView) view.findViewById(R.id.card_view1);
        viewHolder.mTitleView = (TextView) view.findViewById(R.id.ancient_poetry_title);
        viewHolder.mAuthorView = (TextView) view.findViewById(R.id.ancient_poetry_author);
        viewHolder.mSentencesView = (TextView) view.findViewById(R.id.ancient_poetry_sentence);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        PoetryCardContent content = mDatas.get(i);
        viewHolder.mCardView.setBackgroundResource(R.drawable.card_bg);
        viewHolder.mTitleView.setText(content.getTitle());
        viewHolder.mAuthorView.setText(content.getAuthor());
        viewHolder.mSentencesView.setText(content.getAbbr());

        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(content);
                }
            });

        }

    }

    public void setOnItemClickListener(CardItemClickListener listener) {
        mOnItemClickLitener = listener;
    }

    public interface CardItemClickListener {
        public void onItemClick(PoetryCardContent content);
    }

}
