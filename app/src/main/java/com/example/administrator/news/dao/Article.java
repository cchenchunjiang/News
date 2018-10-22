package com.example.administrator.news.dao;

import android.provider.BaseColumns;

public class Article {
    public static abstract class Articles implements BaseColumns {
        public static final String TABLE_NAME="article";//表名
        public static final String COLUMN_NAME_id="id";//列：id
        public static final String COLUMN_NAME_title="title";//列：标题
        public static final String COLUMN_NAME_time="time";//列：时间
        public static final String COLUMN_NAME_tags="tags";//列：属性
        public static final String COLUMN_NAME_text="text";//列：文本
    }
}
