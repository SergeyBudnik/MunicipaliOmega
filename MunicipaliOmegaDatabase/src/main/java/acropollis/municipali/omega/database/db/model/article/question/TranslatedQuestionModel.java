package acropollis.municipali.omega.database.db.model.article.question;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TRANSLATED_QUESTION")
public class TranslatedQuestionModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private QuestionModel question;

    @Column(name = "LANGUAGE")
    private Language language;

    @Column(name = "QUESTION_TEXT", length = 8192)
    private String text;
}
