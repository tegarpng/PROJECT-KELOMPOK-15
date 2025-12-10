import java.util.Scanner;

public class WeaponStackManager {
    Character character;
    Weapon tempsenjata; // Stack history untuk Weapon
    Armor temparmor;   // Stack history untuk Armor
    Scanner scanner = new Scanner(System.in);

    public WeaponStackManager(Character character) {
        this.character = character;
    }

    // =======================================================
    // PUSH ke Stack (History)
    // =======================================================

    // PUSH Weapon ke tempsenjata stack
    public void push(Weapon senjata) {
        if (senjata == null) return;
        // buat salinan supaya stack terpisah dari linked list utama
        Weapon copy = new Weapon(senjata.namasenjata, senjata.role, senjata.physicaldamage, senjata.magicpower, senjata.cost, senjata.id);
        copy.next = tempsenjata;
        tempsenjata = copy;
    }

    // PUSH Armor ke temparmor stack
    public void push(Armor armor) {
        if (armor == null) return;
        // buat salinan supaya stack terpisah dari linked list utama
        Armor copy = new Armor(armor.namaarmor, armor.physicaldefense, armor.magicdefense, armor.cost, armor.id);
        copy.next = temparmor;
        temparmor = copy;
    }

    // =======================================================
    // POP dari Stack (Undo)
    // =======================================================
    
    // POP Weapon (mengambil dan menghapus elemen teratas dari stack senjata)
    public Weapon popWeapon() {
        if (tempsenjata != null) {
            Weapon top = tempsenjata;
            tempsenjata = tempsenjata.next;
            top.next = null;
            return top;
        }
        return null;
    }
    
    // POP Armor (mengambil dan menghapus elemen teratas dari stack armor)
    public Armor popArmor() {
        if (temparmor != null) {
            Armor top = temparmor;
            temparmor = temparmor.next;
            top.next = null;
            return top;
        }
        return null;
    }

    public boolean isWeaponHistoryEmpty(){
        return tempsenjata == null;
    }
    
    public boolean isArmorHistoryEmpty(){
        return temparmor == null;
    }

    // =======================================================
    // OPERASI EQUIP
    // =======================================================

    // EQUIP WEAPON (Logika sama, hanya perbaikan nama pop)
    public void equipWeapon() {
        if (character == null || character.weapon == null) {
            System.out.println("No weapons available to equip!");
            return;
        }

        System.out.println("\n========== EQUIP WEAPON ==========");
        System.out.println("Currently equipped: " + character.weapon.namasenjata);
        System.out.println("\nAvailable weapons to equip:");

        Weapon cursor = character.weapon.next;
        int idx = 1;
        if (cursor == null) {
            System.out.println("No other weapons available.");
            System.out.println("==================================\n");
            return;
        }
        while (cursor != null) {
            System.out.println(idx + ". " + cursor.namasenjata + " (Phys: " + cursor.physicaldamage + " - Mag: " + cursor.magicpower + ")");
            cursor = cursor.next;
            idx++;
        }

        System.out.print("Choose weapon number: (0 to cancel) ");
        int choice = scanner.nextInt();
        
        if (choice == 0) {
            System.out.println("Weapon change cancelled.");
            return;
        }
        
        if (choice < 1 || choice >= idx) {
            System.out.println("Invalid choice.");
            return;
        }

        // Temukan selected node
        Weapon selected = character.weapon.next;
        int count = 1;
        while (selected != null && count < choice) {
            selected = selected.next;
            count++;
        }
        if (selected == null) {
            System.out.println("Weapon not found.");
            return;
        }

        // 1. Buat salinan dari head SAAT INI untuk HISTORY (PUSH ke stack)
        Weapon headCopy = new Weapon(character.weapon.namasenjata, character.weapon.role, character.weapon.physicaldamage, character.weapon.magicpower, character.weapon.cost, character.weapon.id);

        // 2. Swap data antara head dan selected
        String tmpName = character.weapon.namasenjata;
        String tmpRole = character.weapon.role;
        int tmpPhys = character.weapon.physicaldamage;
        int tmpMag = character.weapon.magicpower;
        int tmpCost = character.weapon.cost;
        int tmpId = character.weapon.id;

        character.weapon.namasenjata = selected.namasenjata;
        character.weapon.role = selected.role;
        character.weapon.physicaldamage = selected.physicaldamage;
        character.weapon.magicpower = selected.magicpower;
        character.weapon.cost = selected.cost;
        character.weapon.id = selected.id;

        selected.namasenjata = tmpName;
        selected.role = tmpRole;
        selected.physicaldamage = tmpPhys;
        selected.magicpower = tmpMag;
        selected.cost = tmpCost;
        selected.id = tmpId;

        // 3. PUSH headCopy (old weapon) ke history
        push(headCopy);

        System.out.println(character.orang + " successfully equipped: " + character.weapon.namasenjata);
    }
    
