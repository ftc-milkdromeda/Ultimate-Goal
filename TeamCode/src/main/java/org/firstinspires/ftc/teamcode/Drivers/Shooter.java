package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;

import java.util.logging.SocketHandler;

import RobotFunctions.Units_length;

public class Shooter extends RobotShooter {
    public Shooter(HardwareMap hardware) {
        super();

        this.shooter = new DcMotor[2];

        this.shooter[0] = hardware.dcMotor.get("shooter0");
        this.shooter[0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.shooter[0].setDirection(DcMotorSimple.Direction.REVERSE);

        this.shooter[1] = hardware.dcMotor.get("shooter0");
        this.shooter[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.shooter[1].setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public boolean runShooter(double distance, Units_length units) {
        return false;
    }

    @Override
    public boolean runShooter(double distance, Units_length units, double offset) {
        return false;
    }

    @Deprecated
    @Override
    public boolean runShooter(double rpm) {
        if(super.busy)
            return false;

        super.busy = true;

        this.shooter[0].setPower(rpm / Shooter.maxMotorSpeed);
        this.shooter[1].setPower(rpm / Shooter.maxMotorSpeed);

        return true;
    }

    @Override
    public boolean stopShooter() {
        if(!super.busy)
            return false;

        shooter[0].setPower(0.0);
        shooter[1].setPower(0.0);

        super.busy = false;

        return true;
    }

    private DcMotor shooter[];

    //constants
    private static final double maxMotorSpeed = 4860; //max motor speed given in RPM.
}

