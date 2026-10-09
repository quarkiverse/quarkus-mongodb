package io.quarkiverse.mongodb.flyway.deployment;

import java.util.function.BooleanSupplier;

import io.quarkiverse.mongodb.flyway.runtime.FlywayMongodbBuildTimeConfig;

/**
 * Supplier that can be used to only run build steps
 * if the Flyway-MongoDB extension is enabled.
 */
public class FlywayMongodbEnabled implements BooleanSupplier {

    private final FlywayMongodbBuildTimeConfig config;

    FlywayMongodbEnabled(FlywayMongodbBuildTimeConfig config) {
        this.config = config;
    }

    @Override
    public boolean getAsBoolean() {
        return config.enabled();
    }
}
