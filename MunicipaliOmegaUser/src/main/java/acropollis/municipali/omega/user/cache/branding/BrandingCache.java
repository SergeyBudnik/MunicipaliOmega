package acropollis.municipali.omega.user.cache.branding;


import acropollis.municipali.omega.common.dto.common.Tuple;

import java.util.Map;
import java.util.Optional;

public interface BrandingCache {
    void setBackground(Map<Tuple<Integer, Integer>, byte[]> background);
    Optional<byte []> getBackground(int w, int h);
    void setIcon(Map<Tuple<Integer, Integer>, byte[]> icon);
    Optional<byte []> getIcon(int size);
}
