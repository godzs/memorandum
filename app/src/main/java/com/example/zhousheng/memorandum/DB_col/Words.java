package com.example.zhousheng.memorandum.DB_col;

import android.provider.BaseColumns;

public class Words {

    //每个事件的描述
    public static class WordDescription {
        public String id;
        public String word;
        public String meaning;
        public String sample;

        public WordDescription(String id, String word, String meaning, String sample) {
            this.id = id;
            this.word = word;
            this.meaning = meaning;
            this.sample = sample;
        }
    }
    public Words() {
    }

    //注意，接口BaseColumns接口有一个字段为"_ID",该字段对于ContentProvider非常重要
    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME = "words";//表名
        public static final String COLUMN_NAME_WORD = "word";//字段：标题
        public static final String COLUMN_NAME_MEANING = "meaning";//字段：日期
        public static final String COLUMN_NAME_SAMPLE = "sample";//字段：内容

    }
}
