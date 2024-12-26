package me.clearedspore.Commands.Items;

import me.clearedspore.easycommands;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;

public class Item implements CommandExecutor, TabCompleter {
    private final easycommands easycommands;
    private final Map<String, Enchantment> vanillaEnchantmentMap = createEnchantmentMap();
    private final Map<String, Attribute> vanillaAttributeMap = createAttributeMap();

    public Item(me.clearedspore.easycommands easycommands) {
        this.easycommands = easycommands;
    }

    private Map<String, Enchantment> createEnchantmentMap() {
        Map<String, Enchantment> map = new HashMap<>();
        map.put("efficiency", Enchantment.EFFICIENCY);
        map.put("sharpness", Enchantment.SHARPNESS);
        map.put("unbreaking", Enchantment.UNBREAKING);
        map.put("fortune", Enchantment.FORTUNE);
        map.put("silk_touch", Enchantment.SILK_TOUCH);
        map.put("looting", Enchantment.LOOTING);
        map.put("mending", Enchantment.MENDING);
        map.put("power", Enchantment.POWER);
        map.put("punch", Enchantment.PUNCH);
        map.put("flame", Enchantment.FLAME);
        map.put("infinity", Enchantment.INFINITY);
        map.put("respiration", Enchantment.RESPIRATION);
        map.put("aqua_affinity", Enchantment.AQUA_AFFINITY);
        map.put("depth_strider", Enchantment.DEPTH_STRIDER);
        map.put("frost_walker", Enchantment.FROST_WALKER);
        map.put("binding_curse", Enchantment.BINDING_CURSE);
        map.put("vanishing_curse", Enchantment.VANISHING_CURSE);
        map.put("protection", Enchantment.PROTECTION);
        map.put("fire_protection", Enchantment.FIRE_PROTECTION);
        map.put("blast_protection", Enchantment.BLAST_PROTECTION);
        map.put("projectile_protection", Enchantment.PROJECTILE_PROTECTION);
        map.put("thorns", Enchantment.THORNS);
        map.put("feather_falling", Enchantment.FEATHER_FALLING);
        map.put("soul_speed", Enchantment.SOUL_SPEED);
        map.put("quick_charge", Enchantment.QUICK_CHARGE);
        map.put("piercing", Enchantment.PIERCING);
        map.put("channeling", Enchantment.CHANNELING);
        map.put("riptide", Enchantment.RIPTIDE);
        map.put("impaling", Enchantment.IMPALING);
        map.put("multishot", Enchantment.MULTISHOT);
        // Add any other enchantments supported by your server version
        return map;
    }

