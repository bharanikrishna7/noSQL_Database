package dbCore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import constants.LoggerConstants;
import utilities.DateHelper;

/**
 * Class to Log Operational Information to Disk. This class runs
 * threads to perform different log requests asynchronously while
 * still keeping the log file legible. Also using Future allows
 * the program to execute log operations sometime later thereby
 * not trying to compete (when not required) for CPU cycles with 
 * our database.
 * 
 * 
 * Dependencies
 * ------------
 * constants.LoggerConstants, utilities.DateHelper
 * 
 * 
 * Version
 * -------
 * 0.1 : 07/25/2017
 * - Initial Release
 * 
 * 
 * @author Venkata Bharani Krishna Chekuri
 *
 */
public class Logger {
	public Logger() {
		// do nothing.
	}
	
	protected class LogRunner implements Callable<String> {
		private String _logContents;
		
		public LogRunner (String logContents) {
			_logContents = logContents;
		}
		
		@Override
		public String call() throws Exception {
			BufferedWriter bw = null;
			FileWriter fw = null;
			
			LoggerConstants.loggerLock.acquire();
			
			try {
				File file = new File(LoggerConstants.LOGFILENAME);
				if (!file.exists()) {
					file.createNewFile();
				}
				fw = new FileWriter(file.getAbsoluteFile(), true);
				bw = new BufferedWriter(fw);
				bw.write(DateHelper.TimestampToString(DateHelper.GetCurrentTimestamp()) + ": " + _logContents);
				bw.newLine();
			} catch (IOException ioe) {
				return LoggerConstants.FAILED00;
			} finally {
				LoggerConstants.loggerLock.release();
				try {
					if (bw != null)
						bw.close();
					if (fw != null)
						fw.close();
				} catch (IOException ex) {
					return LoggerConstants.WARNING00;
				}
			}
			return LoggerConstants.SUCCESS;
		}
	}
	
	public String Log(String logContents) {
		String status;
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<String> result = service.submit(new LogRunner(logContents));
		try {
			status = result.get();
		} catch (Exception ex) {
			status = LoggerConstants.FAILED01;
			result.cancel(true);
			return status;
		}
		
		service.shutdown();
		try {
			service.awaitTermination(1, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			status = LoggerConstants.WARNING01;
		}
		return status;
	}
}
