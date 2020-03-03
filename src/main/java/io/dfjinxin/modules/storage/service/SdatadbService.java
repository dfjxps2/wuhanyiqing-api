package io.dfjinxin.modules.storage.service;

import java.util.List;
import java.util.Map;

public interface SdatadbService {

    List<Map<String, Object>> queryData(String sql);
}
