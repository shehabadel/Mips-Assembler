import java.util.HashMap;

class AddressMap{
    private static HashMap<Integer, Integer> AddressMap;
    public static void init(){
        AddressMap = new HashMap<>();
        AddressMap.put(0, 0);
        AddressMap.put(1, 1);
        AddressMap.put(2, 1);
        AddressMap.put(3, 23);
        AddressMap.put(4, 4);
        AddressMap.put(5, 5);
        AddressMap.put(6, 8);
        AddressMap.put(7, 2);
        AddressMap.put(8, 4);
        AddressMap.put(9, 2);
        AddressMap.put(10, 3);
        AddressMap.put(11, 0);
        AddressMap.put(12, 12);
        AddressMap.put(13, 13);
        AddressMap.put(14, 14);
        AddressMap.put(15, 15);
        AddressMap.put(16, 21);
        AddressMap.put(17, 17);
        AddressMap.put(18, 18);
        AddressMap.put(19, 19);
        AddressMap.put(20, 20);
        AddressMap.put(21, 4);
        AddressMap.put(22, 22);
        AddressMap.put(23, 23);
        AddressMap.put(24, 24);
        AddressMap.put(25, 25);
        AddressMap.put(26, 26);
        AddressMap.put(27, 27);
        AddressMap.put(28, 28);
        AddressMap.put(29, 29);
        AddressMap.put(30, 30);
        AddressMap.put(31, 31);
    }
        public static Integer get(Integer address)
        {
            return AddressMap.get(address);
        }
    
        public static void setValue(Integer address, Integer value)
        {
            AddressMap.replace(address,value);
    }
}
