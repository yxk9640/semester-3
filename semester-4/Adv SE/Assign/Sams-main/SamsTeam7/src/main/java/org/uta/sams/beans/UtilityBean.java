/*
 * UtilityBean.java
 *
 * Created on March 3, 2006, 9:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.uta.sams.beans;

/**
 *
 * @author Administrator
 */
public class UtilityBean {
    
    private String[][] region_country;
    private String[][] school_departments;
    private String[][] term_deadline;
    private String[] languages;
   
    
    /** Creates a new instance of UtilityBean */
    public UtilityBean() {
    }

    public String[][] getRegion_country() {
        return region_country;
    }

    public void setRegion_country(String[][] region_country) {
        this.region_country = region_country;
    }

    public String[][] getSchool_departments() {
        return school_departments;
    }

    public void setSchool_departments(String[][] school_departments) {
        this.school_departments = school_departments;
    }

    public String[][] getTerm_deadline() {
        return term_deadline;
    }

    public void setTerm_deadline(String[][] term_deadline) {
        this.term_deadline = term_deadline;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }
    
    
    
}
