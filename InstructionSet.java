import java.util.HashMap;
import java.math.BigInteger; 

class InstructionSet{
    Registers registers = new Registers();
    AddressMap addressMap = new AddressMap();
    private static HashMap<String, InstructionStruct> instructionMap;

    //Adds all of the instructions being implemented to the map.
    public void init(){
        registers.init();
        addressMap.init();
        //add, sub, addi, lw, sw, slt, slti, beq, bne, j, jal, jr    
        if(instructionMap == null){
            instructionMap = new HashMap<String, InstructionStruct>();
            //Set up all instructions into HashMap for lookup. Numeric values are decimal.
            instructionMap.put("add", new InstructionStruct("add", "R", 0, 32));
            instructionMap.put("sub", new InstructionStruct("sub", "R", 0, 34));
            instructionMap.put("addi", new InstructionStruct("addi", "I", 8, -2));
            instructionMap.put("lw", new InstructionStruct("lw", "I", 35, -2));
            instructionMap.put("sw", new InstructionStruct("sw", "I", 43, -2));
            instructionMap.put("slt", new InstructionStruct("slt", "R", 0, 42));
            instructionMap.put("slti", new InstructionStruct("slti", "I", 10, -2));
            instructionMap.put("beq", new InstructionStruct("beq", "I", 4, -2));
            instructionMap.put("bne", new InstructionStruct("bne", "I", 5, -2));
            instructionMap.put("j", new InstructionStruct("j", "J", 2, -2));
            instructionMap.put("andi", new InstructionStruct("andi", "I", 12, -2));
            instructionMap.put("and", new InstructionStruct("and", "R", 0, 36));
            instructionMap.put("or", new InstructionStruct("or", "R", 0, 37));
            instructionMap.put("ori", new InstructionStruct("ori", "I", 13, -2));
            instructionMap.put("nor", new InstructionStruct("nor", "R", 0, 39));
            instructionMap.put("sll", new InstructionStruct("sll", "R", 0, 0));
            instructionMap.put("srl", new InstructionStruct("srl", "R", 0, 2));
            instructionMap.put("sra", new InstructionStruct("sra", "R", 0, 3));
            instructionMap.put("lui", new InstructionStruct("lui", "I", 15, -2));

        }
    }

    //Returns blank string if the instruction was not found.
    public static String getType(String instruction){
        if(instructionMap.containsKey(instruction)){
            return instructionMap.get(instruction).type; 
        }else{
            return "";
        }
    }
    //Returns instruction title, going to be used in executing.
    public static String getInstruction(String instruction)
    {
        if(instructionMap.containsKey(instruction))
        {
            return instructionMap.get(instruction).instruction;
        }else{
            return "";
        }
    }

    //Returns -1 if instruction not found
    public static int getOpCode(String instruction){
        if(instructionMap.containsKey(instruction)){
            return instructionMap.get(instruction).opCode; 
        }else{
            return -1;
        }
       
    }
    
    //returns -2 if non r-type
    //returns -1 if instruction was not found
    public static int getFunct(String instruction){
        if(instructionMap.containsKey(instruction)){
            return instructionMap.get(instruction).function; 
        }else{
            return -1;
        }
  
    }
    
    //Acts as a struct to keep the instruction data togehter.
    private class InstructionStruct{
        public String instruction;
        public String type;
        public int opCode;
        public int function;
        
        InstructionStruct(String instruction, String type, int opCode, int function){
            this.instruction = instruction;
            this.type = type;
            this.opCode = opCode;
            this.function = function;
        }
    }
    
