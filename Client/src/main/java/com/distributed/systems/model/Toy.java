package com.distributed.systems.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@Entity
@Table(name = "toy_price")
public class Toy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code", updatable = false, nullable = false)
    private String code;

    private String name;

    private String description;

    private Long price;

    private Long dateOfManufacture;

    private String batchNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private Manufacturer manufacturer;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Long dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Manufacturer getManufacturer(){ return manufacturer; }

    public void setManufacturer(Manufacturer manufacturer) {this.manufacturer = manufacturer; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}