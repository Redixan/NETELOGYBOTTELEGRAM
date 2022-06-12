package TelegramBot;

import Questionnaire.ScalesOfAcademicMotivation;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    int amountAnswersSOAM = 0;
    int valueAnswer = 0;
    boolean isStartTestSOAM = false;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String greetings = "Привет! Меня зовут RefLex. Я бот от Нетологии, который поможет тебе размышлять над своими целями" +
                " и прогрессом и сохранять мотивацию на протяжении всего обучения. Я буду присылать тебе тесты и другие методики," +
                " а ты сможешь следить за динамикой своего обучения на красивых графиках.\n" +
                "\n" +
                "Данные полученные от тебя в этих тестах помогут скорректировать твой учебный процесс так, чтобы курс принес тебе еще больше пользы!";

        SendMessage messageGreetings = new SendMessage(update.getMessage().getChatId().toString(), greetings);
        ScalesOfAcademicMotivation soam = new ScalesOfAcademicMotivation();
        ArrayList<Integer> arrayListAnswersSOAM = new ArrayList();


        if (update.hasMessage()) {

            Message message = update.getMessage();
            if (message.getText().equals("/start")) {
                execute(messageGreetings);
                sendMessageInMainMenu(message);
                 amountAnswersSOAM = 0;
                isStartTestSOAM = false;
            }

            if (message.getText().equals("Моя Цель")){
                String studentGoal = "Твоя Цель";
                sendMessageInMainMenu(message);
            }

            if (message.getText().equals("Мои программы"))
                sendMessageInMainMenu(message);

            if (message.getText().equals("Моя мотивация")) {
                sendMessageInMotivation(message);
            }

                if (message.getText().equals("Измерь Мотивацию")) {
                    String manual = "ШКАЛЫ АКАДЕМИЧЕСКОЙ МОТИВАЦИИ (сокращенная версия)\n\n" +
                            "Инструкция: Пожалуйста, внимательно прочитайте каждое утверждение. Используя шкалу от 1 до 5, укажите ответ, который наилучшим образом соответствует тому, что Вы думаете о причинах Вашей вовлеченности в деятельность. Отвечайте, используя следующие варианты ответа:\n\n" +
                            "1) совсем не соответствует\n" +
                            "2) скорее не соответствует\n" +
                            "3) нечто среднее\n" +
                            "4) скорее соответствует\n" +
                            "5) вполне соответствует";

                    String mainQuestion = "Почему Вы в настоящее время посещаете курсы?";
                    sendMessageQuestions(message);
                    SendMessage sendMessagesManual = new SendMessage(update.getMessage().getChatId().toString(), manual);
                    execute(sendMessagesManual);
                    sendMessagesManual = new SendMessage(update.getMessage().getChatId().toString(), mainQuestion);
                    execute(sendMessagesManual);
                    isStartTestSOAM = true;
                }

                SendMessage sendMessageQuestion = new SendMessage();
                if(amountAnswersSOAM < 16 && isStartTestSOAM){
                    String question = soam.sendQuestionsToStudent(amountAnswersSOAM);
                     sendMessageQuestion = new SendMessage(update.getMessage().getChatId().toString(), question);
                     execute(sendMessageQuestion);
                        amountAnswersSOAM++;
                }

                        if (message.getText().equals("совсем не соответствует")){
                            valueAnswer = -1;
                            arrayListAnswersSOAM.add(valueAnswer);
                            sendMessageQuestions(message);
                        }

                        if (message.getText().equals("скорее не соответствует")){
                            valueAnswer = 0;
                            arrayListAnswersSOAM.add(valueAnswer);
                            sendMessageQuestions(message);
                        }

                        if (message.getText().equals("нечто среднее")){
                            valueAnswer = 1;
                            arrayListAnswersSOAM.add(valueAnswer);
                            sendMessageQuestions(message);
                        }

                        if (message.getText().equals("скорее соответствует")){
                            valueAnswer = 2;
                            arrayListAnswersSOAM.add(valueAnswer);
                            sendMessageQuestions(message);
                        }

                        if (message.getText().equals("вполне соответствует")){
                            valueAnswer = 3;
                            arrayListAnswersSOAM.add(valueAnswer);
                            sendMessageQuestions(message);
                        }


            if (message.getText().equals("Настройки Бота"))
                sendMessageInMainMenu(message);

            if (message.getText().equals("Рефлексия"))
                sendMessageInMainMenu(message);

            if (message.getText().equals("Подбодри и Замотивируй меня"))
                sendMessageInMainMenu(message);

            if (message.getText().equals("Главное меню")) {
                sendMessageInMainMenu(message);
            }

        }
    }

    @Override
    public String getBotUsername() {
        return "@RedAlexTestBot";
    }

    @Override
    public String getBotToken() {
        return "5434603712:AAH2CLdprtJv0oN4LTzxF3yTE2apy_nxA18";
    }

    @SneakyThrows
    public void sendNotification() {
        SendMessage messageUN = new SendMessage();
        String stringMessage = "Don't forget something";
        SendMessage message = new SendMessage(messageUN.getChatId().toString(), stringMessage);
        execute(message);
    }

    @SneakyThrows
    public void sendMessageInMainMenu(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("...");
        setMainMenu(sendMessage);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageInMotivation(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
         sendMessage.setText("...");
        setMotivationMenu(sendMessage);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageQuestions(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("...");
        setAnswerMenu(sendMessage);
        execute(sendMessage);
    }

    public void setMainMenu(SendMessage sendMessage) {
        SendMessage message = new SendMessage();
        message.setChatId("711028535");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("Моя Цель");
        row.add("Мои программы");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("Моя мотивация");
        row.add("Рефлексия");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("Настройки Бота");
        row.add("Подбодри и Замотивируй меня");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    public void setMotivationMenu(SendMessage sendMessage) {
        SendMessage message = new SendMessage();
        message.setChatId("711028535");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("Измерь Мотивацию");
        row.add("Новые Успехи");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("Покажи текущую мотивацию");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("Главное меню");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    public void setAnswerMenu(SendMessage sendMessage) {
        SendMessage message = new SendMessage();
        message.setChatId("711028535");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("совсем не соответствует");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("скорее не соответствует");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("нечто среднее");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("скорее соответствует");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("вполне соответствует");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

}

