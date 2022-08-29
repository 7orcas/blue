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

public class FileUploadServlet extends BaseServlet {
    
    private static String [] VALID_TYPES = new String [] {"csv", "xls", "xlsx"}; //Keep lower case
	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());    
    
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
System.out.println("Upload servlet NO SESSION");    		
//    		return;
    	}
//    	UserParam params = RestParamPreProcess.getUserParam(request);
//    	if (params == null){
//    	    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//    	    return;
//    	}
//    	
//    	Company comp = null;
//		companyService = Utilities.lookupService(companyService, "CompanyServiceImp");
//		try {
//            comp = companyService.findByNr(params, params.getCompany());
//		} catch (Exception e) {
//            ApplicationLog.error(e);
//        }
		
    	
    	String path = appProperties.get("ImportFileDirectory");
    	if (!path.endsWith(File.separator)){
    	    path += File.separator;
    	}
    	
     	// Save file to directory ---------------------------------------------------------
    	try {
    		
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                
                // Process form file field (input type="file").
                String filename = FilenameUtils.getName(item.getName());
                
                if (!isValidType(filename)){
                	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            		return;
                }
                
                Path pathX = Paths.get(path);
                if (!Files.isDirectory(pathX)) {
        			pathX = Files.createDirectory(pathX);
        		}
                
                filename = pathX.toString() + "/" + filename;
                
                File file = new File(filename);
//                Utilities.mkdirs(file); 
                
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try{
                	inputStream = item.getInputStream();
                	outputStream = new FileOutputStream(new File(filename));
                    
            		int read = 0;
            		byte[] bytes = new byte[1024];
             
            		while ((read = inputStream.read(bytes)) != -1) {
            			outputStream.write(bytes, 0, read);
            		}
            		
            		
//            		ObjectMapper o = new ObjectMapper();
//                	response.getWriter().write(o.writeValueAsString(filename));
                	
            		path = request.getRequestURL().toString();
path = path.replaceFirst("upload", "api") + "?filename=" + filename;
//path = "/blue/api/client-nr0/lang/upload"; // + "?filename=" + filename;



					path = "/api/client-nr0/lang/upload"  + "?filename=" + filename; //goes to filter 3
					request.setAttribute("HttpSession", ses);
response.setHeader("Content-Type", "application/json");
response.setHeader("Accept", "application/json");
					
//path = "/blue/api/client-nr0/lang/upload";
					
//path = "/lang/upload";
System.out.println("forward url=" + path);            		
//            		RequestDispatcher rd = request.getRequestDispatcher(path);
					RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
					rd.forward(request, response);
//					rd.include(request, response);
					

//path = request.getContextPath() + "/" + path;
//System.out.println("redirect url=" + path);
//					response.sendRedirect(path);
            		
//                	response.setStatus(HttpServletResponse.SC_OK);
//                	response.setContentType("application/json;charset=UTF-8");
                	
                	
            		
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
