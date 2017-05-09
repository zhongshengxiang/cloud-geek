package com.example.myapplication.Model;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Administrator on 2017/4/7.
 */

public class MySection extends SectionEntity<Student> {
    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySection(Student student) {
        super(student);
    }
}
