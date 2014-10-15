package com.wpy.multithreadeddownload.entity;

/**
 * 
 * 项目名称：MultithreadedDownload 类名称：File 类描述： 文件的信息 创建人：wpy 创建时间：2014-10-10
 * 上午11:28:26
 * 
 */
public class DownloadFile {

	private String name;
	private String path;
	private String img_path;
	private String content;

	public DownloadFile(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public DownloadFile() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "File [name=" + name + ", path=" + path + ", img_path="
				+ img_path + ", content=" + content + "]";
	}

}
