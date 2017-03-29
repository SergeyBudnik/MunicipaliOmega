package acropollis.municipali.omega.user.image;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {
    @Override
    public Optional<byte []> getImage(String location, long entityId, int size) {
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
    public Optional<Map<Integer, byte[]>> getImages(String location, long entityId) {
        try {
            File parent = getParent(location, entityId);

            if (parent.exists()) {
                Map<Integer, byte []> icons = new HashMap<>();

                File [] children = parent.listFiles();

                if (children != null) {
                    for (File child : children) {
                        byte [] icon = FileUtils.readFileToByteArray(child);

                        if (icon != null) {
                            int size = Integer.parseInt(child.getName().substring(0, child.getName().length() - ".png".length()));

                            icons.put(size, icon);
                        }
                    }
                }

                return Optional.of(icons);
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
