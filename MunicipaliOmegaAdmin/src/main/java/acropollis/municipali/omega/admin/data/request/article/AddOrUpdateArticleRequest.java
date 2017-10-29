package acropollis.municipali.omega.admin.data.request.article;

import acropollis.municipali.omega.common.dto.article.ArticleType;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.TranslatedArticle;
import acropollis.municipali.omega.common.dto.article.question.QuestionAnswerType;
import acropollis.municipali.omega.common.dto.article.question.QuestionWithIcon;
import acropollis.municipali.omega.common.dto.article.question.TranslatedQuestion;
import acropollis.municipali.omega.common.dto.article.question.answer.AnswerWithIcon;
import acropollis.municipali.omega.common.dto.article.question.answer.TranslatedAnswer;
import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class AddOrUpdateArticleRequest {
    @Data
    public static class ArticleInfo {
        private ArticleType type;
        private Map<Language, TranslatedArticle> translatedArticle;
        private List<QuestionInfo> questions;
        private byte [] icon;
        private byte [] image;
        private byte [] clippedImage;
        private String video;
        private boolean sendPushOnRelease;
        private long releaseDate;
        private long calendarStartDate;
        private long calendarFinishDate;
        private long expirationDate;

        @Transient
        public ArticleWithIcon toDto() {
            return toDto(null);
        }

        @Transient
        public ArticleWithIcon toDto(Long id) {
            ArticleWithIcon articleWithIcon = new ArticleWithIcon();

            articleWithIcon.setId(id);
            articleWithIcon.setType(type);
            articleWithIcon.setTranslatedArticle(translatedArticle);
            articleWithIcon.setQuestions(questions.stream().map(QuestionInfo::toDto).collect(Collectors.toList()));
            articleWithIcon.setIcon(Collections.singletonMap(-1, icon));
            articleWithIcon.setImage(Collections.singletonMap(-1, image));
            articleWithIcon.setClippedImage(Collections.singletonMap(-1, clippedImage));
            articleWithIcon.setVideo(video);
            articleWithIcon.setSendPushOnRelease(sendPushOnRelease);
            articleWithIcon.setReleaseDate(releaseDate);
            articleWithIcon.setCalendarStartDate(calendarStartDate);
            articleWithIcon.setCalendarFinishDate(calendarFinishDate);
            articleWithIcon.setExpirationDate(expirationDate);

            return articleWithIcon;
        }
    }

    @Data
    public static class QuestionInfo {
        private QuestionAnswerType answerType;
        private Map<Language, TranslatedQuestion> translatedQuestion;
        private List<AnswerInfo> answers;
        private boolean showResult;

        @Transient
        public QuestionWithIcon toDto() {
            QuestionWithIcon questionWithIcon = new QuestionWithIcon();

            questionWithIcon.setAnswerType(answerType);
            questionWithIcon.setTranslatedQuestion(translatedQuestion);
            questionWithIcon.setAnswers(new ArrayList<>());

            for (AnswerInfo answerInfo : answers) {
                questionWithIcon.getAnswers().add(answerInfo != null ? answerInfo.toDto() : null);
            }

            questionWithIcon.setShowResult(showResult);

            return questionWithIcon;
        }
    }

    @Data
    public static class AnswerInfo {
        private Map<Language, TranslatedAnswer> translatedAnswer;
        private byte [] icon;

        @Transient
        public AnswerWithIcon toDto() {
            AnswerWithIcon answerWithIcon = new AnswerWithIcon();

            answerWithIcon.setTranslatedAnswer(translatedAnswer);
            answerWithIcon.setIcon(Collections.singletonMap(-1, icon));

            return answerWithIcon;
        }
    }

    private ArticleInfo article;
}
