package com.example.administrator.news;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.news.dummy.DummyContent;

import java.util.ArrayList;



public class ShouyeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static  final  ArticleFragment  fragment=new ArticleFragment();
    // TODO: Rename and change types of parameters

    private LinearLayout titleLayout;
    private int currentPos;                         //现在显示的是第几页
    private String[] strList = new String[]{"Asia", "UK", "US&Canada", "Australia", "Sport", "Technology", "Magazine"};
    private int[] idList = new int[]{0, 1, 2, 3, 4, 5, 6};
    private ArrayList<TextView> textViewList;       //标题控件集合
    private ArrayList<String> titleList;            //标题文字集合

    private OnFragmentInteractionListener mListener;

    public ShouyeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShouyeFragment newInstance(String param1, String param2) {
        ShouyeFragment fragment = new ShouyeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_shouye, container, false);
        getFragmentManager()
	    .beginTransaction()
	    .replace(R.id.ffff,fragment)
	     .commit();
        titleLayout=view.findViewById(R.id.titleLayout);
        textViewList=new ArrayList<>();
        titleList=new ArrayList<>();
        for(int i=0; i<strList.length; i++){
            titleList.add(strList[i]);
            addTitleLayout(titleList.get(i), idList[i]);
        }

        textViewList.get(0).setTextColor(Color.rgb(255, 0, 0));
        currentPos = 0;
        return view;
    }


    private void addTitleLayout(String title, int position){
        final TextView textView = (TextView) getLayoutInflater().inflate(R.layout.title, null);
        textView.setText(title);
        textView.setTag(position);
        textView.setOnClickListener(new posOnClickListener());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin =10;
        params.rightMargin =10;
        titleLayout.addView(textView, params);
        textViewList.add(textView);

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
    class posOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //点击的是当前标题什么都不做
            if ((int) view.getTag() == currentPos) {
                mListener.onFragmentInteraction(currentPos);
                return;
            }
            //标题栏变色且设置viewPager
            textViewList.get(currentPos).setTextColor(Color.rgb(0, 0, 0));
            currentPos = (int) view.getTag();
            textViewList.get(currentPos).setTextColor(Color.rgb(255, 0, 0));
            mListener.onFragmentInteraction(currentPos);
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int i);
    }
}
