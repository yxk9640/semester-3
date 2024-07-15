
import sqlite3

def initial_setup(point):
    sql_create_Department = ''' CREATE TABLE IF NOT EXISTS Department(
    DepartmentID INTEGER PRIMARY KEY,
    DepartmentName TEXT
    )'''

    sql_create_Employee = ''' CREATE TABLE IF NOT EXISTS Employee (
    EmployeeID INTEGER PRIMARY KEY NOT NULL,
    EmployeeName TEXT,
    JoiningDate TEXT,
    Salary REAL, 
    DepartmentID INTEGER,
    Experience INTEGER,
    FOREIGN KEY(DepartmentID) REFERENCES 
    Department(DepartmentID)
    )''' 

    sql_insert_Department = ''' INSERT INTO Department (DepartmentID, DepartmentName) 
    VALUES (1,'IT'),(2,'Marketing'),(3,'HR'),(4,'Sales')
    '''

    sql_insert_Employee =  ''' INSERT INTO Employee 
    (EmployeeID, EmployeeName, DepartmentID, JoiningDate, Salary, Experience)
    VALUES 
    (101,'David',1, '2005-2-10',40000,5),
    (102,'Michael',1, '2018-7-23',20000,2),
    (103,'Susan',2, '2016-5-19',25000,3),
    (104,'Robert',2, '2017-12-28',28000,7),
    (105,'Linda',3, '2004-6-4',42000,9),
    (106,'William',3, '2012-9-11',30000,5),
    (107,'Richard',4, '2014-8-21',32000,4),
    (108,'Karen',4, '2011-10-17',30000,8)
    '''
#Create Department table
    point.execute(sql_create_Department)
#     print('\t Department Table has been created')
#Create Employee table
    point.execute(sql_create_Employee)
#     print('\t Employee Table has been created')
#     print()


# #Insert into Department
    check_dept_items = point.execute('''SELECT * FROM Department''')
    check_dept_result = check_dept_items.fetchall()
    if (  len(check_dept_result) == 0):
        point.execute(sql_insert_Department)
#         print(' Department Table has been populated with given values')
#         print(' Department Table with initialized values')
#     initial_dept_query = '''SELECT * FROM Department'''
#     point.execute(initial_dept_query)
#     initial_dept_result = point.fetchall()
    
#     print('\t Department Department_Name',sep='          ')
#     for dept in initial_dept_result:
#         print(f'\t {dept[0]}		{dept[1]}')
#     print()


#Insert into Employee
    check_emp_items = point.execute('''SELECT * FROM Employee''')
    check_emp_result = check_emp_items.fetchall()

    if ( len(check_emp_result) == 0 ):
        point.execute(sql_insert_Employee )
#         print(' Employee Table has been populated with given values')
#         print(' Employee Table with initialized values')
#     initial_emp_query = '''SELECT * FROM Employee'''
#     initial_emp_result = point.execute(initial_emp_query)

#     print('\t Employee_ID	Employee_Name	Joining_Date	Salary	Department_ID	Experience')
#     for emp in initial_emp_result:
#         print(f'\t {emp[0]}		{emp[1]}		{emp[2]}	{emp[3]}		{emp[4]}		{emp[5]}')
#     print()
#     print('\t Initial Setup Completed')


def main():
#Create a database connection
    connection = sqlite3.connect('company.db')
#     print(f'Database has been created : company.db')
    point = connection.cursor()


#This method will create a department and employee and insert values
#     print(f"Task#{1}: \n Initial Setup: ")
    initial_setup(point)
    print()


    connection.commit()
    connection.close()

main()
