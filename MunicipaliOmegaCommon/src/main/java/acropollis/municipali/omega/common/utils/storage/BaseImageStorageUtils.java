package acropollis.municipali.omega.common.utils.storage;

import acropollis.municipali.omega.common.dto.common.Pair;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class BaseImageStorageUtils {
    static Optional<Map<Pair<Integer, Integer>, byte []>> getImages(File parent) throws IOException {
        if (parent.exists()) {
            Map<Pair<Integer, Integer>, byte []> icons = new HashMap<>();

            File[] children = parent.listFiles();

            if (children != null) {
                for (File child : children) {
                    byte [] icon = FileUtils.readFileToByteArray(child);

                    if (icon != null) {
                        String fileName = child.getName().substring(0, child.getName().length() - ".png".length());
                        String [] sizeParts = fileName.split("x");

                        icons.put(new Pair<>(
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
    }
}
