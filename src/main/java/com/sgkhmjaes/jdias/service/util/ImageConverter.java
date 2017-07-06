
package com.sgkhmjaes.jdias.service.util;

import com.sgkhmjaes.jdias.service.dto.AvatarDTO;
import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ImageProcessor;
import java.io.File;
import java.time.LocalDate;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageConverter {
    
    private final Logger log = LoggerFactory.getLogger(ImageConverter.class);
    private final static double COMPRESS_FACTOR = 0.1;
    private final static int SIZE_SMALL = 50;
    private final static int SIZE_MEDIUM = 100;
    private final static int SIZE_LARGE = 800;
    private final static String IMAGE_CATALOG_NAME = "images";
    static {new File(IMAGE_CATALOG_NAME).mkdir();}
    
    // for use add to gradle dependencies compile "net.imagej:ij:1.49d"
    public ImageConverter() {}
    
    public static void main(String[] args) {
        AvatarDTO convert = new ImageConverter().convert("D:/1.JPG");
    }
    
    // AvatarDTO convert = new ImageConverter().convert("D:/1.JPG"); - use example
    public AvatarDTO convert (String sourceImagePath) {
        
        String fileName = IMAGE_CATALOG_NAME + "/" + LocalDate.now().toString() + UUID.randomUUID();
        resizeImage(sourceImagePath, fileName + "-small.jpg", SIZE_SMALL);
        resizeImage(sourceImagePath, fileName + "-medium.jpg", SIZE_MEDIUM);
        resizeImage(sourceImagePath, fileName + "-large.jpg", SIZE_LARGE);
        
        return new AvatarDTO(fileName+"-small.jpg", fileName+"-medium.jpg", fileName+"-large.jpg");
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

