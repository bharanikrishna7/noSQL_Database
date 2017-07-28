package dataTypes;

import java.util.HashSet;

import constants.DataTypesConstants;

/**
 * Data Type which can Hold Character Value. This Class
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
 * 
 * @author Venkata Bharani Krishna Chekuri
 *
 */
public class CharDataType extends BaseDataType<Character> {
	public CharDataType(char data) {
		super(DataTypesConstants.CHARDATATYPE, data);
	}
	
	public CharDataType(char data, HashSet<String> tags) {
		super(DataTypesConstants.CHARDATATYPE, data, tags);
	}
	
	@Override
	public String DataToString() {
		return _data.toString();
	}
}
