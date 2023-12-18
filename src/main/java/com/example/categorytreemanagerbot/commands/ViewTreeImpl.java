package com.example.categorytreemanagerbot.commands;

import com.example.categorytreemanagerbot.bot.BotCommand;
import com.example.categorytreemanagerbot.entity.CategoryTree;
import com.example.categorytreemanagerbot.entity.CategoryTreeStructure;
import com.example.categorytreemanagerbot.service.CategoryTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

public class ViewTreeImpl implements BotCommand {
    private final CategoryTreeService categoryTreeService;
    @Autowired
    public ViewTreeImpl(CategoryTreeService categoryTreeService)
    {
        this.categoryTreeService = categoryTreeService;
    }
    @Override
    public SendMessage execute(Update update) {
        CategoryTree rootTree = categoryTreeService.getRootParent(); //берем из бд root tree
        String result = "";
        SendMessage sendMessage = new SendMessage();
        long chatId = update.getMessage().getChatId();
        if(rootTree==null) {
            result = "В данный момент список категорий пуст! Добавьте новые элементы" +
                    "используя командку /addElement";
        }
        else {
            CategoryTreeStructure categoryTreeStructure = new CategoryTreeStructure(rootTree.getName());
            ArrayList<CategoryTree> categoryTreeList = categoryTreeService.getAllCategory(); //берем все элементы
            if (categoryTreeList.size() == 0) {
                result = "В данный момент дерево категорий пуст! Добавьте элементы \n" +
                        "используя команду /addElement";
            } else {
                for (CategoryTree categoryTree : categoryTreeList) {
                    categoryTreeStructure.addToTree(categoryTree);
                }
                result = categoryTreeStructure.printTree(rootTree.getId(), 1);
                //здесь уже рисуем само дерево
            }
        }



        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(result);
        return sendMessage;

    }
}
