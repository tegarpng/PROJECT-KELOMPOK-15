import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        ManagePlayer player = new ManagePlayer();
        Scanner input = new Scanner(System.in);

        
        player.addplayer("Aria", "Fighter");

        player.displayfighter();

        manageBoss boss = new manageBoss();
        boss.addboss("Goblin King", 1200, 30, 10);
        boss.displayboss();

        SenjataShop senjata = new SenjataShop();
        senjata.addweapon("Sword of Light", 25, 0, 100);
        senjata.addweapon("Staff of Wisdom", 0, 30, 150);

        senjata.display();
        player.buyweapon("Aria","Sword of Light", senjata);

        player.displayfighter();
    }
}
