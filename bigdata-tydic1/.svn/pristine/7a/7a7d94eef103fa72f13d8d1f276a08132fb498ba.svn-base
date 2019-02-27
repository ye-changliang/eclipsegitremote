package com.tydic.bigdata.dynamic.hive.test;

import com.tydic.bigdata.dynamic.hive.DynamicHive;

import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args){
        DynamicHive dh = new DynamicHive("test");
        List<Map<String,String>> result = dh.execute(DynamicHive.SELECT,"select count(*) num from IP_BSS_BS_DAY.TB_JZYXTASK_ACT_COLLECT");
        System.out.println(result.size());
    }
}
