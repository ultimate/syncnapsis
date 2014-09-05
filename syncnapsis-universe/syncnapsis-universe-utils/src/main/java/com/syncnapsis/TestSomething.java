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
package com.syncnapsis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.syncnapsis.utils.math.Functions;

public class TestSomething
{
	public static void main(String[] args)
	{
		Point3d p = new Point3d(1, 0.5, 0);
		Matrix4d m = new Matrix4d();
		System.out.println(p);
		System.out.println(m);

		System.out.println("rotZ");
		m.rotZ(Math.PI / 2);
		m.transform(p);
		System.out.println(p);

		System.out.println("rotX");
		m.rotX(Math.PI / 2);
		m.transform(p);
		System.out.println(p);

		System.out.println("shift");
		m.setTranslation(new Vector3d(2, 3, 4));
		m.transform(p);
		System.out.println(p);
		
		double d;
		int max = 1000;
		for(int i = 0; i < max; i++)
		{
			d = i / (double) max;
			System.out.println(d + " | " + Math.round(Functions.gauss(d)*1000)/1000.0 + " | " + Math.round(Functions.gaussModified(d)*1000)/1000.0);
		}

		final List<Integer> l = Collections.synchronizedList(new ArrayList<Integer>());

		int th = 10;
		List<Thread> threads = new ArrayList<Thread>(th);
		for(int i = 0; i < th; i++)
		{
			threads.add(new Thread(new Runnable() {

				@Override
				public void run()
				{
					for(int j = 0; j < 1000; j++)
						l.add(j);
				}
			}));
		}

		for(Thread t : threads)
		{
			t.start();
		}
		for(Thread t : threads)
		{
			try
			{
				t.join();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		System.out.println("List-size=" + l.size());
	}
}
