package acropollis.municipali.omega.database.db.model.user;

import acropollis.municipali.omega.common.dto.user.UserGender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Data
@Entity
@Table(name = "USER")
public class UserModel {
    @EmbeddedId
    @Delegate
    private UserIdModel id;
    @Column(name = "AUTH_TOKEN")
    private String authToken;
    @Column(name = "NAME")
    private String name;
    @Column(name = "GENDER")
    private UserGender gender;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DATE_OF_BIRTH")
    private Long dateOfBirth;
    @Column(name = "GMS_TOKEN")
    private String gmsToken;
    @Column(name = "LAST_UPDATE_DATE")
    private long lastUpdateDate;
    @Column(name = "IS_DELETED")
    private boolean isDeleted;
}
