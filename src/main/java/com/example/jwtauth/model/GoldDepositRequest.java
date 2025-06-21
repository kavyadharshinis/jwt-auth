package com.example.jwtauth.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gold_deposit_requests")
public class GoldDepositRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String knNumber;
    private String goldType;
    private double weight;
    private String jewelerName;
    private String photoUrl;
    private String accountHolder;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String branchName;

    private String status = "PENDING";

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    // Constructors
    public GoldDepositRequest() {
    }

    public GoldDepositRequest(String knNumber, String goldType, double weight, String jewelerName,
                              String photoUrl, String accountHolder, String accountNumber,
                              String ifscCode, String bankName, String branchName) {
        this.knNumber = knNumber;
        this.goldType = goldType;
        this.weight = weight;
        this.jewelerName = jewelerName;
        this.photoUrl = photoUrl;
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.bankName = bankName;
        this.branchName = branchName;
        this.status = "PENDING";
        this.createdAt = new Date();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKnNumber() {
        return knNumber;
    }

    public void setKnNumber(String knNumber) {
        this.knNumber = knNumber;
    }

    public String getGoldType() {
        return goldType;
    }

    public void setGoldType(String goldType) {
        this.goldType = goldType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getJewelerName() {
        return jewelerName;
    }

    public void setJewelerName(String jewelerName) {
        this.jewelerName = jewelerName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
