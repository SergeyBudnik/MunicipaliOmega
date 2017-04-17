package acropollis.municipali.omega.user.cache.statistics;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserStatisticsCacheImpl implements UserStatisticsCache {
    private Map<Long, Map<Long, Map<Long, Long>>> statisticsCache = new ConcurrentHashMap<>();

    @Override
    public Map<Long, Long> get(long articleId, long questionId) {
        return statisticsCache
                .getOrDefault(articleId, new HashMap<>())
                .getOrDefault(questionId, new HashMap<>());
    }

    @Override
    public void set(Map<Long, Map<Long, Map<Long, Long>>> statistics) {
        this.statisticsCache = statistics;
    }
}
