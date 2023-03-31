package com.ssblur.scriptor.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.ssblur.scriptor.helpers.DictionarySavedData;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class DumpDictionaryCommand {
  public static void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection selection){
    dispatcher.register(Commands.literal("dump_dictionary")
      .requires(s -> s.hasPermission(4))
      .executes(DumpDictionaryCommand::execute));
  }
  private static int execute(CommandContext<CommandSourceStack> command){
    if(command.getSource().getEntity() instanceof Player player)
      player.sendMessage(new TextComponent(DictionarySavedData.computeIfAbsent((ServerLevel) player.level).toString()), Util.NIL_UUID);
    return Command.SINGLE_SUCCESS;
  }
}
