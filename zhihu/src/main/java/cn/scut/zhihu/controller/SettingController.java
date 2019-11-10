package cn.scut.zhihu.controller;

import cn.scut.zhihu.service.ZhihuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * User: yinkai
 * Date: 2019-11-09
 * Time: 11:25
 */
@Controller
public class SettingController {
    @Autowired
    ZhihuService zhihuService;

    @RequestMapping(path = {"/setting"}, method = {RequestMethod.GET})
    @ResponseBody
    public String setting(HttpSession httpSession) {
        return "Setting OK. " + zhihuService.getMessage(1);
    }
}
