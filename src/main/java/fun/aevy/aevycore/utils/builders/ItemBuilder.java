package fun.aevy.aevycore.utils.builders;

import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Easily create itemstacks, without messing your hands.
 * <i>Note that if you do use this in one of your projects, leave this notice.</i>
 * <i>Please do credit me if you do use this in one of your projects.</i>
 * @author NonameSL
 */
public class ItemBuilder
{
    private final ItemStack is;

    /**
     * Create a new ItemBuilder from scratch.
     * @param m The material to create the ItemBuilder with.
     */
    public ItemBuilder(Material m)
    {
        this(m, 1);
    }

    /**
     * Create a new ItemBuilder over an existing itemstack.
     * @param is The itemstack to create the ItemBuilder over.
     */
    public ItemBuilder(ItemStack is)
    {
        this.is = is;
    }

    /**
     * Create a new ItemBuilder from scratch.
     * @param m The material of the item.
     * @param amount The amount of the item.
     */
    public ItemBuilder(Material m, int amount)
    {
        is = new ItemStack(m, amount);
    }

    /**
     * Create a new ItemBuilder from scratch.
     * @param m The material of the item.
     * @param amount The amount of the item.
     * @param durability The durability of the item.
     */
    public ItemBuilder(Material m, int amount, byte durability)
    {
        is = new ItemStack(m, amount, durability);
    }

    /**
     * Clone the ItemBuilder into a new one.
     * @return The cloned instance.
     */
    public ItemBuilder clone()
    {
        return new ItemBuilder(is);
    }

    /**
     * Change the durability of the item.
     * @param dur The durability to set it to.
     * @return The item with the new durability.
     */
    public ItemBuilder setDurability(short dur)
    {
        is.setDurability(dur);
        return this;
    }

    /**
     * Adds a flag to the item.
     * @param itemFlag The flag to be added.
     * @return The item with the new flag.
     */
    public ItemBuilder addItemFlag(ItemFlag itemFlag)
    {
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(itemFlag);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Sets an item as unbreakable or not.
     * @param bool Set the item as unbreakable?
     * @return The changed ItemBuilder.
     */
    public ItemBuilder setUnbreakable(boolean bool)
    {
        ItemMeta im = is.getItemMeta();
        im.setUnbreakable(bool);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Set the display name of the item.
     * @param name The name to change it to.
     * @return The item with the new name.
     */
    public ItemBuilder setName(String name)
    {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add an unsafe enchantment.
     * @param enchantment The enchantment to add.
     * @param level The level to put the enchant on.
     * @return The item with the new unsafe enchantment.
     */
    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level)
    {
        is.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Remove a certain enchant from the item.
     * @param enchantment The enchantment to remove
     * @return The item with the enchantment removed.
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment)
    {
        is.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Set the skull owner for the item. Works on skulls only.
     * @param owner The name of the skull's owner.
     * @return The skull with a new owner.
     */
    @SneakyThrows
    public ItemBuilder setSkullOwner(String owner)
    {
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setOwner(owner);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add an enchant to the item.
     * @param enchantment The enchant to add
     * @param level The level
     * @return The item with the enchantment added.
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level)
    {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(enchantment, level, true);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add multiple enchants at once.
     * @param enchantments The enchants to add.
     * @return The item with the enchantment added.
     */
    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments)
    {
        is.addEnchantments(enchantments);
        return this;
    }

    /**
     * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
     * @return The item with infinite durability.
     */
    public ItemBuilder setInfiniteDurability()
    {
        is.setDurability(Short.MAX_VALUE);
        return this;
    }

    /**
     * Re-sets the lore.
     * @param lore The lore to set it to.
     * @return The item with the new lore.
     */
    public ItemBuilder setLore(String... lore)
    {
        return setLore(Arrays.asList(lore));
    }

    /**
     * Re-sets the lore.
     * @param lore The lore to set it to.
     * @return The item with the new lore.
     */
    public ItemBuilder setLore(List<String> lore)
    {
        ItemMeta im = is.getItemMeta();
        List<String> list = new ArrayList<>();
        lore.forEach(s -> list.add(ChatColor.translateAlternateColorCodes('&', s)));
        im.setLore(list);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Remove a lore line.
     * @return The item with a lore line removed.
     */
    public ItemBuilder removeLoreLine(String line)
    {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());

        if (!lore.contains(line))
            return this;

        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Remove a lore line.
     * @param index The index of the lore line to remove.
     * @return The item with the lore line removed.
     */
    public ItemBuilder removeLoreLine(int index)
    {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());

        if (index < 0 || index > lore.size())
            return this;

        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add a lore line.
     * @param line The lore line to add.
     * @return The item with the new lore line added.
     */
    public ItemBuilder addLoreLine(String line)
    {
        ItemMeta im = is.getItemMeta();

        List<String> lore = im.hasLore() ? new ArrayList<>(im.getLore()) : new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', line));

        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add a lore line.
     * @param line The lore line to add.
     * @param pos The index of where to put it.
     * @return The item with the new lore line added.
     */
    public ItemBuilder addLoreLine(String line, int pos)
    {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Sets the dye color on an item.
     * <b>* Notice that this doesn't check for item type, sets the literal data of the dyecolor as durability.</b>
     * @param color The color to put.
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder setDyeColor(DyeColor color)
    {
        is.setDurability(color.getDyeData());
        return this;
    }

    /**
     * Sets the dye color of a wool item. Works only on wool.
     * @deprecated As of version 1.2 changed to setDyeColor.
     * @see ItemBuilder#setDyeColor(DyeColor)
     * @param color The DyeColor to set the wool item to.
     * @return The colored wool.
     */
    @Deprecated
    public ItemBuilder setWoolColor(DyeColor color)
    {
        if (!is.getType().equals(Material.WOOL))
            return this;

        is.setDurability(color.getDyeData());
        return this;
    }

    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor pieces.
     * @param color The color to set it to.
     * @return The colored leather armor.
     */
    @SneakyThrows
    public ItemBuilder setLeatherArmorColor(Color color)
    {
        LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
        im.setColor(color);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Retrieves the itemstack from the ItemBuilder.
     * @return The itemstack created/modified by the ItemBuilder instance.
     */
    public ItemStack toItemStack()
    {
        return is;
    }

}
