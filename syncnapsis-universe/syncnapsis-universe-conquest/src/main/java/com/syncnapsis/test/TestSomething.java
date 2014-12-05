/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.tests.annotations.Untested;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.universe.CalculatorImpl;
import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.IconUtil;
import com.syncnapsis.utils.MathUtil;

@Untested
@SuppressWarnings("unused")
public class TestSomething
{
	public static void main(String[] args) throws Exception
	{	
		final Calculator calculator = new CalculatorImpl();

		File folder = new File("D:/info/syncnapsis/syncnapsis-examples/syncnapsis-examples-ui-playground/src/main/webapp");

		Map<String, List<Vector.Integer>> galaxies = new HashMap<String, List<Vector.Integer>>();
		List<Vector.Integer> sectors;
		Vector.Integer size;

		StringBuilder output = new StringBuilder();

		long start;
		for(File f : folder.listFiles())
		{
			if(f.getName().startsWith("sectors"))
			{
				System.out.print(f.getName());
				output.append(f.getName());

				start = System.currentTimeMillis();
				sectors = loadSectors(f);

				sortSectors(sectors);
				// getStats(calculator, sectors, output);

				output.append("\t(" + (System.currentTimeMillis() - start) + "ms)\n");
				System.out.print("\t(" + (System.currentTimeMillis() - start) + "ms)\n");
			}
		}

		// FileUtil.writeFile(new File(folder.getAbsolutePath() + "/sector-stats.txt"),
		// output.toString());

		// icon experiment...

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

	private static List<Vector.Integer> loadSectors(File file) throws IOException
	{
		List<Vector.Integer> sectors = new ArrayList<Vector.Integer>(100000);
		BufferedReader br = new BufferedReader(new FileReader(file));
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

			StringTokenizer st = new StringTokenizer(line, ", ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			sectors.add(new Vector.Integer(x, y, z));
		}
		br.close();
		return sectors;
	}

	private static void getStats(final Calculator calculator, List<Vector.Integer> sectors, StringBuilder output) throws InterruptedException
	{
		long maxMovable;
		long max = 1000 * 1000 * 1000000L;

		Vector.Integer size = calculator.calculateSize(sectors);
		output.append("\t" + sectors.size());
		output.append("\t" + size.getX());
		output.append("\t" + size.getY());
		output.append("\t" + size.getZ());

		if(sectors.size() < 30000)
		{
			final List<Vector.Integer> s = sectors;
			CalculationThread<Integer> maxGapT = new CalculationThread<Integer>() {
				protected Integer calc()
				{
					return calculator.calculateMaxGap(s);
				}
			};
			CalculationThread<Integer> avgGapT = new CalculationThread<Integer>() {
				protected Integer calc()
				{
					return calculator.calculateAvgGap(s);
				}
			};
			CalculationThread<Integer> minGapT = new CalculationThread<Integer>() {
				protected Integer calc()
				{
					return calculator.calculateMinGap(s);
				}
			};
			maxGapT.start();
			avgGapT.start();
			minGapT.start();
			output.append("\t" + avgGapT.getResult());
			output.append("\t" + maxGapT.getResult());
			output.append("\t(" + MathUtil.round(maxGapT.getResult().doubleValue() / avgGapT.getResult().doubleValue(), -1) + ":1)");
			output.append("\t" + minGapT.getResult());
			output.append("\t(1:" + MathUtil.round(avgGapT.getResult().doubleValue() / minGapT.getResult().doubleValue(), -1) + ")");

			SolarSystemPopulation originMax = getPopulation(max, 1, maxGapT.getResult());
			SolarSystemPopulation originMax2 = getPopulation(max, max / 2, maxGapT.getResult());
			SolarSystemPopulation originEq = getPopulation(max, max, maxGapT.getResult());
			SolarSystemPopulation originMin2 = getPopulation(max / 2, max, maxGapT.getResult());
			SolarSystemPopulation originMin = getPopulation(1, max, maxGapT.getResult());

			maxMovable = calculator.calculateMaxMovablePopulation(originMin2, avgGapT.getResult());
			output.append("\t" + MathUtil.round(maxMovable / (double) (max / 2), -2));

			maxMovable = calculator.calculateMaxMovablePopulation(originMax, maxGapT.getResult());
			output.append("\t" + MathUtil.round(maxMovable / (double) (max), -2));

			maxMovable = calculator.calculateMaxMovablePopulation(originMin, minGapT.getResult());
			output.append("\t" + MathUtil.round(maxMovable / (double) (1), -2));
		}
	}

	private static void sortSectors(List<Vector.Integer> sectors)
	{
		final Vector.Integer ref = sectors.get(sectors.size()/2);

		Collections.sort(sectors, new Comparator<Vector.Integer>() {
			@Override
			public int compare(com.syncnapsis.data.model.help.Vector.Integer o1, com.syncnapsis.data.model.help.Vector.Integer o2)
			{
				// @formatter:off
				int distSq1 = 	(o1.getX() - ref.getX())*(o1.getX() - ref.getX()) +
								(o1.getY() - ref.getY())*(o1.getY() - ref.getY()) + 
								(o1.getZ() - ref.getZ())*(o1.getZ() - ref.getZ()); 
				int distSq2 = 	(o2.getX() - ref.getX())*(o2.getX() - ref.getX()) +
								(o2.getY() - ref.getY())*(o2.getY() - ref.getY()) + 
								(o2.getZ() - ref.getZ())*(o2.getZ() - ref.getZ()); 
				// @formatter:on
				return (int) Math.signum(distSq1 - distSq2);
			}
		});
	}

	private static SolarSystemPopulation getPopulation(long inf, long pop, int maxGap)
	{
		SolarSystemPopulation population = new SolarSystemPopulation();
		population.setInfrastructure(new SolarSystemInfrastructure());
		population.getInfrastructure().setInfrastructure(inf);
		population.getInfrastructure().setSolarSystem(new SolarSystem());
		population.getInfrastructure().getSolarSystem().setGalaxy(new Galaxy());
		population.getInfrastructure().getSolarSystem().getGalaxy().setMaxGap(maxGap);
		population.setPopulation(pop);
		return population;
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

	private static void mapSpeedTest_toDouble(Map<String, ?> map)
	{
		String aKey = map.keySet().iterator().next();

		long start = System.currentTimeMillis();
		int count = 0;
		Object value;
		double d;
		while(count < 1000000)
		{
			value = map.get(aKey);
			if(value != null)
			{
				d = -1;
				if(value instanceof String)
					d = Double.parseDouble((String) value);
				else if(value instanceof Double)
					d = (Double) value;
				if(d > 0)
					count++;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("time needed: " + (end - start));
		System.out.println("speed: " + ((double) count * 1000 / (end - start)) + " ticks/second");
	}

	private static void mapSpeedTest_toInteger(Map<String, ?> map)
	{
		String aKey = map.keySet().iterator().next();

		long start = System.currentTimeMillis();
		int count = 0;
		Object value;
		int i;
		while(count < 1000000)
		{
			value = map.get(aKey);
			if(value != null)
			{
				if(value != null)
				{
					i = -1;
					if(value instanceof String)
						i = Integer.parseInt((String) value);
					else if(value instanceof Integer)
						i = (Integer) value;
					if(i > 0)
						count++;
				}
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("time needed: " + (end - start));
		System.out.println("speed: " + ((double) count * 1000 / (end - start)) + " ticks/second");
	}
}
