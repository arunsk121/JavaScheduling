import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class DailyJobScheduler implements ServletContextListener {

    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        // Calculate the initial delay to the next midnight
//        calendar.add(Calendar.DAY_OF_YEAR, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 12);
        calendar.set(Calendar.SECOND, 0);
        Date nextOccurrence = calendar.getTime();
        if (nextOccurrence.before(currentTime)) {
            calendar.add(Calendar.MONTH, 1); // Move to the next month if the desired day has already passed in the current month
            nextOccurrence = calendar.getTime();
        }
        long initialDelay = nextOccurrence.getTime() - currentTime.getTime();

        // Create a Timer object
        timer = new Timer();

        // Schedule the task to run daily at midnight
//        timer.schedule(new DailyTask(), initialDelay, 24 * 60 * 60 * 1000);
        timer.schedule(new MonthlyTask(), initialDelay, 30L * 24 * 60 * 60 * 1000);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cancel the timer when the application is being shut down
        if (timer != null) {
            timer.cancel();
        }
    }

    static class DailyTask extends TimerTask {
        @Override
        public void run() {
            // Your task logic goes here
            System.out.println("Executing daily task...");
        }
    }
    
    static class MonthlyTask extends TimerTask {
        @Override
        public void run() {
            // Your task logic goes here
            System.out.println("Executing daily task...");
        }
    }
}
