package org.firstinspires.ftc.teamcode.RobotFunctions;

public abstract class RobotStorage {

    /**
     * @brief Sets the storage to specified location.
     * @param postion An integer 0 - 3, where 0 is the intake position; where 1 - 3 is each of its shooting positions.
     */
    protected abstract void setPosition(int postion);
    protected abstract void shake();
    protected abstract void shakeEnd();

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

    private int position;
    private int numOfRings;
}
