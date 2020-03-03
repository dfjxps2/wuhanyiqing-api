package io.dfjinxin.modules.storage.utils;

import io.dfjinxin.common.utils.DateTools;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.dto.DataPartDto;
import io.dfjinxin.modules.meta.entity.DataPartTypeEntity;
import io.dfjinxin.modules.meta.entity.DpEntity;
import io.dfjinxin.modules.storage.dto.PartDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StorageUtils {

    //作业发布删除标识(1:发布 0:删除)
    public static final String CZ_TYPE_PUB = "1";
    public static final String CZ_TYPE_DEL = "0";

    /**
     * 1	原始区
     * 2	处理区
     * 3	融合区
     * 4	集市区
     */
    public static final Integer Part_Type_YS = 1;
    public static final Integer Part_Type_RH = 3;

    public static final Integer JOB_TYPE_LJ = 2;//2：立即执行数据脱敏作业
    public static final Integer JOB_TYPE_ZQ = 3;//3：周期执行数据脱敏作业

    public static final String TAGPRE = "tag";

    public static boolean isMyPart(List<PartDto> partDtos, int partid) {
        for (PartDto d : partDtos) {
            if (d.getPartid() == partid) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMyPart2(List<DataPartDto> dataPartDtos, int partid) {
        for (DataPartDto d : dataPartDtos) {
            if (d.getPartid() == partid) {
                return true;
            }
        }
        return false;
    }

    public static Integer[] strToInteger(String[] vs) {
        if (null != vs && vs.length > 0) {
            int length = vs.length;
            Integer[] ti = new Integer[length];
            for (int i = 0; i < length; i++) {
                ti[i] = Integer.parseInt(vs[i].replace(TAGPRE, ""));
            }
            return ti;
        }
        return null;
    }

    public static List<String> idToIds(Integer id) {
        List<String> ids = new ArrayList<String>();
        ids.add("" + id);
        return ids;
    }


    //按分区查找
    public static String jointDp(String dbUsageCd, Date dpDt) {
        String dateStr = DateTools.toString(dpDt, "yyyy-MM-dd");
        String dpSql = "";
        if ("02".equals(dbUsageCd)) {//历史库
            //access_partition_year=2019/access_partition_month=07/access_partition_day=03
            //access_partition_year='2019' and access_partition_month='07' and access_partition_day='09';
            String[] dates = dateStr.split("-");
            dpSql = "access_partition_year='" + dates[0] + "' and access_partition_month='" + dates[1] + "' and access_partition_day='" + dates[2] + "'";
        } else {//清洗库
            //data_dt='2019-07-03'
            dpSql = "data_dt='" + dateStr + "'";
        }
        return dpSql;
    }
}
