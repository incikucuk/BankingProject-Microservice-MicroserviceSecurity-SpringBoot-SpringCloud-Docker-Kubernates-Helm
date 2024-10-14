package com.bankingSystem.Accounts.repository;

import com.bankingSystem.Accounts.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

    @Repository
    public interface AccountRepository extends JpaRepository<Account,Long> {

        Optional<Account> findByCustomerId(Long customerId);

        @Transactional
        @Modifying
        void deleteByCustomerId(Long customerId);
    }

/*
Bu durumda, deleteByCustomerId yöntemi, verileri veritabanından kaldıran bir silme işlemi olduğu
için @Modifying ile açıklanır. @Modifying kullanarak, Spring Data jpa'ya sorguyu, verileri silmek
 için gerekli veritabanı işlemlerini gerçekleştirmesine izin veren bir değiştirme sorgusu olarak
 yürütmesini söylüyoruz.

@Değiştirmeden, Spring Data JPA, sorgunun verileri değiştirmeyi amaçladığını bilmez ve sorguyu doğru şekilde
yürütmeyebilir.
@Modifying'in genellikle bu yöntemde de bulunan @Transactional ile birlikte kullanıldığını belirtmekte fayda var.
@Transactional , veritabanı işlemlerinin bir işlem içinde yürütülmesini sağlar, bu da atomiklik ve tutarlılık gibi
ek avantajlar sağlar.

Bu kod bağlamında, Silme işlemini etkinleştirmek için @Modifying kullanılırken, @Transactional işlemin bir işlem içinde
yürütülmesini sağlar.
 */
