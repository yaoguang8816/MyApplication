package com.yg.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yg.R;

import java.util.List;

/**
 * Created by baijunfeng on 17/3/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Integer> mDatas;

    public NoteAdapter(Context context, List<Integer> data)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = data;
    }

    public void updateDatas(List<Integer> data) {
        mDatas = data;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView mCardView;
        TextView mTextView;

        public ViewHolder(View arg0)
        {
            super(arg0);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.ancient_poetry_adapter_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mCardView = (CardView) view.findViewById(R.id.card_view1);
        viewHolder.mTextView = (TextView) view.findViewById(R.id.ancient_poetry_text);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        viewHolder.mTextView.setText("7月" + String.valueOf(mDatas.get(i)) + "日");
//
//        //如果设置了回调，则设置点击事件
//        if (mOnItemClickLitener != null)
//        {
//            viewHolder.itemView.setOnClickListener(new OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
//                }
//            });
//
//        }

    }

}
