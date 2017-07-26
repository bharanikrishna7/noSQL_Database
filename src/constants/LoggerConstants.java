package constants;

import java.util.concurrent.Semaphore;

import utilities.DateHelper;

public class LoggerConstants {
	public static final String LOGFILENAME = DateHelper.GetCurrentTimestamp("yyyyMMddHHmmss") + ".log";
	
	public static Semaphore loggerLock = new Semaphore(1);
	
	public static final String SUCCESS = "[SUCCESS] : Log Successful.";
	public static final String FAILED00 = "[FAILED] : Log Failed. IOException occured.";
	public static final String FAILED01 = "[FAILED] : Log Failed. Error Occured while Processing Log Request";
	public static final String WARNING00 = "[WARNING] : Log Successful but Unable to Close BufferedWriter or FileWriter.";
	public static final String WARNING01 = "[WARNING] : Log Successful but Unable to Terminate Logger Service.";
	
}
