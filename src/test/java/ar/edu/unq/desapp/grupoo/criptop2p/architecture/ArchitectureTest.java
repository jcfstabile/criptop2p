package ar.edu.unq.desapp.grupoo.criptop2p.architecture;

import ar.edu.unq.desapp.grupoo.criptop2p.service.interfaces.IntentionServiceInterface;
import ar.edu.unq.desapp.grupoo.criptop2p.service.interfaces.QuotationServiceInterface;
import ar.edu.unq.desapp.grupoo.criptop2p.service.interfaces.UserServiceInterface;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.interfaces.IntentionControllerInterface;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.interfaces.UserControllerInterface;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import javax.persistence.Entity;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@DisplayName("Architecture's test")
@AnalyzeClasses(packages = "ar.edu.unq.desapp.grupoo.criptop2p", importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class ArchitectureTest {
    @DisplayName("DTOs have simple name ending with DTO")
    @ArchTest
    public void testDtosHaveSimpleNameEndingWithDTO(JavaClasses classes) {
        ArchRule myRule = classes()
                .that().resideInAPackage("..service.dto")
                .should().haveSimpleNameEndingWith("DTO");
        myRule.check(classes);
    }

    @DisplayName("Exceptions have simple name ending with Exception")
    @ArchTest
    public void testExceptionsHaveSimpleNameEndingWithException(JavaClasses classes) {
        ArchRule myRule = classes()
                .that().resideInAPackage("..service.exceptions")
                .should().haveSimpleNameEndingWith("Exception");
        myRule.check(classes);
    }

    @DisplayName("Controllers have simple name ending with Controller")
    @ArchTest
    public void testControllersHaveSimpleNameEndingWithController(JavaClasses classes) {
        ArchRule myRule = classes()
                .that().resideInAPackage("..webservice.controllers")
                .should().haveSimpleNameEndingWith("Controller");
        myRule.check(classes);
    }

    @DisplayName("Controllers are annotated with ResControllerAnnotation")
    @ArchTest
    void testControllersAreAnnotatedWithRestControllerAnnotation(JavaClasses classes) {
        ArchRule myRule = classes().that().
                resideInAPackage("..webservice.controllers").should()
                .beAnnotatedWith(RestController.class);
        myRule.check(classes);
    }

    @DisplayName("Repositories must be interfaces")
    @ArchTest
    public void testRepositoriesMustBeInterfaces(JavaClasses classes) {
        ArchRule myRule = classes()
                .that().resideInAPackage("..persistence")
                .should().beInterfaces();
        myRule.check(classes);
    }

    @DisplayName("Services must be interfaces")
    @ArchTest
    public void ServiceInterfacesMustBeInterfaces(JavaClasses classes) {
        ArchRule myRule = classes()
                .that().resideInAPackage("..service.interfaces")
                .should().beInterfaces();
        myRule.check(classes);
    }

    @DisplayName("Services are annotated with service annotation")
    @ArchTest
    void testServicesAreAnnotatedWithServiceAnnotation(JavaClasses classes) {
        ArchRule myRule = classes().that().
                resideInAPackage("..service.services").should()
                .beAnnotatedWith(Service.class);
        myRule.check(classes);
    }

    @DisplayName("Services have simple name ending with Service")
    @ArchTest
    public void servicesHaveSimpleNameEndingWithServices(JavaClasses classes) {
        ArchRule myRule = classes()
                .that().resideInAPackage("..service.services")
                .should().haveSimpleNameEndingWith("Service");
        myRule.check(classes);
    }

    @DisplayName("1 - Services are only be accessed only for controllers and services")
    @ArchTest
    public void testServicesAreOnlyBeAccessedOnlyForControllersAndServices(JavaClasses classes) {
        ArchRule myRule = classes()
                .that().resideInAPackage("..service.services")
                .should().onlyBeAccessed().byAnyPackage("..webservice..", "..service..");
        myRule.check(classes);
    }

    @DisplayName("UserService impl implemente UserServiceInterface and has Service in its name")
    @ArchTest
    public void testInterfaceUserService(JavaClasses classes) {
        ArchRule myRule = classes().that().implement(UserServiceInterface.class)
                .should().haveSimpleNameEndingWith("Service");
        myRule.check(classes);
    }

    @DisplayName("IntentionService impl implemente IntentionServiceInterface and has Service in its name")
    @ArchTest
    public void testInterfaceIntentionService(JavaClasses classes) {
        ArchRule myRule = classes().that().implement(IntentionServiceInterface.class)
                .should().haveSimpleNameEndingWith("Service");
        myRule.check(classes);
    }

    @DisplayName("QuotationService impl implemente QuotationServiceInterface and has Service in its name")
    @ArchTest
    public void testInterfaceQuotationService(JavaClasses classes) {
        ArchRule myRule = classes().that().implement(QuotationServiceInterface.class)
                .should().haveSimpleNameEndingWith("Service");
        myRule.check(classes);
    }

    @DisplayName("UserController impl implemente UserControllerInterface and has Controller in its name")
    @ArchTest
    public void testInterfaceUserController(JavaClasses classes) {
        ArchRule myRule = classes().that().implement(UserControllerInterface.class)
                .should().haveSimpleNameEndingWith("Controller");
        myRule.check(classes);
    }

    @DisplayName("IntentionController impl implemente IntentionControllerInterface and has Controller in its name")
    @ArchTest
    public void testInterfaceIntentionController(JavaClasses classes) {
        ArchRule myRule = classes().that().implement(IntentionControllerInterface.class)
                .should().haveSimpleNameEndingWith("Controller");
        myRule.check(classes);
    }

    @DisplayName("Loggers should be protected and final")
    @ArchTest
    public void testLoggersShouldBeProtectedAndFinal(JavaClasses classes) {
        ArchRule myRule =
                fields().that().haveRawType(Log.class)
                        .should().beProtected()
                        .andShould().beFinal()
                        .because("we agreed on this convention");
        myRule.check(classes);
    }

    @DisplayName("Repositories must reside in a repository package")
    @ArchTest
    public void testRepositoriesMustResideInARepositoryPackage(JavaClasses classes) {
        ArchRule myRule =
                classes().that().haveNameMatching(".*Repository").should().resideInAPackage("..persistence..")
                        .as("Repositories should reside in a package '..persistence..'");
        myRule.check(classes);
    }

    @DisplayName("Entities should reside in a package ..model..")
    @ArchTest
    public void testEntitiesMustResideInADomainPackage(JavaClasses classes) {
        ArchRule myRule =
                classes().that().areAnnotatedWith(Entity.class).should().resideInAPackage("..model..");
        myRule.check(classes);
    }

    @DisplayName("Methods of controllers are public and should have raw return type ResponseEntity")
    @ArchTest
    public void testMethodOfControllersArePublicAndShouldHaveRawReturnTypeResponseEntity(JavaClasses classes){
        ArchRule myRule =
                methods()
                        .that().areDeclaredInClassesThat().resideInAPackage("..webservice.controllers..")
                        .and().arePublic()
                        .should().haveRawReturnType(ResponseEntity.class)
                        .because("we do not want to couple the client code directly to the return types of the encapsulated module");
        myRule.check(classes);
    }
}









