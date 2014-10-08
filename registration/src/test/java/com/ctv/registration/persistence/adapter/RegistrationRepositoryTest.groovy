package com.ctv.registration.persistence.adapter
import com.ctv.registration.config.RegistrationConfig
import com.ctv.registration.persistence.adapter.model.Account
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.ExpectedDatabase
import com.github.springtestdbunit.assertion.DatabaseAssertionMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.transaction.PlatformTransactionManager
import spock.lang.Specification

import javax.transaction.Transactional
/**
 * @author Dmitry Kovalchuk
 */
@Transactional
@ContextConfiguration(classes = [PersistenceTestConfig, RegistrationConfig])
@TransactionConfiguration(defaultRollback = false)
@TestExecutionListeners([DependencyInjectionTestExecutionListener, DbUnitTestExecutionListener, TransactionalTestExecutionListener])
class RegistrationRepositoryTest extends Specification {

    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private PlatformTransactionManager tm; //todo why?

    //todo add on more data set for authorities table
    @DatabaseSetup("/dataset/updateUser.xml")
    @ExpectedDatabase(value = "/dataset/userUpdated.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    def "update user" () {
        setup:
            def account = new Account()
            account.with {
                id = 1
                enabled = false
                username = "user"
                password = "pass"
                email = "email@company.com"
                type = "watcher"
                site = "http://site.com"
            }
            def updatedAccount = registrationRepository.saveAndFlush(account);

        expect:
            updatedAccount.email == "email@company.com"
    }


}
