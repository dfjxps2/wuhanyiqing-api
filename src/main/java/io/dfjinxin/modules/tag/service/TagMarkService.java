package io.dfjinxin.modules.tag.service;

import java.util.List;
import java.util.Map;

public interface TagMarkService {
    Map<Integer, List<Map<String, String>>> tableAutoMark(List<String> tableIds, Long tnmtid);

    Map<Integer, List<Map<String, String>>> fieldAutoMark(List<String> fieldIds, Long tnmtid);
}
