package io.dfjinxin.modules.storage.service.impl;

import io.dfjinxin.datasource.annotation.DataSource;
import io.dfjinxin.modules.storage.dao.SdataDbDao;
import io.dfjinxin.modules.storage.service.SdatadbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SdatadbServiceImpl implements SdatadbService {

    @Override
    public List<Map<String, Object>> queryData(String sql) {
        return sdataDbDao.executeSql(sql);
    }

    @Autowired
    private SdataDbDao sdataDbDao;
}
