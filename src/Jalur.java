public class Jalur {
    Lokasi tujuan;          // weight
    boolean teleport;   // apakah jalur teleport
    Jalur next;

    public Jalur(Lokasi tujuan, boolean teleport) {
        this.tujuan = tujuan;
        this.teleport = teleport;
        this.next = null;
    }
}
