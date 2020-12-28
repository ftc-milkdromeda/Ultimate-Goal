package org.firstinspires.ftc.teamcode.RobotFunctions;

public abstract class RobotFeeder {
    protected RobotFeeder(RobotStorage storage) {
        this.storage = storage;
    }

    /**
     * @brief Activates the servo to feed the next ring.
     */
    protected abstract void feed();

    public boolean shoot() {
        if(!this.storage.nextRing())
            return false;

        this.feed();

        this.storage.removeRing();

        return true;
    }

    private RobotStorage storage;
}
