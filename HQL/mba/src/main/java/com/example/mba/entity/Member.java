package com.example.mba.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    private int memberid;
    private String membername;
    private int movieid;
    private int tickets;
    public Member() {
    }
    public Member(int memberid, String membername, int movieid, int tickets) {
        this.memberid = memberid;
        this.membername = membername;
        this.movieid = movieid;
        this.tickets = tickets;
    }
    public int getMemberid() {
        return memberid;
    }
    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }
    public String getMembername() {
        return membername;
    }
    public void setMembername(String membername) {
        this.membername = membername;
    }
    public int getMovieid() {
        return movieid;
    }
    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }
    public int getTickets() {
        return tickets;
    }
    public void setTickets(int tickets) {
        this.tickets = tickets;
    }
    @Override
    public String toString() {
        return "Member [memberid=" + memberid + ", membername=" + membername + ", movieid=" + movieid + ", tickets="
                + tickets + "]";
    }
    


}
