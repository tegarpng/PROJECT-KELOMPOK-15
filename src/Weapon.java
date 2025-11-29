public class Weapon {
    String namasenjata;
    int damage;
    Weapon next;
    Weapon prev;
    public Weapon(String namasenjata, int damage) {
        this.namasenjata = namasenjata;
        this.damage = damage;
        this.next = null;
        this.prev = null;
    }
}
