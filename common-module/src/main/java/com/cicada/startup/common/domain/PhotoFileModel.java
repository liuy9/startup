package com.cicada.startup.common.domain;

import java.io.Serializable;

/**
 * 单个图片对象实体类
 * <p/>
 * 创建时间: 2014年11月17日 下午12:59:38 <br/>
 *
 * @since v0.0.1
 */
public class PhotoFileModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String imageId;
    private String imageName;
    private String imagePath;
    private long imageSize;
    private String imageLat;
    private String imageLon;
    private String imageAddDate;
    private String imageEditDate;
    private boolean imageSelected = false;

    /**
     * imageId.
     *
     * @return the imageId
     * @since v0.0.1
     */
    public String getImageId() {
        return imageId;
    }

    /**
     * imageId.
     *
     * @param imageId the imageId to set
     * @since v0.0.1
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    /**
     * imageName.
     *
     * @return the imageName
     * @since v0.0.1
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * imageName.
     *
     * @param imageName the imageName to set
     * @since v0.0.1
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * imagePath.
     *
     * @return the imagePath
     * @since v0.0.1
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * imagePath.
     *
     * @param imagePath the imagePath to set
     * @since v0.0.1
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * imageLat.
     *
     * @return the imageLat
     * @since v0.0.1
     */
    public String getImageLat() {
        return imageLat;
    }

    /**
     * imageLat.
     *
     * @param imageLat the imageLat to set
     * @since v0.0.1
     */
    public void setImageLat(String imageLat) {
        this.imageLat = imageLat;
    }

    /**
     * imageLon.
     *
     * @return the imageLon
     * @since v0.0.1
     */
    public String getImageLon() {
        return imageLon;
    }

    /**
     * imageLon.
     *
     * @param imageLon the imageLon to set
     * @since v0.0.1
     */
    public void setImageLon(String imageLon) {
        this.imageLon = imageLon;
    }

    /**
     * imageAddDate.
     *
     * @return the imageAddDate
     * @since v0.0.1
     */
    public String getImageAddDate() {
        return imageAddDate;
    }

    /**
     * imageAddDate.
     *
     * @param imageAddDate the imageAddDate to set
     * @since v0.0.1
     */
    public void setImageAddDate(String imageAddDate) {
        this.imageAddDate = imageAddDate;
    }

    /**
     * imageEditDate.
     *
     * @return the imageEditDate
     * @since v0.0.1
     */
    public String getImageEditDate() {
        return imageEditDate;
    }

    /**
     * imageEditDate.
     *
     * @param imageEditDate the imageEditDate to set
     * @since v0.0.1
     */
    public void setImageEditDate(String imageEditDate) {
        this.imageEditDate = imageEditDate;
    }

    /**
     * imageSelected.
     *
     * @return the imageSelected
     * @since v0.0.1
     */
    public boolean getImageSelected() {
        return imageSelected;
    }

    /**
     * imageSelected.
     *
     * @param imageSelected the imageSelected to set
     * @since v0.0.1
     */
    public void setImageSelected(boolean imageSelected) {
        this.imageSelected = imageSelected;
    }

    /**
     * imageSize.
     *
     * @return the imageSize
     * @since v0.0.1
     */
    public long getImageSize() {
        return imageSize;
    }

    /**
     * imageSize.
     *
     * @param imageSize the imageSize to set
     * @since v0.0.1
     */
    public void setImageSize(long imageSize) {
        this.imageSize = imageSize;
    }

}
