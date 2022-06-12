package TelegramBot;

import Questionnaire.ScalesOfAcademicMotivation;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    int amountAnswersSOAM =0;
    int valueAnswer = 0;
    boolean isStartTestSOAM = false;
    ArrayList<Integer> arrayListAnswersSOAM = new ArrayList();
    String studentGeneralGoal = "Твоя Цель";

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            String idStudent = update.getMessage().getChatId().toString();

            if (message.getText().equals("/start"))
                greetingsFromBot(update, message, idStudent);

            if (message.getText().equals("Моя Цель")) {
                sendMessageMenuGoal(message);
            }

            if (message.getText().equals("Изменить цель")) {
                sendMessageInMainMenu(message);
            }

            if (message.getText().equals("Помоги поставить цель")) {

                String helpTextGoal = "Если у тебя есть немного времени, подумай что в жизни..\n\n" + "Подумай минутку и напиши свою цель";
                SendMessage sendMessagesHelpTextGoal = new SendMessage(update.getMessage().getChatId().toString(), helpTextGoal);
                execute(sendMessagesHelpTextGoal);
                sendMessageInMainMenu(message);
            }

            if (message.getText().equals("Покажи динамику")) {
                sendMessageInMainMenu(message);
            }

            if (message.getText().equals("Мои программы"))
                sendMessageInMainMenu(message);

            if (message.getText().equals("Моя мотивация")) {
                sendMessageInMotivation(message);
            }

            if (message.getText().equals("Измерь мотивацию")) {
                studentMotivation(update, message, idStudent);
            }

            measurementMotivationStudent(update, message, idStudent, amountAnswersSOAM, isStartTestSOAM);
            answerGetFromStudent(update, message, idStudent);
            motivationOutputInformation(update, message, idStudent, amountAnswersSOAM);


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

    @SneakyThrows
    private void answerGetFromStudent(Update update, Message message, String idStudent) {

        if (message.getText().equals("совсем не соответствует")) {
            valueAnswer = 0;
            arrayListAnswersSOAM.add(valueAnswer);
            sendMessageQuestions(message, idStudent);
            amountAnswersSOAM++;
        }

        if (message.getText().equals("скорее не соответствует")) {
            valueAnswer = 1;
            arrayListAnswersSOAM.add(valueAnswer);
            sendMessageQuestions(message, idStudent);
            amountAnswersSOAM++;
        }

        if (message.getText().equals("нечто среднее")) {
            valueAnswer = 2;
            arrayListAnswersSOAM.add(valueAnswer);
            sendMessageQuestions(message, idStudent);
            amountAnswersSOAM++;
        }

        if (message.getText().equals("скорее соответствует")) {
            valueAnswer = 3;
            arrayListAnswersSOAM.add(valueAnswer);
            sendMessageQuestions(message, idStudent);
            amountAnswersSOAM++;
        }

        if (message.getText().equals("вполне соответствует")) {
            valueAnswer = 4;
            arrayListAnswersSOAM.add(valueAnswer);
            sendMessageQuestions(message, idStudent);
            amountAnswersSOAM++;
        }
    }

    @SneakyThrows
    private void motivationOutputInformation(Update update, Message message, String idStudent, int amountAnswersSOAM) {
        if (amountAnswersSOAM == 16) {

            int cognitiveMotivation = arrayListAnswersSOAM.get(0) + arrayListAnswersSOAM.get(4) + arrayListAnswersSOAM.get(8) + arrayListAnswersSOAM.get(12);
            int achievementMotivation = arrayListAnswersSOAM.get(1) + arrayListAnswersSOAM.get(5) + arrayListAnswersSOAM.get(9) + arrayListAnswersSOAM.get(13);
            int externalMotivation = arrayListAnswersSOAM.get(2) + arrayListAnswersSOAM.get(6) + arrayListAnswersSOAM.get(10) + arrayListAnswersSOAM.get(14);
            int amotivation = arrayListAnswersSOAM.get(3) + arrayListAnswersSOAM.get(7) + arrayListAnswersSOAM.get(11) + arrayListAnswersSOAM.get(15);
            int generalMotivation = cognitiveMotivation + achievementMotivation + externalMotivation - amotivation;
            int domenantMotivationValue = Integer.MIN_VALUE;
            String domenantMotivation = "";
            String generalMotivationOutput = "";

            String[] namesOfMotivation = new String[]{"Познавательная мотивация","Мотивация достижения","Внешняя ","Амотивация "};
            int[] allValuesOfMotivation = new int[]{cognitiveMotivation, achievementMotivation, externalMotivation, amotivation};
            String[] feedbackOnMotivation = new String[]{
                    "ты учишься, потому что стремишься узнать новое, понять изучаемый предмет, ты испытываешь интерес и удовольствие в процессе познания.",
                    "ты учишься, потому что стремишься добиваться максимально высоких результатов в учебе, испытываешь удовольствие в процессе решения трудных задач. ",
                    "ты учишься, потому что ощущаешь стыд и чувство долга перед собой и другими значимыми людьми и необходимость следовать требованиям социума.",
                    "у тебя нет ощущения осмысленности учебной деятельности, ты не испытываешь интереса и удовольствия в процессе учебы. Возможно, тебе стоит поразмышлять немного о цели обучения. \n" +
                            "Если у тебя есть минутка, подумай что в жизни тебя больше всего вдохновляет, какой бы ты хотел(а) видеть свою жизнь в будущем. \n" +
                            "Теперь поразмышляй о том, как можно добиться этого? Какими способами? Как тебе в этом поможет обучение? \n"
            };

            String itMeansThat = "";
            for (int i = 0; i < allValuesOfMotivation.length; i++)
                if (domenantMotivationValue < allValuesOfMotivation[i]){
                    domenantMotivationValue = allValuesOfMotivation[i];
                    domenantMotivation = namesOfMotivation[i];
                    itMeansThat = feedbackOnMotivation[i];
                }

            if(generalMotivation <= 0 ){
                generalMotivationOutput = "Очень низкий";
            }
            if(generalMotivation > 0 && generalMotivation <= 12 ){
                generalMotivationOutput = "Низкий";
            }
            if(generalMotivation > 12 && generalMotivation <= 24 ){
                generalMotivationOutput = "Средний";
            }
            if(generalMotivation > 24 && generalMotivation <= 48 ){
                generalMotivationOutput = "Высокий";
            }

            String finishTestSOAM = "Молодец! Вот результаты теста:\n" +
                    "\n" +
                    "Твой  общий уровень мотивации: " + generalMotivationOutput +"\n\n"+
                    "Твоя доминирующая мотивация: " + domenantMotivation+"\n\n"+
                    "Это значит, что " + itMeansThat +"\n"+
                    "\n" +
                    "ДИАГРАММА\n" +
                    "\n" +
                    "1. Шкала мотивации познания направлена на диагностику стремления узнать новое, понять изучаемый предмет, связанного с переживанием интереса и удовольствия в процессе познания.\n" +
                    "2. Шкала мотивации достижения измеряет стремление добиваться максимально высоких результатов в учебе, испытывать удовольствие в процессе решения трудных задач. \n" +
                    "3. Шкала внешней мотивации измеряет побуждение к учебе, обусловленное ощущением стыда и чувства долга перед собой и другими значимыми людьми и необходимостью следовать требованиям, диктуемым социумом, чтобы избежать возможных проблем.\n" +
                    "4. Шкала амотивации измеряет отсутствие интереса и ощущения осмысленности учебной деятельности.\n\n" +
                    "\n" +
                    "\n";

            sendMessageInMainMenu(message);
            SendMessage sendMessage = new SendMessage(idStudent, finishTestSOAM);
            execute(sendMessage);
            sendMessageInMainMenu(message);

        }
    }

    @SneakyThrows
    private void measurementMotivationStudent(Update update, Message message, String idStudent, int amountAnswersSOAM, boolean isStartTestSOAM) {
        ScalesOfAcademicMotivation soam = new ScalesOfAcademicMotivation();
        SendMessage sendMessageQuestion = new SendMessage();
        if (amountAnswersSOAM < 16 && isStartTestSOAM) {
            String question = soam.sendQuestionsToStudent(amountAnswersSOAM);
            sendMessageQuestion = new SendMessage(idStudent, question);
            execute(sendMessageQuestion);
        }
    }

    @SneakyThrows
    private void studentMotivation(Update update, Message message, String idStudent) {
        String manual = "ШКАЛЫ АКАДЕМИЧЕСКОЙ МОТИВАЦИИ (сокращенная версия)\n\n" +
                "Инструкция: Пожалуйста, внимательно прочитайте каждое утверждение. Используя шкалу от 1 до 5, укажите ответ, который наилучшим образом соответствует тому, что Вы думаете о причинах Вашей вовлеченности в деятельность. Отвечайте, используя следующие варианты ответа:\n\n" +
                "1) совсем не соответствует\n" +
                "2) скорее не соответствует\n" +
                "3) нечто среднее\n" +
                "4) скорее соответствует\n" +
                "5) вполне соответствует";

        String mainQuestion = "Почему Вы в настоящее время посещаете курсы?";
        sendMessageQuestions(message, idStudent);
        SendMessage sendMessagesManual = new SendMessage(idStudent, manual);
        execute(sendMessagesManual);
        sendMessagesManual = new SendMessage(idStudent, mainQuestion);
        execute(sendMessagesManual);
        isStartTestSOAM = true;
    }

    @SneakyThrows
    private void greetingsFromBot(Update update, Message message, String idStudent) {
        String greetings = "Привет! Меня зовут RefLex. Я бот от Нетологии, который поможет тебе размышлять над своими целями" +
                " и прогрессом и сохранять мотивацию на протяжении всего обучения. Я буду присылать тебе тесты и другие методики," +
                " а ты сможешь следить за динамикой своего обучения на красивых графиках.\n" +
                "\n" +
                "Данные полученные от тебя в этих тестах помогут скорректировать твой учебный процесс так, чтобы курс принес тебе еще больше пользы!";

        SendMessage messageGreetings = new SendMessage(idStudent, greetings);
            execute(messageGreetings);
            sendMessageInMainMenu(message);
            amountAnswersSOAM = 0;
            isStartTestSOAM = false;
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
        sendMessage.setText("Выбери раздел");
        setMainMenu(sendMessage);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageInMotivation(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Что я должен сделать?");
        setMotivationMenu(sendMessage);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageMenuGoal(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Что я должен сделать?");
        setGoalMenu(sendMessage);
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
        row.add("Измерь мотивацию");
        row.add("Добавь новые успехи");
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

    @SneakyThrows
    public void sendMessageQuestions(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText(".");
        setAnswerMenuButtons(sendMessage, idStudent);
        execute(sendMessage);
    }

    @SneakyThrows
    public void setAnswerMenuButtons(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        sendMessage.setChatId(idStudent);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> Buttons = new ArrayList<InlineKeyboardButton>();

        List<List<InlineKeyboardButton>> keyboardRow1 = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboardRow2 = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboardRow3 = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboardRow4 = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboardRow5 = new ArrayList<>();

        InlineKeyboardButton doesntMatchAll = new InlineKeyboardButton();
        doesntMatchAll.setText("совсем не соответствует");
        doesntMatchAll.setCallbackData("совсем не соответствует");
        Buttons.add(doesntMatchAll);
        InlineKeyboardButton doesntCorrespond = new InlineKeyboardButton();
        doesntMatchAll.setCallbackData("скорее не соответствует");
        Buttons.add(doesntMatchAll);
        InlineKeyboardButton somethingBetween = new InlineKeyboardButton();
        doesntMatchAll.setCallbackData("нечто среднее");
        Buttons.add(doesntMatchAll);
        InlineKeyboardButton ratherCorresponds = new InlineKeyboardButton();
        doesntMatchAll.setCallbackData("скорее соответствует");
        Buttons.add(doesntMatchAll);
        InlineKeyboardButton quiteConsistent = new InlineKeyboardButton();
        doesntMatchAll.setCallbackData("вполне соответствует");

        keyboardRow1.add(doesntMatchAll);

        Buttons.add(doesntMatchAll);
        Buttons.add(doesntCorrespond);
        Buttons.add(somethingBetween);
        Buttons.add(ratherCorresponds);
        Buttons.add(quiteConsistent);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);
        execute(message);
    }

    public void setGoalMenu(SendMessage sendMessage) {
        SendMessage message = new SendMessage();
        message.setChatId("711028535");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("Помоги поставить цель");
        row.add("Изменить цель");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("Покажи динамику");
        row.add("Главное меню");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

}

