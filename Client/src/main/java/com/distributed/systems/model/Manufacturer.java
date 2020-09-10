package com.distributed.systems.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@Entity
@Table(name = "manufacturer")
public class Manufacturer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String companyName;

    private String streetAddress;

    private String zipCode;

    private String country;

    @OneToOne(mappedBy = "manufacturer")
    private Toy toy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Toy getToy(){
        return toy;
    }

    public void setToy(Toy toy){
        this.toy = toy;
    }


}
