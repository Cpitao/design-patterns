import jdk.swing.interop.SwingInterOpUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONParser {

    private String getFileContent(String filename) {
        StringBuilder content = new StringBuilder("");
        try {
            File inputFile = new File(filename);
            Scanner reader = new Scanner(inputFile);
            while (reader.hasNextLine()) {
                content.append(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public MyJSONObject parseFile(String filename) {
        if (!isFileJSON(filename)) return null;

        String content = getFileContent(filename);

        return parseContent(content);
    }

    private MyJSONObject parseContent(String content) {
        // get main object bracket indices
        int first = content.indexOf('{');
        int last = content.lastIndexOf('}');
        content = content.substring(first + 1, last); // content is now only the inside of main object

        ObjectBuilder objectBuilder = new ObjectBuilder();

        // cases to take care of:
        // > '[\{,\}"]' inside of string (between ")
        // > nested objects
        // ASSUME FIELD NAMES ARE BETWEEN " "
        while (!content.equals("")) {
            int opening = content.indexOf('"');
            int closing = content.indexOf('"', opening + 1);
            while (content.charAt(closing-1) == '\\')
                closing = content.indexOf('"', closing + 1);
            // ^ iterate over all instances of " in sth like "\"\"\""
            String id = content.substring(opening+1, closing);
            // get rid of the id part and ':' separator
            content = content.substring(content.indexOf(':') + 1).strip();

            // find out whether the value is going to be string, float, int or object
            // - first string begins at:
            int stringStart = content.length() + 1;
            int firstApstr = content.indexOf('"');
            if (firstApstr != -1) stringStart = firstApstr;
            // - first (possible) float begins at:
            int floatStart = content.length() + 1;
            Pattern pattern = Pattern.compile("[0-9]+\\.[0-9]*");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                floatStart = matcher.start();
            }
            // - first (possible) int begins at:
            int intStart = content.length() + 1;
            pattern = Pattern.compile("[0-9]+");
            matcher = pattern.matcher(content);
            if (matcher.find()) {
                intStart = matcher.start();
            }
            // - first (possible) object begins at:
            int objectStart = content.length() + 1;
            int firstBracket = content.indexOf('{');
            if (firstBracket != -1) objectStart = firstBracket;

            // now pick the object that begins first
            int minimum = Math.min(Math.min(Math.min(stringStart, floatStart), intStart), objectStart);
            content = content.substring(minimum);

            if (minimum == stringStart) {
                /* handle string */
                int open = content.indexOf('"');
                int close = content.indexOf('"', open + 1);
                while (content.charAt(close-1) == '\\')
                    close = content.indexOf('"', close + 1);
                String value = content.substring(open + 1, close);
                objectBuilder.append(id, value);
                content = content.substring(close + 1);
            }
            else if (minimum == floatStart) {
                /* handle float */
                Pattern p = Pattern.compile("[0-9]+\\.[0-9]*");
                Matcher m = p.matcher(content);
                if (m.find()) {
                    float value = Float.parseFloat(content.substring(m.start(), m.end()));
                    objectBuilder.append(id, value);
                    content = content.substring(m.end());
                }
            }
            else if (minimum == intStart) {
                /* handle int */
                Pattern p = Pattern.compile("[0-9]+");
                Matcher m = p.matcher(content);
                if (m.find()) {
                    int value = Integer.parseInt(content.substring(m.start(), m.end()));
                    objectBuilder.append(id, value);
                    content = content.substring(m.end());
                }
            }
            else if (minimum == objectStart) {
                /* handle object */
                String contentCopy = content;
                int removedLen = 0;
                int openedBrackets = 1;
                int closingBracketIndex = -1;
                int openingBracketIndex = 0;
                while (openedBrackets != 0) {
                    closingBracketIndex = contentCopy.indexOf('}', closingBracketIndex + 1);

                    int apIndex = contentCopy.indexOf('"');
                    while (apIndex != -1 && apIndex < closingBracketIndex) {
                        int start = contentCopy.indexOf('"');
                        int end = contentCopy.indexOf('"', start + 1);
                        while (contentCopy.charAt(end - 1) == '\\') {
                            end = contentCopy.indexOf('"', end + 1);
                        }
                        contentCopy = contentCopy.substring(0, start) + contentCopy.substring(end + 1);
                        removedLen += end - start + 1;
                        if (start < openingBracketIndex)
                        {
                            openingBracketIndex -= end - start + 1;
                            closingBracketIndex -= end - start + 1;
                        } else if (start < closingBracketIndex) {
                            closingBracketIndex -= end - start + 1;
                        }
                        apIndex = contentCopy.indexOf('"');
                    }

                    int newOpen = contentCopy.indexOf('{', openingBracketIndex+1);
                    if (newOpen != -1 && newOpen < closingBracketIndex) {
//                        openedBrackets++;
                        openingBracketIndex = newOpen;
                    } else {
                        openedBrackets--;
                    }
                }
                contentCopy = content.substring(0, closingBracketIndex + removedLen + 1);
                MyJSONObject insideObj = parseContent(contentCopy);

                objectBuilder.append(id, insideObj);
                content = content.substring(closingBracketIndex + removedLen + 1);
            }

            int firstComma = content.indexOf(',');
            if (firstComma != -1) {
                content = content.substring(firstComma + 1);
            }
            content = content.strip();
        }

        return objectBuilder.toJSONObject();
    }

    private boolean isFileJSON(String filename) {
        String content = getFileContent(filename);
        try {
            new JSONObject(content);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
