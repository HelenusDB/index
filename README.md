# HelenusDB Katalog

**Indexing Magic for Cassandra**

Full-text search with TextIndex:

```java
TextIndex<User> index = new TextIndex<>(); // Case insensitive by default.
index.insert("Alice Brown", new User("Alice", "Brown", 25, "Anytown, USA"));
index.insert("Bob Barker", new User("Bob", "Barker", 30, "Littletown, USA"));
index.insert("Charlie Lane", new User("Charlie", "Lane", 35, "Bigtown, USA"));
index.insert("David Smith", new User("David", "Smith", 40, "Hometown, USA"));

List<User> results = index.search("b*"); // Returns Bob and Alice.
results = index.search("lane"); // Returns Carlie.
results = index.search("i?"); // Returns Alice, Charlie, and David.
```