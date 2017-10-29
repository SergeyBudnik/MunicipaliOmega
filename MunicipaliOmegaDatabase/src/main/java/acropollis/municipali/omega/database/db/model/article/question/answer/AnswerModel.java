package acropollis.municipali.omega.database.db.model.article.question.answer;

import acropollis.municipali.omega.database.db.model.article.question.QuestionModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "ANSWER")
public class AnswerModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private QuestionModel question;

    @Column(name = "SORTING_ORDER")
    private Integer order;

    @Column(name = "HAS_ICON")
    private boolean hasIcon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<TranslatedAnswerModel> translatedAnswers;
}
