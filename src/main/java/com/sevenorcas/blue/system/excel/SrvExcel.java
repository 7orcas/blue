package com.sevenorcas.blue.system.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.excel.ent.Column;
import com.sevenorcas.blue.system.excel.ent.ExcelI;
import com.sevenorcas.blue.system.excel.ent.ExcelImport;
import com.sevenorcas.blue.system.excel.ent.ExportListI;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.file.SrvFile;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;

/**
* Create an excel file 
* 
* [Licence]
* Created 27.8.22
* @author John Stewart
*/
@Stateless
public class SrvExcel extends BaseService {
	
	@EJB
	private SrvFile fileSrv;
	
	public String createListFile(
			String filename,
			Integer orgNr,
			ExportListI list) throws Exception{
		
		list.initialiseColumns();
		Workbook workbook = new XSSFWorkbook();

		for (int sheetIdx=0;sheetIdx<list.getSheetCount();sheetIdx++) {
			Sheet sheet = workbook.createSheet(list.getSheetName(sheetIdx));
			List<Column> cols = list.getColumns(sheetIdx);
			
			//Header
			CellStyle headerStyle = createHeaderStyle(workbook);
			Row header = sheet.createRow(0);
			
			for (int j=0;j<cols.size();j++) {
				Column col = cols.get(j);
				sheet.setColumnWidth(j, col.getWidth());
				Cell headerCell = header.createCell(j);
				headerCell.setCellValue(col.getLabel());
				headerCell.setCellStyle(headerStyle);
			}

			CellStyle style = workbook.createCellStyle();
			CellStyle styleRed = createRedStyle (workbook);
			style.setWrapText(true);
			
			//Data
			for (int r=0;r<list.getRowCount(sheetIdx);r++) {
				Row row = sheet.createRow(1+r);
				CellStyle styleX;
				
				if (list.isRowInvalid(sheetIdx, r)) {
					styleX = styleRed;	
				} 
				else {
					styleX = style;
				}
				
				for (int c=0;c<cols.size();c++) {
					Column col = cols.get(c);
					Cell cell = row.createCell(c);
					cell.setCellStyle(styleX);
					Object o = list.getCell(sheetIdx,r,c);
					
					switch (col.getCellClass()) {
			            case ExcelI.STRING:  cell.setCellValue((String)o); break;
			            case ExcelI.INTEGER: cell.setCellValue((Integer)o); break;
			            case ExcelI.LONG:    cell.setCellValue((Long)o); break;
			            case ExcelI.BOOLEAN: cell.setCellValue((Boolean)o); break;
			            default: cell.setCellValue(o != null? o.toString() : (String)null);
			        }
				}
			}
		}
		
		
		Path path = fileSrv.getTempDirectory(orgNr);
		String fileLocation = path + "/" + fileSrv.getFilename(filename, "xlsx");

		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		workbook.write(outputStream);
		workbook.close();

		return fileLocation;
    }

	/**
	 * Standard header format for a cell
	 * @param workbook
	 * @return
	 */
	private CellStyle createHeaderStyle (Workbook workbook) {
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		headerStyle.setFont(font);
		return headerStyle;
	}
	
	private CellStyle createRedStyle (Workbook workbook) {
		CellStyle styleRed = workbook.createCellStyle();
		Font headerFont = workbook.createFont();
		headerFont.setColor(IndexedColors.RED.getIndex());
		styleRed.setFont(headerFont);
		return styleRed;
	}
	
	/**
	 * Read in an excel workbook
	 * @param filename
	 * @param labels
	 * @return
	 * @throws Exception
	 */
	public ExcelImport readListFile(String filename, UtilLabel labels) throws Exception{
		if (filename.toLowerCase().endsWith("xlsx") 
				|| filename.toLowerCase().endsWith("xls")) {
			return readListFileX(filename, labels);
		}
		throw new RedException("errunk", "Unknown file extension");
	}
	
	
	private ExcelImport readListFileX(String filename, UtilLabel labels) throws Exception{

		FileInputStream file = null;
		Workbook workbook = null;
		ExcelImport excel = new ExcelImport(labels);		
		
		try {
			
			file = new FileInputStream(new File(filename));
			if (filename.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(file);
			}
			if (filename.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			
			for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
			    Sheet sheet = workbook.getSheetAt(s);
			    excel.addSheet(sheet.getSheetName());
			    
			    int r = 0;
			    for (Row row : sheet) {
			    	ArrayList<Object> objs = new ArrayList<Object>();
			    	
		        	for (int c=0; c<row.getLastCellNum(); c++) {
		                Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
		                
		                if (cell == null) {
		                	objs.add(ExcelI.NULL);	
		                }
		                else {
				            switch (cell.getCellType()) {
				                case STRING: objs.add(cell.getRichStringCellValue().getString()); break;
				                case NUMERIC: 
				                	if (DateUtil.isCellDateFormatted(cell)) {
				                		objs.add(cell.getDateCellValue());
				                	} else {
				                		objs.add(cell.getNumericCellValue());
				                	}
				                	break;
				                case BOOLEAN: objs.add(cell.getBooleanCellValue()); break;
				                default: objs.add(ExcelI.NULL);
				            }
		                }
		            }
			        
			        if (r++ == 0) {
			        	excel.setColumns(s, objs);
			        } else {
			        	excel.addRow(s, objs);
			        }
			    }
			}
			
		} catch (Exception e) {
			throw new RedException("errunk", e.getMessage());		
			
		} finally {
			if (file != null) file.close();
			if (workbook != null) workbook.close();
		}
		
		
		return excel;
    }
	
}
