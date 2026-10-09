package io.quarkiverse.mongodb.flyway.runtime.graal;

import org.flywaydb.core.internal.scanner.classpath.JarFileClassPathLocationScanner;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.TargetClass;

/**
 * Just make the constructor visible to ClassPathScannerSubstitutions.
 */
@TargetClass(value = JarFileClassPathLocationScanner.class, onlyWith = ClassPathScannerSubstitutions.IsQuarkusFlywayAbsent.class)
public final class JarFileClassPathLocationScannerSubstitutions {

    @Alias
    public JarFileClassPathLocationScannerSubstitutions(String separator) {
    }
}
