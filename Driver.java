import java.util.*;
import java.io.*;

public class Driver {
  public static void main(String args[]) {
    ArrayList<String> instructionsList = new ArrayList<>();
    LinkedHashMap<String, Integer> symbolTable = new LinkedHashMap<>();

    String
    filename,
    inputLine;

    InstructionSet
    mipsInstructionSet;

    Registers
    registers;

    int
    address;

    FileReader
    fileReader;

    BufferedReader
    bufferedReader;

    boolean
    fileFound;

    address = Integer.parseInt("400000", 16);
    fileFound = false;
    mipsInstructionSet = new InstructionSet();
    registers = new Registers();

    mipsInstructionSet.init();
    registers.init();

    //Read in file

    while (!fileFound) {
      System.out.print("Enter a file containing valid MIPS code> ");
      filename = System.console().readLine();

      try {
        fileReader = new FileReader(filename);

        bufferedReader = new BufferedReader(fileReader);

        inputLine = bufferedReader.readLine();

        instructionsList.add(inputLine);

        while ((inputLine = bufferedReader.readLine()) != null) {

          String
          label;

          String []
          instructionArray;
		  
          label = "";

          if (inputLine.indexOf('#') != -1) {
            inputLine = inputLine.substring(0, inputLine.indexOf('#'));
          }//if
          else {
            inputLine = inputLine + "\t\t";
          }//else
		  
		  if (inputLine.trim().length() == 0) {
			  continue;
		  }//if

          if (inputLine.indexOf(":") != -1) {
            label = inputLine.substring(0, inputLine.indexOf(":"));
            symbolTable.put(label, address);
          }//if

			instructionsList.add(inputLine);
			address = address + 4;
        }//while

        fileFound = !fileFound;

        bufferedReader.close();
      }//try
      catch (FileNotFoundException ex) {
        System.out.printf("File %s was not found.\n", filename);
      }//catch
      catch (IOException ex) {
        System.out.printf("Error when reading %s.\n", filename);
      }//catch
    }//while
	
	address = Integer.parseInt("400000", 16);

	for (int ndx = 0; ndx < instructionsList.size(); ndx++) {
        Instruction
        mipsInstruction;
		  
        String []
        instructionArray;
		
		String
		currentInstruction;
		  
		if (instructionsList.get(ndx).indexOf('.') != -1) {
			continue;
		}//if
		
		currentInstruction = instructionsList.get(ndx);

        mipsInstruction = new Instruction(address);


        if (currentInstruction.indexOf(":") != -1) {
            currentInstruction = currentInstruction.substring(currentInstruction.indexOf(":") + 1, currentInstruction.length());
        }//if
		
		
        currentInstruction = currentInstruction.replaceAll(",", "");
		currentInstruction = currentInstruction.replaceAll("\\s+", " ");
		  
        if (currentInstruction.indexOf('(') != -1) {
			String
            temp;

            currentInstruction = currentInstruction.replaceAll("\\(", " ");
            currentInstruction = currentInstruction.replaceAll("\\)", " ");

            instructionArray = currentInstruction.split(" ");

            temp = instructionArray[4];
            instructionArray[4] = instructionArray[3];
            instructionArray[3] = temp;
		}
        else {
            instructionArray = currentInstruction.split(" ");
        }

          switch (mipsInstructionSet.getType(instructionArray[1])) {
            case "I": {
              if (symbolTable.containsKey(instructionArray[4])) {
                instructionArray[4] = Integer.toString(symbolTable.get(instructionArray[4]));
              }

              mipsInstruction.setIType(instructionArray[1], instructionArray[3], instructionArray[2], instructionArray[4]);

              break;
            }//case
            case "J": {               
              if(symbolTable.containsKey(instructionArray[2])){
                instructionArray[2] = Integer.toString(symbolTable.get(instructionArray[2]));
              }
             
              mipsInstruction.setJType(instructionArray[1], Integer.parseInt(instructionArray[2]));

              break;
            }//case
            case "R": {
              if (instructionArray[1].indexOf("jr") != -1) {
                mipsInstruction.setRType(instructionArray[1], instructionArray[2], "", "");
                currentInstruction = currentInstruction + "\t";
              }
              else {
                mipsInstruction.setRType(instructionArray[1], instructionArray[3], instructionArray[4], instructionArray[2]);
              }

              break;
            }//case
            default: {

            }//default
          }//switch
		  
		  instructionsList.set(ndx, instructionsList.get(ndx) + mipsInstruction.toString());
		
        address = address + 4;
	}//for
	
    System.out.println("Symbol Table:\n\nLabel\t\tAddress (in hex)");

    for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
      System.out.printf("%s\t\t%08x\n", entry.getKey(), entry.getValue());
    }//for

	System.out.println("\nMIPS Code\t\t\t\t\tAddress\t\tMachine Lang.");
    for (String mipsInstructionData : instructionsList) {
      System.out.printf("%s\n", mipsInstructionData);
    }//for
    //First Pass Logic
    //
    //Second Pass Logic
    //  
    //Output
  }//main
}//Driver
