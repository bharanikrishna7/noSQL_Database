package dataTypes;

import java.util.HashSet;

import constants.DataTypesConstants;

/**
 * Data Type which can Hold String Variables. This Class
 * is Derived from BaseDataType.
 * 
 * 
 * Dependencies
 * ------------
 * constants.DataTypesConstants, BaseDataType
 * 
 * 
 * Version 
 * -------
 * 0.1a : 07/21/2017
 * - Initial Release
 * 
 * 0.1b : 07/22/2017
 * - Removed ToString Method since it's already fixed in BaseDataType.
 * 
 * 0.1c : 07/23/2017
 * - Added New Constructor which takes Tags Parameter.
 * 
 * 0.2a : 07/25/2017
 * - Value of Variable _types in base class is now set from constants.DataTypesConstants
 * 
 * 0.2b : 07/26/2017
 * - Added ToUpperCase and ToLowerCase methods.
 * 
 * 
 * @author Venkata Bharani Krishna Chekuri
 *
 */
public class StringDataType extends BaseDataType<String> {
	public StringDataType(String data) {
		super(DataTypesConstants.STRINGDATATYPE, data);
	}
	
	public StringDataType(String data, HashSet<String> tags) {
		super(DataTypesConstants.STRINGDATATYPE, data, tags);
	}
	
	public String Concat(String value) {
		_data += value;
		SetTimestamp();
		return GetData();
	}
	
	public String ConcatTo(String value) {
		value += _data;
		SetData(value);
		SetTimestamp();
		return GetData();
	}
	
	public int Length() {
		return _data.length();
	}
	
	public String Substring(int beginIndex) {
		return _data.substring(beginIndex);
	}
	
	public String Substring(int beginIndex, int endIndex) {
		return _data.substring(beginIndex, endIndex);
	}
	
	public String Trim() {
		return _data.trim();
	}
	
	@Override
	public String DataToString() {
		return _data;
	}
	
	public String ToUpperCase() {
		return _data.toUpperCase();
	}
	
	public String ToLowerCase() {
		return _data.toLowerCase();
	}
}
