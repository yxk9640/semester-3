/*
 * ContainerBean.java
 *
 * Created on March 3, 2006, 9:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.uta.sams.beans;

/**
 *
 * @author Administrator
 */
public class ContainerBean {
    private UtilityBean utilitybean;
    private ProgramBean programbean;
    
    /** Creates a new instance of ContainerBean */
    public ContainerBean() {
    }

    public UtilityBean getUtilitybean() {
        return utilitybean;
    }

    public void setUtilitybean(UtilityBean utilitybean) {
        this.utilitybean = utilitybean;
    }

    public ProgramBean getProgrambean() {
        return programbean;
    }

    public void setProgrambean(ProgramBean programbean) {
        this.programbean = programbean;
    }
    
}
