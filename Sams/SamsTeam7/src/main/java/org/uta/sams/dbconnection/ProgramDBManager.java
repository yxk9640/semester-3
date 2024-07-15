/*
 * ProgramDBManager.java
 *
 * Created on March 1, 2006, 10:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.uta.sams.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.uta.sams.beans.ProgramBean;
import org.uta.sams.beans.SearchBean;
import org.uta.sams.beans.UtilityBean;

/**
 *
 * @author user-yxk9640
 */
public class ProgramDBManager {
    Connection conn = null;
    /** Creates a new instance of ProgramDBManager */
    public ProgramDBManager() {
    }
    
    public String addProgram(ProgramBean programBean){
        String query="";
        int i =0;
            
        
        // Populating the Langauges List with "|" as delimiter from array of languages
        //---------- start -------------
        boolean flag=true;
        String languages="";
        for(i=0;programBean.getLanguages()!=null&&i<programBean.getLanguages().length;i++){
            if(programBean.getLanguages()[i]==null)break;
            if(flag){languages=programBean.getLanguages()[i];flag=false;}
            else{
                languages+="|"+programBean.getLanguages()[i];
            }
        }
        
        //-------- end -----------------
        
        Connection con=null;
        int temp = 0;
        
        try {
            DBConnection connection=DBConnection.getInstance();
            query="select prog_id from Programs where prog_name='"+programBean.getProgramName()+
                         "' and term='" + programBean.getTerm() + "' and status='active'";
                System.out.println("insert Program:>"+query);
                con=connection.getConnection();
                conn=con;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                String pgid="";
                if(rs.next()) {
                        pgid=""+rs.getInt("prog_id");
                        return "Already Exist in database";
                }
        
            //query to insert entry in Programs Table
            //-------------start------------------
            query="insert into Programs(prog_name,term,description,prog_type,fees,img1,img2,img3,language,housing,country,region,status) values("+
                        "'" + programBean.getProgramName() + "',"+
                        "'" + programBean.getTerm() + "',"+
                        "'" + programBean.getDescription() + "',"+
                        "'" + programBean.getProgramType() + "',"+
                        "'" + programBean.getFee() + "',"+
                        "'" + programBean.getImg1() + "',"+
                        "'" + programBean.getImg2() + "',"+
                        "'" + programBean.getImg3() + "',"+
                        "'" + languages+ "',"+
                        "'" + programBean.getHousing() +"',"+
                        "'" + programBean.getCountry() +"',"+
                        "'" + programBean.getRegion() +"','active')";
                
                System.out.println("insert Program:>"+query);
               
                 stmt  = con.createStatement();
                stmt.executeUpdate(query);
                 //----------------end------------------------
                
                // selecting prog_id which is auto generated in Mysql when we enter the above program
                // this is used to update the school_has_program table.
                query="select prog_id from Programs where prog_name='"+programBean.getProgramName()+
                         "' and term='" + programBean.getTerm() + "' and status='active'";
                System.out.println("insert Program:>"+query);
                rs = stmt.executeQuery(query);
                 pgid="";
                if(rs.next()) {
                        pgid=""+rs.getInt("prog_id");
                }
                       
            String depts="";
            flag=true;
            
           
            for( i=0;i<programBean.getSubjects().length;i++){
                if(programBean.getSubjects()[i]==null)break;
                String sub =programBean.getSubjects()[i]; 
                //if new subjects are added which is not already there in list ,, it comes in pair with college it belongs to
                // thus the paring is subject|department. So we need to add this subject first to table before performing
                // mapping between programs and Departments
                //--------------------- start ---------------------------------------------
                if(sub.indexOf("|")!=-1){ addSubject(sub); sub= sub.substring(0,sub.indexOf("|"));}
                //------------------------ end --------------------------------------------
                
                //Condition to select dept ids for a particular program for mapping them in school_has_programs table
                //--------------------------- start --------------------------------------
                if(flag){depts="dept_name='"+sub+"'";flag=false;}
                else{
                    depts+=" or dept_name='"+sub+"'";
                }
                //-------------------------------end -----------------------------------
                
            }
            
            // quering to get the dept_ids for the departments that program has
            //-----------start------------
            query="select distinct dept_id from school where "+depts;
            System.out.println(">>"+query);
            rs=stmt.executeQuery(query);
            String[] deptids=new String[100];
            int k=0;
            while(rs.next()){
                 deptids[k++]=rs.getString("dept_id");
            }
            //------- end -----------------
            
            // inserting the mapping values in school_has_prog table 
            //------ start --------------
            for( i=0;i<deptids.length;i++){
                if(deptids[i]==null)break;
                query="insert into school_has_prog (dept_id,prog_id) values('"+Integer.parseInt(deptids[i])+"','"+Integer.parseInt(pgid)+"')";
                System.out.println("insert School_Program:>"+query);
                stmt.executeUpdate(query);
            }
            //---------- stop ---------------
            
            
            con.close();
            String msg="successfully entered the data";
            return msg;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "" ;     }
        catch(ClassNotFoundException ex1){
            ex1.printStackTrace();
            return "error";
        }catch(Exception e){
            e.printStackTrace();
            
        }
        
        return "success";
        
    }
    
    
    public void addImage(String prg, String term, String imgID, String imgValue){
        DBConnection connection=DBConnection.getInstance();
            String query;
             Connection con=null;
             int i;
        try {
            
            
            query="update programs set "+imgID+"='"+imgValue+"' where prog_name='"+prg+"' and term='"+term+"'";
            System.out.println("update Program:>"+query);
            con=connection.getConnection();
            Statement stmt  = con.createStatement();
            i = stmt.executeUpdate(query);
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        } catch(ClassNotFoundException ex1){
            ex1.printStackTrace();
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public ArrayList searchPrograms(SearchBean searchbean){
        //wrong implementation of search for first increment
         
         
         
         System.out.print("vikram>>>>>>>>>>>>>>>>>>>>"+searchbean.getCountry());
         String statuscriteria = "";
         if(!searchbean.getStatus().equals("")){
             if(searchbean.getStatus().equals("active"))statuscriteria="status='active'";
             if(searchbean.getStatus().equals("deactive"))statuscriteria="status='deactive'";
         }
         String[] region = searchbean.getRegion();
         String regioncondition="";
         if(region !=null){
             if(!region[0].trim().equalsIgnoreCase(""))regioncondition+=" region='"+region[0]+"' ";
             for(int i=1;i<region.length;i++){
                if(!region[i].trim().equalsIgnoreCase(""))regioncondition+=" or region='"+region[i]+"' ";    
             }
         }
         
         String[] country=searchbean.getCountry();
         String countrycondition="";
         if(country !=null){
             if(!country[0].trim().equalsIgnoreCase(""))countrycondition+=" country='"+country[0]+"' ";
             for(int i=1;i<country.length;i++){
                if(!country[i].trim().equalsIgnoreCase(""))countrycondition+=" or country='"+country[i]+"' ";    
             }
         }
         
          String[] temp=searchbean.getSchool();
          String schoolcondition="";
         if(temp !=null){
             if(!temp[0].trim().equalsIgnoreCase(""))schoolcondition+=" school_name='"+temp[0]+"' ";
             for(int i=1;i<temp.length;i++){
                if(!temp[i].trim().equalsIgnoreCase(""))schoolcondition+=" or school_name='"+temp[i]+"' ";    
             }
         }
        
          temp=searchbean.getSubjects();
          String deptcondtion="";
          if(temp !=null){
             if(!temp[0].trim().equalsIgnoreCase(""))deptcondtion+=" dept_name='"+temp[0]+"' ";
             for(int i=1;i<temp.length;i++){
                if(!temp[i].trim().equalsIgnoreCase(""))deptcondtion+=" or dept_name='"+temp[i]+"' ";    
             }
         }
        
            temp=searchbean.getLanguages();
          String langcondtion="";
          if(temp !=null){
             if(!temp[0].trim().equalsIgnoreCase(""))langcondtion+=" language like '%"+temp[0]+"%' ";
             for(int i=1;i<temp.length;i++){
                if(!temp[i].trim().equalsIgnoreCase(""))langcondtion+=" or language like '%"+temp[i]+"%' ";    
             }
         }
          
          temp=searchbean.getProgramType();
          String typecondtion="";
          if(temp !=null){
             if(!temp[0].trim().equalsIgnoreCase(""))typecondtion+=" prog_type = '"+temp[0]+"' ";
             for(int i=1;i<temp.length;i++){
                if(!temp[i].trim().equalsIgnoreCase(""))typecondtion+=" or prog_type = '"+temp[i]+"' ";    
             }
         }
         
           temp=searchbean.getTerm();
          String termcondtion="";
          if(temp !=null){
             if(!temp[0].trim().equalsIgnoreCase(""))termcondtion+=" term = '"+temp[0]+"' ";
             for(int i=1;i<temp.length;i++){
                if(!temp[i].trim().equalsIgnoreCase(""))termcondtion+=" or term = '"+temp[i]+"' ";    
             }
         }
        System.out.println("In DBManager");
        DBConnection connection=DBConnection.getInstance();
        //String query="select distinct prog_name,term,country,region,prog_type, from programs  where status='active' " ;
        String query="select * from programs  where  " ;
         if(!statuscriteria.equalsIgnoreCase(""))query+=statuscriteria;
         else query+=" status='active' ";
        if(!regioncondition.equalsIgnoreCase(""))query+=" and ( "+regioncondition+" )";
        if(!countrycondition.equalsIgnoreCase(""))query+=" and ( "+countrycondition+" )";
        if(!termcondtion.equalsIgnoreCase(""))query+=" and ( "+termcondtion+" )";
        if(!typecondtion.equalsIgnoreCase(""))query+=" and ( "+typecondtion+" )";
        if(!langcondtion.equalsIgnoreCase(""))query+=" and ( "+langcondtion+" )";
        
       //   if(!deptcondtion.equalsIgnoreCase("") || !schoolcondition.equalsIgnoreCase(""))query+=" and ( p.prog_id )";
        query+=" order by prog_name ";
        System.out.println("Query is "+query);
        ArrayList programslist=new ArrayList();
        ArrayList finallist=new ArrayList();
        Connection con=null;
        Statement stmt;
        
        try {
            con=connection.getConnection();
            System.out.println("Conection "+con);
            stmt = con.createStatement();
            ResultSet resultset_prog=stmt.executeQuery(query);
            
            while(resultset_prog.next()){
                
                        ProgramBean programbean=new ProgramBean();
                        programbean.setCountry(resultset_prog.getString("country"));
                        String lang = resultset_prog.getString("language");
                        programbean.setDescription(resultset_prog.getString("description"));
                        programbean.setHousing(resultset_prog.getString("housing"));
                        programbean.setFee(resultset_prog.getString("fees"));
                        programbean.setImg1(resultset_prog.getString("img1"));
                        programbean.setImg2(resultset_prog.getString("img2"));
                        programbean.setImg3(resultset_prog.getString("img3"));
                        programbean.setProgramID(resultset_prog.getInt("prog_id"));
                        programbean.setProgramName(resultset_prog.getString("prog_name"));
                        programbean.setProgramType(resultset_prog.getString("prog_type"));
                        programbean.setRegion(resultset_prog.getString("region"));
                        programbean.setTestemony(resultset_prog.getString("testimony"));
                        programbean.setAppDetails(resultset_prog.getString("deadline"));
                        programbean.setTerm(resultset_prog.getString("term"));
                       
                        if(lang.equals(""))lang="English|";
                        StringTokenizer tokens= new StringTokenizer((String)lang,"|");
                        String languages[]=new String[tokens.countTokens()];
                        int index=0;
                       while(tokens.hasMoreTokens()){
                           String temp1=tokens.nextToken();
                           if(!isPresent(languages,temp1)) languages[index++]=temp1;
                       }

                        programbean.setLanguages(languages);
                        programslist.add(programbean);       
            }
            //for(int i=0;i<programslist.size();i++){
              //  ProgramBean program = (ProgramBean)programslist.get(i);
                // we need to add query to get mulitple semester and languages
            //}
            query="select distinct dept_name from programs p,school_has_prog sp, school s where p.prog_id=sp.prog_id and sp.dept_id=s.dept_id "+
                    " and p.prog_name=? and p.term=? ";
             
             if(!schoolcondition.equalsIgnoreCase(""))query+=" and ( "+schoolcondition +" )";
             if(!deptcondtion.equalsIgnoreCase(""))query+=" and ( "+deptcondtion +" )";  
            System.out.println(query);
            PreparedStatement pstmt = con.prepareStatement(query); 
            
            
            for(int i=0;i<programslist.size();i++){
                pstmt.setString(1,((ProgramBean)programslist.get(i)).getProgramName());
                pstmt.setString(2,((ProgramBean)programslist.get(i)).getTerm());
                ResultSet resultset=pstmt.executeQuery();
                ArrayList subjects=new ArrayList();
                while(resultset.next()){
                    subjects.add(resultset.getString("dept_name"));
                }
                String[] subs = new String[subjects.size()];
                ((ProgramBean)programslist.get(i)).setSubjects((String[])subjects.toArray(subs));
                               
                if((!schoolcondition.equalsIgnoreCase("")||!deptcondtion.equalsIgnoreCase(""))&&(subs.length<=0)){
                    programslist.remove(i);
                    i--;
                    System.out.println(" adding the bean ");
                    
                 }
                
            }
            
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            programslist=null;
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
            programslist=null;
        }
        System.out.println("ProgramList "+programslist.size());
        System.out.println("finalList "+finallist.size());
        return programslist;
    }

    
    
     public boolean deactivateProgram(String program_name,String term){
        DBConnection connection=DBConnection.getInstance();
        String query="update programs set status = 'deactive' where prog_name= '"+ program_name + "' and term = '"+term + "'";
        //String proquery ="update program set status='deactivate' where prog_id = "+ programID;
        Connection con=null;
        Statement stmt;
        try {
            con=connection.getConnection();
            System.out.println("Conection "+con);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            //stmt.executeUpdate(proquery);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
            
        }
        //System.out.println("ProgramList "+programslist.size());
       
        return true;
    }
    //check for update
    public boolean deleteProgram(String programID,String termID){
        DBConnection connection=DBConnection.getInstance();
         
        String getProgramID = "select prog_id from programs where prog_name= '"+programID + "' and term= '"+termID+"'";
        String deleteComman = "";
        String query="delete from programs where prog_name= '"+programID + "' and term= '"+termID+"'";
        //
        Connection con=null;
        int getID;
        Statement stmt;
       // ResultSet resultset;
        try {
                con=connection.getConnection();
                stmt = con.createStatement();
                ResultSet resultset=stmt.executeQuery(getProgramID);
                
                if(resultset.next())
                {
                    deleteComman =  "delete from school_has_prog where prog_id= "+ resultset.getInt("prog_id") ;
                }
                stmt.executeUpdate(deleteComman);
                stmt.executeUpdate(query);
                con.close();
           }
        catch (SQLException ex) {
            ex.printStackTrace();
            String d ="";
            
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
                     
        }
        
            
        //System.out.println("ProgramList "+programslist.size());
       
        return true; 

    }

    
   public ProgramBean showDetail(String program, String term){
        System.out.println("In DBManager");
        DBConnection connection=DBConnection.getInstance();
        String query_prog= "select * from programs where prog_name='"+program+"' and term='"+term+"'";
        
        ArrayList programslist=new ArrayList();
        Connection con=null;
        Statement stmt;
         ProgramBean programbean= new ProgramBean();
        try {
            con=connection.getConnection();
            System.out.println("Conection "+con);
            stmt = con.createStatement();
            String[] subject;
            ResultSet resultset_prog = stmt.executeQuery(query_prog);
            
            
            
            int i = 0;
           
            while(resultset_prog.next()){
                programbean.setCountry(resultset_prog.getString("country"));
                programbean.setDescription(resultset_prog.getString("description"));
                        programbean.setHousing(resultset_prog.getString("housing"));
                        programbean.setFee(resultset_prog.getString("fees"));
                        programbean.setImg1(resultset_prog.getString("img1"));
                        programbean.setImg2(resultset_prog.getString("img2"));
                        programbean.setImg3(resultset_prog.getString("img3"));
                        programbean.setProgramID(resultset_prog.getInt("prog_id"));
                        programbean.setProgramName(resultset_prog.getString("prog_name"));
                        programbean.setProgramType(resultset_prog.getString("prog_type"));
                        programbean.setRegion(resultset_prog.getString("region"));
                        programbean.setTestemony(resultset_prog.getString("testimony"));
                        programbean.setAppDetails(resultset_prog.getString("deadline"));
                        programbean.setTerm(resultset_prog.getString("term"));
                        programbean.setStatus(resultset_prog.getString("status"));
                                                
                  }
            ArrayList deptcodes=new ArrayList();
            String query_dept="select distinct s.dept_name from school s, school_has_prog sp where (s.dept_id=sp.dept_id) "+
                    " and (sp.prog_id in (select prog_id from programs where term='"+term+"' and prog_name='"+program+"'))";
            System.out.println(">>>>>>>>>>>>>"+query_dept);
            ResultSet resultset_deptname = stmt.executeQuery(query_dept);
            
            while(resultset_deptname.next()){
                        
                        deptcodes.add(resultset_deptname.getString("dept_name"));
                        
            }
            String[] dept_codes = new String[deptcodes.size()];
           dept_codes= (String[])deptcodes.toArray(dept_codes);
           programbean.setSubjects(dept_codes);
           ArrayList lang=new ArrayList();
           String query_lang="select distinct language from programs where prog_name= '"+program+"' and term= '"+term+"'";
           ResultSet resultset_language = stmt.executeQuery(query_lang);
           int found=0;
           while(resultset_language.next()){
               found=1;
               lang.add(resultset_language.getString("language"));
           }
           if(found==0)lang.add("English|");
           String[] languages=new String[20];
           int index=0;
            for( i=0;i<lang.size();i++){
               StringTokenizer tokens= new StringTokenizer((String)lang.get(i),"|");
               while(tokens.hasMoreTokens()){
                   String temp=tokens.nextToken();
                   if(!isPresent(languages,temp)) languages[index++]=temp;
               }
            }
          String[] lg= new String[index];
          for(i=0;i<index;i++){
              lg[i]=languages[i];
          }
           programbean.setLanguages(lg);
            
            
         con.close();   
        } catch (SQLException ex) {
            ex.printStackTrace();
            programbean=null;
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
            programslist=null;
        }
         
        //System.out.println("ProgramList "+programslist.size());
        return programbean;
    }
    
    public boolean updateProgram(ProgramBean programBean){
        String languages="";
        boolean flag=true;
        if(programBean.getStatus().equalsIgnoreCase("deactive")){
            deactivateProgram(programBean.getProgramName(),programBean.getTerm());
            return true;
        }
        if(programBean.getLanguages()!=null){
        for(int i=0;i<programBean.getLanguages().length;i++){
            if(programBean.getLanguages()[i]==null)break;
            if(flag){languages=programBean.getLanguages()[i];flag=false;}
            else{
                languages+="|"+programBean.getLanguages()[i];
            }
        }}
        System.out.println("  Status>>>>>" +programBean.getStatus());
        
        String query="update programs set prog_type='"+programBean.getProgramType() +"',description='"+programBean.getDescription() +"',fees='"+programBean.getFee() +"',testimony='',housing='"+programBean.getHousing()+"', "+
                " region='"+programBean.getRegion()+"',country='"+programBean.getCountry() +"',deadline='"+programBean.getAppDetails()+"', language='"+languages+"' where prog_name='"+programBean.getProgramName() +"' and term='"+programBean.getTerm() +"'";
        
        DBConnection connection=DBConnection.getInstance();
        
        try {
            Connection con= connection.getConnection();
            Statement stmt= con.createStatement();
            stmt.executeUpdate(query);
            query="select prog_id from programs  where prog_name='"+programBean.getProgramName() +"' and term='"+programBean.getTerm() +"'";
            ResultSet rs=stmt.executeQuery(query);
            String progid="";
            if(rs.next())progid=rs.getString("prog_id");
            String depts="";
            flag=true;
            for(int i=0;i<programBean.getSubjects().length;i++){
                if(programBean.getSubjects()[i]==null)break;
                if(programBean.getSubjects()[i].indexOf("|")!=-1)addSubject(programBean.getSubjects()[i]);
                if(flag){depts="dept_name='"+programBean.getSubjects()[i]+"'";flag=false;}
                else{
                    depts+=" or dept_name='"+programBean.getSubjects()[i]+"'";
                }
                
            }
            query="delete from school_has_prog where prog_id='"+progid+"'";
            stmt.executeUpdate(query);
            query="select distinct dept_id from school where "+depts;
            rs=stmt.executeQuery(query);
            String[] deptids=new String[20];
            int k=0;
            while(rs.next()){
                 deptids[k++]=rs.getString("dept_id");
            }
            
            for(int i=0;i<deptids.length;i++){
                if(deptids[i]==null)break;
                query="insert into school_has_prog (dept_id,prog_id) values('"+Integer.parseInt(deptids[i])+"','"+Integer.parseInt(progid)+"')";
                stmt.executeUpdate(query);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> returning true");
        return true;
    }
    public String uploadProgram(ArrayList list){
        
        String query="";
        int i =0;
        DBConnection connection=DBConnection.getInstance();
        Connection con=null;
         try {
        con=connection.getConnection();
        conn=con;
        Statement stmt = con.createStatement();
        System.out.println(">>>>>>>> "+list.size());
        for(int o=0;o<list.size();o++){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>adding "+o);
            
        ProgramBean programBean = (ProgramBean)list.get(o);    
        
        // Populating the Langauges List with "|" as delimiter from array of languages
        //---------- start -------------
        boolean flag=true;
        String languages="";
        for(i=0;programBean.getLanguages()!=null&&i<programBean.getLanguages().length;i++){
            if(programBean.getLanguages()[i]==null)break;
            if(flag){languages=programBean.getLanguages()[i];flag=false;}
            else{
                languages+="|"+programBean.getLanguages()[i];
            }
        }
        
        //-------- end -----------------
        
        
        int temp = 0;
        
       
            
            query="select prog_id from Programs where prog_name='"+programBean.getProgramName()+
                         "' and term='" + programBean.getTerm() + "' and status='active'";
                System.out.println("insert Program:>"+query);
                
                
                ResultSet rs = stmt.executeQuery(query);
                String pgid="";
                if(rs.next()) {
                        pgid=""+rs.getInt("prog_id");
                        continue;
                }
        
//            query to insert entry in Programs Table
//            -------------start------------------
            query="insert into Programs(prog_name,term,description,prog_type,fees,img1,img2,img3,language,housing,country,region,status) values("+
                        "'" + programBean.getProgramName() + "',"+
                        "'" + programBean.getTerm() + "',"+
                        "'" + programBean.getDescription() + "',"+
                        "'" + programBean.getProgramType() + "',"+
                        "'" + programBean.getFee() + "',"+
                        "'" + programBean.getImg1() + "',"+
                        "'" + programBean.getImg2() + "',"+
                        "'" + programBean.getImg3() + "',"+
                        "'" + languages+ "',"+
                        "'" + programBean.getHousing() +"',"+
                        "'" + programBean.getCountry() +"',"+
                        "'" + programBean.getRegion() +"','active')";
                
                System.out.println("insert Program:>"+query);
               
                 stmt  = con.createStatement();
                stmt.executeUpdate(query);
                 //----------------end------------------------
                
//                 selecting prog_id which is auto generated in Mysql when we enter the above program
//                 this is used to update the school_has_program table.
                query="select prog_id from Programs where prog_name='"+programBean.getProgramName()+
                         "' and term='" + programBean.getTerm() + "' and status='active'";
                System.out.println("insert Program:>"+query);
                rs = stmt.executeQuery(query);
                 pgid="";
                if(rs.next()) {
                        pgid=""+rs.getInt("prog_id");
                }
                       
            String depts="";
            flag=true;
            
           
            for( i=0;i<programBean.getSubjects().length;i++){
                if(programBean.getSubjects()[i]==null)break;
                String sub =programBean.getSubjects()[i]; 
                //if new subjects are added which is not already there in list ,, it comes in pair with college it belongs to
                // thus the paring is subject|department. So we need to add this subject first to table before performing
                // mapping between programs and Departments
                //--------------------- start ---------------------------------------------
                if(sub.indexOf("|")!=-1){ addSubject(sub); sub= sub.substring(0,sub.indexOf("|"));}
                //------------------------ end --------------------------------------------
                
                //Condition to select dept ids for a particular program for mapping them in school_has_programs table
                //--------------------------- start --------------------------------------
                if(flag){depts="dept_name='"+sub+"'";flag=false;}
                else{
                    depts+=" or dept_name='"+sub+"'";
                }
                //-------------------------------end -----------------------------------
                
            }
            
            // quering to get the dept_ids for the departments that program has
            //-----------start------------
            query="select distinct dept_id from school where "+depts;
            System.out.println(">>"+query);
            rs=stmt.executeQuery(query);
            String[] deptids=new String[100];
            int k=0;
            while(rs.next()){
                 deptids[k++]=rs.getString("dept_id");
            }
            //------- end -----------------
            
            // inserting the mapping values in school_has_prog table 
            //------ start --------------
            for( i=0;i<deptids.length;i++){
                if(deptids[i]==null)break;
                query="insert into school_has_prog (dept_id,prog_id) values('"+Integer.parseInt(deptids[i])+"','"+Integer.parseInt(pgid)+"')";
                System.out.println("insert School_Program:>"+query);
                stmt.executeUpdate(query);
            }
            //---------- stop ---------------
            
            
            
           
        }
        con.close();
         String msg="successfully entered the data + yxk";
         return msg;
        
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "" ;     }
        catch(ClassNotFoundException ex1){
            ex1.printStackTrace();
            return "error";
        }catch(Exception e){
            e.printStackTrace();
            
        }
        
        return "success sxp";
    }
    public UtilityBean getCommonDetail(){
        DBConnection connection=DBConnection.getInstance();
        UtilityBean utilitybean= new UtilityBean(); 
        String[][] region_country;
        String[][] school_dept;
        try{
        Connection con=connection.getConnection();
        String query="select distinct region from programs ";
        Statement stmt=con.createStatement();
        ResultSet resultset=stmt.executeQuery(query);
        ArrayList regions=new ArrayList();
         while(resultset.next()){
                regions.add(resultset.getString("region"));
            }
        System.out.println("Regions size is"+regions.size());
        region_country=new String[regions.size()][20];
        for(int i=0;i<regions.size();i++){
            query="select distinct country from programs where region=?";
            PreparedStatement preparedstatement=con.prepareStatement(query);
            preparedstatement.setString(1,(String)regions.get(i));
            resultset=preparedstatement.executeQuery();
            int country_index=0;
            System.out.println("country_index>>>"+country_index+" "+(String)regions.get(i));
            region_country[i][country_index++]=(String)regions.get(i);
            while(resultset.next()){
                System.out.println("Country :-");
                String country=resultset.getString(1);
                System.out.println("Country "+country+" index"+country_index);
                region_country[i][country_index++]=country;
                
            }
            System.out.println(">>> size  is "+region_country[i].length);
        }
        utilitybean.setRegion_country(region_country);
        query="select distinct school_name from school";
        resultset=stmt.executeQuery(query);
        ArrayList schools=new ArrayList();
        while(resultset.next()){
            schools.add(resultset.getString("school_name"));
        }
        school_dept=new String[schools.size()][50];
        for(int i=0;i<schools.size();i++){
            query="select distinct dept_name from school where school_name=?";
            PreparedStatement preparedstatement=con.prepareStatement(query);
            preparedstatement.setString(1,(String)schools.get(i));
            resultset=preparedstatement.executeQuery();
            int dept_index=0;
            school_dept[i][dept_index++]=(String)schools.get(i);
            while(resultset.next()){
                school_dept[i][dept_index++]=resultset.getString("dept_name");
                
            }
        }
        utilitybean.setSchool_departments(school_dept);
        ArrayList lang= new ArrayList();
        String query_lang="select distinct language from programs ";
           ResultSet resultset_language = stmt.executeQuery(query_lang);
           int found=0;
           while(resultset_language.next()){
               found=1;
               lang.add(resultset_language.getString("language"));
           }
           if(found==0)lang.add("English|");
           String[] languages=new String[20];
           int index=0;
            for(int i=0;i<lang.size();i++){
               StringTokenizer tokens= new StringTokenizer((String)lang.get(i),"|");
               while(tokens.hasMoreTokens()){
                   String temp=tokens.nextToken();
                   if(!isPresent(languages,temp)) languages[index++]=temp;
               }
            }
          String[] lg= new String[index];
          for(int i=0;i<index;i++){
              lg[i]=languages[i];
          }
          utilitybean.setLanguages(lg);
        con.close();
        }catch(SQLException e){
            e.printStackTrace();
            utilitybean=null;
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            utilitybean=null;
        }
        return utilitybean;
    }
    
    private boolean isPresent(String[] sample, String key ){
        boolean found=false;
        for(int i=0;i<sample.length;i++){
            if(sample[i]!=null && sample[i].equalsIgnoreCase(key)){
                found=true;
                break;
            }
        }
        return found;
    }
    
     public void addSubject(String subject) throws SQLException, ClassNotFoundException{
        
        StringTokenizer tokens=new StringTokenizer(subject,"|");
        String sub = subject.substring(0,subject.indexOf('|'));
        String school=subject.substring(subject.indexOf('|')+1);
        String query ="insert into school(dept_name,school_name) values('"+sub+"','"+school+"')";
         System.out.println("insert subjects:>"+query);
         Connection con=null;
        if(conn==null){
         DBConnection connection=DBConnection.getInstance();
        connection.getConnection();
        }else con=conn;
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("select * from school where dept_name='"+sub+"' and school_name='"+school+"' ");
        if(!rs.next()) stmt.executeUpdate(query);
        con.close();
    }


   
}

