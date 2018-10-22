package com.example.administrator.news;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.news.dao.Article;
import com.example.administrator.news.dao.Collect;
import com.example.administrator.news.daomain.NoteDBHelper;
import com.example.administrator.news.dummy.DummyContent;
import com.example.administrator.news.dummy.Words;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CollectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static  final  ArticleFragment  fragment1=new ArticleFragment();
    public static  final  WordsFragment  fragment2=new WordsFragment();
    public int type;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NoteDBHelper mDbHelper;

    private OnFragmentInteractionListener mListener;

    public CollectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectionFragment newInstance(String param1, String param2) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.article:
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
                qb.setTables(Article.Articles.TABLE_NAME);
                Cursor cursor=qb.query(db,null,null,null,null,null,null);
                DummyContent.setDummyContent(cursor);
                //fragment1.myArticleRecyclerViewAdapter.refresh();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.collectionffff,fragment1)
                        .commit();
                type=1;
                db.close();
                break;
            case R.id.words:
                SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
                SQLiteQueryBuilder qb1 = new SQLiteQueryBuilder();
                qb1.setTables(Collect.Collects.TABLE_NAME);
                Cursor cursor1=qb1.query(db1,null,null,null,null,null,null);
                Words.setlist(cursor1);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.collectionffff,fragment2)
                        .commit();
                type=2;
                db1.close();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu2,menu);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_collection, container, false);
        Toolbar toolbar=view.findViewById(R.id.collectoolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        mDbHelper=new NoteDBHelper(getContext());
        ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);



        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
        SQLiteQueryBuilder qb1 = new SQLiteQueryBuilder();
        qb1.setTables(Collect.Collects.TABLE_NAME);
        Cursor cursor1=qb1.query(db1,null,null,null,null,null,null);
        Words.setlist(cursor1);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.collectionffff,fragment2)
                .commit();
        type=2;
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDbHelper.close();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
