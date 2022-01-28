package telegram.handlers;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pinterest.Downloader;
import telegram.commands.HelpCommand;
import telegram.commands.StartCommand;

public class CommandsHandler extends TelegramLongPollingCommandBot {

    public static final String BOT_NAME = "AnotheR_VideO_DownloadeR_Bot";

    public CommandsHandler(){

        register(new StartCommand());
        register(new HelpCommand());

        registerDefaultAction((absSender, message) -> {
            SendMessage sender = new SendMessage();
            sender.setChatId(message.getChatId().toString());
            sender.setText("Команда '" + message.getText() + "' не известна этому боту. Используйте команду /help, чтобы получить помощь.");
            try {
                absSender.execute(sender);
            } catch (TelegramApiException e) {

            }
        });
    }

    @Override
    public void processNonCommandUpdate(Update update){
        String userMessage = update.getMessage().getText();
        String chatId =  update.getMessage().getChatId().toString();

        SendMessage messageSender = new SendMessage();
        SendVideo videoSender = new SendVideo();

        if(userMessage.startsWith("https://www.pinterest.com/pin/") || userMessage.startsWith("https://pin.it/")){
            sendMessageToUser(messageSender, "Подождите, пожалуйста.", chatId);
            InputFile video = Downloader.download(userMessage);
            if(video != null){
                sendMessageToUser(videoSender, video, chatId);
            } else {
                sendMessageToUser(messageSender, "Что-то пошло не так. Проверьте правильность ссылки и попробуйте еще раз.", chatId);
            }
        } else {
            sendMessageToUser(messageSender, "Извините, я вас не понимаю.", chatId);
        }
    }

    public void sendMessageToUser(SendMessage messageSender, String message, String id){
        messageSender.setChatId(id);
        messageSender.setText(message);
        try{
            execute(messageSender);
        } catch(TelegramApiException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void sendMessageToUser(SendVideo videoSender, InputFile video, String id){
        videoSender.setChatId(id);
        videoSender.setVideo(video);
        try{
            execute(videoSender);
        } catch(TelegramApiException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }
}
