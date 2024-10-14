package com.bankingSystem.Accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)  //Entity listenerler event listenerlerin aksine, sadece belli bir entity üzerinde işlem yapıldığında işlerler.
// Ornek; entity listener User ve UserLog entitileri üzerinde işlem yapar. Bu işlem sadece User entitisi ile ilgili bir işlem yaptığımızda oluşur.(bizde ise db crud islemleri oldukca listener aktif olur)
@MappedSuperclass    //Bu classları genellikle ortak state ve mapping bilgisi olan entitylerimiz olduğunda kullanırız.
@Getter
@Setter
@ToString
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)    //db UPDATE islemi yapılmamasi icin insertable=false
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate  //bu anotasyonun kullanılma nedeni update islemindeki tarihi verir.Spring frameworkte bu 4 anatasyon temelde vardır.
    @Column(insertable = false)   //db INSERT islemi yapılmamasi icin insertable=false
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
}
