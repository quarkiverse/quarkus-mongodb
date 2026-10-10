package io.quarkiverse.mongodb.flyway.runtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;

import io.quarkiverse.mongodb.flyway.FlywayMongodbConfigurationCustomizer;
import io.quarkus.mongodb.runtime.MongoConfig;
import io.smallrye.config.PropertiesConfigSource;
import io.smallrye.config.SmallRyeConfig;
import io.smallrye.config.SmallRyeConfigBuilder;

class FlywayMongodbCreatorExecuteInTransactionTest {

    private static final String ANALYTICS = "analytics";

    @Test
    void javaScriptSuffixDisablesExecuteInTransaction() {
        Loaded loaded = load(Map.of());

        Flyway flyway = create(loaded, MongoConfig.DEFAULT_CLIENT_NAME, List.of());

        assertThat(flyway.getConfiguration().getSqlMigrationSuffixes()).containsExactly(".js");
        assertThat(flyway.getConfiguration().isExecuteInTransaction()).isFalse();
    }

    @Test
    void jsonSuffixKeepsExecuteInTransaction() {
        Loaded loaded = load(Map.of(
                "quarkus.flyway-mongodb.migration-suffixes", ".json"));

        Flyway flyway = create(loaded, MongoConfig.DEFAULT_CLIENT_NAME, List.of());

        assertThat(flyway.getConfiguration().getSqlMigrationSuffixes()).containsExactly(".json");
        assertThat(flyway.getConfiguration().isExecuteInTransaction()).isTrue();
    }

    @Test
    void customizerOverridesAutomaticFalseForJavaScriptSuffix() {
        Loaded loaded = load(Map.of());

        Flyway flyway = create(loaded, MongoConfig.DEFAULT_CLIENT_NAME,
                List.of(configuration -> configuration.executeInTransaction(true)));

        assertThat(flyway.getConfiguration().getSqlMigrationSuffixes()).containsExactly(".js");
        assertThat(flyway.getConfiguration().isExecuteInTransaction()).isTrue();
    }

    @Test
    void namedClientFollowsItsOwnSuffix() {
        Loaded loaded = load(Map.of(
                "quarkus.flyway-mongodb.migration-suffixes", ".json",
                "quarkus.flyway-mongodb.analytics.migration-suffixes", ".js"));

        Flyway defaultClient = create(loaded, MongoConfig.DEFAULT_CLIENT_NAME, List.of());
        Flyway analytics = create(loaded, ANALYTICS, List.of());

        assertThat(defaultClient.getConfiguration().getSqlMigrationSuffixes()).containsExactly(".json");
        assertThat(defaultClient.getConfiguration().isExecuteInTransaction()).isTrue();
        assertThat(analytics.getConfiguration().getSqlMigrationSuffixes()).containsExactly(".js");
        assertThat(analytics.getConfiguration().isExecuteInTransaction()).isFalse();
    }

    private static Flyway create(Loaded loaded, String clientName,
            List<FlywayMongodbConfigurationCustomizer> customizers) {
        FlywayMongodbClientRuntimeConfig runtime = loaded.runtime.clients().get(MongoConfig.DEFAULT_CLIENT_NAME);
        FlywayMongodbClientBuildTimeConfig build = loaded.build.clients().get(clientName);
        assertThat(runtime).isNotNull();
        assertThat(build).as("build-time config for client %s", clientName).isNotNull();
        return new FlywayMongodbCreator(runtime, build, customizers)
                .createFlyway(clientName, "mongodb://localhost:27017/app", null, null);
    }

    private static Loaded load(Map<String, String> properties) {
        SmallRyeConfig config = new SmallRyeConfigBuilder()
                .withSources(new PropertiesConfigSource(properties, "test", 1000))
                .withMapping(FlywayMongodbBuildTimeConfig.class)
                .withMapping(FlywayMongodbRuntimeConfig.class)
                .build();
        return new Loaded(
                config.getConfigMapping(FlywayMongodbBuildTimeConfig.class),
                config.getConfigMapping(FlywayMongodbRuntimeConfig.class));
    }

    private record Loaded(FlywayMongodbBuildTimeConfig build, FlywayMongodbRuntimeConfig runtime) {
    }
}
