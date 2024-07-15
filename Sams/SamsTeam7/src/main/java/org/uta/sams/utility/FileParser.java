/*
 * FileParser.java
 *
 * Created on April 4, 2006, 12:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 */

package org.uta.sams.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.uta.sams.beans.ProgramBean;

/**
 *
 * @author Administrator
 */
public class FileParser {
    
    int terms_col=0;
    
    /** Creates a new instance of FileParser */
    public FileParser() {
    }
    
    
    public  ArrayList getPrograms(String filepath){
        
    	//Check which file to read -- @yxk
        File fl = new File(filepath);
       // int terms_col=0;
        
        int inputvalues=0;
        ArrayList programlist=new ArrayList();
        try {
            BufferedReader buf= new BufferedReader(new FileReader(fl));
            String temp="";
            temp=buf.readLine(); // Read File yxk 
            if(temp!=null){
                 StringTokenizer tokens = new StringTokenizer(temp,",",true);
                  while(tokens.hasMoreTokens()){
                    String values=tokens.nextToken();
                    if(!(values.toLowerCase().indexOf("academic")==-1)){
                        while(tokens.hasMoreTokens()){
                            values= tokens.nextToken();
                            if(!(values.toLowerCase().indexOf("term")==-1))break;
                            terms_col++;
                        }
                    }
                       
                    
                  }
                
            }
            System.out.println(">>The Terms start from "+terms_col);
            temp= buf.readLine();
            System.out.println("Next line is empty or not "+checkEmptyLine(temp));
            ArrayList list =  new ArrayList();
            
            while((temp=buf.readLine())!=null){
                System.out.println(temp);
                if(checkEmptyLine(temp))continue;
                if(isAddColleges(temp)){
                    addColleges((ProgramBean)programlist.get(programlist.size()-1),temp);
                    continue;
                }
                ProgramBean programbean = new ProgramBean();
                ProgramBean programbean2=null;
                StringTokenizer tokens = new StringTokenizer(temp,",",true);
                int count=0;
                list.clear();
                String college="";
                while(tokens.hasMoreTokens()){
                    String values=tokens.nextToken();
                    if(values.equalsIgnoreCase(","))continue;
                    switch(count){
                    case 0: 
                           programbean.setRegion(values);
                           count++;
                           break;
                    case 1:
                           programbean.setCountry(values);
                           count++;
                           break;
                     case 2:
                           programbean.setProgramName(values);
                           count++;
                           break;
                     case 3:
                           programbean.setProgramType(values);
                           count++;
                           break;
                     case 4:
                           college=values;
                           count++;
                           break;
                     case 5:
                           if(!values.equalsIgnoreCase(",")||values!=null)list.add(values+"|"+college);
                           int commaNos=0;
                           while(commaNos<terms_col&&tokens.hasMoreTokens()){
                               values=tokens.nextToken();
                               
                              if(!values.equalsIgnoreCase(","))list.add(values+"|"+college); 
                              else commaNos++; 
                               
                           }
                           String[] subjects = new String[list.size()];
                           programbean.setSubjects((String[])list.toArray(subjects));
                           count++;
                           break;
                        case 6:
                            programbean.setTerm(values);
                            values=tokens.nextToken();
                            if(tokens.hasMoreTokens()){
                                values=tokens.nextToken();
                                if(!values.equalsIgnoreCase(",")){
                                    programbean2=(ProgramBean)programbean.clone();
                                    programbean2.setTerm(values);
                                }  
                            }
                            count++;
                            break;
                        case 7:
                            list.clear();
                            if(!values.equalsIgnoreCase(","))list.add(values);
                           while(tokens.hasMoreTokens()){
                               values=tokens.nextToken();
                              if(!values.equalsIgnoreCase(","))list.add(values);    
                           }
                           String[] languages = new String[list.size()];
                           programbean.setLanguages((String[])list.toArray(languages));
                           if(programbean2!=null)programbean2.setLanguages((String[])list.toArray(languages));
                           count++;
                           break;
                            
                    }
                }
                programlist.add(programbean);
                if(programbean2!=null)programlist.add(programbean2);
            }
            
            System.out.println("------------------------total program list size is --------------------------------"+programlist.size());
            for(int j=0;j<programlist.size();j++){
                ProgramBean pb=(ProgramBean)programlist.get(j);
                System.out.println("---------------------------programname   "+pb.getProgramName());
                //System.out.println("college"+pb.get());
                System.out.println("porg type"+pb.getProgramType());
                System.out.println("Country "+pb.getCountry());
                System.out.println("Region "+pb.getRegion());
                for(int k=0;k<pb.getSubjects().length;k++){
                    System.out.println("subjects: "+pb.getSubjects()[k]);
                }
                System.out.println("term "+pb.getTerm());
                for(int k=0;pb.getLanguages()!=null&&k<pb.getLanguages().length;k++){
                    System.out.println("Language: "+pb.getLanguages()[k]);
                }
            }
                        
            
        } 
        
        
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Not a proper number entered");
            System.exit(1);
        }catch(Exception ex){
            ex.printStackTrace();
        }
             
    return programlist;   
    }
    
    
    
    
    private   boolean checkEmptyLine(String source){
        StringTokenizer tokens = new StringTokenizer(source,",");
        if(tokens.countTokens()>1)return false;
        else return true;
    }
    
    private  boolean isAddColleges(String source){
        StringTokenizer tokens = new StringTokenizer(source,",",true);
        if(tokens.nextToken().equals(","))return true;
        else return false;
    }

    private  void addColleges(ProgramBean programBean,String temp) {
        
         StringTokenizer tokens = new StringTokenizer(temp,",",true);
                int count=0;
                ArrayList list  = new ArrayList();
                String college="";
                while(tokens.hasMoreTokens()){
                    String values=tokens.nextToken();
                    if(values.equalsIgnoreCase(",")){count++;continue;}
                    switch(count){
                    
                     case 4:
                           college=values;
                           //count++;
                           break;
                     case 6:
                           if(!values.equalsIgnoreCase(",")||values!=null)list.add(values+"|"+college);
                           int commaNos=0;
                           while(commaNos<terms_col&&tokens.hasMoreTokens()){
                               values=tokens.nextToken();
                               
                              if(!values.equalsIgnoreCase(","))list.add(values+"|"+college); 
                              else commaNos++; 
                               
                           }
                           String[] subjects = new String[list.size()];
                           subjects  = (String [])list.toArray(subjects);
                           String[] prev_subjects=programBean.getSubjects();
                           String[] total_subjects = new String[subjects.length+prev_subjects.length];
                           for(int k=0;k<prev_subjects.length;k++)total_subjects[k]=prev_subjects[k];
                           int len=prev_subjects.length;
                           for(int k=len;k<subjects.length+len;k++)total_subjects[k]=subjects[k-len];
                           programBean.setSubjects(total_subjects);
                           count++;
                           break;
                       
                            
                    }
                }
               
            }
}
