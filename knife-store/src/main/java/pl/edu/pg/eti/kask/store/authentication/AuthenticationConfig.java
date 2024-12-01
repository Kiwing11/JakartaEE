package pl.edu.pg.eti.kask.store.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;


//@BasicAuthenticationMechanismDefinition(realmName = "Knife Store")
//@FormAuthenticationMechanismDefinition(
//        loginToContinue = @LoginToContinue(
//                loginPage = "/authentication/form/login.xhtml",
//                errorPage = "/authentication/form/login_error.xhtml"
//        )
//)
@ApplicationScoped
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/authentication/custom/login.xhtml",
                errorPage = "/authentication/custom/login_error.xhtml"
        )
)
@DatabaseIdentityStoreDefinition
(
        dataSourceLookup = "jdbc/KnifeStore",
        callerQuery = "select password from users where login = ?",
        groupsQuery = "select role from user__roles where id = (select id from users where login = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}
