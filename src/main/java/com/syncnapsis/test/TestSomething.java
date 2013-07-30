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
import com.syncnapsis.tests.annotations.Untested;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.universe.CalculatorImpl;
import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.IconUtil;

@Untested
@SuppressWarnings("unused")
public class TestSomething
{
	public static void main(String[] args) throws Exception
	{
		final Calculator calculator = new CalculatorImpl(null); // don't need ParameterManager

		File folder = new File("D:/info/syncnapsis/syncnapsis-examples/syncnapsis-examples-ui-playground/src/main/webapp");

		Map<String, List<Vector.Integer>> galaxies = new HashMap<String, List<Vector.Integer>>();
		List<Vector.Integer> sectors;
		Vector.Integer size;
		
		StringBuilder output = new StringBuilder();

		long start;
		CalculationThread<Integer> maxGapT;
		CalculationThread<Integer> avgGapT;
		for(File f : folder.listFiles())
		{
			if(f.getName().startsWith("sectors"))
			{
				System.out.print(f.getName());
				output.append(f.getName());
				
				sectors = new ArrayList<Vector.Integer>(100000);

				start = System.currentTimeMillis();
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
					sectors.add(new Vector.Integer(x, y, z));
				}
				br.close();

				size = calculator.calculateSize(sectors);
				output.append("\t" + sectors.size());
				output.append("\t" + size.getX());
				output.append("\t" + size.getY());
				output.append("\t" + size.getZ());

//				if(sectors.size() < 30000)
				{
					final List<Vector.Integer> s = sectors;
					maxGapT = new CalculationThread<Integer>() {
						protected Integer calc()
						{
							return calculator.calculateMaxGap(s);
						}
					};
					avgGapT = new CalculationThread<Integer>() {
						protected Integer calc()
						{
							return calculator.calculateAvgGap(s);
						}
					};
					maxGapT.start();
					avgGapT.start();
					output.append("\t" + maxGapT.getResult());
					output.append("\t" + avgGapT.getResult());
				}

				output.append("\t(" + (System.currentTimeMillis() - start) + "ms)\n");
				System.out.println("\t(" + (System.currentTimeMillis() - start) + "ms)\n");
			}
		}
		
		FileUtil.writeFile(new File(folder.getAbsolutePath() + "/sector-stats.txt"), output.toString());

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

	private static abstract class CalculationThread<T> extends Thread
	{
		protected T	result;

		@Override
		public void run()
		{
			this.result = this.calc();
		}

		protected abstract T calc();

		public T getResult() throws InterruptedException
		{
			this.join();
			return this.result;
		}
	}
}
