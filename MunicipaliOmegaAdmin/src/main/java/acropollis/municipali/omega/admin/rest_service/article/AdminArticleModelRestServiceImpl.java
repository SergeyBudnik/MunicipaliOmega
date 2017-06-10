package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;
import acropollis.municipali.omega.database.db.service.answer.AnswerService;
import acropollis.municipali.omega.database.db.service.article.ArticleService;
import acropollis.municipali.omega.database.db.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Qualifier(Qualifiers.MODEL)
public class AdminArticleModelRestServiceImpl implements AdminArticleRestService {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @Transactional(readOnly = true)
    @Override
    public Collection<Article> getAllArticles(CustomerInfo user) {
        return articleService.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Article getArticle(CustomerInfo user, long id) {
        return articleService
                .get(id)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional(readOnly = true)
    @Override
    public byte [] getArticleIcon(CustomerInfo user, long id, int size) {
        return articleService
                .getIcon(id, size)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional(readOnly = true)
    @Override
    public byte [] getAnswerIcon(CustomerInfo user, long articleId, long questionId, long answerId, int size) {
        Question question = articleService
                .get(articleId)
                .flatMap(article -> questionService.get(article, questionId))
                .orElseThrow(() -> new HttpEntityNotFoundException(""));

        return answerService
                .getIcon(question, answerId, size)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional
    @Override
    public long createArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        return articleService.create(articleWithIcon);
    }

    @Transactional
    @Override
    public void updateArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        try {
            articleService.update(articleWithIcon);
        } catch (EntityDoesNotExist e) {
            throw new HttpEntityNotFoundException("");
        }
    }

    @Transactional
    @Override
    public void deleteArticle(CustomerInfo user, long id) {
        try {
            articleService.delete(id);
        } catch (EntityDoesNotExist e) {
            throw new HttpEntityNotFoundException("");
        }
    }
}
