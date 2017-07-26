package constants;

import java.util.Arrays;
import java.util.HashSet;

public class DataTypesConstants {
	public static final String INTDATATYPE = "IntDataType";
	public static final String STRINGDATATYPE = "StringDataType";
	public static final String BOOLEANDATATYPE = "BooleanDataType";
	public static final String UNDEFINEDDATATYPE = "UndefinedDataType";
	
	public static final String HASHSETDATATYPE = "HashSetDataType";	
	public static final String HASHMAPDATATYPE = "HashMapDataType";	
	public static final String ARRAYLISTDATATYPE = "ArrayListDataType";
	
	public static final HashSet<String> COLLECTIONDATATYPES = new HashSet<>(
			Arrays.asList(HASHSETDATATYPE, HASHMAPDATATYPE, ARRAYLISTDATATYPE));
}
