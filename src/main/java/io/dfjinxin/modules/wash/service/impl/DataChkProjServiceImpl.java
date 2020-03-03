package io.dfjinxin.modules.wash.service.impl;

import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.modules.udf.service.UdfService;
import io.dfjinxin.modules.wash.dto.ProgramForm;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;
import io.dfjinxin.modules.wash.utils.ExpressionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.DataChkProjDao;
import io.dfjinxin.modules.wash.entity.DataChkProjEntity;
import io.dfjinxin.modules.wash.service.DataChkProjService;
import org.springframework.transaction.annotation.Transactional;


@Service("dataChkProjService")
public class DataChkProjServiceImpl extends ServiceImpl<DataChkProjDao, DataChkProjEntity> implements DataChkProjService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UdfService udfService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataChkProjEntity> page = this.page(
                new Query<DataChkProjEntity>().getPage(params),
                new QueryWrapper<DataChkProjEntity>()
        );

        return new PageUtils(page);
    }

    public PageUtils query(ProgramForm programForm, Long tnmtid) {
        QueryWrapper<DataChkProjEntity> queryWrapper = new QueryWrapper<DataChkProjEntity>();
        queryWrapper.and(wrapper -> wrapper.eq("tnmtid", tnmtid).or().eq("Chk_Proj_Internal", 1));

        if (null != programForm.getEvalType()) {
            queryWrapper.eq("Chk_Proj_Eval_Type", programForm.getEvalType());
        }
        if (StringUtils.isNotEmpty(programForm.getChkProjNm())) {
            queryWrapper.like("Chk_Proj_Nm", programForm.getChkProjNm());
        }
        if (StringUtils.isNotEmpty(programForm.getJudgeName())) {
            queryWrapper.like("Chk_Proj_Eval_Expr", programForm.getJudgeName());
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", "" + programForm.getPageIndex());
        params.put("limit", "" + programForm.getPageSize());
        IPage<DataChkProjEntity> page = this.page(
                new Query<DataChkProjEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);

    }

    @Transactional(rollbackFor = Exception.class)
    public void saveEntity(DataChkProjEntity dataChkProjEntity) {

        DataChkProjEntity record = baseMapper.selectOneByName(dataChkProjEntity.getChkProjNm());
        //如果存在记录而且主键不相同
        if (null != record) {
            if (!record.getChkProjid().equals(dataChkProjEntity.getChkProjid())) {
                throw new RRException("保存失败：规则名称已存在");
            }
        }

        //新增 默认非内置规则 状态为1 代码默认为主键
        if (null == dataChkProjEntity.getChkProjid()) {
            dataChkProjEntity.setChkProjInternal(0);
            dataChkProjEntity.setChkProjStus(1);
            this.save(dataChkProjEntity);
            dataChkProjEntity.setChkProjCd(dataChkProjEntity.getChkProjid() + "");
        }
        //执行方式为javascript的表达式自动生成、其他方式将表达式赋予源码
        if (dataChkProjEntity.getChkProjEvalType().intValue() == 2) {
            dataChkProjEntity.setChkProjEvalExpr(ExpressionUtils.chkJudge(dataChkProjEntity.getChkProjid()));
            try {
                String source = dataChkProjEntity.getChkProjSource();
                udfService.readWriteUdf(dataChkProjEntity.getChkProjEvalExpr(), source, dataChkProjEntity.getChkProjEvalExpr(), "-INT");
                udfService.uploadUdf(dataChkProjEntity.getChkProjEvalExpr());
            } catch (RRException e) {
                throw new RRException(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RRException("操作失败" + e.getMessage());
            }
        } else {
            dataChkProjEntity.setChkProjSource(dataChkProjEntity.getChkProjEvalExpr());
        }
        this.updateById(dataChkProjEntity);

    }


}