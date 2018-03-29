--NOT TO BE USED AS-IS. Every occurence of [CAMPAIGN] should be replaced by the name of the campaign before attempting to run this file.

--Have to be in this order as each table has a foreign key constraint on the subsequent table
DROP TABLE IF EXISTS [CAMPAIGN]_serverLogs;
DROP TABLE IF EXISTS [CAMPAIGN]_clicks;
DROP TABLE IF EXISTS [CAMPAIGN]_impressions;
DROP TABLE IF EXISTS [CAMPAIGN]_users;

DELETE FROM campaignNames WHERE name='[CAMPAIGN]';