package acropollis.municipali.omega.common.utils.common;

import acropollis.municipali.omega.common.dto.common.Pair;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageUtils {
    private static final String IMAGE_FORMAT_PNG = "png";

    public static Map<Integer, byte []> resizeImages(byte [] src, int... sizes) {
        Map<Integer, byte []> dest = new HashMap<>();

        if (src != null) {
            try {
                for (int size : sizes) {
                    dest.put(size, toBytes(scaleImageByWidth(fromBytes(src), size)));
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        return dest;
    }

    public static Map<Pair<Integer, Integer>, byte []> resizeImages(
            byte [] src,
            Pair<Integer, Integer>... sizes
    ) {
        Map<Pair<Integer, Integer>, byte []> dest = new HashMap<>();

        if (src != null) {
            try {
                for (Pair<Integer, Integer> size : sizes) {
                    dest.put(
                            new Pair<>(size.getX(), size.getY()),
                            toBytes(scaleImageByWidth(fromBytes(src), size.getX()))
                    );
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        return dest;
    }

    public static BufferedImage scaleImageByWidth(BufferedImage src, int width) {
        double scaleFactor = 1.0 * width / src.getWidth();

        return scaleImage(src, scaleFactor, scaleFactor);
    }

    public static BufferedImage scaleImage(BufferedImage src, double scaleFactorX, double scaleFactorY) {
        BufferedImage dest = new BufferedImage(
                (int) (src.getWidth() * scaleFactorX),
                (int) (src.getHeight() * scaleFactorY),
                BufferedImage.TYPE_INT_ARGB
        );

        AffineTransform at = new AffineTransform(); {
            at.scale(scaleFactorX, scaleFactorY);
        }

        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        return scaleOp.filter(src, dest);
    }

    public static BufferedImage cropImage(BufferedImage src, int w, int h) {
        int x = (src.getWidth() - w) / 2;
        int y = (src.getHeight() - h) / 2;

        return src.getSubimage(x, y, w, h);
    }

    public static BufferedImage scaleAndCropImage(BufferedImage src, int w, int h) {
        double scaleFactor = Math.max(1.0 * w / src.getWidth(), 1.0 * h / src.getHeight());

        return cropImage(scaleImage(src, scaleFactor, scaleFactor), w, h);
    }

    public static BufferedImage fromBytes(byte [] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }

    public static byte [] toBytes(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, IMAGE_FORMAT_PNG, baos);

            baos.flush();

            return baos.toByteArray();
        }
    }
}
