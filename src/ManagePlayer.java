import java.util.Scanner;
public class ManagePlayer {
    Scanner input = new Scanner(System.in);
    Character head;
    SenjataShop shop;

    public void addplayer(String nama, String Role){
        Character fighter;
        if(Role.equals("Magic")){
            fighter = new Character(nama, 100, Role, 0, 20, 0, 0);
        }
        else if(Role.equals("Fighter")){
            fighter = new Character(nama, 130, Role, 20, 0, 0, 0);
        }
        else if(Role.equals("Archer")){
            fighter = new Character(nama, 100, Role, 15, 10, 0, 0);
        }
        else if(Role.equals("Tank")){
            fighter = new Character(nama, 150, Role, 10, 5, 20, 15);
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

    public void GiveWeapon(String namasenjata, int physicaldamage, int magicpower){
        Character character = head;
        if(character != null){
            if(character.weapon == null){
                character.weapon = new Weapon(namasenjata, physicaldamage, magicpower, 0);
                character.physicaldamage += physicaldamage;
                character.magicpower += magicpower;
            }else{
                Weapon curr = character.weapon;
                while(curr.next != null){
                    curr = curr.next;
                }
                curr.next = new Weapon(namasenjata, physicaldamage, magicpower, 0);
            }
        }
    }

    public void GiveArmor(String namarmor, int physicaldefense, int magicdefense){
        Character character = head;
        if(character != null){
            if(character.armorplayer == null){
                character.armorplayer = new Armor(namarmor, physicaldefense, magicdefense);
                character.physicaldefense += physicaldefense;
                character.magicdefense += magicdefense;
            }else{
                Armor curr = character.armorplayer;
                while(curr.next != null){
                    curr = curr.next;
                }
                curr.next = new Armor(namarmor, physicaldefense, magicdefense);
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

    public void buyweapon(String namasenjata, SenjataShop shop){
        Character curr = head;
        if(curr != null){
            Weapon weaponToBuy = shop.getweapon(namasenjata);

            Weapon check = curr.weapon;
            while(check != null){
                if(check.namasenjata.equals(namasenjata)){
                    System.out.println(curr.orang + " sudah memiliki senjata '" + namasenjata + "' !\nAnda tidak bisa membeli item yang sama");
                    return;
                }
                check = check.next;
            }
            
            if(weaponToBuy != null){
                if(curr.gold >= weaponToBuy.cost){
                    curr.gold -= weaponToBuy.cost;
                    Weapon beli = new Weapon(weaponToBuy.namasenjata, weaponToBuy.physicaldamage,weaponToBuy.magicpower, weaponToBuy.cost);
                    System.out.println("Apakah kamu yakin membeli item yang bukan role mu ( " + curr.role + " ) \nYA atau TIDAK");
                    String choice = input.nextLine(); 
                    if(choice.equalsIgnoreCase("ya")){
                        curr.physicaldamage += (beli.physicaldamage * 0.4);
                        System.out.println(curr.orang + " membeli " + namasenjata + " seharga " + weaponToBuy.cost + " gold.");
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
                        return;
                    }else{
                        curr.physicaldamage += beli.physicaldamage;
                        curr.magicpower += beli.magicpower;
                        System.out.println(curr.orang + " membeli " + namasenjata + " seharga " + weaponToBuy.cost + " gold.");
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
                    }
                }else{
                    System.out.println(curr.orang + " tidak cukup gold untuk membeli " + namasenjata + ".");
                }
            }else{
                System.out.println("Senjata " + namasenjata + " tidak terdapat pada toko.");
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
            System.out.printf("|| Nama  : " + curr.orang + "  | Role : " + curr.role + "||");
            System.out.println("|| HP    : " + curr.health + " | Gold : " + curr.gold + "||");
            System.out.println("|| Stats   \n|| Physical Damage :" + curr.physicaldamage + " || Magical Power : " + curr.magicpower + "||");
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
        Boss boss = new Boss("Death Knight", 20000, 100, 70);
        Boss boss1 = new Boss("Death Knight", 20000, 100, 70);
        Boss boss2 = new Boss("Death Knight", 20000, 100, 70);
        Boss boss3 = new Boss("Death Knight", 20000, 100, 70);
        head = boss;
        boss.next = boss1;
        boss1.next = boss2;
        boss2.next = boss3;
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
