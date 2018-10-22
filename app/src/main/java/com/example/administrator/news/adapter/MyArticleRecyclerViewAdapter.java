package com.example.administrator.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.news.ArticleFragment.OnListFragmentInteractionListener;
import com.example.administrator.news.R;
import com.example.administrator.news.ShouyeFragment;
import com.example.administrator.news.dummy.DummyContent;
import com.example.administrator.news.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyArticleRecyclerViewAdapter extends RecyclerView.Adapter<MyArticleRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyArticleRecyclerViewAdapter(List<DummyItem> list,OnListFragmentInteractionListener listener) {
        mValues=list;
        mListener = listener;
    }
    public void refresh(){
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.titleView.setText(mValues.get(position).title);
        holder.timeView.setText(mValues.get(position).time);
        holder.tagsView.setText(mValues.get(position).tags);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titleView;
        public final TextView timeView;
        public final TextView tagsView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = (TextView) view.findViewById(R.id.titleview);
            timeView = (TextView) view.findViewById(R.id.timeview);
            tagsView = (TextView) view.findViewById(R.id.tagsview);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }
    }
}
