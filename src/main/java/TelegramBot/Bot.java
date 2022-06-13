package TelegramBot;

import Questionnaire.AttitudeToThOccupation;
import Questionnaire.AttitudeToTheTask;
import Questionnaire.ScalesOfAcademicMotivation;
import com.vdurmont.emoji.EmojiParser;
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

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

public class Bot extends TelegramLongPollingBot {

    int amountAnswersSOAM = 0;
    int amountAnswerVFM = 0;

    int valueAnswer = 0;

    boolean isStartTestSOAM = false;
    boolean isStartTestVFM = false;
    boolean isStartTestAT = false;

    ArrayList<Integer> arrayListAnswersSOAM = new ArrayList();
    ArrayList<Integer> arrayListAnswersVFM = new ArrayList();
    ArrayList<Integer> arrayListAnswersAT = new ArrayList();

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
                sendMessageMenuGoal(message, idStudent);
            }

            if (message.getText().equals("Изменить цель")) {
                sendMessageInMainMenu(message, idStudent);
            }

            if (message.getText().equals("Помоги поставить цель")) {

                String helpTextGoal = "Если у тебя есть немного времени, подумай что в жизни тебя больше всего вдохновляет, какой бы ты хотел(а) видеть свою жизнь в будущем.\n" +
                        "Теперь поразмышляй о том, как можно добиться этого? Какими способами? Как тебе в этом поможет обучение?\n\n" + "Подумай минутку и напиши свою цель";
                SendMessage sendMessagesHelpTextGoal = new SendMessage(update.getMessage().getChatId().toString(), helpTextGoal);
                execute(sendMessagesHelpTextGoal);
                sendMessageMenuGoal(message, idStudent);
            }

            if (message.getText().equalsIgnoreCase("Покажи динамику")) {
                sendMessageInMainMenu(message, idStudent);
            }

            if (message.getText().equalsIgnoreCase("Мои программы"))
                sendMessageInMainMenu(message, idStudent);

            if (message.getText().equalsIgnoreCase("Моя мотивация")) {
                if (amountAnswersSOAM == 16) {
                    amountAnswersSOAM = 0;
                    isStartTestSOAM = false;
                }
                sendMessageInMotivation(message, idStudent);
            }

            if (message.getText().equalsIgnoreCase("Измерь мотивацию")) {
                studentMotivation(update, message, idStudent);
            }

            measurementMotivationStudent(update, message, idStudent, amountAnswersSOAM, isStartTestSOAM);
            answerGetFromStudent(update, message, idStudent,isStartTestSOAM);
            motivationOutputInformation(update, message, idStudent, amountAnswersSOAM);


            if (message.getText().equalsIgnoreCase("Настройки бота"))
                sendMessageInMainMenu(message, idStudent);

            if (message.getText().equalsIgnoreCase("Рефлексия")) {
                sendMessageInReflectionOpen(message, idStudent);
            }

            if (message.getText().equalsIgnoreCase("После онлайн-урока")) {
                isStartTestVFM = true;
                isStartTestAT = true;
                sendMessageInReflectionOnlineLessonMenu(message, idStudent);
            }


            if (message.getText().equalsIgnoreCase("В форме опроса")) {
                sendMessageInReflectionOpen(message, idStudent);
            }

            if (message.getText().equalsIgnoreCase("Очень быстрая методика")) {
                sendMessageInVeryFasMethod(message, idStudent);
            }
            System.out.println("amountAnswerVFM = " + amountAnswerVFM);
            System.out.println("isStartTestVFM = "+ isStartTestVFM);
            measurementVeryFastMethode(update, message, idStudent, amountAnswerVFM, isStartTestVFM);
            answerGetFromStudentVeryFastMethode(update, message, idStudent, isStartTestVFM);

            if (message.getText().equalsIgnoreCase("Подбодри и Замотивируй меня"))
                sendMessageInMainMenu(message, idStudent);

            if (message.getText().equalsIgnoreCase("Главное меню")) {
                sendMessageInMainMenu(message, idStudent);
            }

        }
    }

    private void answerGetFromStudentVeryFastMethode(Update update, Message message, String idStudent, boolean isStartTestVFM) {
        int valueAnswerVFM = Integer.parseInt(message.getText());

        if (isStartTestVFM) {
            arrayListAnswersVFM.add(valueAnswerVFM);
            sendMessageInVeryFasMethod(message, idStudent);
            amountAnswerVFM++;
        }

    }


    @SneakyThrows
    private void measurementVeryFastMethode(Update update, Message message, String idStudent, int amountAnswerVFM, boolean isStartTestVFM) {
        AttitudeToThOccupation att = new AttitudeToThOccupation();
        SendMessage sendMessageQuestion = new SendMessage();
        if (amountAnswerVFM < 6 && isStartTestSOAM) {
            String question = att.sendQuestionsToStudentATO(amountAnswerVFM);
            sendMessageQuestion = new SendMessage(idStudent, question);
            execute(sendMessageQuestion);
        }
    }

    @SneakyThrows
    private void answerGetFromStudent(Update update, Message message, String idStudent, boolean isStartTestSOAM) {
        if (isStartTestSOAM) {
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

            String[] namesOfMotivation = new String[]{"Познавательная мотивация", "Мотивация достижения", "Внешняя ", "Амотивация "};
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
                if (domenantMotivationValue < allValuesOfMotivation[i]) {
                    domenantMotivationValue = allValuesOfMotivation[i];
                    domenantMotivation = namesOfMotivation[i];
                    itMeansThat = feedbackOnMotivation[i];
                }

            if (generalMotivation <= 0) {
                generalMotivationOutput = "Очень низкий";
            }
            if (generalMotivation > 0 && generalMotivation <= 12) {
                generalMotivationOutput = "Низкий";
            }
            if (generalMotivation > 12 && generalMotivation <= 24) {
                generalMotivationOutput = "Средний";
            }
            if (generalMotivation > 24 && generalMotivation <= 48) {
                generalMotivationOutput = "Высокий";
            }

            String books = EmojiParser.parseToUnicode(":books:");
            String trophy = EmojiParser.parseToUnicode(":trophy:");
            String moneybag = EmojiParser.parseToUnicode(":moneybag:");
            String unamused = EmojiParser.parseToUnicode(":unamused:");
            String white_large_square = EmojiParser.parseToUnicode(":white_large_square:");
            String red_large_square = EmojiParser.parseToUnicode(":red_large_square:");
            String black_large_square = EmojiParser.parseToUnicode(":black_large_square:");
            String blue_large_square = EmojiParser.parseToUnicode(":blue_large_square:");
            String green_large_square = EmojiParser.parseToUnicode(":green_large_square:");

            String cognitive = new String(new char[cognitiveMotivation]).replace("\0", black_large_square);
            String achievement = new String(new char[achievementMotivation]).replace("\0", black_large_square);
            String external = new String(new char[externalMotivation]).replace("\0", black_large_square);
            String amotiv = new String(new char[amotivation]).replace("\0", black_large_square);

            String cognitiveWhite = new String(new char[12 - cognitiveMotivation]).replace("\0", white_large_square);
            String achievementWhite = new String(new char[12 - achievementMotivation]).replace("\0", white_large_square);
            String externalWhite = new String(new char[12 - externalMotivation]).replace("\0", white_large_square);
            String amotivWhite = new String(new char[12 - amotivation]).replace("\0", white_large_square);

            String legend = books + " - познавательная мотивация\n"
                    + trophy + " - мотивация достижения\n"
                    + moneybag + " - внешняя мотивация\n"
                    + unamused + " - амотивация";

            String diagram = "Мотивация\n\n" +
                    books + cognitive + cognitiveWhite + "\n"
                    + trophy + achievement + achievementWhite + "\n"
                    + moneybag + external + externalWhite + "\n"
                    + unamused + amotiv + amotivWhite;


            String finishTestSOAM = "Молодец! Вот результаты теста:\n" +
                    "\n" +
                    "Твой  общий уровень мотивации: " + generalMotivationOutput + "\n\n" +
                    "Твоя доминирующая мотивация: " + domenantMotivation + "\n\n" +
                    "Это значит, что " + itMeansThat + "\n" +
                    "\n" +
                    diagram + "\n\n" +
                    legend + "\n\n" +
                    "1. Шкала мотивации познания направлена на диагностику стремления узнать новое, понять изучаемый предмет, связанного с переживанием интереса и удовольствия в процессе познания.\n" +
                    "2. Шкала мотивации достижения измеряет стремление добиваться максимально высоких результатов в учебе, испытывать удовольствие в процессе решения трудных задач. \n" +
                    "3. Шкала внешней мотивации измеряет побуждение к учебе, обусловленное ощущением стыда и чувства долга перед собой и другими значимыми людьми и необходимостью следовать требованиям, диктуемым социумом, чтобы избежать возможных проблем.\n" +
                    "4. Шкала амотивации измеряет отсутствие интереса и ощущения осмысленности учебной деятельности.\n\n" +
                    "\n" +
                    "\n";

            sendMessageInMainMenu(message, idStudent);
            SendMessage sendMessage = new SendMessage(idStudent, finishTestSOAM);
            execute(sendMessage);
            sendMessageInMainMenu(message, idStudent);

        }

    }

    @SneakyThrows
    private void measurementMotivationStudent(Update update, Message message, String idStudent, int amountAnswersSOAM, boolean isStartTestSOAM) {
        if(isStartTestSOAM){
            ScalesOfAcademicMotivation soam = new ScalesOfAcademicMotivation();
            SendMessage sendMessageQuestion = new SendMessage();
            if (amountAnswersSOAM < 16) {
                String question = soam.sendQuestionsToStudent(amountAnswersSOAM);
                sendMessageQuestion = new SendMessage(idStudent, question);
                execute(sendMessageQuestion);
            }
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
        SendMessage sendMessagesManual = new SendMessage(idStudent, manual);
        sendMessageQuestions(message, idStudent);
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
        sendMessageInMainMenu(message, idStudent);
        amountAnswerVFM = 0;
        amountAnswersSOAM = 0;
        isStartTestSOAM = false;
        isStartTestVFM = false;
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
    public void sendMessageInMainMenu(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Выберите раздел");
        setMainMenu(sendMessage, idStudent);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageInReflectionOpen(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Я знаю несколько техник рефлексии, которые подходят для разных ситуаций");
        reflectionMenu(sendMessage, idStudent);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageInReflectionMenuUniversal(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Отличный выбор");
        reflectionMenu(sendMessage, idStudent);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageInMotivation(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Что я должен сделать?");
        setMotivationMenu(sendMessage, idStudent);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageMenuGoal(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Что я должен сделать?");
        setGoalMenu(sendMessage, idStudent);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageInVeryFasMethod(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Ваш ответ:");
        buttonsOneToTen(sendMessage, idStudent);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageInReflectionOnlineLessonMenu(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Ваш ответ:");
        reflectionOnlineLessonMenu(sendMessage, idStudent);
        execute(sendMessage);
    }


    public void setMainMenu(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        message.setChatId(idStudent);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("МОЯ ЦЕЛЬ");
        row.add("МОИ ПРОГРАММЫ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("МОЯ МОТИВАЦИЯ");
        row.add("РЕФЛЕКСИЯ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("НАСТРОЙКИ БОТА");
        row.add("ПОДБОРИ И ЗАМОТИВИРУЙ МЕНЯ");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    public void setMotivationMenu(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        message.setChatId(idStudent);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("ИЗМЕРЬ МОТИВАЦИЮ");
        row.add("ДОБАВЬ НОВЫЕ УСПЕХИ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ПОКАЖИ ТЕКУЩУЮ МОТИВАЦИЮ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ГЛАВНОЕ МЕНЮ");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    @SneakyThrows
    public void sendMessageQuestions(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Ваш ответ");
        setAnswerMenuButtons(sendMessage, idStudent);
        execute(sendMessage);
    }

    @SneakyThrows
    public void setAnswerMenuButtons(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        sendMessage.setChatId(idStudent);

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

    public void setGoalMenu(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        message.setChatId(idStudent);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("ПОМОГИ ПОСТАВИТЬ ЦЕЛЬ");
        row.add("ИЗМЕНИТЬ ЦЕЛЬ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ПОКАЖИ ДИНАМИКУ");
        row.add("ГЛАВНОЕ МЕНЮ");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    public void reflectionMenu(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        sendMessage.setChatId(idStudent);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("УНИВИРАСАЛЬНАЯ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ПОСЛЕ ОНЛАЙН-УРОКА");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ПОСЛЕ ВИДЕО-УРОКА");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ПОСЛЕ ВЫПОЛНЕНИЯ ПРОЕКТА ИЛИ ДЗ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ПОСЛЕ РЕФЛЕКСИИ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ГЛАВНОЕ МЕНЮ");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    public void reflectionOnlineLessonMenu(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        sendMessage.setChatId(idStudent);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("НЕЗАКОНЧЕНННЫЕ ПРЕДЛОЖЕНИЯ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("В ФОРМЕ ОПРОСА");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ОЧЕНЬ БЫСТРАЯ МЕТОДИКА");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ПОСЛЕ РЕФЛЕКСИИ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("НАЗАД <=");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    public void buttonsOneToTen(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        sendMessage.setChatId(idStudent);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("1");
        row.add("2");
        row.add("3");
        row.add("4");
        row.add("5");
        row.add("6");
        row.add("7");
        row.add("8");
        row.add("9");
        row.add("10");
        keyboardRowsList.add(row);


        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    public void cheerMeUpMenu(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        sendMessage.setChatId(idStudent);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("РАССКАЖИ ПРО МОИ УСПЕХИ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ПОДБОДРИ МЕНЯ МЕМОМ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ЗАМОТИВИРУЙ МЕНЯ СЛОВАМИ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ЗАМОТИВИРУЙ МЕНЯ ВИДЕОРОЛИКОМ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("ПОДБОДРИ МЕНЯ ВИДЕОРОЛИКОМ");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("НАЗАД <=");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }


    @SneakyThrows
    public void sendMessageCheerMeUp(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Ты потрясающий");
        cheerMeUpMenu(sendMessage, idStudent);
        execute(sendMessage);
    }

    public void emotionVideoMenu(SendMessage sendMessage, String idStudent) {
        SendMessage message = new SendMessage();
        sendMessage.setChatId(idStudent);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row = new KeyboardRow();
        row.add("мне грустно");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("мне страшно");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("мне одиноко");
        keyboardRowsList.add(row);

        row = new KeyboardRow();
        row.add("НАЗАД <=");
        keyboardRowsList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    @SneakyThrows
    public void sendMessageInYourFeelings(Message message, String idStudent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(idStudent);
        sendMessage.setText("Что ты сейчас чувствуешь?");
        emotionVideoMenu(sendMessage, idStudent);
        execute(sendMessage);
    }

}

