import Notifications.SendNotification;
import TelegramBot.Bot;
import lombok.SneakyThrows;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Launcher {

    @SneakyThrows
    public static void main(String[] args) {

        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = null;

        telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);

        JobDetail jobSendNotificationLesson1 = JobBuilder.newJob(SendNotification.class)
                .withIdentity("sendNotification")
                .build();

        Trigger lessonNotification = TriggerBuilder
                .newTrigger()
                .withIdentity("Lesson")
                .withSchedule(
                        CronScheduleBuilder.weeklyOnDayAndHourAndMinute(1,12,0))
                .withSchedule(
                        CronScheduleBuilder.weeklyOnDayAndHourAndMinute(4,19,0))
                .build();


        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.getContext().put("bot", bot);
        scheduler.start();
        scheduler.scheduleJob(jobSendNotificationLesson1, lessonNotification);

    }
}
