package com.sevenorcas.blue.system.file;

import java.nio.file.Path;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseServiceI;

/**
* File and directory utilities interface
* [Licence]
* Created 27.8.22
* @author John Stewart
*/
@Local
public interface SFileI extends BaseServiceI {
	public Path getTempDirectory(Integer orgNr) throws Exception;
	public String getFilename (String filename, String ext) throws Exception;
	public Response getFile(String path, String filename, boolean delete) throws Exception;
}
