package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Drivers.MecanumDrive;

import RobotFunctions.MecanumWheels.Procedure;
import RobotFunctions.MecanumWheels.RoughMecanumWheels;
import RobotFunctions.Units;

@TeleOp(name = "MecanumVelocityTest")
public class MecanumVelocityTest extends LinearOpMode {
    private static class MotorActivity extends Thread {
        MotorActivity(RoughMecanumWheels mecanumWheels) {
            this.mecanumWheels = mecanumWheels;
        }

        public boolean getStatus() {
            return this.status;
        }

        @Override
        public void run() {
            this.status = true;
            this.mecanumWheels.drive();

            for( ; ; ) {
                if(Thread.interrupted()) {
                    this.mecanumWheels.stop();
                    this.status = false;
                    return;
                }
            }
        }

        private boolean status = false;
        private RoughMecanumWheels mecanumWheels;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        motors = new DcMotor[4];

        this.motors[0] = hardwareMap.get(DcMotor.class, "wheel0");
        this.motors[1] = hardwareMap.get(DcMotor.class, "wheel1");
        this.motors[2] = hardwareMap.get(DcMotor.class, "wheel2");
        this.motors[3] = hardwareMap.get(DcMotor.class, "wheel3");

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

        mecanumWheels = RoughMecanumWheels.instance(new MecanumDrive(motors[0], motors[1], motors[2], motors[3]), 18, 18, Units.IN);
        mecanumWheels.addTrojectory(new Procedure(this.trojectory, this.power, this.pivot));
        MecanumVelocityTest.MotorActivity controller = new MotorActivity(this.mecanumWheels);

        waitForStart();

        while(!super.gamepad1.x)
            telemetry.update();

        System.out.println("Starting Test.");

        controller.start();

        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        while(endTime - startTime <= this.testDuration * 1000 && super.gamepad1.x) {
            endTime = System.currentTimeMillis();
            super.telemetry.addData("Time: ", "%.2f", (endTime - startTime)/1000f);
            super.telemetry.update();
        }

        controller.interrupt();
        System.out.println("Finished Test");
        System.out.println("Final Time " + (endTime - startTime)/1000f);

        for( ; ; );
    }

    private RoughMecanumWheels mecanumWheels;
    private DcMotor motors[];
    private double trojectory = Math.PI / 2;
    private double pivot = 0;
    private double power = 1;
    private int testDuration = 1; //seconds
}
