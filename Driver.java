import java.util.*;
import java.io.*;

public class Driver {
  public static void main(String args[]) {
    ArrayList<String> instructionsList = new ArrayList<>(); //Holds instructions inputted from the input file(inputLine variable)
    LinkedHashMap<String, Integer> symbolTable = new LinkedHashMap<>(); //holds labels and their addresses

    String
    filename,
    inputLine, //holds string representation of every line of instructions in a while loop
    currentInstruction, //holds the current instruction as string in the processing stage
    Empty="";

    String []
    instructionArray; //splits the instruction line to several indexs holding 

    int
    address;

    boolean
    fileFound;

    //initiliazing the InstructionSet class for processing which contains
    //Instructions intializing, it contains Instruction types and associated data attributes 
    InstructionSet
    mipsInstructionSet; 

    //used for intializing the types of instructions
    Instruction
    mipsInstruction;

    Registers
    registers;

    FileReader
    fileReader;

    BufferedReader
    bufferedReader;

    address = Integer.parseInt("400000", 16);
    fileFound = false;
    mipsInstructionSet = new InstructionSet();
    registers = new Registers();

    mipsInstructionSet.init();
    Registers.init();

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
		  
          label = "";

          //in case the instruction line containing a comment 
          if (inputLine.indexOf('#') != -1) {
            inputLine = inputLine.substring(0, inputLine.indexOf('#'));
          }//if
          else {
            inputLine = inputLine + "\t\t";
          }//else

          //in case the instruction line is empty
          if (inputLine.trim().length() == 0) {
            continue;
          }//if

          //initializing labels, if they present and associating them with an address
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
  		  
  		if (instructionsList.get(ndx).indexOf('.') != -1) {
  			continue;
      }//if
      
      //currentInstruction holds the instruction line of specific index from the for loop
      //for example in the test case, instructionsList.get(0)=="slti $at,$s5,$5"
  		currentInstruction = instructionsList.get(ndx);

      mipsInstruction = new Instruction(address);


      //Elmfrod lama yshof en ely b3d instruction "Exit: " byb2a null 3ashan fadya, y5rog bara el for loop
      //we myro7sh lel switch case fel asas, bas lesa fe exception 3and ndx 5
      //ely hwa 3and instruction exit:
      if (currentInstruction.indexOf(":") != -1) {
        if(currentInstruction.substring(
          currentInstruction.indexOf(":") , currentInstruction.length())==null ){

        continue;
      }
      else{
        currentInstruction = currentInstruction.substring(
        currentInstruction.indexOf(":") + 1, currentInstruction.length());
      }
    }//if
  
      currentInstruction = currentInstruction.replaceAll(",", "");
  		currentInstruction = currentInstruction.replaceAll("\\s+", " ");
  		  //incase of arrays and offsets, did not test it yet
      if (currentInstruction.indexOf('(') != -1) {
  			String
        temp;

        currentInstruction = currentInstruction.replaceAll("\\(", " ");
        currentInstruction = currentInstruction.replaceAll("\\)", " ");

        instructionArray = currentInstruction.split(" ");

        temp = instructionArray[4];
        instructionArray[4] = instructionArray[3];
        instructionArray[3] = temp;
  		}//if
      else {
        instructionArray = currentInstruction.split(" ");
      }//else

      //leha 3elaka bel case beta3et Exit: brdo
      if(currentInstruction.indexOf(":") !=-1 ){
        String[] S = currentInstruction.split(" ");
        if(S.toString().contains("Exit"))
        {
          continue;
        }
      }

      //ybd2 ya5od el instruction men instructionArray, for example htb2a dlw2ty "slti"
      try{
    switch (InstructionSet.getType(instructionArray[0])) {
        case "I": {
          if (symbolTable.containsKey(instructionArray[3])) {
            instructionArray[3] = Integer.toString(

            symbolTable.get(instructionArray[3]));
          }//ife

          mipsInstruction.setIType(instructionArray[0], instructionArray[2], 
          instructionArray[1], instructionArray[3]);

          break;
        }//case
        case "J": {               
          if(symbolTable.containsKey(instructionArray[1])){
            instructionArray[1] = Integer.toString(

            symbolTable.get(instructionArray[1]));
          }//if
         
          mipsInstruction.setJType(instructionArray[0], 
          Integer.parseInt(instructionArray[1]));

          break;
        }//case
        case "R": {
          if (instructionArray[0].indexOf("jr") != -1) {
            mipsInstruction.setRType(instructionArray[0], instructionArray[1],
              "", "");
            
            currentInstruction = currentInstruction + "\t";
          }//if
          else {
            mipsInstruction.setRType(instructionArray[0], instructionArray[2],
              instructionArray[3], instructionArray[1]);
              //Execution of the instruction
              InstructionSet.exceuteR(mipsInstruction,ndx);
          }//else

          break;
        }//case
        default: {
          break;
        }//default
      }//switch
      }catch(ArrayIndexOutOfBoundsException e){
        System.out.println("The exception is in ndx  "+ndx);
        }
  		  
  		instructionsList.set(ndx, instructionsList.get(ndx) +
        mipsInstruction.toString());
  		
      address = address + 4;
  	}//for
	
    System.out.println("Symbol Table:\n\nLabel\t\tAddress (in hex)");

    for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
      System.out.printf("%s\t\t\t\t%08x\n", entry.getKey(), entry.getValue());
    }//for

    System.out.println("\nMIPS Code\t\t\t\t\tAddress\t\tMachine Lang.");
    for (String mipsInstructionData : instructionsList) {
      System.out.printf("%s\n", mipsInstructionData);
    }//for
    
  }//main
}//Driver