    // EQUIP ARMOR (Perbaikan pada logika pembatalan input)
    public void equipArmor() {
        if (character == null || character.armorplayer == null) {
            System.out.println("No armors available to equip!");
            return;
        }

        System.out.println("\n========== EQUIP ARMOR ==========");
        System.out.println("Currently equipped: " + character.armorplayer.namaarmor);
        System.out.println("\nAvailable armor to equip:");

        Armor cursor = character.armorplayer.next;
        int idx = 1;
        if (cursor == null) {
            System.out.println("No other armor available.");
            System.out.println("==================================\n");
            return;
        }
        while (cursor != null) {
            System.out.println(idx + ". " + cursor.namaarmor + " (Phys Def: " + cursor.physicaldefense + " - Mag Def: " + cursor.magicdefense + ")");
            cursor = cursor.next;
            idx++;
        }

        System.out.print("Choose armor number: (0 to cancel) ");
        int choice = scanner.nextInt();
        
        if (choice == 0) {
            System.out.println("Armor change cancelled.");
            return;
        }

        if (choice < 1 || choice >= idx) {
            System.out.println("Invalid choice.");
            return;
        }

        // Temukan selected node
        Armor selected = character.armorplayer.next;
        int count = 1;
        while (selected != null && count < choice) {
            selected = selected.next;
            count++;
        }
        if (selected == null) {
            System.out.println("Armor not found.");
            return;
        }

        // 1. Buat salinan dari head SAAT INI untuk HISTORY (PUSH ke stack)
        Armor headCopy = new Armor(character.armorplayer.namaarmor, character.armorplayer.physicaldefense, character.armorplayer.magicdefense, character.armorplayer.cost, character.armorplayer.id);

        // 2. Swap data antara head dan selected
        String tmpName = character.armorplayer.namaarmor;
        int tmpPhys = character.armorplayer.physicaldefense;
        int tmpMag = character.armorplayer.magicdefense;
        int tmpCost = character.armorplayer.cost;
        int tmpId = character.armorplayer.id;

        character.armorplayer.namaarmor = selected.namaarmor;
        character.armorplayer.physicaldefense = selected.physicaldefense;
        character.armorplayer.magicdefense = selected.magicdefense;
        character.armorplayer.cost = selected.cost;
        character.armorplayer.id = selected.id;

        selected.namaarmor = tmpName;
        selected.physicaldefense = tmpPhys;
        selected.magicdefense = tmpMag;
        selected.cost = tmpCost;
        selected.id = tmpId;

        // 3. PUSH headCopy (old armor) ke history
        push(headCopy);

        System.out.println(character.orang + " successfully equipped: " + character.armorplayer.namaarmor);
    }
    
    // =======================================================
    // FUNGSI UNDO (menggunakan POP)
    // =======================================================
    
    /**
     * Mengembalikan senjata karakter ke senjata yang terakhir kali diganti (Undo).
     * Senjata yang saat ini terpasang akan di-push kembali ke history.
     */
    public void undoEquipWeapon() {
        if (isWeaponHistoryEmpty()) {
            System.out.println("No weapon history available to undo.");
            return;
        }

        // 1. POP senjata terakhir yang diganti dari stack (History/Undo)
        Weapon weaponToUndo = popWeapon(); // Ini adalah data Pedang A
        Weapon currentlyEquipped = character.weapon; // Ini adalah node Head (Pistol B)

        // 2. Simpan data Pistol B (yang akan dicopot) untuk history dan inventaris
        Weapon oldEquippedData = new Weapon(
            currentlyEquipped.namasenjata, 
            currentlyEquipped.role, 
            currentlyEquipped.physicaldamage, 
            currentlyEquipped.magicpower, 
            currentlyEquipped.cost, 
            currentlyEquipped.id
        );

        // 3. OVERWRITE data di Head node dengan data dari Pedang A (POP'd item)
        currentlyEquipped.namasenjata = weaponToUndo.namasenjata;
        currentlyEquipped.role = weaponToUndo.role;
        currentlyEquipped.physicaldamage = weaponToUndo.physicaldamage;
        currentlyEquipped.magicpower = weaponToUndo.magicpower;
        currentlyEquipped.cost = weaponToUndo.cost;
        currentlyEquipped.id = weaponToUndo.id;

        // 4. PUSH data Pistol B (oldEquippedData) ke history
        push(oldEquippedData);

        // 5. Overwrite node kedua (yang sebelumnya duplikat Pedang A) 
        // dengan data Pistol B (item yang baru saja dicopot).
        if (currentlyEquipped.next != null) {
            Weapon secondNode = currentlyEquipped.next;
            
            secondNode.namasenjata = oldEquippedData.namasenjata;
            secondNode.role = oldEquippedData.role;
            secondNode.physicaldamage = oldEquippedData.physicaldamage;
            secondNode.magicpower = oldEquippedData.magicpower;
            secondNode.cost = oldEquippedData.cost;
            secondNode.id = oldEquippedData.id;
        }

        System.out.println("Undo successful! " + character.orang + " now equips: " + character.weapon.namasenjata);
    }
    
