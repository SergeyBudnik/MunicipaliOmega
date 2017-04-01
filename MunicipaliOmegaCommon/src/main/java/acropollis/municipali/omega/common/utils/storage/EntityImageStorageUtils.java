package acropollis.municipali.omega.common.utils.storage;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class EntityImageStorageUtils {
    public static Optional<byte []> getIcon(String location, long entityId, int size) {
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

    public static void saveImages(String location, long entityId, Map<Integer, byte[]> icon) {
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

    public static void removeImages(String location, long entityId) {
        try {
            File parent = getParent(location, entityId);

            if (parent.exists()) {
                FileUtils.deleteDirectory(parent);
            }
        } catch (IOException e) {
            throw new RuntimeException(); /* ToDo */
        }
    }

    private static File getParent(String location, long entityId) {
        return new File(location + File.separator + entityId);
    }

    private static File getFile(String location, long entityId, int size) {
        return new File(getParent(location, entityId) + File.separator + size + ".png");
    }
}
