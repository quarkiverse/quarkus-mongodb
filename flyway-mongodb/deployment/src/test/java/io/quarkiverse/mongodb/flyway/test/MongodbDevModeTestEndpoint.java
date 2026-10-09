package io.quarkiverse.mongodb.flyway.test;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.flywaydb.core.Flyway;

@Path("/fly")
public class MongodbDevModeTestEndpoint {

    @Inject
    Instance<Flyway> flyway;

    @GET
    public boolean present() {
        flyway.get();
        return true;
    }
}
