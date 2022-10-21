package com.sevenorcas.blue.system.file;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseService;

/**
* File and directory utilities
* 
* [Licence]
* Created 27.8.22
* @author John Stewart
*/

@Stateless
public class SFileImp extends BaseService implements SFile {
	
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
	
	
	/**
     * Get unique file name by appending an incremental number 
     * @param filename
     * @return
     */
	static public String filenameIncrement (String filename){
		return filenameIncrement(filename, 0);
	}
	
	/**
     * Get unique file name by appending an incremental number 
     * @param filename
     * @param current increment
     * @return
     */
    static private String filenameIncrement (String filename, int counter){
    	
    	String file = null;
    	String ext = null;
        
        int index = filename.lastIndexOf(".");
        if (index != -1){
        	file = filename.substring(0, index);
        	ext = filename.substring(index);
        }
        else{
        	file = filename;
        	ext = "";
        }
        
        String filename1 = file +  
                           (counter > 0? "-" + counter : "") +
                           ext; 

        Path p = Paths.get(filename1);
        if (Files.exists(p)){
        	return filenameIncrement (filename, ++counter);
        }
        
    	return filename1;
    }
	
}

