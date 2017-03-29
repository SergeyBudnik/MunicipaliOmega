package acropollis.municipali.omega.admin.service.image.entity;

import java.util.Map;
import java.util.Optional;

public interface EntityImageStorageService {
    Optional<byte []> getIcon(String location, long entityId, int size);
    void saveImages(String location, long entityId, Map<Integer, byte[]> icon);
    void removeImages(String location, long entityId);
}
