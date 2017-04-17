package acropollis.municipali.omega.user.cache.answer.pending;

import acropollis.municipali.omega.user.data.dto.answer.UserAnswer;

import java.util.Optional;

public interface UserPendingAnswersCache {
    void addAnswer(UserAnswer answer);
    Optional<UserAnswer> getAndRemoveNextAnswer();
}
