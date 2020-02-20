import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project1 {
    public static Scanner readFile(Scanner scan) throws FileNotFoundException {
        Scanner file;
        System.out.print("Input file name: ");
        String fName = scan.next();
        try {
            file = new Scanner(new File(fName));
            System.out.println("File found.");
        } catch (FileNotFoundException fileEx) {
            throw new FileNotFoundException(("File not found, exiting."));
        }
        return file;
    }

    public static void print(int line, int index, String type, StringBuilder token) {
        index = index + 1;
        System.out.println("Line" + line + ": " + index + " " + " " + type + ": " + token.toString());
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        Scanner file = readFile(scan);
        int lineCount = 0;
        while (file.hasNextLine()) {
            lineCount++;
            String analyzedType = "";
            StringBuilder analyzedToken = new StringBuilder();
            String line = file.nextLine();
            // loop through line
            for (int i = 0; i < line.length(); i++) {
                // check if character is a digit
                boolean checkDigit = Character.isDigit(line.charAt(i));
                boolean checkAlpha = Character.isAlphabetic(line.charAt(i));
                if (line.charAt(i) == ' ') {
                    continue;
                } else if (checkDigit) {
                    // begin parsing digits in sequence
                    analyzedToken.delete(0, analyzedToken.length());
                    for (int j = i; j < line.length(); j++) {
                        // check for decimal; if decimal present, type is double.
                        // add first digit of number to a string
                        boolean checkDigit1 = Character.isDigit(line.charAt(j));
                        if (line.charAt(j) == '.') {
                            analyzedToken.append(line.charAt(j));
                            analyzedType = "double constant";
                        }
                        // decimal not present, see if constant continues
                        else if (checkDigit1 && analyzedType.equals("double constant")) {
                            analyzedToken.append(line.charAt(j));
                        } else if (checkDigit1) {
                            analyzedToken.append(line.charAt(j));
                            analyzedType = "int constant";
                        } else {// constant has ended
                            print(lineCount, i, analyzedType, analyzedToken);
                            //System.out.println("Line" + lineCount + ": " + i+1 + " " + " " + analyzedType + ": " + analyzedToken.toString());
                            i = j - 1; // move index i to index j to continue parsing line without retreading ground
                            break;
                        }


                    }

                } else if (checkAlpha) {
                    boolean keyword = false;
                    //check first if the following characters spell a keyword
                    analyzedToken.delete(0, analyzedToken.length());
                    if (i + 4 < line.length()) {
                        if (line.substring(i, i + 4).equals("int ")) {
                            analyzedType = "keyword";
                            analyzedToken.append("int");
                            print(lineCount, i, analyzedType, analyzedToken);
                            i = i + 3;
                            keyword = true;
                            //System.out.println("Line" + lineCount + ": " + i+1 + " " + " " + analyzedType + ": " + analyzedToken.toString());
                        }
                    }
                    if (i + 7 < line.length()) {
                        if (line.substring(i, i + 7).equals("double ")) {
                            analyzedType = "keyword";
                            analyzedToken.append("double");
                            print(lineCount, i, analyzedType, analyzedToken);
                            i = i + 6;
                            keyword = true;
                        } else if (line.substring(i, i + 7).equals("String ")) {
                            analyzedType = "keyword";
                            analyzedToken.append("string");
                            print(lineCount, i, analyzedType, analyzedToken);
                            i = i + 6;
                            keyword = true;
                        }
                    }
                    if (!keyword) {
                        for (int j = i; j < line.length(); j++) {
                            boolean checkAlpha1 = Character.isAlphabetic(line.charAt(j));
                            if (checkAlpha1) {
                                analyzedToken.append(line.charAt(j));
                            } else {
                                analyzedType = "identifier";
                                print(lineCount, i, analyzedType, analyzedToken);
                                i = j-1;
                                break;
                            }
                        }
                    }
                } else if (line.charAt(i) == '=' || line.charAt(i) == '(' || line.charAt(i) == ')' || line.charAt(i) == '+' || line.charAt(i) == '-' || line.charAt(i) == '*' || line.charAt(i) == '/' || line.charAt(i) == ',' || line.charAt(i) == ';') {
                    analyzedType = "operator";
                    analyzedToken.delete(0, analyzedToken.length());
                    analyzedToken.append(line.charAt(i));
                    print(lineCount, i, analyzedType, analyzedToken);
                } else if (line.charAt(i) == '\"'){
                    analyzedToken.delete(0, analyzedToken.length());
                    analyzedType = "string constant";
                    analyzedToken.append(line.charAt(i));
                    for (int j = i+1; j < line.length(); j++){
                        if (line.charAt(j) != '\"'){
                            analyzedToken.append(line.charAt(j));
                        } else {
                            analyzedToken.append(line.charAt(j));
                            print(lineCount, i, analyzedType, analyzedToken);
                            i = j;
                            break;
                        }
                    }
                } else {
                    analyzedToken.delete(0, analyzedToken.length());
                    analyzedToken.append(line.charAt(i)).append(" not recognized");
                    analyzedType = "error";
                    print(lineCount, i, analyzedType, analyzedToken);
                }
            }

        }
    }
}
