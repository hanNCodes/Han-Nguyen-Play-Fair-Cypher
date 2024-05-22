import java.io.*; 
import java.util.*;

public class operations {

  //variables
  String[][] alphabetGrid = {{"D", "A", "V", "I", "O"},
                             {"Y", "N", "E", "R", "B"},
                             {"C", "F", "G", "H", "K"},
                             {"L", "M", "P", "Q", "S"},
                             {"T", "U", "W", "X", "Z"}};
  
  ArrayList<String> splittedInput = new ArrayList<String>(); 

  public class rowColumn {
    int row;
    int column;
  };

  
  //
  //constructor
    public operations() {

      String Updatedmessage;
      
      //prompts and obtains input
      Console cnsl = System.console(); 
          if (cnsl == null) { 
              System.out.println( 
                  "No console available"); 
              return; 
          } 

      while(true) {
        String eOrD = "";
          //prompts user for en or de
          while(!eOrD.equals("e") && !eOrD.equals("d")) {
            eOrD = cnsl.readLine("Would you like to encrypt(e) or decrypt(d)? Press enter to exit: ");
            if(eOrD.equals("")) {
              return;
            }
          }

        //prompts for message
        String userInput = cnsl.readLine("Enter message: "); 
          String userInputOrg = userInput;
      
          //alters input to be analyzed
          userInput = userInputFiltered(userInput);
          
          //splits input into list
          splittedInput = splitIntoPairs(userInput);
    
        //encrypt or decrypt, with print
        System.out.println();
        if(eOrD.equals("e")) {
          Updatedmessage = encrypt(splittedInput);
          System.out.println("encrypt(\"" + userInputOrg + "\") -> " + Updatedmessage + "\""); 
        } else {
          Updatedmessage = decrypt(splittedInput);
          System.out.println("decrypt(" + userInputOrg + ") -> " + Updatedmessage + "\"");
        }

        System.out.println("\n");
      }
    }

  
  //
  //encrypt 
    String encrypt(ArrayList<String> input) {
      String output = "";
      for(int i = 0; i < input.size(); i++) {
        //assigns first and second
        String first = getLetter(input.get(i), 0);
          rowColumn firstRC = getRowAndColumn(first);
        String second = getLetter(input.get(i), 1);
          rowColumn secondRC = getRowAndColumn(second);
        
        //checks conditions of letters
          String[] newValues = new String[2]; 
        
          //if same, update
          if(first.equals(second)) {
            second = "X";
            secondRC = getRowAndColumn(second);
          } 
        
          //same column
          if (firstRC.column == secondRC.column) {
              newValues = encryptColumnCase(firstRC, secondRC);

          //same row
          } else if (firstRC.row == secondRC.row) {
              newValues = encryptRowCase(firstRC, secondRC);

          //rectangle case
          } else {
              newValues = rectangleCase(firstRC, secondRC);
          }

          //updates
            first = newValues[0];
            second = newValues[1];
        
        output += first + second;
      }
      return output;
    }


  //
  //decrypt
  String decrypt(ArrayList<String> input) {
    String output = "";
    for(int i = 0; i < input.size(); i++) {
      //assigns first and second
      String first = getLetter(input.get(i), 0);
        rowColumn firstRC = getRowAndColumn(first);
      String second = getLetter(input.get(i), 1);
        rowColumn secondRC = getRowAndColumn(second);

      //checks conditions of letters
        String[] newValues = new String[2]; 

        //same column
        if (firstRC.column == secondRC.column) {
            newValues = decryptColumnCase(firstRC, secondRC);

        //same row
        } else if (firstRC.row == secondRC.row) {
            newValues = decryptRowCase(firstRC, secondRC);

        //rectangle case
        } else {
            newValues = rectangleCase(firstRC, secondRC);
        }

        //updates
          first = newValues[0];
          second = newValues[1];

      output += first + second;
      }
      return output;
  }

  
  //
  //rectangle case
  String[] rectangleCase(rowColumn first, rowColumn second) {
    String[] output = new String[2];
      output[0] = alphabetGrid[first.row][second.column];
      output[1] = alphabetGrid[second.row][first.column];
    return output;
  }


