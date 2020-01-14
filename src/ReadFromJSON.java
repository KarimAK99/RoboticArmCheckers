import java.io.*;
import java.util.Iterator;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ReadFromJSON {
    static String data;
    public static void main(String[] args) throws Exception {

        /**
         * specify src for the file,
         * get file and read it then makes it into 3 different arrays representing board state, i.e. player, xpos,ypos
         * for each tiles on the board
         */
        Object obj = new JSONParser().parse(new FileReader("src/boardData.json"));
        JSONObject jsonObject = (JSONObject) obj;

        JSONArray solutions = (JSONArray) jsonObject.get("board");
        Iterator iterator = solutions.iterator();
        while (iterator.hasNext()) {
            Object n = iterator.next();
            String test = n.toString();
            //read through JSONarray
            if (test.contains("board")){
                String ans = test.substring(test.indexOf("["),test.indexOf("]}"));
                data = ans.substring(1);
                System.out.println(data);
            }
        }
        //remove useless char
        String[] items = data.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
        int size = items.length;
        int[] arr = new int[size];
        // creates array from data string from the file
        for(int i = 0; i < items.length;i++){
            arr[i] = Integer.parseInt(items[i]);
        }
        //3 arrays with all the data, color = player, xpos= x- position, ypos = y- position
        int[] color = new int[size/3];
        int[]xPos = new int[size/3];
        int[]yPos = new int[size/3];
        int j =0; int x =1; int y = 2;
        for(int i = 0; i< color.length;i++){
                color[i]= arr[j];
                xPos[i]=arr[x];
                yPos[i] = arr[y];
                j+=3; x+=3; y+=3;
            }
    }
}
