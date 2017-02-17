class Instruction{  
    private int address, jumpAddress, immediate;
    private String instruction, source, target, destination, type;

    
    //Destination will hold immediates and jump addresses
    // Enum type; R, I, J (Later could add FR, FJ if later assignment uses it)
    
    Instruction(int address){
        this.address = address;

    }
  
    public void setJType(String instruction, int jump){
        this.instruction = instruction;     
        this.jumpAddress = jump - address - 4;
        type = "J";   
    }
    public void setRType(String instruction, String source, String target, String destination){
     
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
        long
        temp = InstructionSet.getOpCode(instruction);

        if(type == "J"){
            temp = (temp << 26) +  jumpAddress;
        }else if(type == "R"){
            temp = (temp << 5) + Registers.get(source);
            temp = (temp << 5) + Registers.get(target);
            temp = (temp << 5) + Registers.get(destination);
            temp = (temp << 11) + InstructionSet.getFunct(instruction);
        }else if(type == "I"){
            temp = (temp << 5) +  Registers.get(source);
            temp = (temp << 5) + Registers.get(target);
            temp = (temp << 16) + immediate;
System.out.printf("%s\t%d\n", instruction, temp);
        }
        return String.format("%08x", temp);
    }

    public String toString(){ // Formats for output
        String
        returnString,
        machineLang;

        machineLang = toHex();
        returnString = String.format("%08x\t%s", address, machineLang);

       return returnString;
    }

}
