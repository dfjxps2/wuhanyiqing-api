package io.dfjinxin.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/3/31 18:32
 * @Version: 1.0
 */
public class Key2Name {
//          '${user}'= 'jh-yy'  取  '江汉区'
//            '${user}'= 'dhkf-zhy'  取  '东湖新技术开发区'
//            '${user}'= 'xz-txf'  取  '新洲区'
//            '${user}'= 'xd-dl'  取  '蔡甸区'
//            '${user}'= 'dhfj-zxh'  取  '东湖生态旅游风景区'
//            '${user}'= 'qs-wj'  取  '青山区'
//            '${user}'= 'ja-hql'  取  '江岸区'
//            '${user}'= 'hy-zsn'  取  '汉阳区'
//            '${user}'= 'jx-hzy'  取  '江夏区'
//            '${user}'= 'dxh-lif'  取  '东西湖区'
//            '${user}'= 'hn-lhc'  取  '汉南区'
//            '${user}'= 'hp-wjl'  取  '黄陂区'
//            '${user}'= 'hs-zyq'  取  '洪山区'
//            '${user}'= 'qk-zh'  取  '硚口区'
//            '${user}'= 'wc-zgh'  取  '武昌区'

    public static String getNameByKey(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("jh-yy", "新洲区");
        map.put("dhkf-zhy", "湖新技术开发区");
        map.put("xz-txf", "新洲区");
        map.put("xd-dl", "蔡甸区");
        map.put("dhfj-zxh", "东湖生态旅游风景区");
        map.put("qs-wj", "青山区");
        map.put("ja-hql", "江岸区");
        map.put("hy-zsn", "汉阳区");
        map.put("jx-hzy", "江夏区");
        map.put("dxh-lif", "东西湖区");
        map.put("hn-lhc", "汉南区");
        map.put("hp-wjl", "黄陂区");
        map.put("hs-zyq", "洪山区");
        map.put("qk-zh", "硚口区");
        map.put("wc-zgh", "武昌区");
        return map.get(key);
    }
}
