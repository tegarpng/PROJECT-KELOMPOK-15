public class SkillTreeGen {

    // Method Utama: Mengisi Skill List berdasarkan Role
    public SkillList generateSkillForRole(String role) {
        SkillList playerSkills = new SkillList();

        if (role.equalsIgnoreCase("Fighter")) {
            playerSkills.addSkill(buildSaberTree1()); // Sword
            playerSkills.addSkill(buildSaberTree2()); // Fist
        } 
        else if (role.equalsIgnoreCase("Magic")) {
            playerSkills.addSkill(buildCasterTree1()); // Fire
            playerSkills.addSkill(buildCasterTree2()); // Ice
        }
        else if (role.equalsIgnoreCase("Archer")) {
            playerSkills.addSkill(buildArcherTree1());  // Sharpshooter
            playerSkills.addSkill(buildArcherTree2());  // Hunter
        }
        return playerSkills;
    }

    // ==========================================
    // BUILDER FIGHTER (SABER)
    // ==========================================
    private SkillNode buildSaberTree1() {
        SkillNode root = new SkillNode("Mana Burst", "Ledakan mana", 800, 1);
        SkillNode air = new SkillNode("Invisible Air", "Pedang angin", 1800, 2);
        SkillNode clarent = new SkillNode("Clarent", "Pedang listrik", 1850, 2);
        root.setBranches(air, clarent);

        SkillNode excal = new SkillNode("Excalibur", "Sinar suci", 3500, 3);
        // Avalon kini memberikan damage (Holy Counter)
        SkillNode avalon = new SkillNode("Avalon", "Counter suci", 3400, 3); 
        air.setBranches(excal, avalon);
        
        SkillNode morgan = new SkillNode("Excalibur Morgan", "Tebasan gelap", 3500, 3);
        SkillNode rhongo = new SkillNode("Rhongomyniad", "Tombak cahaya", 3400, 3);
        clarent.setBranches(morgan, rhongo);

        excal.setBranches(new SkillNode("Last Phantasm", "Cahaya bintang", 6400, 4), new SkillNode("Star of Caliburn", "Pedang raja", 6400, 4));
        // Update Avalon upgrades
        avalon.setBranches(new SkillNode("Everdistant Utopia", "Refleksi cahaya", 6400, 4), new SkillNode("Dream World", "Ledakan mimpi", 6400, 4));
        morgan.setBranches(new SkillNode("Vortigern", "Naga hitam", 6400, 4), new SkillNode("Dark Caliburn", "Ledakan hitam", 6400, 4));
        rhongo.setBranches(new SkillNode("Tower of End", "Pilar kiamat", 6400, 4), new SkillNode("Lion King", "Penghakiman", 6400, 4));
        return root;
    }

    private SkillNode buildSaberTree2() {
        SkillNode root = new SkillNode("Mind's Eye", "Serangan titik lemah", 700, 1);
        SkillNode tsubame = new SkillNode("Tsubame Gaeshi", "3 tebasan", 1600, 2);
        SkillNode shukuchi = new SkillNode("Reduced Earth", "Tebasan kilat", 1700, 2);
        root.setBranches(tsubame, shukuchi);

        SkillNode sandan = new SkillNode("Sandanzuki", "Tusukan", 3000, 3);
        SkillNode regend = new SkillNode("Regend Slash", "Tebasan layang", 3200, 3);
        tsubame.setBranches(sandan, regend);
        
        SkillNode ishana = new SkillNode("Ishana Daitenshou", "Energi murni", 3000, 3);
        SkillNode voidShiki = new SkillNode("Mystic Eyes", "Mata kematian", 3200, 3);
        shukuchi.setBranches(ishana, voidShiki);

        sandan.setBranches(new SkillNode("Mumyo Sandanzuki", "Sure Kill", 6200, 4), new SkillNode("Infinity Strike", "Tanpa henti", 6200, 4));
        // Update Temple Guard
        regend.setBranches(new SkillNode("Swallow Killer", "Anti-Air", 6200, 4), new SkillNode("Temple Guard", "Counter Slash", 6200, 4));
        ishana.setBranches(new SkillNode("Niten Ichi Ryu", "Dua pedang", 6200, 4), new SkillNode("Fifth Force", "4 tangan", 6200, 4));
        voidShiki.setBranches(new SkillNode("Root of Akasha", "Hapus eksistensi", 6200, 4), new SkillNode("Boundary", "Potong ruang", 6200, 4));
        return root;
    }

    // ==========================================
    // BUILDER ARCHER
    // ==========================================
    private SkillNode buildArcherTree1() {
        SkillNode root = new SkillNode("Trace On", "Proyeksi Pedang", 800, 1);
        SkillNode calad = new SkillNode("Caladbolg", "Panah bor", 1800, 2);
        // Rho Aias update damage
        SkillNode rho = new SkillNode("Rho Aias", "Hantaman Perisai", 1850, 2);
        root.setBranches(calad, rho);

        SkillNode ubw = new SkillNode("Unlimited Blade Works", "Reality Marble", 3500, 3);
        SkillNode hrunting = new SkillNode("Hrunting", "Panah pelacak", 3400, 3);
        calad.setBranches(ubw, hrunting);
        
        SkillNode bell = new SkillNode("Bellerophon", "Pegasus", 3500, 3);
        // Raw Aias update damage
        SkillNode raw = new SkillNode("Raw Aias", "Pantulan Shield", 3400, 3);
        rho.setBranches(bell, raw);

        ubw.setBranches(new SkillNode("Infinite Swords", "Hujan pedang", 6400, 4), new SkillNode("Kanshou Bakuya", "Tebasan fatal", 6400, 4));
        hrunting.setBranches(new SkillNode("Broken Phantasm", "Ledakan nuklir", 6400, 4), new SkillNode("Nine Lives", "9 serangan", 6400, 4));
        bell.setBranches(new SkillNode("Triple Link", "Kombinasi", 6400, 4), new SkillNode("Gorgon Breaker", "Membatukan", 6400, 4));
        // Update Ultimate Defense & Mana Tank
        raw.setBranches(new SkillNode("Ultimate Defense", "Ledakan Perisai", 6400, 4), new SkillNode("Mana Tank", "Mana Burst", 6400, 4));
        return root;
    }

    private SkillNode buildArcherTree2() {
        SkillNode root = new SkillNode("Golden Rule", "Lempar Emas", 700, 1); // Ubah jadi damage kecil
        SkillNode gate = new SkillNode("Gate of Babylon", "Hujan emas", 1600, 2);
        SkillNode phoebus = new SkillNode("Phoebus Catastrophe", "Hujan panah", 1700, 2);
        root.setBranches(gate, phoebus);

        SkillNode enuma = new SkillNode("Enuma Elish", "Ea", 3000, 3);
        SkillNode chain = new SkillNode("Enkidu Chain", "Rantai", 3200, 3);
        gate.setBranches(enuma, chain);
        
        SkillNode agrius = new SkillNode("Agrius Metamorphosis", "Monster", 3000, 3);
        SkillNode stella = new SkillNode("Stella", "Meteor", 3200, 3);
        phoebus.setBranches(agrius, stella);
        
        enuma.setBranches(new SkillNode("Star of Creation", "Reset dunia", 6200, 4), new SkillNode("Sword of Rupture", "Hancur armor", 6200, 4));
        chain.setBranches(new SkillNode("Gate Spam", "1000 senjata", 6200, 4), new SkillNode("Vimana", "Pesawat", 6200, 4));
        agrius.setBranches(new SkillNode("Tauropolos Skia", "Panah gelap", 6200, 4), new SkillNode("Beast Mode", "Speed Max", 6200, 4));
        stella.setBranches(new SkillNode("Lone Meteor", "Area Map", 6200, 4), new SkillNode("Star Breaker", "Hapus target", 6200, 4));
        return root;
    }

    // ==========================================
    // BUILDER CASTER
    // ==========================================
    private SkillNode buildCasterTree1() {
        SkillNode root = new SkillNode("Divine Words", "Sinar Laser", 800, 1);
        SkillNode rain = new SkillNode("Rain of Light", "Laser hujan", 1800, 2);
        SkillNode rule = new SkillNode("Rule Breaker", "Tusukan Magic", 1850, 2);
        root.setBranches(rain, rule);
        
        SkillNode hecatic = new SkillNode("Hecatic Graea", "Bombardir", 3500, 3);
        SkillNode domus = new SkillNode("Domus Aurea", "Theater Api", 3400, 3);
        rain.setBranches(hecatic, domus);
        
        // Pain Breaker update damage
        SkillNode pain = new SkillNode("Pain Breaker", "Magic Drain", 3500, 3);
        SkillNode argon = new SkillNode("Argon Coin", "Summon naga", 3400, 3);
        rule.setBranches(pain, argon);
        
        // Update Caster1 Lv 4 Skills
        hecatic.setBranches(new SkillNode("Age of Gods", "Sihir kuno", 6400, 4), new SkillNode("Infinite Mana", "Mana Overload", 6400, 4));
        domus.setBranches(new SkillNode("Fax Caelestis", "Matahari", 6400, 4), new SkillNode("Laus St. Claudius", "Api Neraka", 6400, 4));
        pain.setBranches(new SkillNode("Panacea", "Racun Suci", 6400, 4), new SkillNode("Time Reversal", "Kejutan Waktu", 6400, 4));
        argon.setBranches(new SkillNode("Golden Fleece", "Colchis Dragon", 6400, 4), new SkillNode("Witchcraft", "Kutukan Darah", 6400, 4));
        return root;
    }
    
    private SkillNode buildCasterTree2() {
        SkillNode root = new SkillNode("Territory Creation", "Jebakan Magic", 700, 1);
        // Hero Creation & Illusion update damage
        SkillNode hero = new SkillNode("Hero Creation", "Buff Strike", 1600, 2);
        SkillNode illus = new SkillNode("Illusion", "Serangan Mental", 1700, 2);
        root.setBranches(hero, illus);
        
        // Garden & Dreamlike update damage
        SkillNode garden = new SkillNode("Garden of Avalon", "Bunga Berduri", 3000, 3);
        SkillNode ars = new SkillNode("Ars Almadel", "Laser Pembakar", 3200, 3);
        hero.setBranches(garden, ars);
        
        SkillNode fou = new SkillNode("Fou-kun Kick", "Maskot Kick", 3000, 3);
        SkillNode dream = new SkillNode("Dreamlike", "Mimpi Buruk", 3200, 3);
        illus.setBranches(fou, dream);
        
        // Update Caster2 Lv 4 Skills
        garden.setBranches(new SkillNode("Hope of Humanity", "Ledakan Cahaya", 6200, 4), new SkillNode("Eternal Tower", "Himpitan Menara", 6200, 4));
        ars.setBranches(new SkillNode("Ring of Solomon", "Pembatal Sihir", 6200, 4), new SkillNode("Goetia", "72 Demon God", 6200, 4));
        fou.setBranches(new SkillNode("Primate Murder", "Beast IV", 6200, 4), new SkillNode("Cath Palug", "Cakaran Beast", 6200, 4));
        dream.setBranches(new SkillNode("Kingmaker", "Titah Raja", 6200, 4), new SkillNode("Incubus", "Hisap Nyawa", 6200, 4));
        return root;
    }
}