  //  
  //row case - encrypt
  String[] encryptRowCase(rowColumn first, rowColumn second) {
    String[] output = new String[2];

      //checks first
      if(first.column == alphabetGrid[0].length-1) {
        output[0] = alphabetGrid[first.row][0];
      } else {
        output[0] = alphabetGrid[first.row][first.column + 1];
      }
  
      //checks second
      if(second.column == alphabetGrid[0].length-1) {
        output[1] = alphabetGrid[second.row][0];
      } else {
        output[1] = alphabetGrid[second.row][second.column + 1]; 
      }
    return output;
  }

  
  //
  //row case - decrypt
  String[] decryptRowCase(rowColumn first, rowColumn second) {
    String[] output = new String[2];

      //checks first
      if(first.column == 0) {
        output[0] = alphabetGrid[first.row][alphabetGrid[0].length-1];
      } else {
        output[0] = alphabetGrid[first.row][first.column - 1];
      }
    
      //checks second
      if(second.column == 0) {
        output[1] = alphabetGrid[second.row][alphabetGrid[0].length-1];
      } else {
        output[1] = alphabetGrid[second.row][second.column - 1];
      }
    return output;
  }

  
  //
  //column case - encrypt
  String[] encryptColumnCase(rowColumn first, rowColumn second) {
    String[] output = new String[2];

      //checks first
      if(first.row == alphabetGrid.length-1)
      {
        output[0] = alphabetGrid[0][first.column];
      } else {
        output[0] = alphabetGrid[first.row + 1][first.column];
      }
    
      //checks second
      if(second.row == alphabetGrid.length-1) {
        output[1] = alphabetGrid[0][second.column];
      } else {
        output[1] = alphabetGrid[second.row + 1][second.column];
      }
    return output;
  }


  //
  //column case - decrypt
  String[] decryptColumnCase(rowColumn first, rowColumn second) {
    String[] output = new String[2];

      //checks first
      if(first.row == 0) {
        output[0] = alphabetGrid[alphabetGrid.length-1][first.column];
      } else {
        output[0] = alphabetGrid[first.row - 1][first.column];
      }

      //checks second
      if(second.row == 0) {
        output[1] = alphabetGrid[alphabetGrid.length-1][second.column];
      } else {
        output[1] = alphabetGrid[second.row - 1][second.column];
      }
    return output;
  }

  
  //
  //same letter
  rowColumn sameLetter() {
    rowColumn output = new rowColumn();
    output.row = 4;
    output.column = 3;
    return output;
  }

  
  //
  //identifies the row and column number
    rowColumn getRowAndColumn(String letter) {
      rowColumn letterPosition = new rowColumn();
      
      //loops through alphabet 2d list
      for(int i = 0; i < alphabetGrid.length; i++) {
        for(int o = 0; o < alphabetGrid[i].length; o++)
          {
            
            //if letter found
            if(alphabetGrid[i][o].equals(letter))
            {
              letterPosition.row = i;
              letterPosition.column = o;
              break;
            }
          }
      }
      
      return letterPosition; 
    }

  
  //
  //alters the input to be analyzed
    String userInputFiltered(String paramInput) {
      String input = "";
      
      //removes non-letter characters
      for(int i = 0; i < paramInput.length(); i++) {
        if(Character.isLetter(paramInput.charAt(i))) {
          input += paramInput.charAt(i);
        }
      }
      
      //adds 'X' if length is odd
      if(input.length()%2 != 0) {
        input += "X";
      }
      
      //to uppercase
      input = input.toUpperCase();
      
      //changes all Js to I
      input = input.replaceAll("J", "I");
      return input;
    }

  
  //
  //split string into pairs
    ArrayList<String> splitIntoPairs(String input) {
      ArrayList<String> temp = new ArrayList<String>();
      for(int i = 0; i < input.length(); i=i+2) {
        String stringTemp = input.substring(i, i+2);
        temp.add(stringTemp);
      }

      return temp;
    }

  
  //
  //gets letter at x of string
  String getLetter(String input, int x) {
    String temp = "";
    if(x >= input.length()) {
      return "";
    } else {
      temp = String.valueOf(input.charAt(x));
    }
    return temp;
  }

  
}
