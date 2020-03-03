package io.dfjinxin.modules.wash.service.impl;

import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.modules.udf.service.UdfService;
import io.dfjinxin.modules.wash.utils.ExpressionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.DataWashCmpuDao;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;
import io.dfjinxin.modules.wash.service.DataWashCmpuService;
import org.springframework.transaction.annotation.Transactional;


@Service("dataWashCmpuService")
public class DataWashCmpuServiceImpl extends ServiceImpl<DataWashCmpuDao, DataWashCmpuEntity> implements DataWashCmpuService {

    @Autowired
    private UdfService udfService;

    @Autowired
    private DataWashCmpuDao dataWashCmpuDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataWashCmpuEntity> page = this.page(
                new Query<DataWashCmpuEntity>().getPage(params),
                new QueryWrapper<DataWashCmpuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryList(Map<String, Object> params) {
        IPage<DataWashCmpuEntity> page = baseMapper.queryList(
                new Query<DataWashCmpuEntity>().getPage(params),
                params
        );
        return new PageUtils(page);
    }

    @Override
    public int getInfo(String name, Integer id,long tnmtid) {
        return dataWashCmpuDao.getInfo(name,id,tnmtid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveInfo(DataWashCmpuEntity dataWashCmpu) throws IOException, InterruptedException {
        if(this.getInfo(dataWashCmpu.getDataWashCmpuNm(),null,dataWashCmpu.getTnmtid())>0){
            throw new RRException("名称已存在，请修改后再提交！");
        }
        if(StringUtils.isBlank(dataWashCmpu.getDataWashCmpuTransExpr())){
            dataWashCmpu.setDataWashCmpuTransExpr("");//清洗表达式为空 则设置''
        }
        this.save(dataWashCmpu);
        dataWashCmpu.setDataWashCmpuCd(dataWashCmpu.getDataWashCmpuid()+"");
        if(dataWashCmpu.getDataWashCmpuEvalType()==2){
            //清洗源码为空 则清洗表达式设置为''
            if(StringUtils.isBlank(dataWashCmpu.getDataWashCmpuTransSource())){
                dataWashCmpu.setDataWashCmpuTransExpr("");//清洗表达式为空 则设置''
            }else{
                String transName=ExpressionUtils.washTransition(dataWashCmpu.getDataWashCmpuid());
                dataWashCmpu.setDataWashCmpuTransExpr(transName);
            }
            String judgeName=ExpressionUtils.washJudge(dataWashCmpu.getDataWashCmpuid());
            dataWashCmpu.setDataWashCmpuJudgeExpr(judgeName);
        }
        this.updateInfo(dataWashCmpu);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(DataWashCmpuEntity dataWashCmpu) throws IOException, InterruptedException {
        if(this.getInfo(dataWashCmpu.getDataWashCmpuNm(),dataWashCmpu.getDataWashCmpuid(),dataWashCmpu.getTnmtid())>0){
            throw new RRException("名称已存在，请修改后再提交！");
        }
        if(StringUtils.isBlank(dataWashCmpu.getDataWashCmpuTransExpr())){
            dataWashCmpu.setDataWashCmpuTransExpr("");//清洗表达式为空 则设置''
        }
        if(dataWashCmpu.getDataWashCmpuEvalType()==2){
            //清洗源码为空 则清洗表达式设置为''
            if(StringUtils.isBlank(dataWashCmpu.getDataWashCmpuTransSource())){
                dataWashCmpu.setDataWashCmpuTransExpr("");//清洗表达式为空 则设置''
            }else{
                String transName=ExpressionUtils.washTransition(dataWashCmpu.getDataWashCmpuid());
                dataWashCmpu.setDataWashCmpuTransExpr(transName);
            }
            this.jacAndJava(dataWashCmpu);
        }
        this.updateById(dataWashCmpu);

    }

    @Transactional(rollbackFor = Exception.class)
    public void jacAndJava(DataWashCmpuEntity dataWashCmpu) throws IOException, InterruptedException {
        if(dataWashCmpu.getDataWashCmpuEvalType()==2){
            String judgeName=ExpressionUtils.washJudge(dataWashCmpu.getDataWashCmpuid());
            String judgeSource = dataWashCmpu.getDataWashCmpuJudgeSouce();
            udfService.readWriteUdf(judgeName, judgeSource, judgeName, "-INT");
            udfService.uploadUdf(judgeName);
            if(!StringUtils.isBlank(dataWashCmpu.getDataWashCmpuTransSource())){
                String transSource = dataWashCmpu.getDataWashCmpuTransSource();
                String transName=ExpressionUtils.washTransition(dataWashCmpu.getDataWashCmpuid());
                udfService.readWriteUdf(transName, transSource, transName, "-STR");
                udfService.uploadUdf(transName);
            }
        }
    }

    @Override
    public List<DataWashCmpuEntity> queryDataWashCmpuByDataWashCmpuType(Integer dataWashCmpuType){
        return this.baseMapper.queryDataWashCmpuByDataWashCmpuType(dataWashCmpuType);
    }

}