package utilities;

import java.util.HashMap;

/**
 * Utility Class Containing Static Methods Which will be Used
 * by Other Classes. This Class Contains Mainly Methods Associated
 * with Strings.
 * 
 * 
 * Dependencies
 * ------------
 * <N/A>
 * 
 * 
 * Version
 * -------
 * 0.1a : 7/22/2017
 * - Initial Release
 * 
 * 
 * @author Venkata Bharani Krishna, Chekuri
 *
 */
public class StringHelper {
	public static String Title(String str, char underline) {
		StringBuilder aggregator = new StringBuilder();
		str = str.trim();
		for(int i = 0; i < str.length(); i++)
			aggregator.append(underline);
		aggregator.append("\n"+str+"\n");
		for(int i = 0; i < str.length(); i++)
			aggregator.append(underline);
		return aggregator.toString();
	}
	
	public static String SubTitle(String str, char underline) {
		StringBuilder aggregator = new StringBuilder();
		str = str.trim();
		aggregator.append(str+"\n");
		for(int i = 0; i < str.length(); i++)
			aggregator.append(underline);
		return aggregator.toString();
	}
	
	public static String Encrypt(String str, HashMap<Character, String> encryptionKeys) {
		StringBuilder aggregator = new StringBuilder();
		char current;
		for(int i = 0; i < str.length(); i++) {
			current = str.charAt(i);
			if(encryptionKeys.containsKey(current)) {
				aggregator.append(encryptionKeys.get(current));
			} else {
				aggregator.append(current);
			}
		}
		return aggregator.toString();
	}
	
	/* not final*/
	public static String Decypt(String str, HashMap<String, String> decryptionKeys) {
		for(String key : decryptionKeys.keySet()) {
			str.replace(key, decryptionKeys.get(key));
		}
		return str;
	}
}