    public static void exceuteR(Instruction ins, int lineNo)
    {
        //returns the instruction title of ins parameter
        switch (ins.getInstruction(ins)) {
            case "sra":
            Integer rtValueSra=AddressMap.get(Registers.get(ins.getTarget(ins)));
            String rtValueSraBinLead=String.format("%32s",Integer.toBinaryString(rtValueSra)).replace(' ','0');
 
            Integer ShmtValueSra= Instruction.getShamt(ins);
            Integer rdValueSra = rtValueSra >>>  ShmtValueSra;
            //Creating Binary String representation of rdValueSra
            String rdValueSraLead= String.format("%32s",Integer.toBinaryString(rdValueSra)).replace(' ', '0');

            AddressMap.setValue(Registers.get(ins.getDest(ins)), rdValueSra);

            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
            ins.getTarget(ins)+" = "+rtValueSra+" And it's binary value is "+rtValueSraBinLead+"\n"+
            "The Shift Amount = "+ Instruction.getShamt(ins) + "\n "
                    +
            "The new value of register "+ins.getDest(ins)+" = "+AddressMap.get(Registers.get(ins.getDest(ins)))
            +"\nIts binary value = "+rdValueSraLead
            );
            break;
            case "srl":
            Integer rtValueSrl=AddressMap.get(Registers.get(ins.getTarget(ins)));
            String rtValueSrlBinLead=String.format("%32s",Integer.toBinaryString(rtValueSrl)).replace(' ','0');
 
            Integer ShmtValueSrl= Instruction.getShamt(ins);
            Integer rdValueSrl = rtValueSrl >>  ShmtValueSrl;
            //Creating Binary String representation of rdValueSrl
            String rdValueSrlLead= String.format("%32s",Integer.toBinaryString(rdValueSrl)).replace(' ', '0');

            AddressMap.setValue(Registers.get(ins.getDest(ins)), rdValueSrl);

            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
            ins.getTarget(ins)+" = "+rtValueSrl+" And it's binary value is "+rtValueSrlBinLead+"\n"+
            "The Shift Amount = "+ Instruction.getShamt(ins) + "\n "
                    +
            "The new value of register "+ins.getDest(ins)+" = "+AddressMap.get(Registers.get(ins.getDest(ins)))
            +"\nIts binary value = "+rdValueSrlLead
            );
            break;
            case "sll":
            Integer rtValueSll=AddressMap.get(Registers.get(ins.getTarget(ins)));
            String rtValueSllBinLead=String.format("%32s",Integer.toBinaryString(rtValueSll)).replace(' ','0');
 
            Integer ShmtValueSll= Instruction.getShamt(ins);
            Integer rdValueSll = rtValueSll << ShmtValueSll;
            //Creating Binary String representation of rdValueSrl
            String rdValueSllLead= String.format("%32s",Integer.toBinaryString(rdValueSll)).replace(' ', '0');

