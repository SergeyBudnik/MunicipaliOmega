package acropollis.municipali.omega.common.utils.storage;

import acropollis.municipali.omega.common.dto.common.Pair;

import java.util.HashMap;
import java.util.Map;

public class SquareImageAdapter {
    public static Map<Pair<Integer, Integer>, byte []> pack(Map<Integer, byte[]> image) {
        Map<Pair<Integer, Integer>, byte []> res = new HashMap<>();

        image.forEach((size, imageBytes) -> res.put(new Pair<>(size, size), imageBytes));

        return res;
    }

    public static Map<Integer, byte []> unpack(Map<Pair<Integer, Integer>, byte[]> image) {
        Map<Integer, byte []> res = new HashMap<>();

        image.forEach((size, imageBytes) -> res.put(size.getX(), imageBytes));

        return res;
    }
}
