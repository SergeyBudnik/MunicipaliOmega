package acropollis.municipali.omega.database.db.model.report;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "REPORT")
public class ReportModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "LATITUDE")
    private double latitude;
    @Column(name = "LONGITUDE")
    private double longitude;
    @Column(name = "COMMENT", length = 2048)
    private String comment;
}
