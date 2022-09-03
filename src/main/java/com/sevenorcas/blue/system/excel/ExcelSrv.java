package com.sevenorcas.blue.system.excel;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.file.FileSrv;
import com.sevenorcas.blue.system.lang.ent.LabelDto;

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
			ExcelListI list) throws Exception{
		
		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet(list.getSheetName());
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 4000);

		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);

		for (int j=0;j<list.getColumns().size();j++) {
			Cell headerCell = header.createCell(j);
			headerCell.setCellValue(list.getColumns().get(j));
			headerCell.setCellStyle(headerStyle);
		}
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);

		for (int i=0;i<list.getRowCount();i++) {
			Row row = sheet.createRow(2+i);
			for (int j=0;j<list.getColumns().size();j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(list.getCell(i,j));
				cell.setCellStyle(style);
			}
		}
		
		
		Path path = fileSrv.getTempDirectory(orgNr);
		String fileLocation = path + "/" + fileSrv.getFilename(filename, "xlsx");

		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		workbook.write(outputStream);
		workbook.close();

		return fileLocation;
    }
	
	
}
