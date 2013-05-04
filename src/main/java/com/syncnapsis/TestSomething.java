package com.syncnapsis;

import java.util.Random;

public class TestSomething
{
	public static void main(String[] args)
	{
		Random r = new Random();
		long seed1 = System.currentTimeMillis();
		Random rS11 = new Random(seed1);
		Random rS12 = new Random(seed1); 
		long seed2 = "syncnapsis".hashCode();
		Random rS21 = new Random (seed2);
		Random rS22 = new Random (seed2);
		Random rS23 = new Random (seed2);
		Random rS24 = new Random (seed2);
		
		print1(r);
		print1(rS11);
		print1(rS12);
		print1(rS21);
		print1(rS22);
		print2(rS23);
		print3(rS24);
	}
	
	private static void print1(Random r)
	{
		System.out.print(r.nextInt() + "   ");
		System.out.print(r.nextDouble() + "   ");
		System.out.print(r.nextFloat() + "   ");
		System.out.print(r.nextGaussian() + "   ");
		System.out.print(r.nextInt() + "   ");
		System.out.println("-----------");
	}
	private static void print2(Random r)
	{
		System.out.print(r.nextInt() + "   ");
		System.out.print(r.nextLong() + "   ");
		System.out.print(r.nextFloat() + "   ");
		System.out.print(r.nextLong() + r.nextLong() + "   ");
		System.out.print(r.nextInt() + "   ");
		System.out.println("-----------");
	}
	private static void print3(Random r)
	{
		System.out.print(r.nextLong() + "   ");
		System.out.print(r.nextInt() + "   ");
		System.out.print(r.nextInt() + "   ");
		System.out.print(r.nextLong() + r.nextLong() + "   ");
		System.out.print(r.nextInt() + "   ");
		System.out.println("-----------");
	}
	
}
