
package com.sgkhmjaes.jdias.service.util;

import com.sgkhmjaes.jdias.service.dto.AvatarDTO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.UUID;
import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageConverter {

    private final Logger log = LoggerFactory.getLogger(ImageConverter.class);
    private static final int HEIGHT_SMALL = 50;
    private static final int WIDTH_SMALL = 50;
    private static final int HEIGHT_MEDIUM = 100;
    private static final int WIDTH_MEDIUM = 100;
    //private static final String IMAGE_CATALOG_NAME = "images";
    private static final String SMALL_PREFIX = "small_";
    private static final String MEDIUM_PREFIX = "medium_";
    //static {new File(IMAGE_CATALOG_NAME).mkdir();}

    public ImageConverter() {}

    public AvatarDTO convert (File sourceImage) {

        if (sourceImage == null) return null;

        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(sourceImage);
        } catch (IOException ex) {
            log.debug("ImageConverter.convert finished with IOException", ex.getMessage());
            return null;
        }

        BufferedImage resizeImageSmall = resize(originalImage, WIDTH_SMALL, HEIGHT_SMALL);
        BufferedImage resizeImageMedium = resize(originalImage, WIDTH_MEDIUM, HEIGHT_MEDIUM);
        String path = sourceImage.getParent(); //IMAGE_CATALOG_NAME + "/" + LocalDate.now().toString() + UUID.randomUUID();
        String fileName = sourceImage.getName();

        try /*(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path + "-origin.jpg")))) */ {
            //out.writeObject(sourceImage);
            ImageIO.write(resizeImageSmall, "JPG", new File(path + SMALL_PREFIX + fileName));
            ImageIO.write(resizeImageMedium, "JPG", new File(path + MEDIUM_PREFIX + fileName));
        } catch (IOException ex) {
            log.debug("ImageConverter.convert finished with IOException", ex.getMessage());
            return null;
        }

        return new AvatarDTO(path + SMALL_PREFIX + fileName, path + MEDIUM_PREFIX + fileName, path + fileName);
    }

    private BufferedImage resize(BufferedImage image, int width, int height) {
        int w, h;
        if (image.getWidth() == width && image.getHeight() == height) {
            w = image.getWidth();
            h = image.getHeight();
        }
        else {
            float dx = ((float) width) / image.getWidth();
            float dy = ((float) height) / image.getHeight();
            if (dx == dy) {
                w = width;
                h = (int) (dx * image.getHeight());
            } else {
                w = (int) (dy * image.getWidth());
                h = height;
            }
        }
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = null;
        try {
            graphics2D = bufferedImage.createGraphics();
            graphics2D.fillRect(0, 0, width, height);
            graphics2D.drawImage(image.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        } finally {
            if (graphics2D != null) graphics2D.dispose();
        }
        return bufferedImage;
    }

}

