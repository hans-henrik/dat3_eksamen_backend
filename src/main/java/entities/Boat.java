/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Boat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OWNER_ID")
    private Long ownerId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "OWNER_ID", insertable = false, updatable = false)
    private User owner;

    private String name;
    private String brand;
    private String make;
    private int year;
    private String img;

    public Boat() {
    }

    public Boat(Long id, User owner, Long ownerId, String name, String brand, String make, int year, String img) {
        this.id = id;
        this.owner = owner;
        this.ownerId = ownerId;
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
