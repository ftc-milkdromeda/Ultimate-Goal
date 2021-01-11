package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotArm;

import TaskManager.Task;

public class Arm extends RobotArm {
    public Arm(HardwareMap hardware) {
        super();

        this.armServo = hardware.servo.get("arm");
        this.handServo = hardware.servo.get("hand");
    }

    @Override
    public boolean setArmPosition(Task task, int position) {
        if(!super.testTask(task))
            return false;

        super.busy = true;

        if(position == 0)
            armServo.setPosition(armInitialPosition);
        else if(position == 1)
            armServo.setPosition(armReadyPosition);
        else if(position == 2)
            armServo.setPosition((armExtendedPosition));

        super.busy = false;

        return true;
    }

    @Override
    public boolean setGrabberPosition(Task task, boolean open) {
        if(!super.testTask(task))
        return false;
        super.busy = true;

        if(open)
            handServo.setPosition(handOpenPosition);
        else if(!open)
            handServo.setPosition(handClosedPosition);

        super.busy = false;

        return true;
    }

    private Servo armServo;
    private Servo handServo;

    //constants
    private static double armInitialPosition = 0.0;
    private static double armReadyPosition = .25;
    private static double armExtendedPosition = 0.5;

    private static double handClosedPosition = 0.0;
    private static double handOpenPosition = 1.0;
}
