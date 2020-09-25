package touro.snake;

/**
 * Thread responsible for advancing the Garden and updating GardenView.
 */
public class GardenThread extends Thread {

    private static final int DELAY_MS = 80;
    private int currentDelay;
    private int currentRound;

    private final Garden garden;
    private final GardenView gardenView;

    public GardenThread(Garden garden, GardenView gardenView) {
        this.garden = garden;
        this.gardenView = gardenView;
        currentDelay = DELAY_MS;
        currentRound = 0;
    }

    /**
     * Every second, advance the Garden (moving the Snake, eating, death) and repaint the Garden
     */
    public void run() {
        while (garden.advance()) {
            gardenView.repaint();
            try {
                decrementDelay();
                Thread.sleep(currentDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void decrementDelay() {
        if (currentDelay > 15) { //the smallest delay allowed
            if (currentRound < 50) {
                currentRound++;
            } else {
                currentDelay--;
                currentRound = 0;
            }
        }
    }
}
