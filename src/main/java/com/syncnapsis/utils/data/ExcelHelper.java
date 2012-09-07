package com.syncnapsis.utils.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelHelper
{
	public static void main(String[] args) throws Exception
	{
		String fileName = "testdata.xls";
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("src/main/resources/" + fileName));
		
		HSSFSheet sheet = workbook.getSheet("Benutzer");
		HSSFRow row = sheet.getRow(0);
		Cell cell;
		
		for(int i = 2; i < 256; i++)
		{
			try
			{
				cell = row.getCell(i);
				if(cell == null)
					cell = row.createCell(i);
				cell.setCellFormula("IF(ISBLANK(Benutzer!A" + (i-1) + "),\"\",Benutzer!A" + (i-1) + ")");				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		workbook.write(new FileOutputStream("src/main/resources/new.xls"));
	}
}
