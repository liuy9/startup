package com.cicada.startup.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 图片目录的相册对象
 * <p/>
 * 创建时间: 2014年11月17日 下午12:59:56 <br/>
 *
 * @since v0.0.1
 */
public class PhotoFolderModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int imageCount = 0;
	private String imageFolderName;
	private String imageFolderPath;
	private String imageFolderLogo;
	private List<PhotoFileModel> imageList;
	/**
	 * imageCount.
	 *
	 * @return  the imageCount
	 * @since   v0.0.1
	 */
	public int getImageCount() {
		return imageCount;
	}
	/**
	 * imageCount.
	 *
	 * @param   imageCount    the imageCount to set
	 * @since   v0.0.1
	 */
	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}
	/**
	 * imageFolderName.
	 *
	 * @return  the imageFolderName
	 * @since   v0.0.1
	 */
	public String getImageFolderName() {
		return imageFolderName;
	}
	/**
	 * imageFolderName.
	 *
	 * @param   imageFolderName    the imageFolderName to set
	 * @since   v0.0.1
	 */
	public void setImageFolderName(String imageFolderName) {
		this.imageFolderName = imageFolderName;
	}
	/**
	 * imageFolderPath.
	 *
	 * @return  the imageFolderPath
	 * @since   v0.0.1
	 */
	public String getImageFolderPath() {
		return imageFolderPath;
	}
	/**
	 * imageFolderPath.
	 *
	 * @param   imageFolderPath    the imageFolderPath to set
	 * @since   v0.0.1
	 */
	public void setImageFolderPath(String imageFolderPath) {
		this.imageFolderPath = imageFolderPath;
	}
	/**
	 * imageFolderLogo.
	 *
	 * @return  the imageFolderLogo
	 * @since   v0.0.1
	 */
	public String getImageFolderLogo() {
		return imageFolderLogo;
	}
	/**
	 * imageFolderLogo.
	 *
	 * @param   imageFolderLogo    the imageFolderLogo to set
	 * @since   v0.0.1
	 */
	public void setImageFolderLogo(String imageFolderLogo) {
		this.imageFolderLogo = imageFolderLogo;
	}
	/**
	 * imageList.
	 *
	 * @return  the imageList
	 * @since   v0.0.1
	 */
	public List<PhotoFileModel> getImageList() {
		return imageList;
	}
	/**
	 * imageList.
	 *
	 * @param   imageList    the imageList to set
	 * @since   v0.0.1
	 */
	public void setImageList(List<PhotoFileModel> imageList) {
		this.imageList = imageList;
	}
}
