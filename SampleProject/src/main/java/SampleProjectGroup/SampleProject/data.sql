DECLARE @PolicyNumber VARCHAR(20)
SET @PolicyNumber = '5756899903'

select PublicID,ProductCode,ID,IssueDate,CreateTime,OriginalEffectiveDate from pc_policy  where ID in (select PolicyID from pc_policyperiod where PolicyNumber=@PolicyNumber);

select PublicID,ID,PolicyNumber,PolicyID,PolicyTermID,EditEffectiveDate,PeriodStart,PeriodEnd,WrittenDate,ModelDate,UpdateTime from pc_policyperiod where PolicyNumber=@PolicyNumber;

select PublicID,ID,FixedID,UpdateTime,CreateTime,EffectiveDate,ExpirationDate from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber=@PolicyNumber);

select PublicID,FixedID,ID,CPLine,CreateTime,EffectiveDate,ExpirationDate from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber=@PolicyNumber));

select PublicID,ID,FixedID,CPLocation,CreateTime,EffectiveDate,ExpirationDate,BuildingValue_amt from pc_cpbuilding where CPLocation in (select ID from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber=@PolicyNumber)));

select PublicID,FixedID,ID,CPBuilding,CreateTime,EffectiveDate,ExpirationDate,DirectTerm1,DirectTerm2,DirectTerm3 from pc_cpbuildingcov where CPBuilding in  (select ID from pc_cpbuilding where CPLocation in (select ID from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber=@PolicyNumber))));

select PublicID,ID,CPBuildingCov,EffectiveDate,ExpirationDate from pc_cpcost where CPBuildingCov in (select ID from pc_cpbuildingcov where CPBuilding in  (select ID from pc_cpbuilding where CPLocation in (select ID from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber=@PolicyNumber)))));

select PublicID,ID,CPCost,EffDate,ExpDate, EffectiveDate,ExpirationDate from pc_cptransaction where CPCost in (select ID from pc_cpcost where CPBuildingCov in (select ID from pc_cpbuildingcov where CPBuilding in  (select ID from pc_cpbuilding where CPLocation in (select ID from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber=@PolicyNumber))))));


