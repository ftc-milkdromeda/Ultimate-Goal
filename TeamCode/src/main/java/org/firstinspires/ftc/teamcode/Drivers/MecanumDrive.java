package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import RobotFunctions.MecanumWheels.Drive;
import RobotFunctions.MecanumWheels.Motor;

public class MecanumDrive extends Drive {
    public MecanumDrive(HardwareMap hardware) {
        this.motors = new DcMotor[4];

        this.motors[0].setDirection(DcMotorSimple.Direction.FORWARD);
        this.motors[1].setDirection(DcMotorSimple.Direction.FORWARD);
        this.motors[2].setDirection(DcMotorSimple.Direction.REVERSE);
        this.motors[3].setDirection(DcMotorSimple.Direction.REVERSE);

        this.motors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.motors[0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.motors[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.motors[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.motors[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.motors[0] = hardware.dcMotor.get("motor0");
        this.motors[1] = hardware.dcMotor.get("motor1");
        this.motors[2] = hardware.dcMotor.get("motor2");
        this.motors[3] = hardware.dcMotor.get("motor3");
    }

    @Override
    public void setMotor(Motor motor, double power) {
        this.motors[motor.getValue()].setPower(power);
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

    DcMotor motors[];
}
