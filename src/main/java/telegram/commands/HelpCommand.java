package telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HelpCommand  extends BotCommand {

    public HelpCommand(){
        super("help", "A command to get information about the bot's functions.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        SendMessage sender = new SendMessage();

        sender.setChatId(chat.getId().toString());
        sender.setText("Чтобы загрузить видео, введите ссылку на него.");

        try{
            absSender.execute(sender);
        } catch (TelegramApiException e){}
    }

}
