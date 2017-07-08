
package com.sgkhmjaes.jdias.service.util;

import com.sgkhmjaes.jdias.config.Constants;
import com.sgkhmjaes.jdias.service.dto.AvatarDTO;
import com.sgkhmjaes.jdias.service.dto.PhotoSizesDTO;
import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ImageProcessor;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageConverter {

    private final Logger log = LoggerFactory.getLogger(ImageConverter.class);
    private final static double COMPRESS_FACTOR = 0.1;
    private final static int SIZE_SMALL = 50;
    private final static int SIZE_MEDIUM = 100;
    private final static int SIZE_LARGE = 300;
    private final static int SIZE_SCALED_FULL = 700;

    // for use add to gradle dependencies compile "net.imagej:ij:1.49d"
    public ImageConverter() {}

    // AvatarDTO convert = new ImageConverter().convert("D:/1.JPG"); - use example
    public PhotoSizesDTO convert (File file) {

        //String fileParent = IMAGE_CATALOG_NAME + "/" + LocalDate.now().toString() + UUID.randomUUID();
        String fileParent = file.getParent() + "/";
        String fileName = file.getName();
        resizeImage(file.getPath(), fileParent + Constants.SMALL_PREFIX + fileName, SIZE_SMALL);
        resizeImage(file.getPath(), fileParent + Constants.MEDIUM_PREFIX + fileName, SIZE_MEDIUM);
        resizeImage(file.getPath(), fileParent + Constants.LARGE_PREFIX + fileName, SIZE_LARGE);
        resizeImage(file.getPath(), fileParent + Constants.SCALED_FULL_PREFIX + fileName, SIZE_SCALED_FULL);

        return new PhotoSizesDTO(fileParent + Constants.SMALL_PREFIX + fileName,
            fileParent + Constants.MEDIUM_PREFIX + fileName,
            fileParent + Constants.LARGE_PREFIX + fileName,
            fileParent + Constants.SCALED_FULL_PREFIX + fileName);
    }

    private void resizeImage(String inputFilePath, String outputFilePath, int size) {
        log.debug("IMAGE CONVRTER, method 'resizePicture' start with parametrs: ", inputFilePath, outputFilePath, size);

        if (size<=0) return;
        ImageProcessor ip;
        try{
            ip = new Opener().openImage(inputFilePath).getProcessor();
        }catch (NullPointerException e){
            log.debug("IMAGE CONVRTER exception: ", e);
            return;
        }

        float scaleFactor;
        float width = (float) size / ip.getWidth();
        float height = (float) size / ip.getHeight();
        if (width < height) scaleFactor = width;
        else scaleFactor = height;

        ip.blurGaussian(COMPRESS_FACTOR / scaleFactor);
        ip.setInterpolationMethod(ImageProcessor.NONE);
        ImageProcessor outputProcessor = ip.resize((int)(ip.getWidth() * scaleFactor), (int)(ip.getHeight()*scaleFactor));
        IJ.saveAs(new ImagePlus("", outputProcessor), outputFilePath.substring(outputFilePath.lastIndexOf('.')+1), outputFilePath);

    }

}
