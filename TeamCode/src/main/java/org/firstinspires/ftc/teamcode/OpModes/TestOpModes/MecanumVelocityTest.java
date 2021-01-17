package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drivers.MecanumDrive;

import Milkdromeda.RobotFunctions.MecanumWheels.Procedure;
import Milkdromeda.RobotFunctions.MecanumWheels.RoughMecanumWheels;
import Milkdromeda.RobotFunctions.Units_length;

@Disabled
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
        mecanumWheels = RoughMecanumWheels.instance(new MecanumDrive(super.hardwareMap), 16, 13.3125, Units_length.IN);
        mecanumWheels.addTrajectory(new Procedure(this.trojectory, this.power, this.pivot));
        mecanumWheels.addTrajectory(new Procedure(this.trojectory, this.power, this.pivot));
        MecanumVelocityTest.MotorActivity controller = new MotorActivity(this.mecanumWheels);

        super.waitForStart();

        while(super.opModeIsActive()) {
           super.telemetry.addData("Trajectory: ", "%.1f", this.trojectory * 180 / Math.PI);
            while (!super.gamepad1.x)
                super.telemetry.update();

            System.out.println("Starting Test.");

            controller.start();

            long startTime = System.currentTimeMillis();
            long endTime = startTime;
            while (endTime - startTime <= this.testDuration * 1000 && super.gamepad1.x) {
                endTime = System.currentTimeMillis();
                super.telemetry.addData("Time: ", "%.2f", (endTime - startTime) / 1000f);
                super.telemetry.update();
            }

            controller.interrupt();
            System.out.println("Finished Test");
            System.out.println("Final Time " + (endTime - startTime) / 1000f);

            trojectory += 15 * Math.PI / 180;
            mecanumWheels.addTrajectory(new Procedure(this.trojectory, this.power, this.pivot));

            while(!super.gamepad1.y) {
                telemetry.update();
                if(!super.opModeIsActive())
                    return;
            }

            controller = new MotorActivity(this.mecanumWheels);
        }
    }

    private RoughMecanumWheels mecanumWheels;
    private double trojectory = 0;
    private double pivot = 0.0;
    private double power = .3;
    private int testDuration = 2; //seconds
}
