package com.example.categorytreemanagerbot.commands;

import com.example.categorytreemanagerbot.bot.BotCommand;
import com.example.categorytreemanagerbot.entity.CategoryTreeRequest;
import com.example.categorytreemanagerbot.service.CategoryTreeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SaveTreeImpl implements BotCommand {
    private final CategoryTreeService categoryTreeService;
    @Autowired
    public SaveTreeImpl(CategoryTreeService categoryTreeService)
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

            CategoryTreeRequest categoryTreeRequest = new CategoryTreeRequest(parts[1]);
            result = categoryTreeService.save(categoryTreeRequest);

        }
        else if(parts.length ==3){
            String parent = parts[1];
            String child = parts[2];

            CategoryTreeRequest categoryTreeRequest1 = new CategoryTreeRequest(parent,child);
            result = categoryTreeService.save(categoryTreeRequest1);
        }
        else {
            result = "Пожалуйста убедитесь в правительности написания запроса \n" +
                    "'/addElement название' - чтобы добавить родителький элемент \n" +
                    "'/addElement название_Дочерного_Элемента название_Родительного_Элемента' " +
                    "- чтобы добавить добавить дочерный элемент к родителю";
        }



        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(result);
        return sendMessage;
    }
}
