package Notifications;

import TelegramBot.Bot;
import org.quartz.*;

public class SendNotification implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SchedulerContext schedulerContext = null;

        try {
            schedulerContext = jobExecutionContext.getScheduler().getContext();
        } catch (SchedulerException e1) {
            e1.printStackTrace();
        }

        Bot bot = (Bot) schedulerContext.get("bot");

        bot.sendNotification();
    }
}
