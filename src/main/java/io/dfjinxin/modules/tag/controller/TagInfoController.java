package io.dfjinxin.modules.tag.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.service.DataFldMarkIdxService;
import io.dfjinxin.modules.meta.service.DataTblMarkIdxService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.tag.entity.TagInfoEntity;
import io.dfjinxin.modules.tag.service.TagInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 标签信息controller
 *
 * @author z.h.c
 * @date 2019-06-14 16:25:48
 */
@RestController
@RequestMapping("tag/info")
public class TagInfoController extends AbstractController {

    @Autowired
    private TagInfoService tagInfoService;
    @Autowired
    private DataFldMarkIdxService dataFldMarkIdxService;
    @Autowired
    private DataTblMarkIdxService dataTblMarkIdxService;

    private final static String DOUBLE_LINE = "__";


    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TagInfoEntity tagInfo) {

        Map<String, Object> map = new HashMap<>();
        tagInfo.setTnmtid(this.getTenantId().intValue());
        tagInfo.setIfReadOnly(0);
        tagInfo.setLabelNm(tagInfo.getLabelNm().replace(" ", ""));
        if (StringUtils.isNotBlank(tagInfo.getLabelCd())) {
            tagInfo.setLabelCd(tagInfo.getLabelCd().replace(" ", ""));
            if (tagInfo.getLabelCd().startsWith(DOUBLE_LINE)) {
                return R.error("标签代码不能以__开头!");
            }
        }
        map.put("id", tagInfoService.saveAndGetId(tagInfo));
        return R.ok(map);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TagInfoEntity tagInfo) {

        //同一类目下已存在相同名称的标签
        tagInfo.setTnmtid(this.getTenantId().intValue());
        tagInfo.setLabelNm(tagInfo.getLabelNm().replace(" ", ""));
        if (StringUtils.isNotBlank(tagInfo.getLabelCd())) {
            tagInfo.setLabelCd(tagInfo.getLabelCd().replace(" ", ""));
            if (tagInfo.getLabelCd().startsWith(DOUBLE_LINE)) {
                return R.error("标签代码不能以__开头!");
            }
        }
        TagInfoEntity sameLabelNm = tagInfoService.selectEntityOne(tagInfo);
        if (sameLabelNm != null && tagInfo.getLabelid().intValue() != sameLabelNm.getLabelid().intValue()) {
            return R.error(tagInfo.getLabelNm() + "：标签已存在!");
        }

        TagInfoEntity updateLabel = tagInfoService.selectEntityOneById(tagInfo.getLabelid());
        if (updateLabel == null) {
            return R.error(updateLabel.getLabelNm() + "：标签不存在!");
        }
        if (updateLabel.getIfReadOnly() != null && updateLabel.getIfReadOnly().intValue() == 1) {
            return R.error(tagInfo.getLabelNm() + "：标签不可修改!");
        }
        tagInfoService.updateById(tagInfo);
        return R.ok();
    }

    /**
     * 删除单个标签
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] labelIds) {

        if (labelIds.length == 0) {
            return R.error("标签id为空!");
        }

        List<Integer> ids = Arrays.asList(labelIds);
        TagInfoEntity tagInfoEntity = tagInfoService.selectEntityOneById(ids.get(0));
        if (tagInfoEntity == null) {
            return R.error(tagInfoEntity.getLabelNm() + "：标签不存在!");
        }

        String labelName = tagInfoEntity.getLabelNm();
        if (tagInfoEntity.getIfReadOnly() != null && tagInfoEntity.getIfReadOnly().intValue() == 1) {
            return R.error(labelName + "：为只读不可删除!");
        }

        int tabLabelCount = dataTblMarkIdxService.selectCountByLabelId(ids.get(0));
        if (tabLabelCount > 0) {
            return R.error(labelName + "：已被标引不可删除!");
        }
        int fldLabelCount = dataFldMarkIdxService.selectCountByLabelId(ids.get(0));
        if (fldLabelCount > 0) {
            return R.error(labelName + "：已被标引不可删除!");
        }

        tagInfoService.removeByIds(ids);
        return R.ok();
    }

}
