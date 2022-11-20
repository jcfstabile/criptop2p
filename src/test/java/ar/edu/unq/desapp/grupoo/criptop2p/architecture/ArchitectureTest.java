package ar.edu.unq.desapp.grupoo.criptop2p.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Architecture's test")
@SpringBootTest
public class ArchitectureTest {
    private ClassFileImporter fileImporter;

    @BeforeEach
    void setUp() {
        fileImporter = new ClassFileImporter();
    }

    @DisplayName("Model classes are only in te model package")
    @Test
    void testModel(){
        JavaClasses classes = fileImporter.importPackages("ar.edu.desapp.grupoo.criptop2p.model");
    }
}
