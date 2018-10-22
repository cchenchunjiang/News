package com.example.administrator.news.dummy;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class Words {
    public static final List<WordItem> list=new ArrayList<>();
    public static void setlist(Cursor c){
        list.removeAll(list);
        WordItem itme=null;
        if (c!=null) {
            while (c.moveToNext()) {
                itme=new WordItem(c.getInt(0),c.getString(1),c.getString(2));
                list.add(itme);
            }
        }
    }
    public static class WordItem {
        public final int id;
       public final  String query;
        public final  String explains;

        WordItem(int id,String query,String explains){
            this.id=id;
            this.query=query;
            this.explains=explains;
        }
    }
}
