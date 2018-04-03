--NOT TO BE USED AS-IS. Every occurence of [VAR] should be replaced by the name of the campaign before attempting to run this file.

--Have to be in this order as each table has a foreign key constraint on the subsequent table
DROP TABLE IF EXISTS [VAR]_serverLogs;
DROP TABLE IF EXISTS [VAR]_clicks;
DROP TABLE IF EXISTS [VAR]_impressions;
DROP TABLE IF EXISTS [VAR]_users;

CREATE TABLE [VAR]_users (
ID		    char(19) NOT NULL,
Gender		enum('Male', 'Female', 'Other') NOT NULL,
AgeRange	enum('<25', '25-34', '35-44', '45-54', '>54') NOT NULL,
Income		enum('Low', 'Medium', 'High') NOT NULL,
PRIMARY KEY(ID)
);

CREATE TABLE [VAR]_impressions (
Date		dateTime NOT NULL,
ID		    char(19) NOT NULL,
Context		enum('Blog', 'News', 'Shopping', 'Social Media') NOT NULL,
Cost		float NOT NULL,
PRIMARY KEY(Date, ID),
FOREIGN KEY(ID) REFERENCES [VAR]_users(ID)
);

CREATE TABLE [VAR]_clicks (
Date		dateTime NOT NULL,
ID		    char(19) NOT NULL,
ClickCost	float NOT NULL,
PRIMARY KEY(Date, ID),
FOREIGN KEY(ID) REFERENCES [VAR]_impressions(ID)
);

CREATE TABLE [VAR]_serverLogs (
EntryDate	dateTime NOT NULL,
ID		    char(19) NOT NULL,
ExitDate	dateTime,
PagesViewed	int NOT NULL,
PRIMARY KEY(EntryDate, ID),
FOREIGN KEY(ID) REFERENCES [VAR]_clicks(ID)
);
