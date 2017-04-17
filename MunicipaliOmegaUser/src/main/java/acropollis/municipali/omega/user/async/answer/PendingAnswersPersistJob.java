package acropollis.municipali.omega.user.async.answer;

import acropollis.municipali.omega.user.cache.answer.pending.UserPendingAnswersCache;
import acropollis.municipali.omega.user.data.converter.answer.UserAnswerDtoConverter;
import acropollis.municipali.omega.user.data.dto.answer.UserAnswer;
import acropollis.municipali.omega.database.db.dao.UserAnswerDao;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PendingAnswersPersistJob {
    @Autowired
    private UserAnswerDao userAnswerDao;

    @Autowired
    private UserPendingAnswersCache userPendingAnswersCache;

    @Scheduled(fixedDelay = 5 * 1000)
    @Transactional(readOnly = false)
    public void persistAnswers() {
        List<UserAnswerModel> answersToPersist = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Optional<UserAnswer> answer = userPendingAnswersCache.getAndRemoveNextAnswer();

            if (!answer.isPresent()) {
                break;
            }

            answersToPersist.add(UserAnswerDtoConverter.convert(answer.get()));
        }

        userAnswerDao.save(answersToPersist);
    }
}
