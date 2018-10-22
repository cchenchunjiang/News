package com.example.administrator.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.news.R;
import com.example.administrator.news.WordsFragment.OnListFragmentInteractionListener;
import com.example.administrator.news.dummy.Words.WordItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link WordItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyWordsRecyclerViewAdapter extends RecyclerView.Adapter<MyWordsRecyclerViewAdapter.ViewHolder> {

    private final List<WordItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public WordItem getWordItem() {
        return wordItem;
    }

    public void setWordItem(WordItem wordItem) {
        this.wordItem = wordItem;
    }

    public WordItem wordItem;
    public void refresh(){
        Log.v("asdasd","asdasd");
        notifyDataSetChanged();
    }
    public MyWordsRecyclerViewAdapter(List<WordItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }
    public void delete(WordItem item){
        for(int i=0;i<mValues.size();i++){
            if(item.id==mValues.get(i).id){
                mValues.remove(item);
                notifyItemRemoved(i);
                notifyDataSetChanged();
            }
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_words, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).query);
        holder.mContentView.setText(mValues.get(position).explains);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public WordItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            view.setOnCreateContextMenuListener(this);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            setWordItem(mItem);
        }
    }
}
