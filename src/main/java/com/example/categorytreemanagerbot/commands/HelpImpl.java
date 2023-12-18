package com.example.categorytreemanagerbot.commands;

import com.example.categorytreemanagerbot.bot.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpImpl implements BotCommand {
    @Override
    public SendMessage execute(Update update) {
        long chatId = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage();
        String result = "/viewTree показывает дерево категорий в наличие бд  \n " +
                "/addElement <название элемента> добавление корнегого элемента, можно запросить только один раз \n" +
                "/addElement <родительский элемент> <дочерний элемент> добавление нового элемента в дочерный список \n" +
                "/removeElement <название элемента> удалить элемент с названием \n, если у элемента есть дети они тоже будут" +
                "удалены" +
                "/help - -выводит список комманд \n";

        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(result);
        return sendMessage;
        //класс HelpImpl отвечает за команду /help
    }
}
