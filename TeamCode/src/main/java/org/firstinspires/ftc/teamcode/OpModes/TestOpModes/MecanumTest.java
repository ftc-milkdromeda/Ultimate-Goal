package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Drivers.MecanumDrive;

import RobotFunctions.MecanumWheels.Procedure;
import RobotFunctions.MecanumWheels.RoughMecanumWheels;
import RobotFunctions.Units_length;
import RobotFunctions.Units_length;

@Disabled
@TeleOp(name = "MecanumTest", group =  "Robot Test")
public class MecanumTest extends OpMode {
    @Override
    public void init() {
        mecanumWheels = RoughMecanumWheels.instance(new MecanumDrive(hardwareMap), 18, 18, Units_length.IN);
    }

    @Override
    public void loop() {
        double pivot = Math.pow(super.gamepad1.right_stick_x, 2) * Math.signum(super.gamepad1.right_stick_x);
        double magnitude = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2));
        double trajectory = Math.acos(gamepad1.left_stick_x/magnitude) * Math.signum(-gamepad1.left_stick_y);

        if(trajectory == 0)
            trajectory = Math.signum(gamepad1.left_stick_x) == 1 ? 0 : Math.PI;

        if(magnitude == 0)
            mecanumWheels.addTrajectory(new Procedure(0, pivot, -1));
        else
            mecanumWheels.addTrajectory(new Procedure(trajectory, magnitude, -pivot * .75));

        mecanumWheels.drive();
    }

    private RoughMecanumWheels mecanumWheels;
}
