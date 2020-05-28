import java.util.*;
import java.io.*;

public class Driver {
  public static void main(String args[]) {
    ArrayList<String> instructionsList = new ArrayList<>(); //Holds instructions inputted from the input file(inputLine variable)
    LinkedHashMap<String, Integer> symbolTable = new LinkedHashMap<>(); //holds labels and their addresses
    LinkedHashMap<String, Integer> labelMap= new LinkedHashMap<>();
    LinkedHashMap<Integer, String> addressMachine = new LinkedHashMap<>();

    String
    filename,
    inputLine, //holds string representation of every line of instructions in a while loop
    currentInstruction,
    //branchInstruction, //holds the current instruction as string in the processing stage
    labelBranch=""; //branch equal temp instruction

    String []
    instructionArray;
    //BranchArray; //splits the instruction line to several indexs holding 

    Integer
    address,
    ndx,
    labelNdx,
    labelLine=1;
    //labelAddress;

    boolean
    //flag=false,
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
    AddressMap
    addressMap;
    
    address = Integer.parseInt("400000", 16);
    fileFound = false;
    mipsInstructionSet = new InstructionSet();
    registers = new Registers();

    mipsInstructionSet.init();
    Registers.init();

int h=8;
int w=70;

  for(int j=1; j<=h; j++)
  {  

    for(int i=1; i<=w; i++)
    {
      if(j ==1 || j==h || i==1 || i==w)  
      {
        System.out.print("*");
      }

      else
      {
           System.out.print(" ");
      }
      if(i==2 && j==2)
      {
          System.out.print("Even though I walk through the darkest valley");
          i=i+45;
      }
      if(i==2 && j==3)
      {
          System.out.print("I fear nobody, for who you are with me");
            i=i+38;
          }
         
      
      if(i==2&&j==4)
      {
          System.out.print("This project was made by Shehab Adel, ");
          i=i+38;
      }
      if(i==2&&j==5)
      {
          System.out.print("Abdelrahman Shemis, Daniel Tarek, Omar Ossman, Ziad Yakan.");
          i=i+58;
      }
      if(i==2&&j==6)
      {
          System.out.print("Computer Architecture & Organization Project - Spring 2020");
          i=i+58;
      }

    }

     System.out.println();
  } 
 

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
            labelMap.put(label, labelLine);
          }//if
          
          instructionsList.add(inputLine);
          address = address + 4;

