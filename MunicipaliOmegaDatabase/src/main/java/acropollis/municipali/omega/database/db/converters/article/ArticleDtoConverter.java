package acropollis.municipali.omega.database.db.converters.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.database.db.model.article.TranslatedArticleCategoriesModel;
import acropollis.municipali.omega.database.db.model.article.TranslatedArticleModel;
import acropollis.municipali.omega.database.db.model.article.question.QuestionModel;
import acropollis.municipali.omega.database.db.model.article.question.TranslatedQuestionModel;
import acropollis.municipali.omega.database.db.model.article.question.answer.AnswerModel;
import acropollis.municipali.omega.database.db.model.article.question.answer.TranslatedAnswerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleDtoConverter {
    public static ArticleModel convert(Article article, boolean isDeleted) {
        ArticleModel articleModel = new ArticleModel();

        articleModel.setId(article.getId());
        articleModel.setType(article.getType());
        articleModel.setTranslatedArticles(getTranslatedArticle(article, articleModel));
        articleModel.setQuestions(new ArrayList<>()); {
            int questionOrder = 0;

            for (Question question : article.getQuestions()) {
                articleModel.getQuestions().add(convert(question, articleModel, questionOrder++));
            }
        }
        articleModel.setSendPushOnRelease(article.isSendPushOnRelease());
        articleModel.setCreationDate(article.getCreationDate());
        articleModel.setReleaseDate(article.getReleaseDate());
        articleModel.setExpirationDate(article.getExpirationDate());
        articleModel.setLastUpdateDate(article.getLastUpdateDate());
        articleModel.setDeleted(isDeleted);

        return articleModel;
    }

    private static List<TranslatedArticleModel> getTranslatedArticle(Article article, ArticleModel articleModel) {
        List<TranslatedArticleModel> translatedArticlesModels = new ArrayList<>();

        article.getTranslatedArticle().forEach((language, translatedArticle) -> {
            TranslatedArticleModel translatedArticleModel = new TranslatedArticleModel();

            translatedArticleModel.setId(null);
            translatedArticleModel.setArticle(articleModel);
            translatedArticleModel.setLanguage(language);
            translatedArticleModel.setTitle(translatedArticle.getTitle());
            translatedArticleModel.setText(translatedArticle.getText());
            translatedArticleModel.setCategories(
                    translatedArticle
                            .getCategories()
                            .stream()
                            .map(it -> {
                                TranslatedArticleCategoriesModel translatedArticleCategoriesModel = new TranslatedArticleCategoriesModel();

                                translatedArticleCategoriesModel.setTranslatedArticle(translatedArticleModel);
                                translatedArticleCategoriesModel.setText(it);

                                return translatedArticleCategoriesModel;
                            })
                            .collect(Collectors.toList())
            );

            translatedArticlesModels.add(translatedArticleModel);
        });

        return translatedArticlesModels;
    }

    public static QuestionModel convert(Question question, ArticleModel articleModel, int questionOrder) {
        QuestionModel questionModel = new QuestionModel();

        questionModel.setId(question.getId());
        questionModel.setArticle(articleModel);
        questionModel.setOrder(questionOrder);
        questionModel.setAnswerType(question.getAnswerType());
        questionModel.setShowResult(question.isShowResult());
        questionModel.setTranslatedQuestions(getTranslatedQuestion(question, questionModel));
        questionModel.setAnswers(new ArrayList<>()); {
            int answerOrder = 0;

            for (Answer answer : question.getAnswers()) {
                if (answer != null) {
                    questionModel.getAnswers().add(convert(answer, questionModel, answerOrder));
                }

                answerOrder++;
            }
        }

        return questionModel;
    }

    private static List<TranslatedQuestionModel> getTranslatedQuestion(Question question, QuestionModel questionModel) {
        List<TranslatedQuestionModel> translatedQuestionsModels = new ArrayList<>();

        question.getTranslatedQuestion().forEach((language, translatedQuestion) -> {
            TranslatedQuestionModel translatedQuestionModel = new TranslatedQuestionModel();

            translatedQuestionModel.setId(null);
            translatedQuestionModel.setQuestion(questionModel);
            translatedQuestionModel.setLanguage(language);
            translatedQuestionModel.setText(translatedQuestion.getText());

            translatedQuestionsModels.add(translatedQuestionModel);
        });

        return translatedQuestionsModels;
    }

    public static AnswerModel convert(Answer answer, QuestionModel questionModel, int order) {
        AnswerModel answerModel = new AnswerModel();

        answerModel.setId(answer.getId());
        answerModel.setTranslatedAnswers(getTranslatedAnswer(answer, answerModel));
        answerModel.setQuestion(questionModel);
        answerModel.setOrder(order);

        return answerModel;
    }

    private static List<TranslatedAnswerModel> getTranslatedAnswer(Answer answer, AnswerModel answerModel) {
        List<TranslatedAnswerModel> translatedAnswerModels = new ArrayList<>();

        answer.getTranslatedAnswer().forEach((language, translatedAnswer) -> {
            TranslatedAnswerModel translatedAnswerModel = new TranslatedAnswerModel();

            translatedAnswerModel.setId(null);
            translatedAnswerModel.setAnswer(answerModel);
            translatedAnswerModel.setLanguage(language);
            translatedAnswerModel.setText(translatedAnswer.getText());

            translatedAnswerModels.add(translatedAnswerModel);
        });

        return translatedAnswerModels;
    }
}
