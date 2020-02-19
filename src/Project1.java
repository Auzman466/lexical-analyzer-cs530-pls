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

//    public static String readLex(int line, int index) {
//
//    }

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
                if(checkDigit){
                    // begin parsing digits in sequence
                    // add first digit of number to a string
                    analyzedToken = new StringBuilder();
                    for (int j = i; j < line.length(); j++){
                        // check for decimal; if decimal present, type is double.
                        boolean checkDigit1 = Character.isDigit(line.charAt(j));
                        if(line.charAt(j) == '.'){
                            analyzedToken.append(line.charAt(j));
                            analyzedType = "double constant";
                        }
                        // decimal not present, see if constant continues
                        else if(checkDigit1 && analyzedType.equals("double constant")){
                            analyzedToken.append(line.charAt(j));
                        }
                        else if(checkDigit1){
                            analyzedToken.append(line.charAt(j));
                            analyzedType = "int constant";
                        }
                        else{// constant has ended
                            System.out.println("Line" + lineCount + ": " + i + " " + " " + analyzedType + " " + analyzedToken.toString());
                            i = j; // move index i to index j to continue parsing line without retreading ground
                            break;
                        }


                    }

                }
                else if (checkAlpha){
                    
                }
            }

        }
    }
}
