*CSE 6324 - Coding Project 2*
**MavAppoint - Intermediate**

***Contents of this archive:***
1. README
2. MavAppoint.war
3. MavAppoint.sql

**Setup & Deploy**

***Requirements:***
1. Apache Tomcat Server
2. Eclipse or any other IDE
3. Somewhere to host database (either download mySQL & mySQL workbench or you can host database on uta.cloud)

***Steps***
Step 1: Create database using the .sql file
Step 2: Import .war file into Eclipse as a Dynamic Web Project
Step 3: Add server to your Eclipse project
Step 4: Modify database connection details in mavappoint.properties
Step 5: Also add sender email address and password in mavappoint.properties & other specified places
Step 6: Run your project on the server; should open in a browser window
Step 7: You're all set!

***Currently Working Functionalities:***
1. Login/Logout (All Users)
    - Student Registration (with sending email for temporary password)
    - Forgot Password/Change Password
    - Maintaining Sessions
2. Student User:
    - CRUD on own Appointments (View, Edit, Delete, Reminder)
    - View Available Advisors based on your Department/Major
    - Create time slots (needs work)
3. Advisor User:
    - CRUD on own Appointments (View, Edit, Delete, Reminder)
    - Customize Settings (verify with backend)
    - Add Availabilty time slots (needs work)
4. Admin User:
    - Add new Advisor
    - Delete existing Advisor
    - Assign Advisor to Students based on Department, Major & Last names
    - CRUD on all Appointments (View, Edit, Delete, Reminder)

***Some requirements you may work on to get the existing system to function smoothly:***
1. Creating Appointments
2. Time Slots & Availability Management
3. Waitlist Management
4. Finetune UI (probably needs work w.r.t. css files & bootstrap)
5. Make any enhancements or add new features as you wish.