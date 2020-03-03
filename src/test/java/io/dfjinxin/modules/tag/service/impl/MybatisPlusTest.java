//package io.dfjinxin.modules.tag.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.google.gson.Gson;
//import io.dfjinxin.common.utils.DateTools;
//import io.dfjinxin.modules.storage.dto.DesenForm;
//import io.dfjinxin.modules.tag.controller.FiledLabelViewController;
//import io.dfjinxin.modules.tag.controller.TagDataTblController;
//import io.dfjinxin.modules.tag.dao.TagInfoDao;
//import io.dfjinxin.modules.tag.entity.TagInfoEntity;
//import io.dfjinxin.modules.tag.service.TagDataTblService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * @Description:
// * @Author: z.h.c
// * @CreateDate: 2019/6/25 10:26
// * @Version: 1.0
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MybatisPlusTest {
//
//    @Autowired
//    TagDataTblController tagDataTblController;
//    @Autowired
//    FiledLabelViewController filedLabelViewController;
//
//    @Autowired
//    TagDataTblService dataTblService;
//
//    @Autowired
//    TagInfoDao tagInfoDao;
//
//    @Test
//    public void test() {
//        /*DesenForm desenForm = new DesenForm();
//        org.springframework.util.Assert.isNull(desenForm,"请求参数为空");
//        desenForm.setPartTypeid(1);
//        desenForm.setStart(2);
//        desenForm.setPageSize(20);
//        Integer[] tblIds = {1,2};
//        desenForm.setTblIds(tblIds);
////        R tblAndFldInfoListbyTblIds = tagDataTblController.getTblAndFldInfoListbyTblIds(desenForm);
//        PageUtils pageUtils = this.dataTblService.getTblAndFldInfoListbyTblIds(desenForm);*/
//
////        List<TagInfoEntity> tagInfoEntityList = tagInfoDao.selectList(new QueryWrapper<TagInfoEntity>()
////                .in("Labelid", 1)
////        );
////
////        Assert.assertNull(tagInfoEntityList);
////
////        for (TagInfoEntity entity : tagInfoEntityList) {
////            System.out.println("tagInfo:" + entity.toString());
////        }
//
//        DesenForm desenForm = new DesenForm();
////        int labelId = 3;//标签id
//        String[] tableMarksStr = {};//字段id集合
//        String[] fieldMarksStr = {};//字段id集合
//        desenForm.setPartid(1);
//        desenForm.setPageIndex(1);
//        desenForm.setPageSize(20);
//        desenForm.setLabelId(3);
//        desenForm.setTableMarksStr(tableMarksStr);
//        desenForm.setFieldMarksStr(fieldMarksStr);
//        filedLabelViewController.queryLabelViewInfoByPage(desenForm);
//
//    }
//
//    /**
//    * @Desc:
//    * @Param: []
//    * @Return: void
//    * @Author: z.h.c
//    * @Date: 2019/7/11
//    */
//    @Test
//    public void test2() {
//        QueryWrapper<TagInfoEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda()
//                .eq(TagInfoEntity::getLabelid, 1023)
//                .eq(TagInfoEntity::getLabelNm, "出生信息");
//        List<TagInfoEntity> studentList = tagInfoDao.selectList(queryWrapper);
//        for (TagInfoEntity student : studentList)
//            System.out.println((new Gson().toJson(student)));
//    }
//
//}
