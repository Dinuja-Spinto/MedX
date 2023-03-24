package com.devStack.dto;

import com.devStack.enums.GenderType;

import java.util.Date;

public class PatientDto {
    private String nic;
    private String fName;
    private String lName;
    private Date dbo;
    private GenderType genderType;
    private String address;
    private String email;

    public PatientDto(String nic, String fName, String lName, Date dbo, GenderType genderType, String address, String email) {
        this.nic = nic;
        this.fName = fName;
        this.lName = lName;
        this.dbo = dbo;
        this.genderType = genderType;
        this.address = address;
        this.email = email;
    }

    public PatientDto() {
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Date getDbo() {
        return dbo;
    }

    public void setDbo(Date dbo) {
        this.dbo = dbo;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PatientDto{" +
                "nic='" + nic + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", dbo=" + dbo +
                ", genderType=" + genderType +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
