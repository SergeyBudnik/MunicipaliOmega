package acropollis.municipali.omega.common.utils.storage;

import acropollis.municipali.omega.common.dto.common.Tuple;

import java.util.HashMap;
import java.util.Map;

public class SquareImageAdapter {
    public static Map<Tuple<Integer, Integer>, byte []> pack(Map<Integer, byte[]> image) {
        Map<Tuple<Integer, Integer>, byte []> res = new HashMap<>();

        image.forEach((size, imageBytes) -> res.put(new Tuple<>(size, size), imageBytes));

        return res;
    }

    public static Map<Integer, byte []> unpack(Map<Tuple<Integer, Integer>, byte[]> image) {
        Map<Integer, byte []> res = new HashMap<>();

        image.forEach((size, imageBytes) -> res.put(size.getX(), imageBytes));

        return res;
    }
}
