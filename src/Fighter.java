public class Fighter {
    String orang;
    int health;
    Fighter next;
    Fighter prev;
    Weapon weapon;
    public Fighter(String orang, int health) {
        this.orang = orang;
        this.health = health;
        this.weapon = null;
        this.next = null;
        this.prev = null;
    }
}
