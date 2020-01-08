import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ReadFromJSON {
    public static void main(String[] args) throws Exception {

        Object obj = new JSONParser().parse(new FileReader("boardData.json"));
        JSONObject jsonObject = (JSONObject) obj;

        //System.out.println(jsonObject.get("some"));
        String solutions = (String) jsonObject.get("some");
        System.out.println(solutions);
        /*JSONArray solutions = (JSONArray) jsonObject.get("some");

       Iterator iterator = solutions.iterator();
        while(iterator.hasNext())

        {
            System.out.println(iterator.next());
        }*/
    }
}
