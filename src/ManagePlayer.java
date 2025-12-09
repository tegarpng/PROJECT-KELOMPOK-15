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
        
        SkillTreeGen generator = new SkillTreeGen();
        fighter.mySkills = generator.generateSkillForRole(Role);

        head = fighter;
        weaponStackManager = new WeaponStackManager(fighter);
    }

    public void buyweapon(SenjataShop shop){
        int idsenjata;
        Character curr = head;
        if(curr != null){
            Weapon weaponToBuy;
            //menentukan role senjata
            shop.loadweapon();
            if(curr.role.equals("Fighter")){  
                shop.displayweapon(curr.role);
                System.out.println("Mau yang mana senjatanya? :");
                idsenjata = input.nextInt();
                weaponToBuy = shop.getweaponfighter(idsenjata);
            }else if(curr.role.equals("Magic")){
                shop.displayweapon(curr.role);
                System.out.println("Mau yang mana senjatanya? :");
                idsenjata = input.nextInt();
                weaponToBuy = shop.getweaponmagic(idsenjata);
            }else if(curr.role.equals("Archer")){
                shop.displayweapon(curr.role);
                System.out.println("Mau yang mana senjatanya? :");
                idsenjata = input.nextInt();
                weaponToBuy = shop.getweaponarcher(idsenjata);
            }else{
                System.out.println("Role tidak tersedia!");
                return;
            }

            //cek apakah sudah punya senjata yang sama
            Weapon check = curr.weapon;
            while(check != null){
                if(check.id == idsenjata){
                    System.out.println(curr.orang + " sudah memiliki senjata '" + idsenjata + "' !\nAnda tidak bisa membeli item yang sama");
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
                    System.out.println(curr.orang + " tidak cukup gold untuk membeli " + weaponToBuy.namasenjata + ".");
                }
            }else{
                System.out.println("Senjata dengan id " + idsenjata + " tidak terdapat pada toko.");
                return;
            }
            
        }else{
            System.out.println("Player tidak ditemukan");
            return;
        }
    }

    public void buyarmor(SenjataShop shop){
        int idarmor;
        Character curr = head;
        if(curr != null){
            Armor Armortobuy;
            shop.loadrmor();
            shop.displayarmor();
            System.out.println("Mau yang mana armornya? :");
            idarmor = input.nextInt();
            Armortobuy = shop.getArmor(idarmor);

            //cek apakah sudah punya armor yang sama
            Armor check = curr.armorplayer;
            while(check != null){
                if(check.id == idarmor){
                    System.out.println(curr.orang + " sudah memiliki armor '" + check.namaarmor + "' !\nAnda tidak bisa membeli item yang sama");
                    return;
                }
                check = check.next;
            }
            
            //proses pembelian
            if(Armortobuy != null){
                if(curr.gold >= Armortobuy.cost){
                    curr.gold -= Armortobuy.cost;
                    Armor beli = new Armor(Armortobuy.namaarmor, Armortobuy.physicaldefense, Armortobuy.magicdefense, Armortobuy.cost, Armortobuy.id);
                    System.out.println(curr.orang + " membeli " + Armortobuy.namaarmor + " seharga " + Armortobuy.cost + " gold.");
                    System.out.println("Gold " + curr.orang + " tersisa " + curr.gold + " gold.");
                    if(curr.armorplayer == null){
                        curr.armorplayer = beli;
                    }else{
                        Armor current = curr.armorplayer;
                        while(current.next != null){
                            current = current.next;
                        }
                        current.next = beli;
                    }
                }else{
                    System.out.println(curr.orang + " tidak cukup gold untuk membeli " + Armortobuy.namaarmor + ".");
                }
            }else{
                System.out.println("Armor dengan id " + idarmor + " tidak terdapat pada toko.");
                return;
            }
            
        }else{
            System.out.println("Player tidak ditemukan");
            return;
        }
    }

    public void sellweapon(){
        int choice;
        Character curr = head;
        if(curr != null){
            Weapon weaponChar = curr.weapon;

            while(weaponChar != null){
                System.out.println(weaponChar.namasenjata + " - " + weaponChar.cost + " - " + weaponChar.id);
                weaponChar = weaponChar.next;
            }

            weaponChar = curr.weapon;

            System.out.println("Ingin menjual senjata apa :");
            choice = input.nextInt();
            if(weaponChar.id == choice){
                curr.gold += weaponChar.cost;
                System.out.println(weaponChar.namasenjata + " - " + weaponChar.cost + " Dijual");
                curr.weapon = weaponChar.next;
                return;
            }

            while(weaponChar.next != null && weaponChar.next.id != choice){
                weaponChar = weaponChar.next;
            }
            if(weaponChar.next == null){
                System.out.println("Senjata dengan id " + choice + " tidak ada");
                return;
            }else{
                Weapon toSell = weaponChar.next;
                weaponChar.next = weaponChar.next.next;
                curr.gold += toSell.cost;
                System.out.println(toSell.namasenjata + " - " + toSell.cost + " Dijual");
            }
        }
    }

    public void sellArmor(){
        int choice;
        Character curr = head;
        if(curr != null){
            Armor armorChar = curr.armorplayer;

            while(armorChar != null){
                System.out.println(armorChar.namaarmor + " - " + armorChar.cost + " - " + armorChar.id);
                armorChar = armorChar.next;
            }

            armorChar = curr.armorplayer;
            System.out.println("Ingin menjual armor mana : ");
            choice = input.nextInt();
                if(armorChar.id == choice){
                    curr.gold += armorChar.cost;
                    System.out.println(armorChar.namaarmor + " - " + armorChar.cost + " Dijual");
                    curr.armorplayer = armorChar.next;
                    return;
                }

                while(armorChar.next != null && armorChar.next.id != choice){
                    armorChar = armorChar.next;
                }
                if(armorChar.next == null){
                    System.out.println("Armor dengan id " + choice + " tidak ada");
                    return;
                }else{
                    Armor toSell = armorChar.next;
                    armorChar.next = armorChar.next.next;
                    curr.gold += toSell.cost;
                    System.out.println(toSell.namaarmor + " - " + toSell.cost + " Dijual");
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
