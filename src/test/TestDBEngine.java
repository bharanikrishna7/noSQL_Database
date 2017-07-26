package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import dataTypes.BaseDataType;
import dataTypes.BooleanDataType;
import dataTypes.IntDataType;
import dataTypes.StringDataType;
import dbCore.DBEngine;
import utilities.StringHelper;

public class TestDBEngine {
	@SuppressWarnings("rawtypes")
	private static ArrayList<BaseDataType> createDBObjects() {
		ArrayList<BaseDataType> objects = new ArrayList<>();
		System.out.println("Create IntDataType Object with value '5'");
		BaseDataType intObj = new IntDataType(5, new HashSet<>(Arrays.asList("integer", "data", "Fives")));
		System.out.println("Create StringDataType Object with value 'Darth Vadar'");
		BaseDataType strObj = new StringDataType("Darth Vadar", new HashSet<>(Arrays.asList("string", "data", "Siths")));
		System.out.println("Create BooleanDataType Object with value 'TRUE'");
		BaseDataType boolObj = new BooleanDataType(true, new HashSet<>(Arrays.asList("boolean", "data", "Fives")));
		
		objects.add(intObj);
		objects.add(strObj);
		objects.add(boolObj);
		
		return objects;
	}
	
	@SuppressWarnings("rawtypes")
	public static void InsertCreated(DBEngine db) {
		System.out.println(StringHelper.SubTitle("Create Objects to be Inserted into Database", '-'));
		ArrayList<BaseDataType> objects = createDBObjects();
		System.out.println("Objects Created\n\n");
		
		System.out.println(StringHelper.SubTitle("Insert Objects into Database", '-'));
		{
			int count = 0;
			for(BaseDataType object : objects) {
				System.out.println("Inserted : " + db.Insert("Key"+Integer.toString(count), object));
				count++;
			}
			System.out.println("Total KVP inserted : " + count + "\n\n");
		}
	}
	
	private static void SearchFunction(DBEngine db) {
		System.out.println(StringHelper.SubTitle("Show all Objects in Database", '-'));
		System.out.print(StringHelper.SubTitle("Objects in Database", '~'));
		System.out.println("\n" + db.Show());
		
		System.out.println(StringHelper.SubTitle("Search Objects by Tags in Database", '-'));
		System.out.print(StringHelper.SubTitle("Search for All Objects with Tag : 'Fives'", '~'));
		System.out.println("\n" + db.SearchByTag("Fives"));
	}
	
	private static void UpdateAndRemove(DBEngine db) {
		System.out.println(StringHelper.SubTitle("Update Object with Key : 'Key0'", '-'));
		System.out.println(StringHelper.SubTitle("Old 'Key0' Details", '~'));
		System.out.println(db.Get("Key0"));
		StringDataType object = new StringDataType("Luke Skywaalker", new HashSet<>(Arrays.asList("Jedi Master", "string", "data")));
		System.out.println("Update Status : " + db.Update("Key0", object) + "\n");
		System.out.println(StringHelper.SubTitle("New 'Key0' Details", '~'));
		System.out.println(db.Get("Key0") + "\n");
		
		System.out.println(StringHelper.SubTitle("Remove Object with Key : 'Key2'", '-'));
		System.out.println("'Key2' Exists : " + db.Exists("Key2"));
		System.out.println("Remove Status : " + db.Remove("Key2"));
		System.out.println("'Key2' Exists : " + db.Exists("Key2"));
		
		System.out.println("\n\n");
	}
	
	public static void main(String[] args) {
		System.out.println(StringHelper.Title("TESTING DBENGINE", '='));
		System.out.println(StringHelper.SubTitle("Create and Instantiate Database", '-'));
		System.out.println("Creating Database with Owner : 'anonymous'");
		DBEngine db = new DBEngine("anonymous");
		System.out.println("DB Created");
		System.out.println("DB Owner : " + db.GetOwner());
		System.out.println("DB Name  : " + db.GetDBName() + "\n\n");
		
		InsertCreated(db);
		SearchFunction(db);
		UpdateAndRemove(db);
		
		System.out.println("[Status] Testing Complete ...");
	}
}
