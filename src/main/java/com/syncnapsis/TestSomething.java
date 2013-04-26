package com.syncnapsis;

public class TestSomething
{
	public static void main(String[] args)
	{
		int xMin = -2;
		int xMax = 5;
		int yMin = -4;
		int yMax = 10;
		int zMin = -6;
		int zMax = 15;

		int i;
		int count = 0;
		int x2, y2, z2;
		for(int x = xMin; x <= xMax; x++)
		{
			for(int y = yMin; y <= yMax; y++)
			{
				for(int z = zMin; z <= zMax; z++)
				{
					i = ((x - xMin) * (yMax - yMin + 1) + (y - yMin)) * (zMax - zMin + 1) + (z - zMin);
					z2 = i % (zMax - zMin + 1) + zMin;
					y2 = (i - (z2 - zMin)) / (zMax - zMin + 1) % (yMax - yMin + 1) + yMin;
					x2 = (((i - (z2 - zMin)) / (zMax - zMin + 1)) - (y2 - yMin)) / (yMax - yMin + 1) + xMin;
					System.out.println("x=" + x + " y=" + y + " z=" + z + " -- i=" + i + " vs. " + count + " -- x2=" + x2 + " y2=" + y2 + " z2=" + z2);
					if(x != x2)	return;
					if(y != y2)	return;
					if(z != z2)	return;
					if(i != count)	return;
					count++;
				}
			}
		}
		System.out.println("done!");
	}
}
