package com.sevenorcas.blue.system.file;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseSrv;

/**
* Created 27.8.22
* 
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Stateless
public class FileSrv extends BaseSrv {
	
	/**
	 * Temporary directories are by org number
	 * 
	 * @param orgNr
	 * @return
	 * @throws Exception
	 */
	public Path getTempDirectory(
			Integer orgNr) throws Exception{
		String dir = appProperties.get("TempDirectory") + orgNr.toString();
		Path path = Paths.get(dir);
		
		if (!Files.isDirectory(path)) {
			path = Files.createDirectory(path);
		}
		
		return path;
	}
	
	/**
	 * Convenience method to format a filename with extension
	 * @param filename
	 * @param ext
	 * @return
	 * @throws Exception
	 */
	public String getFilename (
			String filename, 
			String ext) throws Exception {
		
		if (filename.endsWith(ext)) {
			return filename;
		}
		return filename + "." + ext;
	}
	
	/**
	 * Get generated file from the temporary directory and return as a Response object
	 * 
	 * @param path + filename
	 * @param filename of returning file
	 * @param true == delete file
	 * @return
	 * @throws Exception
	 */
	public Response getFile(String path, String filename, boolean delete) throws Exception{
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		Response res = Response.ok(org.apache.commons.io.IOUtils.toByteArray(fis))
				.header("Content-Disposition","attachment; filename=" + filename)
				.build();
		fis.close();
		if (delete){
			file.delete();
		}
		return res;
	}
	
	
}
