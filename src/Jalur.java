public class Jalur {
    Lokasi tujuan;
    int jarak;          // weight
    boolean teleport;   // apakah jalur teleport
    Jalur next;

    public Jalur(Lokasi tujuan, int jarak, boolean teleport) {
        this.tujuan = tujuan;
        this.jarak = jarak;
        this.teleport = teleport;
        this.next = null;
    }
}
