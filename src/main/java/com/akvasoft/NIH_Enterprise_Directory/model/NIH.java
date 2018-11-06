package com.akvasoft.NIH_Enterprise_Directory.model;

public class NIH {

    String legalName;
    String preferredName;
    String eMail;
    String location;
    String mailStop;
    String phone;
    String fax;
    String ic;
    String organization;
    String classification;
    String tty;

    public NIH() {
    }

    public NIH(String legalName, String preferredName, String eMail, String location, String mailStop, String phone, String fax, String ic, String organization, String classification, String tty) {
        this.legalName = legalName;
        this.preferredName = preferredName;
        this.eMail = eMail;
        this.location = location;
        this.mailStop = mailStop;
        this.phone = phone;
        this.fax = fax;
        this.ic = ic;
        this.organization = organization;
        this.classification = classification;
        this.tty = tty;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMailStop() {
        return mailStop;
    }

    public void setMailStop(String mailStop) {
        this.mailStop = mailStop;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getTty() {
        return tty;
    }

    public void setTty(String tty) {
        this.tty = tty;
    }
}
