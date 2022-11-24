package ar.edu.unq.desapp.grupoo.criptop2p.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

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
    void testControllersAreAnnotatedWithRestControllerAnnotation(JavaClasses classes){
        ArchRule myRule = classes().that().
                resideInAPackage("..webservice.controllers").should()
                .beAnnotatedWith(RestController.class);
        myRule.check(classes);
    }

    @DisplayName("Repositories must be interfaces")
    @ArchTest
    public void testRepositoriesMustBeInterfaces(JavaClasses classes) {
        classes()
                .that().resideInAPackage("..persistence")
                .should().beInterfaces();
    }

    @DisplayName("Services must be interfaces")
    @ArchTest
    public void ServiceInterfacesMustBeInterfaces(JavaClasses classes) {
        classes()
                .that().resideInAPackage("..service.interfaces")
                .should().beInterfaces();
    }

    @DisplayName("3")
    @ArchTest
    void testServicesAreAnnotatedWithServiceAnnotation(JavaClasses classes){
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


}

/*
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.junit.jupiter.api.DisplayName;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@DisplayName("Architecture's test")
@AnalyzeClasses(packages = "ar.edu.unq.desapp.grupoo.criptop2p", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

    //Este falla y no entiendo porque


    //Este falla y no entiendo porque
    @DisplayName("3")
    @ArchTest
    void testServicesAreAnnotatedWithServiceAnnotation(JavaClasses classes){
        ArchRule myRule = classes().that().
                resideInAPackage("..service.services").should()
                .beAnnotatedWith(Service.class);
        myRule.check(classes);
    }

    @DisplayName("1")
    @ArchTest
    void testServicesAreOnlyBeAccessedOnlyForControllersAndServices(){
        classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..", "..utils..");
    }







    @DisplayName("3")
    @ArchTest
    void testControllersAreAnnotatedWithRestController(){
        classes().that().
                resideInAPackage("..webservice.controllers").should()
                .beAnnotatedWith(RestController.class);
    }

    @DisplayName("4")
    @ArchTest
    void testServicesAreAnnotatedWithService(){
        classes().that().resideInAPackage("..service.services").should()
                .beAnnotatedWith(Service.class);
    }

    @DisplayName("1")
    @ArchTest
    void testServicesAreOnlyBeAccessedOnlyForControllersAndServices(JavaClasses classes) {
        ArchRule myRule = classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..", "..utils..");
        myRule.check(classes);
    }
 */
    /*
    @DisplayName("2")
    @ArchTest
    void testModelAreOnlyBeAccessedOnlyForControllersAndServices(){
        classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage(".. integrations..", "..controller..", "..service..");
    }

    @DisplayName("3")
    @ArchTest
    void testControllersAreAnnotatedWithRestController(){
        classes().that().
                resideInAPackage("..webservice..").should()
                .beAnnotatedWith(RestController.class);
    }

    @DisplayName("4")
    @ArchTest
    void testServicesAreAnnotatedWithService(){
        classes().that().resideInAPackage("..service").should()
                .beAnnotatedWith(Service.class);
    }

    @DisplayName("5")
    @ArchTest
    public void classesAreContainedInRigthPackages() {
        classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().resideInAPackage("..controllers");
        classes()
                .that().haveSimpleNameEndingWith("Service")
                .should().resideInAPackage("..services");
        classes()
                .that().resideInAPackage("..service.dto")
                .should().haveSimpleNameEndingWith("DTO");
    }

    @ArchTest
    void testExceptionsHaveSimpleNameEndingWithException(){
        classes()
                .that().resideInAPackage("..service.exceptions")
                .should().haveSimpleNameEndingWith("Exception");
    }

    @ArchTest
    void testControllersHaveSimpleNameEndingWithController(){
        classes()
                .that().resideInAPackage("webservice")
                .should().haveSimpleNameEndingWith("Controller");
    }

    @ArchTest
    void testServicesHaveSimpleNameEndingWithService(){
        classes()
                .that().resideInAPackage("..service.exceptions")
                .should().haveSimpleNameEndingWith("Service");
    }



}
*/