public class ManagePlayer {
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
        
        if(head == null){
        head = fighter;
        }else{
            Character curr = head;
            while(curr.next != null){
                curr = curr.next;
            }
            curr.next = fighter;
        }
    }

    public Character getPlayer(String nama){
        Character curr = head;
        while(curr != null){
            if(curr.orang.equals(nama)){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public void GiveWeapon(String namafighter, String namasenjata, int physicaldamage, int magicpower){
        Character character = getPlayer(namafighter);
        if(character != null){
            if(character.weapon == null){
                character.weapon = new Weapon(namasenjata, physicaldamage, magicpower);
                character.physicaldamage += physicaldamage;
                character.magicpower += magicpower;
            }else{
                Weapon curr = character.weapon;
                while(curr.next != null){
                    curr = curr.next;
                }
                curr.next = new Weapon(namasenjata, physicaldamage, magicpower);
            }
        }
    }

    public void GiveArmor(String namafighter, String namarmor, int physicaldefense, int magicdefense){
        Character character = getPlayer(namafighter);
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

    public void buyweapon(String namaplayer, String namasenjata, SenjataShop shop){
        Character curr = getPlayer(namaplayer);
        if(curr != null){
            Weapon weaponToBuy = shop.getweapon(namasenjata);

            Weapon check = curr.weapon;
            while(check != null){
                if(check.namasenjata.equals(namasenjata)){
                    System.out.println(curr.orang + " sudah memiliki senjata '" + namasenjata + "' !");
                    return;
                }
                check = check.next;
            }
            
            if(weaponToBuy != null){
                if(curr.gold >= weaponToBuy.cost){
                    curr.gold -= weaponToBuy.cost;
                    Weapon beli = new Weapon(weaponToBuy.namasenjata, weaponToBuy.physicaldamage,weaponToBuy.magicpower, weaponToBuy.cost);
                    curr.physicaldamage += beli.physicaldamage;
                    curr.magicpower += beli.magicpower;
                    System.out.println(namaplayer + " membeli " + namasenjata + " seharga " + weaponToBuy.cost + " gold.");
                    System.out.println("Gold " + namaplayer + " tersisa " + curr.gold + " gold.");
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
                    System.out.println(namaplayer + " tidak cukup gold untuk membeli " + namasenjata + ".");
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
    
    public void displayfighter(){
        Character curr = head;
        while(curr != null){
            System.out.println("Nama : " + curr.orang + " | Role : " + curr.role );
            System.out.println("HP : " + curr.health + " | Gold : " + curr.gold);
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
    public void addboss(String namaboss, int health, int physicaldamage, int magicpower){
        Boss boss = new Boss(namaboss, health, physicaldamage, magicpower);
        if(head == null){
            head = boss;
        }else{
            Boss curr = head;
            while(curr.next != null){
                curr = curr.next;
            }
            curr.next = boss;
        }
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
