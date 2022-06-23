package com.kcb.telegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EchoBot extends TelegramLongPollingBot {
    private String botToken;
    private String botUsername;

    EchoBot(@Value("${bot.BOT_TOKEN}") String botToken,
            @Value("${bot.BOT_USERNAME}") String botUsername) {
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //check if update contains message and text
        if (update.hasMessage() && update.getMessage().hasText()) {
            //create object of sendMessage
            SendMessage message = new SendMessage();
            //set chat id
            message.setChatId(update.getMessage().getChatId().toString());

            //set reply to message id
            message.setReplyToMessageId(update.getMessage().getMessageId());

            //set text to reply with
            message.setText(update.getMessage().getText());

            try {
                //send message
                execute(message);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
