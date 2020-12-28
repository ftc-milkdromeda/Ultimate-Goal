package org.firstinspires.ftc.teamcode.RobotFunctions;

public abstract class RobotStorage {

    /**
     * @brief Sets the storage to specified location.
     * @param postion an integer 0 - 2 for the 3 different positions of lift.
     */
    protected abstract void setPosition(int postion);

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

        this.setPosition(3 - numOfRings);

        return true;
    }

    private int position;
    private int numOfRings;
}
