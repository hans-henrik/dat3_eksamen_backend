/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos.user;

import entities.User;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private long id;
    private String name;
    private String phone;
    private String email;
    private String username;
    private String password;
    private List<RoleDTO> roles = new ArrayList<>();

    public UserDTO(User user) {
        this.username = user.getUserName();
        user.getRoleList().forEach(role->this.roles.add(new RoleDTO(role)));
    }

    public UserDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getEntity(){
        User user = new User(this.id, this.username, this.password, this.name, this.phone, this.email);
        this.roles.forEach(roleDTO->user.addRole(roleDTO.getEntity()));
        return user;
    }
}
