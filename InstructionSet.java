import java.util.HashMap;

class InstructionSet{
    Registers registers = new Registers();
    
    private static HashMap<String, InstructionStruct> instructionMap;

    //Adds all of the instructions being implemented to the map.
    public void init(){
        registers.init();
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
            
            case "add":
            Integer add=(Registers.get(ins.getSource(ins)) + Registers.get(ins.getTarget(ins))); //Adding value of rs and value of rt
            String addS= add.toString();
            Registers.setValue(ins.getDest(ins), add); //updating the rd value to the new value
                //output
               System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\t"+
                ins.getDest(ins)+
                " = "+ins.getSource(ins).toString()+" + "+ins.getTarget(ins).toString()+" "+
                " = "+addS+
                "\n******************************* \n"
                 );
                break;

            case "sub":
            Integer sub=(Registers.get(ins.getSource(ins)) - Registers.get(ins.getTarget(ins))); //Adding value of rs and value of rt
            String subS= sub.toString();
            Registers.setValue(ins.getDest(ins), sub); //updating the rd value to the new value    
                System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\t"+
                ins.getDest(ins).toString()+
                " = "+ins.getSource(ins).toString()+" - "+ins.getTarget(ins).toString()+" "+
                " = "+
                (
                Registers.get(ins.getSource(ins)) - Registers.get(ins.getTarget(ins))
                )
                +"\n******************************* \n"
                );
            break;
            
            case "slt":
                //if rs<rt set value of 1 to rd, if not set value 0
                Integer slt= (Registers.get(ins.getSource(ins)) < Registers.get(ins.getTarget(ins))
                ?
                1:0
                );
                //updating the value of rd
                Registers.setValue(ins.getDest(ins), slt);
                
                System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\t"+
                ins.getDest(ins)+" = " +

                    Registers.get(ins.getSource(ins)) +"<"+ Registers.get(ins.getTarget(ins))
                    
                );
            break;
            //case "and":
            //System.out.println("**************Execution**************\nLine Number:"+(lineNo+1)+"\t"+
            //ins.getDest(ins)+ " = "+
                
            default:
                break;
        }
    }

}




