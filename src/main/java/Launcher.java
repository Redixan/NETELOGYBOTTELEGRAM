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

        JobDetail jobSendNotification = JobBuilder.newJob(SendNotification.class)
                .withIdentity("sendNotification")
                .build();

        Trigger trigger4 = TriggerBuilder
                .newTrigger()
                .withIdentity("EveryDay")
                .withSchedule(
                        CronScheduleBuilder.dailyAtHourAndMinute(23, 07))
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.getContext().put("bot", bot);
        scheduler.start();
        scheduler.scheduleJob(jobSendNotification, trigger4);




    }
}
