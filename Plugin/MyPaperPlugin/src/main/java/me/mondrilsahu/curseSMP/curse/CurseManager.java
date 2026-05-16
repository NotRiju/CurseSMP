package me.mondrilsahu.curseSMP.curse;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CurseManager {

    private static final HashMap<UUID, CurseData> map = new HashMap<>();

    private static boolean enabled = false;

    private static File file;
    private static YamlConfiguration data;

    // ================= INIT =================

    public static void init(){

        file = new File(CurseSMP.get().getDataFolder(), "data.yml");

        if(!file.exists()){

            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        data = YamlConfiguration.loadConfiguration(file);
    }

    // ================= ENABLE =================

    public static boolean isEnabled(){
        return enabled;
    }

    public static void setEnabled(boolean value){
        enabled = value;
    }

    // ================= PLAYER DATA =================

    public static boolean has(Player p){
        return map.containsKey(p.getUniqueId());
    }

    public static CurseData getData(Player p){
        return map.get(p.getUniqueId());
    }

    public static CurseType getType(Player p){

        if(!has(p)) return null;

        return map.get(p.getUniqueId()).getType();
    }

    public static int getLevel(Player p){

        if(!has(p)) return 0;

        return map.get(p.getUniqueId()).getLevel();
    }

    public static void setLevel(Player p, int level){

        if(!has(p)) return;

        map.get(p.getUniqueId()).setLevel(
                Math.max(1, Math.min(3, level))
        );

        savePlayer(p);
    }

    public static void setCurse(Player p, CurseType type){

        map.put(
                p.getUniqueId(),
                new CurseData(type, 1)
        );

        savePlayer(p);
    }

    public static void remove(Player p){

        map.remove(p.getUniqueId());

        data.set(p.getUniqueId().toString(), null);

        try {
            data.save(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // ================= RANDOM =================

    public static void random(Player p){

        List<CurseType> available = new ArrayList<>();

        for(CurseType type : CurseType.values()){

            // skip legendary
            if(isLocked(type)) continue;

            available.add(type);
        }

        CurseType random =
                available.get(new Random().nextInt(available.size()));

        setCurse(p, random);
    }

    // ================= REROLL =================

    public static CurseType reroll(Player p){

        if(!has(p)) return null;

        CurseType current = getType(p);

        if(isLocked(current)){
            return current;
        }

        List<CurseType> available = new ArrayList<>();

        for(CurseType type : CurseType.values()){

            if(isLocked(type)) continue;

            if(type == current) continue;

            available.add(type);
        }

        CurseType random =
                available.get(new Random().nextInt(available.size()));

        setCurse(p, random);

        return random;
    }

    // ================= SAVE =================

    public static void savePlayer(Player p){

        if(!has(p)) return;

        CurseData cd = getData(p);

        data.set(
                p.getUniqueId() + ".type",
                cd.getType().name()
        );

        data.set(
                p.getUniqueId() + ".level",
                cd.getLevel()
        );

        try {
            data.save(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // ================= LOAD =================

    public static void loadPlayer(Player p){

        UUID uuid = p.getUniqueId();

        if(!data.contains(uuid + ".type")) return;

        CurseType type = CurseType.valueOf(
                data.getString(uuid + ".type")
        );

        int level = data.getInt(uuid + ".level");

        map.put(uuid, new CurseData(type, level));
    }

    // ================= LEGENDARY =================

    public static boolean isLocked(CurseType type){

        return type == CurseType.DEEP_SEA_CREATURE
                || type == CurseType.ENDER_DRAGON
                || type == CurseType.WITHER;
    }

    public static boolean isObtainable(CurseType type){

        switch (type){

            case DEEP_SEA_CREATURE:
                return CurseSMP.get().getConfig()
                        .getBoolean("obtainable.warden");

            case ENDER_DRAGON:
                return CurseSMP.get().getConfig()
                        .getBoolean("obtainable.dragon");

            case WITHER:
                return CurseSMP.get().getConfig()
                        .getBoolean("obtainable.wither");

            default:
                return true;
        }
    }

    public static void setObtained(CurseType type){

        switch (type){

            case DEEP_SEA_CREATURE:
                CurseSMP.get().getConfig()
                        .set("obtainable.warden", false);
                break;

            case ENDER_DRAGON:
                CurseSMP.get().getConfig()
                        .set("obtainable.dragon", false);
                break;

            case WITHER:
                CurseSMP.get().getConfig()
                        .set("obtainable.wither", false);
                break;
        }

        CurseSMP.get().saveConfig();
    }

    public static void setAvailable(CurseType type){

        switch (type){

            case DEEP_SEA_CREATURE:
                CurseSMP.get().getConfig()
                        .set("obtainable.warden", true);
                break;

            case ENDER_DRAGON:
                CurseSMP.get().getConfig()
                        .set("obtainable.dragon", true);
                break;

            case WITHER:
                CurseSMP.get().getConfig()
                        .set("obtainable.wither", true);
                break;
        }

        CurseSMP.get().saveConfig();
    }

    // ================= FORMAT =================

    public static String format(CurseType type){

        String s = type.name()
                .toLowerCase()
                .replace("_", " ");

        return Character.toUpperCase(s.charAt(0))
                + s.substring(1);
    }

    // ================= COLORS =================

    public static Color getColor(CurseType type){

        switch (type){

            case FIRE:
                return Color.fromRGB(255, 80, 0);

            case VOID:
                return Color.fromRGB(120, 0, 255);

            case STORM:
                return Color.fromRGB(0, 170, 255);

            case SHADOW:
                return Color.fromRGB(40, 40, 40);

            case BLOOD:
                return Color.fromRGB(255, 0, 0);

            case FREEZE:
                return Color.fromRGB(150, 255, 255);

            case WIND:
                return Color.fromRGB(200, 255, 200);

            case DEMON:
                return Color.fromRGB(255, 0, 150);

            case NATURE:
                return Color.fromRGB(0, 255, 100);

            case DEATH:
                return Color.fromRGB(100, 100, 100);

            case DEEP_SEA_CREATURE:
                return Color.fromRGB(0, 30, 120);

            case ENDER_DRAGON:
                return Color.fromRGB(170, 0, 255);

            case WITHER:
                return Color.fromRGB(30, 30, 30);

            default:
                return Color.WHITE;
        }
    }

    // ================= GLASS =================

    public static Material getGlass(Color color){

        if(color.equals(Color.fromRGB(255, 80, 0)))
            return Material.ORANGE_STAINED_GLASS;

        if(color.equals(Color.fromRGB(120, 0, 255)))
            return Material.PURPLE_STAINED_GLASS;

        if(color.equals(Color.fromRGB(0, 170, 255)))
            return Material.LIGHT_BLUE_STAINED_GLASS;

        if(color.equals(Color.fromRGB(40, 40, 40)))
            return Material.GRAY_STAINED_GLASS;

        if(color.equals(Color.fromRGB(255, 0, 0)))
            return Material.RED_STAINED_GLASS;

        if(color.equals(Color.fromRGB(150, 255, 255)))
            return Material.CYAN_STAINED_GLASS;

        if(color.equals(Color.fromRGB(200, 255, 200)))
            return Material.LIME_STAINED_GLASS;

        if(color.equals(Color.fromRGB(255, 0, 150)))
            return Material.MAGENTA_STAINED_GLASS;

        if(color.equals(Color.fromRGB(0, 255, 100)))
            return Material.GREEN_STAINED_GLASS;

        return Material.WHITE_STAINED_GLASS;
    }
}