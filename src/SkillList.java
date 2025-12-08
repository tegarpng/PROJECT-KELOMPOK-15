import java.util.Scanner;

public class SkillList {
    SkillNode head; 
    Scanner input = new Scanner(System.in);

    public SkillList() {
        this.head = null;
    }

    public void addSkill(SkillNode newSkill) {
        if (head == null) {
            head = newSkill;
        } else {
            SkillNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newSkill;
        }
    }

    public void displaySkills() {
        System.out.println("=== STATUS NOBLE PHANTASM (SKILL) ===");
        SkillNode current = head;
        int i = 1;
        while (current != null) {
            System.out.println("SLOT " + i + ": [" + current.name + "] (Lv." + current.level + ")");
            System.out.println("   > Power: " + current.damage + " | Desc: " + current.description);
            current = current.next;
            i++;
        }
        System.out.println("=====================================");
    }

    public void upgradeMenu() {
        System.out.println("\n[!] QUEST COMPLETED: SKILL ASCENSION AVAILABLE [!]");
        System.out.println("Pilih Skill yang ingin di-upgrade:");

        SkillNode current = head;
        int index = 1;
        int countAvailable = 0;

        while (current != null) {
            String status = current.hasUpgrade() ? "[READY UPGRADE]" : "[MAX LEVEL]";
            System.out.println(index + ". " + current.name + " (Lv." + current.level + ") " + status);
            if (current.hasUpgrade()) countAvailable++;
            current = current.next;
            index++;
        }

        if (countAvailable == 0) {
            System.out.println("Semua skill sudah Max Level.");
            return;
        }

        SkillNode selected = null;
        while (selected == null) {
            System.out.print("Pilih nomor slot: ");
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                selected = getSkillAt(choice);
                if (selected == null || !selected.hasUpgrade()) {
                    System.out.println("Slot tidak valid atau sudah Max. Coba lagi.");
                    selected = null;
                }
            } else {
                input.next(); // Clear invalid input
            }
        }

        System.out.println("\n>>> ASCENSION PROCESS: " + selected.name);
        System.out.println("1. [A] " + selected.left.name + " (Pwr: " + selected.left.damage + ")");
        System.out.println("   Efek: " + selected.left.description);
        System.out.println("2. [B] " + selected.right.name + " (Pwr: " + selected.right.damage + ")");
        System.out.println("   Efek: " + selected.right.description);

        int branch = 0;
        while (branch != 1 && branch != 2) {
            System.out.print("Pilihanmu (1/2): ");
            if (input.hasNextInt()) branch = input.nextInt();
            else input.next();
        }

        SkillNode nextStage = (branch == 1) ? selected.left : selected.right;
        
        System.out.println(">> SUCCESS! Skill berevolusi menjadi: " + nextStage.name);
        
        // Update Skill Node saat ini
        selected.name = nextStage.name;
        selected.description = nextStage.description;
        selected.damage = nextStage.damage;
        selected.level = nextStage.level;
        selected.left = nextStage.left;
        selected.right = nextStage.right;
    }

    private SkillNode getSkillAt(int index) {
        SkillNode current = head;
        int count = 1;
        while (current != null) {
            if (count == index) return current;
            count++;
            current = current.next;
        }
        return null;
    }
}