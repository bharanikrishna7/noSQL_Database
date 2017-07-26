package test;

import dataTypes.BaseDataType;
import utilities.StringHelper;

public class TestBaseDataType {
	public static void main(String[] args) {
		System.out.println(StringHelper.Title("TESTING BASE DATA TYPE", '='));
		System.out.println(StringHelper.SubTitle("Create BaseDataType Object", '-'));
		BaseDataType<Integer> object = new BaseDataType<>("Integer", 5);
		System.out.println("Created BaseDataType Object of Type Integer with value '5'\n\n");
		
		System.out.println(StringHelper.SubTitle("Test GetType Method", '-'));
		System.out.println("Type : " + object.GetType() + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test GetData Method", '-'));
		System.out.println("Data : " + object.GetData() + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test SetData Method", '-'));
		System.out.println("New Data : " + object.SetData(7) + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test InsertTag Method", '-'));
		System.out.println("Adding tags \"int\", \"object\", \"test\" to object");
		System.out.println("\"int\" Tag Inserted : " + object.InsertTag("int"));
		System.out.println("\"object\" Tag Inserted : "  + object.InsertTag("object"));
		System.out.println("\"test\" Tag Inserted : "  + object.InsertTag("test") + "\n\n");

		System.out.println(StringHelper.SubTitle("Test ShowTag Method", '-'));
		System.out.println("Tags : " + object.GetTags() + "\n\n");
		
		System.out.println(StringHelper.SubTitle("Test RemoveTag Method", '-'));
		System.out.println("\"int\" Tag Removed : " + object.RemoveTag("int"));
		System.out.println("Tags : " + object.GetTags() + "\n\n");

		System.out.println(StringHelper.SubTitle("Test ToString Method", '-'));
		System.out.println("Tags : " + object.ToString() + "\n\n");
		
		System.out.println("[Status] Testing Complete ...");
	}
}
