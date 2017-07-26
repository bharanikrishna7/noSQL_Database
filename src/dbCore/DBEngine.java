package dbCore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import dataTypes.*;
import utilities.StringHelper;

/**
 * DBEngine Class is responsible for Storing the
 * Database Values and Performing Insert, Update
 * and Delete Operations on the Values Present in
 * Database.
 * 
 * 
 * Dependencies
 * ------------
 * datatypes.BaseDataType, datatypes.BooleanDataType, datatypes.IntDataType, datatypes.StringDataType,
 * utilities.StringHelper
 * 
 * 
 * Version 
 * -------
 * 0.1a : 07/21/2017
 * - Initial Release
 * 
 * 0.1b : 07/22/2017
 * - Added Update Operations. Update operations will update the values
 *   associated with a Key which is already present in database.
 * 
 * 0.1c : 07/23/2017
 * - Added Support For Indexing Using Tags. This will allow users to
 *   quickly Retrieve Data Associated with a Tag without Iterating 
 *   over entire Database.
 * 
 * 0.1d : 07/25/2017
 * - DBEngine now uses Logger to log operational information to a log 
 *   file. Currently logger logs Insert, Update, Remove & Show operations.
 *   
 *   
 * Additional Information
 * ----------------------
 * - 0.1rc Update could be implemented in a more sophisticated way using AOP
 *   in spring framework. But I wanted to use as less 3rd party libraries as
 *   possible.
 * 
 * @author Venkata Bharani Krishna, Chekuri
 *
 */
public class DBEngine {
	private String _name;
	private String _owner;
	@SuppressWarnings("unused")
	private String _persistFile;
	@SuppressWarnings("rawtypes")
	private HashMap<String, BaseDataType> _dbEngine;
	private HashMap<String, HashSet<String>> _tagList;
	private Logger logger = new Logger();
	
	public DBEngine(String owner, String name) {
		_name = name;
		Init(owner);
	}
	
	/* Constructor For Now */
	public DBEngine(String owner) {
		_name = "default";
		Init(owner);
	}
	
	private void Init(String owner) {
		_owner = owner;
		_persistFile = _name + owner + ".xml";
		_tagList = new HashMap<>();
		_dbEngine = new HashMap<>();
	}
	
	public String GetDBName() {
		return _name;
	}
	
	public String GetOwner() {
		return _owner;
	}
	
	public Set<String> KeySet() {
		return _dbEngine.keySet();
	}
	
	public boolean Exists(String key) {
		return _dbEngine.containsKey(key);
	}
	
	public boolean TagExists(String tag) {
		return _tagList.containsKey(tag);
	}
	
	@SuppressWarnings("rawtypes")
	public boolean Insert(String key, BaseDataType value) {
		if(Exists(key))
			return false;
		_dbEngine.put(key, value);
		IndexAddTags(key, value);
		logger.Log("Inserted Object into Database");
		return true;
	}
	
	public boolean Insert(String key, IntDataType value) {
		if(Exists(key))
			return false;
		_dbEngine.put(key, value);
		IndexAddTags(key, value);
		logger.Log("Inserted Object with key \"" + key + "\" into Database");
		return true;
	}
	
	public boolean Insert(String key, StringDataType value) {
		if(Exists(key))
			return false;
		_dbEngine.put(key, value);
		IndexAddTags(key, value);
		logger.Log("Inserted Object with key \"" + key + "\" into Database");
		return true;
	}
	
