package io.quarkiverse.mongodb.flyway.deployment;

import java.util.List;

import org.flywaydb.core.extensibility.Plugin;

import io.quarkus.arc.deployment.IgnoreSplitPackageBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ServiceProviderBuildItem;

public class FlywayMongodbAlwaysEnabledProcessor {

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem("flyway-mongodb");
    }

    @BuildStep
    IndexDependencyBuildItem indexFlywayArtifacts() {
        // we need to index all Flyway dependencies
        return new IndexDependencyBuildItem("org.flywaydb", null);
    }

    /**
     * Flyway 12 puts {@code Callback} and {@code NativeConnectorsCallbackHandler} in
     * {@code flyway-nc-callbacks} and {@code CallbackManager} in {@code flyway-nc-core},
     * all in the same package. Both jars are required, so indexing them warns about a
     * split the application cannot fix.
     */
    @BuildStep
    IgnoreSplitPackageBuildItem ignoreFlywayNcCallbacksSplit() {
        return new IgnoreSplitPackageBuildItem(List.of("org.flywaydb.nc.callbacks"));
    }

    @BuildStep
    public ServiceProviderBuildItem flywayPlugins() {
        return ServiceProviderBuildItem.allProvidersFromClassPath(Plugin.class.getName());
    }

}