    private Map<String, Attribute> createAttributeMap() {
        Map<String, Attribute> map = new HashMap<>();
        map.put("generic.max_health", Attribute.GENERIC_MAX_HEALTH);
        map.put("generic.attack_damage", Attribute.GENERIC_ATTACK_DAMAGE);
        map.put("generic.movement_speed", Attribute.GENERIC_MOVEMENT_SPEED);
        // Add other attributes as needed
        return map;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("easycommands.item.*") && args.length > 0 && !player.hasPermission("easycommands.item." + args[0].toLowerCase())) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "You must be holding an item to use this command.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /item <arguments>");
            return true;
        }

        ItemMeta meta = item.getItemMeta();

        switch (args[0].toLowerCase()) {
            case "addattribute":
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "Usage: /item addattribute <attribute> <value>");
                    return true;
                }

                try {
                    Attribute attribute = Attribute.valueOf(args[1].toUpperCase());
                    double value = Double.parseDouble(args[2]);

                    ItemMeta Meta = item.getItemMeta();
                    if (Meta == null) {
                        player.sendMessage(ChatColor.RED + "This item does not support attributes.");
                        return true;
                    }

                    AttributeModifier modifier = new AttributeModifier(
                            UUID.randomUUID(),
                            attribute.name(),
                            value,
                            AttributeModifier.Operation.ADD_NUMBER
                    );

                    Meta.addAttributeModifier(attribute, modifier);
                    item.setItemMeta(Meta);
                    player.sendMessage(ChatColor.GREEN + "Added attribute: " + attribute.name() + " with value " + value);
                } catch (IllegalArgumentException e) {
                    player.sendMessage(ChatColor.RED + "Invalid attribute or value. Use /item addattribute for a list of attributes.");
                } catch (UnsupportedOperationException e) {
                    player.sendMessage(ChatColor.RED + "This item does not support attributes.");
                }
                break;
            case "removeattribute":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /item removeattribute <attribute>");
                    return true;
                }

                try {
                    Attribute attribute = Attribute.valueOf(args[1].toUpperCase());
                    ItemMeta Meta = item.getItemMeta();

                    if (Meta == null || !Meta.hasAttributeModifiers() || Meta.getAttributeModifiers(attribute) == null) {
                        player.sendMessage(ChatColor.RED + "This item has no modifiers for that attribute.");
                        return true;
                    }

                    Meta.removeAttributeModifier(attribute);
                    item.setItemMeta(Meta);
                    player.sendMessage(ChatColor.GREEN + "Removed attribute: " + attribute.name());
                } catch (IllegalArgumentException e) {
                    player.sendMessage(ChatColor.RED + "Invalid attribute name.");
                } catch (UnsupportedOperationException e) {
                    player.sendMessage(ChatColor.RED + "This item does not support attributes.");
                }
                break;
            case "removelore":
                if (!meta.hasLore()) {
                    player.sendMessage(ChatColor.RED + "This item has no lore to remove.");
                    return true;
                }

                List<String> currentLore = meta.getLore();
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /item removelore <line>");
                    return true;
                }

                try {
                    int line = Integer.parseInt(args[1]) - 1; // Convert to 0-based index
                    if (line < 0 || line >= currentLore.size()) {
                        player.sendMessage(ChatColor.RED + "Invalid line number. Use a number between 1 and " + currentLore.size());
                        return true;
                    }

                    String removedLine = currentLore.remove(line);
                    meta.setLore(currentLore);
                    item.setItemMeta(meta);
                    player.sendMessage(ChatColor.GREEN + "Removed lore line: " + ChatColor.stripColor(removedLine));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid line number. Please use a number.");
                }
                break;
            case "rename":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /item rename <name>");
                    return true;
                }
                String newName = ChatColor.translateAlternateColorCodes('&', String.join(" ", args).substring(7));
                meta.setDisplayName(newName);
                item.setItemMeta(meta);
                player.sendMessage(ChatColor.GREEN + "Item renamed to: " + newName);
                break;

            case "addlore":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /item addlore <lore>");
                    return true;
                }
                String newLore = ChatColor.translateAlternateColorCodes('&', String.join(" ", args).substring(8));
                List<String> lore = meta.hasLore() ? meta.getLore() : new java.util.ArrayList<>();
                lore.add(newLore);
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.sendMessage(ChatColor.GREEN + "Added lore: " + newLore);
                break;
            case "enchant":
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "Usage: /item enchant <enchantment> <level>");
                    return true;
                }
                try {
                    Enchantment enchantment = Enchantment.getByName(args[1].toUpperCase());
                    int level = Integer.parseInt(args[2]);
                    if (enchantment == null || level < 1 || level > 225) {
                        throw new IllegalArgumentException();
                    }
                    item.addUnsafeEnchantment(enchantment, level);
                    player.sendMessage(ChatColor.GREEN + "Enchantment " + args[1] + " added with level " + level);
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Invalid enchantment or level.");
                }
                break;
            case "enchantremove":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /item enchantremove <enchantment>");
                    return true;
                }

                String enchantmentToRemove = args[1].toLowerCase();
                Enchantment removeEnchant = vanillaEnchantmentMap.get(enchantmentToRemove);

                if (removeEnchant == null || !item.containsEnchantment(removeEnchant)) {
                    player.sendMessage(ChatColor.RED + "Invalid enchantment name or the item does not have this enchantment.");
                    return true;
                }

                item.removeEnchantment(removeEnchant);
                player.sendMessage(ChatColor.GREEN + "Removed enchantment: " + enchantmentToRemove);
                break;
            case "proprety":
                handlePropertyCommand(player, meta, args, item);
                break;

            case "unbreakable":
                if (meta != null) {
                    meta.setUnbreakable(true);
                    item.setItemMeta(meta);
                    player.sendMessage(ChatColor.GREEN + "Item is now unbreakable.");
                } else {
                    player.sendMessage(ChatColor.RED + "This item cannot be made unbreakable.");
                }
                break;

            case "durability":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /item durability <value>");
                    return true;
                }

                try {
                    int durability = Integer.parseInt(args[1]);
                    int maxDurability = item.getType().getMaxDurability();

                    if (durability < 0 || durability > maxDurability) {
                        player.sendMessage(ChatColor.RED + "Durability must be between 0 and " + maxDurability + ".");
                        return true;
                    }

                    item.setDurability((short) (maxDurability - durability));
                    player.sendMessage(ChatColor.GREEN + "Durability set to: " + durability);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid durability value.");
                }
                break;

            case "glow":
                toggleGlow(player, meta, item);
                break;
            default:
                player.sendMessage(ChatColor.RED + "Unknown argument: " + args[0]);
        }

        return true;
    }
    private void handlePropertyCommand(Player player, ItemMeta meta, String[] args, ItemStack item) {
        if (args.length < 3) {
            player.sendMessage(ChatColor.RED + "Usage: /item proprety <type> <value>");
            return;
        }

        String type = args[1].toLowerCase();
        String value = args[2].toLowerCase();

        switch (type) {
            case "attribute":
                toggleItemFlag(player, meta, item, ItemFlag.HIDE_ATTRIBUTES, value);
                break;

            case "enchantments":
                toggleItemFlag(player, meta, item, ItemFlag.HIDE_ENCHANTS, value);
                break;

            case "unbreakable":
                toggleItemFlag(player, meta, item, ItemFlag.HIDE_UNBREAKABLE, value);
                break;
            case "durability":
                toggleItemFlag(player, meta, item, ItemFlag.HIDE_DESTROYS, value);
                break;

            default:
                player.sendMessage(ChatColor.RED + "Unknown property type: " + type);
        }
    }
    private void toggleItemFlag(Player player, ItemMeta meta, ItemStack item, ItemFlag flag, String value) {
        if (value.equals("show")) {
            meta.removeItemFlags(flag);
            player.sendMessage(ChatColor.GREEN + "Property is now visible.");
        } else if (value.equals("hide")) {
            meta.addItemFlags(flag);
            player.sendMessage(ChatColor.GREEN + "Property is now hidden.");
        } else {
            player.sendMessage(ChatColor.RED + "Invalid value. Use 'show' or 'hide'.");
            return;
        }
        item.setItemMeta(meta);
    }



    private void toggleGlow(Player player, ItemMeta meta, ItemStack item) {
        if(meta.hasEnchantmentGlintOverride()){
            meta.setEnchantmentGlintOverride(false);
            player.sendMessage(ChatColor.GREEN + "Glow disabled");
        } else {
            meta.setEnchantmentGlintOverride(true);
            player.sendMessage(ChatColor.GREEN + "Glow enabled");
        }
        item.setItemMeta(meta);
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            return Arrays.asList("rename", "addlore", "removelore", "glow", "enchant", "enchantremove", "addattribute", "removeattribute", "proprety", "unbreakable", "durability").stream()
                    .filter(arg -> player.hasPermission("easycommands.item." + arg) || player.hasPermission("easycommands.item.*"))
                    .filter(arg -> arg.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args[0].equalsIgnoreCase("enchant") && args.length == 2) {
            return createEnchantmentMap().keySet().stream()
                    .filter(name -> name.startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args[0].equalsIgnoreCase("addattribute") && args.length == 2) {
            return Arrays.stream(Attribute.values())
                    .map(attr -> attr.name().toLowerCase())
                    .filter(name -> name.startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (args[0].equalsIgnoreCase("removeattribute") && args.length == 2) {
            ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
            if (meta == null || !meta.hasAttributeModifiers()) return new ArrayList<>();

            return meta.getAttributeModifiers().keySet().stream()
                    .map(attr -> attr.name().toLowerCase())
                    .filter(name -> name.startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (args[0].equalsIgnoreCase("removelore") && args.length == 2) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List<String> lore = item.getItemMeta().getLore();
                return lore.stream()
                        .map(line -> String.valueOf(lore.indexOf(line) + 1))
                        .filter(line -> line.startsWith(args[1]))
                        .collect(Collectors.toList());
            }
            return new ArrayList<>();
        }
        if (args[0].equalsIgnoreCase("enchantremove") && args.length == 2) {
            ItemStack item = player.getInventory().getItemInMainHand();
            return item.getEnchantments().keySet().stream()
                    .map(enchantment -> enchantment.getKey().getKey().toLowerCase().replace("_", " "))
                    .filter(name -> name.startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (args[0].equalsIgnoreCase("proprety") && args.length == 2) {
            return Arrays.asList("attribute", "enchantments", "unbreakable", "durability")
                    .stream()
                    .filter(arg -> arg.startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args[0].equalsIgnoreCase("proprety") && args.length == 3) {
            return Arrays.asList("show", "hide")
                    .stream()
                    .filter(arg -> arg.startsWith(args[2].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();

    }
}


