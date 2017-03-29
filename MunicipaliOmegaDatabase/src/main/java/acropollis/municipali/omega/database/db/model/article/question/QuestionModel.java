package acropollis.municipali.omega.database.db.model.article.question;

import acropollis.municipali.omega.common.dto.article.question.QuestionAnswerType;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.database.db.model.article.question.answer.AnswerModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "QUESTION")
public class QuestionModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID", nullable = false)
    private ArticleModel article;

    @Column(name = "ANSWER_TYPE")
    private QuestionAnswerType answerType;

    @Column(name = "SORTING_ORDER")
    private Integer order;

    @Column(name = "SHOW_RESULT")
    private boolean showResult;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<TranslatedQuestionModel> translatedQuestions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<AnswerModel> answers;
}
