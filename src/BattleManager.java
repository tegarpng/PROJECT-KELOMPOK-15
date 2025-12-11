import java.util.Scanner;

public class BattleManager {
    Scanner input = new Scanner(System.in);
    
    private TurnStack turnStack;

    // Warna ANSI
    public static final String RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m"; 
    public static final String CYAN = "\u001B[36m";   
    public static final String RED = "\u001B[31m";    
    public static final String PURPLE = "\u001B[35m"; 

    public BattleManager() {
        this.turnStack = new TurnStack();
    }

    public boolean startBattle(Character player, Boss enemy) {
        turnStack.clear();
        prepareTurns(); 

        int currentBossHP = enemy.health;
        int maxBossHP = enemy.health;
        int turnCount = 1;
        String lastLog = "Boss " + enemy.namaboss + " Muncul!"; 

        while (player.health > 0 && currentBossHP > 0) {
            
            if (turnStack.isEmpty()) {
                prepareTurns();
                turnCount++;
            }

            String currentTurn = turnStack.pop();
            
            // Render UI (Gunakan Math.max agar HP tidak terlihat minus di layar)
            printUI(player, enemy, Math.max(0, currentBossHP), maxBossHP, turnCount, currentTurn, lastLog);

            if (currentTurn.equals("PLAYER")) {
                // === GILIRAN PLAYER ===
                System.out.println(centerText("PILIH AKSI", 90));
                System.out.println("                         1. " + YELLOW + "Skills" + RESET );
                System.out.println("                         2. " + YELLOW + "Attack" + RESET );
                System.out.println("==========================================================================================");
                System.out.print("Pilihan (1. Skill / 2. Attack): ");
                
                int aksi = 0;
                // Validasi input agar tidak crash jika user input huruf
                if(input.hasNextInt()) {
                    aksi = input.nextInt();
                } else {
                    input.next(); // Bersihkan buffer
                }

                int damageDealt = 0;

                switch(aksi) {
                    case 1: // SKILL
                        if (player.mySkills == null || player.mySkills.head == null) {
                            lastLog = "Skill tidak tersedia!";
                            turnStack.push("PLAYER"); // Ulang giliran
                        } else {
                            damageDealt = useSkillMenu(player);
                            if (damageDealt == -1) { 
                                turnStack.push("PLAYER"); // Batal, ulang giliran
                                continue; 
                            }
                            damageDealt = Math.max(0, damageDealt); 
                            currentBossHP -= damageDealt;
                            lastLog = "Skill digunakan! " + enemy.namaboss + " terkena " + damageDealt + " dmg.";
                        }
                        break;
                    
                    case 2: // BASIC ATTACK
                        if(player.role.equals("Fighter")){
                            int weaponDmg = (player.weapon != null) ? player.weapon.physicaldamage : 0;
                            damageDealt = player.physicaldamage + weaponDmg;
                            currentBossHP -= damageDealt;
                            lastLog = "Basic Attack!! " + enemy.namaboss + " terkena " + damageDealt + " dmg.";
                        }else if(player.role.equals("Magic")){
                            int weaponDmg = (player.weapon != null) ? player.weapon.magicpower : 0;
                            damageDealt = player.magicpower + weaponDmg;
                            currentBossHP -= damageDealt;
                            lastLog = "Basic Attack!! " + enemy.namaboss + " terkena " + damageDealt + " dmg.";
                        }else if(player.role.equals("Archer")){
                            int damagearcher = (player.weapon.physicaldamage + player.weapon.magicpower);
                            int basedamage = player.physicaldamage + player.magicpower;
                            int weaponDmg = (player.weapon != null) ? (damagearcher) : 0;
                            damageDealt = basedamage + weaponDmg;
                            currentBossHP -= damageDealt;
                            lastLog = "Basic Attack!! " + enemy.namaboss + " terkena " + damageDealt + " dmg.";
                        }
                        break;

                    default:
                        lastLog = "Aksi tidak valid.";
                        turnStack.push("PLAYER"); 
                        continue;
                }

            } else {
                // === GILIRAN BOSS (Logika Damage Spesifik) ===
                try { Thread.sleep(1500); } catch (Exception e) {}

                int bossDmg = 0;
                String attackName = "";
                
                // Hitung Defense Player
                int pPhysDef = (player.armorplayer == null) ? 0 : (int)((player.physicaldamage + player.armorplayer.physicaldefense) * 0.20);
                int pMagDef = (player.armorplayer == null) ? 0 : (int)((player.physicaldamage + player.armorplayer.physicaldefense) * 0.15);
                
                if (currentBossHP < (maxBossHP / 2)) {
                    // FASE 2: ULTIMATE (MAGIC)
                    bossDmg = enemy.magicpower - pMagDef;
                    attackName = "Ultimate Arts (Magic Type)";
                } else {
                    // FASE 1: SMASH (PHYSICAL)
                    bossDmg = enemy.physicaldamage - pPhysDef;
                    attackName = "Smash Attack (Physical Type)";
                }
    
                player.health -= bossDmg;
                System.out.println(">> " + enemy.namaboss + " menggunakan " + attackName + "!");
                System.out.println(">> Anda terkena " + bossDmg + " damage.");
            }
        }

        // Print hasil akhir
        printUI(player, enemy, Math.max(0, currentBossHP), maxBossHP, turnCount, "SELESAI", lastLog);
        
        if (player.health > 0) {
            System.out.println("\n" + YELLOW + "=== VICTORY! BOSS DEFEATED ===" + RESET);
            return true;
        } else {
            System.out.println("\n" + RED + "=== YOU DIED ===" + RESET);
            if(player.role.equals("Fighter")) player.health = 1300;
                else if(player.role.equals("Magic")) player.health = 1000;
                else player.health = 950;
            return false;
        }
    }

    private void prepareTurns() {
        turnStack.push("BOSS");
        turnStack.push("PLAYER");
    }

    private int useSkillMenu(Character player) {
        System.out.println("\n--- DAFTAR SKILL ---");
        SkillNode curr = player.mySkills.head;
        int idx = 1;
        while (curr != null) {
            System.out.println(idx + ". " + curr.name + " (Base Pwr: " + curr.damage + ")");
            curr = curr.next;
            idx++;
        }
        System.out.print("Pilih Skill (0 Batal): ");
        int choice = 0;
        if(input.hasNextInt()) choice = input.nextInt();
        
        if (choice == 0) return -1;

        curr = player.mySkills.head;
        int count = 1;
        while (curr != null) {
            if (count == choice) {
                int weaponMagic = (player.weapon != null) ? player.weapon.magicpower : 0;
                // Total Dmg = Magic Player + Magic Weapon + Base Skill Dmg
                return player.magicpower + weaponMagic + curr.damage;
            }
            count++;
            curr = curr.next;
        }
        return -1;
    }

    // --- VISUAL UI ---
    private void printUI(Character p, Boss b, int bhp, int maxBhp, int turn, String turnName, String log) {
        System.out.print("\033[H\033[2J");  
        System.out.flush();

        String border = "==========================================================================================";
        
        System.out.println(border);
        System.out.println(centerText("TURN " + turn, 90));
        System.out.println(border);
        
        String bossInfo = "BOSS: " + PURPLE + b.namaboss + RESET + " | HP: " + bhp + "/" + maxBhp + 
                          " | PHY: " + b.physicaldamage + " | MAG: " + b.magicpower;
        System.out.println(" " + bossInfo);
        
        // PANGGIL GAMBAR SESUAI NAMA BOSS
        printBossArt(b.namaboss);
        
        System.out.println(border);
        
        // --- PERBAIKAN CRASH JIKA WEAPON NULL ---
        int totalPhysDmg = p.physicaldamage + ((p.weapon != null) ? p.weapon.physicaldamage : 0);
        int totalMagicPwr = p.magicpower + ((p.weapon != null) ? p.weapon.magicpower : 0);
        
        System.out.println("| " + CYAN + p.orang + RESET + " | HP: " + p.health + " | ATK (Phys): " + totalPhysDmg + " | MAG: " + totalMagicPwr + " | Player Weapon : " + p.weapon.namasenjata);
        System.out.println(border);
        
        if (turnName.equals("PLAYER")) {
            System.out.println(centerText("GILIRAN: " + CYAN + p.orang + RESET, 90));
        } else if (turnName.equals("BOSS")) {
            System.out.println(centerText("GILIRAN: " + RED + b.namaboss + RESET, 90));
        } else {
            System.out.println(centerText("--- BATTLE SELESAI ---", 90));
        }
        
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("LOG: " + log);
        System.out.println("------------------------------------------------------------------------------------------");
    }

