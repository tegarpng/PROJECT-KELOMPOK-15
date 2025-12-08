public class SkillTreeGen {

    public SkillList generateSkill(String role) {
        SkillList list = new SkillList();
        
        if (role.equalsIgnoreCase("Fighter") || role.equalsIgnoreCase("Saber") || role.equalsIgnoreCase("Tank")) {
            list.addSkill(buildSaberTree1());
            list.addSkill(buildSaberTree2());
        } 
        else if (role.equalsIgnoreCase("Archer")) {
            list.addSkill(buildArcherTree1());
            list.addSkill(buildArcherTree2());
        } 
        else if (role.equalsIgnoreCase("Magic") || role.equalsIgnoreCase("Caster")) {
            list.addSkill(buildCasterTree1());
            list.addSkill(buildCasterTree2());
        }
        return list;
    }

    // --- SABER TREE ---
    private SkillNode buildSaberTree1() {
        SkillNode root = new SkillNode("Mana Burst", "Ledakan mana", 20, 1);
        SkillNode air = new SkillNode("Invisible Air", "Pedang angin", 40, 2);
        SkillNode clarent = new SkillNode("Clarent", "Pedang listrik", 45, 2);
        root.setBranches(air, clarent);

        SkillNode excal = new SkillNode("Excalibur", "Sinar suci", 90, 3);
        SkillNode avalon = new SkillNode("Avalon", "Heal abadi", 0, 3);
        air.setBranches(excal, avalon);
        
        SkillNode morgan = new SkillNode("Excalibur Morgan", "Tebasan gelap", 100, 3);
        SkillNode rhongo = new SkillNode("Rhongomyniad", "Tombak cahaya", 95, 3);
        clarent.setBranches(morgan, rhongo);

        excal.setBranches(new SkillNode("Last Phantasm", "Cahaya bintang", 200, 4), new SkillNode("Star of Caliburn", "Pedang raja", 180, 4));
        avalon.setBranches(new SkillNode("Everdistant Utopia", "Kebal total", 0, 4), new SkillNode("Dream World", "Buff All Stat", 50, 4));
        morgan.setBranches(new SkillNode("Vortigern", "Naga hitam", 210, 4), new SkillNode("Dark Caliburn", "Ledakan hitam", 190, 4));
        rhongo.setBranches(new SkillNode("Tower of End", "Pilar kiamat", 205, 4), new SkillNode("Lion King", "Penghakiman", 200, 4));
        return root;
    }

    private SkillNode buildSaberTree2() {
        SkillNode root = new SkillNode("Mind's Eye", "Melihat gerakan", 15, 1);
        SkillNode tsubame = new SkillNode("Tsubame Gaeshi", "3 tebasan", 50, 2);
        SkillNode shukuchi = new SkillNode("Reduced Earth", "Teleport", 30, 2);
        root.setBranches(tsubame, shukuchi);

        SkillNode sandan = new SkillNode("Sandanzuki", "Tusukan", 90, 3);
        SkillNode regend = new SkillNode("Regend Slash", "Tebasan layang", 85, 3);
        tsubame.setBranches(sandan, regend);
        
        SkillNode ishana = new SkillNode("Ishana Daitenshou", "Energi murni", 100, 3);
        SkillNode voidShiki = new SkillNode("Mystic Eyes", "Mata kematian", 120, 3);
        shukuchi.setBranches(ishana, voidShiki);

        sandan.setBranches(new SkillNode("Mumyo Sandanzuki", "Sure Kill", 250, 4), new SkillNode("Infinity Strike", "Tanpa henti", 220, 4));
        regend.setBranches(new SkillNode("Swallow Killer", "Anti-Air", 200, 4), new SkillNode("Temple Guard", "Defense Perfect", 0, 4));
        ishana.setBranches(new SkillNode("Niten Ichi Ryu", "Dua pedang", 230, 4), new SkillNode("Fifth Force", "4 tangan", 240, 4));
        voidShiki.setBranches(new SkillNode("Root of Akasha", "Hapus eksistensi", 999, 4), new SkillNode("Boundary", "Potong ruang", 300, 4));
        return root;
    }

    // --- ARCHER TREE ---
    private SkillNode buildArcherTree1() {
        SkillNode root = new SkillNode("Trace On", "Proyeksi", 20, 1);
        SkillNode calad = new SkillNode("Caladbolg", "Panah bor", 55, 2);
        SkillNode rho = new SkillNode("Rho Aias", "Perisai bunga", 0, 2);
        root.setBranches(calad, rho);

        SkillNode ubw = new SkillNode("Unlimited Blade Works", "Reality Marble", 110, 3);
        SkillNode hrunting = new SkillNode("Hrunting", "Panah pelacak", 90, 3);
        calad.setBranches(ubw, hrunting);
        
        SkillNode bell = new SkillNode("Bellerophon", "Pegasus", 100, 3);
        SkillNode raw = new SkillNode("Raw Aias", "Defense", 0, 3);
        rho.setBranches(bell, raw);

        ubw.setBranches(new SkillNode("Infinite Swords", "Hujan pedang", 250, 4), new SkillNode("Kanshou Bakuya", "Tebasan fatal", 220, 4));
        hrunting.setBranches(new SkillNode("Broken Phantasm", "Ledakan nuklir", 240, 4), new SkillNode("Nine Lives", "9 serangan", 230, 4));
        bell.setBranches(new SkillNode("Triple Link", "Kombinasi", 180, 4), new SkillNode("Gorgon Breaker", "Membatukan", 170, 4));
        raw.setBranches(new SkillNode("Ultimate Defense", "Kebal Magic", 0, 4), new SkillNode("Mana Tank", "Regen Mana", 0, 4));
        return root;
    }

    private SkillNode buildArcherTree2() {
        SkillNode root = new SkillNode("Golden Rule", "Drop rate", 10, 1);
        SkillNode gate = new SkillNode("Gate of Babylon", "Hujan emas", 60, 2);
        SkillNode phoebus = new SkillNode("Phoebus Catastrophe", "Hujan panah", 55, 2);
        root.setBranches(gate, phoebus);

        SkillNode enuma = new SkillNode("Enuma Elish", "Ea", 150, 3);
        SkillNode chain = new SkillNode("Enkidu Chain", "Rantai", 40, 3);
        gate.setBranches(enuma, chain);
        
        SkillNode agrius = new SkillNode("Agrius Metamorphosis", "Monster", 120, 3);
        SkillNode stella = new SkillNode("Stella", "Meteor", 300, 3);
        phoebus.setBranches(agrius, stella);
        
        enuma.setBranches(new SkillNode("Star of Creation", "Reset dunia", 500, 4), new SkillNode("Sword of Rupture", "Hancur armor", 350, 4));
        chain.setBranches(new SkillNode("Gate Spam", "1000 senjata", 280, 4), new SkillNode("Vimana", "Pesawat", 250, 4));
        agrius.setBranches(new SkillNode("Tauropolos Skia", "Panah gelap", 200, 4), new SkillNode("Beast Mode", "Speed Max", 220, 4));
        stella.setBranches(new SkillNode("Lone Meteor", "Area Map", 700, 4), new SkillNode("Star Breaker", "Hapus target", 600, 4));
        return root;
    }

    // --- CASTER TREE ---
    private SkillNode buildCasterTree1() {
        SkillNode root = new SkillNode("Divine Words", "Cast speed", 25, 1);
        SkillNode rain = new SkillNode("Rain of Light", "Laser hujan", 55, 2);
        SkillNode rule = new SkillNode("Rule Breaker", "Hapus buff", 35, 2);
        root.setBranches(rain, rule);
        
        SkillNode hecatic = new SkillNode("Hecatic Graea", "Bombardir", 110, 3);
        SkillNode domus = new SkillNode("Domus Aurea", "Theater", 90, 3);
        rain.setBranches(hecatic, domus);
        
        SkillNode pain = new SkillNode("Pain Breaker", "Heal status", 0, 3);
        SkillNode argon = new SkillNode("Argon Coin", "Summon naga", 100, 3);
        rule.setBranches(pain, argon);
        
        hecatic.setBranches(new SkillNode("Age of Gods", "Sihir kuno", 250, 4), new SkillNode("Infinite Mana", "Mana", 0, 4));
        domus.setBranches(new SkillNode("Fax Caelestis", "Matahari", 280, 4), new SkillNode("Laus St. Claudius", "Api", 240, 4));
        pain.setBranches(new SkillNode("Panacea", "Sembuh total", 0, 4), new SkillNode("Time Reversal", "Reset waktu", 0, 4));
        argon.setBranches(new SkillNode("Golden Fleece", "Colchis Dragon", 300, 4), new SkillNode("Witchcraft", "Kontrol", 0, 4));
        return root;
    }
    
    private SkillNode buildCasterTree2() {
        SkillNode root = new SkillNode("Territory Creation", "Magic Passive", 15, 1);
        SkillNode hero = new SkillNode("Hero Creation", "Buff Dmg", 0, 2);
        SkillNode illus = new SkillNode("Illusion", "Invincible", 0, 2);
        root.setBranches(hero, illus);
        
        SkillNode garden = new SkillNode("Garden of Avalon", "Heal", 0, 3);
        SkillNode ars = new SkillNode("Ars Almadel", "Laser", 350, 3);
        hero.setBranches(garden, ars);
        
        SkillNode fou = new SkillNode("Fou-kun Kick", "Maskot", 120, 3);
        SkillNode dream = new SkillNode("Dreamlike", "Isi mana", 0, 3);
        illus.setBranches(fou, dream);
        
        garden.setBranches(new SkillNode("Hope of Humanity", "Charge NP", 0, 4), new SkillNode("Eternal Tower", "Kurungan", 0, 4));
        ars.setBranches(new SkillNode("Ring of Solomon", "No Magic", 0, 4), new SkillNode("Goetia", "72 Demon God", 600, 4));
        fou.setBranches(new SkillNode("Primate Murder", "Beast IV", 450, 4), new SkillNode("Cath Palug", "Reset CD", 0, 4));
        dream.setBranches(new SkillNode("Kingmaker", "Buff ATK 200%", 0, 4), new SkillNode("Incubus", "Tidur", 0, 4));
        return root;
    }
}