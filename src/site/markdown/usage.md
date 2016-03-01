KUNAI: Usage
============

Overview
--------

 This products has two interfaces, ```DataSource``` and
```DataSink```.

 ```DataSource``` represents a source for reading classes. Also,
```DataSource``` iterates ```Entry``` objects which shows entries of
archive, such as a class file from a jar archive.

 ```DataSink``` shows destination of ```Entry``` objects.  We put
```Entry``` objects into the ```DataSink``` object, and close the
```DataSink``` object, then the corresponding archive is built.
 
### Extract from Jar files

```java
DataSource source = DataSourceBuilder.getBuilder().build(
    "path to jar file"
);
for(Entry entry: source){
    // some operation for each entry
}
```

### Extract from Jar files with Stream API

```java
DataSource source = DataSourceBuilder.getBuilder().build(
    "path to jar file"
);
Stream<Entry> stream = source.stream();
```

 You can see, if you want to read classes from directory, war file,
and class files, essential routine is completely the same.
