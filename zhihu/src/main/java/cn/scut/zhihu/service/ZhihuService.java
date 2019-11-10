package cn.scut.zhihu.service;

import org.springframework.stereotype.Service;

/**
 * User: yinkai
 * Date: 2019-11-09
 * Time: 11:07
 */
@Service
public class ZhihuService {
    public String getMessage(int userId) {
        return "Hello Message:" + String.valueOf(userId);
    }
}
