package com.example.administrator.news;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.news.dao.Article;
import com.example.administrator.news.dao.Collect;
import com.example.administrator.news.daomain.NoteDBHelper;
import com.example.administrator.news.dummy.Words;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TranslateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TranslateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Translator translator;
    private Spinner spinner1;
    private Spinner spinner2;
    private EditText editText;
    private ImageView imageView;
    private Button button;

    private String query;
    private String as;
    private List<String> explains;

    private Language langFrom;
    private Language langTo;
    private OnFragmentInteractionListener mListener;
    private NoteDBHelper mDbHelper ;
    public TranslateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TranslateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TranslateFragment newInstance(String param1, String param2) {
        TranslateFragment fragment = new TranslateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper=new NoteDBHelper(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_translate, container, false);

        editText=view.findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setSingleLine(false);
        editText.setHorizontallyScrolling(false);

        spinner1=view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langFrom = LanguageUtils.getLangByName(spinner1.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2=view.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.items, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langTo = LanguageUtils.getLangByName(spinner2.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       button=view.findViewById(R.id.button);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               query = editText.getText().toString();
               TranslateParameters tps = new TranslateParameters.Builder()
                       .source("fanyi")
                       .from(langFrom).to(langTo).build();
               Log.v("as",langFrom+"   "+langTo+"  "+query);
               translator = Translator.getInstance(tps);

               translator.lookup(query, "requestId", new TranslateListener() {

                   @Override
                   public void onError(TranslateErrorCode translateErrorCode, String s) {
                   }


                   @Override
                   public void onResult(Translate translate, String s, String s1) {
                       as=translate.getTranslations().toString();
                       explains=translate.getExplains();
                       MeanFragment meanFragment=new MeanFragment();
                       meanFragment.setvalues(query,as,explains);
                       getFragmentManager().beginTransaction()
                               .replace(R.id.translate,meanFragment)
                               .commit();
                   }

                   @Override
                   public void onResult(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {

                   }
               });
           }
       });
       Button button=view.findViewById(R.id.button2);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ContentValues values=new ContentValues();
               values.put(Collect.Collects.COLUMN_NAME_querys,query);
               values.put(Collect.Collects.COLUMN_NAME_explains,as+explains);
               SQLiteDatabase db = mDbHelper.getWritableDatabase();
               long ok=db.insert(Collect.Collects.TABLE_NAME,null,values);
               if(MainActivity.f3.type==2) {
                   SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
                   qb.setTables(Collect.Collects.TABLE_NAME);
                   Cursor cursor1=qb.query(db,null,null,null,null,null,null);
                   Words.setlist(cursor1);
                   CollectionFragment.fragment2.myWordsRecyclerViewAdapter.refresh();
                   Toast.makeText(getContext(),"122",Toast.LENGTH_LONG).show();
               }
               if(ok>0)
                   Toast.makeText(getContext(),"收藏成功",Toast.LENGTH_LONG).show();
           }
       });
        // fragment
        MeanFragment meanFragment=new MeanFragment();
        meanFragment.setvalues(null,null,null);
        getFragmentManager().beginTransaction()
                .replace(R.id.translate,meanFragment)
                .commit();
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
