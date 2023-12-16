package com.example.categorytreemanagerbot.bot;

import com.example.categorytreemanagerbot.commands.*;

import com.example.categorytreemanagerbot.config.BotConfig;

import com.example.categorytreemanagerbot.service.CategoryTreeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private BotConfig botConfig;
    @Autowired
    private CategoryTreeImpl categoryTreeService;
    private Map<String, BotCommand> commandMap;
    public TelegramBot(BotConfig botConfig,CategoryTreeImpl categoryTreeService)
    {
        this.categoryTreeService = categoryTreeService;
        this.botConfig = botConfig;
        commandMap = new HashMap<>();
        commandMap.put("/start", new StartImpl());
        commandMap.put("/addElement", new SaveTreeImpl(categoryTreeService));
        commandMap.put("/help", new HelpImpl());
        commandMap.put("/viewTree", new ViewTreeImpl(categoryTreeService));
        commandMap.put("/removeElement", new RemoveTreeElementImpl(categoryTreeService));

    }
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String[] parts = messageText.split(" ");

            if (parts.length > 0) {
                String command = parts[0]; // Extract the command

                BotCommand botCommand = commandMap.get(command);
                if (botCommand != null) {
                   SendMessage sendMessage = botCommand.execute(update);
                   sendMessage(Long.parseLong(sendMessage.getChatId()),sendMessage.getText());
                } else {

                    sendMessage(chatId, "Unknown command");
                }
            }
        }

    }
    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
