PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Train" ("number" integer NOT NULL,"name" varchar(50) NOT NULL,"source" varchar(50) NOT NULL,"destination" varchar(50) NOT NULL,"on_day" varchar(10) NOT NULL,"fare_premium" integer,"fare_general" integer, PRIMARY KEY (number, on_day));
INSERT INTO Train VALUES(1,'Orient Express','Paris','Istanbul','Friday',800,600);
INSERT INTO Train VALUES(2,'Flying Scotsman','Edinburgh','London','Monday',4000,3500);
INSERT INTO Train VALUES(1,'Orient Express','Paris','Istanbul','Saturday',800,600);
INSERT INTO Train VALUES(1,'Orient Express','Paris','Istanbul','Sunday',800,600);
INSERT INTO Train VALUES(2,'Flying Scotsman','Edinburgh','London','Tuesday',4000,3500);
INSERT INTO Train VALUES(2,'Flying Scotsman','Edinburgh','London','Wednesday',4000,3500);
INSERT INTO Train VALUES(3,'Golden Arrow','Victoria','Dover','Saturday',980,860);
INSERT INTO Train VALUES(3,'Golden Arrow','Victoria','Dover','Sunday',980,860);
INSERT INTO Train VALUES(4,'Golden Chariot','Bangalore','Goa','Wednesday',4300,3800);
INSERT INTO Train VALUES(4,'Golden Chariot','Bangalore','Goa','Thursday',4300,3800);
INSERT INTO Train VALUES(4,'Golden Chariot','Bangalore','Goa','Friday',4300,3800);
INSERT INTO Train VALUES(5,'Maharaja Express','Delhi','Mumbai','Sunday',5980,4510);
INSERT INTO Train VALUES(5,'Maharaja Express','Delhi','Mumbai','Thursday',5980,4510);
CREATE TABLE IF NOT EXISTS "Train_Status" (
	"train_number"	integer NOT NULL,
	"date"	DATE NOT NULL,
	"day" varchar(20) NOT NULL,
	"seats_available_premium"	integer NOT NULL,
	"seats_available_general"	integer NOT NULL,
	"seats_occupied_premium"	integer NOT NULL,
	"seats_occupied_general"	integer NOT NULL,
	PRIMARY KEY("train_number","date"),
	FOREIGN KEY("train_number") REFERENCES "Train"("number")
	FOREIGN KEY("day") REFERENCES "Train"("on_day")
);
INSERT INTO Train_Status VALUES(1,'19/02/2022', "Saturday",10,10,0,0);
INSERT INTO Train_Status VALUES(2,'21/02/2022', "Monday", 5,3,2,3);
INSERT INTO Train_Status VALUES(3,'20/02/2022', "Sunday", 1,3,1,3);
INSERT INTO Train_Status VALUES(4,'24/02/2022', "Thursday", 10,8,8,4);
CREATE TABLE IF NOT EXISTS "Passenger" (
	"ssn"	INTEGER NOT NULL,
	"age"	INTEGER NOT NULL,
	"first_name"	INTEGER, "last_name" varchar(50) NOT NULL, "phone" varchar(20) NOT NULL, "address" text NOT NULL, "city" varchar(30) NOT NULL,
	PRIMARY KEY("ssn")
);
INSERT INTO Passenger VALUES(240471168,59,'Josephine','Darakjy','810-374-9840','4 B Blue Ridge Blvd','Brighton');
INSERT INTO Passenger VALUES(250951162,55,'Simona','Morasca','419-800-6759','3 Mcauley Dr','Ashland');
INSERT INTO Passenger VALUES(256558303,57,'Abel','Maclead','631-677-3675','37275 St Rt 17m M','Middle Island');
INSERT INTO Passenger VALUES(264816896,51,'James','Butt','504-845-1427','6649 N Blue Gum St','New Orleans');
INSERT INTO Passenger VALUES(268682534,20,'Leota','Dilliard','408-813-1105','7 W Jackson Blvd','San Jose');
INSERT INTO Passenger VALUES(272610795,54,'Donette','Foller','513-549-4561','34 Center St','Hamilton');
INSERT INTO Passenger VALUES(272913578,60,'Mitsue','Tollner','773-924-8565','7 Eads St','Chicago');
INSERT INTO Passenger VALUES(277292710,45,'Graciela','Ruta','440-579-7763','98 Connecticuit Ave Nw','Chagrin Falls');
INSERT INTO Passenger VALUES(284965676,52,'Kiley','Caldarera','310-254-3084','25 E 75th St #69','Los Angles');
INSERT INTO Passenger VALUES(285200976,45,'Art','Venere','856-264-4130','8 W Cerritos Ave #54','Bridgeport');
INSERT INTO Passenger VALUES(286411536,53,'Gladys','Rim','414-377-2880','322 New Horizon Blvd','Milwaukee');
INSERT INTO Passenger VALUES(290123298,59,'Meaghan','Garufi','931-235-7959','69734 E Carrillo St','Mc Minnville');
INSERT INTO Passenger VALUES(294860856,52,'Yuki','Whobrey','313-341-4470','1 State Route 27','Taylor');
INSERT INTO Passenger VALUES(302548590,60,'Jesse','Paul','865-543-4564','112 S Aks Rd','Texas');
INSERT INTO Passenger VALUES(309323096,34,'Lenna','Paprocki','907-921-2010','639 Main St','Anchorage');
INSERT INTO Passenger VALUES(310908858,44,'Sage','Wieser','605-794-4895','5 Boston Ave #88','Sioux Falls');
INSERT INTO Passenger VALUES(317434088,54,'Fletcher','Flosi','815-426-5657','394 Manchester Blvd','Rockford');
INSERT INTO Passenger VALUES(322273872,25,'Kris','Marrier','410-804-4694','228 Runamuck Pl #2808','Baltimore');
INSERT INTO Passenger VALUES(331160133,58,'Cammy','Albares','956-841-7216','56 E Morehead St','Laredo');
INSERT INTO Passenger VALUES(331293204,59,'Mattie','Poquette','602-953-6360','73 State Road 434 E','Phoenix');
CREATE TABLE IF NOT EXISTS "Booking" (
	"ticket_category"	TEXT NOT NULL,
	"reservation_status"	TEXT NOT NULL,
	"passenger_ssn"	INTEGER NOT NULL,
	"train_number"	INTEGER NOT NULL,
	"date"	DATE NOT NULL,
	PRIMARY KEY("passenger_ssn","train_number","date"),
	FOREIGN KEY("date") REFERENCES "Train_Status"("date"),
	FOREIGN KEY("train_number") REFERENCES "Train_Status"("train_number"),
	FOREIGN KEY("passenger_ssn") REFERENCES "Passenger"("ssn")
);
INSERT INTO Booking VALUES('Premium','Booked',264816896,3,'20/02/2022');
INSERT INTO Booking VALUES('General','Booked',240471168,2,'21/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',285200976,4,'24/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',285200976,2,'21/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',317434088,2,'21/02/2022');
INSERT INTO Booking VALUES('General','Booked',310908858,2,'21/02/2022');
INSERT INTO Booking VALUES('General','Booked',322273872,2,'21/02/2022');
INSERT INTO Booking VALUES('Premium','WaitList',256558303,3,'20/02/2022');
INSERT INTO Booking VALUES('General','WaitList',302548590,2,'21/02/2022');
INSERT INTO Booking VALUES('Premium','WaitList',284965676,3,'20/02/2022');
INSERT INTO Booking VALUES('General','Booked',277292710,3,'20/02/2022');
INSERT INTO Booking VALUES('General','Booked',331160133,3,'20/02/2022');
INSERT INTO Booking VALUES('General','Booked',331293204,3,'20/02/2022');
INSERT INTO Booking VALUES('General','WaitList',290123298,3,'20/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',286411536,4,'24/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',294860856,4,'24/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',317434088,4,'24/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',310908858,4,'24/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',322273872,4,'24/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',256558303,4,'24/02/2022');
INSERT INTO Booking VALUES('Premium','Booked',302548590,4,'24/02/2022');
INSERT INTO Booking VALUES('General','Booked',284965676,4,'24/02/2022');
INSERT INTO Booking VALUES('General','Booked',277292710,4,'24/02/2022');
INSERT INTO Booking VALUES('General','Booked',331160133,4,'24/02/2022');
INSERT INTO Booking VALUES('General','Booked',331293204,4,'24/02/2022');
COMMIT;
