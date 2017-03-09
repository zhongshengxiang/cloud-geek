package com.example.myapplication.Model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class Teacher {
    public List<ResultBean> result;

    @Override
    public String toString() {
        return "Teacher{" +
                "result=" + result +
                '}';
    }

    public static class ResultBean {
        @Override
        public String toString() {
            return "ResultBean{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", sid=" + sid +
                    '}';
        }

        /**
         * age : 13
         * name : 张三
         * sid : 1
         */

        public int age;
        public String name;
        public int sid;
    }
}
