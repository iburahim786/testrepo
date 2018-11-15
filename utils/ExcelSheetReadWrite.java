package com.cisco.ui.test.compliance.wrappers.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelSheetReadWrite {
	
	public String[][] Datainput(String DatasheetName) throws IOException{
		
		String [][] data=null;
		
		FileInputStream fis = new FileInputStream(new File("./src/"+ DatasheetName + ".xlsx"));
		
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		XSSFSheet sheet= workbook.getSheetAt(0);
		
		
		//Row Count
		
		int rCount= sheet.getLastRowNum();
		
		//Column Count
		
		int cCount= sheet.getRow(0).getLastCellNum();
		
		for (int i=1;i<rCount+1;i++){
		   try {	
			XSSFRow  row = sheet.getRow(i);
		      for (int j=0;j<cCount;j++){
		    	  String cellValue="";
		    	  try {
		    		  cellValue=row.getCell(j).getStringCellValue();
		    	  }
		    	  catch (NullPointerException e){
		    		  e.printStackTrace();
		    	  }
		    		data[i-1][j]=cellValue;  
		    	  }
		      }
		   catch (Exception e){
			   e.printStackTrace();
		   }
			   
		}
		
		return data;
		
	}
}