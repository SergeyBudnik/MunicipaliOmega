package acropollis.municipali.omega.user.cache.statistics;

import java.util.Map;

public interface UserStatisticsCache {
    Map<Long, Long> get(long articleId, long questionId);
    void set(Map<Long, Map<Long, Map<Long, Long>>> statistics);
}
