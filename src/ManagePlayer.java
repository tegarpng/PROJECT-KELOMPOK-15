import java.util.Scanner;
public class ManagePlayer {
    Scanner input = new Scanner(System.in);
    Character head;
    SenjataShop shop;
    WeaponStackManager weaponStackManager;

    public void addplayer(String nama, String Role){
        Character fighter;
        if(Role.equals("Magic")){
            fighter = new Character(nama, 1000, Role, 0, 20, 0, 0);
        }
        else if(Role.equals("Fighter")){
            fighter = new Character(nama, 1300, Role, 20, 0, 0, 0);
        }
        else if(Role.equals("Archer")){
            fighter = new Character(nama, 1000, Role, 15, 10, 0, 0);
        }
        else{
            System.out.println("Role tidak tersedia!");
            return;
        }
        head = fighter;
    }

    public Character getPlayerbyRole(String role){
        Character curr = head;
        while(curr != null){
            if(curr.orang.equals(role)){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public void GiveWeapon(String namasenjata,String role, int physicaldamage, int magicpower){
        Character character = head;
        if(character != null){
            if(character.weapon == null){
                if(role.equals("Fighter")){
                    character.weapon = new Weapon(namasenjata, role, physicaldamage, magicpower, 0);
                }else if(role.equals("Magic")){
                    character.weapon = new Weapon(namasenjata, role, physicaldamage, magicpower, 0);
                }else if(role.equals("Archer")){
                    character.weapon = new Weapon(namasenjata, role, physicaldamage, magicpower, 0);
                }else{
                    System.out.println("Role tidak tersedia!");
                    return;
                }
            }
        }
    }

    public void GiveArmor(String namarmor, int physicaldefense, int magicdefense){
        Character character = head;
        if(character != null){
            if(character.armorplayer == null){
                character.armorplayer = new Armor(namarmor, physicaldefense, magicdefense,0);
                character.physicaldefense += physicaldefense;
                character.magicdefense += magicdefense;
            }
        }
    }

    public void removeplayer(String nama){
        if(head == null){
            System.out.println("No players to remove.");
            return;
        }
        if(head.orang.equals(nama)){
            head = head.next;
            return;
        }
        Character curr = head;
        while(curr.next != null && !curr.next.orang.equals(nama)){
            curr = curr.next;
        }
        if(curr.next != null){
            curr.next = curr.next.next;
        }else{
            System.out.println("Player not found.");
        }
    }

    public void buyweapon(SenjataShop shop){
        int namasenjata;
        Character curr = head;
        if(curr != null){
            Weapon weaponToBuy;
            //menentukan role senjata
            if(curr.role.equals("Fighter")){  
                shop.displayweapon(curr.role);
                System.out.println("Mau yang mana senjatanya? :");
                namasenjata = input.nextInt();
                weaponToBuy = shop.getweaponfighter(namasenjata);
            }else if(curr.role.equals("Magic")){
                shop.displayweapon(curr.role);
                System.out.println("Mau yang mana senjatanya? :");
                namasenjata = input.nextInt();
                weaponToBuy = shop.getweaponmagic(curr.role);
            }else if(curr.role.equals("Archer")){
                shop.displayweapon(curr.role);
                System.out.println("Mau yang mana senjatanya? :");
                namasenjata = input.nextInt();
                weaponToBuy = shop.getweaponarcher(curr.role);
            }else{
                System.out.println("Role tidak tersedia!");
                return;
            }

            //cek apakah sudah punya senjata yang sama
            Weapon check = curr.weapon;
            while(check != null){
                if(check.id == namasenjata){
                    System.out.println(curr.orang + " sudah memiliki senjata '" + namasenjata + "' !\nAnda tidak bisa membeli item yang sama");
                    return;
                }
                check = check.next;
            }
            
            //proses pembelian
            if(weaponToBuy != null){
                if(curr.gold >= weaponToBuy.cost){
                    curr.gold -= weaponToBuy.cost;
                    Weapon beli = new Weapon(weaponToBuy.namasenjata, weaponToBuy.role, weaponToBuy.physicaldamage, weaponToBuy.magicpower, weaponToBuy.cost, weaponToBuy.id);
                    System.out.println(curr.orang + " membeli " + weaponToBuy.namasenjata + " seharga " + weaponToBuy.cost + " gold.");
                    System.out.println("Gold " + curr.orang + " tersisa " + curr.gold + " gold.");
                    if(curr.weapon == null){
                        curr.weapon = beli;
                    }else{
                        Weapon current = curr.weapon;
                        while(current.next != null){
                            current = current.next;
                        }
                        current.next = beli;
                    }
                }else{
                    System.out.println(curr.orang + " tidak cukup gold untuk membeli " + namasenjata + ".");
                }
            }else{
                System.out.println("Senjata dengan " + namasenjata + " tidak terdapat pada toko.");
                return;
            }
            
        }else{
            System.out.println("Player tidak ditemukan");
            return;
        }
    }
    
    public void sell(String namaOrang, String namaItems){
        Character curr = head;
        if(curr != null){
            Weapon weaponChar = curr.weapon;

            if(weaponChar.namasenjata.equals(namaItems)){
                curr.gold += weaponChar.cost;
                System.out.println(weaponChar.namasenjata + " - " + weaponChar.cost + " Dijual");
                curr.weapon = weaponChar.next;
                return;
            }

            while(weaponChar.next != null && !weaponChar.next.namasenjata.equals(namaItems)){
                weaponChar = weaponChar.next;
            }
            if(weaponChar.next == null){
                System.out.println("Tidak ada items");
                return;
            }else{
                Weapon toSell = weaponChar.next;
                weaponChar.next = weaponChar.next.next;
                curr.gold += toSell.cost;
                System.out.println(toSell.namasenjata + " - " + toSell.cost + " Dijual");
            }
        }
    }

    public void displayfighter(){
        Character curr = head;
        while(curr != null){
            System.out.println("##==========================================================##");
            System.out.println("|| Nama  : " + curr.orang + "  | Role : " + curr.role + "||");
            System.out.println("|| HP    : " + curr.health + " | Gold : " + curr.gold + "||");
            System.out.println("|| Stats");
            System.out.println("|| Physical Damage :" + curr.physicaldamage + " || Magical Power : " + curr.magicpower + "||");
            System.out.println("Items:");
            Weapon weaponCurr = curr.weapon;
            System.out.println("- Weapons:");

            if(weaponCurr == null){
                System.out.println("  - None");
            }else{
                while(weaponCurr != null){
                    System.out.println("  - " + weaponCurr.namasenjata + " Physical Damage: " + weaponCurr.physicaldamage + " Magic Power: " + weaponCurr.magicpower);
                    weaponCurr = weaponCurr.next;
                }
            }

            Armor armorCurr = curr.armorplayer;
            System.out.println("- Armors:");
            if(armorCurr == null){
                System.out.println("  - None");
            }else{
                while(armorCurr != null){
                    System.out.println("  - " + armorCurr.namaarmor + " Physical Defense: " + armorCurr.physicaldefense + " Magic Defense: " + armorCurr.magicdefense);
                    armorCurr = armorCurr.next;
                }
            }

            if(curr.armorplayer != null || curr.weapon != null){
                System.out.println("Total Stats with Equipment:");
                System.out.println("  >> Total Physical Damage : " + curr.physicaldamage);
                System.out.println("  >> Total Magic Power     : " + curr.magicpower);
                System.out.println("  >> Total Physical Defense : " + curr.physicaldefense);
                System.out.println("  >> Total Magic Defense    : " + curr.magicdefense);
            }


            System.out.println("-------------------------------------------");
            curr = curr.next;
        }
    }
}

class manageBoss{
    Boss head;
    public void loadBoss(){
        Boss bossmain1 = new Boss("Death Knight", 5000, 50, 40);
        Boss bossmain2 = new Boss("Euroboros", 8000, 70, 70);
        Boss bossmain3 = new Boss("Omen", 12000, 120, 170);
        Boss bossmain4 = new Boss("Chronos", 20000, 180, 170);
        Boss finalboss = new Boss("Aetherius", 40000, 200, 250);
        head = bossmain1;
        bossmain1.next = bossmain2;
        bossmain2.next = bossmain3;
        bossmain3.next = bossmain4;
        bossmain4.next = finalboss;
    }

    public void displayboss(){
        Boss curr = head;
        while(curr != null){
            System.out.println("Nama Boss : " + curr.namaboss);
            System.out.println("Health : " + curr.health);
            System.out.println("Physical Damage : " + curr.physicaldamage);
            System.out.println("Magic Power : " + curr.magicpower);
            System.out.println("-----------------------------------");
            curr = curr.next;
        }
    }
}
