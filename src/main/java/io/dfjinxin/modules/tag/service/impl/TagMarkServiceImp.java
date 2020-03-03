package io.dfjinxin.modules.tag.service.impl;

import com.google.common.base.Strings;
import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.tag.dao.TagMarkDao;
import io.dfjinxin.modules.tag.entity.AutoMarkIdxRule;
import io.dfjinxin.modules.tag.entity.TagTypeEntity;
import io.dfjinxin.modules.tag.service.TagMarkService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service("tagMarkServiceImp")
public class TagMarkServiceImp implements TagMarkService {

    private Logger logger = LoggerFactory.getLogger(TagMarkServiceImp.class);

    private final static Integer MARK_IDX_TYPE_AUTO = 0;//自动标引

    private final static Integer MARK_IDX_TYPE_CUST = 1;//手动选择

    private final static Integer AUTO_MARK_IDX_OBJ_TYPE_TABLE = 0;//自动匹配维度：表匹配

    private final static Integer AUTO_MARK_IDX_OBJ_TYPE_COLUM = 1;//自动匹配维度：字段匹配

    private boolean mockTest = false;

    @Autowired
    private TagMarkDao tagMarkDao;

    private static ThreadLocal<Map<Integer,List<Integer>>> tagTmpMap = new ThreadLocal<>();

    @Override
    public Map<Integer, List<Map<String, String>>> tableAutoMark(List<String> tableIds, Long tnmtid){
        //获取表定义 联表 租户-->数据库分区-->数据库-->数据表
        logger.info("待匹配的表Id列表:{}",tableIds);

        if(tableIds!=null&&tableIds.size()>0){
        }else{
            logger.error("当前自动匹配无法进行,待匹配列表为空");
            return  new HashMap<>();
        }
        List markDataList = tagMarkDao.getTableInfosByIds(tableIds);
        return this.doAutoMark(markDataList,AUTO_MARK_IDX_OBJ_TYPE_TABLE, tnmtid);
    }