    // === LOGIKA GAMBAR BOSS DINAMIS ===
    private void printBossArt(String bossName) {
        String[] art = new String[]{};

        if (bossName.equalsIgnoreCase("Death Knight")) {
            art = new String[]{
                "                 qaaq                                                                               ",
                "              qa1UUU1av                                                                             ",
                "            kUUUUUU1166f                                                                            ",
                "           fUUPPPPU6faa6v                                                                           ",
                "          k1UPPPPPUU6Ua1v                                                                           ",
                "         v6UPPPKKKKKPPUU                                                                            ",
                "          a1PPPKK1vq1K1a                                                                            ",
                "          k6UUUPUfv f1v6v                                                                           ",
                "          f16UUP1f661KqUf                                                                           ",
                " v6a      61661UUUPKPKFP6                                                                           ",
                "   k1f   vvUK1666111UP16v      vk                                                                   ",
                "     k16q  fKKPPUUUaafaf      kk                                                                    ",
                "       k6k  fKFKKKKP61PKPU6qq6v    faq                                                              ",
                "        v6fa1KFKKP1fkfkUPKFFUvq6UUf                                                                 ",
                "      f6faa6666PPPUav vqaUPKPU1fv                                                                   ",
                "      k111U16ak6KKP11faf11PKKKFKPUU16k                                                              ",
                "      q6UUU11KUPKU11a1PF61FKKKFUkv  v1Ua                                                            ",
                "    va1U1161UU1PKU1PU1PPPFP1FKPf     qq6q                                                           ",
                "     aPUPPPUK6aPKUUKPUPPUFFFFF1v       k                                                            ",
                "     vf1U16f1UUUKPUPKKKFFAKPFFP1v     v                                                             ",
                "     q6UUfqf1UUU66PU11faKPq1KU66U6                                                                  ",
                "     f666qq66UKKKPPU1kqkP6kUK16111U1v               61UUU166aq                                      ",
                "    qUUUaqa111UPPPP1Uf6aUUf1KKP11U11UP1v        kPKPFFFFFFFFFFFFKKP6v                               ",
                "    aKUUUUP111PKKPPPKP1fPAFFKPKU1UPPUPKKKKKKPPKFFFKKKKKFFFFFFFFFFFFFFFKUv                           ",
                "   vaKPKFFKUUUPFFFFFKKFUFFFFKFFPUPPPPPKKKKKKKKKKKKKKKKKKKFFFFFFFFFFFFFFFFKf vPPKFa                  ",
                "   vqKKK16PUUUPFFFFFFFFFFFFPUKFKPPKKKKKKKKKKKKKKKKKKKKKKKKKFFFFFFFKKFFFFFFFFPv                      ",
                "    6PFKFKUUPPFFFKPKFFFKFAAFFFKKKPKKKKKKKKKPPKKKKKKKKKKFFFFFFFFFKFFKKKKFFFFFFFPv                    ",
                "     vUKFPPPPKFKKPPKKKFFFAFFFFFKKKKKKKKKKKKKKKKKKKKKKKKKKKKFFKFFFFFKKKKKFFFFFFFKk                   ",
                "    vq6KKPPPKFFKPKKKKKFFFAaFFFFKKKPPPPKKKKKKKKKKKKKKKKKKKFKKKKKFFFFKKKFKKKFKFFFFPk                  ",
                "     fPK6kUPFKKKKU11aUKUKKqKKFKKKKPUPPPPUKKKKKKKKKKKKKKKKKFKKFKKFFFKKKKKFKFKKFFFF6                  ",
                "     qUPa qKFPPFKKUkf1PqkKUaPPKFKPKPUKPPPUUUPPPPPPKKKKKKKKKKKKF  kKFFFFFFFFFKFFFFPq                 ",
                "    vUKUfqqKFKFKFFF6fqqafqfFKUa1UPU1U6U61fk1PPPPPPPPKKKKKKKKKK6 ak fKFFFFKFFFFK6 qq                 ",
                "     aKUfPfKAFFFFFFP6aqkqUFFFK1a1U1161ff6fvaU11UPKKPPPPKKKKKKPqf   q6FFFFKFFFFKKf                   ",
                "      11 akqfAAFKFFFF1f6PFKKPPKPPKP166fqffv aPKPKKKKKKKKKKKKP  a   q1k  aKFFFFFFF1vaUf              ",
                "      q1k   6FFFKKKKKFFFFUPKPPKFFFKKPUfkfafqvq6UPPKKKKPfkUKKKP           kKFFKKFFKFFFFFFKq          ",
                "         v  fAAFFFFPKFFKFFAKPPKKPPKKK11aq6aaafv vPKPPKKUv 1KKKKf         k   v 1FFFFFa    aa        ",
                "            fAFFFFKPPKFAAFKKKKKKKPU16P1a16aU16U6av6PU1Ua qUKKKP                  qP6        vq      ",
                "            fAFFKKPPKKFFPFFKKFKKKFKPUPPUUUU6fkaU1P6v  vq  6PPPk                                     ",
                "           kvAKKKFKPKKFFAAFKKFFFK61PPPUPPPKKP6a666KUU1k     k6U1k                                   ",
                "           k1FFFFAFKFFFAFFFFFFFKfqaaaffa61PKKKKKKUaaPPP1kv      f6   q1f            qkvk            ",
                "           aAFFFFFFKFFFFPFFFFFKF1v        faaa6UPUKPP11UUU1av     v                                 ",
                "           6FAAAAFKKFAFFFFFFFFFFPa              f6UPPPPPPPPUU116fv                                  ",
                "           qAFAAF1v6FFAAAFF1KFFFUv                  vkaPPPPUU116fv                                  ",
                "           6FFFAUv aFAAAAAafKKFFFFUv    kff          qfa61UPUUUU16fv                                ",
                "           FFFAFk  1FAAAFf k  6KFFKk       v              qka1UPUU6akq                              ",
                "          1AFFFKq qPAAAFU        kUPKav       v qvv           vaa11116aafq                          ",
                "          fFFAFU   1AAFFf    qfqkv     vq        1                kfa6111111fv                      ",
                "          fFFAKq   6AAAU      fKFU1q                                 vfa61UPPPUav                   ",
                "          kFAA1    fFAAa        qa  vfv                                  ka6UPKPPUaqv               ",
                "          vFFFk    fFAFq                                                    vkaUPKKKK1ak            ",
                "          vKAF     kKAK                                                         f6UKKFFK1aq         ",
                "         v KFK     qKAP                                                            k6PPKKKP1fv      ",
                "         qaFAU     kKFU k                                                             qa6UPKPUaq    ",
                "          1FAFq    aFAFKf                                                                  qa1UU6q  ",
                "          vKAK     6AAFFFk  f                                                                   qffv",
                "          vKAFv    UAAAAAFKPUq                                                                      ",
                "          1FAFU    kaafa11a66av                                                                     ",
                "           6KUv                                                                                     "
            };
        } 
        else if (bossName.equalsIgnoreCase("Euroboros")) {
            art = new String[]{
                "                                                                                                    ",
                "                                                 V                                                  ",
                "                   V                              SS                                                ",
                "                   P                               XSSSX                                            ",
                "                   S      X                         XXSPX                                           ",
                "                  VKN    SX                         XSSPPPX                                         ",
                "                  PHN    KX                           XPPPNNX                                       ",
                "                  XKN   SKV                             VSNNKKK                                     ",
                "                   NKV   KK                             VVVSKKKK                                    ",
                "                   NNP   NNX                           SXVVVVSKHKS                                  ",
                "                   PKPXXXKKN            X              NNXVXXVSNFHKS     SPPNFKSPK SX   S           ",
                "           XS      PKNVXVPKKV           V              KK XXXXXKFHHHHHFFFFFFHHFFHHFFFNSX XS   X     ",
                "         S VNX     VKKPVXVKKNXXX       VPV             PK  XXVVHNPFFFFFFFFFFFFFHHHHKHHHKKVVV   V    ",
                "    PSVNFHHKX XP   XKKPVVSNHKPXXX       PV             SHNXVKFFHFFFFFFFFFFHHKNNNNNNKHHHHHFKSPSX P   ",
                "  V     PN VNPNNV  VPHKVVVVHHKKPXX      NNVXP           SHHFFFFFFFFFFCCFFFHS       SPSPHKFFKPKHKP   ",
                "        SNV  PNN   VSHHKSVVVHKSSNSXXXX  SNNKHHHHPN   XKHHFFFFFFFFFCCCCCCFNP           XXVPKHFNKFK   ",
                "       XXX    SKNXVSVSKHPSSVSHKSVSSXXXVXSNNKKHHHFFFKPNFFFFFFHFFCCCCFCCCNPNNKV           SHPNKNKHHS  ",
                "             VNKFKPSSSSKFPSSPHKKVVVSPVVVVPNKKHHHFFFHHFFHHHHHFFFFCCFCCCKNNPKHHP            XHHKKFHN  ",
                "         XNKKKNVVHKPSPSSNFNSSSNHKSSVSVVSPNNKKHHFFCFHHHHFFFFHFFFCCCCCCFNNNNKKHHN          XVPNSSHHNKK",
                "     XNHHHKKNV    VKNSPPPKFHPSSNFHKKPSSSSSPKKKHFFFKNNNKKFFFFFFFFCCCCCCKHNKKHHHHK                 S  ",
                "    KHHHHNX         PKNPPPNFHKPSKFHNPNPSSPSNHHHFFFHKPSPPHCCCCCCCCCCCCCCPSKKHHHHHK P                 ",
                "  SKHFFX              KHNPPPKFKPPHCFFNPSPNHNPKHFFFFKNNHFFCCACCCCFCFFCCNVVSNKHHHHHKV                 ",
                "VXHFFK              XPPHFHPPNKFFHNHCFFHKPPNNNKKFFCFFHHHFFFHHCCCCCCCFFFN XVSNHHHHHHN                 ",
                "VHHFHN            PKNNNNNHFKPNKCFFHNKHFFFHNNNNKKFCFFHHFFFFFFFCFFKFFFHS     SNHHHHHHX                ",
                "  HHFHP        KKHKKKKKKKKHCFHNNKHCFFFHKFFFFFHKKKFCFH   NFFFFFHHKHHV        VKHHHHHHP               ",
                "   KFFFFFFFFFFHHHHHHHHHKS  VKHFFFHHHKHFFCHHHFCCCFHFFCHX     XPNNXX            PHHHHHKKV             ",
                "    VHFFFFFFFFHHFHFFKV        VSSPKHFFHHHHHFFFFFCCFFCCFK                       VHHHHKVNP            ",
                "        XPHHFFFHS            XSSSSXVXVSNFFFFCCFCCCCCCCCFFSS                     XHHHK   P           ",
                "                             PPPP      XPNHHFFCCCFFCCCCCFFHH                     NHHK               ",
                "                     SNNPV  PNPPP XVSSSSNKKHHFCKX VPPKFCCFHHH                     KKKV              ",
                "                          VKFFFHHKHKKHHHHHHFFFFKNS      VHHHHHS                   NPKNSX            ",
                "                            PPNNPNPSXVSVPKNNFCCCCCK       PKKHHKHHKN              NSPNKSS           ",
                "                                                           XS V V                                   "
            };
        } 
        else if (bossName.equalsIgnoreCase("Omen")) {
            art = new String[]{
                "                                         V X                                                        ",
                "                                       S                                        H                   ",
                "                                     A             V                                                ",
                "                                    CX          FAA S                             H                 ",
                "                                   X         NCCCHCP                             XA                 ",
                "                                  X         NCCFPNPKFA                           VAF                ",
                "                      X           X        CCNSSKKKSPFF                         XSAF                ",
                "                     VX            CP    FFFPSSVSHPVVKCH      AK              SP FAF                ",
                "                   X                AACFKHHSSS VPHPV SPCCVP FC                  FAAC                ",
                "                X                    VCCCFKVVVSPSNPSSXVSFFX CK                HAAAAH                ",
                "             P V                       HCCVVVVSVNSNXXXSVVCHN AA          XNCAAAACAC                 ",
                "           VN                  P   VAACCCHNNVVVVXXXXXXXPKCH   FCF      FAACAACCCAAP                 ",
                "           PXV               VHCCAAAP XS HKXX   XXXXXVVSFHSV  VFC S  CFCACCFH  X                    ",
                "          NP V          FHNCACHFPKC  XXXVHFKPH      XXPCCCVXX XNFFCNCCCCCCX                         ",
                "          VSX           HCHSFCHKFSV   SVPHFFFV       PFFPPXX  KFCCCCFCACCP   F       S              ",
                "                  X  K  XNFCKCPNKFHC    XSFFFCFV  VKFFHKVX   VKNSNFCCAAAAAACCCFV PHFC               ",
                "               X N  VKXVX HCNCFFFPCV   X  VKFFFFFHPPNX XS     VPHFCCCCACHACACAAACCCV                ",
                "              XSSKXXVHVVX VSCCCNHVKFPP      VXSXS V X     S HKSNHCFCACAHCAP                         ",
                "                 SN  XS KHNHKKNKSSVPP P                   KKHNPHFFNFCCCCPCA                         ",
                "                      KFKHFHFXSHFHKSX V              S NKHPPSXVVSNCCCFFHFCCAV                       ",
                "                       SX  NFFNFFFHKXVVPP VVV  PH PSKPSSSVVSSXVPKKFKFHFHFHCAAF                      ",
                "                         V KFCCKFKPHFVVVKSSVSHFFCH FFXSPNKSXNHFCHKPFCCKCCCFP                        ",
                "                       CCFHCCCCFFCCFCCFHPFCFSKKCCCFCNXNFFHVFCCCHHPKKHCCCCCCCCP                      ",
                "                      ACCAAAFFHFHCCFCCCFNVNKHHNFCCCFVNHHSSFCCCCCFCCAAAAACFKP                        ",
                "                     CCCACFFCAACACCCCCAFPHHPPHFKFCCNHPSHHNCCCCACCCCAACCCF                           ",
                "                        FCCFCCFCAACCKCACPCCFHPCKCCFCPKCCFCCCCCHCFCACFFCCCV         V    V   S       ",
                "      S   PPS  P      PAFCCCCCCCFAAFCPHSFHFACHHCACCKHCCACCFFKCCNCAACFCCCCCCN       P N  SKHSX       ",
                "       SSSP  K VSN VHCCCCCCCCCCCFFAFCCCACFCFFCKCACAHCACHPNNHFFKAAAACCCCCCCCCCCFNSXSS VH SNK         ",
                "        NNSNNS XXPHFFFCCCCCCCCFFFHFAFACCCCKFNCFFAACAAACPHFFFFNCAAACCCCCCCCCCCCCFPSSSSKVPVP          ",
                "          FPFPPVPPSHKFCCCAACCCFFFHCCACFFCCFNFFACAACCACKFHFKHFCCAAFFCCCCCCCCCCCHPPNSNSPVH            ",
                "           VPNVPSNPSNPFCCCACCCCCFCCFACCCHFKHHHFAAACCCFKCFKHCFCCAAFFCCCCCCCCCFNNSNNPSXVSCKKK         ",
                "         X HNPVV XSSV VPCCCCCCCCCCFCACFCFHHCFFCCCCCCCHCCHCFFCCFACHFFFCCCCCCVCXXVSSSXXVPPC     V     ",
                "           NNXPSPNNPVSSVCCCCCAACCCFCACHFFCFFCCCCCCCCFFCFKFCFCHFCCFFFFCCFFFKXXVVSNFKKHKPN      XV    ",
                "           V  NKKKNSVSSPCCFCCAACCCFACKCCCCCFCCCCCCCCCCCFCCCCCCFCAFHFFFFFHHS   VPNNSSKHV        K    ",
                "            XNKKHKNNNSSKHSCCCCACC  AFCACACCCFCCCCCCCCCFFCCCCCFHCAHHFFFFFHHP  SXSNKNNNHC        N    ",
                "          HKKNKKHKKKKNPPSKCCK   CV AAAACCCCCCCCCCCCCCCFCCCCCCCCCCC   NCFFHKVNVSNKKFHHHHC            ",
                "       KKKKKKKHCFFKHFHNPPNAS   NX XCCACCCCCCCCACCCCCCCCCCCCCCCFKCS  CK VFFFFKPNKKHFFFFKNNCF         ",
                "      KFKKKHHHHHHFKKFKSPKCN       AACAACCCCCCCCCCCCCCCCCCCCCCCCFCA  F     VACHHFHKHFFFNHHKH         ",
                "       HHKS     CKKFCCCCAF       XAAACAAACCCCCCCCCCCCCCCCFACCCCCCA  H       NAAFHKK    HHHHC        ",
                "      XCFN       SCX    X        AAAAAACCACCCCCCCCCCCCCCHCCCCCFCC              NCFHS     FHHHFFFFK  ",
                "       C          X             HACAACCCCCCCCCFFCCCCCCCCHCCCCFCCAV                 P       HHH    X ",
                "       FS                      HPAACCCCCCCFCCCCCACCCCCCCNCCCCCCFCCX                          XS    P",
                "                                XCACCCCCCCHCCCCCCCCCCCCFPFFFCFCCFCK                                 ",
                "                                AACCCCCCCCFCFCCCCCCCCCFFKKFFFFFCCFC                                 ",
                "                               ACCCCCCFFFHFCCCCCCCCCCFFHKNFFFFFCCCCS                                ",
                "                              CCACCCCFCFHPHFFCCCCCCCCFKPNPKFCFCCCFCF                                ",
                "                             HACACCCCFVNSXFFCCCCCCCFFFFPPPKFFFFCFFCCFP                              ",
                "                            AAACCCCFCSSFSVFCCCCCCCCFFFFPPPHFFFCCCCCCFCCK XX                         ",
                "                           CAACCCCCCPPNFNNCACCCCCCFFFFHPPPFPFFFKFCCCCCA                             ",
                "                          AACCCCCCFHPHKNPHCCCCCCCCFFFFHPPSHVHHFFFCCCCCC     P                       ",
                "                        HCACCCCCCCFFKKPNKCFCCCCFCFFFFHKVNPVXNKHKFFFCCCCF    NH                      ",
                "                      V ACCCCCCCFCNPPPPPCCCCCCFFFFFFFHKP X VNKXPHFFCCACAP   CV                      ",
                "                  S   NCCCCCCCCCFNNPPSPHHCCFFFFFFFFFHHP    NKXXKKFFCCCCCCN SN                       ",
                "                   NACACACCCCCFCHNNNPPVCCFCFFFFFFFFFFVN X  KKPPPHFCCCCCCCCHV       N                ",
                "                NACAACCCACCCCFCFKNNPPFPCCFFFFFFFFFFHHHXVVSVSVNVSPNFCCCCCCCAK      SCC               ",
                "                CCAACCCCCACCCHCHNNPPPNFFCCFFFFFFFHHFPVSPNPPSSNSNNHFCCCCCCCCCCFKKHCS                 ",
                "    ACAAACAAAAAACACCCCCCCCFCKKKFNPNPSHCCCFCCFFFFFFFPSPSPKKKPPPNKKKKCFCCCCCCCCCCCCK                  ",
                "  KN    ACAAACCCCCCCACCCCHNHKHKFKNPNPCCCCFFFFFFFFCKHNPPNNNHKNKNKKHKHFCCCCCCCCCCCCV                  ",
                " S       AACCCFCCAACCCCFNSPHNKFHKNFNNHCCCCFFFFFFCFFFPNNNKNKNNNFNKKKHFCACCCCCCCCCCK                  ",
                "S       CAACHHFFCCCCCCCCSSFSNCNKHCHKNFFCCCFFFFFFFCFCFNKNNHKKNKCNKNHFFCCCCCCCCCCCC                   ",
                "N      CFCHHKFFCAACAACCFHKPPKKKHKHKNNCCFCCFFFCCCCFCFCCFHHKNNNKPNPNHFFCCCCCCCCCCCA           FCCAV   ",
                "      CHFSNFCFCCCCCCCCCCNNNHNKHHKKKKCCFFFCFFCFFFFFCCCCHHHNPNKFHFPNKHCCCCCCCCCAAAACH   A     XCCH    ",
                "         HACACCCCCCCCCCCKKHKHHFFFFHFCCCCCFCCCCCCCCCFCCKFKHHNKFFFFFCFCCCCCCCCCACCACCCACCCCCCCCP      ",
                "        AAACCCCCCCCCAFCCCFCFKKKKFFFFCCCCCCCCCCCCCCCCFHKKHKKHKFACCFFCCCCCCCCCAAACCCCCCACF    V       ",
                "       N  NP  PCCCCCCFCCCCCFKNNKKKFFHCFCCCCCCCCCCCCFHHHHKKHKFFFCCCCFFCCCCCCCACCCCCCCCC              ",
                "               CFACCCFFCAACCKNNNFKHHKCCCCCCCCCCCCHHHFFFFKHHHFCCFHFCFHFCCCCCCCCCCACCCCCF             ",
                "                 FCCCCCCCCCKKNNKFKHKKCACCCCCCCCFFFFHHCCFCCFFCFHHHHCCCFCCCCCACCCCFCCCCCCF            ",
                "                XACCCCCFCCHHKFCCKFFCFKCCCCCCCCCFFFHFCCFCFHX KFFHHHCCACACCCCCCF   KCCCKHCVPACKPS     ",
                "           ASP NCCCK    XCFFFCFCHHHHKNHCCCCCCFFFFHFHFCFHK     CCFCAX XHCACAF                        ",
                "               XPS       NFFSNCXKHHKKKKCCCCCFFHHHHHFCCCFKK     CP      SCCH                         ",
                "                                  NN   FCCFHFFFF   KHK               HCCCCCC                        ",
                "                                        K    NP                                                     "
            };
        } 
        else if (bossName.equalsIgnoreCase("Chronos")) {
            art = new String[]{
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                   K                                                ",
                "                                                 PKKK                                               ",
                "                                             V SKKHHNK H                                            ",
                "                                            SCCKKFAKKNHAN                                           ",
                "                                           SAAFNHCFKKKKKFCP     PKS  C                              ",
                "                                        XKHFACAACACCCNNKKCFFFCP      VK                             ",
                "                             PX     XNHKKHHFFHHFCCCNNPNHFAPVSHKNHX    KX                            ",
                "                            K      HNVVHNKAANS PNCNNSVXXHCCHHPXXNNKKCP C                            ",
                "                           K    PNPPKCCAAAAAFPV HFHKN XNHCACAACCHFHKKKHFP                           ",
                "                          K   HKPHFAAAAAAAAAACCFFFCFFKHFCAAAAAACAACKKKHCFCFK                        ",
                "                          V KKNKKACAAAAAAACACCAFCFCFCCAAACAAAACCCAACKFCCHHKKHFCP                    ",
                "                         XPCCHKCAAAAAACACNACCACFACACACCCCCAAAACAAACAACHKKHKKCFFFFFCH                ",
                "                       SKHKNKFAAAAAAAKNHNSVXSNCAFHACFAACCHPSPFPSFAAAAAFKKHFKFFCCHKKKKHCK            ",
                "                      FFKHFCCCAAAAAAAAHCAACCHNPPSHCCCHNNPNKHFFAAAAAAAAAACCFFFCAAAACAP               ",
                "                   PHKFFACCAAAAAAAAAAAFAAAAAACCHFACFAAAAAAAAACAACACAAAAAACCAAAAAACN                 ",
                "                PHKKKKCAACCAAAAAAAAAAACKHHHCAAAAAACFAAAAAAAAAFNVFCCAAAAAAAFAAAAAAK                  ",
                "                  AAAAACACFACAAAAAAAAAHACAASVHAAAACHAAAAAAACKVFAAAACAAAAACHAACAAACACAK              ",
                "                   NAAAAACHCCCAACAACACAACACCFHKKKKFCCFHKHHKHFFCCCAAAACCAACFAAAAAAF                  ",
                "                   KAAAACCAFAAAAAAAAAAAACAACACCCAACAACCAAACFCCCAACAAAAACAHCAAAAAAV                  ",
                "                 PAAFAAAFAAFAAAAHHHAAAAAAAAAACFCAAAACAAACCFCAAAAAAAACP PCKAAAAAAAF                  ",
                "                     FCFAACCFAAH SHHAAAACAACAACCCFCCAACFFCACCACAAAAACK NFAAAAAAAAK                  ",
                "                      AAACAAHAAN HKHAAAAAAAACAAACFFFFFFCCCFFCAACAAAAAN  HCAAAAAAHP                  ",
                "                      FCCAAACFA  HNHACFCCFCAACCAAACFFCAACCCAAAAACNKKCK HVAAAAAFPSK                  ",
                "                      KNCAAAACN  NVSVHCCCCCFAAACAAAAAAAAAAAAACANPNKKPN S  AAAAHNKN                  ",
                "                      KKACCAAA  SNSPPSACACACCAAAACCACCCKHCKACFCCSKNPHH    KAAACPVN                  ",
                "                      KNCACAAH  KNNPNSPCCCFACACPHACAAAKCCNHCCCFVNPNPPH     AAAHPVH                  ",
                "                    PCHNAAACAV  FNSPPSVCCACCHPNKNCKCFACCAHHNAAFVPPPPSH     PAAAHVNC                 ",
                "                 H  NFHCAAAAF   FPSSVSVCFACKNHCAFKACCCCFAAHHKAHXPPSSSK     PACFNVNASA               ",
                "                 KX SAACCAAAX   FPVVPSPCAFKFCACACCAAHAAFFAHCKKHVPSVVPN    XAAAAACCACC   XX          ",
                "                 KH  FCAAAAC    FSVPVSCAKCAACAACCAAHAAAAFCCKAKHNXVVSSN     AAAAAAAAHC  XH           ",
                "                 NCP XAAAAAA    KPVVVHCFAAACACCCAAFCAAAAAFAHFAHAP VSSN      AAAACAFHX  CF           ",
                "                  AAVFHFACCAN   KSVXKCAFACSHACFACFCAAAAAAACAHACANXVVVN     KACFAACKK VCFP           ",
                "                  KCAAAACCCC    NVSHFCCAFXSCFCAFFAAAAAAAAACACFACHSVVXN     KACCAACAAFAAF            ",
                "                   ACACAACCH    PPFCAAAH PCFAACFACAAAAAAAAAAACFCHXS XP      FCACAACAAAFN            ",
                "                  VAAAAAAAAH    VVKACC  VCAAAACAAAAAAAAAAACAAAFFKSXPVS     FAACAAAAAACCV            ",
                "                 FCAAAAAAACH    VFCCFXCCAAACFCAAAAAAAAAAAAAAACACFNV PS      FAAAAAAAACA             ",
                "                  HAAAAAAACF    VCFFFFFCACCFAAAAAAAAAAAAAAAAAFCACCK NS       FAAAAAAAAA             ",
                "                  PFCAAAAAAAS    ACFFCCAFCAAAAAAAAAAAAAAAAAAACCCCAF PP        AAAAAAAAAV            ",
                "                   HCAAAAAAC    XX  PAACAAAAAAAAAAAAAAAAAAACCCHNFKV VP        CAAAAAAAHK            ",
                "                   FCAAAAAAH    XS  CHHCCAAAAAAAAAAAAAAAAAAAAFCCV    N        FAAAAACCHC            ",
                "                  VKCACAAAAH    XV KHCAAAAAAAAAAAAAAAAAAAAAAACFHH    N        FAAAAAAFHFV           ",
                "                  PKCAAACAAP     S CAAAAACAAAAAAAAAAAAAAAAAAAACHHAFX N        KAAAAAAHHFX           ",
                "                  HKCAAAAAAX     PCCCAACAAAAAAAAAAAAAAAAAAAAAACHCCCNVS        VAAAAACHHC            ",
                "                  FKFAACAAA      VHFAAAAAAAAAAAAAAAAAAAAAAAAAACACCFNS         XAAAAAFFCC            ",
                "                  FKFAAAAAC      XHHCCAAAAAAAAAAAAAAAAAAAAAAAAAAACCPS          AAAAAACCK            ",
                "                  KKFAAAAAN       KFCCAAAAAAAAAAAAAAAAAAAAAAAAAAFCCVV          AAAAAAFCP            ",
                "                  NKFAAAAAX       AFFCAAAAAAAAAAAAFFCAAAAAAAAAACCHFVV          AAAAAACA             ",
                "                  PKKAAACC        FACCAAAAAAAAAAAH HAAAAAAAAAAACHAAS          KAAAACACC             ",
                "                  XHNAACAF        CHFCAAAAAAAAAACH XAAAAAAAAAAANFAFN          FAAAAACCV             ",
                "                   FNNAAAF       XCHCHACAAAAAAAAAX  NCAACAACACNCCHCX         NACCCCCCC              ",
                "                   CKVFAAA       XCHCFPAAAAAAAAAF   SAAAAAAACFCACCK         FACAACCCAS              ",
                "                   CHNAAAAK       CHCCCKCAAAAAAAF    FAAAAACAAAACFP        FAACCCAACA               ",
                "                   VHACCCAAS      CFFCAACCAAAAAAF    HAAAAAAAAAAAHC       VHCACAAACAN               ",
                "                    HFAFAAAK      KCFCAAAAAAAAAAN    NAAAAAAAAAACHAV        CAACCAAC                ",
                "                    KCAHFAHH       FHCAAAAAAAAAAV  X SAAACAAAAACAAN         CACAACAK                ",
                "                    KCFFFAACX     HCCCAACAAAAAAA   X SAAACAAAAACAACFC       SAAAAACACC              ",
                "                    FCFFCAACAS  X KCFCAAAAAAAAAA    XVAAAAAAACCAFHK     NAAAACACCACA                ",
                "                   VCCFCCAACCCCACKSCFCFAAAAAAAAC    X ACAAAACAACCHP    SACAAAAACCACA                ",
                "                   ACAFCCCCCC PAACCACAACCAAAAAAH      AAAAAACAACCFV  PCAAH NAACAAK F                ",
                "                   CN FFCCCC    CCCAFACAACAAAAAK   X  FAAAAAAAACCA   CAC    FCAAAX                  ",
                "                  SC  PCCCA      KCCCCAAAAAAAAAP   V  NAAAAAAAACCA   AC     FAAAA                   ",
                "                  PV   CCCA       FNCCAAAAAAAAAP  V   PAAAAAAAAACX   AN     KCAAK                   ",
                "                       NAACK      V HCAAAAAAAAAS  S   PAAAAAAAACC     H     HCCAS                   ",
                "                       AAAAC K C    XACCAAAAAAAV SV   CCAAAAAAAAF      XV K CAAAF                   ",
                "                       VAAACACACCS  XCFFCCCCAAAP X S  NACAAAAAACA      NFPFAAAAAP                   ",
                "                        VFACFCCFN   FFFFCCCACAACV    VSCAAAAAAAAS     KKCAAKNNN                     ",
                "                          XHK       PCCFFCCAAACAV     VAAAAAAAAA                                    ",
                "                                     HCACCAAACAP      PAAAAAAAAAH                                   ",
                "                                     CAACACAAAC      FNAAAAFCAACAF                                  ",
                "                                   N PCAAACAACAXK    HCCACFCCCAACA                                  ",
                "                                   CCNCACAAAAACAP    VAACCACAFCCCN                                  ",
                "                                   VAAAAAAACAAAA      CAAACAAAFFA                                   ",
                "                                    NAAAAAAAAAAN      CACCAACCCAS                                   ",
                "                                     SAACAAAAAAX      VCACCCAAAA                                    ",
                "                                     KAAAAAAAAAN      AAAAAAAAAC P                                  ",
                "                                     CAAAAAAAAAA     FCAAAAAAAAHFA                                  ",
                "                                      AACAAAAAAP    CACACAAAAAAAHA                                  ",
                "                                      VAAAAAAAC     NACAAAAAAAAFC                                   ",
                "                                       NAAAAAAF     XCACAAAAACCCS                                   ",
                "                                        AACAAAP      AAACAACCCCH                                    ",
                "                                        AAACAA       FAACACCCCC                                     ",
                "                                        AAAACA       NAACACAFAS                                     ",
                "                                        PAAAAV        AAAAAACA                                      ",
                "                                         FCAAV        AACAAACA                                      ",
                "                                         KAAAK        AAAACCAS                                      ",
                "                                         HACACN       KCAACCP                                       ",
                "                                         AAAAA        KAACAC                                        ",
                "                                        XAAAAK        FCAAAX                                        ",
                "                                        ACAACS        FCCCF                                         ",
                "                                        FACAAV        FCCCF                                         ",
                "                                        ACACAV        CCCCCS                                        ",
                "                                       SAAACAK       CCCCCC                                         ",
                "                                      CACCCCCC       CACCCH                                         ",
                "                                     XACCCCCFA       FCCACH                                         ",
                "                                     SCCCCACCAX      HCCCCC                                         ",
                "                                  X HHHFFCHFFFF      HCACAA                                         ",
                "                                 KHHHK HFCN NHH      FCCCCCV                                        ",
                "                               VKKNKX KHF    NFN     CFFFFCF                                        ",
                "                              XP     XPP      CC    XFHHHKKHV                                       ",
                "                                     SN       KC     HHNKHKPKN                                      ",
                "                                     S         H     KHKKKNSNNNS                                    ",
                "                                                    XHNNSPNPSSSPP                                   ",
                "                                                    HKN   PNS  SPPPPV                               ",
                "                                                    KNKS   KSPV  SPPPS                              ",
                "                                                     NKH     PPX    PP                              ",
                "                                                      HK      XN     NV                             ",
                "                                                      KK        X     S                             ",
                "                                                      VS                                            "
            };
        } 
        else if (bossName.equalsIgnoreCase("Aetherius")) {
            art = new String[]{
                "                                                                                                    ",
                "                                                    †                        oÎ²                    ",
                "                                                    ð…                      ¬ææÎ                    ",
                "                                                   ÎÎ… ²      ²o         †§êæææÎ                    ",
                "                                                  …ÿ¬ §… …… …²¬…       …©êæææææÎ                    ",
                "                                                  ©ÿ¬………©ÎÎÎÎo…      …§ææææææææÎ                    ",
                "                                              …   ÎðÎ©öööö©©†…†    Îêæææææææææê©                    ",
                "                               oêæÿ¬           ²Î†ÿðo††¬††oöö©  ¬©©ðææææææææææê©                    ",
                "                             …êææææ©…          …§©ÿæ¬……²²¬¬†oö…¬ææææææêêêêêêêæê©                    ",
                "                            ²ðæææææ©…¬    †¬ o§…¬†Îêê¬ …†ö²²†o…oæææêêêêêêêêêêêê©                    ",
                "                           ¬ðêæêêæêö…ö   …oðêÿ ¬ö©ÿæÿêÎo©©êêðæ¬§ððæêðððððððððððö                    ",
                "                         …öêææêêêêêööð …oêÿðê†o¬©ÿ§§ê§ÿêÎöêêêêêêððêððÿðÿÿÿÿðððÿö                    ",
                "                        ²ÿææêêêêðêêêðÿ §êêðÿðÿ©²…oðððÎê†…©Æêêêêêêêæðÿÿÿ§ÿÿ§ÿÿÿÿö                    ",
                "                       ²æææêêêêððððêê² ¬æê§§ðÿðo²¬†²²Îê…oêÆêðêæêðÿêÿ§§§§§§§ÿ§ÿÿÎ²                   ",
                "                       Îêæêêêððððÿÿêðð¬ðêðÿêððÿ§²†ÿ§§ê§©ÆÆÆêêêêêÿðê§ÎÎ§Î§§Î§§§ÿêê©…                 ",
                "                      ¬ðææêêððÿÿÿÿêêêêêð†ÎðêððêöÎ§§ÿêê§ðææÆÆêêêêêêæ§Î©ÎÎÎÎÎÎ§§ÿêêê©…                ",
                "                     ©êÎ©êêðððÿÿÿÿÿððêê†ÎêêêêððêððêðÿÿêêÿêæÆÿ§öÿêêæ§©©Î©ÎÎÎÎÎ§§ððêðÿ¬               ",
                "                    ²ö…†æêêððÿÿ§§ÿ§§ÿêê©êêêêo²ð§ððÿ§ÎðêÿooÎÿðð©ÎððêÎ©Î©©ÎÎ©ÎÎÎ§ðððÿ¬ö…              ",
                "                     …Îæêêððÿÿÿ§§§Î§§êðêêê§ÿêêêæææê†²²†ö²²ÎæÆÆêð§§§ÎÎÎ©©ÎÎ©©ÎÎ§ððððêö               ",
                "                   …oêæêêêðððÿÿÎ§§§ÿððÿæðÿêêææÆÆêêÎo©o…†¬†©ðÆæðæð©©ö©ÎÎ§öoö©§Î§ðÿðððêo              ",
                "                  …©êææêêêÿÎ§ÿÿ¬ÿ§§ðæðêöÿææê§æÆÆÆêÿ§ö¬öo†©Îo§§êðÿæÿÎÎ§©©††Î§Î§§ÿÿðÿ†ÿo              ",
                "                  †êææÿæê§……Îÿÿ …¬†§o†Îêêðÿð¬…†êð§ð§oðê§©ðÎ² †§ðêêê§†¬²†©ö©©©§§ÿððð² †…             ",
                "                 …öðæöÿæêÎ  ©ð²     …†…§ÿ§§ö…  ÿððð§§§Îðð§Î¬  ¬§ÿ§§§ð§ÎÎðððÿ§ÿÎÎ©§ð©…               ",
                "                  †§¬ ÿæÿ…   …      ©ê§êêææÿ¬§êêêÿ©†öðððêÿÿ§²…§êððÿðêêê©©ÿÿ†ðÿððÿÿðæ…               ",
                "                  ……  ²o           …Îêðêðÿ©§ðêêêÿÿê§©ððÿ§§æ§ð© …†©©¬²²†ÿÿÿo……oðoðêêæ…               ",
                "                                    ¬ðêêêðêêêêÿÿêæðoÎÎêêÿêðê§êð§…      ¬©ö²   ………ÎÎö¬               ",
                "                          …§êêððÿ²Îðÿ§êêÿðêðê§êæÿÎêêêêÿêêðÿÎöêððððÎ†Îêðð©ö…      ……                 ",
                "                        ¬êêêêðððððÿÿÿÿêÿæðÿððÿ§ðð©öoêêêê§êê§ÿ©§ððððððððð§ÿo                         ",
                "                    … ¬æêêêðððÿÿÿÿ§ÿÿÿ§êêð§ðêêæðêæê§§ÎÎÎÿÎÎÎÎÎÿÿðððððððÿ©Îðæ²                       ",
                "                  …©ææêêêððÿÿÿÿ§§§§§Î§ðêæêðÿêêæêððÎÎÎÎ©©Î©©©©©Î§ðÿÿððððÿö©ðêêÿ…                     ",
                "                …oööðêêêðððÿÿ§§§ÎÎÎÎÎ§êæêê§ÿêêÿêÿÎðæðöö©öööoooo©ÎÿÿÿÿÿðÿÎöðêêêð…                    ",
                "                  ²Îæêêðððÿ§§§Î§ÎÎÎ©ÎÎðêððêêÿÎðð§ê§êêÆæoo†††¬¬¬†ö©§ÿÿÿÿððÿÿðêêÿ§²                   ",
                "                ²©æêæêêêð§öÎ§§ÎÎ†§Îö©©…††Îðÿðÿÿ§æêêÿðê§Æð†¬²²²¬¬††oo©§§ÿ§§ÿððêê§†²                  ",
                "               ©æææêðö©êÿ²¬Î§Î©… ……     …öðð§o§æêêæêê§§æÆÆ§†²²……²……²…………………†§êÎÿêö…                 ",
                "              …Îÿæêö… ¬†… ¬o………        …o§ÿ§o§Îæêêêæê§§ðÆÆæêêö†öo†²………………………²†ð©oÎ²                 ",
                "              …êêÎ¬                   ¬oöö†¬ÎÎÎêÿðêðð§Î§ÆÆææêÿð§ð§§ö²…………………²²ö©²                   ",
                "              ö…                 ²¬†öo†††¬¬ö§§ðêêêêêêð§êêðæææÿ§§ÿ§Î§Î†²²²²²²¬†o…                    ",
                "                               …ö©††¬¬¬²²¬oÎÎ§ÿêðÿðêÿ§ðêêêêê§ÎÎ§§§ÎÎÎÎo††¬††oo¬                     ",
                "                              …©©öo¬¬²²²†©©§§êêêæêêêÿðÿðððêê§ÎÎÎ§ÎÎ§§ÎÎ… †öoöo†                     ",
                "                              †Î©öo†¬²²¬ÿÎÎ§ÿêêêðÿðêêÿêêêêêðÿ§ÎÎ§ÎÎ§ÎÎÎ¬ ²†oooo¬…                   ",
                "                             …ÎÎÎ©öo†¬¬†Î©§§öÿêêêêðÿ¬…Îÿÿêêo… ²o†…©§§§Î©…  …††¬o²²…                 ",
                "                             …§ÎÎÎ©öo†o²… …… öêêêêæ†  …êêêêo        …¬ö§o                           ",
                "                              ©ÎÎÎ©©ö¬       …ÿêêðö…  …©êðð©…          ²o†                          ",
                "                              …ÎÎÎÎÎö…       …êêðêo   …ð§êææ¬            …                          ",
                "                               …ö§Î¬…        …öêðo…   êêêêêæð…                                      ",
                "                                …öo          Î§ÿÿ©…   †ææêêð¬                                       ",
                "                                …           öð§æê©²  …  †ððêê…                                      ",
                "                                            …©ðð§o      ²ÿæêæÆ²                                     ",
                "                                            …ÿêðÿ²        oêÿæð…                                    ",
                "                       ²Îö…                 ²ÿÿêÎ           †ðêö                                    ",
                "                       †o¬…                 ²ÿêê§…                                                  ",
                "                       ¬²                    ÿææÿ                                                   ",
                "                                             ©æê†                                                   ",
                "                                              ¬…                                                    "
            };
        } 
        else {
            // Default Monster (Pengganti Gambar Lama)
            art = new String[]{
                "                                                                                                    ",
                "                                                ……††                                                ",
                "                                                 o¬††                                               ",
                "                                             …………††¬¬†…                                             ",
                "                                            ……oo†††¬†¬oö†††ö†  …                                    ",
                "                                          †o†¬oo†††¬¬†¬oo¬¬¬¬¬¬ö …                                  ",
                "                                       ¬o¬¬¬o†¬¬†¬†¬†¬¬†††¬¬¬¬¬¬††…                                 ",
                "                                     ²©¬¬¬†¬¬††ö††¬††¬¬¬†ö¬¬¬¬¬²¬¬††                                ",
                "                              ……²²†§ð²¬¬²¬¬†¬†o†o††¬†¬†¬¬o¬¬¬¬¬¬¬¬……ö§o…………                         ",
                "                        …oÿÿÿðê§ö†ê†……¬†¬o¬¬ö†ö†ö¬†¬††¬¬¬††¬¬¬†²¬¬²……²ÿo†©ððÿððö²                   ",
                "                       oêðððððÿ²öÿ²………¬o¬¬†o†o†¬ö†¬††o¬¬†¬¬†¬¬¬o†²……………©©¬§ððððÿðÎ                  ",
                "                       ²êêðê§†¬Î§²………²ö¬…¬¬o¬o†††o†¬¬ö¬†¬†o†¬¬¬²¬o²…………²©ð¬¬Îððêê†                  ",
                "                       …ÿêêêo¬ðÎ…²¬²¬o¬²o††o††Î¬¬oo†¬o†²¬²o†¬¬¬……¬o²…………²öð¬†êêêæ…                  ",
                "                        oæêö†ÿ©¬o¬¬¬†o¬o¬¬†o¬††ö¬¬†ö¬¬ö¬¬²¬êo¬†¬²¬¬©¬¬¬²¬†öêo©êê©                   ",
                "                         æêöêÎ†††¬††o†ö¬¬¬§ö¬†©¬o©¬¬¬ö©¬¬†††©†¬†¬¬¬¬o¬¬¬¬o²öê©ðð¬                   ",
                "                         oöð©¬²o†¬¬oö†¬¬¬o²o¬¬Î¬¬¬¬¬††oÎo¬¬†©†o¬o¬¬†o¬¬¬¬o¬¬©êö©…                   ",
                "                         ¬ÿÎo†††¬†ö†¬¬†ö†oöö©††ö†¬¬¬²²²o¬öo¬¬ö©Î¬ö¬¬o¬¬¬¬ö¬¬¬©ðo                    ",
                "                         ¬©ö†¬¬ööoöö†¬…… ……………o†…o¬¬²²²²†…………………¬††¬†¬¬††o†¬¬¬ðo                    ",
                "                       ……oö¬¬†o†¬¬ö†¬…²†¬oo²…o……o………†ö††¬††…²o†¬†…†²Î¬¬¬¬ö¬¬o¬†¬…                   ",
                "                       …©†¬¬†o†o¬†Î¬²öêÎ¬²†¬©ÿ¬²……………………²²ðö¬†²†§êÎ¬²o¬¬¬ö¬¬ö¬¬o                    ",
                "       …           …  ²Î†¬†¬†o†o¬†²²ÿ§…   §ðÿ…©²……………………¬o   oðæ¬…ÿ§…o¬¬o†††o†¬ö²                   ",
                "        ²²          ²öö††¬††††¬o¬†…†§… † ²ðÎðö ………………………… ¬ …ÿÎð§ …ð†²†¬ö¬¬ö¬†¬oö                   ",
                "     …  o©…    ………oo†©†©¬†o†††¬o¬†…ö† …ê†ÎêÎ©Î ………………………… §öoð§öê  ¬©²††o†ö¬¬¬¬oö…                  ",
                "        o¬¬¬††††¬¬††o¬ö¬†©††¬o¬o††¬©²  §©o©†Îö  ……………………… ©©o©oÎÎ …¬©†oooo†††††ooo                  ",
                "        ²©¬¬¬¬¬¬†††ö¬öo†o©†¬¬ö¬oo©…†Î …¬ö†¬¬©²……………²†……………¬©††¬©¬  §¬öoö†¬¬¬¬†¬ö†o…                 ",
                "        … ²oooöo††o†©§††öÎ†¬¬††¬§o…²…² … ²²²……………………………………… ²²²   ²…öÎ†oo††¬††¬©o††                 ",
                "           …†††¬†Î†©Î©††©Î†¬¬†o¬©©o²¬²¬¬²²²…²……²²¬¬††¬¬²…²……²…²…¬†²o†ö†¬¬¬†††¬©§†¬o²                ",
                "           ©¬†††Î†oÎ§†¬¬öÎo†††o†öoo²¬²²¬²²²²ÿÿÎo†¬²²²²²¬††Îÿ¬²²¬¬ö†Î¬†¬††¬¬†¬ö§©¬o††                ",
                "       … …†††††Î©¬ÎÎ§†††oÎÎ†¬†¬o†Î††…²²²²²²¬§öoo††ooooooooö©ö…¬o¬©†¬¬¬¬††¬†¬ö§§Î††o††               ",
                "         ††††¬©Îö†ÎÎ§††¬†©Î©†¬††oÎÎÎÎ²²…²………¬o¬†††††††††††††¬o¬o††¬¬¬†¬¬††¬ö§Î§©©††o¬†…   …         ",
                "        …ö††¬†Î©©†§ö§o†¬††©ÎÎö†¬oÎÎ©©§†……………²…o††††††¬¬††o¬ö¬¬ö¬¬¬†¬†¬¬¬¬†Î§Î§Î©©ö††©†o…  …         ",
                "         ©¬†¬†Î©Î§¬ ©Î¬¬¬†¬öÎ©©©oö§©©§ÎÎo…²††¬………²¬††o………ö¬o††¬¬¬††¬†††¬ö§Î©ÎÎ©©©©öoö†oÎ²²©o …      ",
                "         †††††©©o †o†ÿö¬†††¬†oÎ©©©©§Î§©©Îö¬¬ö…………………………†o†…²†¬¬†¬¬¬†¬¬öÎÎ©©©Î©©©©©ö†©öööooö†        ",
                "          ¬††¬†§…  …¬Î†¬†††¬¬¬¬¬†ooooö†¬†¬†©ÿÎÿððÿ§ÎÎÎÎÿÿÿ§†¬†¬¬†¬¬†öÎÎ©©ö©Îöö©öö©ö††Î©©Îö²         ",
                "         …  ¬oo¬oööö…§©Î§©†††¬†††¬¬¬†¬†¬†öÎÎÎÎ©o††ö©©Îo††§ö¬¬†¬¬†o©Î©©öö©Î©Î…†öööÎöo†o              ",
                "              …   ……öÎ©ö©öö§Îöo†o††††o©©©©Î†¬¬¬o††¬¬¬†oö¬ö¬¬¬¬oÎ©Îöööö©Îööö©   †§öö†o²              ",
                "          ……       oÎöööööö©©  … oððêðÿðð§ö… ²¬¬†o¬¬²²¬†¬¬¬†o©©öööö©ððÿÿÿÿð§oÎ²²©ööo¬†²               ",
                "                      … …     ©ððððððððððöo… … ……²¬†¬²……o¬oööööö©ððÿÿÿÿÿðÿÿÎ©oo©©o²…                ",
                "                         …² öðððððððððÿððoö …  … …†oooo²†oo²………o†ÿðððÿÿÿðÿÿðÿö…   …                 ",
                "                      …öðððêðÿðÿððððððððö†ö² …  ²©ööoöoo†©… ……²†¬Îðÿÿðÿÿÿÿÿÿðöoo…                 ",
                "                    ¬ÿÿÿÿÿÿðêðððÿððððððÿ†o²²………²öÎ§öö©Î§©……†…†…†¬Îðððÿÿðÿÿÿÿÿÿððÿÿð§…               ",
                "                 ¬§ÿÿÿÿÿÿÿðÿÿðððððððÿðÿ§¬†  ………©ÎÎÎ¬…ÎÎÎÎ§ … ……o†Îððÿÿÿððÿðððÿÿÿÿÿð§ÿ§…             ",
                "            …o§ÿðÿÿÿÿÿÿÿÿÿÿÿðÿÿÿÿðððððÿððÎ¬†o†o†o©öö©††oöööo¬²††o†¬Îððððððððÿÿððÿÿÿÿÿÿÿÿÿÿ†           ",
                "       …†©ÿÿÿÿÿÿÿÿÿððððððððÿÿÿÿðÿððÿÿððððÿoo†©©©ööööooooooooo†o§ÿððððððððÿðÿÿÿÿÿÿÿðÿððððððÎ²  …     ",
                "      †ÿÿÿ§§ÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðððððððððððððÿoöoÿððððÿÿÿÿÿÿÿÿÿðööoÿððððÿððêêêêêêðêðððÿÿÿÿÿÿÿÿðÿðo…     ",
                "     …oÿÿÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðêêððððÿÿÿÿð§ÿÿÿÿÿÿÿÿÿÿÿÿðÿÿððÿ§ðððÿÿððêêðððððÿÿðððððÿÿÿÿÿÿÿÿÿÿÿð§…  ",
                "      oÿÿðððÿÿÿÿÿÿÿÿÿðÿÿÿÿðÿêêæÎoðððððððððÿÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿððÿÿðððððêêê§§æððððÿððÿðÿÿÿÿÿÿÿÿÿÿÿðÿð² ",
                "     …¬ÿÿÿÿððððÿÿÿÿÿÿÿðÿÿðêÎ¬   ©ðÿðÿððÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðÿÿÿððÿððêêððö  ²©êêððÿðððððÿÿÿððððððÿê… ",
                "      …ðÿÿÿÿÿððððððÿÿðêêÎ² …   …ðððÿððÿððÿ§ððÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðÿ§ðÿÿððêêððÿ      öððððððððêêêððÿÿÿð©  ",
                "       ÿðÿÿÿÿÿÿÿðððêêð²        ¬ððððððÿðð§ÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿ§ðððêðêððê       …oêêêððððððÿÿðððð¬  ",
                "       ¬êÿÿÿÿÿÿÿððÿððê…        öÿðÿððÿððÿ§ÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿððÿÎÿðððêðððð¬       ©ðððððððððððððê§   ",
                "       …§êðÿÿÿðÿðÿÿððÿ§        ÿððððððððÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðððÿÿÿðÿêðêððððö     †êððððððððððððððê…   ",
                "        †ðêÿðÿÿÿðððÿ©©Î©……    †ðððððêðÿðÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿððÿÿÿÿððððÿðððÿ   ¬§©©ööoo§ððÿðððêêê†    ",
                "         ðêêÿðÿð§o©†oö©Î§…    §ðððêðððÿÿÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿððððð§ðððêððððÿ²…öö©©Î†o†ö©o§êðððêêo     ",
                "         …êðêêÎ§©©©öö†ööo©…  ²ÿððêêðÿðð§ðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðêððÿÎððÿêðððÿðÎ††††²††¬Îööoö©ÿêðêö      ",
                "          Îðð§©©©©Î†ö¬²¬¬o† …†ððêððÿðÿÿ§ÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðððððð§ðððêððððÿöo²………¬²††ö©Î©§ÿæ†…      ",
                "     …    ²ê§§§§©ö†¬¬¬²²²†öo…Îêêððÿððÿ§ððÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðððððÿÿÿððððððÿ¬¬¬¬²¬²¬¬¬oöÎ§ÿÿo        ",
                "           ¬ÿðÿ§©††¬¬¬††¬¬²©†êêðððððÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðððêêÿÿðððððêðð² ²†o††¬†ooö§§Î          ",
                "           …¬ð§§Î©o††o†ö…¬²²Îêððððððÿÿÿÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿððððððêêÿÿðððððêðö†²…†oööoo©Î§©           ",
                "             öÿÎ©ÎöoöÎ²²†………ðððêðððÿ§ÿðÿÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðððÿðððÿêðððððððÿ……©²†ðö©ÎÎÿ…            ",
                "              †§Î§§ÿ§ÿö†………oðððÿðððÿ§ÿððÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðÿÿÿðððððêððêððððððð¬……†……öÿ§ö              ",
                "               …ÿÿÿ©¬…†………Îððððððÿÿ§ÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðÿÿðððððÿððêêðððððêö………²   …  …            ",
                "             … … …   …†…²§ðððÿðððÿÿÿððÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðÿðððÿðÿêêêêððððÿ………ö…    …             ",
                "                   …²²o¬ðÿððððððððÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðððððððÿðððÿÿðêêððððð§……†…†                 ",
                "                     †©ððððððððððÿðÿððÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðÿÿððÿðÿðð§ðððêêêðððÎ……¬†…                ",
                "                    …§ðððððððððððÎÿðððÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿððÿððððððÿðÿððêêêêêðð§²…                  ",
                "                   …ÿðððððððÿððð§ÿðððÿÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿððððððððððð§ððððêêêêêê†                   ",
                "                … ¬êððððððððððÿ©oÎðððÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿððððððððð§ÿððððêêêêêê²…                 ",
                "                …ÎêÿÎööo†o†oöÎö††Îêÿÿðÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿðÿÿððððððððððÿÿðððððêêêêêð…                 ",
                "               ²ÎöÎööoö©öoo†ööoöÎ©ö†ö©êÿðÿÿÿÿÿðÿÿÿÿÿðÿÿÿÿÿððÿðððððððððÿðððððððêêæê§                 ",
                "              †§Îöö©©öööo©o©ö††ö©†öoööÎöoö©Îÿÿÿÿÿÿðÿððÿÿÿÿðÿðððððððððððÿðððððððêêêæö                ",
                "             öÎ©©ö©©©oo©©o©©©o†öö©oö†öoö†ö©©ööoo©§§ÿðÿÿÿÿÿÿÿððððððððððÿ©ÿððððððððêêê…               ",
                "         … …©§©©©©öÎ©©ÎöoÎ©o†ö†o§ooöÎ†©©†ö††©oo†©ö©Îo††öÎððÿððÿððððððð†oo©ðððêðððêêêæ……             ",
                "          ¬©o†öÿoo††ö†ö©©o¬†©©©ooöÎ†©o†öÎ†ööoööoöoö†©†öo¬ö©ö©ö©o†¬¬²²²²²²²……²…²²†ooo©Î²             ",
                "         ²ö†oo†oo†o†o†††††oooöo†ooÎö†ööö†o†oo©öooöoöööoo†oö†o†öö†o¬¬††¬o¬¬†²†¬†²¬oöö©öö             ",
                "        †o††††o††††††††††††††††††öoö††öööö†o©©öoö†ööooö©o†öÎo©†oo†©¬†¬²öo¬o²¬²¬ö†oöö§©©ö            ",
                "       öoo†o††o††††††††††††††††††o††††©öoo†o©©©oö†ööooöo†öo©o¬o¬o©©†o†o²¬§†¬²¬¬††††©ö©Îÿ²           ",
                "     …©oo††o††o††††††††††††††††¬††††††¬†©†¬†©Î©o†¬¬†öÎÎ©ooö©o¬ö¬oöo†o¬¬o²©o¬²²²o²ooöÎöÎÎÿ²          ",
                "    ¬§§Î§Îö†ö©o††††o†††††††††††††††††††††††††††öo††††ooö©©©öo††††¬†¬¬¬¬²¬²²²¬……………¬o©©©öoo…         ",
                "    †§§ÎÎ§§§§Î©†oöo††††©††††††††††††††††††††††††††¬†††¬oo†††¬¬o†¬¬¬¬¬¬†²²²²²²†……………ö††††öoo         ",
                "    …Î§ÎÎÎ§§§§§§§Îö†oö†††o††††††††††††††††††††††††††¬†††¬¬¬††¬¬¬¬¬¬¬¬¬¬²²²²²²¬……………²†††††o†¬        ",
                "      o§§ÎÎÎÎ§§§§§§§§©††öö†††oö†††††††††††††††††††††††††††¬†¬¬¬¬¬¬¬¬¬¬¬²²²²²²²²……………²††††††o…       ",
                "       …§ÿ§ÎÎÎÎÎÎ§Î§§§§§§o††©†††††††††††††††††††††¬††¬†¬††¬†¬¬†¬¬¬¬¬¬¬¬¬¬²²²²²¬²……………²†††††oö…      ",
                "        …o§§ÎÎÎÎÎÎÎÎÎÎÎ§Î§ÿö©††öo††††ö††††††¬†††††††¬¬¬¬¬¬¬¬¬¬†¬¬¬¬¬¬¬¬¬¬¬¬²²²²²………………¬††††ooo      ",
                "          …ö§§§ÎÎÎÎÎÎÎÎÎÎ§öoooööö†oöo††††††††††††††††††¬¬†¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬²¬²²²²²…………¬²¬oööooö†     ",
                "             ²©§§§ÎÎÎÎÎÎÎÎo†††oöoö©©ö†öööo†††o©†††††††††¬†¬¬†¬¬†¬¬¬¬¬¬¬¬¬¬¬¬¬¬²ö¬… …²¬o…¬öÎÎo…      ",
                "                †§Îÿ§ÎÎ©Îö††oo††ooöoÎ§§Îöo†©©o†††††††¬o†¬†††¬¬†¬¬¬†¬¬¬¬o†¬¬¬²²²¬¬ö²……²©Î§¬          ",
                "                   ²ÎÎ§§Î††††††††oö©ÎÎÎÎÎÎ§§Î©o†oö©öo¬†††††††††o†¬¬¬¬†¬†¬†¬oö†¬¬²o©§o²… …           ",
                "                      …oooooöööö††ö§§ÎÎÎÎÎÎÎÎÎÎÎ§§ÎÎ©o††oooooööö©©öooooo††¬†oöÎÎ²  …                ",
                "                      ²©†††oooooo©Î©©©©ÎÎ§ÿ§§§§§§§§§§§§§§§ÿÿ§Î©ööoooÎÎööo†††¬¬¬¬…                   ",
                "                     …o†††††oooooo                                  ¬ooo†††¬¬²……†                   ",
                "                    …ö²††††††oooö                                    ¬öo††¬²… …… ¬ …                ",
                "                   …¬²¬¬¬††††ooo…                                     ²ö†¬²……… … ²…                 ",
                "                 … ²……²¬¬¬†††o²                                        …†¬… … ……… †…                ",
                "                  …¬…  …¬¬†††ö                                          ²o¬………… …… ¬                ",
                "                  ¬  …………²¬†©…                                           ¬¬²…… …………²                ",
                "                  ¬…… ……  ¬¬ö                                            …†¬… … …  ²²               ",
                "                 ²¬……  ………²†…                                             †²²……… …… †               ",
                "                 †………  ………²ö                                              …o²² ……………†               ",
                "                 o   ……… …o…                                               ¬¬²… …  …²²              ",
                "                 ©………   …¬¬                                               ……†²²……  … ²              ",
                "                 ö  … ………†…                                                 ¬†²……… … ²              ",
                "                …† ……… …¬†                                                   †¬² … … ²              ",
                "                ²¬  … …²o                                                    …†¬…… ……²              ",
                "                ²…… … …¬²                                                     o¬…… ……²…             ",
                "                ¬ …………²ö                                                       ö²…………²…             ",
                "                ¬ …… …†²                                                       ¬¬  ……²              ",
                "                ¬ …… …o                                                        …o… ……¬              ",
                "                ¬ …… …¬                                                         o² ……²…             ",
                "                ¬… ……²²                                                         ¬¬…………†             ",
                "                ² …… ²²                                                         …o …  o             ",
                "               ²²…… ……²                                                         …ö …… ¬…            ",
                "               ö©êêðö²²                                                         …©öêæðö¬            ",
                "              ²ê² ……†æ¬ …                                                       ²ê² … ²Î            ",
                "              ©ðö  ²ÿðo                                                         ¬ê©… …†ê†           ",
                "           …  êÿðÿÿððêö                                                         †êÿÿÿÿÿÿæ…          ",
                "              oêêêêêÿ†                                                          …öðêêêðð†           ",
                "                       …                                                            … … …           ",
                "                                                                                                    "
            };
        }

        // Print langsung tanpa padding tambahan karena gambarnya sangat lebar
        for (String line : art) {
            System.out.println(" " + line);
        }
    }

    private String centerText(String text, int width) {
        String clean = text.replaceAll("\u001B\\[[;\\d]*m", "");
        int padding = (width - clean.length()) / 2;
        if (padding < 0) padding = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<padding; i++) sb.append(" ");
        sb.append(text);
        while(sb.length() < width + (text.length() - clean.length())) sb.append(" ");
        return sb.toString();
    }

    private String padLeft(String s, int width) {
        if (s.length() >= width) return s;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<width-s.length(); i++) sb.append(" ");
        sb.append(s);
        return sb.toString();
    }

    // MANUAL STACK CLASS
    class TurnStack {
        private Node top;
        private class Node {
            String data;
            Node next;
            Node(String data) { this.data = data; this.next = null; }
        }
        public TurnStack() { this.top = null; }
        public void push(String data) {
            Node newNode = new Node(data);
            newNode.next = top;
            top = newNode;
        }
        public String pop() {
            if (isEmpty()) return null;
            String data = top.data;
            top = top.next;
            return data;
        }
        public boolean isEmpty() { return top == null; }
        public void clear() { top = null; }
    }
}