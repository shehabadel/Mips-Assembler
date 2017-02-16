import java.util.HashMap;

class InstructionSet{
   
    private static HashMap<String, InstructionStruct> instructionMap;

    public void init(){
   
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
            instructionMap.put("jal", new InstructionStruct("jal", "J", 3, -2));
            instructionMap.put("jr", new InstructionStruct("jr", "R", 0, 8)); 
            
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

}




