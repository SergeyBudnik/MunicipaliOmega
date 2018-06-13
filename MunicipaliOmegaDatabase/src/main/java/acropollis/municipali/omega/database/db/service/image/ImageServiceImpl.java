package acropollis.municipali.omega.database.db.service.image;

import lombok.SneakyThrows;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Service
public class ImageServiceImpl implements ImageService {
    private static final int BUFFER_SIZE = 5 * 1024 * 1024;

    @Override
    public void addImage(List<String> root, String fileName, byte[] image) {
        runInFtp(root, ftpClient -> {
            try {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                try (InputStream is = new ByteArrayInputStream(image)) {
                    ftpClient.storeFile(fileName + ".png", is);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @SneakyThrows
    public void addImage(List<String> root, String directory, String fileName, byte [] image) {
        runInFtp(root, ftpClient -> {
            try {
                ftpClient.makeDirectory(directory);
                ftpClient.changeWorkingDirectory(directory);

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                try (InputStream is = new ByteArrayInputStream(image)) {
                    ftpClient.storeFile(fileName + ".png", is);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @SneakyThrows
    public void removeAllImagesRemoveDirectory(FTPClient ftpClient, String directory) {
        try {
            ftpClient.removeDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAllImagesKeepDirectory(List<String> root) {
        runInFtp(root, ftpClient -> {
            try {
                for (FTPFile file : ftpClient.listFiles()) {
                    ftpClient.deleteFile(file.getName());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SneakyThrows
    public void runInFtp(List<String> root, Consumer<FTPClient> action) {
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(
                    config.getImageHostingFtpUrl(),
                    (int) config.getImageHostingFtpPort()
            );

            ftpClient.login(
                    config.getImageHostingFtpUsername(),
                    config.getImageHostingFtpPassword()
            );

            ftpClient.setBufferSize(BUFFER_SIZE);
            ftpClient.enterLocalPassiveMode();

            ftpClient.changeWorkingDirectory(config.getId());

            for (String rootItem : root) {
                ftpClient.changeWorkingDirectory(rootItem);
            }

            action.accept(ftpClient);
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }
    }
}
