package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.TranslatedArticle;
import acropollis.municipali.omega.common.dto.article.question.QuestionWithIcon;
import acropollis.municipali.omega.common.dto.article.question.TranslatedQuestion;
import acropollis.municipali.omega.common.dto.article.question.answer.AnswerWithIcon;
import acropollis.municipali.omega.common.dto.article.question.answer.TranslatedAnswer;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.common.exceptions.EntityNotValidException;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Function;

import static com.google.common.base.Strings.isNullOrEmpty;

@Qualifier(Qualifiers.REQUEST_VALIDATION)
@Service
public class AdminArticleRequestValidationRestServiceImpl implements AdminArticleRestService {
    @Qualifier(Qualifiers.REQUEST_PROCESSING)
    @Autowired
    private AdminArticleRestService adminArticleRestService;

    @Override
    public Collection<Article> getAllArticles(CustomerInfo user) {
        return adminArticleRestService.getAllArticles(user);
    }

    @Override
    public Article getArticle(CustomerInfo user, long id) {
        return adminArticleRestService.getArticle(user, id);
    }

    @Override
    public byte [] getArticleIcon(CustomerInfo user, long id, int size) {
        return adminArticleRestService.getArticleIcon(user, id, size);
    }

    @Override
    public byte [] getAnswerIcon(CustomerInfo user, long articleId, long questionId, long answerId, int size) {
        return adminArticleRestService.getAnswerIcon(user, articleId, questionId, answerId, size);
    }

    @Override
    public long createArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        checkArticleValid(articleWithIcon);

