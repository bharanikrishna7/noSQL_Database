package test;

import utilities.StringHelper;

import java.util.Arrays;
import java.util.HashSet;

import dataTypes.BooleanDataType;

public class TestBooleanDataType {
	public static void main(String[] args) {
		System.out.println(StringHelper.Title("TESTING BOOLEAN DATA TYPE", '='));
		System.out.println(StringHelper.SubTitle("Create BooleanDataType Object", '-'));
		BooleanDataType object = new BooleanDataType(true, new HashSet<String>(Arrays.asList("boolean", "data", "test")));
		System.out.println("Created BooleanDataType Object with value \"TRUE\" and Tags : {boolean, data, test}" + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test GetType Method", '-'));
		System.out.println("Type : " + object.GetType() + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test Invert Method", '-'));
		System.out.println("New Value : " + object.Invert() + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test ToString Method", '-'));
		System.out.println("New Value : " + object.ToString() + "\n\n");
		
		System.out.println("[Status] Testing Complete ...");
	}
}
