package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;

import RobotFunctions.MecanumWheels.Drive;
import RobotFunctions.MecanumWheels.Motor;

public class MecanumDrive extends Drive {
    public MecanumDrive(DcMotor moter0, DcMotor moter1, DcMotor moter2, DcMotor moter3) {
        this.moters = new DcMotor[4];

        this.moters[0] = moter0;
        this.moters[1] = moter1;
        this.moters[2] = moter2;
        this.moters[3] = moter3;
    }

    @Override
    public void setMotor(Motor motor, double power) {
        this.moters[motor.getValue()].setPower(power);
    }

    @Override
    public void setMotors(double[] power) {
       this.setMotor(Motor.LOWER_LEFT, power[Motor.LOWER_LEFT.getValue()]);
       this.setMotor(Motor.LOWER_RIGHT, power[Motor.LOWER_RIGHT.getValue()]);
       this.setMotor(Motor.UPPER_RIGHT, power[Motor.UPPER_RIGHT.getValue()]);
       this.setMotor(Motor.UPPER_LEFT, power[Motor.UPPER_LEFT.getValue()]);
    }


    @Override
    public void getMotorSpeed(double[] doubles) {
    }

    @Override
    public double getMotorSpeed() {
        return 0;
    }

    DcMotor moters[];
}