	public boolean Insert(String key, BooleanDataType value) {
		if(Exists(key))
			return false;
		_dbEngine.put(key, value);
		IndexAddTags(key, value);
		logger.Log("Inserted Object with key \"" + key + "\" into Database");
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean Update(String key, BaseDataType value) {
		if(!Exists(key))
			return false;
		IndexRemoveTags(key);
		_dbEngine.replace(key, value);
		IndexAddTags(key, value);
		logger.Log("Updated Object with key \"" + key + "\" in Database");
		return true;
	}
	
	public boolean Update(String key, IntDataType value) {
		if(!Exists(key))
			return false;
		IndexRemoveTags(key);
		_dbEngine.replace(key, value);
		IndexAddTags(key, value);
		logger.Log("Updated Object with key \"" + key + "\" in Database");
		return true;
	}
	
	public boolean Update(String key, StringDataType value) {
		if(!Exists(key))
			return false;
		IndexRemoveTags(key);
		_dbEngine.replace(key, value);
		IndexAddTags(key, value);
		logger.Log("Updated Object with key \"" + key + "\" in Database");
		return true;
	}
	
	public boolean Update(String key, BooleanDataType value) {
		if(!Exists(key))
			return false;
		IndexRemoveTags(key);
		_dbEngine.replace(key, value);
		IndexAddTags(key, value);
		logger.Log("Updated Object with key \"" + key + "\" in Database");
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void IndexAddTags(String key, BaseDataType value) {
		HashSet<String> tags = value.GetTags();
		for(String tag : tags) {
			if(TagExists(tag)) {
				HashSet<String> tagValueList = _tagList.get(tag);
				tagValueList.add(key);
				_tagList.put(tag, tagValueList);
			} else {
				HashSet<String> tagValueList = new HashSet<>();
				tagValueList.add(key);
				_tagList.put(tag, tagValueList);
			}
		}
	}
	
	public boolean Remove(String key) {
		if(!Exists(key))
			return false;
		IndexRemoveTags(key);
		_dbEngine.remove(key);
		logger.Log("Removed Object with key \"" + key + "\" from Database");
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private void IndexRemoveTags(String key) {
		HashSet<String> tags = _dbEngine.get(key).GetTags();
		for(String tag : tags) {
			HashSet<String> tagValueList = _tagList.get(tag);
			tagValueList.remove(key);
			_tagList.put(tag, tagValueList);
		}
	}
	
	public String GetType(String key) {
		if(Exists(key))
			return _dbEngine.get(key).GetType();
		return "[ERR01] : No Object with given Key Exists";
	}
	
	public int Size() {
		return _dbEngine.size();
	}
	
	public String Get(String key) {
		if(Exists(key))
			return _dbEngine.get(key).ToString();
		return "[ERR01] : No Object with given Key Exists";
	}
	
	@SuppressWarnings("rawtypes")
	public BaseDataType GetRaw(String key) {
		if(Exists(key))
			return _dbEngine.get(key);
		return null;
	}
	
	public String Show() {
		StringBuilder aggregator = new StringBuilder();
		for(String key : _dbEngine.keySet()) {
			if(Exists(key)) {
				aggregator.append(StringHelper.SubTitle(key, '-'));
				aggregator.append("\nType = " + _dbEngine.get(key).GetType());
				aggregator.append("\nTags = " + _dbEngine.get(key).GetTags());
				aggregator.append("\nData = " + _dbEngine.get(key).GetData());
				aggregator.append("\n\n");
			}
		}
		logger.Log("Show Database");
		System.out.println("\n[STATUS] : Found " + Size() + " Objects in Database");
		return aggregator.toString();
	}
	
	public String Show(HashSet<String> keys) {
		int counter = 0;
		StringBuilder aggregator = new StringBuilder();
		for(String key : keys) {
			if(Exists(key)) {
				counter++;
				aggregator.append(StringHelper.SubTitle(key, '-'));
				aggregator.append("\nType = " + _dbEngine.get(key).GetType());
				aggregator.append("\nTags = " + _dbEngine.get(key).GetTags());
				aggregator.append("\nData = " + _dbEngine.get(key).GetData());
				aggregator.append("\n\n");
			}
		}
		logger.Log("Show Database");
		System.out.println("\n[STATUS] : Found " + counter + " of " + keys.size() + " in Database");
		return aggregator.toString();
	}
	
	public String SearchByTag(String tag) {
		if(!TagExists(tag))
			return "[ERR02] : The specified Tag doesnot Exist";
		return Show(_tagList.get(tag));
	}
}