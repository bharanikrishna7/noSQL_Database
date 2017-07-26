package dataTypes;

import java.util.HashSet;

import constants.DataTypesConstants;

/**
 * Data Type which can Hold Boolean Value. This Class
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
 * 0.1r : 07/23/2017
 * - Added New Constructor which takes Tags Parameter.
 * 
 * 0.2a : 07/25/2017
 * - Value of Variable _types in base class is now set from constants.DataTypesConstants
 * 
 * 
 * @author Venkata Bharani Krishna Chekuri
 *
 */
public class BooleanDataType extends BaseDataType<Boolean> {
	public BooleanDataType(boolean data) {
		super(DataTypesConstants.BOOLEANDATATYPE, data);
	}
	
	public BooleanDataType(boolean data, HashSet<String> tags) {
		super(DataTypesConstants.BOOLEANDATATYPE, data, tags);
	}
	
	public boolean Invert() {
		_data = !_data;
		return _data;
	}
	
	@Override
	public String DataToString() {
		return _data.toString();
	}
}
