--NOT TO BE USED AS-IS. Every occurence of [VAR] should be replaced by the name of the campaign before attempting to run this file.

--Have to be in this order as each table has a foreign key constraint on the subsequent table
DROP TABLE IF EXISTS [VAR]_serverLogs;
DROP TABLE IF EXISTS [VAR]_clicks;
DROP TABLE IF EXISTS [VAR]_impressions;
DROP TABLE IF EXISTS [VAR]_users;

DELETE FROM campaignNames WHERE name='[VAR]';