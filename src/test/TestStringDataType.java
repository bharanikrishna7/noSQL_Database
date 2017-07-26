package test;

import java.util.Arrays;
import java.util.HashSet;

import utilities.StringHelper;
import dataTypes.StringDataType;

public class TestStringDataType {
	public static void main(String[] args) {
		System.out.println(StringHelper.Title("TESTING BOOLEAN DATA TYPE", '='));
		System.out.println(StringHelper.SubTitle("Create StringDataType Object", '-'));
		StringDataType object = new StringDataType("Darth Vadar", new HashSet<String>(Arrays.asList("string", "data", "test")));
		System.out.println("Created StringDataType Object with value \"TRUE\" and Tags : {string, data, test}" + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test GetType Method", '-'));
		System.out.println("Type : " + object.GetType() + "\n\n");

		System.out.println(StringHelper.SubTitle("Test Length Method", '-'));
		System.out.println("New Value : " + object.Length() + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test Concat Method", '-'));
		System.out.println("New Value : " + object.Concat(" Sith Lord") + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test ConcatTo Method", '-'));
		System.out.println("New Value : " + object.ConcatTo("Great ") + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test Substring Method", '-'));
		System.out.println("New Value : " + object.Substring(6) + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test Substring Method", '-'));
		System.out.println("New Value : " + object.Substring(6, 17) + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test ToString Method", '-'));
		System.out.println("New Value : " + object.ToString() + "\n\n");
		
		System.out.println("[Status] Testing Complete ...");
	}
}
