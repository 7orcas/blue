package com.sevenorcas.blue.system.lang.ent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.sevenorcas.blue.system.excel.BaseExcel;
import com.sevenorcas.blue.system.excel.Column;
import com.sevenorcas.blue.system.excel.ExcelImport;
import com.sevenorcas.blue.system.excel.ExportListI;
import com.sevenorcas.blue.system.exception.RedException;

/**
* Create a standard label excel file 
* Used in both export and import
* 
* [Licence]
* Created 03.09.22
* @author John Stewart
*/
public class LabelExcel extends BaseExcel implements ExportListI {

	final private static String SHEET_LANGKEY = "labels";
	private List<LabelDto> table;
	private int sheet;
	

	/**
	 * Export Constructor
	 * @param labels
	 * @param table
	 * @throws Exception
	 */
	public LabelExcel (LabelUtil labels,
			List<LabelDto> table) throws Exception {
		super (labels);
		addSheet(0, SHEET_LANGKEY);
		this.table = table;
	}

	/**
	 * Import Constructor
	 * @param labels
	 * @param import excel
	 * @throws Exception
	 */
	public LabelExcel (LabelUtil labels,
			ExcelImport excel) throws Exception {
		super (labels, excel);		
		sheet = excel.findSheetByLangKey(SHEET_LANGKEY);
		if (sheet == -1) {
			throw new RedException("invexcel", "Can't find sheet name: " + getLabel(SHEET_LANGKEY, true));
		}
		addSheet(sheet, SHEET_LANGKEY);
		
		//Get Column indexes
		initialiseColumns();
		List<Column> cols = getColumns (sheet);
		for (int i=0;i<cols.size();i++) {
			int index = excel.findColumnByLangKey(sheet, cols.get(i).getLangKey());
			if (index == -1) {
				throw new RedException("invexcel", "Can't find column name: " + getLabel(cols.get(i).getLangKey(), true));
			}
			cols.get(i).setIndex(index);
		}
		
	}
	
	public void initialiseColumns() {
		List<Column> cols = new ArrayList<>();
		cols.add(addColumn(-1, "id", 1500, LONG));
		cols.add(addColumn(-1, "lang", 2000, STRING));
		cols.add(addColumn(-1, "orgnr-s", 2000, INTEGER));
		cols.add(addColumn(-1, "langkey", 6000, STRING));
		cols.add(addColumn(-1, "label", 20000, STRING));
		if (isImportComment()) {
			cols.add(addColumn(-1, "comment", 20000, STRING));
		}
		setColumns(0, cols);
	}
	
	
	/**
	 * Populate the label DTO's with changes
	 */
	public void populate (List<LabelDto> table) throws Exception {
		this.table = table;
		
		int idx = findColumnByLangKey(sheet, "id"),
			langx = findColumnByLangKey(sheet, "lang"),
			orgx = findColumnByLangKey(sheet, "orgnr-s"),
			langkeyx = findColumnByLangKey(sheet, "langkey"),
			labelx = findColumnByLangKey(sheet, "label");
		
		Hashtable<Long, LabelDto> dtos = listToHashtableId(table);
		
		for (int r=0;r<excel.getRowCount(sheet);r++) {
			
			Long id = excel.getCellLong(sheet, r, idx);
			Integer org = excel.getCellInteger(sheet, r, orgx);
			String lang = excel.getCellString(sheet, r, langx);
			String langkey = excel.getCellString(sheet, r, langkeyx);
			String label = excel.getCellString(sheet, r, labelx);
			
			LabelDto dto = null;
			
			//New 
			if (id == null) {
				dto = new LabelDto();	
				table.add(dto);
			}
			//Existing
			else {
				dto = dtos.get(id);	
			}
			
			//Find changes
			if (!isSameNonNull(org, dto.getOrgNr())) {
				dto.setOrgNr(org)
				   .addImportComment("Can't change org nr") 
				   .setChanged() 
				   .setValid(false);
			}
			if (!isSameNonNull(langkey, dto.getCode())) {
				dto.setCode(langkey)
				   .addImportComment("Can't change lang key")
				   .setChanged() 
				   .setValid(false);
			}
			if (!isSameNonNull(lang, dto.getLang())) {
				dto.setLang(lang)
				   .addImportComment("Can't change language")
				   .setChanged() 
				   .setValid(false);
			}
			if (!isSameNonNull(label, dto.getLabel())) {
				dto.setLabel(label)
				   .setChanged() 
				   .setValid(isNotEmpty(label))
				   .addImportComment(isNotEmpty(label)?"":"Label can't be blank");
			}
			
			if (dto.isChanged()) {
				setIsChanged(true);

				if (!dto.isValid()) {
					setIsInvalid(true);
				}
			}
		}
	}
	
	
	public Integer getRowCount(int sheet) {
		return table.size();
	}
	
	/**
	 * Excel values
	 * @param index sheet number
	 * @param row
	 * @param column
	 */
	public Object getCell(int sheet, Integer row, Integer col) {
		LabelDto l = table.get(row);
		switch (col) {
			case 0: return l.getId();
			case 1: return l.getLang()!=null? l.getLang() : getLanguage();
			case 2: return l.getOrgNr();
			case 3: return l.getCode();
			case 4: return l.getLabel();
			case 5: return l.getImportComment();
			default: return "ERROR";
		}
	}
}
