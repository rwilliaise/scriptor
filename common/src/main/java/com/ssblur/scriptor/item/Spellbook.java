package com.ssblur.scriptor.item;

import com.ssblur.scriptor.helpers.DictionarySavedData;
import com.ssblur.scriptor.helpers.LimitedBookSerializer;
import com.ssblur.scriptor.word.Spell;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Spellbook extends Item {
  public Spellbook(Properties properties) {
    super(properties);
  }

  @Override
  public Component getName(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getTag();
    if (compoundTag != null) {
      String string = compoundTag.getString("title");
      if (!StringUtil.isNullOrEmpty(string)) {
        return new TextComponent(string);
      }
    }

    return super.getName(itemStack);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
    var result = super.use(level, player, interactionHand);

    Tag tag = player.getItemInHand(interactionHand).getTag();
    if(tag instanceof CompoundTag compound && level instanceof ServerLevel server) {
      var text = compound.getList("pages", Tag.TAG_STRING);
      Spell spell = DictionarySavedData.computeIfAbsent(server).parse(LimitedBookSerializer.decodeText(text));
      if(spell != null) {
        if(spell.cost() > 50) {
          player.sendMessage(new TranslatableComponent("extra.scriptor.fizzle"), Util.NIL_UUID);
          player.getCooldowns().addCooldown(this, 350);
        }
        spell.cast(player);
        if(!player.isCreative())
          player.getCooldowns().addCooldown(this, (int) Math.round(spell.cost() * 7));
      }
    }

    return result;
  }

  public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction clickAction, Player player) {
    if (clickAction != ClickAction.SECONDARY) {
      return false;
    } else {

    }
    return false;
  }
}