    @Override
    public Map<Integer, List<Map<String, String>>> fieldAutoMark(List<String> fieldIds, Long tnmtid){
        logger.info("待匹配的字段Id列表:{}",fieldIds);

        if(fieldIds!=null&&fieldIds.size()>0){
        }else{
            logger.error("当前自动匹配无法进行,待匹配列表为空");
            return  new HashMap<>();
        }

        List markDataList = tagMarkDao.getFieldInfosByIds(fieldIds);
        return this.doAutoMark(markDataList,AUTO_MARK_IDX_OBJ_TYPE_COLUM, tnmtid);
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<Integer, List<Map<String,String>>> doAutoMark(List markDataList,Integer markIdxObjType, Long tnmtid) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startAutoDate = new Date();
        logger.debug("开始执行时间:{}",simpleDateFormat.format(startAutoDate));

        Map<Integer,List<Map<String,String>>> autoMarkResult = new HashMap<>();

        //根据当前用户所属租户获取所有叶子节点标签类别信息
        List<Integer> tagCatgids = this.getTagCatgList(tnmtid.intValue());
        if(tagCatgids==null){
            return autoMarkResult;
        }
        
        //根据叶子节点标签类别获取所有匹配规则
        List<AutoMarkIdxRule> tagRuleList = tagMarkDao.getAllTagRules(tagCatgids);
        Map<Integer,String> tagNameCache = new HashMap<>();
        for (AutoMarkIdxRule autoMarkIdxRule : tagRuleList) {
            tagNameCache.put(autoMarkIdxRule.getLabelid(),autoMarkIdxRule.getLabelNm());
        }

        //开始循环执行
        tagTmpMap.set(new HashMap<>());

        for (AutoMarkIdxRule autoMarkIdxRule : tagRuleList) {
            Integer tagId = autoMarkIdxRule.getLabelid();
            Integer markIdxObjTypeTmp = autoMarkIdxRule.getMarkIdxObjType();//0：数据表 1：字段
            if(!markIdxObjType.equals(markIdxObjTypeTmp)){
                continue;
            }
            logger.info("执行匹配规则:标签-{} 规则-{}",autoMarkIdxRule.getLabelNm(),autoMarkIdxRule.getRuleDesc());

            Integer mathStgy = autoMarkIdxRule.getObjMatchStgy();
            String ruleExprs = autoMarkIdxRule.getRuleExprs();//正则表达式
//            ruleExprs = this.escapeExprSpecialWord(ruleExprs);
            if(Strings.isNullOrEmpty(ruleExprs)){
                continue;
            }

            Pattern pattern = null;

            try{
                pattern = Pattern.compile(ruleExprs);
            }catch(PatternSyntaxException e){
                throw new RuntimeException("正则表达式错误："+ruleExprs);
            }
            List<Integer> tagResultList = null;

            if(mathStgy==0){//正则
                tagResultList = markMatch(markDataList,tagId, pattern,true);
            }else if(mathStgy==1){//模糊匹配
                tagResultList =  markMatch(markDataList,tagId,pattern,false);
            }else{
                continue;
            }

            logger.info("匹配规则:标签-{} 规则-{},匹配结果为",
                    autoMarkIdxRule.getLabelNm(),
                    autoMarkIdxRule.getRuleDesc(),
                    tagResultList);

            if(tagResultList!=null){
                Map<Integer, List<Integer>> tagMapCache = tagTmpMap.get();
                if(!tagMapCache.containsKey(autoMarkIdxRule.getLabelid())){
                    tagMapCache.put(autoMarkIdxRule.getLabelid(),new ArrayList<>());
                }
                tagMapCache.get(autoMarkIdxRule.getLabelid()).addAll(tagResultList);
            }
        }

        logger.debug("标签对应关系数据为:{}",tagTmpMap.get());

        //保存标签与表的对应关系
        saveLabelAssign(markIdxObjType);

        Map<Integer, List<Integer>> tagAssign = tagTmpMap.get();
        for (Integer tagId : tagAssign.keySet()) {
            List<Integer> markTableIds = tagAssign.get(tagId);
            for (Integer tableId : markTableIds) {
                if(!autoMarkResult.containsKey(tableId)){
                    autoMarkResult.put(tableId,new ArrayList<>());
                }
                Map<String,String> labelMap = new HashMap<>();
                labelMap.put("labelId",tagId.toString());
                labelMap.put("labelNm",tagNameCache.get(tagId));
                autoMarkResult.get(tableId).add(labelMap);
            }
        }

        Date endAutoDate = new Date();
        logger.debug("结束执行时间:{},共计耗时{}毫秒",simpleDateFormat.format(endAutoDate),(endAutoDate.getTime() - startAutoDate.getTime()));

        return autoMarkResult;

    }


