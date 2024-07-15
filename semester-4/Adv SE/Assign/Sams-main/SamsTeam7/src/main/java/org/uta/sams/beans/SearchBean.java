/*
 * SearchBean.java
 *
 * Created on March 18, 2006, 1:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.uta.sams.beans;

/**
 *
 * @author user-yxk9640
 */
public class SearchBean {
    
    private String[] region;
    private String[] country;
    private String[] school;
    private String[] subjects;
    private String[] term;
    private String[] languages;
    private String[] programType;
    private String status="";
    
    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
    }

    public String[] getRegion() {
        return region;
    }

    public void setRegion(String[] region) {
        this.region = region;
    }

    public String[] getCountry() {
        return country;
    }

    public void setCountry(String[] country) {
        this.country = country;
    }

    public String[] getSchool() {
        return school;
    }

    public void setSchool(String[] school) {
        this.school = school;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    public String[] getTerm() {
        return term;
    }

    public void setTerm(String[] term) {
        this.term = term;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String[] getProgramType() {
        return programType;
    }

    public void setProgramType(String[] programType) {
        this.programType = programType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
