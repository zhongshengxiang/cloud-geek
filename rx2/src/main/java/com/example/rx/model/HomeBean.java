package com.example.rx.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vinctor on 2016/4/27.
 */
public class HomeBean {

    public List<ItemHomeBean> items;

    public class ItemHomeBean {
        public String lt_sn;
        public String sn;//期号
        public String id;//夺宝活动ID
        public String lt_id;//夺宝活动ID
        public String p_head_img;//pic

        //@SerializedName("p_name")
        public String title;//标题

        public String p_name;

        public String spec_zone;//专区名称
        public int lotto_quota;//总需人次
        @SerializedName("surplus")
        public int left;//剩余人次
        public int prog_rate;//进度
        public int min_bets;//步长,最小
        public String zone_head_img;

    }
}
