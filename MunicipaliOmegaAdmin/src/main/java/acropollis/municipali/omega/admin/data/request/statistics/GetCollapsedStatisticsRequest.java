package acropollis.municipali.omega.admin.data.request.statistics;

import acropollis.municipali.omega.common.dto.user.UserGender;
import lombok.Data;

import java.util.Set;

@Data
public class GetCollapsedStatisticsRequest {
    @Data
    public static class QuestionCriteria {
        private long articleId;
        private long questionId;
        private Set<Long> answersIds;
    }

    @Data
    public static class UserCriteria {
        private Set<UserGender> allowedUserGenders;
        private Long dateOfBirthFrom, dateOfBirthTo;
    }

    private long from, to;
    private QuestionCriteria questionCriteria;
    private UserCriteria userCriteria;
}
