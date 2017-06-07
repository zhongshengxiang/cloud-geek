package com.example.rx.ui;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class Json {
    public String RespCode;
    public Object RespDesc;
    public RespDataBean RespData;
    public static class RespDataBean {
        public String BonusType;
        public boolean IsPlayed;
        public boolean ActiveBtn;
        public int LeftSecs;
        public String JsonText;
        public static class JsonTextBean {
            public String title;
            public String summary;
            public List<ContentBean> content;
            public static class ContentBean {
                public String item;
            }
        }
    }
}
