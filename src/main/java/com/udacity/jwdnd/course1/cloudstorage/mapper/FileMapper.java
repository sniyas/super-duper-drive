package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Mapper
public interface FileMapper {

	@Select("SELECT * FROM FILES WHERE userid = #{userId}")
	List<File> getFiles(Integer userId);

	@Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
	File getFile(Integer fileId);

	@Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) "
			+ "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int insert(File file);

	@Select("SELECT * FROM FILES WHERE filename = #{fileName}")
	File getFileByFileName(String fileName);

	@Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
	void deleteFile(@Param("fileId") Integer fileId, @Param("userId") Integer userId);

	@Delete("DELETE FROM FILES WHERE fileName = #{fileName} AND userId = #{userId}")
	int deleteFileByFileName(String fileName, Integer userId);

	@Select("SELECT COUNT(*) FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
	public Integer countByFileName(String fileName, Integer userId);

}
