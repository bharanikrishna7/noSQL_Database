package dbCore;

import constants.SchedulerConstants;

/**
 * Scheduler allows Users to Schedule Database backup to XML using
 * PersistEngine. The writes happen after a set time interval. So 
 * as to not make unnecessary writes.
 * 
 * 
 * Dependencies
 * ------------
 * PersistEngine, constants.SchedulerConstants
 * 
 * 
 * Version
 * -------
 * 0.1 : 07/25/2017
 * - Initial Release.
 * 
 * 
 * @author Venkata Bharani Krishna Chekuri
 *
 */
public class Scheduler {
	private DBEngine _db;
	private String _filename;
	private Thread _asyncWrite;
	private Logger logger = new Logger();
	
	public Scheduler(DBEngine db, String filename) {
		_db = db;
		_filename = filename;
		Start();
	}
	
	void Start() {
		_asyncWrite = new Thread(new ScheduleWrites(_db, _filename), "asyncWrite");
		_asyncWrite.start();
		logger.Log(SchedulerConstants.SCHEDULERSTARTED);
	}
	
	@SuppressWarnings("deprecation")
	void Pause() {
		_asyncWrite.stop();
		logger.Log(SchedulerConstants.SCHEDULERSTOPPED);
	}
	
	private class ScheduleWrites implements Runnable {
		private DBEngine _dbRef;
		private String _filenameWrite;
		private Logger logWriter = new Logger();
		
		public ScheduleWrites (DBEngine db, String filename) {
			_dbRef = db;
			_filenameWrite = filename;
		}
		
		@Override
		public void run() {
			while(true) {
				PersistanceEngine.SaveToXML(_dbRef, _filenameWrite);
				logWriter.Log(SchedulerConstants.WRITESUCCESSFUL);
				try {
					Thread.sleep(SchedulerConstants.WRITEINTERVALMINS * 60 * 1000);
				} catch (InterruptedException e) {
					logWriter.Log(SchedulerConstants.LOGSLEEPINTERRUPTED);
				}
			}
		}
	}
}
