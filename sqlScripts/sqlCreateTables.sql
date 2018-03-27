--NOT TO BE USED AS-IS. Every occurence of [CAMPAIGN] should be replaced by the name of the campaign before attempting to run this file.

--Have to be in this order as each table has a foreign key constraint on the subsequent table
DROP TABLE IF EXISTS [CAMPAIGN]_serverLogs;
DROP TABLE IF EXISTS [CAMPAIGN]_clicks;
DROP TABLE IF EXISTS [CAMPAIGN]_impressions;
DROP TABLE IF EXISTS [CAMPAIGN]_users;

CREATE TABLE [CAMPAIGN]_users (
ID		    char(19) NOT NULL,
Gender		enum('Male', 'Female', 'Other') NOT NULL,
AgeRange	enum('<25', '25-34', '35-44', '45-54', '>54') NOT NULL,
Income		enum('Low', 'Medium', 'High') NOT NULL,
PRIMARY KEY(ID)
);

CREATE TABLE [CAMPAIGN]_impressions (
Date		dateTime NOT NULL,
ID		    char(19) NOT NULL,
Context		enum('Blog', 'News', 'Shopping', 'Social Media') NOT NULL,
Cost		float NOT NULL,
PRIMARY KEY(Date, ID),
FOREIGN KEY(ID) REFERENCES [CAMPAIGN]_users(ID)
);

CREATE TABLE [CAMPAIGN]_clicks (
Date		dateTime NOT NULL,
ID		    char(19) NOT NULL,
ClickCost	float NOT NULL,
PRIMARY KEY(Date, ID),
FOREIGN KEY(ID) REFERENCES [CAMPAIGN]_impressions(ID)
);

CREATE TABLE [CAMPAIGN]_serverLogs (
EntryDate	dateTime NOT NULL,
ID		    char(19) NOT NULL,
ExitDate	dateTime,
PagesViewed	int NOT NULL,
PRIMARY KEY(EntryDate, ID),
FOREIGN KEY(ID) REFERENCES [CAMPAIGN]_clicks(ID)
);
