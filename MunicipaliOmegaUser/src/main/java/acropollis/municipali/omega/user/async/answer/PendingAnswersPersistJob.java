package acropollis.municipali.omega.user.async.answer;

import acropollis.municipali.omega.user.cache.answer.pending.UserPendingAnswersCache;
import acropollis.municipali.omega.user.data.converter.answer.UserAnswerDtoConverter;
import acropollis.municipali.omega.user.data.dto.answer.UserAnswer;
import acropollis.municipali.omega.database.db.dao.UserAnswerDao;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModel;
import acropollis.municipali.omega.user.utils.log.LogUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PendingAnswersPersistJob {
    private static final Logger log = LogUtils.getPendingAnswersPersistLogger();

    @Autowired
    private UserAnswerDao userAnswerDao;

    @Autowired
    private UserPendingAnswersCache userPendingAnswersCache;

    @Scheduled(fixedDelay = 5 * 1000)
    @Transactional
    public void persistAnswers() {
        long currentTime = new Date().getTime();

        try {
            List<UserAnswerModel> answersToPersist = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                Optional<UserAnswer> answer = userPendingAnswersCache.getAndRemoveNextAnswer();

                if (!answer.isPresent()) {
                    break;
                }

                answersToPersist.add(UserAnswerDtoConverter.convert(answer.get()));
            }

            userAnswerDao.save(answersToPersist);

            log.info(String.format("%d answers persisted in %d ms",
                    answersToPersist.size(),
                    new Date().getTime() - currentTime
            ));
        } catch (Exception e) {
            log.error(String.format("Answers persist failed in %d ms", new Date().getTime() - currentTime));
        }
    }
}
