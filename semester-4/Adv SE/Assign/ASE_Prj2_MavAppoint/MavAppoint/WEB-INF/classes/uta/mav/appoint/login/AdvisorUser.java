package uta.mav.appoint.login;

import java.util.ArrayList;

import uta.mav.appoint.visitor.Visitor;

/**
 * @author Ruchi
 *
 */
public class AdvisorUser extends LoginUser{
	private String pname;
	private String dept;
	private ArrayList<String> majors;
	private String notification;
	private String nameLow;
	private String nameHigh;
	private String cutOffPreference;

	public AdvisorUser(){
		super();
	}
	
	//Testing Constructor Dont modify it
	public AdvisorUser( String pname, String name_low, String name_high, Integer degree_types){
		super();
		this.pname = pname;
		this.nameLow = name_low;
		this.nameHigh = name_high;
		setDegType(degree_types);
		this.majors = new ArrayList<String>();
		this.majors.add("Software Engineering");
		this.majors.add("Computer Science");
		this.majors.add("Computer Engineering");
	}
	
	
	public AdvisorUser(String em, String p){
		super(em);
		pname = p;
	}
	
	public AdvisorUser(String em){
		super(em);
	}
	
	public Boolean advisesStudent(ArrayList<String> studDeps, ArrayList<String> studMajors, Character studentLastName, Integer studDegTypes)
	{
		Boolean advises = false;
		
		ArrayList<String> advDeps = getDepartments();
		for(int advDepIndex = 0; advDepIndex < advDeps.size(); advDepIndex++)
		{
			for(int studDepIndex = 0; studDepIndex < studDeps.size(); studDepIndex++)
				if(advDeps.get(advDepIndex).equals(studDeps.get(studDepIndex)))
				{
					advises = true;
					break;
				}
		}
		
		if(!advises)
		{
			//System.out.println(getPname()+" -- Does not advise w/ deps " + studDeps.get(0));
			return false;
		}
		advises = false;
		
		ArrayList<String> advMajors = getMajors();
		for(int advDepIndex = 0; advDepIndex < advMajors.size(); advDepIndex++)
		{
			for(int studDepIndex = 0; studDepIndex < studMajors.size(); studDepIndex++)
				if(advMajors.get(advDepIndex).equals(studMajors.get(studDepIndex)))
				{
					advises = true;
					break;
				}
		}
		
		if(!advises)
		{
			//System.out.println(getPname()+" -- Does not advise w/ majors " + studMajors.get(0));
			return false;
		}
		
		Character advNameLow = getNameLow().charAt(0);
		Character advNameHigh = getNameHigh().charAt(0);
		//System.out.println(" FOUND ---------------------------------------- "+advNameLow);
		if(studentLastName<advNameLow || advNameHigh<studentLastName)
		{
			//System.out.println(getPname()+" -- Does not advise w/ name " + advNameLow + " and "+advNameHigh);
			return false;
		}
		
		int tempDeg = studDegTypes;
		Integer advDegTypes = getDegType();
		for(int degLevel=4; degLevel>0; degLevel/=2)
		{
			Boolean advTakes = false;
			Boolean studIs = false;
			if(advDegTypes>=degLevel)
			{
				advDegTypes -= degLevel;
				advTakes = true;
			}
			
			if(studDegTypes>=degLevel)
			{
				studDegTypes -= degLevel;
				studIs = true;
			}
			
			if(advTakes && studIs)
				return true;
		}
		
		//System.out.println(getPname()+" -- Does not advise w/ degTypes " + tempDeg);
		return false;
	}
	
	@Override
	public String getHeader(){
		return "lead_advisor_header";
	}

	/**
	 * @return the pname
	 */
	@Override
	public String getPname() {
		return pname;
	}
	
	
	@Override
	public void accept(Visitor v){
		v.check(this);
	}
	
	@Override
	public ArrayList<Object> accept(Visitor v, Object o){//allow javabean to be passed in
		return v.check(this,o);
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public ArrayList<String> getMajors() {
		return majors;
	}

	public void setMajors(ArrayList<String> majors) {
		this.majors = majors;
	}

	public String getNameLow() {
		return nameLow;
	}

	public void setNameLow(String nameLow) {
		this.nameLow = nameLow;
	}

	public String getNameHigh() {
		return nameHigh;
	}

	public void setNameHigh(String nameHigh) {
		this.nameHigh = nameHigh;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
	public String getCutOffPreference() {
		return cutOffPreference;
	}

	public void setCutOffPreference(String cutOffPreference) {
		this.cutOffPreference = cutOffPreference;
	}

	@Override
	public String getNotification() {
		return notification;
	}
	
	@Override
	public void setNotification(String notification) {
		this.notification = notification;
	}
}
