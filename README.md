# Quarkus MongoDB

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.mongodb/quarkus-mongodb-parent?logo=apache-maven&style=flat-square)](https://central.sonatype.com/artifact/io.quarkiverse.mongodb/quarkus-mongodb-parent)

Quarkiverse extensions for MongoDB.

## Compatibility

| Quarkus      | Quarkus MongoDB |
|--------------|-----------------|
| 3.40.x (LTS) | 0.x             |

## Flyway MongoDB

`io.quarkiverse.mongodb:quarkus-flyway-mongodb` applies [Flyway](https://www.red-gate.com/products/flyway/) schema migrations to MongoDB databases configured through the
[Quarkus MongoDB client](https://quarkus.io/guides/mongodb), using Flyway's
[Native Connectors for MongoDB](https://documentation.red-gate.com/fd/flyway-native-connectors-mongodb-271583122.html).

Supports `.js` (executed via `mongosh`) and `.json` migrations, multiple named clients, callbacks, a Dev UI panel and a codestart.

### Getting started

Add the dependency, replacing `LATEST_VERSION` with the version shown in the badge above:

```xml
<dependency>
    <groupId>io.quarkiverse.mongodb</groupId>
    <artifactId>quarkus-flyway-mongodb</artifactId>
    <version>LATEST_VERSION</version>
</dependency>
```

Read the full [documentation](https://docs.quarkiverse.io/quarkus-mongodb/dev/flyway-mongodb.html).
