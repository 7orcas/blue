package com.sevenorcas.blue.system.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseServlet;
import com.sevenorcas.blue.system.lifecycle.Filter2UrlRedirect;
import com.sevenorcas.blue.system.login.ClientSession;

/**
 * Upload files to the server and forward request for processing
 * 
 * [Licence]
 * Created 29/08/22
 * @author John Stewart
 */

public class FileUploadServlet extends BaseServlet {
    
	private static final long serialVersionUID = 1L;
	private static String [] VALID_TYPES = new String [] {"csv", "xls", "xlsx"}; //Keep lower case
	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());    
    private String importFileDir;
	
	@Override
	public void init() {
		importFileDir = appProperties.get("ImportFileDirectory");
    	if (!importFileDir.endsWith(File.separator)){
    		importFileDir += File.separator;
    	}
	}
	
    /**
     * Process POST request.
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process the actual request.
     * @param request The request to be processed.
     * @param response The response to be created.
     * @throws IOException If something fails at I/O level.
     */
    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession ses = request.getSession(false);
    	
    	if (ses == null){
    		log.debug("Call to Upload servlet with NO SESSION");    		
    		return;
    	}
    	
    	try {
    		String importFileDirX = importFileDir;
    		String url = request.getRequestURL().toString();
    		
    		//Create directory with org_nr appended (if required)
			@SuppressWarnings("unchecked")
    		Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)ses.getAttribute(CLIENT_SESSIONS);
			ClientSession cs = clientSessions.get(Filter2UrlRedirect.getClientSessionNr(url));
			
    		importFileDirX += cs.getOrgNr() + "/";
    		Path pathX = Paths.get(importFileDirX);
    		if (!Files.isDirectory(pathX)) {
    			pathX = Files.createDirectory(pathX);
    		}
    		
    		
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                
                // Process form file field (input type="file").
                String filename = FilenameUtils.getName(item.getName());
                
                if (!isValidType(filename)){
                	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            		return;
                }
                                
                filename = importFileDirX + filename;
                //If file exists then increment
                filename = FileSrv.filenameIncrement(filename);
                
                
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try{
                	
                	//Save file
                	inputStream = item.getInputStream();
                	outputStream = new FileOutputStream(new File(filename));
                    
            		int read = 0;
            		byte[] bytes = new byte[1024];
             		while ((read = inputStream.read(bytes)) != -1) {
            			outputStream.write(bytes, 0, read);
            		}
            		
            		//Get new URL to forward to
             		url = url.replaceFirst(UPLOAD_PATH, APPLICATION_PATH) + "?filename=" + filename;
            		int index = url.indexOf(APPLICATION_PATH + "/");
            		url = url.substring(index);

            		//Forward to rest for processing
  					RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
					rd.forward(request, response);
            		
                } catch (IOException e) {
                	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            		e.printStackTrace();
            		
            	} finally {
            		if (inputStream != null) {
            			try {
            				inputStream.close();
            			} catch (IOException e) {
            				e.printStackTrace();
            				log.error(e.getMessage(), e);
            			}
            		}
            		if (outputStream != null) {
            			try {
            				outputStream.close();
            			} catch (IOException e) {
            				e.printStackTrace();
            				log.error(e.getMessage(), e);
            			}
             
            		}
            	}
                
                
            }
        
        } catch (FileUploadException e) {
        	log.error("Cannot parse multipart request.", e);
            throw new ServletException("Cannot parse multipart request.", e);
        }
    	
    	
    }
  
    private boolean isValidType (String file){
    	
    	file = file.toLowerCase();
    	
    	for (String t: VALID_TYPES){
            if (file.endsWith("." + t)){
                return true;
            }
        }
        return false;
    }
    
 
}
