class Instruction{  
    //Set up data that the instruction should store.
    private int address, jumpAddress, immediate;
    private int shamt=0;
    private String instruction, source, target, destination, type, label;
    private static boolean flag;

    
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
    public void setFlag(boolean flag)
    {
        this.flag=flag;
    }
    public static boolean getFlag(){
        return flag;
    } 
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
    public Integer getImmediate(Instruction ins)
    {
        return ins.immediate;
    }
    public int getJump(Instruction ins)
    {
        return ins.jumpAddress;
    }
    public static Integer getShamt(Instruction ins)
    {
        return ins.shamt;
    }
    public static String getLabel(Instruction ins)
    {
        return ins.label;
    }
    public void setLabel(String label)
    {
        this.label=label;
    }
    public void setShamt(int shamt)
    {
        this.shamt=shamt;
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
    public void setRType(String instruction, String source, String target, String destination, String shamt){
        this.instruction = instruction;
        this.destination = destination;
        this.source = source;
        this.target = target;
        this.shamt = Integer.parseInt(shamt);
        type = "R";

        
    }

    //Sets up I-type instruction.
    public void setIType(String instruction, String source, String target, String immediate){
        //Initialize variables.
        if(instruction.equals("lui"))
        {
            setLui(instruction, target, immediate);
        }
        else if(instruction.equals("beq")||instruction.equals("bne")){
            this.instruction = instruction;
            this.source = source;
            this.target = target;
            this.immediate= Integer.parseInt(immediate);  
        }
        else{
        this.instruction = instruction;
        this.source = source;
        this.target = target;
        try{
            this.immediate = Integer.parseInt(immediate);
        }catch(NumberFormatException e)
        {   System.out.println("Exception caught, by default the immediate = zero");
            this.immediate = 0;
        }
        }
        //Branching switches some registers. Perform switch.
        /*if(instruction.equals("bne") || instruction.equals("beq")){
            //setBranch(instruction, source, target, label);

            
            this.immediate = (this.immediate - address - 4) / 4;
            this.source = target;
            this.target = source;
        this.immediate = this.immediate & 0x0000FFFF;   
        }*/
        type = "I";   
    }
    public void setLui(String instruction, String target, String immediate){
        this.instruction = instruction;
        this.target = target;
        this.immediate = Integer.parseInt(immediate);
    }
    public void setBranch(String instruction, String source, String target, String label, Integer address)
    {
        this.instruction=instruction;
        this.source=source;
        this.target=target;
        this.label=label;
    }
    //Create a hex representation of the instruction.
    public String toHex(){
        long temp = InstructionSet.getOpCode(instruction);
        //String tempS=;
        //Follow MIPS Green Card order of information.
        if(type == "J"){
            temp = (temp << 26) +  jumpAddress;
        }else if(type == "R"){
            if(instruction.equals("srl")||instruction.equals("sll")||instruction.equals("sra") ){
                temp =(temp<<5) + 0;
            }else{
            temp = (temp << 5) + Registers.get(source);
            }
            temp = (temp << 5) + Registers.get(target);
            temp = (temp << 5) + Registers.get(destination);
            temp = (temp << 5) + shamt;
            temp = (temp << 11) + InstructionSet.getFunct(instruction);
        }else if(type == "I"){
            if(instruction.equals("lui"))
            {
                temp=(temp<<5) + 0;
            }
            else{
                temp = (temp << 5) +  Registers.get(source);
            }
            temp = (temp << 5) + Registers.get(target);
            temp = (temp << 16) + immediate;
        }
        //Return the hex value with 8 characters. 32-bit instruction.
        return String.format("%08x", temp);
    }
    public String toBinary(Instruction ins){         //Instead of toHex();
        String temp="";
        String op=Integer.toBinaryString(InstructionSet.getOpCode(instruction));
        String opLead=String.format("%6s",op).replace(' ', '0');
        temp=opLead;
        if(type=="J"){
            String JumpAddress=Integer.toBinaryString(jumpAddress);
            String JumpAddressLead=String.format("%26s",JumpAddress).replace(' ', '0');
            temp = temp +  JumpAddressLead;
        }

        if(type=="R")
        {
            String rs= Integer.toBinaryString(Registers.get(source)); //converting rs into binary
            String rsLead= String.format("%5s",rs).replace(' ', '0'); //formating it into 5 bits
            temp=temp+rsLead;                                         //appending it to temp
            String rt= Integer.toBinaryString(Registers.get(target));
            String rtLead= String.format("%5s",rt).replace(' ', '0');
            temp=temp+rtLead;
            String rd= Integer.toBinaryString(Registers.get(destination));
            String rdLead= String.format("%5s",rd).replace(' ', '0');
            temp=temp+rdLead;
            String shmt= Integer.toBinaryString(getShamt(ins));
            String shmtLead= String.format("%5s",shmt).replace(' ', '0');
            temp=temp+shmtLead;
            String func=Integer.toBinaryString(InstructionSet.getFunct(instruction));
            String funcLead=String.format("%6s",func).replace(' ','0');
            temp=temp+funcLead;
        }
        if(type=="I")
        {
            String rs= Integer.toBinaryString(Registers.get(source)); //converting rs into binary
            String rsLead= String.format("%5s",rs).replace(' ', '0'); //formating it into 5 bits
            temp=temp+rsLead;                                         //appending it to temp
            String rt= Integer.toBinaryString(Registers.get(target));
            String rtLead= String.format("%5s",rt).replace(' ', '0');
            temp=temp+rtLead;
            String imm=Integer.toBinaryString(ins.getImmediate(ins));
            String immLead=String.format("%16s",imm).replace(' ','0');
            temp=temp+immLead;
        }
        return temp;
    }
    
    public String hexToBinary(String hex)
    {
        int i = Integer.parseInt(hex, 16);
        String bin = Integer.toBinaryString(i);
        return bin;
    }
    public Integer binaryToDecimal(String bin)
    {
        return Integer.parseInt(bin, 2);
    }

    public String toString(Instruction ins){ // Formats for output
        String
        returnString,
        machineLang;

        machineLang = toHex();
        returnString = String.format("%08x\t%s", address, machineLang);

       return returnString;
    }

}
