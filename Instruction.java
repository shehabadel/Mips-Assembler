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
  
    public void setJType(String instruction, String jump){
        this.instruction = instruction;     
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
        this.immediate = Integer.parseInt(immediate, 16);
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
        StringBuilder sb = new StringBuilder();
        if(!label.equals(null) && !label.equals("")){
            sb.append(label + ":\t");
        }else{
            sb.append("\t\t");
        }
        
        sb.append(instruction + "\t");

        if(type.equals("J")){
            sb.append(jumpAddress + "\t");
        }else if(type.equals("R")){
            sb.append(destination + "," + source + "," + target);
        }else if(type.equals("I")){
            sb.append(source + "," + target + "," + immediate);
        }else{
            return "";
        }
        sb.append("\t\t" + address + "\t" + toHex());
        return sb.toString();
    }

}
