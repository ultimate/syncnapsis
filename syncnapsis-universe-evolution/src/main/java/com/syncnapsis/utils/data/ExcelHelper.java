/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
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
