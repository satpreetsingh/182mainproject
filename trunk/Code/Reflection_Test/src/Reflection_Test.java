import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Reflection_Test {

	public static void describeInstance(Object object) {
		Class<?> clazz = object.getClass();

		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		Field[] fields = clazz.getDeclaredFields();
		Method[] methods = clazz.getDeclaredMethods();

		System.out.println("Description for class: " + clazz.getName());
		System.out.println();
		System.out.println("Summary");
		System.out.println("-----------------------------------------");
		System.out.println("Constructors: " + (constructors.length));
		System.out.println("Fields: " + (fields.length));
		System.out.println("Methods: " + (methods.length));

		System.out.println();
		System.out.println();
		System.out.println("Details");
		System.out.println("-----------------------------------------");

		if (constructors.length > 0) {
			System.out.println();
			System.out.println("Constructors:");
			for (Constructor<?> constructor : constructors) {
				System.out.println(constructor);
			}
		}

		if (fields.length > 0) {
			System.out.println();
			System.out.println("Fields:");
			for (Field field : fields) {
				System.out.println(field);
			}
		}

		if (methods.length > 0) {
			System.out.println();
			System.out.println("Methods:");
			for (Method method : methods) {
				System.out.println(method);
			}
		}
	}

	public static void main(String[] args) {

		/* Create the JFileChooser component, and set the directory to the user directory */
		JFileChooser FileChooser = new JFileChooser(System.getProperty("user.dir"));

		/* Initiate the classes to null */
		Class NewClass = null;
		Object GenericObject = null;

		/* Now load the shape class */
		if  (FileChooser.showDialog(null, "Open Shape file") == JFileChooser.APPROVE_OPTION) {
			System.out.println(FileChooser.getSelectedFile().getName());
			String filename = FileChooser.getSelectedFile().getName();

			/* Trim off the extension */
			filename = filename.substring(0, filename.indexOf("."));

			/* Add the results as optional output (defined by user preferences) */
			System.out.println("[actionPerformed] Filename = " + filename);

			CompilingClassLoader classloader = new CompilingClassLoader();

			/* Load the class */
			try {
				NewClass = classloader.loadClass(filename);
			} catch (ClassNotFoundException e1) {
				System.out.println("Cannot load class = " + filename);
			}			
		}


//		try {
//			NewClass = Class.forName("MyThreePointShape");
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}
//		System.out.println("[NewClass] " + NewClass );
		
		
//		Constructor[] cstr = NewClass.getDeclaredConstructors();
		Object newObject = null;

		if (NewClass != null) {
			try {
				newObject = NewClass.newInstance();
				System.out.println("[describeInstance(newObject)]");
				describeInstance(newObject);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
//		MyTwoPointShape advRect = (MyTwoPointShape) newObject;
//		System.out.println("[describeInstance(advRect)]");
//		describeInstance(advRect);

	}
}


