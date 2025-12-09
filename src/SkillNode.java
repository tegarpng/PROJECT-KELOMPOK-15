public class SkillNode {
    String name;
    String description;
    int damage;
    int level; // <--- ERROR HILANG KARENA INI DITAMBAHKAN
    
    // Pointer Tree (Pilihan Upgrade)
    SkillNode left;
    SkillNode right;
    
    // Pointer Linked List (Skill Berikutnya)
    SkillNode next;

    public SkillNode(String name, String desc, int damage, int level) {
        this.name = name;
        this.description = desc;
        this.damage = damage;
        this.level = level; // <--- Disimpan di sini
        this.left = null;
        this.right = null;
        this.next = null;
    }

    public void setBranches(SkillNode leftOpt, SkillNode rightOpt) {
        this.left = leftOpt;
        this.right = rightOpt;
    }

    public boolean hasUpgrade() {
        return (left != null && right != null);
    }
}