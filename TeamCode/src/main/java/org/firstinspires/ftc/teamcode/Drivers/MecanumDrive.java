package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;

import RobotFunctions.MecanumWheels.Drive;
import RobotFunctions.MecanumWheels.Moter;

public class MecanumDrive extends Drive {
    public MecanumDrive(DcMotor moter0, DcMotor moter1, DcMotor moter2, DcMotor moter3) {
        this.moters = new DcMotor[4];

        this.moters[0] = moter0;
        this.moters[1] = moter1;
        this.moters[2] = moter2;
        this.moters[3] = moter3;
    }

    @Override
    public void setMoter(Moter index, double power) {
        this.moters[index.getValue()].setPower(power);
    }

    @Override
    public void setMoters(double[] power) {
        for(int a = 0; a < power.length; a++)
             this.moters[a].setPower(power[a]);
    }

    DcMotor moters[];
}
