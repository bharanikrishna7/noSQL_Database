package dataTypes;

import java.util.HashSet;

import constants.DataTypesConstants;

/**
 * Data Type which can Hold Integer Value. This Class
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
 * 
 * @author Venkata Bharani Krishna Chekuri
 *
 */
public class IntDataType extends BaseDataType<Integer> {
	
	public IntDataType(int data) {
		super(DataTypesConstants.INTDATATYPE, data);
	}
	
	public IntDataType(int data, HashSet<String> tags) {
		super(DataTypesConstants.INTDATATYPE, data, tags);
	}
	
	public int Increment() {
		_data++;
		SetTimestamp();
		return GetData();
	}
	
	public int Decrement() {
		_data--;
		SetTimestamp();
		return GetData();
	}
	
	public int Add(int value) {
		_data += value;
		SetTimestamp();
		return GetData();
	}
	
	public int Subtract(int value) {
		_data -= value;
		SetTimestamp();
		return GetData();
	}
	
	public int Multiply(int value) {
		_data *= value;
		SetTimestamp();
		return GetData();
	}
	
	public int Divide(int value) {
		if(value != 0) {
			_data /= value;
			SetTimestamp();
		}
		return GetData();
	}
	
	@Override
	public String DataToString() {
		return _data.toString();
	}
}