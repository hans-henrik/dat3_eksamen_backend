/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos.user;

import entities.Auction;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * @author EG
 */
public class AuctionDTO {
    private Long id;
    private String name;
    private Date date;
    private Time time;
    private String location;


    public AuctionDTO(Auction a) {
        this.id = a.getId();
        this.name = a.getName();
        this.date = a.getDate();
        this.time = a.getTime();
        this.location = a.getLocation();
    }


    public AuctionDTO(String name, Date date, Time time, String location) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
    }


    public static List<AuctionDTO> getDTOs(List<Auction> auctions) {
        List<AuctionDTO> dtos = new ArrayList<>();
        auctions.forEach(auction -> dtos.add(new AuctionDTO(auction)));
        return dtos;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
