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
package com.syncnapsis.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.impl.GalaxyManagerImpl;
import com.syncnapsis.tests.annotations.Untested;
import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.IconUtil;

@Untested
@SuppressWarnings("unused")
public class TestSomething
{
	public static void main(String[] args) throws Exception
	{
		File folder = new File("D:/info/syncnapsis/syncnapsis-examples/syncnapsis-examples-ui-playground/src/main/webapp");

		Map<String, List<Vector<Integer>>> galaxies = new HashMap<String, List<Vector<Integer>>>();
		List<Vector<Integer>> sectors;

		for(File f : folder.listFiles())
		{
			if(f.getName().startsWith("sectors"))
			{
				System.out.print(f.getName());
				sectors = new ArrayList<Vector<Integer>>(100000);

				BufferedReader br = new BufferedReader(new FileReader(f));
				String line;
				String nextLine;
				nextLine = br.readLine();
				// when checking next line, last line can be skipped
				while(nextLine != null)
				{
					line = nextLine;
					nextLine = br.readLine();
					if(!line.startsWith("\t["))
						continue;

					line = line.substring(2, line.indexOf(']'));

					StringTokenizer st = new StringTokenizer(line, ",");
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					int z = Integer.parseInt(st.nextToken());
					sectors.add(new Vector.Integer(x,y,z));
				}
				br.close();
				
//				System.out.println(" --> " + sectors.size() + " coords loaded!");

				GalaxyManagerImpl.calculateSize();
				System.out.println("\t" + sectors.size());
//				System.out.println("\t" + xSize);
//				System.out.println("\t" + ySize);
//				System.out.println("\t" + zSize);
			}
		}
		
		
		
		// createIconFile("ultimate");
		// createIconFile("moronicjoker");
		// createIconFile("X");
		// createIconFile("no");
		// createIconFile("foo");
		// createIconFile("bazz");
		// createIconFile("thing");
		// createIconFile("abcdef");
		// createIconFile("nothing");
	}

	private static void createIconFile(String s) throws IOException
	{
		FileUtil.writeFile(new File(s + ".svg"), IconUtil.createIcon(s));
	}
}
