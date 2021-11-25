Name: Nikhil Pradhan
ID: 2020A7PS1205P

PROJECT DESCRIPTION

SplitWise is an app that allows users to keep track of how much they owe each other whenever they split bills. It also has an additional feature of forming groups. People who end up splitting bills frequently can form groups. The advantage of using groups is that a list of IDs does not need to be entered.

This project has 4 classes:
1.	Main: This class contains main method and it is the class that needs to be executed. It takes the user’s input and calls the relevant methods using an if else-if else structure.
2.	Manager: This class handles all the activities of the project. A manager object can create users, create groups, modify groups, show balance and handle transactions. It also contains a list of all the users and a list of all the groups.
3.	User: This stores all the information of the user. It stores the user’s name, ID, password, and how much the particular user owes the other users.
4.	Group: It contains the list of IDs of users present in the group

This project has been written in such a way that the program will not crash even if incorrect queries are written. This way, the user will not have to enter all the previous information again if he makes a slight mistake while entering a command.

FEATURES

This program supports the following features:
1.	Create a new user
2.	Create a new group
3.	Join a group
4.	Leave a group
5.	Pay another user (can be used for returning money owed)
6.	Show balance
7.	Split a bill
    a.	Equally among everyone
    b.	Based on exact amount
    c.	Based on percentage
The program will continue executing till the user enters “END”

SOFTWARE REQUIRED

This program uses ArrayList, so any version of Java after Java Platform SE 8 (inclusive) will work.

COMMAND SYNTAX

1.	CREATE USER <Name> <Password>
2.	CREATE GROUP <Space separated list of user ids>
3.	JOIN <Group ID> <User ID>
4.	LEAVE <Group ID> <User ID>
5.	SHOW ALL
6.	SHOW <User ID>
7.	SHOW ALL USERS
8.	SHOW ALL GROUPS
9.	PAY <ID of Payer> <ID of Receiver> <amount>
10.	EXPENSE <ID of User who paid> <No. of users> <space separated list of user ids> EQUAL <Total Amount>
11.	EXPENSE <ID of User who paid> <No. of users> <space separated list of user ids> EXACT <Space separated exact amounts>
12.	EXPENSE <ID of User who paid> <No. of users> <space separated list of user ids> PERCENT <Total Amount> <Space separated percentage (excluding user who paid)>
13.	EXPENSE GROUP <Group ID> <ID of user who paid> EQUAL <Total Amount>
14.	EXPENSE GROUP <Group ID> <ID of user who paid> EXACT <Space separated exact amounts (excluding user who paid)>
15.	EXPENSE GROUP <Group ID> <ID of user who paid> PERCENT <Total Amount> <Space separated list of percentages (excluding user who paid)>
16.	END



VOD Link (Demo Video)
https://drive.google.com/drive/folders/1-boC3xZjJnsP9tUzraCBMbA8AvbGk14k?usp=sharing