# Quarkus MongoDB

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.mongodb/quarkus-flyway-mongodb?logo=apache-maven&style=flat-square)](https://central.sonatype.com/artifact/io.quarkiverse.mongodb/quarkus-mongodb-parent)

Quarkiverse extensions for MongoDB.

## Flyway MongoDB

`io.quarkiverse.mongodb:quarkus-flyway-mongodb` applies [Flyway](https://www.red-gate.com/products/flyway/) schema migrations to MongoDB databases configured through the
[Quarkus MongoDB client](https://quarkus.io/guides/mongodb), using Flyway's
[Native Connectors for MongoDB](https://documentation.red-gate.com/fd/flyway-native-connectors-mongodb-271583122.html).

Supports `.js` (executed via `mongosh`) and `.json` migrations, multiple named clients, callbacks, a Dev UI panel and a codestart.

Read the full [documentation](https://docs.quarkiverse.io/quarkus-mongodb/dev/flyway-mongodb.html).

### Known workarounds

- A build-time bytecode transform (`ClasspathSqlMigrationScannerEnhancer`) and `QuarkusMongodbPathLocationScanner` replace
  classpath scanning in `flyway-nc-scanners`, which fails under `QuarkusClassLoader`
  ([flyway/flyway#4241](https://github.com/flyway/flyway/issues/4241)). Remove both once an upstream fix is released and
  validated in dev, fast-jar and native modes.
- The `flyway.version` property in the root `pom.xml` must match the `flyway-core` version managed by the Quarkus BOM.
