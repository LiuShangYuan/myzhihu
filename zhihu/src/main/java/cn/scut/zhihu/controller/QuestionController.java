package cn.scut.zhihu.controller;

import cn.scut.zhihu.model.HostHolder;
import cn.scut.zhihu.model.Question;
import cn.scut.zhihu.service.QuestionService;
import cn.scut.zhihu.service.UserService;
import cn.scut.zhihu.util.ZhihuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * User: yinkai
 * Date: 2019-11-10
 * Time: 11:35
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetaail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.selectById(qid);
        model.addAttribute("question", question);
        return "detail";
    }

    @RequestMapping(value = "/question/add", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {

        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCommentCount(0);
            question.setCreatedDate(new Date());
            if (hostHolder.getUser() == null) {
                question.setUserId(ZhihuUtil.ANONYMOUS_USERID);
            } else {
                question.setUserId(hostHolder.getUser().getId());
            }

            if (questionService.addQuestion(question) > 0) {
                return ZhihuUtil.getJSONString(0, "添加问题成功");
            } else {
                return ZhihuUtil.getJSONString(1, "添加问题失败");
            }
        } catch (Exception e) {
            logger.error("添加问题失败" + e.getMessage());
            return ZhihuUtil.getJSONString(1, "添加问题失败");
        }

    }

}
