package com.blogapp.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.services.FileService;
@Service
public class FileServiceImpl implements FileService {
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
 	String fileName=file.getOriginalFilename();
	String filePath=path+File.separator+fileName;
	InputStream ios=file.getInputStream();
	System.out.println(ios);
	byte data[]=new byte[ios.available()];
	
	FileOutputStream fos=new FileOutputStream(filePath);
	fos.write(data);
	fos.flush();
	fos.close();
	return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullPath);
		
		return is;
	}
}