        return adminArticleRestService.createArticle(user, articleWithIcon);
    }

    @Override
    public void updateArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        checkArticleValid(articleWithIcon);

        adminArticleRestService.updateArticle(user, articleWithIcon);
    }

    @Override
    public void deleteArticle(CustomerInfo user, long id) {
        adminArticleRestService.deleteArticle(user, id);
    }

    private void checkArticleValid(ArticleWithIcon article) {
        if (article.getType() == null) {
            throw new EntityNotValidException("'article.type' can't be null");
        }

        if (article.getTranslatedArticle() == null) {
            throw new EntityNotValidException("'article.translatedArticle' can't be null");
        }

        if (article.getQuestions() == null) {
            throw new EntityNotValidException("'article.question' can't be null");
        }

        article.getTranslatedArticle().forEach((language, translatedArticle) ->
            checkTranslatedArticleValid(translatedArticle, language)
        );

        article.getQuestions().forEach(this::checkQuestionValid);
    }

    private void checkTranslatedArticleValid(TranslatedArticle translatedArticle, Language language) {
        if (translatedArticle == null) {
            throw new EntityNotValidException("'article.translatedArticle[%s]' can't be null", language);
        }

        if (isNullOrEmpty(translatedArticle.getTitle())) {
            throw new EntityNotValidException("'article.translatedArticle[%s].title' can't be null", language);
        }

        if (isNullOrEmpty(translatedArticle.getText())) {
            throw new EntityNotValidException("'article.translatedArticle[%s].text' can't be null", language);
        }

        for (String category : translatedArticle.getCategories()) {
            if (isNullOrEmpty(category)) {
                throw new EntityNotValidException("'article.translatedArticle[%s].category' can't be null", language);
            }
        }
    }

    private void checkQuestionValid(QuestionWithIcon question) {
        if (question == null) {
            throw new EntityNotValidException("'article.question' can't be null");
        }

        if (question.getTranslatedQuestion() == null) {
            throw new EntityNotValidException("'article.question.translatedQuestion' can't be null");
        }

        if (question.getAnswerType() == null) {
            throw new EntityNotValidException("'article.question.answerType' can't be null");
        }

        if (question.getAnswers() == null) {
            throw new EntityNotValidException("'article.question.answers' can't be null");
        }

        question.getTranslatedQuestion().forEach((language, translatedQuestion) ->
                checkTranslatedQuestionValid(translatedQuestion, language)
        );

        question.getAnswers().forEach(this::checkAnswerValid);

        switch (question.getAnswerType()) {
            case FIVE_POINTS:
                checkFivePointsQuestionValid(question);
                break;
            case THREE_VARIANTS:
                checkThreeVariantsQuestionsValid(question);
                break;
            case DYCHOTOMOUS:
                checkDychotomousQuestionValid(question);
                break;
        }
    }

    private void checkTranslatedQuestionValid(TranslatedQuestion translatedQuestion, Language language) {
        if (translatedQuestion == null) {
            throw new EntityNotValidException("'article.question.translatedQuestion[%s]' can't be null", language);
        }

        if (isNullOrEmpty(translatedQuestion.getText())) {
            throw new EntityNotValidException("'article.question.translatedQuestion[%s].text' can't be null", language);
        }
    }

    private void checkAnswerValid(AnswerWithIcon answer) {
        if (answer == null) {
            throw new EntityNotValidException("'article.question.answer' can't be null");
        }

        if (answer.getTranslatedAnswer() == null) {
            throw new EntityNotValidException("'article.question.answer.translatedAnswer' can't be null");
        }

        answer.getTranslatedAnswer().forEach((language, translatedAnswer) ->
                checkTranslatedAnswerValid(translatedAnswer, language)
        );
    }

    private void checkTranslatedAnswerValid(TranslatedAnswer translatedAnswer, Language language) {
        if (translatedAnswer == null) {
            throw new EntityNotValidException("'article.question.answer.translatedAnswer[%s]' can't be null", language);
        }

        if (isNullOrEmpty(translatedAnswer.getText())) {
            throw new EntityNotValidException("'article.question.answer.translatedAnswer[%s].text' can't be null", language);
        }
    }

    private void checkFivePointsQuestionValid(QuestionWithIcon question) {
        if (question.getAnswers().size() != 5) {
            throw new EntityNotValidException("'article.question' of 'five points' type must have 5 non-null answers");
        }

        Function<Integer, Boolean> isAnswerEmpty = index -> question.getAnswers().get(index).getTranslatedAnswer().isEmpty();
        Function<Integer, Boolean> isAnswerNonEmpty = index -> !isAnswerEmpty.apply(index);

        if (
                isAnswerEmpty.apply(0) ||
                isAnswerNonEmpty.apply(1) ||
                isAnswerNonEmpty.apply(2) ||
                isAnswerNonEmpty.apply(3) ||
                isAnswerEmpty.apply(4)
        ) {
            throw new EntityNotValidException(
                    "'article.question.answer[0, 4]' should be non-null and not empty. " +
                    "'article.question.answer[1, 2, 3]' should be non-null and empty."
            );
        }
    }

    private void checkDychotomousQuestionValid(QuestionWithIcon question) {
        if (question.getAnswers().size() != 2) {
            throw new EntityNotValidException("'article.question' of 'dychotomous' type must have 2 non-null answers");
        }

        Function<Integer, Boolean> isAnswerEmpty = index -> question.getAnswers().get(index).getTranslatedAnswer().isEmpty();

        if (isAnswerEmpty.apply(0) || isAnswerEmpty.apply(1)) {
            throw new EntityNotValidException("'article.question.answer's of 'dychotomous' type must have 2 non-null answers");
        }
    }

    private void checkThreeVariantsQuestionsValid(QuestionWithIcon question) {
        if (question.getAnswers().size() != 3) {
            throw new EntityNotValidException("'article.question' of 'three variants' type must have 3 non-null answers");
        }

        Function<Integer, Boolean> isAnswerEmpty = index -> question.getAnswers().get(index).getTranslatedAnswer().isEmpty();

        if (isAnswerEmpty.apply(0) || isAnswerEmpty.apply(1) || isAnswerEmpty.apply(2)) {
            throw new EntityNotValidException("'article.question.answer's of 'three variants' type must have 3 non-null answers");
        }
    }
}