package test;

import dataTypes.IntDataType;
import utilities.StringHelper;

public class TestIntDataType {
	
	public static void main(String[] args) {
		System.out.println(StringHelper.Title("TESTING INT DATA TYPE", '='));
		System.out.println(StringHelper.SubTitle("Create IntDataType Object", '-'));
		IntDataType object = new IntDataType(5);
		System.out.println("Created IntDataType Object with value '5'\n\n");
		
		System.out.println(StringHelper.SubTitle("Test GetType Method", '-'));
		System.out.println("Type : " + object.GetType() + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test Add Method", '-'));
		System.out.println("Add 7 to IntDataType Object");
		System.out.println("New Value : " + object.Add(7) + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test Subtract Method", '-'));
		System.out.println("Subtract 1 from IntDataType Object");
		System.out.println("New Value : " + object.Subtract(1) + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test Increment Method", '-'));
		System.out.println("New Value : " + object.Increment() + " \n\n");
		
		System.out.println(StringHelper.SubTitle("Test Decrement Method", '-'));
		System.out.println("New Value : " + object.Decrement() + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test ToString Method", '-'));
		System.out.println("New Value : " + object.ToString() + "\n\n");
		
		System.out.println("[Status] Testing Complete ...");
	}
}
