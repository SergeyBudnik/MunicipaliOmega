package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.common.dto.user.UserGender;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/statistics")
@Api(tags = "Statistics", description = "PROTECTED")
public class StatisticsResource {
    @Data
    public static class GetStatisticsRequest {
        @Data
        public static class QuestionCriteria {
            private long questionId;
            @NonNull private Set<Long> answersIds;
        }

        @Data
        public static class UserCriteria {
            @NonNull private Set<UserGender> allowedUserGenders;
            private Long dateOfBirthFrom, dateOfBirthTo;
        }

        private long from, to;
        @NonNull private QuestionCriteria questionCriteria;
        @NonNull private UserCriteria userCriteria;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<Long, Long> getStatistics(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetStatisticsRequest request) {

        Map<Long, Long> answers = new HashMap<>();

        for (long answerId : request.getQuestionCriteria().getAnswersIds()) {
            answers.put(answerId, (long) new Random().nextInt(10));
        }

        return answers;
    }
}
