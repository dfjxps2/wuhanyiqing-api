package io.dfjinxin.modules.tag.controller;

import io.dfjinxin.common.annotation.SysLog;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.common.validator.ValidatorUtils;
import io.dfjinxin.modules.meta.service.DataFldMarkIdxService;
import io.dfjinxin.modules.meta.service.DataTblMarkIdxService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.tag.entity.TagInfoEntity;
import io.dfjinxin.modules.tag.entity.TagTypeEntity;
import io.dfjinxin.modules.tag.service.TagInfoService;
import io.dfjinxin.modules.tag.service.TagTypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Description: 标签类目管理Controller
 * @Author: z.h.c
 * @CreateDate: 2019/6/13 14:01
 * @Version: 1.0
 */

@RestController
@RequestMapping("/tag/type")
public class TagTypeController extends AbstractController {

    @Autowired
    private TagTypeService tagTypeService;
    @Autowired
    private TagInfoService tagInfoService;
    @Autowired
    private DataFldMarkIdxService dataFldMarkIdxService;
    @Autowired
    private DataTblMarkIdxService dataTblMarkIdxService;

    private final static String DOUBLE_LINE = "__";

    /**
     * 新增标签类型
     */
    @SysLog("保存标签类型")
    @PostMapping("/save")
    public R save(@RequestBody TagTypeEntity entity) {
        ValidatorUtils.validateEntity(entity);
        //添加租id
        entity.setTnmtid(this.getTenantId().intValue());
        entity.setIfReadOnly(0);
        entity.setLabelCatgNm(entity.getLabelCatgNm().replace(" ", ""));
        if (StringUtils.isNotBlank(entity.getLabelCatgCd())) {
            entity.setLabelCatgCd(entity.getLabelCatgCd().replace(" ", ""));
            if (entity.getLabelCatgCd().startsWith(DOUBLE_LINE)) {
                return R.error("标签类目代码不能以__开头!");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", tagTypeService.saveAndGetId(entity));
        return R.ok(map);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TagTypeEntity tagType) {

        tagType.setTnmtid(this.getTenantId().intValue());
        tagType.setLabelCatgNm(tagType.getLabelCatgNm().replace(" ", ""));
        if (StringUtils.isNotBlank(tagType.getLabelCatgCd())) {
            tagType.setLabelCatgCd(tagType.getLabelCatgCd().replace(" ", ""));
            if (tagType.getLabelCatgCd().startsWith(DOUBLE_LINE)) {
                return R.error("标签类目代码不能以__开头!");
            }
        }

        TagTypeEntity sameNameTag = tagTypeService.selectEntityByWrapper(tagType);
        if (sameNameTag != null && tagType.getLabelCatgid().intValue() != sameNameTag.getLabelCatgid().intValue()) {
            return R.error(tagType.getLabelCatgNm() + "：类目已存在!");
        }

        TagTypeEntity entity = tagTypeService.selectEntityOne(tagType.getLabelCatgid());
        if (entity == null) {
            return R.error(entity.getLabelCatgNm() + "：类目不存在!");

        }
        if (entity.getIfReadOnly() != null && entity.getIfReadOnly().intValue() == 1) {
            return R.error(entity.getLabelCatgNm() + "：类目不可修改!");
        }

        tagTypeService.updateById(tagType);
        return R.ok();
    }

    /**
     * 删除目录及子目录和其标签
     * modify by zhc 6.23
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Map<String, Object> params) {

        if (params.isEmpty()) {
            return R.error("标签类目id为空!");
        }
        /**
         * tagRootId:所选要删除目录的id，
         * 1、该目录下如果有其中一个标签被标引，该目录不可被删除
         * 2、该目录为只读则不可删除
         */
        Integer tagRootid = (Integer) (params.get("tagRootId"));
        TagTypeEntity entity = tagTypeService.selectEntityOne(tagRootid);
        if (entity == null) {
            return R.error(entity.getLabelCatgNm() + "：类目不存在!");
        }

        if (entity.getIfReadOnly() != null && entity.getIfReadOnly().intValue() == 1) {
            return R.error(entity.getLabelCatgNm() + "：类目为只读状态不可删除!");
        }

        //类目id集合
        List<Integer> tagIdList = (List) (params.get("tagIdList"));
        // 所属所选类目的标签id集合
        List<Integer> labelIdList = (List) params.get("labelIdList");
        int fldLabelCount = dataFldMarkIdxService.selectFldLabCountByLabelIds(labelIdList);
        if (fldLabelCount > 0) {
            return R.error(entity.getLabelCatgNm() + "：类目下的标签已被标引不可删除!");
        }

        int tabLabelCount = dataTblMarkIdxService.selectTblLabCountByLabelIds(labelIdList);
        if (tabLabelCount > 0) {
            return R.error(entity.getLabelCatgNm() + "：类目下的标签已被标引不可删除!");
        }
        tagTypeService.delTagAndLabel(tagIdList, labelIdList);
        return R.ok();
    }

    /**
     * 全部树型列表
     */
    @RequestMapping("/queryAll/{labelType}")
    public R list(@PathVariable("labelType") String labelType) {

        Long tenantId = this.getTenantId();

        List<TagTypeEntity> tagList = tagTypeService.queryTagTypeList(tenantId);//标签类别数据
        List infoList = new ArrayList();

        if ("TABLE".equals(labelType)) {
            infoList = tagInfoService.queryTblTagInfoList(tenantId);
        } else if ("FIELD".equals(labelType)) {
            infoList = tagInfoService.queryFldTagInfoList(tenantId);
        } else if ("RULE".equals(labelType)) {
            infoList = tagInfoService.queryRuleInfoList(tenantId);
        } else {//全部
            List<TagInfoEntity> filedInfoList = tagInfoService.queryFldTagInfoList(tenantId);
            List<TagInfoEntity> tblInfoList = tagInfoService.queryTblTagInfoList(tenantId);
            infoList = this.mergeTblAndField(filedInfoList, tblInfoList);
        }

        tagList = this.sumTypeCount(tagList, infoList);

        Map resultMap = new HashMap();
        resultMap.put("dataType", tagList);//标签类目
        resultMap.put("dataInfo", infoList);//标签信息
        return R.ok().put("data", resultMap);
    }


    private List<TagInfoEntity> mergeTblAndField(List<TagInfoEntity> filedInfoList, List<TagInfoEntity> tblInfoList) {
        Map<Integer, TagInfoEntity> tagInfoTMp = new LinkedHashMap<>();
        if (filedInfoList != null && filedInfoList.size() > 0) {
            for (TagInfoEntity tagInfoEntity : filedInfoList) {
                Integer labelId = tagInfoEntity.getLabelid();
                tagInfoTMp.put(labelId, tagInfoEntity);
            }

            for (TagInfoEntity tagInfoEntity : tblInfoList) {
                Integer labelId = tagInfoEntity.getLabelid();
                if (tagInfoTMp.containsKey(labelId)) {
                    Integer oldTimes = tagInfoTMp.get(labelId).getUsedTimes();
                    tagInfoTMp.get(labelId).setUsedTimes(oldTimes + tagInfoEntity.getUsedTimes());
                } else {
                    tagInfoTMp.put(labelId, tagInfoEntity);
                }
            }

            return new ArrayList<>(tagInfoTMp.values());
        } else {
            return tblInfoList;
        }
    }

    private List<TagTypeEntity> sumTypeCount(List<TagTypeEntity> typeList, List<TagInfoEntity> infoList) {

        Map<Integer, TagTypeEntity> typeMapCache = new LinkedHashMap<>();
        for (TagTypeEntity tagTypeEntity : typeList) {
            Integer typeId = tagTypeEntity.getLabelCatgid();
            typeMapCache.put(typeId, tagTypeEntity);
            tagTypeEntity.setUsedTimes(0);
        }

        for (TagInfoEntity tagInfoEntity : infoList) {
            Integer parentId = tagInfoEntity.getLabelCatgid();
            this.forEachTypeTree(parentId, tagInfoEntity.getUsedTimes(), typeMapCache);
        }

        List<TagTypeEntity> list = new ArrayList<TagTypeEntity>(typeMapCache.values());

        return list;
    }

    private void forEachTypeTree(Integer typeId, Integer addCount, Map<Integer, TagTypeEntity> typeMapCache) {
        if (typeMapCache.containsKey(typeId)) {
            TagTypeEntity typeEntity = typeMapCache.get(typeId);
            Integer typeUsedTimes = typeMapCache.get(typeId).getUsedTimes();
            if (typeUsedTimes != null) {
                typeEntity.setUsedTimes(typeUsedTimes + addCount);
            } else {
                typeEntity.setUsedTimes(addCount);
            }

            Integer parentId = typeEntity.getSuprLabelCatgid();
            if (parentId != null) {
                this.forEachTypeTree(parentId, addCount, typeMapCache);
            }
        }
    }
}
