package com.ssblur.scriptor.word.action;

import com.ssblur.scriptor.helpers.targetable.EntityTargetable;
import com.ssblur.scriptor.helpers.targetable.Targetable;
import com.ssblur.scriptor.word.descriptor.Descriptor;
import com.ssblur.scriptor.word.descriptor.StrengthDescriptor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HarmAction extends Action {
  @Override
  public void apply(Entity caster, Targetable targetable, Descriptor[] descriptors) {
    double strength = 1;
    for(var d: descriptors) {
      if(d instanceof StrengthDescriptor strengthDescriptor)
        strength += strengthDescriptor.strengthModifier();
    }

    strength *= 2;
    if(targetable instanceof EntityTargetable entityTargetable) {
      Entity entity = entityTargetable.getTargetEntity();
      if(entity instanceof LivingEntity target) {
        if(caster instanceof Player player)
          target.hurt(DamageSource.indirectMagic(caster, player), (int) Math.round(strength));
        else
          target.hurt(DamageSource.indirectMagic(caster, null), (int) Math.round(strength));
      }
    }
  }
  @Override
  public Cost cost() { return new Cost(3, COSTTYPE.ADDITIVE); }
}
