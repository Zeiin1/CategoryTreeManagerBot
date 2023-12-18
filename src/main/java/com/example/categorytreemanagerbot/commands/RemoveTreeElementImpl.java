package com.example.categorytreemanagerbot.commands;

import com.example.categorytreemanagerbot.bot.BotCommand;
import com.example.categorytreemanagerbot.entity.CategoryTreeRequest;
import com.example.categorytreemanagerbot.service.CategoryTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RemoveTreeElementImpl implements BotCommand {
    //класс HelpImpl отвечает за команду /help
    private final CategoryTreeService categoryTreeService;
    @Autowired
    public RemoveTreeElementImpl(CategoryTreeService categoryTreeService)
    {
        this.categoryTreeService = categoryTreeService;
    }
    @Override
    public SendMessage execute(Update update) {
        long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        String result = "";
        String[] parts = messageText.split(" ");
        if(parts.length == 2)
        {
            //проверяем что у команды есть название элемента для удаления
            CategoryTreeRequest categoryTreeRequest = new CategoryTreeRequest();
            categoryTreeRequest.setName(parts[1]);
            result = categoryTreeService.deleteCategoryTree(categoryTreeRequest);

        }

        else {
            result = "Пожайлуйста напишите название элемента для удаления";
        }



        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(result);
        return sendMessage;
    }
}
