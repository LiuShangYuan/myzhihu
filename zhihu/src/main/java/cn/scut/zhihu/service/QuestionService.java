package cn.scut.zhihu.service;

import cn.scut.zhihu.dao.QuestionDao;
import cn.scut.zhihu.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * User: yinkai
 * Date: 2019-11-09
 * Time: 10:35
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    @Autowired
    SensitiveService sensitiveService;

    public int addQuestion(Question question) {
        //防止xss注入
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        int i = questionDao.addQuestion(question);
        return i;
    }


    public Question selectById(int id) {
        return questionDao.selectById(id);
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

}
