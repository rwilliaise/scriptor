package com.ssblur.scriptor.item;

import com.ssblur.scriptor.events.TomeReloadListener;
import com.ssblur.scriptor.helpers.DictionarySavedData;
import com.ssblur.scriptor.helpers.LimitedBookSerializer;
import com.ssblur.scriptor.helpers.TomeResource;
import com.ssblur.scriptor.word.Spell;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AncientSpellbook extends Item {
  int tier;
  public AncientSpellbook(Properties properties, int tier) {
    super(properties.stacksTo(1));
    this.tier = tier;
  }

  @Override
  public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
    super.appendHoverText(itemStack, level, list, tooltipFlag);
    list.add(new TranslatableComponent("extra.scriptor.tome_description"));
    list.add(new TranslatableComponent("extra.scriptor.tome_tier", tier));
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
    var result = super.use(level, player, interactionHand);

    if(!level.isClientSide) {
      ServerLevel server = (ServerLevel) level;

      player.sendMessage(new TranslatableComponent("extra.scriptor.tome_use"), Util.NIL_UUID);
      player.getCooldowns().addCooldown(this, 20);

      var resource = TomeReloadListener.INSTANCE.getRandomTome(tier);
      System.out.println(resource.getName());
      Spell spell = resource.getSpell();
      String sentence = DictionarySavedData.computeIfAbsent(server).generate(spell);
      player.setItemInHand(interactionHand, LimitedBookSerializer.createSpellbook(resource.getAuthor(), resource.getName(), sentence));
      player.sendMessage(new TranslatableComponent("extra.scriptor.spell_get", resource.getName()), Util.NIL_UUID);
      return InteractionResultHolder.consume(player.getItemInHand(interactionHand));
    }

    return result;
  }
}
