--TODO: Include variable in this file so we can prefix the name of the created tables

--Have to be in this order as each table has a foreign key constraint on the subsequent table
DROP TABLE IF EXISTS serverLogs;
DROP TABLE IF EXISTS impressions;
DROP TABLE IF EXISTS clicks;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
ID		    char(19) NOT NULL,
Gender		enum('Male', 'Female', 'Other') NOT NULL,
AgeRange	enum('<25', '25-34', '35-44', '45-54', '>54') NOT NULL,
Income		enum('Low', 'Medium', 'High') NOT NULL,
Cost		float NOT NULL,
PRIMARY KEY(ID)
);

CREATE TABLE impressions (
Date		dateTime NOT NULL,
ID		    char(19) NOT NULL,
Context		enum('Blog', 'News', 'Shopping', 'Social Media') NOT NULL,
PRIMARY KEY(Date, ID),
FOREIGN KEY(ID) REFERENCES users(ID)
);

CREATE TABLE clicks (
Date		dateTime NOT NULL,
ID		    char(19) NOT NULL,
ClickCost	float NOT NULL,
PRIMARY KEY(Date, ID),
FOREIGN KEY(ID) REFERENCES impressions(ID)
);

CREATE TABLE serverLogs (
EntryDate	dateTime NOT NULL,
ID		    char(19) NOT NULL,
ExitDate	dateTime,
PagesViewed	int NOT NULL,
PRIMARY KEY(EntryDate, ID),
FOREIGN KEY(ID) REFERENCES clicks(ID)
);
