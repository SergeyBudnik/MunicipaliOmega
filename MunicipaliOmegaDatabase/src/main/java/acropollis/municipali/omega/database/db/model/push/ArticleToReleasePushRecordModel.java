package acropollis.municipali.omega.database.db.model.push;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ARTICLE_RELEASED_PUSH_RECORD")
public class ArticleToReleasePushRecordModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "RELEASE_DATE")
    private long releaseDate;
}
