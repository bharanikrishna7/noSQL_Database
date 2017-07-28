package dataTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import constants.DataTypesConstants;

/**
 * Data Type which can Hold ArrayList of Strings. This Class
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
public class ArrayListDataType extends BaseDataType<ArrayList<String>> {
	public ArrayListDataType(ArrayList<String> data) {
		super(DataTypesConstants.ARRAYLISTDATATYPE, data);
	}

	public ArrayListDataType(ArrayList<String> data, HashSet<String> tags) {
		super(DataTypesConstants.ARRAYLISTDATATYPE, data, tags);
	}
	
	public boolean Exists(String value) {
		return _data.contains(value);
	}
	
	public String Get(int index) {
		return _data.get(index);
	}
	
	public int Size() {
		return _data.size();
	}
	
	public int Insert(String value) {
		_data.add(value);
		return Size();
	}
	
	public int Insert(int index, String value) {
		_data.add(index, value);
		return Size();
	}
	
	public int Remove(int index) {
		_data.remove(index);
		return Size();
	}
	
	public int RemoveFirst(String value) {
		_data.remove(value);
		return Size();
	}
	
	public int RemoveAll(Collection<String> values) {
		_data.removeAll(values);
		return Size();
	}
}