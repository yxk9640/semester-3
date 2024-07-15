/*
 * IProgram.java
 *
 * Created on March 1, 2006, 10:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.uta.sams.beans;

/**
 *
 * @author user-yxk9640
 */
 interface IProgram {

   public String getRegion ();
   public void setRegion (String region);
   public String getCountry ();
   public void setCountry (String country);
   public String getProgramName ();
   public void setProgramName (String programName);
   public String getProgramType ();
   public void setProgramType (String programType);
   public String getTerm ();
   public void setTerm (String term);
   public String[] getSubjects ();
   public void setSubjects (String[] subjects);
   public String[] getLanguages ();
   public void setLanguages (String[] languages);
   public String getDescription ();
   public void setDescription (String description);
   public String getFee ();
   public void setFee (String fee);
   public String getHousing ();
   public void setHousing (String housing);
   public String getAppDetails ();
   public void setAppDetails (String appDetails);
   public String getTestemony ();
   public void setTestemony (String testemony);
   public String getImg1 ();
   public void setImg1 (String img1);
   public String getImg2 ();
   public void setImg2 (String img2);
   public String getImg3 ();
   public void setImg3 (String img3);
   public String getStatus ();
   public void setStatus (String status);
}

    

