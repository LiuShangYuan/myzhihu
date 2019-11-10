package cn.scut.zhihu.model;

import java.util.HashMap;
import java.util.Map;

/**
 * User: yinkai
 * Date: 2019-11-09
 * Time: 11:08
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<String, Object>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
