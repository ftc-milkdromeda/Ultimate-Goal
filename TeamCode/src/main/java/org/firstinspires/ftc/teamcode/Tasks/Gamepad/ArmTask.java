package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotArm;

import Drivers.Controller;
import TaskManager.Clock;
import TaskManager.KeyTask;

public class ArmTask extends KeyTask {
    protected ArmTask(Clock clock, Controller controller, RobotArm arm) {
        super(clock, controller);

        this.arm = arm;
    }

    @Override
    protected void deconstructor() {
        super.deconstructor();
        this.arm.exitThread(this);

        this.onSwitch = new Toggle(super.clock, super.controller) {
            @Override
            protected boolean testKey() {
                return super.controller.get_A() == 1.0;
            }
        };
    }

    @Override
    public void run() {
        while(!super.isInterrupted()) {
            while(this.keyMapping()[3] == 1.0) {
                this.arm.setArmPosition(this, this.keyMapping()[1] == 1.0 ? 2 : 1);
                this.arm.setGrabberPosition(this, this.keyMapping()[0] == 1.0);

                int startClock = super.clock.getCurrentState();
                while(super.clock.getCurrentState() == startClock);
            }

            this.arm.setArmPosition(this, 0);

            int startClock = super.clock.getCurrentState();
            while(super.clock.getCurrentState() == startClock);
        }
    }

    @Override
    protected double[] keyMapping() {
        double returnValue[] = {
                super.controller.get_LeftTrigger(), //key to open and close grabber
                super.controller.get_RightBumper(), //key to extend arm
                this.onSwitch.getToggleState() //key used to turn the arm on
        };

        return returnValue;
    }

    private RobotArm arm;
    private Toggle onSwitch;
}
