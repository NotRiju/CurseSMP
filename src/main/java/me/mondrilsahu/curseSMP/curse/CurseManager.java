/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 */
package me.mondrilsahu.curseSMP.curse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import me.mondrilsahu.curseSMP.CurseSMP;
import me.mondrilsahu.curseSMP.curse.CurseData;
import me.mondrilsahu.curseSMP.curse.CurseType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CurseManager {
    private static final HashMap<UUID, CurseData> map = new HashMap();
    private static boolean enabled = false;
    private static File file;
    private static FileConfiguration config;

    public static void init() {
        File folder = CurseSMP.get().getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        file = new File(folder, "data.yml");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        config = YamlConfiguration.loadConfiguration((File)file);
    }

    public static void save() {
        try {
            config.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(Player p) {
        String path = p.getUniqueId().toString();
        if (config.contains(path)) {
            CurseType type = CurseType.valueOf(config.getString(path + ".type"));
            int level = config.getInt(path + ".level");
            map.put(p.getUniqueId(), new CurseData(type, level));
        }
    }

    public static String format(CurseType type) {
        String name = type.name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    public static void savePlayer(Player p) {
        if (!map.containsKey(p.getUniqueId())) {
            return;
        }
        String path = p.getUniqueId().toString();
        CurseData data = map.get(p.getUniqueId());
        config.set(path + ".type", (Object)data.getType().name());
        config.set(path + ".level", (Object)data.getLevel());
        CurseManager.save();
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static void random(Player p) {
        ArrayList<CurseType> pool = new ArrayList<CurseType>();
        for (CurseType type : CurseType.values()) {
            if (type == CurseType.DEEP_SEA_CREATURE || type == CurseType.ENDER_DRAGON || type == CurseType.WITHER) continue;
            pool.add(type);
        }
        CurseType type = (CurseType)((Object)pool.get(new Random().nextInt(pool.size())));
        map.put(p.getUniqueId(), new CurseData(type, 1));
        CurseManager.savePlayer(p);
    }

    public static boolean isLocked(CurseType type) {
        return type == CurseType.DEEP_SEA_CREATURE || type == CurseType.ENDER_DRAGON || type == CurseType.WITHER;
    }

    public static boolean isObtainable(CurseType type) {
        switch (type) {
            case DEEP_SEA_CREATURE: {
                return CurseSMP.get().getConfig().getBoolean("obtainable.warden");
            }
            case ENDER_DRAGON: {
                return CurseSMP.get().getConfig().getBoolean("obtainable.dragon");
            }
            case WITHER: {
                return CurseSMP.get().getConfig().getBoolean("obtainable.wither");
            }
        }
        return true;
    }

    public static void setObtained(CurseType type) {
        switch (type) {
            case DEEP_SEA_CREATURE: {
                CurseSMP.get().getConfig().set("obtainable.warden", (Object)false);
                break;
            }
            case ENDER_DRAGON: {
                CurseSMP.get().getConfig().set("obtainable.dragon", (Object)false);
                break;
            }
            case WITHER: {
                CurseSMP.get().getConfig().set("obtainable.wither", (Object)false);
            }
        }
        CurseSMP.get().saveConfig();
    }

    public static void setAvailable(CurseType type) {
        switch (type) {
            case DEEP_SEA_CREATURE: {
                CurseSMP.get().getConfig().set("obtainable.warden", (Object)true);
                break;
            }
            case ENDER_DRAGON: {
                CurseSMP.get().getConfig().set("obtainable.dragon", (Object)true);
                break;
            }
            case WITHER: {
                CurseSMP.get().getConfig().set("obtainable.wither", (Object)true);
            }
        }
        CurseSMP.get().saveConfig();
    }

    public static CurseType reroll(Player p) {
        CurseType current = CurseManager.getType(p);
        ArrayList<CurseType> pool = new ArrayList<CurseType>();
        for (CurseType type : CurseType.values()) {
            if (type == CurseType.DEEP_SEA_CREATURE || type == CurseType.ENDER_DRAGON || type == CurseType.WITHER) continue;
            pool.add(type);
        }
        CurseType newType = (CurseType)((Object)pool.get(new Random().nextInt(pool.size())));
        map.put(p.getUniqueId(), new CurseData(newType, 1));
        CurseManager.savePlayer(p);
        return newType;
    }

    public static CurseType getType(Player p) {
        CurseData data = map.get(p.getUniqueId());
        return data != null ? data.getType() : null;
    }

    public static int getLevel(Player p) {
        CurseData data = map.get(p.getUniqueId());
        return data != null ? data.getLevel() : 0;
    }

    public static void setLevel(Player p, int level) {
        map.computeIfAbsent(p.getUniqueId(), uuid -> new CurseData(CurseType.FIRE, 1)).setLevel(level);
        CurseManager.savePlayer(p);
    }

    public static void setCurse(Player p, CurseType type) {
        map.put(p.getUniqueId(), new CurseData(type, 1));
        CurseManager.savePlayer(p);
    }

    public static void remove(Player p) {
        map.remove(p.getUniqueId());
        config.set(p.getUniqueId().toString(), null);
        CurseManager.save();
    }

    public static void unload(Player p) {
        map.remove(p.getUniqueId());
    }

    public static boolean has(Player p) {
        return map.containsKey(p.getUniqueId());
    }

    public static Color getColor(CurseType type) {
        if (type == null) {
            return Color.WHITE;
        }
        switch (type) {
            case FIRE: {
                return Color.fromRGB((int)255, (int)80, (int)0);
            }
            case VOID: {
                return Color.fromRGB((int)120, (int)0, (int)255);
            }
            case STORM: {
                return Color.fromRGB((int)0, (int)170, (int)255);
            }
            case SHADOW: {
                return Color.fromRGB((int)40, (int)40, (int)40);
            }
            case BLOOD: {
                return Color.fromRGB((int)255, (int)0, (int)0);
            }
            case FREEZE: {
                return Color.fromRGB((int)150, (int)255, (int)255);
            }
            case WIND: {
                return Color.fromRGB((int)200, (int)255, (int)200);
            }
            case DEMON: {
                return Color.fromRGB((int)255, (int)0, (int)150);
            }
            case NATURE: {
                return Color.fromRGB((int)0, (int)255, (int)100);
            }
            case DEATH: {
                return Color.fromRGB((int)100, (int)100, (int)100);
            }
            case DEEP_SEA_CREATURE: {
                return Color.fromRGB((int)0, (int)40, (int)120);
            }
        }
        return Color.WHITE;
    }

    public static Material getGlass(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r > 200 && g < 100) {
            return Material.RED_STAINED_GLASS;
        }
        if (b > 200 && r < 100) {
            return Material.BLUE_STAINED_GLASS;
        }
        if (g > 200 && r < 100) {
            return Material.GREEN_STAINED_GLASS;
        }
        if (r > 200 && b > 200) {
            return Material.MAGENTA_STAINED_GLASS;
        }
        if (g > 200 && b > 200) {
            return Material.CYAN_STAINED_GLASS;
        }
        return Material.WHITE_STAINED_GLASS;
    }
}

