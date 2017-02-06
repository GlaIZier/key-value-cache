## API for key value cache
Simple API and implementation of key-value cache

### Dependencies
All project dependencies will be downloaded by Maven from central repo.

### Build
To build locally you need installed Maven locally:
```
mvn clean package
```

### Run
You can run simple test of this API from command line.
```
java -jar <path-to-uber-jar>
```
This will create 2 level (Memory and Disk) LRU cache and put there 50 Integer-String pairs.
To control this test you can use these options
```
-s <lru|mru> :strategy to use
-c1 <int>    :l1 capacity
-c2 <int>    :l2 capacity
-d <string>  :path to files directory
```

### LICENSE
GNU GPLv3