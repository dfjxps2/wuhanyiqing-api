package io.dfjinxin.modules.wash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.wash.dao.DataWashCmpuDao;
import io.dfjinxin.modules.wash.dto.ProgramForm;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;
import io.dfjinxin.modules.wash.service.DataWashCmpuService;
import io.dfjinxin.modules.wash.service.WashRuleProgramService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WashRuleProgramServiceImpl extends ServiceImpl<DataWashCmpuDao, DataWashCmpuEntity> implements WashRuleProgramService {

    public PageUtils query(ProgramForm programForm){
        QueryWrapper<DataWashCmpuEntity> queryWrapper = new QueryWrapper<DataWashCmpuEntity>();
        queryWrapper.eq("Data_Wash_Cmpu_Type", 0);//0：数据清洗
        queryWrapper.and(w -> w.eq("tnmtid", programForm.getTnmtid()).or().isNull("tnmtid"));
        if(null != programForm.getEvalType()){  queryWrapper.eq("Data_Wash_Cmpu_Eval_Type", programForm.getEvalType()); }
        if(null != programForm.getDataWashCmpuNm()){    queryWrapper.like("Data_Wash_Cmpu_Nm", programForm.getDataWashCmpuNm());  }
        if(null != programForm.getJudgeName()){    queryWrapper.like("Data_Wash_Cmpu_Judge_Expr", programForm.getJudgeName());  }
        if(null != programForm.getTransName()){    queryWrapper.like("Data_Wash_Cmpu_Trans_Expr", programForm.getTransName());  }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", "" + programForm.getPageIndex());
        params.put("limit", "" + programForm.getPageSize());
        IPage<DataWashCmpuEntity> page = this.page(
                new Query<DataWashCmpuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);

    }

    @Autowired
    private DataWashCmpuService dataWashCmpuService;
}
