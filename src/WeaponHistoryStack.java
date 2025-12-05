public class WeaponHistoryStack {
    private WeaponHistoryStack next;
    private Weapon weapon;
    private Character character;

    public WeaponHistoryStack() {
        this.next = null;
        this.weapon = null;
        this.character = null;
    }

    private WeaponHistoryStack(Weapon weapon, Character character, WeaponHistoryStack next) {
        this.weapon = weapon;
        this.character = character;
        this.next = next;
    }

    public void push(Weapon weapon, Character character) {
        this.next = new WeaponHistoryStack(weapon, character, this.next);
    }

    public Weapon pop() {
        if (this.next == null) {
            return null;
        }
        Weapon poppedWeapon = this.next.weapon;
        this.next = this.next.next;
        return poppedWeapon;
    }

    public Weapon peek() {
        if (this.next == null) {
            return null;
        }
        return this.next.weapon;
    }

    public boolean isEmpty() {
        return this.next == null;
    }

    public void display() {
        WeaponHistoryStack current = this.next;
        while (current != null) {
            System.out.println(current.character.orang + " menggunakan " + current.weapon.namasenjata);
            current = current.next;
        }
    }
}