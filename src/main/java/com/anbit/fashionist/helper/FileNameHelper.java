package com.anbit.fashionist.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FileNameHelper {
	
	public String generateUniqueNumber() {
		int min = 10000;
		int max = 99999;
		int random_int = (int) (Math.random() * (max - min + 1) + min);
		return "" + random_int;
	}
	
	public String generateFileName(String fileName) {

		// generate random alphabet
		String shortRandomAlphabet = generateUniqueNumber();

		// create date format as string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss_ddMMyyyy");
		String dateStrFormat = LocalDateTime.now().format(formatter);

		// find extension of file
		int indexOfExtension = fileName.indexOf(".");
		String extensionName = fileName.substring(indexOfExtension);

		// return new file name
		return dateStrFormat + "_" + shortRandomAlphabet + extensionName;

	}

	public String generateDisplayName(String orgFileName) throws FileNameException {
		String orgCleanPath = StringUtils.cleanPath(orgFileName);

		// Check if the file's name contains invalid characters
		if (orgCleanPath.contains(".."))
			throw new FileNameException("Sorry! Filename contains invalid path sequence " + orgCleanPath);

		// generate new file name
		return generateFileName(orgCleanPath);
	}


}
