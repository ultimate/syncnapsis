select points, numberofempires, username from userrank inner join app_user on userrank.fkentity = app_user.id order by points desc
select shortname, numberofempires, economy, military, science, averagetotalperempire, total  from alliancerank inner join alliance on alliancerank.fkentity = alliance.id order by total desc
select shortname, economy, military, science, total from empirerank inner join empire on empirerank.fkentity = empire.id order by total desc

select * from alliancemember inner join empire on fkempire = empire.id where shortname = 'emp10'
select * from empire where fkuser = 16999

select * from alliancerank order by total desc