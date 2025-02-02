package com.ssblur.scriptor.item;

import com.ssblur.scriptor.ScriptorMod;
import com.ssblur.scriptor.block.ScriptorBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ScriptorItems {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ScriptorMod.MOD_ID, Registry.ITEM_REGISTRY);
  public static final RegistrySupplier<Item> SPELLBOOK = ITEMS.register("spellbook", () ->
    new Spellbook(new Item.Properties()));
  public static final RegistrySupplier<Item> SPELLBOOK_BINDER = ITEMS.register("spellbook_binder", () ->
    new Item(new Item.Properties().tab(ScriptorMod.SCRIPTOR_TAB)));
  public static final RegistrySupplier<Item> LEATHER_BINDER = ITEMS.register("leather_binder", () ->
    new Item(new Item.Properties().tab(ScriptorMod.SCRIPTOR_TAB)));
  public static final RegistrySupplier<Item> TOME_TIER1 = ITEMS.register("tome_tier1", () ->
    new AncientSpellbook(new Item.Properties().tab(ScriptorMod.SCRIPTOR_TAB), 1));
  public static final RegistrySupplier<Item> TOME_TIER2 = ITEMS.register("tome_tier2", () ->
    new AncientSpellbook(new Item.Properties().tab(ScriptorMod.SCRIPTOR_TAB), 2));
  public static final RegistrySupplier<Item> TOME_TIER3 = ITEMS.register("tome_tier3", () ->
    new AncientSpellbook(new Item.Properties().tab(ScriptorMod.SCRIPTOR_TAB), 3));
  public static final RegistrySupplier<Item> TOME_TIER4 = ITEMS.register("tome_tier4", () ->
    new AncientSpellbook(new Item.Properties().tab(ScriptorMod.SCRIPTOR_TAB), 4));

  public static void register() {
    ITEMS.register();
  }
}
