package org.firstinspires.ftc.teamcode.RobotFunctions;

public abstract class RobotFeeder {
    protected RobotFeeder(RobotStorage storage) {
        this.storage = storage;
        this.busy = false;
    }

    /**
     * @brief Activates the servo to feed the next ring.
     */
    protected abstract void feed();

    public boolean shoot() {
        if(!this.storage.nextRing())
            return false;

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime <= RobotFeeder.shootingTimeDelay);

        this.feed();

        this.storage.removeRing();

        return true;
    }

    public boolean isBusy() {
        return this.busy;
    }

    private RobotStorage storage;
    protected boolean busy;
    //constants
    private static final int shootingTimeDelay = 1000;
}
