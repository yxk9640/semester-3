package uta.mav.appoint.team3.controller;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.helpers.TimeSlotHelpers;
import uta.mav.appoint.login.LoginUser;

import uta.mav.appoint.visitor.ManageTimeSlotVisitor;
import uta.mav.appoint.visitor.Visitor;

public class DeleteTimeSlotController {

public static String deleteTimeSlotController(String date, String startTime, String endTime,String email, LoginUser user, String repeat, String reason){
		
		int rep;
		try{
			rep = Integer.parseInt(repeat);
		}
		catch(Exception e){
			rep = 0;
		}
		
		AllocateTime at = new AllocateTime();
		at.setDate(date);
		at.setStartTime(startTime);
		at.setEndTime(endTime);
		at.setEmail(email);
		at.setReasons(reason);
		
		Visitor v = new ManageTimeSlotVisitor();
		user.accept(v,at);
		for (int i=0;i<rep;i++){
			String nextDate = TimeSlotHelpers.addDate(at.getDate(),1);
			String[] parts = nextDate.split("-");
			String part2 = parts[1]; 
			String part3 = parts[2]; 
			if(part2.length() == 1)
			{
				part2 = "0"+parts[1];
				if(part3.length() == 1)
					part3 = "0"+parts[2];

				nextDate = parts[0]+"-"+part2+"-"+part3;
			}
			at.setDate(nextDate);
			user.accept(v,(Object)at);
		}	
		return "Advising hours have been deleted successfully";

	}
}
