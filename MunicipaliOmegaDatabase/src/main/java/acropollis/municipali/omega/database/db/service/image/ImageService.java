package acropollis.municipali.omega.database.db.service.image;

import java.util.List;

public interface ImageService {
    void addImage(List<String> root, String fileName, byte [] image);
    void addImage(List<String> root, String directory, String fileName, byte [] image);
    void removeAllImagesRemoveDirectory(List<String> root, String directory);
    void removeAllImagesKeepDirectory(List<String> root);
}
