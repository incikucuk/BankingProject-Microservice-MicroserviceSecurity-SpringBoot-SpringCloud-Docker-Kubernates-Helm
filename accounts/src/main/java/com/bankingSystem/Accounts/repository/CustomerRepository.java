package com.bankingSystem.Accounts.repository;

import com.bankingSystem.Accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    //metod yazımı java pojo nesnesidir yani Customer ile aynı olmalıdır.
    Optional<Customer> findByMobileNumber(String mobileNumber);

}
