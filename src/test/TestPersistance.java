package test;

import java.io.FileNotFoundException;

import dbCore.DBEngine;
import dbCore.PersistanceEngine;
import utilities.DateHelper;
import utilities.StringHelper;

public class TestPersistance {
	public static void main(String[] args) {
		System.out.println(StringHelper.Title("TESTING PERSISTANCE", '='));
		System.out.println(StringHelper.SubTitle("Create and Insert Objects into DBEngine", '-'));
		DBEngine db = new DBEngine("anonymous");
		System.out.println("Created and Instantiated DBEngine with Owner : 'anonymous'");
		TestDBEngine.InsertCreated(db);
		System.out.println(StringHelper.SubTitle("Persist DBEngine to File : 'test.xml'", '-'));
		long lastmodified = PersistanceEngine.SaveToXML(db, "test.xml");
		System.out.println("Last Modified Timestamp of File : 'test.xml' is " + DateHelper.TimestampToString(lastmodified) + "\n\n");
		System.out.println(StringHelper.SubTitle("Load DBEngine From File : 'test.xml'", '-'));
		try {
			DBEngine dbNew = PersistanceEngine.LoadFromXML("test.xml");
			System.out.print(StringHelper.SubTitle("Objects in Database", '~'));
			System.out.println("\n" + dbNew.Show());
		} catch (FileNotFoundException e) {
			System.out.println("File \"test.xml\" not found");
		}
	}
}
