package SampleProjectGroup.SampleProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataAccessLayer {

	public static String PolicyNumber = "@@PolicyNumber@@";

	private String[] SQL_QUERIES = {
			"select PublicID,ProductCode,ID,IssueDate,CreateTime,OriginalEffectiveDate from pc_policy  where ID in (select PolicyID from pc_policyperiod where PolicyNumber= '"
					+ PolicyNumber + "')",
			"select PublicID,ID,PolicyNumber,PolicyID,PolicyTermID,EditEffectiveDate,PolicyNumber,PeriodStart,PeriodEnd,WrittenDate,ModelDate,UpdateTime from pc_policyperiod where PolicyNumber= '"
					+ PolicyNumber + "'",
			"select PublicID,ID,FixedID,UpdateTime,CreateTime,EffectiveDate,ExpirationDate from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber= '"
					+ PolicyNumber + "')",
			"select PublicID,FixedID,ID,CPLine,CreateTime,EffectiveDate,ExpirationDate from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber= '"
					+ PolicyNumber + "'))",
			"select PublicID,ID,FixedID,CPLocation,CreateTime,EffectiveDate,ExpirationDate,BuildingValue_amt from pc_cpbuilding where CPLocation in (select ID from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber= '"
					+ PolicyNumber + "')))",
			"select PublicID,FixedID,ID,CPBuilding,CreateTime,EffectiveDate,ExpirationDate,DirectTerm1,DirectTerm2,DirectTerm3 from pc_cpbuildingcov where CPBuilding in  (select ID from pc_cpbuilding where CPLocation in (select ID from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber= '"
					+ PolicyNumber + "'))))",
			"select PublicID,ID,CPBuildingCov,EffectiveDate,ExpirationDate from pc_cpcost where CPBuildingCov in (select ID from pc_cpbuildingcov where CPBuilding in  (select ID from pc_cpbuilding where CPLocation in (select ID from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber= '"
					+ PolicyNumber + "')))))",
			"select PublicID,ID,CPCost,EffDate,ExpDate, EffectiveDate,ExpirationDate from pc_cptransaction where CPCost in (select ID from pc_cpcost where CPBuildingCov in (select ID from pc_cpbuildingcov where CPBuilding in  (select ID from pc_cpbuilding where CPLocation in (select ID from pc_cplocation where CPLine in (select ID from pc_policyline where ID in (select ID from pc_policyperiod where PolicyNumber= '"
					+ PolicyNumber + "'))))))" };

	private String[] TABLE_NAME = { "pc_policy", "pc_policyperiod", "pc_policyline", "pc_cplocation", "pc_cpbuilding",
			"pc_cpbuildingcov", "pc_cpcost", "pc_cptransaction" };

	public JSONObject getDataFromDB(String processName, String policyNumber2) {
		JSONObject finalProcessObj = new JSONObject();
		Map tableObjectMap = new HashMap<>();

		try {
			for (int i = 0; i < SQL_QUERIES.length; i++) {
				JSONObject tableJsonObject = new JSONObject();
				JSONArray tableJsonArray = new JSONArray();
				ResultSet rs = getResultSet(StringUtils.replace(SQL_QUERIES[i], PolicyNumber, policyNumber2));
				if (rs != null) {
					ResultSetMetaData metaData = rs.getMetaData();
					int colCount = metaData.getColumnCount();
					// String tableName = metaData.getTableName(1);
					while (rs.next()) {
						JSONObject tableValJSON = new JSONObject();
						for (int j = 1; j <= colCount; j++) {
							String colName = metaData.getColumnName(j);
							Object colVal = rs.getObject(j);
							// tableName = metaData.getTableName(j);
							tableValJSON.put(colName, String.valueOf(colVal));
						}
						tableJsonArray.add(tableValJSON);
					}
					tableObjectMap.put(TABLE_NAME[i], tableJsonArray);
					// tableObjectMap.add(tableJsonObject);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finalProcessObj.putAll(tableObjectMap);
		JSONObject jsonFinalCover = new JSONObject();
		jsonFinalCover.put(processName, finalProcessObj);
		//System.out.println(finalProcessObj.toJSONString());

		return jsonFinalCover;
	}

	Connection conn = null;

	private ResultSet getResultSet(String query) {
		System.out.println(query);
		ResultSet rs = null;

		try {
			if (conn == null) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
				String db_url = "jdbc:sqlserver://localhost:2014;DatabaseName=PC_DT92_JP;User=sa;Password=Gw_123";
				conn = DriverManager.getConnection(db_url);
			}
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (Exception e) {

			e.printStackTrace();
		}
		// System.out.println(rs);
		return rs;
	}

}
