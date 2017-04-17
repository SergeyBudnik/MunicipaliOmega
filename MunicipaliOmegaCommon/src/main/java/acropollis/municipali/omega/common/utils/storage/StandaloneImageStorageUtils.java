package acropollis.municipali.omega.common.utils.storage;

import acropollis.municipali.omega.common.dto.common.Tuple;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StandaloneImageStorageUtils {
    public static Optional<byte[]> getImage(String location, int w, int h) {
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

    public static Optional<Map<Tuple<Integer, Integer>, byte []>> getImages(String location) {
        try {
            File parent = getParent(location);

            if (parent.exists()) {
                Map<Tuple<Integer, Integer>, byte []> icons = new HashMap<>();

                File [] children = parent.listFiles();

                if (children != null) {
                    for (File child : children) {
                        byte [] icon = FileUtils.readFileToByteArray(child);

                        if (icon != null) {
                            String fileName = child.getName().substring(0, child.getName().length() - ".png".length());
                            String [] sizeParts = fileName.split("x");

                            icons.put(new Tuple<>(
                                    Integer.parseInt(sizeParts[0]),
                                    Integer.parseInt(sizeParts[1])
                            ), icon);
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

    public static void saveImages(String location, Map<Tuple<Integer, Integer>, byte[]> image) {
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

    public static void removeImages(String location) {
        try {
            File parent = getParent(location);

            if (parent.exists()) {
                FileUtils.deleteDirectory(parent);
            }
        } catch (IOException e) {
            throw new RuntimeException(); /* ToDo */
        }
    }

    private static File getParent(String location) {
        return new File(location);
    }

    private static File getFile(String location, int w, int h) {
        return new File(getParent(location) + File.separator + w + "x" + h + ".png");
    }
}
