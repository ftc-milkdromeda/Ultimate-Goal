package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Drivers.MecanumDrive;

import RobotFunctions.MecanumWheels.Procedure;
import RobotFunctions.MecanumWheels.RoughMecanumWheels;
import RobotFunctions.Units;

@TeleOp(name = "MecanumTest", group =  "Robot Test")
public class MecanumTest extends OpMode {
    @Override
    public void init() {
        moters = new DcMotor[4];

        this.moters[0] = hardwareMap.get(DcMotor.class, "wheel0");
        this.moters[1] = hardwareMap.get(DcMotor.class, "wheel1");
        this.moters[2] = hardwareMap.get(DcMotor.class, "wheel2");
        this.moters[3] = hardwareMap.get(DcMotor.class, "wheel3");

        this.moters[0].setDirection(DcMotorSimple.Direction.FORWARD);
        this.moters[1].setDirection(DcMotorSimple.Direction.FORWARD);
        this.moters[2].setDirection(DcMotorSimple.Direction.REVERSE);
        this.moters[3].setDirection(DcMotorSimple.Direction.REVERSE);

        this.moters[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.moters[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.moters[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.moters[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mecanumWheels = RoughMecanumWheels.instance(new MecanumDrive(moters[0], moters[1], moters[2], moters[3]), 18, 18, Units.IN);
    }

    @Override
    public void loop() {
        double pivot = Math.pow(super.gamepad1.right_stick_x, 2) * Math.signum(super.gamepad1.right_stick_x);
        double magnitude = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2));
        double trojectory = Math.acos(gamepad1.left_stick_x/magnitude) * Math.signum(-gamepad1.left_stick_y);

        if(trojectory == 0)
            trojectory = Math.signum(gamepad1.left_stick_x) == 1 ? 0 : Math.PI;

        if(magnitude == 0)
            mecanumWheels.addTrojectory(new Procedure(0, pivot, -1));
        else
            mecanumWheels.addTrojectory(new Procedure(trojectory, magnitude, -pivot * .75));

        mecanumWheels.drive();
    }

    private DcMotor moters[];
    private RoughMecanumWheels mecanumWheels;
}