    private List<Integer> getTagCatgList(Integer tnmtid){
        List<TagTypeEntity> tagTypeList = tagMarkDao.getAllTagTypes(tnmtid);
        List<Integer> rootTagCatgIds = this.checkRootTagCatgId(tagTypeList);
        List<TagTypeEntity> leafTagCatgs = new ArrayList<>();
        for (Integer rootTagCatgId : rootTagCatgIds) {
            List<TagTypeEntity> leafTagCatgsTmp = this.getLeafTagType(tagTypeList, rootTagCatgId);
            if(leafTagCatgsTmp!=null&&leafTagCatgsTmp.size()>0){
                leafTagCatgs.addAll(leafTagCatgsTmp);
            }
        }

        List<Integer> tagCatgids = new ArrayList<>();


        logger.debug("tag type : {}",leafTagCatgs);
        for (TagTypeEntity leafTagCatg : leafTagCatgs) {
            logger.debug("{}",leafTagCatg.getLabelCatgNm());
            tagCatgids.add(leafTagCatg.getLabelCatgid());
        }
        return tagCatgids;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveLabelAssign(Integer markType){
        Map<Integer, List<Integer>> tagCacheMap = this.tagTmpMap.get();
        Set<Integer> labelIds = tagCacheMap.keySet();
        for (Integer labelId : labelIds) {
            List<Integer> markIdList = tagCacheMap.get(labelId);
            for (Integer markId : markIdList) {
//                0：自动标引
//                1：人工标引
                if(markType.equals(AUTO_MARK_IDX_OBJ_TYPE_TABLE)){
                    tagMarkDao.removeLabelTableAssign(labelId,markId);
                    tagMarkDao.saveLabelTableAssign(labelId,markId,MARK_IDX_TYPE_AUTO);
                }else if(markType.equals(AUTO_MARK_IDX_OBJ_TYPE_COLUM)){
                    tagMarkDao.removeLabelFldAssign(labelId,markId);
                    tagMarkDao.saveLabelFldAssign(labelId,markId,MARK_IDX_TYPE_AUTO);
                }

            }
        }
    }

    private List<Integer> markMatch(List markDataList,
                                    Integer tagId,
                                    Pattern pattern,
                                    boolean isFullMatch){
        List<Integer> tableIds = new ArrayList<>();

        for (Object dataEntity : markDataList) {
            String cnName = null;
            String enName = null;
            Integer markDataId = null;

            if(dataEntity instanceof DataTblEntity){
                DataTblEntity dataTblEntity = (DataTblEntity)dataEntity;
                cnName = dataTblEntity.getDataTblCnNm();
                enName = dataTblEntity.getDataTblPhysNm();
                markDataId = dataTblEntity.getDataTblid();
            }else if(dataEntity instanceof DataFldEntity){
                DataFldEntity dataFLdEntity = (DataFldEntity)dataEntity;
                cnName = dataFLdEntity.getFldCnNm();
                enName = dataFLdEntity.getFldPhysNm();
                markDataId = dataFLdEntity.getFldid();
            }else{
                continue;
            }

            if(StringUtils.isBlank(cnName)){
                cnName=enName;
            }
            Matcher cnMatcher = pattern.matcher(cnName);
            Matcher enMatcher = pattern.matcher(enName);
            if(isFullMatch){
                if(mockTest||cnMatcher.matches()||enMatcher.matches()){//命中.打
                    if(tagTmpMap.get().containsKey(tagId)){
                        if(!tagTmpMap.get().get(tagId).contains(markDataId)){
                            tableIds.add(markDataId);
                        }
                    }else
                        tableIds.add(markDataId);
                }
            }else{
                if(mockTest||cnMatcher.find()||enMatcher.find()){//命中.打
                    if(tagTmpMap.get().containsKey(tagId)){
                        if(!tagTmpMap.get().get(tagId).contains(markDataId)){
                            tableIds.add(markDataId);
                        }
                    }else
                        tableIds.add(markDataId);
                }
            }

        }

        return tableIds;
    }


    private List<Integer> checkRootTagCatgId(List<TagTypeEntity> allTagCatgs){
        List<Integer> rootTagCatgIds = new ArrayList<>();

        //获取根节点
        for (TagTypeEntity allTagCatg : allTagCatgs) {
            Integer superLabelCatgId = allTagCatg.getSuprLabelCatgid();
            if(superLabelCatgId==null||(new Integer("0").equals(superLabelCatgId))){
                Integer rootTagCatgId = allTagCatg.getLabelCatgid();
                rootTagCatgIds.add(rootTagCatgId);
            }
        }
        return rootTagCatgIds;
    }


    /**
     * 获取标签类别叶子节点列表
     */
    private List<TagTypeEntity> getLeafTagType(List<TagTypeEntity> allTagCatgs,
                                               Integer rootTagCatgId){
        if(rootTagCatgId==null){
            return null;
        }

        List<TagTypeEntity> resultTagTypeEntities = new ArrayList<>();

        for (TagTypeEntity tagCatg : allTagCatgs) {
            Integer superLabelCatgId = tagCatg.getSuprLabelCatgid();
            if(rootTagCatgId.equals(superLabelCatgId)){
                Integer labelCatgId = tagCatg.getLabelCatgid();

                List<TagTypeEntity> childrenList = getLeafTagType(allTagCatgs, labelCatgId);
                if(childrenList!=null&&childrenList.size()==0){//当前为叶子节点
                    resultTagTypeEntities.add(tagCatg);
                }else{
                    resultTagTypeEntities.addAll(childrenList);
                }
            }
        }

        return resultTagTypeEntities;
    }

    private String escapeExprSpecialWord(String keyword) {
        StringBuilder sb = new StringBuilder();
        if (!Strings.isNullOrEmpty(keyword)) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };

            char[] keyWordChars = keyword.toCharArray();
            for (char keyWordChar : keyWordChars) {
                for (String key : fbsArr) {
                    if(key.equals(String.valueOf(keyWordChar))){
                        sb.append("\\");
                    }
//                    if (keyword.contains(key)) {
//                        keyword = keyword.replace(key, "." + key);
//                    }
                }
                sb.append(keyWordChar);
            }



        }
        return sb.toString();
    }
}
