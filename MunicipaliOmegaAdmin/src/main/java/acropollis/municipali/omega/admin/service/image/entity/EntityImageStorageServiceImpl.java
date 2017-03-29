package acropollis.municipali.omega.admin.service.image.entity;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class EntityImageStorageServiceImpl implements EntityImageStorageService {
    @Override
    public Optional<byte []> getIcon(String location, long entityId, int size) {
        try {
            File f = getFile(location, entityId, size);

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
    public void saveImages(String location, long entityId, Map<Integer, byte[]> icon) {
        try {
            File parent = getParent(location, entityId);

            if (!parent.exists()) {
                if (!parent.mkdir()) {
                    throw new RuntimeException(); /* ToDo */
                }
            }

            for (Integer iconSize : icon.keySet()) {
                FileUtils.writeByteArrayToFile(getFile(location, entityId, iconSize), icon.get(iconSize));
            }
        } catch (IOException e) {
            throw new RuntimeException(); /* ToDo */
        }
    }

    @Override
    public void removeImages(String location, long entityId) {
        try {
            File parent = getParent(location, entityId);

            if (parent.exists()) {
                FileUtils.deleteDirectory(parent);
            }
        } catch (IOException e) {
            throw new RuntimeException(); /* ToDo */
        }
    }

    private File getParent(String location, long entityId) {
        return new File(location + File.separator + entityId);
    }

    private File getFile(String location, long entityId, int size) {
        return new File(getParent(location, entityId) + File.separator + size + ".png");
    }
}
