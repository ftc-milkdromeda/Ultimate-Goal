package org.firstinspires.ftc.teamcode.Tasks.Auto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotArm;

import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.Task;

public class ArmTask extends Task {
    public ArmTask(Clock clock, RobotArm arm, Telemetry telemetry) {
        super(clock);

        this.arm = arm;

        this.armPosition = 0;
        this.open = false;

        this.arm.enterThread(this);

        this.telemetry = telemetry;
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

            int startClock = super.clock.getCurrentState();
            while(super.clock.getCurrentState() == startClock && !super.isInterrupted());
        }

        this.telemetry.addData("HOOKA", "");
        this.telemetry.update();
    }

    public void setPosition(int position) {
        this.armPosition = position;
    }

    public void setGrabber(boolean open) {
        this.open = open;
    }

    private RobotArm arm;

    private boolean open;
    private int armPosition;
    private Telemetry telemetry;
}
