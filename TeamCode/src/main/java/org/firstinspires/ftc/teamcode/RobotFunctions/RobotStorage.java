package org.firstinspires.ftc.teamcode.RobotFunctions;

import Drivers.Template.Driver;

public abstract class RobotStorage extends Driver {
    protected RobotStorage() {
        this.busy = false;
    }

    /**
     * @brief Sets the storage to specified location.
     * @param postion An integer 0 - 3, where 0 is the intake position; where 1 - 3 is each of its shooting positions.
     * @return returns true on success; false if function failed to execute.
     */
    public abstract boolean setPosition(int postion);
    protected abstract boolean shake();
    protected abstract boolean shakeEnd();

    public int getRings() {
        return this.numOfRings;
    }
    public void setRings(int rings) {
        this.numOfRings = rings;
    }
    public void addRing() {
        this.numOfRings++;
    }
    public void removeRing() {
        this.numOfRings--;
    }

    public boolean nextRing() {
        if(numOfRings == 0)
            return false;

        this.setPosition(4 - numOfRings);

        return true;
    }

    public boolean isBusy() {
        return this.busy;
    }

    private int position;
    private int numOfRings;

    protected boolean busy;
}
