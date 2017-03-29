package acropollis.municipali.omega.user.cache.answer.pending;

import acropollis.municipali.omega.user.data.dto.answer.UserAnswer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class PendingAnswersCacheImpl implements PendingAnswersCache {
    private List<UserAnswer> answers = new ArrayList<>();

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void addAnswer(UserAnswer answer) {
        try {
            lock.writeLock().lock();

            answers.add(answer);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<UserAnswer> getAndRemoveNextAnswer() {
        if (answers.size() == 0) {
            return Optional.empty();
        }

        try {
            lock.writeLock().lock();

            return answers.size() == 0 ?
                    Optional.empty() :
                    Optional.of(answers.remove(answers.size() - 1));
        } finally {
            lock.writeLock().unlock();
        }
    }
}
