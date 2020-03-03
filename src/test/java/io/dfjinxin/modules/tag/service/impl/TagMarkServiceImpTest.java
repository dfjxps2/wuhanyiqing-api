//package io.dfjinxin.modules.tag.service.impl;
//
//import io.dfjinxin.modules.tag.service.TagMarkService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@WebAppConfiguration
//public class TagMarkServiceImpTest {
//
//    @Autowired
//    private TagMarkService tagMarkService;
//
//    @Test
//    public void tableAutoMark() {
//        List<String> tableIds = new ArrayList<>();
//        tableIds.add("1");
//        tableIds.add("2");
//        tableIds.add("3");
//        tableIds.add("4");
//        tableIds.add("5");
//        tableIds.add("6");
//        Map<Integer, List<Map<String, String>>> markResult = tagMarkService.tableAutoMark(tableIds);
//        System.out.println(markResult);
//    }
//
//
//    @Test
//    public void fieldAutoMark() {
//        List<String> filedIds = new ArrayList<>();
//        filedIds.add("1");
//        filedIds.add("2");
//        filedIds.add("3");
//        Map<Integer, List<Map<String, String>>> markResult = tagMarkService.fieldAutoMark(filedIds);
//        System.out.println(markResult);
//    }
//}