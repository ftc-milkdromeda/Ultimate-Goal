package org.firstinspires.ftc.teamcode.Tasks.Auto;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotArm;

import TaskManager.Clock;
import TaskManager.Task;

public class ArmTask extends Task {
    public ArmTask(Clock clock, RobotArm arm) {
        super(clock);

        this.arm = arm;

        this.arm.enterThread(this);
    }

    @Override
    protected void deconstructor() {
        this.arm.exitThread(this);
    }

    @Override
    public void run() {
        while(!super.isInterrupted()) {
            this.arm.setGrabberPosition(this, this.open);
            this.arm.setArmPosition(this, this.armPosition);
        }

        int startClock = super.clock.getCurrentState();
        while(super.clock.getCurrentState() == startClock && !super.isInterrupted());
    }

    public void setPosition(int position) {
        this.armPosition = armPosition;
    }

    public void setGrabber(boolean open) {
        this.open = open;
    }

    private RobotArm arm;

    private boolean open;
    private int armPosition;
}
