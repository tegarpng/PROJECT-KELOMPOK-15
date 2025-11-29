public class ManagePlayer {
    Fighter head;
    SenjataPlayer weapon = null;
    public void addplayer(String nama){
        Fighter fighter = new Fighter(nama, 100);
        if(head == null){
            head = fighter;
        }else{
            Fighter curr = head;
            while(curr.next != null){
                curr = curr.next;
            }
            curr.next = fighter;
        }
    }

    public Fighter getPlayer(String nama){
        Fighter curr = head;
        while(curr != null){
            if(curr.orang.equals(nama)){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public void GiveWeapon(String namafighter, String namasenjata, int damage){
        Fighter fighter = getPlayer(namafighter);
        if(fighter != null){
            if(fighter.weapon == null){
                fighter.weapon = new Weapon(namasenjata, damage);
            }else{
                Weapon curr = fighter.weapon;
                while(curr.next != null){
                    curr = curr.next;
                }
                curr.next = new Weapon(namasenjata, damage);
            }
        }
    }
    public void displayfighter(){
        Fighter curr = head;
        while(curr != null){
            System.out.println(curr.orang + " HP : " + curr.health);
            System.out.println("Weapons:");
            Weapon weaponCurr = curr.weapon;
            while(weaponCurr != null){
                System.out.println("- " + weaponCurr.namasenjata + " Damage: " + weaponCurr.damage);
                weaponCurr = weaponCurr.next;
            }
            curr = curr.next;
        }
    }
}
