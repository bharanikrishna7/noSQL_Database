package dataTypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import constants.DataTypesConstants;

/**
 * Data Type which can Hold HashMap of <String, String>. This Class
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
public class HashMapDataType extends BaseDataType<HashMap<String, String>>{
	public HashMapDataType(HashMap<String, String> data) {
		super(DataTypesConstants.HASHMAPDATATYPE, data);
	}
	
	public HashMapDataType(HashMap<String, String> data, HashSet<String> tags) {
		super(DataTypesConstants.HASHMAPDATATYPE, data, tags);
	}
	
	public boolean Exists(String key) {
		if(_data.containsKey(key))
			return true;
		return false;
	}
	
	public boolean Insert(String key, String value) {
		if(Exists(key))
			return false;
		_data.put(key, value);
		return true;
	}
	
	public boolean Update(String key, String value) {
		if(!Exists(key))
			return false;
		_data.replace(key, value);
		return true;
	}
	
	public boolean Remove(String key) {
		if(!Exists(key))
			return false;
		_data.remove(key);
		return true;
	}
	
	public boolean Empty() {
		return _data.isEmpty();
	}
	
	public Set<String> KeySet() {
		return _data.keySet();
	}
}
