import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoClicker {
    private MouseController mouseController;
    private ScheduledExecutorService executorService;
    private int clicksPerSecond;
    private boolean isLeftMouseButton;
    private boolean isRunning;
    private int currentClicks;

    public AutoClicker(MouseController mouseController) {
        this.mouseController = mouseController;
    }

    public int getCurrentClicks() {
        return currentClicks;
    }

    public void setClicksPerSecond(int clicksPerSecond) {
        this.clicksPerSecond = clicksPerSecond;
    }

    public void setLeftMouseButton(boolean isLeftMouseButton) {
        this.isLeftMouseButton = isLeftMouseButton;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void start() {
        if (!isRunning) {
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(this::performClick, 0, 1000 / clicksPerSecond, TimeUnit.MILLISECONDS);
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            executorService.shutdown();
            isRunning = false;
        }
    }

    private void performClick() {
        if (isLeftMouseButton) {
            mouseController.clickLeftMouseButton();
        } else {
            mouseController.clickRightMouseButton();
        }
        currentClicks++;
    }
}

