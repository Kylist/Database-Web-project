use final_project;
insert into user_type (typeID, typeName, permission)
values 
	(0, 'guest', 0),
    (1, 'user', 1),
    (2, 'senior user', 2),
    (3, 'admin', 3);
    
insert into user (userID, password, fName, lName, typeID, email, country, state, avatarURL, offenceNo, isSuspended)
values('TEST', 'TEST', 'Avin', 'Smith', 3, 'tug50154@temple.edu', 'USA', 'PA', null, 0, 0);

