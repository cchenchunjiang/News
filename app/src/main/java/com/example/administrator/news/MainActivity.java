package com.example.administrator.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.news.adapter.BottomViewAdapter;
import com.example.administrator.news.dummy.DummyContent;
import com.example.administrator.news.dummy.Words;
import com.youdao.sdk.app.YouDaoApplication;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements ArticleFragment.OnListFragmentInteractionListener,ShouyeFragment.OnFragmentInteractionListener
,TranslateFragment.OnFragmentInteractionListener,MeanFragment.OnFragmentInteractionListener,
CollectionFragment.OnFragmentInteractionListener,WordsFragment.OnListFragmentInteractionListener{

    private ViewPager viewPager;
    private ShouyeFragment f1;
    private TranslateFragment f2;
    public static final CollectionFragment f3= new CollectionFragment();
    private  int i;
    private int type;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    type=0;
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_translate:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_words:
                    type=1;
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YouDaoApplication.init(this, "08d9185e4400ba9b");


        Thread thread=new Thread() {
            public void run() {
                DummyContent.setItems(0);
            }
        };
        thread.start();
        while (thread.isAlive()){};
        if(DummyContent.ITEMS.size()==0){
            Toast.makeText(this,"暂时无法访问",Toast.LENGTH_LONG).show();
        }
        //首页
        viewPager=findViewById(R.id.viewpager);
        List<Fragment> list_fragment = new ArrayList<>(3);
        f1= new ShouyeFragment();
        f2= new TranslateFragment();
        list_fragment.add(f1);
        list_fragment.add(f2);
        list_fragment.add(f3);

        BottomViewAdapter adapter = new BottomViewAdapter(getSupportFragmentManager(), list_fragment);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
       // 底部
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    @Override
    public void onListFragmentInteraction(Words.WordItem item) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent=new Intent(MainActivity.this,ReadActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        bundle.putInt("id",item.id);
        bundle.putString("title",item.title);
        bundle.putString("tags",item.tags);
        bundle.putString("text",item.text);
        bundle.putString("time",item.time);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
    @Override
    public void onFragmentInteraction(int j) {
        i=j;
        Thread thread=new Thread() {
            public void run() {
                DummyContent.setItems(i);
            }
        };
        thread.start();
        while (thread.isAlive()){};
        f1.fragment.myArticleRecyclerViewAdapter.refresh();
        if(DummyContent.ITEMS.size()==0){
           Toast.makeText(this,"暂时无法访问",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
