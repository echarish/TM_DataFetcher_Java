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
public class AppBackUp {
	private static final String FILENAME = "C:\\Users\\hkumar\\Work\\Devlopment\\"
			+ "Workspaces\\EclipseOxygen\\SampleProject\\src\\main\\java\\SampleProjectGroup\\SampleProject\\"
			+ "datafile.txt";

	private static final String processName = "change2Start";

/*	public static void main(String[] args) {
		System.out.println("=========== Starting to Create JSON FILE ==========");
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			JSONObject masterObj = new JSONObject();
			JSONArray list = new JSONArray();
			boolean firstLine = true;

			String sCurrentLine;
			String[] headerArray = null;

			while ((sCurrentLine = br.readLine()) != null) {
				String[] splitValue = sCurrentLine.split("\\t");
				if (firstLine) {
					headerArray = splitValue;
					firstLine = false;
				} else {
					JSONObject obj = new JSONObject();
					for (int i = 0; i < splitValue.length; i++) {
						obj.put(headerArray[i], splitValue[i].trim());

					}
					list.add(obj);
				}

			}
			masterObj.put(processName, list);
			
			
			
			

			try (FileWriter file = new FileWriter(
					"C:\\\\Users\\\\hkumar\\\\Work\\\\Devlopment\\\\Workspaces\\\\EclipseOxygen\\\\SampleProject\\\\src\\\\main\\\\java\\\\SampleProjectGroup\\\\SampleProject\\Result.json")) {

				file.write(masterObj.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(masterObj.toJSONString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/
}
