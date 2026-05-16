package me.mondrilsahu.curseSMP.abilities;

import me.mondrilsahu.curseSMP.CurseSMP;
import me.mondrilsahu.curseSMP.curse.CurseManager;
import me.mondrilsahu.curseSMP.curse.CurseType;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class AbilityManager implements Listener {
    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private static final HashMap<UUID, Long> reviveCooldowns = new HashMap<>();
    private static final Set<UUID> activeRage = new HashSet<>();

    public static void startTasks() {
        // Smooth Visual/Aura Task: Every 5 ticks (4 times a second)
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!CurseManager.isEnabled()) return;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!CurseManager.has(p)) continue;
                    playAura(p);
                }
            }
        }.runTaskTimer(CurseSMP.get(), 0L, 5L);

        // Passive Effects Task: Every 20 ticks (1 second)
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!CurseManager.isEnabled()) return;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!CurseManager.has(p)) continue;
                    applyPassives(p);
                }
            }
        }.runTaskTimer(CurseSMP.get(), 0L, 20L);
    }

    private static void playAura(Player p) {
        CurseType type = CurseManager.getType(p);
        if (type == null) return;
        Location loc = p.getLocation().clone().add(0, 0.1, 0);

        switch (type) {
            case FIRE:
                // Flame ring
                for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 4) {
                    double x = Math.cos(theta) * 0.7;
                    double z = Math.sin(theta) * 0.7;
                    p.getWorld().spawnParticle(Particle.FLAME, loc.clone().add(x, 0.2, z), 1, 0.0, 0.0, 0.0, 0.0);
                }
                break;
            case VOID:
                p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().clone().add(0, 1.0, 0), 3, 0.2, 0.4, 0.2, 0.05);
                break;
            case STORM:
                p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation().clone().add(0, 2.1, 0), 1, 0.3, 0.0, 0.3, 0.0);
                p.getWorld().spawnParticle(Particle.WATER_DROP, p.getLocation().clone().add(0, 2.0, 0), 1, 0.3, 0.0, 0.3, 0.0);
                break;
            case SHADOW:
                p.getWorld().spawnParticle(Particle.SQUID_INK, p.getLocation().clone().add(0, 1.0, 0), 2, 0.2, 0.4, 0.2, 0.02);
                break;
            case BLOOD:
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().clone().add(0, 1.0, 0), 3, 0.2, 0.4, 0.2, new Particle.DustOptions(Color.RED, 1.0f));
                break;
            case FREEZE:
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().clone().add(0, 1.0, 0), 3, 0.2, 0.4, 0.2, new Particle.DustOptions(Color.fromRGB(150, 230, 255), 1.0f));
                p.getWorld().spawnParticle(Particle.SNOW_SHOVEL, p.getLocation().clone().add(0, 1.0, 0), 1, 0.2, 0.4, 0.2, 0.01);
                break;
            case WIND:
                p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation().clone().add(0, 0.1, 0), 2, 0.3, 0.1, 0.3, 0.01);
                p.getWorld().spawnParticle(Particle.CRIT, p.getLocation().clone().add(0, 1.0, 0), 1, 0.2, 0.4, 0.2, 0.02);
                break;
            case DEMON:
                p.getWorld().spawnParticle(Particle.LAVA, p.getLocation().clone().add(0, 1.0, 0), 1, 0.2, 0.4, 0.2, 0.05);
                break;
            case NATURE:
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().clone().add(0, 1.0, 0), 3, 0.2, 0.4, 0.2, new Particle.DustOptions(Color.fromRGB(30, 180, 50), 1.0f));
                break;
            case DEATH:
                p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation().clone().add(0, 1.0, 0), 2, 0.2, 0.4, 0.2, 0.01);
                break;
            
            // Legendary Curses
            case DEEP_SEA_CREATURE:
                p.getWorld().spawnParticle(Particle.WATER_WAKE, p.getLocation().clone().add(0, 0.1, 0), 3, 0.3, 0.1, 0.3, 0.02);
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().clone().add(0, 1.0, 0), 3, 0.2, 0.4, 0.2, new Particle.DustOptions(Color.fromRGB(0, 40, 120), 1.0f));
                break;
            case ENDER_DRAGON:
                p.getWorld().spawnParticle(Particle.DRAGON_BREATH, p.getLocation().clone().add(0, 1.0, 0), 2, 0.3, 0.4, 0.3, 0.02);
                p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().clone().add(0, 1.0, 0), 2, 0.2, 0.4, 0.2, 0.05);
                break;
            case WITHER:
                p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation().clone().add(0, 1.0, 0), 3, 0.2, 0.4, 0.2, 0.02);
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().clone().add(0, 1.0, 0), 2, 0.2, 0.4, 0.2, new Particle.DustOptions(Color.BLACK, 1.0f));
                break;
        }
    }

    private static void applyPassives(Player p) {
        CurseType type = CurseManager.getType(p);
        if (type == null) return;
        int level = CurseManager.getLevel(p);

        switch (type) {
            case FIRE:
                // Fire immunity (passive potion effect / mitigation)
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 0, false, false));
                break;
            case STORM:
                // Speed in rain/water
                if (p.getWorld().hasStorm() || p.getLocation().getBlock().getType() == Material.WATER) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, level - 1, false, false));
                }
                break;
            case SHADOW:
                // Night invisibility
                long time = p.getWorld().getTime();
                if (time > 13000 && time < 23000) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0, false, false));
                }
                break;
            case BLOOD:
                // Rage mode on low health (< 4 hearts / 8 HP)
                if (p.getHealth() < 8.0) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, level - 1, false, false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, level - 1, false, false));
                    p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().clone().add(0, 1.0, 0), 2, 0.2, 0.3, 0.2, new Particle.DustOptions(Color.MAROON, 1.2f));
                }
                break;
            case DEMON:
                // Passive strength boost
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, Math.max(0, level - 2), false, false));
                break;
            case NATURE:
                // Passive slow healing
                double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                if (p.getHealth() < maxHealth) {
                    double heal = 0.2 * level;
                    p.setHealth(Math.min(maxHealth, p.getHealth() + heal));
                }
                break;

            // Legendary Passives
            case DEEP_SEA_CREATURE:
                p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 0, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 40, 0, false, false));
                break;
            case WITHER:
                if (p.hasPotionEffect(PotionEffectType.WITHER)) {
                    p.removePotionEffect(PotionEffectType.WITHER);
                }
                break;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!CurseManager.isEnabled()) return;
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();

        // Active Ability Trigger: Sneak + Left Click OR Empty Hand + Right Click
        boolean isRightClickEmpty = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) 
                && (item == null || item.getType() == Material.AIR);
        boolean isSneakLeftClick = (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
                && p.isSneaking();

        if (isRightClickEmpty || isSneakLeftClick) {
            CurseType type = CurseManager.getType(p);
            if (type == null) return;

            // Check Cooldown
            long now = System.currentTimeMillis();
            int level = CurseManager.getLevel(p);
            
            // Legendary curses have a base 45s cooldown, others scale with level
            int cooldownSeconds = (type == CurseType.DEEP_SEA_CREATURE || type == CurseType.ENDER_DRAGON || type == CurseType.WITHER) 
                    ? 40 : (35 - (5 * level));
            
            long nextUse = cooldowns.getOrDefault(p.getUniqueId(), 0L);

            if (now < nextUse) {
                long remaining = (nextUse - now) / 1000 + 1;
                p.sendMessage("§cActive Ability on cooldown for " + remaining + " seconds.");
                return;
            }

            // Trigger Active
            boolean success = executeActiveAbility(p, type, level);
            if (success) {
                cooldowns.put(p.getUniqueId(), now + (cooldownSeconds * 1000L));
            }
        }
    }

    private boolean executeActiveAbility(Player p, CurseType type, int level) {
        switch (type) {
            case FIRE:
                // Fire Burst
                p.sendTitle("§6§lFIRE BURST", "§7Igniting all nearby enemies!", 5, 20, 5);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0f, 1.0f);
                for (Entity target : p.getNearbyEntities(4.0 * level, 3.0, 4.0 * level)) {
                    if (target instanceof LivingEntity && target != p) {
                        LivingEntity le = (LivingEntity) target;
                        le.setFireTicks(80 * level);
                        le.damage(4.0 + (2.0 * level), p);
                        le.getWorld().spawnParticle(Particle.FLAME, le.getLocation(), 15, 0.3, 0.5, 0.3, 0.1);
                    }
                }
                for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 16) {
                    double x = Math.cos(theta) * 3.0 * level;
                    double z = Math.sin(theta) * 3.0 * level;
                    p.getWorld().spawnParticle(Particle.FLAME, p.getLocation().clone().add(x, 0.3, z), 2, 0.0, 0.1, 0.0, 0.02);
                }
                return true;

            case VOID:
                // Void Teleport
                double dist = 6.0 * level;
                Location start = p.getLocation();
                Vector dir = start.getDirection().normalize();
                Location targetLoc = start.clone().add(dir.multiply(dist));
                
                Block block = targetLoc.getBlock();
                Block headBlock = targetLoc.clone().add(0, 1, 0).getBlock();
                if (block.getType().isSolid() || headBlock.getType().isSolid()) {
                    targetLoc = p.getWorld().getHighestBlockAt(targetLoc).getLocation().add(0, 1, 0);
                    targetLoc.setYaw(start.getYaw());
                    targetLoc.setPitch(start.getPitch());
                }

                p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation(), 20, 0.3, 0.8, 0.3, 0.1);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                
                p.teleport(targetLoc);
                
                p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation(), 20, 0.3, 0.8, 0.3, 0.1);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.2f);
                p.sendTitle("§5§lVOID STEP", "§7Flipped through space", 5, 20, 5);
                return true;

            case STORM:
                // Lightning Strike
                Block targetBlock = p.getTargetBlockExact(12 * level);
                if (targetBlock == null) {
                    p.sendMessage("§cLook at a solid block to strike lightning!");
                    return false;
                }
                Location strikeLoc = targetBlock.getLocation();
                p.getWorld().strikeLightning(strikeLoc);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 0.8f);

                for (Entity ent : strikeLoc.getWorld().getNearbyEntities(strikeLoc, 4.0, 4.0, 4.0)) {
                    if (ent instanceof LivingEntity && ent != p) {
                        ((LivingEntity) ent).damage(6.0 + (2.0 * level), p);
                    }
                }
                p.sendTitle("§b§lTEMPEST STRIKE", "§7Lightning summoned!", 5, 20, 5);
                return true;

            case SHADOW:
                // Shadow step
                LivingEntity nearest = null;
                double closest = Double.MAX_VALUE;
                for (Entity ent : p.getNearbyEntities(8.0 * level, 4.0, 8.0 * level)) {
                    if (ent instanceof LivingEntity && ent != p) {
                        double d = p.getLocation().distanceSquared(ent.getLocation());
                        if (d < closest) {
                            closest = d;
                            nearest = (LivingEntity) ent;
                        }
                    }
                }
                if (nearest == null) {
                    p.sendMessage("§cNo shadows nearby to meld into.");
                    return false;
                }
                
                p.getWorld().spawnParticle(Particle.SQUID_INK, p.getLocation(), 15, 0.3, 0.5, 0.3, 0.1);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.8f, 0.6f);

                Vector targetDir = nearest.getLocation().getDirection().normalize();
                Location teleportLoc = nearest.getLocation().clone().subtract(targetDir.multiply(1.2));
                teleportLoc.setYaw(nearest.getLocation().getYaw());
                teleportLoc.setPitch(nearest.getLocation().getPitch());
                
                p.teleport(teleportLoc);
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, level - 1));

                p.getWorld().spawnParticle(Particle.SQUID_INK, p.getLocation(), 15, 0.3, 0.5, 0.3, 0.1);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.8f, 0.8f);
                p.sendTitle("§8§lSHADOW STEP", "§7Emerged from the dark", 5, 20, 5);
                return true;

            case BLOOD:
                // Blood Burst
                p.sendTitle("§c§lBLOOD BURST", "§7Draining surrounding life...", 5, 20, 5);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITCH_DRINK, 1.0f, 0.8f);
                
                double drain = 2.0 + (1.5 * level);
                double healed = 0.0;

                for (Entity ent : p.getNearbyEntities(3.0 * level, 2.0, 3.0 * level)) {
                    if (ent instanceof LivingEntity && ent != p) {
                        LivingEntity le = (LivingEntity) ent;
                        le.damage(drain, p);
                        le.getWorld().spawnParticle(Particle.REDSTONE, le.getLocation().add(0, 1.0, 0), 10, 0.2, 0.4, 0.2, new Particle.DustOptions(Color.fromRGB(139, 0, 0), 1.2f));
                        healed += drain * 0.5;
                    }
                }

                if (healed > 0) {
                    double maxHP = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                    p.setHealth(Math.min(maxHP, p.getHealth() + healed));
                    p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 1.5, 0), 5, 0.2, 0.2, 0.2, 0.1);
                }
                return true;

            case FREEZE:
                // Freeze Box
                p.sendTitle("§3§lGLACIAL IMPRISONMENT", "§7Encasing all nearby entities!", 5, 20, 5);
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 0.8f);

                double rad = 2.0 * level + 1.0;
                int durationTicks = 20 * (2 * level + 2);

                for (Entity ent : p.getNearbyEntities(rad, 2.0, rad)) {
                    if (ent instanceof LivingEntity && ent != p) {
                        LivingEntity le = (LivingEntity) ent;
                        le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, durationTicks, 9, false, false));
                        le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, durationTicks, 0, false, false));
                        
                        Location feetLoc = le.getLocation().getBlock().getLocation();
                        Location headLoc = feetLoc.clone().add(0, 1, 0);
                        Block feetBlock = feetLoc.getBlock();
                        Block freezeHeadBlock = headLoc.getBlock();
                        
                        final Material oldFeet = feetBlock.getType();
                        final Material oldHead = freezeHeadBlock.getType();

                        if (!feetBlock.getType().isSolid() || feetBlock.getType() == Material.AIR) feetBlock.setType(Material.ICE);
                        if (!freezeHeadBlock.getType().isSolid() || freezeHeadBlock.getType() == Material.AIR) freezeHeadBlock.setType(Material.ICE);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                feetBlock.setType(oldFeet);
                                freezeHeadBlock.setType(oldHead);
                            }
                        }.runTaskLater(CurseSMP.get(), durationTicks);

                        le.getWorld().spawnParticle(Particle.SNOW_SHOVEL, le.getLocation().add(0, 1.0, 0), 10, 0.3, 0.5, 0.3, 0.05);
                    }
                }
                return true;

            case WIND:
                // Wind Dash
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0f, 0.8f);
                p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 15, 0.2, 0.2, 0.2, 0.1);
                
                Vector dashDir = p.getLocation().getDirection().normalize();
                p.setVelocity(dashDir.multiply(1.5 + (0.3 * level)).setY(0.2));
                p.sendTitle("§a§lWIND DASH", "§7Slicing through air", 5, 20, 5);
                return true;

            case DEMON:
                // Rage
                p.sendTitle("§d§lDEMON RAGE", "§7Entering demon bloodlust!", 5, 20, 5);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.8f, 1.2f);
                
                int dur = 20 * (5 + (2 * level));
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, dur, 1, false, true));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, dur, 1, false, true));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, dur, level - 1, false, true));
                
                activeRage.add(p.getUniqueId());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        activeRage.remove(p.getUniqueId());
                    }
                }.runTaskLater(CurseSMP.get(), dur);

                new BukkitRunnable() {
                    int t = 0;
                    @Override
                    public void run() {
                        if (t > dur / 5 || !p.isOnline() || !activeRage.contains(p.getUniqueId())) {
                            this.cancel();
                            return;
                        }
                        p.getWorld().spawnParticle(Particle.LAVA, p.getLocation().add(0, 1.0, 0), 2, 0.3, 0.5, 0.3, 0.0);
                        t++;
                    }
                }.runTaskTimer(CurseSMP.get(), 0L, 5L);
                return true;

            case NATURE:
                // Poison trap
                p.sendTitle("§2§lENTANGLING VINES", "§7Trapping and poisoning targets!", 5, 20, 5);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITCH_DRINK, 0.8f, 1.4f);

                double nRad = 3.0 * level;
                int poisonDuration = 20 * (3 + (2 * level));

                for (Entity ent : p.getNearbyEntities(nRad, 2.0, nRad)) {
                    if (ent instanceof LivingEntity && ent != p) {
                        LivingEntity le = (LivingEntity) ent;
                        le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, poisonDuration, 4, false, false));
                        le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, poisonDuration, level - 1, false, false));
                        le.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, le.getLocation(), 15, 0.3, 0.5, 0.3, 0.05);
                    }
                }
                return true;

            case DEATH:
                // Wither Skull
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1.0f, 1.0f);
                WitherSkull skull = p.launchProjectile(WitherSkull.class);
                skull.setShooter(p);
                skull.setCharged(level >= 3);
                skull.setVelocity(p.getLocation().getDirection().multiply(1.5 + (0.3 * level)));
                p.sendTitle("§7§lWITHER BLAST", "§7Unleashed doom!", 5, 20, 5);
                return true;

            // --- Legendary Active Curses ---
            case DEEP_SEA_CREATURE:
                // Sonic Boom + Strength 3 + Resistance 4
                p.sendTitle("§1§lSONIC BOOM", "§9Strength III & Resistance IV Activated", 5, 20, 5);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM, 1.0f, 1.0f);
                
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 2, false, true));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 3, false, true));

                // Shoot Sonic Boom forward up to 15 blocks
                Location eyeLoc = p.getEyeLocation();
                Vector eyeDir = eyeLoc.getDirection().normalize();
                for (double dRange = 1.0; dRange <= 15.0; dRange += 0.5) {
                    Location point = eyeLoc.clone().add(eyeDir.clone().multiply(dRange));
                    p.getWorld().spawnParticle(Particle.SONIC_BOOM, point, 1, 0.0, 0.0, 0.0, 0.0);
                    
                    for (Entity ent : p.getWorld().getNearbyEntities(point, 1.2, 1.2, 1.2)) {
                        if (ent instanceof LivingEntity && ent != p) {
                            LivingEntity le = (LivingEntity) ent;
                            le.damage(14.0, p);
                            le.setVelocity(eyeDir.clone().multiply(1.5).setY(0.4));
                        }
                    }
                }
                return true;

            case ENDER_DRAGON:
                // Cone Dragon's Breath + Gravity Pull
                p.sendTitle("§5§lDRAGON'S BREATH", "§dGravity Pull executed!", 5, 20, 5);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.9f, 1.0f);

                // Cone dragon breath
                Location pEye = p.getEyeLocation();
                Vector pDir = pEye.getDirection().normalize();
                for (int i = 0; i < 20; i++) {
                    double spread = 0.25;
                    Vector randDir = pDir.clone().add(new Vector(
                        (Math.random() - 0.5) * spread,
                        (Math.random() - 0.5) * spread,
                        (Math.random() - 0.5) * spread
                    )).normalize();
                    p.getWorld().spawnParticle(Particle.DRAGON_BREATH, pEye.clone().add(randDir.clone().multiply(2.0)), 2, 0.1, 0.1, 0.1, 0.05);
                }

                for (Entity ent : p.getNearbyEntities(7.0, 4.0, 7.0)) {
                    if (ent instanceof LivingEntity && ent != p) {
                        LivingEntity le = (LivingEntity) ent;
                        le.damage(8.0, p);
                        le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0));
                    }
                }

                // Gravity Pull: Pull all targets within 12 blocks towards the player
                for (Entity ent : p.getNearbyEntities(12.0, 6.0, 12.0)) {
                    if (ent instanceof LivingEntity && ent != p) {
                        Vector pullVec = p.getLocation().toVector().subtract(ent.getLocation().toVector()).normalize().multiply(1.2);
                        pullVec.setY(0.35);
                        ent.setVelocity(pullVec);
                        p.getWorld().spawnParticle(Particle.PORTAL, ent.getLocation(), 5, 0.1, 0.1, 0.1, 0.0);
                    }
                }
                return true;

            case WITHER:
                // Triple Explosive Skulls + AOE Decay
                p.sendTitle("§0§lWITHER DECAY", "§7Triple skull barrage summoned!", 5, 20, 5);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1.0f, 0.9f);

                Vector centerDir = p.getLocation().getDirection().normalize();
                Vector leftDir = rotateVector(centerDir.clone(), 15);
                Vector rightDir = rotateVector(centerDir.clone(), -15);

                WitherSkull s1 = p.launchProjectile(WitherSkull.class);
                s1.setShooter(p);
                s1.setVelocity(centerDir.multiply(1.8));

                WitherSkull s2 = p.launchProjectile(WitherSkull.class);
                s2.setShooter(p);
                s2.setVelocity(leftDir.multiply(1.8));

                WitherSkull s3 = p.launchProjectile(WitherSkull.class);
                s3.setShooter(p);
                s3.setVelocity(rightDir.multiply(1.8));

                for (Entity ent : p.getNearbyEntities(8.0, 4.0, 8.0)) {
                    if (ent instanceof LivingEntity && ent != p) {
                        LivingEntity le = (LivingEntity) ent;
                        le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 160, 2));
                        p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, le.getLocation(), 5, 0.2, 0.2, 0.2, 0.0);
                    }
                }
                return true;
        }
        return false;
    }

    private Vector rotateVector(Vector v, double angleDegrees) {
        double angleRad = Math.toRadians(angleDegrees);
        double cos = Math.cos(angleRad);
        double sin = Math.sin(angleRad);
        double x = v.getX() * cos - v.getZ() * sin;
        double z = v.getX() * sin + v.getZ() * cos;
        return new Vector(x, v.getY(), z);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!CurseManager.isEnabled()) return;
        
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            CurseType type = CurseManager.getType(p);
            if (type == null) return;
            int level = CurseManager.getLevel(p);

            if (type == CurseType.BLOOD && e.getEntity() instanceof LivingEntity) {
                double lifestealRatio = 0.08 * level;
                double heal = e.getFinalDamage() * lifestealRatio;
                double maxHP = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                p.setHealth(Math.min(maxHP, p.getHealth() + heal));
                p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 1.2, 0), 1, 0.1, 0.1, 0.1, 0.0);
            }

            if (type == CurseType.DEATH && e.getEntity() instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) e.getEntity();
                le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40 * level, 0, false, true));
            }
        }
    }

    @EventHandler
    public void onFireDamage(EntityDamageEvent e) {
        if (!CurseManager.isEnabled()) return;
        if (!(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();
        CurseType type = CurseManager.getType(p);
        if (type == null) return;

        // Fire immunity
        if (type == CurseType.FIRE) {
            EntityDamageEvent.DamageCause cause = e.getCause();
            if (cause == EntityDamageEvent.DamageCause.FIRE 
                    || cause == EntityDamageEvent.DamageCause.FIRE_TICK 
                    || cause == EntityDamageEvent.DamageCause.LAVA 
                    || cause == EntityDamageEvent.DamageCause.HOT_FLOOR) {
                e.setCancelled(true);
            }
        }

        // Dragon Fall damage mitigation
        if (type == CurseType.ENDER_DRAGON && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }

        // Fatal Damage -> DEATH Revive Ability
        if (type == CurseType.DEATH) {
            double finalDmg = e.getFinalDamage();
            if (p.getHealth() - finalDmg <= 0) {
                long now = System.currentTimeMillis();
                long nextRevive = reviveCooldowns.getOrDefault(p.getUniqueId(), 0L);
                
                if (now >= nextRevive) {
                    e.setCancelled(true);
                    
                    int level = CurseManager.getLevel(p);
                    double maxHP = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                    p.setHealth(maxHP);
                    
                    p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 0.8f);
                    p.getWorld().strikeLightningEffect(p.getLocation());

                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 160, 4));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, 4));
                    
                    for (Entity ent : p.getNearbyEntities(5.0, 3.0, 5.0)) {
                        if (ent instanceof LivingEntity && ent != p) {
                            ((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
                        }
                    }

                    int reviveCooldownSeconds = 420 - (60 * level);
                    reviveCooldowns.put(p.getUniqueId(), now + (reviveCooldownSeconds * 1000L));
                    
                    p.sendTitle("§4§lDEATH DEFIED", "§7Your curse pulls you back from the grave!", 10, 40, 10);
                }
            }
        }
    }

    // --- Double Jump Mechanics for Wind Curse ---
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!CurseManager.isEnabled()) return;
        Player p = e.getPlayer();
        CurseType type = CurseManager.getType(p);
        
        if (type == CurseType.WIND) {
            Location loc = p.getLocation();
            Block standBlock = loc.clone().subtract(0, 0.1, 0).getBlock();
            if (standBlock.getType().isSolid()) {
                p.setAllowFlight(true);
            }
        }
    }

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent e) {
        if (!CurseManager.isEnabled()) return;
        Player p = e.getPlayer();
        CurseType type = CurseManager.getType(p);

        if (type == CurseType.WIND) {
            if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) return;
            
            e.setCancelled(true);
            p.setAllowFlight(false);
            p.setFlying(false);

            Vector jumpDir = p.getLocation().getDirection().multiply(0.85);
            jumpDir.setY(0.7 + (0.05 * CurseManager.getLevel(p)));
            p.setVelocity(jumpDir);

            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0f, 1.5f);
            p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 12, 0.2, 0.1, 0.2, 0.05);
        }
    }
}
