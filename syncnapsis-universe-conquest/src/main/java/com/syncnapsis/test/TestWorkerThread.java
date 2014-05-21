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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.tests.annotations.Untested;

@Untested
public class TestWorkerThread
{
	private static final Logger	logger				= LoggerFactory.getLogger(TestWorkerThread.class);
	private static int			numberOfSectors		= 10000;
	private static int			numberOfPlayers		= 100;
	private static long			populationFactor	= 1000000000;
	private static long			minPopulation		= populationFactor / 10000;
	private static double		growthFactor		= 1000.0;
	private static double		lossFactor			= 1000000.0;
	private static int			cycles				= 1000;

	private static List<Sector>	sectors				= new ArrayList<Sector>(numberOfSectors);

	public static void main(String[] args)
	{
		Sector sector;
		Units units;
		Random r = new Random();
		int n;

		logger.debug("initiating systems");
		for(int i = 0; i < numberOfSectors; i++)
		{
			sector = new Sector();
			sector.size = r.nextInt(9) + 1;
			sector.growthRate = r.nextInt(9) + 1;
			sector.lastUpdated = new Date();
			n = r.nextInt(10);
			if(n < 5)
				n = 1;
			else if(n < 8)
				n = 2;
			else
				n = n - 7;
			sector.units = new ArrayList<Units>(n);
			for(int u = 0; u < n; u++)
			{
				units = new Units();
				units.amount = (((long) r.nextInt((int) populationFactor)) + minPopulation) * sector.size;
				units.arrived = new Date(System.currentTimeMillis() - r.nextInt(Integer.MAX_VALUE));
				units.lastUpdated = new Date();
				units.player = r.nextInt(numberOfPlayers);
				sector.units.add(units);
			}

			sectors.add(sector);

			if(i % (numberOfSectors / 10) == 0)
				System.out.print("|");
		}
		System.out.println("");
		logger.debug(numberOfSectors + " random sectors created");

		logger.debug("starting cyclic unit calculation");
		int cycle = 0;
		Units home = null;
		Date homeDate, now;
		long diff, limit, old, interval, total;
		long time = System.currentTimeMillis();
		int battles;
		List<Units> losers = new ArrayList<Units>(10);
		while(cycles < 0 || cycle < cycles)
		{
			logger.debug("cycle #" + (++cycle) + " (last cycle: " + (System.currentTimeMillis() - time) + "ms)");
			time = System.currentTimeMillis();
			battles = 0;
			for(Sector s : sectors)
			{
				// determine interval
				now = new Date();
				interval = now.getTime() - s.lastUpdated.getTime();
				// determine home bonus
				homeDate = now;
				home = null;
				for(Units u : s.units)
				{
					if(u.arrived.before(homeDate))
					{
						homeDate = u.arrived;
						home = u;
					}
				}
				if(home == null)
					logger.error("oops!");
				// calculate growth
				old = home.amount;
				limit = s.size * populationFactor;
				if(home.amount < limit / 2)
				{
					// exponential growth
					home.amount = (long) (home.amount * Math.pow(1.0 + s.growthRate / growthFactor, interval / 1000));
				}
				else
				{
					// reverse exponential growth
					diff = (limit - home.amount);
					diff = (long) (diff / Math.pow(1.0 + s.growthRate / growthFactor, interval / 1000));
					home.amount = limit - diff;
				}
				if(home.amount > limit)
					home.amount = limit;
				// if(s.equals(sectors.get(0)))
				// logger.debug(old + " -> " + home.amount);
				if(home.amount < old)
					logger.error("oops!");
				// calculate battle
				if(s.units.size() > 1)
				{
					battles++;
					total = 0;
					for(Units u : s.units)
						total += u.amount;
					for(Units u : s.units)
					{
						u.amount -= r.nextDouble() * total / u.amount * lossFactor;
						if(u.amount < minPopulation)
							losers.add(u);
					}
					if(losers.size() > 0)
					{
						s.units.removeAll(losers);
						losers.clear();
					}
				}
			}
			logger.debug("battles: " + battles);
		}
	}

	public static class Sector
	{
		public List<Units>	units;
		public int			size;
		public int			growthRate;
		public Date			lastUpdated;
	}

	public static class Units
	{
		public long	amount;
		public Date	lastUpdated;
		public Date	arrived;
		public int	player;
	}
}
