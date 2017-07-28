package constants;

import java.util.Arrays;
import java.util.HashSet;

/**
 * This class contains static constant variables which are
 * associated to DataTypes package. 
 * 
 * 
 * Dependencies
 * ------------
 * <N/A>
 * 
 * 
 * Version
 * -------
 * 0.1a : 07/25/2017
 * - Initial Release.
 * 
 * 0.1b : 07/26/2017
 * - Added HashSet, HashMap, ArrayList and collection variables.
 * 
 * 
 * @author Venkata Bharani Krishna Chekuri
 *
 */
public class DataTypesConstants {
	public static final String INTDATATYPE = "IntDataType";
	public static final String CHARDATATYPE = "CharDataType";
	public static final String STRINGDATATYPE = "StringDataType";
	public static final String BOOLEANDATATYPE = "BooleanDataType";
	public static final String UNDEFINEDDATATYPE = "UndefinedDataType";
	
	public static final String HASHSETDATATYPE = "HashSetDataType";	
	public static final String HASHMAPDATATYPE = "HashMapDataType";	
	public static final String ARRAYLISTDATATYPE = "ArrayListDataType";
	
	public static final HashSet<String> COLLECTIONDATATYPES = new HashSet<>(
			Arrays.asList(HASHSETDATATYPE, HASHMAPDATATYPE, ARRAYLISTDATATYPE));
}
