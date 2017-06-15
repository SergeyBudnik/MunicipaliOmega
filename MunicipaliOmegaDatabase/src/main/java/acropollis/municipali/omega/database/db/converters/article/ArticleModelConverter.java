package acropollis.municipali.omega.database.db.converters.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.TranslatedArticle;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.TranslatedQuestion;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.common.dto.article.question.answer.TranslatedAnswer;
import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.database.db.model.article.TranslatedArticleCategoriesModel;
import acropollis.municipali.omega.database.db.model.article.TranslatedArticleModel;
import acropollis.municipali.omega.database.db.model.article.question.QuestionModel;
import acropollis.municipali.omega.database.db.model.article.question.TranslatedQuestionModel;
import acropollis.municipali.omega.database.db.model.article.question.answer.AnswerModel;
import acropollis.municipali.omega.database.db.model.article.question.answer.TranslatedAnswerModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static acropollis.municipali.omega.common.utils.common.EncodingUtils.fromBase64;

public class ArticleModelConverter {
    public static Article convert(ArticleModel articleModel) {
        Article article = new Article();

        article.setId(articleModel.getId());
        article.setType(articleModel.getType());
        article.setTranslatedArticle(getTranslatedArticle(articleModel));
        article.setQuestions(
                articleModel
                        .getQuestions()
                        .stream()
                        .sorted(Comparator.comparingInt(QuestionModel::getOrder))
                        .map(ArticleModelConverter::convert)
                        .collect(Collectors.toList())
        );
        article.setSendPushOnRelease(articleModel.isSendPushOnRelease());
        article.setCreationDate(articleModel.getCreationDate());
        article.setReleaseDate(articleModel.getReleaseDate());
        article.setExpirationDate(articleModel.getExpirationDate());
        article.setLastUpdateDate(articleModel.getLastUpdateDate());

        return article;
    }

    private static Map<Language, TranslatedArticle> getTranslatedArticle(ArticleModel articleModel) {
        Map<Language, TranslatedArticle> translatedArticles = new HashMap<>();

        for (TranslatedArticleModel translatedArticleModel : articleModel.getTranslatedArticles()) {
            TranslatedArticle translatedArticle = new TranslatedArticle();

            translatedArticle.setTitle(fromBase64(translatedArticleModel.getTitle()));
            translatedArticle.setText(fromBase64(translatedArticleModel.getText()));
            translatedArticle.setCategories(
                    translatedArticleModel
                            .getCategories()
                            .stream()
                            .map(TranslatedArticleCategoriesModel::getText)
                            .collect(Collectors.toList())
            );

            translatedArticles.put(translatedArticleModel.getLanguage(), translatedArticle);
        }

        return translatedArticles;
    }

    private static Question convert(QuestionModel questionModel) {
        Question question = new Question();

        question.setId(questionModel.getId());
        question.setAnswerType(questionModel.getAnswerType());
        question.setShowResult(questionModel.isShowResult());
        question.setTranslatedQuestion(getTranslatedQuestion(questionModel));

        Map<Integer, AnswerModel> answerModels = questionModel
                .getAnswers()
                .stream()
                .collect(Collectors.toMap(AnswerModel::getOrder, Function.identity()));

        int maxAnswerIndex = questionModel
                .getAnswers()
                .stream()
                .map(AnswerModel::getOrder)
                .max(Integer::compareTo)
                .orElse(-1);

        question.setAnswers(new ArrayList<>());

        for (int answerIndex = 0; answerIndex <= maxAnswerIndex; answerIndex++) {
            AnswerModel answerModel = answerModels.get(answerIndex);

            question.getAnswers().add(
                    answerModel != null ?
                            ArticleModelConverter.convert(answerModel) :
                            null
            );
        }

        return question;
    }

    private static Map<Language, TranslatedQuestion> getTranslatedQuestion(QuestionModel questionModel) {
        Map<Language, TranslatedQuestion> translatedQuestions = new HashMap<>();

        for (TranslatedQuestionModel translatedQuestionModel : questionModel.getTranslatedQuestions()) {
            TranslatedQuestion translatedQuestion = new TranslatedQuestion();

            translatedQuestion.setText(fromBase64(translatedQuestionModel.getText()));

            translatedQuestions.put(translatedQuestionModel.getLanguage(), translatedQuestion);
        }

        return translatedQuestions;
    }

    private static Answer convert(AnswerModel answerModel) {
        Answer answer = new Answer();

        answer.setId(answerModel.getId());
        answer.setTranslatedAnswer(getTranslatedAnswer(answerModel));

        return answer;
    }

    private static Map<Language, TranslatedAnswer> getTranslatedAnswer(AnswerModel answerModel) {
        Map<Language, TranslatedAnswer> translatedAnswers = new HashMap<>();

        for (TranslatedAnswerModel translatedAnswerModel : answerModel.getTranslatedAnswers()) {
            TranslatedAnswer translatedAnswer = new TranslatedAnswer();

            translatedAnswer.setText(fromBase64(translatedAnswerModel.getText()));

            translatedAnswers.put(translatedAnswerModel.getLanguage(), translatedAnswer);
        }

        return translatedAnswers;
    }
}
