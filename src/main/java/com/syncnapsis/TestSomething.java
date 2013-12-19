package com.syncnapsis;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class TestSomething
{
	public static void main(String[] args)
	{		
		Point3d p = new Point3d(1, 0.5, 0);
		Matrix4d m = new Matrix4d();
		System.out.println(p);		
		System.out.println(m);
		
		System.out.println("rotZ");
		m.rotZ(Math.PI/2);		
		m.transform(p);
		System.out.println(p);	

		System.out.println("rotX");
		m.rotX(Math.PI/2);		
		m.transform(p);
		System.out.println(p);		

		System.out.println("shift");
		m.setTranslation(new Vector3d(2, 3, 4));
		m.transform(p);
		System.out.println(p);		
	}	
}
