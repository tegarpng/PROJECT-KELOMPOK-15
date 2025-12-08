public class SenjataShop {
    Weapon headfighter;
    Weapon headarcher;
    Weapon headmagic;
    Armor headarmor;
    Weapon head;

    public void loadweapon(){
        Weapon weapon1 = new Weapon("Excalibur", "Fighter", 20, 0, 0);
        Weapon weapon2 = new Weapon("Excalibur", "Fighter", 20, 0, 0);
        Weapon weapon3 = new Weapon("Excalibur", "Fighter",20 , 0, 0);
        Weapon weapon4 = new Weapon("Excalibur", "Fighter", 20, 0, 0);
        Weapon weapon5 = new Weapon("Busur Derajat", "Archer", 20, 0, 0);
        Weapon weapon6 = new Weapon("Excalibur", "Archer", 20, 0, 0);
        Weapon weapon7 = new Weapon("Excalibur", "Archer", 20, 0, 0);
        Weapon weapon8 = new Weapon("Excalibur", "Archer", 20, 0, 0);
        Weapon weapon9 = new Weapon("Lantern", "Magic", 20, 0, 0);
        Weapon weapon10 = new Weapon("Excalibur", "Magic",20 , 0, 0);
        Weapon weapon11 = new Weapon("Excalibur", "Magic", 20, 0, 0);
        Weapon weapon12 = new Weapon("Excalibur", "Magic", 20, 0, 0);

        headfighter = weapon1;
        weapon1.next = weapon2;
        weapon2.next = weapon3;
        weapon3.next = weapon4;
        
        headarcher = weapon5;
        weapon5.next = weapon6;
        weapon6.next = weapon7;
        weapon7.next = weapon8;

        headmagic = weapon9;
        weapon9.next = weapon10;
        weapon10.next = weapon11;
        weapon11.next = weapon12;
    }

    public void loadrmor(){
        Armor armor1 = new Armor("Steel Armor", 15, 10,0);
        Armor armor2 = new Armor("Dragon Scale", 25, 20,0);
        Armor armor3 = new Armor("Mystic Robe", 10, 30,0);
        Armor armor4 = new Armor("Mystic Robe", 10, 30,0);
        Armor armor5 = new Armor("Mystic Robe", 10, 30,0);

        headarmor = armor1;
        armor1.next = armor2;
        armor2.next = armor3;
        armor3.next = armor4;
        armor4.next = armor5;
    }

    public Weapon getweaponfighter(String nama){
        Weapon curr = headfighter;
        while(curr != null){
            if(curr.namasenjata.equals(nama)){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }
    public Weapon getweaponmagic(String nama){
        Weapon curr = headmagic;
        while(curr != null){
            if(curr.namasenjata.equals(nama)){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }
    public Weapon getweaponarcher(String nama){
        Weapon curr = headarcher;
        while(curr != null){
            if(curr.namasenjata.equals(nama)){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public void displayweapon(String role){
        Weapon curr;
        if(role.equals("Fighter")){
            curr = headfighter;
        }else if(role.equals("Magic")){
            curr = headmagic;
        }else if(role.equals("Archer")){
            curr = headarcher;
        }else{
            System.out.println("Role tidak tersedia!");
            return;
        }
        
        int count = 1;
        while(curr != null){
            System.out.println(count +". "+ curr.namasenjata + " Damage : " + curr.physicaldamage + " Magic Power : " + curr.magicpower + " Cost : " + curr.cost);
            count++;
            curr = curr.next;
        }
    }

    public void displayarmor(){
        Armor curr = headarmor;
        int count = 1;
        while(curr != null){
            System.out.println(count +". "+ curr.namaarmor + " Physical Defense : " + curr.physicaldefense + " Magic Defense : " + curr.magicdefense);
            count++;
            curr = curr.next;
        }
    }
}
