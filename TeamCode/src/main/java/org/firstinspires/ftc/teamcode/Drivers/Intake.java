package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import Milkdromeda.TaskManager.Task;

public class Intake extends RobotIntake {
    public Intake(RobotStorage storage, HardwareMap hardware) {
        super();

        this.storage = storage;
        this.intake = hardware.dcMotor.get("intake");

        this.busy = false;
    }

    @Override
    public boolean runIntake(Task task) {
        if(super.busy || !super.testTask(task))
            return false;

        super.busy = true;

        this.intake.setPower(this.power);
        this.storage.shake(task);
        this.storage.setPosition(task, 0);

        return true;
    }

    @Override
    public boolean stopIntake(Task task) {
        if(!super.testTask(task))
                return false;
        if(!super.busy)
            return true;

        this.intake.setPower(Intake.reversePower);
        this.storage.shakeEnd(task);
        super.busy = false;
        return true;
    }

    @Override
    public boolean hardStop(Task task) {
        if(!super.testTask(task))
            return false;

        this.intake.setPower(0.0);
        this.storage.shakeEnd(task);
        super.busy = false;

        return true;
    }

    @Override
    public void destructor() {
        super.destructor();
        this.intake.setPower(0.0);
    }

    public void setPower(double power) {
        this.power = power;
    }

    private DcMotor intake;
    private RobotStorage storage;
    private double power = 1.0;

    //constants
    private static final double reversePower = -.10;
}
