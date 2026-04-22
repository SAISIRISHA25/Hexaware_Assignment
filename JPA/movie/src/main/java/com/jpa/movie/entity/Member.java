package com.jpa.movie.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must contain exactly 10 digits")
    @Column(unique = true)
    private String mobileNo;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Booking> bookings;

    public Member() {
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
