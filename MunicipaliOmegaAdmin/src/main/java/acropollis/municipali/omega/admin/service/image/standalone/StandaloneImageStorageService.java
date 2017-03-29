package acropollis.municipali.omega.admin.service.image.standalone;

import acropollis.municipali.omega.common.dto.common.Tuple;

import java.util.Map;
import java.util.Optional;

public interface StandaloneImageStorageService {
    Optional<byte []> getImage(String location, int w, int h);
    void saveImages(String location, Map<Tuple<Integer, Integer>, byte[]> icon);
    void removeImages(String location);
}
