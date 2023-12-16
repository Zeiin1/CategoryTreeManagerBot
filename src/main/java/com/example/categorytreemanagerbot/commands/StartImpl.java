package com.example.categorytreemanagerbot.commands;

import com.example.categorytreemanagerbot.bot.BotCommand;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Update;

public class StartImpl implements BotCommand {

    @Override
    public SendMessage execute(Update update) {
        long chatId = update.getMessage().getChatId();
        String name = update.getMessage().getChat().getFirstName();
        SendMessage sendMessage = new SendMessage();
        String result = "Привет,"+name+", для получения информаций как работать с Telegram-ботом для управления " +
                "деревом категорий напиши /help ";

        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(result);
        return sendMessage;

    }
}
