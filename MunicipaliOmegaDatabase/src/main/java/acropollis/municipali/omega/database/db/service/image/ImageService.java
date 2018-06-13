package acropollis.municipali.omega.database.db.service.image;

import org.apache.commons.net.ftp.FTPClient;

import java.util.List;
import java.util.function.Consumer;

public interface ImageService {
    void runInFtp(List<String> root, Consumer<FTPClient> action);
    void addImage(List<String> root, String fileName, byte [] image);
    void addImage(List<String> root, String directory, String fileName, byte [] image);
    void removeAllImagesRemoveDirectory(FTPClient ftpClient, String directory);
    void removeAllImagesKeepDirectory(List<String> root);
}
