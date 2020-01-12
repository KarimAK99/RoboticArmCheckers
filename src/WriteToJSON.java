import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class WriteToJSON {
    public static void main(String[] args) throws Exception {
        JSONObject jo = new JSONObject();
        //put data to JSONObject
        jo.put("Board", "data");
        jo.put("firstName", "John");
        jo.put("lastName", "Doe");
        //make data into an array, use that!! don't make it loose data
        JSONArray ja = new JSONArray();
        ja.put(jo);

        JSONObject mainObj = new JSONObject();
        mainObj.put("employees", ja);

        // writing JSON to file, specify file name with src/ to write it in the src folder
        PrintWriter pw = new PrintWriter("src/boardData.json");
        pw.write(mainObj.toString());

        pw.flush();
        pw.close();

    }
}

