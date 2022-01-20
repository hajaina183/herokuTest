package com.springboot.app;

public class FormLoginPerson {
	String email;
    String mdp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public FormLoginPerson(String email, String mdp) {
        super();
        this.email = email;
        this.mdp = mdp;
    }
    public FormLoginPerson() {
        super();
    }
}
