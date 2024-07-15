/*
 * ProgramController.java
 *
 * Created on March 1, 2006, 10:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.uta.sams.controller;

import java.util.ArrayList;
import org.uta.sams.beans.ContainerBean;
import org.uta.sams.beans.ProgramBean;
import org.uta.sams.beans.SearchBean;
import org.uta.sams.beans.UtilityBean;
import org.uta.sams.dbconnection.ProgramDBManager;
import org.uta.sams.utility.FileParser;

/**
 *
 * @author user-yxk9640
 */
public class ProgramController {
    
    /** Creates a new instance of ProgramController */
    public ProgramController() {
    }
    
    public UtilityBean getCommonDetail(){
        ProgramDBManager programdbmanager=new ProgramDBManager();
        UtilityBean utilitybean=programdbmanager.getCommonDetail();
        return utilitybean;
    }
    public String addProgram(ProgramBean programBean){
        ProgramDBManager dbmanager= new ProgramDBManager();
        String msg=dbmanager.addProgram(programBean);
        return msg;
    } 
    
    public void addImage(String prg, String term, String imgID, String imgValue){
        ProgramDBManager dbmanager= new ProgramDBManager();
        dbmanager.addImage(prg,term,imgID,imgValue);
        
    } 
     public ArrayList searchPrograms(SearchBean searchbean){
        System.out.println("In Controller ");
        return (new ProgramDBManager()).searchPrograms(searchbean);
    }
       
    public String deactivateProgram(String programID,String termID){
        boolean getResponse = (new ProgramDBManager()).deactivateProgram(programID,termID);
       String sendResponse;
        if(getResponse)
       {
        sendResponse = "Successful Deactivate";
       }
       else
       {
       sendResponse = "Not a Successful Deactivate";
       }
        return sendResponse ;
    }
    public String deleteProgram(String programID,String termID){
        boolean getResponse = (new ProgramDBManager()).deleteProgram(programID,termID);
       String sendResponse;
        if(getResponse)
       {
        sendResponse = "Successful Delete";
       }
       else
       {
       sendResponse = "Not a Successful Delete";
       }
        return sendResponse ;
    }

     public ProgramBean showDetail(String programID,String termID){
        
        System.out.println("In Controller ");
        return (new ProgramDBManager()).showDetail(programID,termID);
    }
    
    public ContainerBean updateProgramShowDetail(String programID,String termID){
        ProgramDBManager programdbmanager= new ProgramDBManager();
        ProgramBean programbean=programdbmanager.showDetail(programID,termID);
        UtilityBean utilitybean=programdbmanager.getCommonDetail();
        ContainerBean containerbean=new ContainerBean();
        containerbean.setProgrambean(programbean);
        containerbean.setUtilitybean(utilitybean);
        return containerbean;
    }
    
    
    public String updateProgram(ProgramBean programBean){
        ProgramDBManager programdbmanager=new ProgramDBManager();
        boolean result=programdbmanager.updateProgram(programBean);
        if(result)return "Data updated successfully.";
        else return "Some error occured during submission of data.";
    }
    
    
    public String uploadProgram(String filepath){
        
         FileParser fp = new FileParser();
        System.out.println(" file in controller "+filepath);
        ArrayList programs = fp.getPrograms(filepath);
        ProgramDBManager programdbmanager=new ProgramDBManager();
        System.out.println("after file parser size "+programs.size());
        //for(int i=0;i<programs.size();i++){
            programdbmanager.uploadProgram(programs);
        //}
        return "success";
    }
    }
