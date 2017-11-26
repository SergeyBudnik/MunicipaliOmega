package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.async.CommonHealthCheck;
import acropollis.municipali.omega.health_check.data.ReloadHealth;
import acropollis.municipali.omega.user.cache.answer.pending.UserPendingAnswersCache;
import acropollis.municipali.omega.user.data.converter.answer.UserAnswerDtoConverter;
import acropollis.municipali.omega.user.data.dto.answer.UserAnswer;
import acropollis.municipali.omega.database.db.dao.UserAnswerDao;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModel;
import acropollis.municipali.omega.user.data.health_check.UserHealth;
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
public class UserPendingAnswersPersistJob extends CommonHealthCheck<UserHealth, ReloadHealth> {
    private static final Logger log = LogUtils.getPendingAnswersPersistLogger();

    @Autowired
    private UserAnswerDao userAnswerDao;

    @Autowired
    private UserPendingAnswersCache userPendingAnswersCache;

    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional
    public void persistAnswers() {
        onReloadStarted();

        long reloadStartDate = new Date().getTime();

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

            updateHealthWithSuccess(
                    answersToPersist.size(),
                    reloadStartDate,
                    new Date().getTime()
            );
        } catch (Exception e) {
            updateHealthWithFailure(
                    reloadStartDate,
                    new Date().getTime(),
                    e
            );
        }
    }

    @Override
    protected HealthCheckCache getHealthCheckCache() {
        return healthCheckCache;
    }

    @Override
    protected UserHealth getHealthEntity() {
        return new UserHealth();
    }

    @Override
    protected ReloadHealth getReloadJobHealthEntity() {
        return new ReloadHealth();
    }

    @Override
    protected void updateReloadJobHealth(UserHealth userHealth, ReloadHealth reloadJobHealth) {
        userHealth.setPendingAnswersPersistJobHealth(reloadJobHealth);
    }

    private void updateHealthWithSuccess(int amountOfAnswers, long startDate, long endDate) {
        onReloadSuccess(startDate, endDate)
                .getPendingAnswersPersistJobHealth()
                .setAmount(amountOfAnswers);

        log.info(String.format("%d answers persisted in %d ms",
                amountOfAnswers,
                endDate - startDate
        ));
    }

    private void updateHealthWithFailure(long startDate, long endDate, Exception failureReason) {
        onReloadFailure(startDate, endDate, failureReason);

        log.error(
                String.format("Answers persist failed in %d ms", endDate - startDate),
                failureReason
        );
    }
}
