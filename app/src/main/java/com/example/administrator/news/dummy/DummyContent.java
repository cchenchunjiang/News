package com.example.administrator.news.dummy;

import android.database.Cursor;
import android.util.Log;

import com.example.administrator.news.ShouyeFragment;
import com.example.administrator.news.util.BBC;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */

    private static String url0="http://api02.idataapi.cn:8000/news/bbc?apikey=ICi7LAvGLUaqNdLMaMcX5MKuYw7gIOMjdNAZmtVPOOhSHYA9uu8X1Jts3S55pqEO&catid=/cps/news/asia";
    private static String url1="http://api02.idataapi.cn:8000/news/bbc?apikey=ICi7LAvGLUaqNdLMaMcX5MKuYw7gIOMjdNAZmtVPOOhSHYA9uu8X1Jts3S55pqEO&catid=/cps/news/uk";
    private static String url2="http://api02.idataapi.cn:8000/news/bbc?apikey=ICi7LAvGLUaqNdLMaMcX5MKuYw7gIOMjdNAZmtVPOOhSHYA9uu8X1Jts3S55pqEO&catid=/cps/news/world/us_and_canada";
    private static String url3="http://api02.idataapi.cn:8000/news/bbc?apikey=ICi7LAvGLUaqNdLMaMcX5MKuYw7gIOMjdNAZmtVPOOhSHYA9uu8X1Jts3S55pqEO&catid=/cps/news/world/australia";
    private static String url4="http://api02.idataapi.cn:8000/news/bbc?apikey=ICi7LAvGLUaqNdLMaMcX5MKuYw7gIOMjdNAZmtVPOOhSHYA9uu8X1Jts3S55pqEO&catid=/cps/sport/front-page";
    private static String url5="http://api02.idataapi.cn:8000/news/bbc?apikey=ICi7LAvGLUaqNdLMaMcX5MKuYw7gIOMjdNAZmtVPOOhSHYA9uu8X1Jts3S55pqEO&catid=/cps/news/technology";
    private static String url6="http://api02.idataapi.cn:8000/news/bbc?apikey=ICi7LAvGLUaqNdLMaMcX5MKuYw7gIOMjdNAZmtVPOOhSHYA9uu8X1Jts3S55pqEO&catid=/cps/news/magazine";

    public static void setDummyContent(Cursor c){
        ITEMS.removeAll(ITEMS);
        DummyItem itme=null;
        if (c!=null) {
            while (c.moveToNext()) {
                itme=new DummyItem(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(0));
                ITEMS.add(itme);
            }
        }
    }
    public static void setItems(int i){
        String url=url0;
        DummyItem dummyItem=null;
        switch (i){
            case 0:
                url=url0;break;
            case 1:
                url=url1;break;
            case 2:
                url=url2;break;
            case 3:
                url=url3;break;
            case 4:
                url=url4;break;
            case 5:
                url=url5;break;
            case 6:
                url=url6;break;
                default:break;
        }
       ITEMS.removeAll(ITEMS);
        BBC bbc=new BBC();
        try {
            JSONObject json =bbc.getRequestFromUrl(url);
            JSONArray jsonArray=json.getJSONArray("data");
            for(int j=0;j<jsonArray.length();j++){
                dummyItem=new DummyItem(jsonArray.getJSONObject(j).getString("title"),
                        jsonArray.getJSONObject(j).getString("publishDateStr"),
                        jsonArray.getJSONObject(j).getString("tags"),
                        jsonArray.getJSONObject(j).getString("content"),0);
                ITEMS.add(dummyItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String title;
        public final String time;
        public final String tags;
        public final String text;
        public final int id;

        public DummyItem(String title, String time, String tags,String text,int id) {
            this.title = title;
            this.time=time;
            this.tags=tags;
            this.text=text;
            this.id=id;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