            AddressMap.setValue(Registers.get(ins.getDest(ins)), rdValueSll);

            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
            ins.getTarget(ins)+" = "+rtValueSll+" And it's binary value is "+rtValueSllBinLead+"\n"+
            "The Shift Amount = "+ Instruction.getShamt(ins) + "\n "
                    +
            "The new value of register "+ins.getDest(ins)+" = "+AddressMap.get(Registers.get(ins.getDest(ins)))
            +"\nIts binary value = "+rdValueSllLead
            );

            break;
            case "nor":

            Integer rsValueNor= AddressMap.get(Registers.get(ins.getSource(ins)));//Holds the stored value in the register's address
            Integer rtValueNor= AddressMap.get(Registers.get(ins.getTarget(ins)));


            String rsBinNor = Integer.toBinaryString(rsValueNor); //String of Binary for rs converted from the value of the register
            String rtBinNor= Integer.toBinaryString(rtValueNor);  //String of Binary for rt
            
            String rsBinLeadNor=String.format("%32s",rsBinNor).replace(' ', '0'); //formatting the rsBin into 32 bits binary
            String rtBinLeadNor=String.format("%32s",rtBinNor).replace(' ', '0'); //formatting the rtBin into 32 bits binary
            
            BigInteger rsBigNor= new BigInteger(rsBinLeadNor,2);
            BigInteger rtBigNor= new BigInteger(rtBinLeadNor,2);
            BigInteger rdBigNor;
            //String rdNorLead="";
            String rdNor="";//the new rd string of binary characters
            //Using the not format as !A && !B --> rsBigNor.not() && rtBigNor.not()
            //then formating it into 32 bit binary code
            
            rdNor=String.format("%32s",((rsBigNor.or(rtBigNor)) ).toString(2)).replace(' ', '0');
            rdBigNor= new BigInteger(rdNor,2);
            for(int i=0;i<rdNor.length();i++)
            {
                rdBigNor=rdBigNor.flipBit(i); //flipping every single bit in the Or result
            }
            String rdBinLeadNor =rdBigNor.toString(2); //converting flipped bits to String in binary
            String rdBigNorLead="";
             rdBigNorLead= String.format("%32s",rdBinLeadNor).replace(' ', '0'); //in order to make sure it's staying 32 bits
            
             //Avoiding NumberFormatException which occures when we try to input a decimal value
             //bigger than the max value of Integer
             try{
                //adding the new decimal value to the address of register rd 
            AddressMap.setValue(Registers.get(ins.getDest(ins)), Integer.parseInt(rdBigNorLead,2)); //adding decimal value converted from binary
             }catch(NumberFormatException e)
             {
                 System.out.println("The decimal value for the instruction  "+ins.getInstruction(ins) +" is so big to be an integer, it won't be added to the register.");
             }

            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
             ins.getSource(ins)+" = "+rsValueNor+" And it's binary value is "+rsBinLeadNor+"\n"+
            ins.getTarget(ins)+" = "+rtValueNor+" And it's binary value is "+rtBinLeadNor+"\n"+
            ins.getDest(ins) + " = " + " "+ins.getSource(ins)+" NOR "+ins.getTarget(ins)+" = "+rdBigNorLead+"\n"+
            "The new value of register "+ins.getDest(ins)+" = "+AddressMap.get(Registers.get(ins.getDest(ins)))
            );
            break;
            case "add":
            //Add = Value inside (Register rs address ) + Value inside (register rt address)
            Integer addRd= AddressMap.get(Registers.get(ins.getDest(ins)));
            Integer add= AddressMap.get(Registers.get(ins.getSource(ins))) + AddressMap.get(Registers.get(ins.getTarget(ins))); //Adding value of rs and value of rt
            String addS= add.toString();
            AddressMap.setValue(Registers.get(ins.getDest(ins)), add);  //updating the rd value to the new value
                //output
               System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\t"+"\n"+ "The old rd value "+ins.getDest(ins)+ " equals "+addRd+"\n"+
                ins.getDest(ins)+
                " = "+ins.getSource(ins).toString()+"("+AddressMap.get(Registers.get(ins.getSource(ins)))+")"+" + "+ins.getTarget(ins).toString()+
                "("+AddressMap.get(Registers.get(ins.getTarget(ins)))+")"+" "+
                " = "+AddressMap.get(Registers.get(ins.getDest(ins)))+
                "\n******************************* \n"
                 );
                break;

            case "sub":
            Integer sub=(AddressMap.get(Registers.get(ins.getSource(ins))) - AddressMap.get(Registers.get(ins.getTarget(ins)))); //Adding value of rs and value of rt
            //String subS= sub.toString();
            AddressMap.setValue(Registers.get(ins.getDest(ins)), sub); //updating the rd value to the new value    
                System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\t"+
                ins.getDest(ins).toString()+
                " = "+ins.getSource(ins).toString()+" - "+ins.getTarget(ins).toString()+" "+
                " = "+
                sub
                +"\n******************************* \n"
                );
            break;
            
            case "slt":
                //if rs<rt set value of 1 to rd, if not set value 0
                Integer slt= (AddressMap.get(Registers.get(ins.getSource(ins))) <AddressMap.get(Registers.get(ins.getTarget(ins)))
                ?
                1:0
                );
                //updating the value of rd
                AddressMap.setValue(Registers.get(ins.getDest(ins)), slt);
                
                System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\t"+
                ins.getDest(ins)+" = " +

                    Registers.get(ins.getSource(ins)) +" <  ?"+ Registers.get(ins.getTarget(ins))
                    +" The Value of rd now is "+AddressMap.get(Registers.get(ins.getDest(ins)))
                );
            break;
            case "and":
            // creating a variable which holds binary value of Instruction
            //the hexToBinary function takes String Hexadecimal representation of the instruction.
            //rsBin.length()<rtBin.length()? rsBin.length() : rtBin.length() ---> this condition was made to 
            //make the length of rd the smallest length between rtBin or rsBin, but since its all 32 bits so its useless
            int rsValueAnd=AddressMap.get(Registers.get(ins.getSource(ins)));//Holds value of the register
            int rtValueAnd=AddressMap.get(Registers.get(ins.getTarget(ins)));

            String rsBinAnd = Integer.toBinaryString(rsValueAnd); //String of Binary for rs converted from the value of the register
            String rtBinAnd= Integer.toBinaryString(rtValueAnd);  //String of Binary for rt
            
            String rsBinLeadAnd=String.format("%32s",rsBinAnd).replace(' ', '0'); //formatting the rsBin into 32 bits binary
            String rtBinLeadAnd=String.format("%32s",rtBinAnd).replace(' ', '0'); //formatting the rtBin into 32 bits binary
            
            BigInteger rsBigAnd= new BigInteger(rsBinLeadAnd,2);
            BigInteger rtBigAnd= new BigInteger(rtBinLeadAnd,2);

            String rdAnd="";//the new rd string of binary characters
            rdAnd=String.format("%32s",rsBigAnd.and(rtBigAnd).toString(2)).replace(' ', '0'); //String binary representation of rd
            
             //adding decimal value converted from binary
            AddressMap.setValue(Registers.get(ins.getDest(ins)), Integer.parseInt(rdAnd,2));

            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
             ins.getSource(ins)+" = "+rsValueAnd+" And "+rsBinLeadAnd+"\n"+
            ins.getTarget(ins)+" = "+rtValueAnd+" And "+rtBinLeadAnd+"\n"+
            ins.getDest(ins) + " = " + " "+ins.getSource(ins)+" & "+ins.getTarget(ins)+" = "+rdAnd+"\n"+
            "The new value of register "+ins.getDest(ins)+" = "+AddressMap.get(Registers.get(ins.getDest(ins)))
            
            );
            
            break;
            case "or":
            //Same Instructions of AND, but it's OR
            int rsValueOr=AddressMap.get(Registers.get(ins.getSource(ins)));//Holds value of the register
            int rtValueOr=AddressMap.get(Registers.get(ins.getTarget(ins)));

            String rsBinOr = Integer.toBinaryString(rsValueOr); //String of Binary for rs converted from the value of the register
            String rtBinOr= Integer.toBinaryString(rtValueOr);  //String of Binary for rt
            
            String rsBinLeadOr=String.format("%32s",rsBinOr).replace(' ', '0'); //formatting the rsBin into 32 bits binary
            String rtBinLeadOr=String.format("%32s",rtBinOr).replace(' ', '0'); //formatting the rtBin into 32 bits binary
            
            BigInteger rsBigOr= new BigInteger(rsBinLeadOr,2);
            BigInteger rtBigOr= new BigInteger(rtBinLeadOr,2);

            String rdOr="";//the new rd string of binary characters
            rdOr=String.format("%32s",rsBigOr.or(rtBigOr).toString(2)).replace(' ', '0'); //String binary representation of rd
            
            AddressMap.setValue(Registers.get(ins.getDest(ins)), Integer.parseInt(rdOr,2)); //adding decimal value converted from binary
            
            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
             ins.getSource(ins)+" = "+rsValueOr+" And "+rsBinLeadOr+"\n"+
            ins.getTarget(ins)+" = "+rtValueOr+" And "+rtBinLeadOr+"\n"+
            ins.getDest(ins) + " = " + " "+ins.getSource(ins)+" & "+ins.getTarget(ins)+" = "+rdOr+"\n"+
            "The new value of register "+ins.getDest(ins)+" = "+AddressMap.get(Registers.get(ins.getDest(ins)))
            );
            break;

    
            default:
                break;
        }
    }
    public static void exceuteI(Instruction ins, int lineNo)
    {
        switch (ins.getInstruction(ins)) { 

            case "beq":
            Integer rsValueBeq= AddressMap.get(Registers.get(ins.getSource(ins)));
            Integer rtValueBeq= AddressMap.get(Registers.get(ins.getTarget(ins)));
            boolean cond = (rsValueBeq.equals(rtValueBeq));
            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
            "Is the value inside rs equals value inside rt? "+cond
            );
            
            break;
            case "bne":
            Integer rsValueBne= AddressMap.get(Registers.get(ins.getSource(ins)));
            Integer rtValueBne= AddressMap.get(Registers.get(ins.getTarget(ins)));
            boolean condne = (!(rsValueBne.equals(rtValueBne)));
            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
            "Is the value inside rs not equals value inside rt? "+condne
            );
            break;
            case "lui":
            
            Integer ImmValueLui= ins.getImmediate(ins);
            Integer rtValueLui= ImmValueLui << 16;
            String rtValueLuiBinLead=String.format("%32s",Integer.toBinaryString(rtValueLui)).replace(' ','0');
            
  
            AddressMap.setValue(Registers.get(ins.getTarget(ins)), rtValueLui);

            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
            ins.getTarget(ins)+" = "+rtValueLui+" And it's binary value is "+rtValueLuiBinLead+"\n"+
            " = "+ins.getImmediate(ins)+" << "+"16"
            );
            break;
            case "ori":
            int rsValueOrI=AddressMap.get(Registers.get(ins.getSource(ins)));//Holds value of the register
            int OrImm=ins.getImmediate(ins);

            String rsBinOrI = Integer.toBinaryString(rsValueOrI); //String of Binary for rs converted from the value of the register
            String OrImmBin= Integer.toBinaryString(OrImm);  //String of Binary for rt
            
            String rsBinLeadOrI=String.format("%32s",rsBinOrI).replace(' ', '0'); //formatting the rsBin into 32 bits binary
            String OrImmBinLead=String.format("%32s",OrImmBin).replace(' ', '0'); //formatting the rtBin into 32 bits binary
            
            BigInteger rsBigOrI= new BigInteger(rsBinLeadOrI,2);
            BigInteger OrImmBig= new BigInteger(OrImmBinLead,2);

            String rdOrI="";//the new rd string of binary characters
            rdOrI=String.format("%32s",rsBigOrI.or(OrImmBig).toString(2)).replace(' ', '0'); //String binary representation of rd
            
            AddressMap.setValue(Registers.get(ins.getDest(ins)), Integer.parseInt(rdOrI,2)); //adding decimal value converted from binary

            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
             ins.getSource(ins)+" = "+rsValueOrI+" And "+rsBinLeadOrI+"\n"+
            ins.getImmediate(ins)+" = "+OrImm+" And "+OrImmBinLead+"\n"+
            ins.getTarget(ins) + " = " + " "+ins.getSource(ins)+" & "+ins.getImmediate(ins)+" = "+rdOrI+"\n"+
            "The new value of register "+ins.getTarget(ins)+" = "+AddressMap.get(Registers.get(ins.getTarget(ins))));

            break; 
            case "addi":
            Integer addI=(AddressMap.get(Registers.get(ins.getSource(ins))) + ins.getImmediate(ins)); //Adding value of rs and value of imm
            String addSI= addI.toString();
            AddressMap.setValue(Registers.get(ins.getTarget(ins)), addI); //updating the rd value to the new value
                //output
               System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\t"+
                ins.getTarget(ins)+
                " = "+ins.getSource(ins).toString()+" + "+ins.getTarget(ins).toString()+" "+
                " = "+addSI+
                "\n******************************* \n"
                 );
                break;

            case "andi":
            int rsValueAndI=AddressMap.get(Registers.get(ins.getSource(ins)));//Holds value of the register
            int AndImm=ins.getImmediate(ins);

            String rsBinAndI = Integer.toBinaryString(rsValueAndI); //String of Binary for rs converted from the value of the register
            String AndImmBin= Integer.toBinaryString(AndImm);  //String of Binary for rt
            
            String rsBinLeadAndI=String.format("%32s",rsBinAndI).replace(' ', '0'); //formatting the rsBin into 32 bits binary
            String AndImmBinLead=String.format("%32s",AndImmBin).replace(' ', '0'); //formatting the rtBin into 32 bits binary
            
            BigInteger rsBigAndI= new BigInteger(rsBinLeadAndI,2);
            BigInteger AndImmBig= new BigInteger(AndImmBinLead,2);

            String rdAndI="";//the new rd string of binary characters
            rdAndI=String.format("%32s",rsBigAndI.and(AndImmBig).toString(2)).replace(' ', '0'); //String binary representation of rd
            
            AddressMap.setValue(Registers.get(ins.getDest(ins)), Integer.parseInt(rdAndI,2)); //adding decimal value converted from binary

            System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\n"+
             ins.getSource(ins)+" = "+rsValueAndI+" And "+rsBinLeadAndI+"\n"+
            ins.getImmediate(ins)+" = "+AndImm+" And "+AndImmBinLead+"\n"+
            ins.getTarget(ins) + " = " + " "+ins.getSource(ins)+" & "+ins.getImmediate(ins)+" = "+rdAndI+"\n"+
            "The new value of register "+ins.getTarget(ins)+" = "+AddressMap.get(Registers.get(ins.getTarget(ins)))
            
            );
            break;
            case "slti" :
              //if rs<imm set value of 1 to rt, if not set value 0
              Integer slti= (AddressMap.get(Registers.get(ins.getSource(ins))) < ins.getImmediate(ins)
              ?
              1:0
              );
              //updating the value of rt
              AddressMap.setValue(Registers.get(ins.getTarget(ins)), slti);
              
              System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\t"+
              ins.getTarget(ins)+" = " +

                  AddressMap.get(Registers.get(ins.getSource(ins)))+"("+ins.getSource(ins)+")" +" <  "+ ins.getImmediate(ins) + " ? "+
                  "\n"+"Now "+ins.getTarget(ins)+" = "+AddressMap.get(Registers.get(ins.getTarget(ins)))
                  );

                  break;
    }
    }

    
}




