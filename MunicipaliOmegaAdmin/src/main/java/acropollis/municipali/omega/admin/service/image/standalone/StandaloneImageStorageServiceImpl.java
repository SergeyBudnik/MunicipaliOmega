package acropollis.municipali.omega.admin.service.image.standalone;

import acropollis.municipali.omega.common.dto.common.Tuple;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class StandaloneImageStorageServiceImpl implements StandaloneImageStorageService {
    @Override
    public Optional<byte[]> getImage(String location, int w, int h) {
        try {
            File f = getFile(location, w, h);

            if (f.exists()) {
                return Optional.of(FileUtils.readFileToByteArray(f));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException(); /* ToDo */
        }
    }

    @Override
    public void saveImages(String location, Map<Tuple<Integer, Integer>, byte[]> image) {
        try {
            File parent = getParent(location);

            if (!parent.exists()) {
                if (!parent.mkdir()) {
                    throw new RuntimeException(); /* ToDo */
                }
            }

            for (Tuple<Integer, Integer> imageSize : image.keySet()) {
                FileUtils.writeByteArrayToFile(getFile(location, imageSize.getX(), imageSize.getY()), image.get(imageSize));
            }
        } catch (IOException e) {
            throw new RuntimeException(); /* ToDo */
        }
    }

    @Override
    public void removeImages(String location) {
        try {
            File parent = getParent(location);

            if (parent.exists()) {
                FileUtils.deleteDirectory(parent);
            }
        } catch (IOException e) {
            throw new RuntimeException(); /* ToDo */
        }
    }

    private File getParent(String location) {
        return new File(location);
    }

    private File getFile(String location, int w, int h) {
        return new File(getParent(location) + File.separator + w + "x" + h + ".png");
    }
}
