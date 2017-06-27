package acropollis.municipali.omega.user.cache.branding;


import acropollis.municipali.omega.common.dto.common.Pair;

import java.util.Map;
import java.util.Optional;

public interface UserBrandingCache {
    void setBackground(Map<Pair<Integer, Integer>, byte[]> background);
    Optional<byte []> getBackground(int w, int h);
    void setIcon(Map<Pair<Integer, Integer>, byte[]> icon);
    Optional<byte []> getIcon(int size);
}
