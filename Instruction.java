class Instruction{  
    private int address, jumpAddress, immediate;
    private String instruction, source, target, destination, label;
    private String type;
    //Destination will hold immediates and jump addresses
    // Enum type; R, I, J (Later could add FR, FJ if later assignment uses it)
    
    Instruction(int address){
        this.address = address;
    }
    
    public void setLabel(String newLabel){
        label = newLabel;
    }
  
    public void setJType(String instruction, int jump){
        this.instruction = instruction;     
        this.jumpAddress = jump - address - 4;
        type = "J";   
    }
    public void setRType(int address, String instruction, String source, String target, String destination){
     
        this.instruction = instruction;
        this.destination = destination;
        this.source = source;
        this.target = target;
        type = "R";
    }

    public void setIType(String instruction, String source, String target, String immediate){
    
        this.instruction = instruction;
        this.source = source;
        this.target = target;
        this.immediate = Integer.parseInt(immediate);
        type = "I";   
    }

    public String toHex(){
        long temp = InstructionSet.getOpCode(instruction);
        if(type == "J"){
            temp = (temp << 26) +  jumpAddress;
        }else if(type == "R"){
            temp = (temp << 5) + Registers.get(source);
            temp = (temp << 5) + Registers.get(target);
            temp = (temp << 5) + Registers.get(destination);
            temp = (temp << 10) + InstructionSet.getFunct(instruction);
        }else if(type == "I"){
            temp = (temp << 5) +  Registers.get(source);
            temp = (temp << 5) + Registers.get(target);
            temp = (temp << 16) + immediate;
        }       
        return Long.toHexString(temp);
    }

    public String toString(){ // Formats for output
       return ("\t\t" + address + "\t" + toHex());
    }

}
