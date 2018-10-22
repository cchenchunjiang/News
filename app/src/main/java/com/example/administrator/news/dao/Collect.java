package com.example.administrator.news.dao;

import android.provider.BaseColumns;

public class Collect {
    public static abstract class Collects implements BaseColumns {
        public static final String TABLE_NAME="collect";//表名
        public static final String COLUMN_NAME_id="id";//列：id
        public static final String COLUMN_NAME_querys="querys";//列：标题
        public static final String COLUMN_NAME_explains="explains";//列：属性
    }
}
