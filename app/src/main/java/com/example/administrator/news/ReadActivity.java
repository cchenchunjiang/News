package com.example.administrator.news;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.news.dao.Article;
import com.example.administrator.news.daomain.NoteDBHelper;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import java.util.List;


public class ReadActivity extends AppCompatActivity {
    private Bundle bundle;
    private  EditText editText;
    private  int state;
    private Menu menu;
    private String text;
    private NoteDBHelper mDbHelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mDbHelper=new NoteDBHelper(this);

        Toolbar toolbar=findViewById(R.id.readtoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        state=0;

        editText=findViewById(R.id.readtext);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setSingleLine(false);
        editText.setHorizontallyScrolling(false);

        bundle=getIntent().getBundleExtra("bundle");
        editText.append(bundle.getString("title")+"\n\n");
        editText.append(bundle.getString("text"));
        editText.setSelection(0);


        text="";
        String s=editText.getText().toString();
        Language langFrom= LanguageUtils.getLangByName("英文");
        Language langTo= LanguageUtils.getLangByName("中文");
        TranslateParameters tps = new TranslateParameters.Builder()
                .source("fanyi")
                .from(langFrom).to(langTo).build();
        Translator translator = Translator.getInstance(tps);
        Log.v("asd","asd"+s);
        translator.lookup(s, "requestId", new TranslateListener() {

            @Override
            public void onError(TranslateErrorCode translateErrorCode, String s) {
            }

            @Override
            public void onResult(Translate translate, String s, String s1) {
                text=translate.getTranslations().toString();
            }

            @Override
            public void onResult(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        this.menu=menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }
                break;
            case R.id.translatetext:
                if(state==0){
                    if(!text.equals("")) {
                        editText.setText("");
                        editText.append(text);
                        editText.setSelection(0);
                        state = 1;
                        menu.findItem(R.id.translatetext).setTitle("原文");
                    }
                }else if(state==1){
                    editText.setText("");
                    editText.append(bundle.getString("title")+"\n\n"+bundle.getString("text"));
                    state=0;
                    editText.setSelection(0);
                    menu.findItem(R.id.translatetext).setTitle("翻译");
                }
                break;
            case R.id.collection:
                if(bundle.getInt("type")==0){// 收藏
                    ContentValues values=new ContentValues();
                    values.put(Article.Articles.COLUMN_NAME_title,bundle.getString("title"));
                    values.put(Article.Articles.COLUMN_NAME_time,bundle.getString("time"));
                    values.put(Article.Articles.COLUMN_NAME_tags,bundle.getString("tags"));
                    values.put(Article.Articles.COLUMN_NAME_text,bundle.getString("text"));
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    long ok=db.insert(Article.Articles.TABLE_NAME,null,values);
                    if(MainActivity.f3.type==1)
                        CollectionFragment.fragment1.myArticleRecyclerViewAdapter.notifyDataSetChanged();
                    if(ok>0)
                    Toast.makeText(this,"收藏成功",Toast.LENGTH_LONG).show();
                }else if(bundle.getInt("type")==1){// 取消收藏
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    String whereClause=Article.Articles.COLUMN_NAME_id + "=" +bundle.getInt("id");
                    if( db.delete(Article.Articles.TABLE_NAME, whereClause, null)>0)
                        Toast.makeText(this,"取消了收藏",Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
    }
}
