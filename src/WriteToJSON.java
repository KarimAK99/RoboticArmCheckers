import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteToJSON {
    public static void main(String[] args) throws Exception {
        JSONObject jo = new JSONObject();
        //put data to JSONObject
        jo.put("some", "data");

        // writing JSON to file
        PrintWriter pw = new PrintWriter("boardData.json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();

    }
}

