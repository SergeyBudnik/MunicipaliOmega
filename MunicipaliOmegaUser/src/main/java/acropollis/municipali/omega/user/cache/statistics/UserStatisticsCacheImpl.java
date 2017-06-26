package acropollis.municipali.omega.user.cache.statistics;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class UserStatisticsCacheImpl implements UserStatisticsCache {
    private AtomicReference<Map<Long, Map<Long, Map<Long, Long>>>> statisticsCache = new AtomicReference<>(new ConcurrentHashMap<>());

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Map<Long, Long> get(long articleId, long questionId) {
        try {
            lock.readLock().lock();

            return statisticsCache
                    .get()
                    .getOrDefault(articleId, new HashMap<>())
                    .getOrDefault(questionId, new HashMap<>());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void set(Map<Long, Map<Long, Map<Long, Long>>> statistics) {
        lock.writeLock().lock();

        this.statisticsCache.set(statistics);

        lock.writeLock().unlock();
    }
}
