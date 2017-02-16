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

        instructionsList.add("MIPS Code\t\t\t\t\tAddress\t\tMachine Lang");
        instructionsList.add(inputLine);

        while ((inputLine = bufferedReader.readLine()) != null) {
          Instruction
          mipsInstruction;

          String
          label,
          nonLabeledInstruction,
          instructionType;

          String []
          instructionArray;

          mipsInstruction = new Instruction(address);
          label = "";
          nonLabeledInstruction = "";
          instructionType = "";

          if (inputLine.indexOf('#') != -1) {
            inputLine = inputLine.substring(0, inputLine.indexOf('#'));
            nonLabeledInstruction = inputLine;
          }//if

          if (inputLine.indexOf(":") != -1) {
            label = inputLine.substring(0, inputLine.indexOf(":"));
            inputLine = inputLine.substring(inputLine.indexOf(":") + 1, inputLine.length() - 1);
            symbolTable.put(label, address);
          }//if

          inputLine = inputLine.replaceAll("\\s+", " ");
          inputLine = inputLine.replaceAll(",", "");

          instructionArray = inputLine.split(" ");
          instructionType = mipsInstructionSet.getType(instructionArray[1]);

          switch (instructionType) {
            case "I": {

              break;
            }//case
            case "J": {
              mipsInstruction.setJType(instructionArray[1], symbolTable.get(instructionArray[2]));

              break;
            }//case
            case "R": {
              if (instructionArray[1] == "jr") {
                mipsInstruction.setRType(instructionArray[1], instructionArray[2], "", "");
              }
              else {
                mipsInstruction.setRType(instructionArray[1], instructionArray[3], instructionArray[4], instructionArray[2]);
              }

              break;
            }//case
            default: {

            }//default
          }//switch

          nonLabeledInstruction = nonLabeledInstruction + mipsInstruction.toString();

          instructionsList.add(nonLabeledInstruction);

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

    System.out.println("Symbol Table:\n\nLabel\t\tAddress (in hex)");

    for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
      System.out.printf("%s\t\t%08x\n", entry.getKey(), entry.getValue());
    }//for

    System.out.println('\0');

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
