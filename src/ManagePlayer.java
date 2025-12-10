import java.util.Scanner;
public class ManagePlayer {
    Scanner input = new Scanner(System.in);
    Character head;
    SenjataShop shop;
    WeaponStackManager weaponStackManager;
    PetaGame currentLocation;

    public void addplayer(String nama, String Role){
        Character fighter;
        if(Role.equals("Magic")){
            fighter = new Character(nama, 1000, Role, 0, 100, 0, 0);
        }
        else if(Role.equals("Fighter")){
            fighter = new Character(nama, 1300, Role, 100, 0, 0, 0);
        }
        else if(Role.equals("Archer")){
            fighter = new Character(nama, 950, Role, 50, 50, 0, 0);
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
        Character curr = head;
        if(curr == null){
            System.out.println("Player tidak ditemukan");
            return;
        }
        
        boolean shoppingWeapon = true;
        while(shoppingWeapon) {
            System.out.println("\n++=============================================++");
            System.out.println("||        TOKO SENJATA (Role: " + String.format("%-7s", curr.role) + ")      ||");
            System.out.println("|| ------------------------------------------- ||");
            System.out.println("||  GOLD ANDA SAAT INI : " + String.format("%-21d", curr.gold) + " ||");
            System.out.println("++=============================================++");            
            if(curr.role.equals("Fighter")) shop.displayweapon("Fighter");
            else if(curr.role.equals("Magic")) shop.displayweapon("Magic");
            else if(curr.role.equals("Archer")) shop.displayweapon("Archer");
            else { 
                System.out.println("Role error."); 
                return; 
            }

            System.out.println("++---------------------------------------------++");
            System.out.println("|| OPSI SORTING:                               ||");
            System.out.println("|| [91] Harga Termurah   [92] Harga Termahal   ||");
            System.out.println("|| [93] Damage Terbesar  [94] Magic Terbesar   ||");
            System.out.println("||---------------------------------------------||");
            System.out.println("|| KETIK ID BARANG UNTUK MEMBELI               ||");
            System.out.println("|| KETIK 0 UNTUK KEMBALI                       ||");
            System.out.println("++=============================================++");
            System.out.print("Pilihan Anda >> ");
            
            int inputPilihan = input.nextInt();

            if (inputPilihan == 0) {
                shoppingWeapon = false;
            }
            else if (inputPilihan == 91) {
                shop.sortWeapon(curr.role, 1);
                System.out.println(">> List diurutkan: Harga Termurah.");
            }
            else if (inputPilihan == 92) {
                shop.sortWeapon(curr.role, 2);
                System.out.println(">> List diurutkan: Harga Termahal.");
            }
            else if (inputPilihan == 93) {
                shop.sortWeapon(curr.role, 3);
                System.out.println(">> List diurutkan: Physical Damage Terbesar.");
            }
            else if (inputPilihan == 94) {
                shop.sortWeapon(curr.role, 4);
                System.out.println(">> List diurutkan: Magic Power Terbesar.");
            }
            else {
                int idsenjata = inputPilihan;
                Weapon weaponToBuy = null;
                
                if(curr.role.equals("Fighter")) weaponToBuy = shop.getweaponfighter(idsenjata);
                else if(curr.role.equals("Magic")) weaponToBuy = shop.getweaponmagic(idsenjata);
                else if(curr.role.equals("Archer")) weaponToBuy = shop.getweaponarcher(idsenjata);

                if(weaponToBuy != null) {
                    boolean punya = false;
                    Weapon check = curr.weapon;
                    while(check != null){
                        if(check.id == idsenjata){
                            System.out.println(">> GAGAL: Anda sudah memiliki " + check.namasenjata + "!");
                            punya = true;
                            break;
                        }
                        check = check.next;
                    }
                    
                    if(!punya){
                        if(curr.gold >= weaponToBuy.cost){
                            curr.gold -= weaponToBuy.cost;
                            
                            Weapon beli = new Weapon(weaponToBuy.namasenjata, weaponToBuy.role, 
                                                     weaponToBuy.physicaldamage, weaponToBuy.magicpower, 
                                                     weaponToBuy.cost, weaponToBuy.id);
                            
                            if(curr.weapon == null){
                                curr.weapon = beli;
                            } else {
                                Weapon current = curr.weapon;
                                while(current.next != null){
                                    current = current.next;
                                }
                                current.next = beli;
                            }
                            System.out.println(">> SUKSES: Membeli " + weaponToBuy.namasenjata + "!");
                        } else {
                            System.out.println(">> GAGAL: Gold tidak cukup! (Kurang " + (weaponToBuy.cost - curr.gold) + ")");
                        }
                    }
                } else {
                    System.out.println(">> ERROR: Item dengan ID " + idsenjata + " tidak ditemukan.");
                }
                
                try { Thread.sleep(1500); } catch(Exception e){}
            }
        }
    }

    public void buyarmor(SenjataShop shop){
        Character curr = head;
        if(curr == null){
            System.out.println("Player tidak ditemukan");
            return;
        }

        boolean shoppingArmor = true;
        while(shoppingArmor) {
            System.out.println("\n++=============================================++");
            System.out.println("||              TOKO ARMOR (UMUM)              ||");
            System.out.println("|| ------------------------------------------- ||");
            System.out.println("||  GOLD ANDA SAAT INI : " + String.format("%-21d", curr.gold) + " ||");
            System.out.println("++=============================================++");

            shop.displayarmor(); 

            System.out.println("++---------------------------------------------++");
            System.out.println("|| OPSI SORTING:                               ||");
            System.out.println("|| [91] Harga Termurah   [92] Harga Termahal   ||");
            System.out.println("|| [93] Phys Def Besar   [94] Magic Def Besar  ||");
            System.out.println("||---------------------------------------------||");
            System.out.println("|| KETIK ID BARANG UNTUK MEMBELI               ||");
            System.out.println("|| KETIK 0 UNTUK KEMBALI                       ||");
            System.out.println("++=============================================++");
            System.out.print("Pilihan Anda >> ");

            int inputPilihan = 0;
            try {
                inputPilihan = input.nextInt();
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                input.nextLine(); 
                continue;
            }

            if (inputPilihan == 0) {
                shoppingArmor = false;
            } 
            else if (inputPilihan == 91) {
                shop.sortArmor(1);
                System.out.println(">> List Armor diurutkan: Harga Termurah.");
            }
            else if (inputPilihan == 92) {
                shop.sortArmor(2);
                System.out.println(">> List Armor diurutkan: Harga Termahal.");
            }
            else if (inputPilihan == 93) {
                shop.sortArmor(3);
                System.out.println(">> List Armor diurutkan: Physical Defense Terbesar.");
            }
            else if (inputPilihan == 94) {
                shop.sortArmor(4);
                System.out.println(">> List Armor diurutkan: Magic Defense Terbesar.");
            }
            else {
                int idarmor = inputPilihan;
                Armor armorToBuy = shop.getArmor(idarmor);

                if (armorToBuy != null) {
                    boolean punya = false;
                    Armor check = curr.armorplayer;
                    while(check != null){
                        if(check.id == idarmor){
                            System.out.println(">> GAGAL: Anda sudah memiliki " + check.namaarmor + "!");
                            punya = true;
                            break;
                        }
                        check = check.next;
                    }

                    if (!punya) {
                        if (curr.gold >= armorToBuy.cost) {
                            curr.gold -= armorToBuy.cost;
                            
                            Armor beli = new Armor(armorToBuy.namaarmor, armorToBuy.physicaldefense, 
                                                   armorToBuy.magicdefense, armorToBuy.cost, armorToBuy.id);
                            
                            if (curr.armorplayer == null) {
                                curr.armorplayer = beli;
                            } else {
                                Armor current = curr.armorplayer;
                                while (current.next != null) {
                                    current = current.next;
                                }
                                current.next = beli;
                            }
                            System.out.println(">> SUKSES: Membeli " + armorToBuy.namaarmor + "!");
                        } else {
                            System.out.println(">> GAGAL: Gold tidak cukup! (Kurang " + (armorToBuy.cost - curr.gold) + ")");
                        }
                    }
                } else {
                    System.out.println(">> ERROR: Armor dengan ID " + idarmor + " tidak ditemukan.");
                }
                
                try { Thread.sleep(1000); } catch(Exception e){}
            }
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
                System.out.println("  >> Total Physical Damage : " + (curr.physicaldamage + curr.weapon.physicaldamage));
                System.out.println("  >> Total Magic Power     : " + (curr.magicpower + curr.weapon.magicpower));
                System.out.println("  >> Total Physical Defense : " + (curr.physicaldefense));
                System.out.println("  >> Total Magic Defense    : " + (curr.magicdefense));
            }


            System.out.println("-------------------------------------------");
            curr = curr.next;
        }


        Scanner sc = new Scanner(System.in);
        SearchItem si = new SearchItem();

        while(true){
            System.out.println("\n=== MENU PENCARIAN ITEM ===");
            System.out.println("1. Pencarian Weapon");
            System.out.println("2. Pencarian Armor");
            System.out.println("0. Kembali");
            System.out.print(">> Pilih: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            if(pilih == 0){
                break; 
            }

            switch(pilih){
                case 1:
                    while(true){
                        System.out.println("\n--- PENCARIAN WEAPON ---");
                        System.out.println("1. Cari berdasarkan Nama");
                        System.out.println("2. Cari berdasarkan Physical Damage");
                        System.out.println("3. Cari berdasarkan Magic Power");
                        System.out.println("4. Cari berdasarkan Cost");
                        System.out.println("5. Cari berdasarkan ID");  
                        System.out.println("0. Kembali");
                        System.out.print(">> Pilih: ");
                        int w = sc.nextInt();
                        sc.nextLine();
                        
                        if(w == 0) break;
                        switch(w){
                            case 1:
                                System.out.print("Nama Weapon: ");
                                si.searchWeaponByName(head.weapon, sc.nextLine());
                                break;
                            case 2:
                                System.out.print("Physical Damage: ");
                                si.searchWeaponByPhysicalDamage(head.weapon, sc.nextInt());
                                break;
                            case 3:
                                System.out.print("Magic Power: ");
                                si.searchWeaponByMagicPower(head.weapon, sc.nextInt());
                                break;
                            case 4:
                                System.out.print("Cost Weapon: ");
                                si.searchWeaponByCost(head.weapon, sc.nextInt());
                                break;
                            case 5: 
                                System.out.print("ID Weapon: ");
                                si.searchWeaponById(head.weapon, sc.nextInt());
                                break;
                            default:
                                System.out.println("Kembali...");
                        }
                    }
                    break;
                case 2:
                    while(true){
                        System.out.println("\n--- PENCARIAN ARMOR ---");
                        System.out.println("1. Cari berdasarkan Nama");
                        System.out.println("2. Cari berdasarkan Physical Defense");
                        System.out.println("3. Cari berdasarkan Magic Defense");
                        System.out.println("4. Cari berdasarkan Cost");
                        System.out.println("0. Kembali");
                        System.out.print(">> Pilih: ");
                        int a = sc.nextInt();
                        sc.nextLine();

                        if(a == 0) break;
                        switch(a){
                            case 1:
                                System.out.print("Nama Armor: ");
                                si.searchArmorByName(head.armorplayer, sc.nextLine());
                                break;
                            case 2:
                                System.out.print("Physical Defense: ");
                                si.searchArmorByPhysicalDefense(head.armorplayer, sc.nextInt());
                                break;
                            case 3:
                                System.out.print("Magic Defense: ");
                                si.searchArmorByMagicDefense(head.armorplayer, sc.nextInt());
                                break;
                            case 4:
                                System.out.print("Cost Armor: ");
                                si.searchArmorByCost(head.armorplayer, sc.nextInt());
                                break;
                            default:
                                System.out.println("Kembali...");
                        }
                    }
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    public void manageEquipment() {
        Character curr = head;
        if (curr == null) {
            System.out.println("Player tidak ditemukan.");
            return;
        }

        boolean managing = true;
        while (managing) {
            System.out.println("\n========== MANAGE EQUIPMENT ==========");
            System.out.println("Karakter saat ini: " + curr.orang);
            System.out.println("Weapon: " + (curr.weapon != null ? curr.weapon.namasenjata : "None"));
            System.out.println("Armor: " + (curr.armorplayer != null ? curr.armorplayer.namaarmor : "None"));
            System.out.println("--------------------------------------");
            System.out.println("1. Equip Weapon Baru");
            System.out.println("2. Undo Equip Weapon (Kembali ke senjata sebelumnya)");
            System.out.println("3. Tampilkan Weapon History");
            System.out.println("4. Equip Armor Baru");
            System.out.println("5. Undo Equip Armor (Kembali ke armor sebelumnya)");
            System.out.println("6. Tampilkan Armor History");
            System.out.println("0. Kembali ke Menu Explore");
            System.out.print("Pilih: ");
            int choice = input.nextInt();
            input.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    weaponStackManager.equipWeapon();
                    break;
                case 2:
                    weaponStackManager.undoEquipWeapon();
                    break;
                case 3:
                    weaponStackManager.showWeaponHistory();
                    break;
                case 4:
                    weaponStackManager.equipArmor();
                    break;
                case 5:
                    weaponStackManager.undoEquipArmor();
                    break;
                case 6:
                    weaponStackManager.showArmorHistory();
                    break;
                case 0:
                    managing = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    public void openShopMenu(SenjataShop shop) {
        Character curr = head;
        if (curr == null) {
            System.out.println("Error: Player belum dibuat!");
            return;
        }

        boolean shopping = true;
        while (shopping) {
            System.out.println("\n++==========================================================++");
            System.out.println("||                      WEAPON & ARMOR SHOP                   ||");
            System.out.println("++==========================================================++");
            System.out.println("|| Halo, " + String.format("%-15s", curr.orang) + " Gold Anda: " + String.format("%-14d", curr.gold) + " ||");
            System.out.println("++==========================================================++");
            System.out.println("|| 1. Beli Senjata (Weapon)                                 ||");
            System.out.println("|| 2. Beli Armor                                            ||");
            System.out.println("|| 3. Jual Senjata                                          ||");
            System.out.println("|| 4. Jual Armor                                            ||");
            System.out.println("|| 5. Cek Inventory (Status)                                ||");
            System.out.println("|| 0. Keluar dari Shop                                      ||");
            System.out.println("++==========================================================++");
            System.out.print("Pilihan Anda >> ");
            
            int choice = -1;
            try {
                choice = Integer.parseInt(input.next());
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                input.nextLine(); 
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--- MEMBELI SENJATA ---");
                    buyweapon(shop);
                    break;
                case 2:
                    System.out.println("\n--- MEMBELI ARMOR ---");
                    buyarmor(shop);
                    break;
                case 3:
                    System.out.println("\n--- MENJUAL SENJATA ---");
                    sellweapon();
                    break;
                case 4:
                    System.out.println("\n--- MENJUAL ARMOR ---");
                    sellArmor();
                    break;
                case 5:
                    System.out.println("\n--- STATUS PEMAIN ---");
                    displayfighter();
                    break;
                case 0:
                    System.out.println("Terima kasih sudah berkunjung!");
                    shopping = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public void runAllQuestsAtCurrentLocation(PetaGame peta) {
        Character curr = head;
        if(curr == null) {
            System.out.println("Player tidak ditemukan!");
            return;
        }

        Lokasi currentLok = peta.getCurrentLocation();
        if(currentLok == null) {
            System.out.println("Lokasi tidak valid!");
            return;
        }

        System.out.println("\n=== QUEST DI " + currentLok.nama + " ===");
        currentLok.Quest.tampilkanQuestAktif();

        boolean doingQuests = true;
        while(doingQuests && !peta.areAllQuestsCompleted()) {
            System.out.println("\n[1] Jalankan Quest");
            System.out.println("[2] Lihat Progress");
            System.out.println("[0] Kembali");
            System.out.print("Pilihan: ");
            
            int choice = 0;
            try {
                choice = input.nextInt();
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                input.nextLine();
                continue;
            }

            if(choice == 1) {
                currentLok.Quest.prosesQuest(curr);
                peta.doquest(null);
                System.out.println("\nQuest diselesaikan!");
                try { Thread.sleep(1500); } catch(Exception e) {}
            }
            else if(choice == 2) {
                peta.showQuestProgress();
            }
            else if(choice == 0) {
                doingQuests = false;
            }
            else {
                System.out.println("Pilihan tidak valid!");
            }
        }

        if(peta.areAllQuestsCompleted()) {
            System.out.println("\n*** SEMUA QUEST DI " + currentLok.nama + " SELESAI! ***");
            System.out.println("Anda sekarang dapat berpindah ke lokasi lain.");
        }
    }

    public void exploreLocation(PetaGame peta) {
        Character curr = head;
        if(curr == null) {
            System.out.println("Player tidak ditemukan!");
            return;
        }

        boolean exploring = true;
        while(exploring) {
            Lokasi currentLok = peta.getCurrentLocation();
            System.out.println("\n=== LOKASI: " + currentLok.nama + " ===");
            
            System.out.println("\n[1] Jalankan Quest Lokasi");
            System.out.println("[2] Pindah Lokasi");
            System.out.println("[3] Lihat Status Pemain");
            System.out.println("[4] Manage Equipment");
            System.out.println("[5] Lihat Peta");
            System.out.println("[0] Kembali");
            System.out.print("Pilihan: ");
            
            int choice = 0;
            try {
                choice = input.nextInt();
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                input.nextLine();
                continue;
            }

            switch(choice) {
                case 1:
                    runAllQuestsAtCurrentLocation(peta);
                    break;
                case 2:
                    System.out.print("Pilih lokasi (atau 0 untuk batal): ");
                    String locName = "";
                    try {
                        locName = input.next();
                    } catch (Exception e) {
                        System.out.println("Input tidak valid!");
                        input.nextLine();
                        break;
                    }
                    
                    if(locName.equals("0")) {
                        System.out.println("Dibatalkan.");
                        break;
                    }
                    
                    peta.moveToLocation(locName);
                    break;
                case 3:
                    displayfighter();
                    break;
                case 4:
                    manageEquipment();
                    break;
                case 5:
                    peta.displayMap();
                    break;
                case 0:
                    exploring = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
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