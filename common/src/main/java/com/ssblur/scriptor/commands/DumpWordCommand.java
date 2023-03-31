package com.ssblur.scriptor.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.ssblur.scriptor.helpers.DictionarySavedData;
import com.ssblur.scriptor.registry.WordRegistry;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class DumpWordCommand {
  public static void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection selection){
    var command = Commands.literal("dump_word").requires(s -> s.hasPermission(4)).executes(DumpWordCommand::execute);

    for(String key: WordRegistry.INSTANCE.actionRegistry.keySet())
      command = command.then(Commands
        .literal("action:" + key)
        .requires(s -> s.hasPermission(4))
        .executes(generateActionCommand(key)));
    for(String key: WordRegistry.INSTANCE.descriptorRegistry.keySet())
      command = command.then(Commands
        .literal("descriptor:" + key)
        .requires(s -> s.hasPermission(4))
        .executes(generateDescriptorCommand(key)));
    for(String key: WordRegistry.INSTANCE.subjectRegistry.keySet())
      command = command.then(Commands
        .literal("subject:" + key)
        .requires(s -> s.hasPermission(4))
        .executes(generateSubjectCommand(key)));

    dispatcher.register(command);
  }

  private static Command<CommandSourceStack> generateActionCommand(String key) {
    return command -> {
      if(command.getSource().getEntity() instanceof Player player) {
        var dict = DictionarySavedData.computeIfAbsent((ServerLevel) player.level);
        var data = dict.getWord("action:" + key);
        player.sendMessage(new TextComponent("The word for action \"" + key + "\" is \"" + data.word() + "\""), Util.NIL_UUID);
        if(dict.actionArticlePosition == DictionarySavedData.ARTICLEPOSITION.AFTER)
          player.sendMessage(
            new TextComponent(
              "This word should be proceeded with the article \""
                + dict.actionGenderedArticles.get(data.gender())
                + "\""), Util.NIL_UUID);
        else if(dict.actionArticlePosition == DictionarySavedData.ARTICLEPOSITION.BEFORE)
          player.sendMessage(
            new TextComponent(
              "This word should be preceeded with the article \""
                + dict.actionGenderedArticles.get(data.gender())
                + "\""), Util.NIL_UUID);

      }
      return Command.SINGLE_SUCCESS;
    };
  }

  private static Command<CommandSourceStack> generateDescriptorCommand(String key) {
    return command -> {
      if(command.getSource().getEntity() instanceof Player player) {
        var dict = DictionarySavedData.computeIfAbsent((ServerLevel) player.level);
        var data = dict.getWord("descriptor:" + key);
        player.sendMessage(new TextComponent("The word for descriptor \"" + key + "\" is \"" + data.word() + "\""), Util.NIL_UUID);
        if(dict.descriptorArticlePosition == DictionarySavedData.ARTICLEPOSITION.AFTER)
          player.sendMessage(
            new TextComponent(
              "This word should be proceeded with the article \""
                + dict.descriptorGenderedArticles.get(data.gender())
                + "\""), Util.NIL_UUID);
        else if(dict.descriptorArticlePosition == DictionarySavedData.ARTICLEPOSITION.BEFORE)
          player.sendMessage(
            new TextComponent(
              "This word should be preceeded with the article \""
                + dict.descriptorGenderedArticles.get(data.gender())
                + "\""), Util.NIL_UUID);

      }
      return Command.SINGLE_SUCCESS;
    };
  }

  private static Command<CommandSourceStack> generateSubjectCommand(String key) {
    return command -> {
      if(command.getSource().getEntity() instanceof Player player) {
        var dict = DictionarySavedData.computeIfAbsent((ServerLevel) player.level);
        var data = dict.getWord("subject:" + key);
        player.sendMessage(new TextComponent("The word for subject \"" + key + "\" is \"" + data.word() + "\""), Util.NIL_UUID);
        if(dict.subjectArticlePosition == DictionarySavedData.ARTICLEPOSITION.AFTER)
          player.sendMessage(
            new TextComponent(
              "This word should be proceeded with the article \""
                + dict.subjectGenderedArticles.get(data.gender())
                + "\""), Util.NIL_UUID);
        else if(dict.subjectArticlePosition == DictionarySavedData.ARTICLEPOSITION.BEFORE)
          player.sendMessage(
            new TextComponent(
              "This word should be preceeded with the article \""
                + dict.subjectGenderedArticles.get(data.gender())
                + "\""), Util.NIL_UUID);

      }
      return Command.SINGLE_SUCCESS;
    };
  }

  private static int execute(CommandContext<CommandSourceStack> command){
    if(command.getSource().getEntity() instanceof Player player)
      player.sendMessage(new TextComponent("Please specify a word to dump."), Util.NIL_UUID);
    return Command.SINGLE_SUCCESS;
  }
}
