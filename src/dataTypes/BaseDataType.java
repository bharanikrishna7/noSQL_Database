package dataTypes;

import java.util.HashSet;

import utilities.DateHelper;
import constants.DataTypesConstants;

/**
 * Template Class For All The Derived Data Types
 * which can be Inserted into the Database.
 * 
 * 
 * Dependencies
 * ------------
 * constants.DataTypesConstants, utilities.DateHelper
 * 
 * 
 * Version 
 * -------
 * 0.1a : 07/21/2017
 * - Initial Release
 * 
 * 0.1b : 07/22/2017
 * - Added Field for timestamp. This will allow users to 
 *   track changes in data.
 *   
 * - Added Tags Field. Tags will allow users to Search Data 
 *   using Tags in the Database.
 *   
 * - Updated ToString Method. Previously this Method Had to
 *   be Overridden in all the Derived Classes, not it's not
 *   required to do so.
 * 
 * 0.1c : 07/23/2017
 * - Added New Constructor which Takes Tags Parameter.
 *   
 * 0.2a : 07/25/2017
 * - Value of Variable _types is now set from constants.DataTypesConstants
 * 
 * 0.2b : 07/26/2017
 * - Added method to check if the derived data type is collection type or not.
 * - Added method to return timestamp in long.
 * - Added method to return timestamp in YYYY/mm/DD HH:MM:SS format.
 *   
 * @author Venkata Bharani Krishna, Chekuri
 *
 * @param <Data>
 */
public class BaseDataType<Data> {
	String _type;
	long _timestamp;
	HashSet<String> _tags;
	Data _data;
	
	public BaseDataType(Data data) {
		_type = DataTypesConstants.UNDEFINEDDATATYPE;
	}
	
	public BaseDataType(String type, Data data) {
		_type = type;
		_data = data;
		_tags = new HashSet<>();
		SetTimestamp();
	}
	
	public BaseDataType(String type, Data data, HashSet<String> tags) {
		_type = type;
		_data = data;
		_tags = tags;
		SetTimestamp();
	}
	
	protected void SetTimestamp() {
		_timestamp = DateHelper.GetCurrentTimestamp();
	}
	
	public Data GetData() {
		return _data;
	}
	
	public String GetType() {
		return _type;
	}
	
	public HashSet<String> GetTags() {
		return _tags;
	}
	
	public boolean TagExists(String tag) {
		return _tags.contains(tag);
	}
	
	public boolean InsertTag(String tag) {
		if(TagExists(tag))
			return false;
		_tags.add(tag);
		SetTimestamp();
		return true;
	}
	
	public boolean RemoveTag(String tag) {
		if(!TagExists(tag))
			return false;
		_tags.remove(tag);
		SetTimestamp();
		return true;
	}
	
	public Data SetData(Data data) {
		_data = data;
		SetTimestamp();
		return GetData();
	}
	
	public boolean IsCollection() {
		if(DataTypesConstants.COLLECTIONDATATYPES.contains(_type))
			return true;
		return false;
	}
	
	public long GetTimestamp() {
		return _timestamp;
	}
	
	public String GetLastModified() {
		return DateHelper.TimestampToString(_timestamp);
	}
	
	/* always override */
	public String DataToString() {
		return _data.toString();
	}
	
	public String ToString() {
		StringBuilder aggregator = new StringBuilder();
		aggregator.append("Type : " + _type + "\n");
		aggregator.append("Timestamp : " + DateHelper.TimestampToString(_timestamp) + "\n");
		aggregator.append("Tags : " + _tags.toString() + "\n");
		aggregator.append("Value : " + _data + "\n");
		
		return aggregator.toString();
	}
}
