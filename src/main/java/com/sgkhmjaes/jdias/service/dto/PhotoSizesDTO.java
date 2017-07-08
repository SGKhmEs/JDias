package com.sgkhmjaes.jdias.service.dto;

/**
 * Created by yurak on 08.07.2017.
 */
public class PhotoSizesDTO {
    private String thumbSmall;
    private String thumbMedium;
    private String thumbLarge;
    private String scaledFull;

    public PhotoSizesDTO(String thumbSmall, String thumbMedium, String thumbLarge, String scaledFull) {
        this.thumbSmall = thumbSmall;
        this.thumbMedium = thumbMedium;
        this.thumbLarge = thumbLarge;
        this.scaledFull = scaledFull;
    }

    public String getThumbSmall() {
        return thumbSmall;
    }

    public String getThumbMedium() {
        return thumbMedium;
    }

    public String getThumbLarge() {
        return thumbLarge;
    }

    public String getScaledFull() {
        return scaledFull;
    }

}
