package cn.scut.zhihu.service;

import cn.scut.zhihu.dao.QuestionDao;
import cn.scut.zhihu.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

}
