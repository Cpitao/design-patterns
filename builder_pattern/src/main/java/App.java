public class App {

    public static void main(String[] args) {
        // accepts only JSON with identificators between ""
        String filename = "test.json";
        JSONParser jsonParser = new JSONParser();
        MyJSONObject myJSONObject = jsonParser.parseFile(filename);
        System.out.println(myJSONObject);
    }
}
