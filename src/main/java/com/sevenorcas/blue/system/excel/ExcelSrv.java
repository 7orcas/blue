package com.sevenorcas.blue.system.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.file.FileSrv;
import com.sevenorcas.blue.system.lang.ent.LabelUtil;

/**
* Create an excel file 
* 
* [Licence]
* Created 27.8.22
* @author John Stewart
*/
@Stateless
public class ExcelSrv extends BaseSrv {
	
	@EJB
	private FileSrv fileSrv;
	
	public String createListFile(
			String filename,
			Integer orgNr,
			ExportListI list) throws Exception{
		
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

			//Data
			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);
			
			for (int r=0;r<list.getRowCount(sheetIdx);r++) {
				Row row = sheet.createRow(1+r);
				for (int c=0;c<cols.size();c++) {
					Column col = cols.get(c);
					Cell cell = row.createCell(c);
					cell.setCellStyle(style);
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
		font.setFontHeightInPoints((short) 14);
		font.setBold(true);
		headerStyle.setFont(font);
		return headerStyle;
	}
	
	/**
	 * Read in an excel workbook
	 * @param filename
	 * @param labels
	 * @return
	 * @throws Exception
	 */
	public ExcelImport readListFile(String filename, LabelUtil labels) throws Exception{
	
		if (filename.toLowerCase().endsWith("xlsx")) {
			return readListFileXlsx(filename, labels);
		}
		
		throw new RedException("errunk", "Unknown file extension");
	}
	
	
	private ExcelImport readListFileXlsx(String filename, LabelUtil labels) throws Exception{

		FileInputStream file = null;
		Workbook workbook = null;
		ExcelImport excel = new ExcelImport(labels);		
		
		try {
			
			file = new FileInputStream(new File(filename));
			workbook = new XSSFWorkbook(file);
			
			for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
			    Sheet sheet = workbook.getSheetAt(s);
			    excel.addSheet(sheet.getSheetName());
			    
			    int r = 0;
			    for (Row row : sheet) {
			    	ArrayList<Object> objs = new ArrayList<Object>();
			    	
			        for (Cell cell : row) {
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
			                default: objs.add(null);
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