    /**
     * Mengembalikan armor karakter ke armor yang terakhir kali diganti (Undo).
     * Armor yang saat ini terpasang akan di-push kembali ke history.
     */
    public void undoEquipArmor() {
        if (isArmorHistoryEmpty()) {
            System.out.println("No armor history available to undo.");
            return;
        }

        // 1. POP armor terakhir yang diganti dari stack (History/Undo)
        Armor armorToUndo = popArmor(); 
        Armor currentlyEquipped = character.armorplayer;

        // 2. Simpan data Armor B (yang akan dicopot) untuk history dan inventaris
        Armor oldEquippedData = new Armor(
            currentlyEquipped.namaarmor, 
            currentlyEquipped.physicaldefense, 
            currentlyEquipped.magicdefense, 
            currentlyEquipped.cost, 
            currentlyEquipped.id
        );

        // 3. OVERWRITE data di Head node dengan data dari Armor A (POP'd item)
        currentlyEquipped.namaarmor = armorToUndo.namaarmor;
        currentlyEquipped.physicaldefense = armorToUndo.physicaldefense;
        currentlyEquipped.magicdefense = armorToUndo.magicdefense;
        currentlyEquipped.cost = armorToUndo.cost;
        currentlyEquipped.id = armorToUndo.id;

        // 4. PUSH data Armor B (oldEquippedData) ke history
        push(oldEquippedData);

        // 5. Overwrite node kedua (yang sebelumnya duplikat Armor A) 
        // dengan data Armor B (item yang baru saja dicopot).
        if (currentlyEquipped.next != null) {
            Armor secondNode = (Armor)currentlyEquipped.next; // Cast necessary if next is defined as Object/general type
            
            secondNode.namaarmor = oldEquippedData.namaarmor;
            secondNode.physicaldefense = oldEquippedData.physicaldefense;
            secondNode.magicdefense = oldEquippedData.magicdefense;
            secondNode.cost = oldEquippedData.cost;
            secondNode.id = oldEquippedData.id;
        }

        System.out.println("Undo successful! " + character.orang + " now equips: " + character.armorplayer.namaarmor);
    }

    // =======================================================
    // Tampilkan Riwayat Stack
    // =======================================================

    // Tampilkan riwayat stack Weapon
    public void showWeaponHistory() {
        if (tempsenjata == null) {
            System.out.println("Weapon history is empty.");
            return;
        }

        System.out.println("\n--- Weapon History (Stack/Undo) ---");
        Weapon temp = tempsenjata;
        int count = 1;
        while (temp != null) {
            System.out.println(count + ". Sebelumnya menggunakan " + temp.namasenjata);
            temp = temp.next;
            count++;
        }
        System.out.println("-----------------------------------\n");
    }

    // Tampilkan riwayat stack Armor (Baru ditambahkan)
    public void showArmorHistory() {
        if (temparmor == null) {
            System.out.println("Armor history is empty.");
            return;
        }

        System.out.println("\n--- Armor History (Stack/Undo) ---");
        Armor temp = temparmor;
        int count = 1;
        while (temp != null) {
            System.out.println(count + ". Sebelumnya menggunakan " + temp.namaarmor);
            temp = temp.next;
            count++;
        }
        System.out.println("----------------------------------\n");
    }
}