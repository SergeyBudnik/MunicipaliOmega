package acropollis.municipali.omega.user.image;

import java.util.Map;
import java.util.Optional;

public interface ImageStorageService {
    Optional<byte []> getImage(String location, long entityId, int size);
    Optional<Map<Integer, byte []>> getImages(String location, long entityId);
    void saveImages(String location, long entityId, Map<Integer, byte[]> image);
    void removeImages(String location, long entityId);
}
