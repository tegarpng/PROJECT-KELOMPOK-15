public class TempWeaponStack {
    private TempWeaponStack next;
    private Weapon weapon;

    public TempWeaponStack() {
        this.next = null;
        this.weapon = null;
    }

    private TempWeaponStack(Weapon weapon, TempWeaponStack next) {
        this.weapon = weapon;
        this.next = next;
    }

    public void push(Weapon weapon) {
        this.next = new TempWeaponStack(weapon, this.next);
    }

    public Weapon pop() {
        if (this.next == null) {
            return null;
        }
        Weapon poppedWeapon = this.next.weapon;
        this.next = this.next.next;
        return poppedWeapon;
    }

    public boolean isEmpty() {
        return this.next == null;
    }
}