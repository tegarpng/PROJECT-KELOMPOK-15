public class WeaponStackManager {
    private Character character;
    private WeaponHistoryStack weaponHistory;
    private TempWeaponStack tempWeapons;
    
    public WeaponStackManager(Character character) {
        this.character = character;
        this.weaponHistory = new WeaponHistoryStack();
        this.tempWeapons = new TempWeaponStack();
    }
    
    public void equipWeapon(Weapon newWeapon) {
        if (character.weapon != null) {
            tempWeapons.push(character.weapon);
        }
        character.weapon = newWeapon;
        weaponHistory.push(newWeapon, character);
    }
    
    public void switchToPreviousWeapon() {
        if (!weaponHistory.isEmpty()) {
            Weapon currentWeapon = weaponHistory.pop();
            if (currentWeapon != null) {
                tempWeapons.push(currentWeapon);
            }
            character.weapon = weaponHistory.peek();
        }
    }
    
    public void restoreTempWeapon() {
        if (!tempWeapons.isEmpty()) {
            Weapon tempWeapon = tempWeapons.pop();
            if (tempWeapon != null) {
                equipWeapon(tempWeapon);
            }
        }
    }
    
    public void showWeaponHistory() {
        weaponHistory.display();
    }
}