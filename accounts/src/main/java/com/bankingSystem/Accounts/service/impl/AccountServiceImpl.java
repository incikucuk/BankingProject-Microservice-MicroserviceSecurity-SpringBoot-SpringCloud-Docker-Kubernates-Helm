package com.bankingSystem.Accounts.service.impl;

import com.bankingSystem.Accounts.constants.AccountConstants;
import com.bankingSystem.Accounts.dto.AccountDto;
import com.bankingSystem.Accounts.dto.CustomerDto;
import com.bankingSystem.Accounts.entity.Account;
import com.bankingSystem.Accounts.entity.Customer;
import com.bankingSystem.Accounts.exception.CustomerAlreadyExistsException;
import com.bankingSystem.Accounts.exception.ResourceNotFoundException;
import com.bankingSystem.Accounts.mapper.AccountMapper;
import com.bankingSystem.Accounts.mapper.CustomerMapper;
import com.bankingSystem.Accounts.repository.AccountRepository;
import com.bankingSystem.Accounts.repository.CustomerRepository;
import com.bankingSystem.Accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomerEntity(customerDto, new Customer());

        //Req1:db yeni kayit olustururken aynı phoneNumber giris yapilamaz
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number!"
                    +customer.getMobileNumber());
        }
//        customer.setCreatedAt(LocalDateTime.now());   //Kayit işleminde BaseEntityden de gelen, sadece create değerleri burada set edilmeli
//        customer.setCreatedBy("Anonymous");     //AuditAwareImpl class dahil edildiği icin artik burada kendimizin atamasina gerek kalmadi

        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    //Req2:phoneNumber ile customer bilgilerini getirmek istiyoruz
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","mobileNumber",mobileNumber));

        //Req3:customer'ın account bilgilerini getirmek istiyoruz.(Hem customer hem de account bilgilerini getirmek istiyoruz)
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        return customerDto;
    }
    //Req3:kullanici hesap numarası hariç diğer tüm bilgileri güncelleyebilmelidir.
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;
        //farklı accountNo girdisi update etmek istenirse hata verir(accountNo update edilmemlidir.)
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto != null) {
            Account account = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", accountDto.getAccountNumber().toString()));

            Account accounts = AccountMapper.mapToAccountEntity(accountDto, account);
            Account savedAccount = accountRepository.save(accounts);


            Long customerId = accounts.getCustomerId();
            if(customerId != null){
                Customer customer = customerRepository.findById(customerId).orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
                );
                Customer customers = CustomerMapper.mapToCustomerEntity(customerDto, customer);
                Customer savedCustomer = customerRepository.save(customers);
                isUpdated = true;
            }
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String phoneNumber) {
        boolean isValid = false;
        Customer customer = customerRepository.findByMobileNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",phoneNumber));

        if(customer != null){
            accountRepository.deleteByCustomerId(customer.getCustomerId());
            customerRepository.deleteById(customer.getCustomerId());
            isValid = true;
        }
        return isValid;
    }


    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();

        newAccount.setCustomerId(customer.getCustomerId());

        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccountNumber);

        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);

//        newAccount.setCreatedAt(LocalDateTime.now());  //AuditAwareImpl class dahil edildiği icin artik burada kendimizin atamasina gerek kalmadi
//        newAccount.setCreatedBy("Anonymous");

        return accountRepository.save(newAccount);
    }


}

