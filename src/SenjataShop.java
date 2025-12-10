public class SenjataShop {
    Weapon headfighter;
    Weapon headarcher;
    Weapon headmagic;
    Armor headarmor;
    Weapon head;

    public void loadweapon(){
        Weapon weapon1 = new Weapon("Garnok's Fist", "Fighter", 20, 0, 50, 1);
        Weapon weapon2 = new Weapon("Zephyrum Cleaver", "Fighter", 40, 0, 230, 2);
        Weapon weapon3 = new Weapon("Vat'gir Jawblade", "Fighter", 20, 0, 478, 3);
        Weapon weapon4 = new Weapon("Kraken's Gavel", "Fighter", 20, 0, 650, 4);
        Weapon weapon5 = new Weapon("Whisperwire Cord", "Archer", 20, 0, 50, 1);
        Weapon weapon6 = new Weapon("Thrynne's Shard", "Archer", 20, 0, 300, 2);
        Weapon weapon7 = new Weapon("Aethelwin", "Archer", 20, 0, 520, 3);
        Weapon weapon8 = new Weapon("Voidlash", "Archer", 20, 0, 680, 4);
        Weapon weapon9 = new Weapon("Ostracon of Yl'geth", "Magic", 20, 0, 50, 1);
        Weapon weapon10 = new Weapon("Kyanix Prism", "Magic", 20, 0, 278, 2);
        Weapon weapon11 = new Weapon("Voidalith Shard", "Magic", 20, 0, 450, 3);
        Weapon weapon12 = new Weapon("Zir'ael Focus", "Magic", 20, 0, 700, 4);

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
        Armor armor1 = new Armor("Steel Armor", 10, 5, 20, 1);
        Armor armor2 = new Armor("Dragon Scale", 25, 20, 70, 2);
        Armor armor3 = new Armor("Mystic Robe", 10, 30, 0, 3);
        Armor armor4 = new Armor("Black Knight Armor", 10, 30, 0, 4);
        Armor armor5 = new Armor("Abyssal Armor", 10, 30, 0, 5);

        headarmor = armor1;
        armor1.next = armor2;
        armor2.next = armor3;
        armor3.next = armor4;
        armor4.next = armor5;
    }

    public Weapon getweaponfighter(int id){
        Weapon curr = headfighter;
        while(curr != null){
            if(curr.id == id){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public Weapon getweaponmagic(int id){
        Weapon curr = headmagic;
        while(curr != null){
            if(curr.id == id){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public Weapon getweaponarcher(int id){
        Weapon curr = headarcher;
        while(curr != null){
            if(curr.id == id){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public Armor getArmor(int id){
        Armor curr = headarmor;
        while(curr != null){
            if(curr.id == id){
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
        
        while(curr != null){
            System.out.println(curr.id +". "+ curr.namasenjata + " ==> Physical Damage : " + curr.physicaldamage + " || Magic Power : " + curr.magicpower + " || Cost : " + curr.cost + "|| ID : "+ curr.id);
            curr = curr.next;
        }
    }

    public void displayarmor(){
        Armor curr = headarmor;
        int count = 1;
        while(curr != null){
            System.out.println(count +". "+ curr.namaarmor + " Physical Defense : " + curr.physicaldefense + " Magic Defense : " + curr.magicdefense + " Cost : " + curr.cost + " ID : " + curr.id);
            count++;
            curr = curr.next;
        }
    }

    private void swapData(Weapon a, Weapon b) {
        int tempId = a.id; a.id = b.id; b.id = tempId;
        String tempName = a.namasenjata; a.namasenjata = b.namasenjata; b.namasenjata = tempName;
        int tempCost = a.cost; a.cost = b.cost; b.cost = tempCost;
        int tempPhys = a.physicaldamage; a.physicaldamage = b.physicaldamage; b.physicaldamage = tempPhys;
        int tempMag = a.magicpower; a.magicpower = b.magicpower; b.magicpower = tempMag;
    }

    public void sortWeapon(String role, int mode) {
        Weapon head = null;
        if (role.equals("Fighter")) head = headfighter;
        else if (role.equals("Archer")) head = headarcher;
        else if (role.equals("Magic")) head = headmagic;

        if (head == null || head.next == null) return;

        boolean swapped;
        Weapon ptr1;
        Weapon lptr = null;

        do {
            swapped = false;
            ptr1 = head;

            while (ptr1.next != lptr) {
                boolean condition = false;
                
                switch(mode) {
                    case 1: condition = ptr1.cost > ptr1.next.cost; break;
                    case 2: condition = ptr1.cost < ptr1.next.cost; break;
                    case 3: condition = ptr1.physicaldamage < ptr1.next.physicaldamage; break;
                    case 4: condition = ptr1.magicpower < ptr1.next.magicpower; break;
                }

                if (condition) {
                    swapData(ptr1, ptr1.next);
                    swapped = true;
                }
                ptr1 = ptr1.next;
            }
            lptr = ptr1;
        } while (swapped);
    }

    private void swapArmorData(Armor a, Armor b) {
        int tempId = a.id; a.id = b.id; b.id = tempId;
        String tempName = a.namaarmor; a.namaarmor = b.namaarmor; b.namaarmor = tempName;
        int tempCost = a.cost; a.cost = b.cost; b.cost = tempCost;
        int tempPhys = a.physicaldefense; a.physicaldefense = b.physicaldefense; b.physicaldefense = tempPhys;
        int tempMag = a.magicdefense; a.magicdefense = b.magicdefense; b.magicdefense = tempMag;
    }

    public void sortArmor(int mode) {
        Armor head = headarmor;
        if (head == null || head.next == null) return;

        boolean swapped;
        Armor ptr1;
        Armor lptr = null;

        do {
            swapped = false;
            ptr1 = head;

            while (ptr1.next != lptr) {
                boolean condition = false;
                
                switch(mode) {
                    case 1: condition = ptr1.cost > ptr1.next.cost; break;              
                    case 2: condition = ptr1.cost < ptr1.next.cost; break;
                    case 3: condition = ptr1.physicaldefense < ptr1.next.physicaldefense; break; 
                    case 4: condition = ptr1.magicdefense < ptr1.next.magicdefense; break;
                }

                if (condition) {
                    swapArmorData(ptr1, ptr1.next);
                    swapped = true;
                }
                ptr1 = ptr1.next;
            }
            lptr = ptr1;
        } while (swapped);
    }
}