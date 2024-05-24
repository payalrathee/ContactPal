package com.contactManager.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileHandlerService {

	public String uploadFile(MultipartFile file) throws IOException {
		
		String filename = "";
		if (!file.isEmpty()) {
			
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String timestamp = dateFormat.format(now);

			filename = file.getOriginalFilename();
			filename = timestamp + "_" + filename;

			ClassPathResource resource = new ClassPathResource("/static/images/uploads/");
			File targetFile = resource.getFile();

			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}

			Path path = Paths.get(targetFile.getAbsolutePath(), filename);

			InputStream iStream = file.getInputStream();

			Files.copy(iStream, path, StandardCopyOption.REPLACE_EXISTING);

		}
		
		return filename;
	}
	
	public void deleteFile(String filename) throws IOException {
		if (filename != null && !filename.isEmpty()) {
	        
	          File saveFile = new ClassPathResource("/static/images/uploads/").getFile();
	          Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + filename);
	          Files.deleteIfExists(path);
	              
	      }
	}

}