          //addressMachine.put(address,"x")
          labelLine++;
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
outerLoop:
  	for ( ndx = 0; ndx < instructionsList.size(); ndx++) {
      
      //setting up the address based on the index of the instruction
      address = Integer.parseInt("400000", 16) + (4*ndx);

  		if (instructionsList.get(ndx).indexOf('.') != -1) {
  			continue;
      }//if
      //When find the exit label, it breaks to avoid arrayexception 
      if(instructionsList.get(ndx).contains("Exit:")){
        break;
      }
      //currentInstruction holds the instruction line of specific index from the for loop
      //for example in the test case, instructionsList.get(0)=="slti $at,$s5,$5"
  		currentInstruction = instructionsList.get(ndx);

      mipsInstruction = new Instruction(address);


      //Elmfrod lama yshof en ely b3d instruction "Exit: " byb2a null 3ashan fadya, y5rog bara el for loop
      //we myro7sh lel switch case fel asas, bas lesa fe exception 3and ndx 5
      //ely hwa 3and instruction exit:
      if (currentInstruction.indexOf(":") != -1) {
        if(currentInstruction.substring(
          currentInstruction.indexOf(":") , currentInstruction.length()).equals(null) ){

        continue;
      }
      else{
        currentInstruction = currentInstruction.substring(
        currentInstruction.indexOf(":") + 2, currentInstruction.length());
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

outerSwitch: switch (InstructionSet.getType(instructionArray[0])) {
        case "I": {
          
          if(instructionArray[0].indexOf("beq")!=-1){
            
            Integer offset = ( labelMap.get(instructionArray[3]) - ndx - 1 );
            mipsInstruction.setIType(instructionArray[0], instructionArray[2], instructionArray[1], String.valueOf(offset));
            InstructionSet.exceuteI(mipsInstruction, ndx);
            Integer rsValueBeq= AddressMap.get(Registers.get(mipsInstruction.getSource(mipsInstruction)));
            Integer rtValueBeq= AddressMap.get(Registers.get(mipsInstruction.getTarget(mipsInstruction)));
            if (rsValueBeq.equals(rtValueBeq)){
              ndx=labelMap.get(instructionArray[3])-1;
              break;
            }else{

            System.out.println("\nValue inside rs is not equalt value inside rt, moving to the next instruction");

             break; 
            }
            //break;

          }
           
          /*if (symbolTable.containsKey(instructionArray[3])) {
            instructionArray[3] = Integer.toString(symbolTable.get(instructionArray[3])); 
          }*/

        //if
          else if(instructionArray[0].indexOf("lui")!=-1){
            mipsInstruction.setIType(instructionArray[0], instructionArray[2], 
          instructionArray[1], instructionArray[3]);
          InstructionSet.exceuteI(mipsInstruction, ndx);
          break;
          }
          /*if(currentInstruction.indexOf(":")!=-1)
          {
            mipsInstruction.setIType(instructionArray[1], instructionArray[3], instructionArray[2], instructionArray[4]);
            InstructionSet.exceuteI(mipsInstruction, ndx);
          }*/
          
          else {
          mipsInstruction.setIType(instructionArray[0], instructionArray[2], 
          instructionArray[1], instructionArray[3]);

          InstructionSet.exceuteI(mipsInstruction, ndx);
          break;
          }
          
        }//case
        case "J": {      
          Integer offsetJ = ( labelMap.get(instructionArray[1]) - ndx - 1 );

          mipsInstruction.setJType(instructionArray[0], offsetJ);
          System.out.println("**************Execution**************"+ "\n"+"Line Number:"+(ndx+1) + " The next line is "+ (labelMap.get(instructionArray[1])+1)+"\n"+"*******************************\n" );
          ndx=labelMap.get(instructionArray[1])-1;
          break outerSwitch;
          /*if(symbolTable.containsKey(instructionArray[1])){
            instructionArray[1] = Integer.toString(symbolTable.get(instructionArray[1]));*/ //hwa byro7 lel address, 23mel 7aga tro7 lel address afdl


/*            if(currentInstruction.indexOf("j")!=-1)
            {
              labelBranch=instructionArray[3]+":";
              instructionsList.set(ndx, instructionsList.get(ndx) +
              mipsInstruction.toString());
                  labelNdx=ndx;
    JumpLoop: for(int i=labelNdx+1; i<instructionsList.size(); i++){
                  if(instructionsList.get(i).contains(labelBranch)){
                    ndx=i;
                    break outerLoop;
                    /*
                    branchInstruction=instructionsList.get(i);
                    branchInstruction = branchInstruction.substring(branchInstruction.indexOf(":") + 1, branchInstruction.length());
                    BranchArray = branchInstruction.split(" ");
                    */
/*                  }
                  else{
                    instructionsList.set(i, instructionsList.get(i) +
                    mipsInstruction.toString());
                    continue;
                  }
            }
          } */ //if
         



        }//case
        case "R": {
          if (instructionArray[0].indexOf("jr") != -1) {
            mipsInstruction.setRType(instructionArray[0], instructionArray[1],
              "", "");
            
            currentInstruction = currentInstruction + "\t";
          }//if
          if (instructionArray[0].indexOf("srl")!=-1 || instructionArray[0].indexOf("sll")!=-1 || instructionArray[0].indexOf("sra")!=-1 )
          {
            //Incase of SRL or SLL or SRA, Setup Instruction as Follow Instruction, RD, RT, Shmt
            mipsInstruction.setRType(instructionArray[0], "0", instructionArray[2], instructionArray[1],instructionArray[3]);
            InstructionSet.exceuteR(mipsInstruction,ndx);
            break;
          }
          else {
            mipsInstruction.setRType(instructionArray[0], instructionArray[2],
              instructionArray[3], instructionArray[1]);
              //Execution of the instruction
              InstructionSet.exceuteR(mipsInstruction,ndx);
              break;
          }//else

        
        }//case
        default: {
          break;
        }//default
      }//switch
      }catch(ArrayIndexOutOfBoundsException e){
        System.out.println("The ArrayIndexOutOfBoundsException is in ndx  "+(ndx+1));
        }
  		 /*try{ 
  		instructionsList.set(ndx, instructionsList.get(ndx) +
        mipsInstruction.toString());
       }catch(NullPointerException e){
         System.out.println("The Null pointer exception is at line "+(ndx+1));
       }
       catch(IndexOutOfBoundsException e){
          System.out.println("Index Exception @ Line"+(ndx+1));
       }*/
       System.out.println("Machine Code of the Instruction Of Line: "+(ndx+1)+"\t"+mipsInstruction.toBinary(mipsInstruction));

       addressMachine.put(Integer.parseInt(String.valueOf(address)), mipsInstruction.toBinary(mipsInstruction));
       instructionsList.set(ndx, instructionsList.get(ndx) + String.format("\t%08x\t%s",address,mipsInstruction.toBinary(mipsInstruction)));
       //address = address + 4;


  	}//for
	
    System.out.println("Symbol Table:\n\nLabel\t\t\t\tAddress (in hex)");

    for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
      System.out.printf("%s\t\t\t\t%08x\n", entry.getKey(), entry.getValue());
    }//for

    System.out.println("\nMIPS Code\t\t\t\t\tAddress\t\tMachine Lang.");
    for (String mipsInstructionData : instructionsList) {
      System.out.printf("%s\n", mipsInstructionData);

    
    }//for
    System.out.println();
    for (Map.Entry<String, Integer> entry : labelMap.entrySet()) {
      System.out.println("\nLabel"+"\t\t\t"+"Line");
      System.out.printf("%s\t\t\t%d", entry.getKey(), entry.getValue());
    }
    System.out.println();
    for (Map.Entry<Integer, String> entry : addressMachine.entrySet()) {
      System.out.println("\nAddress"+"\t\t\t\t"+"Machine Code");
      System.out.printf("%08x\t\t\t%s\n", entry.getKey(), entry.getValue());
    }
    
  }//main
}//Driver
