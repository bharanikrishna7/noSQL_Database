# INTRODUCTION
A NoSQL (originally referring to "non SQL", "non relational" or "not only SQL") database provides a mechanism for storage and retrieval of data that is modeled in means other than the tabular relations used in relational databases. NoSQL databases are increasingly used in big data and real-time web applications. [Wiki Definition](https://en.wikipedia.org/wiki/NoSQL)

The goal of this project is to create an **in-memory noSQL Database** with flexibility of using different data types (*ex: int, char, boolean, string, arraylists, hashmap, hashset etc.*) and common methods associated with them. 

# FEATURES
This project has features listed below :
* Fast data insertion and retrival since it's in-memory.
* Allows users to insert/update/remove different data types. Currently it supports int, boolean, string data types.
* Continuous auto-indexing data of the data using tags.
* Backup database to disk and load database from disk. Comes in handy when user restarts program but doesnot want to lose the data.
* Logging operations to a logfile. This helps in keeping track of the operations being performed on the database. 
* Scheduler to periodically backup database to disk. This feature is turned on by default, but can be turned off.
* Non-core tasks like logging and scheduled backups are done using threads. This allows making use of other cpu cores to do non-core tasks.

# BRIEF PACKAGE DETAILS
Please refer to individual classes for detailed information of the class.
### Constants
This package contains details of the immutable variables which are used in the project. Immutable variables may include status messages, error messages, warning messages, mutexes, time intervals etc.

### DataTypes
This package contains classes for data types which can be inserted into the database. Each data type will be derived from **BaseDataType** and they may have methods which are specific to the derived data type (*ex: int data type has increment and decrement which is not available for boolean or string*).

### DBCore
All of the core database functions are implemented in this package. Core functions include Database Engine, Scheduler, Logger, Persistance Engine, Query Engine & Database Node.
*Query Engine and Database Node are yet to be implemented*

### Utilities
Utilities package contains static functions which are used commonly throught the project. *ex: Generate Current Timestamp, Convert Timestamp from one format to another, Generate Title representation of a string and many more*.

### Test
This package is used to test the functions and features of the project.

# TODO FOR NEXT RELEASE
* Create Query Engine.
* Create Database Node. Wrapper for all the Database Core Features.
* Add comments to methods.

# 3rd PARTY LIBRARIES/APIs
* Apache-commons

# STATE OF PROJECT
Currently the project is in **pre-alpha state**, but once Query Engine and Database Nodes are built it should be usable like a normal console java application.