package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Service
public class FileService {

	@Autowired
	private FileMapper fileMapper;

	/**
	 * Saves a new file
	 * @param file
	 * @param userId
	 * @throws DuplicateFileNameException
	 * @throws Exception
	 */
	public void storeFile(MultipartFile file, Integer userId) throws DuplicateFileNameException, Exception {
		String fileName = file.getOriginalFilename();
		if (!isFileNameAvailable(fileName, userId)) {
			throw new DuplicateFileNameException("A file with the same name already exists.");
		}
		try {
			File newFile = new File(fileName, file.getContentType(), file.getSize(), file.getBytes(), userId);
			fileMapper.insert(newFile);
		} catch (Exception e) {
			throw new Exception("Failed to store file: " + e.getMessage());
		}
	}

	/**
	 * fetches all files for a user
	 * @param userId
	 * @return
	 */
	public List<File> getFiles(Integer userId) {

		return fileMapper.getFiles(userId);
	}

	/**
	 * Fetches a specific file
	 * @param fileId
	 * @return
	 */
	public File getFile(Integer fileId) {
		return fileMapper.getFile(fileId);
	}

	/**
	 * Deletes a specific file
	 * @param fileId
	 * @param userId
	 */
	public void deleteFile(Integer fileId, Integer userId) {
		fileMapper.deleteFile(fileId, userId);
	}

	/**
	 * Checks for duplicate file name
	 * @param fileName
	 * @param userId
	 * @return
	 */
	public boolean isFileNameAvailable(String fileName, Integer userId) {
		Integer count = fileMapper.countByFileName(fileName, userId);
		return count == 0;
	}

	/**
	 * Custom Exception for duplicate file name
	 *
	 */
	public static class DuplicateFileNameException extends Exception {
		private static final long serialVersionUID = 1L;

		public DuplicateFileNameException(String message) {
			super(message);
		}
	}

}
