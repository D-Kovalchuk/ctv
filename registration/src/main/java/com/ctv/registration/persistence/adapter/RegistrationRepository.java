package com.ctv.registration.persistence.adapter;

import com.ctv.registration.persistence.adapter.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dmitry Kovalchuk
 */
public interface RegistrationRepository extends JpaRepository<Account, Integer> {
}
