package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility Class Containing Static Methods Which will be Used
 * by Other Classes. This Class Contains Mainly Methods Associated
 * with DateTime.
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
public class DateHelper {
	public static long GetCurrentTimestamp() {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		return Long.parseLong(timeStamp);
	}
	
	public static long GetCurrentTimestamp(String dateFormat) {
		String timeStamp = new SimpleDateFormat(dateFormat).format(new java.util.Date());
		return Long.parseLong(timeStamp);
	}
	
	public static String TimestampToString(long timestamp) {
		String dttm = Long.toString(timestamp);
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMddHHmmss").parse(dttm);
		} catch (ParseException e) {
			System.out.println("\n[ERROR] : Unable to parse date. Format should be \"yyyyMMddHHmmss\"");
		}
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
	}
}
