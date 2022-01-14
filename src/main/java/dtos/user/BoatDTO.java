/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos.user;

import entities.Boat;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class BoatDTO {
    private Long id;
    private Long ownerId; // rettes alle steder
    private String name;
    private String brand;
    private String make;
    private int year;
    private String img;

    public BoatDTO() {
    }

    public BoatDTO(Boat boat) {
        this.id = boat.getId();
        this.ownerId = boat.getOwnerId();
        this.name = boat.getName();
        this.brand = boat.getBrand();
        this.make = boat.getMake();
        this.year = boat.getYear();
        this.img = boat.getImg();
    }

    public BoatDTO(Long id, Long ownerId, String name, String brand, String make, int year, String img) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.img = img;
    }

    public static List<BoatDTO> getDTOs(List<Boat> boats) {
        List<BoatDTO> boatDTOS = new ArrayList<>();
        boats.forEach(boat -> boatDTOS.add(new BoatDTO(boat)));
        return boatDTOS;
    }

    public Boat getEntity() {
        return new Boat(id, null, ownerId, name, brand, make, year, img);
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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
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


}
