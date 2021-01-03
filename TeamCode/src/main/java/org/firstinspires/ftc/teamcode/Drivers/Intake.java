package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;

public class Intake extends RobotIntake {
    public Intake(Storage storage, HardwareMap hardware) {
        super();

        this.storage = storage;
        this.intake = hardware.dcMotor.get("intake");
    }

    @Override
    public void runIntake() {
        this.intake.setPower(Intake.power);
        this.storage.shake();
    }

    @Override
    public void stopIntake() {
        this.intake.setPower(0.0);
        this.storage.shakeEnd();
    }

    private DcMotor intake;
    private Storage storage;

    //constants
    private static final double power = 1.0;
}
