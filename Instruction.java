class Instruction{  
    //Set up data that the instruction should store.
    private int address, jumpAddress, immediate;
    private String instruction, source, target, destination, type;

    
    //Destination will hold immediates and jump addresses
    // Enum type; R, I, J (Later could add FR, FJ if later assignment uses it)
    
    Instruction(int address){
        this.address = address;

    }
    Instruction(String instruction, String destination, String source, String target)
    {
        this.instruction=instruction;
        this.source=source;
        this.target=target;
        this.destination=destination;
    }
    Instruction(String instruction, String destination, String source, int immediate){
        this.instruction=instruction;
        this.destination=destination;
        this.source=source;
        this.immediate=immediate;
    }
    //some setters
    public void setInstruction(String instruction)
    {
        this.instruction=instruction;
    }
    public void setSource(String source)
    {
        this.source=source;
    }
    public void setTarget(String target)
    {
        this.target=target;
    }
    public void setDes(String destination)
    {
        this.destination=destination;
    }
    //some getters
    public String getInstruction(Instruction ins)
    {
        return ins.instruction;
    }
    public String getSource(Instruction ins)
    {
        return ins.source;
    }
    public String getTarget(Instruction ins)
    {
        return ins.target;
    }
    public String getDest(Instruction ins)
    {
        return ins.destination;
    }
    public String getType(Instruction ins)
    {
        return ins.type;
    }
    public int getAddress(Instruction ins)
    {
        return ins.address;
    }
    public int getImmediate(Instruction ins)
    {
        return ins.immediate;
    }
    public int getJump(Instruction ins)
    {
        return ins.jumpAddress;
    }
    //Sets up a j-type instruction
    public void setJType(String instruction, int jump){
        this.instruction = instruction;     
        this.jumpAddress = jump;  
        type = "J";   
    }

    //Sets up a r-type instruction.
    public void setRType(String instruction, String source, String target, String destination){
        this.instruction = instruction;
        this.destination = destination;
        this.source = source;
        this.target = target;
        type = "R";
    }

    //Sets up I-type instruction.
    public void setIType(String instruction, String source, String target, String immediate){
        //Initialize variables.
        this.instruction = instruction;
        this.source = source;
        this.target = target;
        this.immediate = Integer.parseInt(immediate);
        
        //Branching switches some registers. Perform switch.
        if(instruction.equals("bne") || instruction.equals("beq")){
            this.immediate = (this.immediate - address - 4) / 4;
            this.source = target;
            this.target = source;
            this.immediate = this.immediate & 0x0000FFFF;
        }
    
        type = "I";   
    }


    //Create a hex representation of the instruction.
    public String toHex(){
        long temp = InstructionSet.getOpCode(instruction);
        
        //Follow MIPS Green Card order of information.
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
        }
        //Return the hex value with 8 characters. 32-bit instruction.
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
