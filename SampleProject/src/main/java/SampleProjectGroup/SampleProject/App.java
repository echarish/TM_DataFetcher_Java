package SampleProjectGroup.SampleProject;

import java.io.BufferedReader;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

	private static final String BasePolicyData = "BasePolicyData";
	private static final String Change1Start = "Change1Start";
	private static final String Change1Bind = "Change1Bind";
	private static final String Change2Start = "Change2Start";
	private static final String Change2Bind = "Change2Bind";

	public static void main(String[] args) {
		System.out.println("=========== Starting to Create JSON FILE ==========");
		BufferedReader br = null;
		FileReader fr = null;

		try {
			String policyNumber = args[0];
			String processName = getProcessName(args);
			System.out.println(" Getting data for  " + processName);
			DataAccessLayer dataAccessLayer = new DataAccessLayer();
			JSONObject masterObj = dataAccessLayer.getDataFromDB(processName,policyNumber);

			System.out.println();
			System.out.println(masterObj.toJSONString());
			String outputFile = "C:\\IntelliJ_WS\\TMNF_DataFlow\\src\\data\\" + processName + ".json";

			try (FileWriter file = new FileWriter(outputFile)) {

				file.write(masterObj.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println();
			System.out.println(outputFile);
			System.out.println("=========== Starting to Create JSON FILE ==========");
			// System.out.println(masterObj.toJSONString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getProcessName(String[] args) {
		String processName = BasePolicyData;
		String inputKey = (args != null && args.length >= 1) ? args[1] : "1";
		switch (inputKey) {
		case "1":
			processName = BasePolicyData;
			break;
		case "2":
			processName = Change1Start;
			break;
		case "3":
			processName = Change1Bind;
			break;
		case "4":
			processName = Change2Start;
			break;
		case "5":
			processName = Change2Bind;
			break;

		default:
			break;
		}
		return processName;
	}
}
