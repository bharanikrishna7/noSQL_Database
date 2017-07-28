package dataTypes;

import java.util.HashSet;

import constants.DataTypesConstants;

/**
 * Data Type which can Hold HashSet of Strings. This Class
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
 * 0.1a : 07/26/2017
 * - Initial Release
 * 
 * 
 * @author Venkata Bharani Krishna Chekuri
 *
 */
public class HashSetDataType extends BaseDataType<HashSet<String>>{
	public HashSetDataType (HashSet<String> data) {
		super(DataTypesConstants.HASHSETDATATYPE, data);
	}
	
	public HashSetDataType (HashSet<String> data, HashSet<String> tags) {
		super(DataTypesConstants.HASHSETDATATYPE, data, tags);
	}
	
	public boolean Exists (String value) {
		if(_data.contains(value))
			return true;
		return false;
	}
	
	public boolean Insert (String value) {
		if(Exists(value))
			return false;
		_data.add(value);
		SetTimestamp();
		return true;
	}
	
	public boolean Remove (String value) {
		if(!Exists(value))
			return false;
		_data.remove(value);
		SetTimestamp();
		return true;
	}
	
	public boolean Empty() {
		return _data.isEmpty();
	}
	
	@Override
	public String ToString() {
		return _data.toString();
	}
}
