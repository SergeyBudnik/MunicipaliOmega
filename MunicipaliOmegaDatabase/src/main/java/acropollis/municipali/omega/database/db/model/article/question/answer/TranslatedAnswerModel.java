package acropollis.municipali.omega.database.db.model.article.question.answer;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TRANSLATED_ANSWER")
public class TranslatedAnswerModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANSWER_ID", nullable = false)
    private AnswerModel answer;

    @Column(name = "LANGUAGE")
    private Language language;

    @Column(name = "ANSWER_TEXT", length = 64)
    private String text;
}
