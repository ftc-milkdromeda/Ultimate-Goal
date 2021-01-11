package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;

import TaskManager.Task;

public class Intake extends RobotIntake {
    public Intake(Storage storage, HardwareMap hardware, Telemetry telemetry) {
        super();

        this.storage = storage;
        this.intake = hardware.dcMotor.get("intake");
        this.intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.intake.setDirection(DcMotorSimple.Direction.REVERSE);

        this.busy = false;
    }

    @Override
    public boolean runIntake(Task task) {
        if(super.busy || !super.testTask(task))
            return false;

        super.busy = true;

        this.intake.setPower(Intake.power);
        this.storage.shake(task);
        this.storage.setPosition(task, 0);

        return true;
    }

    @Override
    public boolean stopIntake(Task task) {
        if(!super.busy || !super.testTask(task))
                return false;

        this.intake.setPower(Intake.reversePower);
        this.storage.shakeEnd(task);
        this.storage.setPosition(task, 1);
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

    private DcMotor intake;
    private Storage storage;

    //constants
    private static final double power = 1.0;
    private static final double reversePower = -.10;
}
