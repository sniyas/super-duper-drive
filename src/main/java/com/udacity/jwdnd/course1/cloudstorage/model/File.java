package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
	private Integer fileId;
	private String fileName;
	private String contentType;
	private Long fileSize;
	private byte[] fileData;
	private Integer userId;

	public File(Integer fileId, String fileName, String contentType, Long fileSize, Integer userId, byte[] fileData) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.fileData = fileData;
		this.userId = userId;
	}

	public File(String fileName, String contentType, Long fileSize, byte[] fileData, Integer userId) {
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.fileData = fileData;
		this.userId = userId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
