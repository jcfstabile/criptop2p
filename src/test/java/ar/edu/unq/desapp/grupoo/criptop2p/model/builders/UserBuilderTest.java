package ar.edu.unq.desapp.grupoo.criptop2p.model.builders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


@DisplayName("Builder Tests")
@SpringBootTest
class UserBuilderTest {
    UserBuilder anyUser = new UserBuilder("aaa", "bbb", "c@d.e", "fghijklmno", "Pqrs7$", "12345678", "1234567890123456789012");

    @DisplayName("When create a UserBuilder, it is consistent with its data")
    @Test
    void testAUserBuilderIsConsistent(){
        assertEquals("aaa", anyUser.name);
        assertEquals("bbb", anyUser.surname);
        assertEquals("c@d.e", anyUser.email);
        assertEquals("fghijklmno", anyUser.address);
        assertEquals("Pqrs7$", anyUser.password);
        assertEquals("12345678", anyUser.walletAddress);
        assertEquals("1234567890123456789012", anyUser.cvu);
    }

    @DisplayName("with name only modicated this attribute")
    @Test
    void testAUserBuilderCanBeCreateWithOtherName(){
        UserBuilder otherUser = anyUser.withName("pepe");
        assertEquals("pepe", otherUser.name);
        assertEquals("bbb", otherUser.surname);
        assertEquals("c@d.e", otherUser.email);
        assertEquals("fghijklmno", otherUser.address);
        assertEquals("Pqrs7$", otherUser.password);
        assertEquals("12345678", otherUser.walletAddress);
        assertEquals("1234567890123456789012", otherUser.cvu);
    }

    @DisplayName("with surname only modicated this attribute")
    @Test
    void testAUserBuilderCanBeCreateWithOtherSurname(){
        UserBuilder otherUser = anyUser.withSurname("pepe");
        assertEquals("aaa", otherUser.name);
        assertEquals("pepe", otherUser.surname);
        assertEquals("c@d.e", otherUser.email);
        assertEquals("fghijklmno", otherUser.address);
        assertEquals("Pqrs7$", otherUser.password);
        assertEquals("12345678", otherUser.walletAddress);
        assertEquals("1234567890123456789012", otherUser.cvu);
    }

    @DisplayName("with Email only modicated this attribute")
    @Test
    void testAUserBuilderCanBeCreateWithOtherEmail(){
        UserBuilder otherUser = anyUser.withEmail("aaa@d.c");
        assertEquals("aaa", otherUser.name);
        assertEquals("bbb", otherUser.surname);
        assertEquals("aaa@d.c", otherUser.email);
        assertEquals("fghijklmno", otherUser.address);
        assertEquals("Pqrs7$", otherUser.password);
        assertEquals("12345678", otherUser.walletAddress);
        assertEquals("1234567890123456789012", otherUser.cvu);
    }

    @DisplayName("with Address only modicated this attribute")
    @Test
    void testAUserBuilderCanBeCreateWithOtherAddress(){
        UserBuilder otherUser = anyUser.withAddress("xxxxxxx");
        assertEquals("aaa", otherUser.name);
        assertEquals("bbb", otherUser.surname);
        assertEquals("c@d.e", otherUser.email);
        assertEquals("xxxxxxx", otherUser.address);
        assertEquals("Pqrs7$", otherUser.password);
        assertEquals("12345678", otherUser.walletAddress);
        assertEquals("1234567890123456789012", otherUser.cvu);
    }

    @DisplayName("with Password only modicated this attribute")
    @Test
    void testAUserBuilderCanBeCreateWithOtherPassword(){
        UserBuilder otherUser = anyUser.withPassword("Asd+asd1");
        assertEquals("aaa", otherUser.name);
        assertEquals("bbb", otherUser.surname);
        assertEquals("c@d.e", otherUser.email);
        assertEquals("fghijklmno", otherUser.address);
        assertEquals("Asd+asd1", otherUser.password);
        assertEquals("12345678", otherUser.walletAddress);
        assertEquals("1234567890123456789012", otherUser.cvu);
    }

    @DisplayName("with Wallet Address only modicated this attribute")
    @Test
    void testAUserBuilderCanBeCreateWithOtherWalletAddress(){
        UserBuilder otherUser = anyUser.withWalletAddress("87654321");
        assertEquals("aaa", otherUser.name);
        assertEquals("bbb", otherUser.surname);
        assertEquals("c@d.e", otherUser.email);
        assertEquals("fghijklmno", otherUser.address);
        assertEquals("Pqrs7$", otherUser.password);
        assertEquals("87654321", otherUser.walletAddress);
        assertEquals("1234567890123456789012", otherUser.cvu);
    }

    @DisplayName("with Cvu only modicated this attribute")
    @Test
    void testAUserBuilderCanBeCreateWithOtherCvu(){
        UserBuilder otherUser = anyUser.withCvu("1111111111111111111111");
        assertEquals("aaa", otherUser.name);
        assertEquals("bbb", otherUser.surname);
        assertEquals("c@d.e", otherUser.email);
        assertEquals("fghijklmno", otherUser.address);
        assertEquals("Pqrs7$", otherUser.password);
        assertEquals("12345678", otherUser.walletAddress);
        assertEquals("1111111111111111111111", otherUser.cvu);
    }

    @DisplayName("with name return an userbuilder")
    @Test
    void testWithNameReturnsAnUserBuilder(){
        assertInstanceOf(UserBuilder.class, anyUser.withName("pepe"));
    }

    @DisplayName("with surname return an userbuilder")
    @Test
    void testWithSurnameReturnsAnUserBuilder(){
        assertInstanceOf(UserBuilder.class, anyUser.withSurname("pepe"));
    }

    @DisplayName("with Email return an userbuilder")
    @Test
    void testWithEmailReturnsAnUserBuilder(){
        assertInstanceOf(UserBuilder.class, anyUser.withEmail("asd@ghj.com"));
    }

    @DisplayName("with Address return an userbuilder")
    @Test
    void testWithAddressReturnsAnUserBuilder(){
        assertInstanceOf(UserBuilder.class, anyUser.withAddress("xxxxxxx"));
    }

    @DisplayName("with Password return an userbuilder")
    @Test
    void testWithPasswordReturnsAnUserBuilders(){
        assertInstanceOf(UserBuilder.class, anyUser.withPassword("Asd+asd1"));
    }

    @DisplayName("with Wallet Address returns an UserBuilder")
    @Test
    void testWithWalletAddressReturnsAnUserBuilder(){
        assertInstanceOf(UserBuilder.class, anyUser.withWalletAddress("87654321"));
    }

    @DisplayName("with Cvu returns an UserBuilder")
    @Test
    void testWithCvuReturnsAnUserBuilder(){
        assertInstanceOf(UserBuilder.class, anyUser.withCvu("1111111111111111111111"));
    }
}