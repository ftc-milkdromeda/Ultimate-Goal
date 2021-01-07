package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;

public class Intake extends RobotIntake {
    public Intake(Storage storage, HardwareMap hardware) {
        super();

        this.storage = storage;
        this.intake = hardware.dcMotor.get("intake");
        this.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.status = false;
    }

    @Override
    public void runIntake() {
        if(this.status)
            return;
        this.status = true;

        this.intake.setPower(Intake.power);
        this.storage.shake();
    }

    @Override
    public void stopIntake() {
        if(!this.status)
                return;

        this.intake.setPower(0.0);
        this.storage.shakeEnd();
        this.status = false;
    }

    @Override
    public void hardStop() {
        this.intake.setPower(0.0);
        this.storage.shakeEnd();
    }

    @Override
    public void terminate() {
        this.stopIntake();
    }

    private DcMotor intake;
    private Storage storage;
    private boolean status;

    //constants
    private static final double power = 1.0;
}
