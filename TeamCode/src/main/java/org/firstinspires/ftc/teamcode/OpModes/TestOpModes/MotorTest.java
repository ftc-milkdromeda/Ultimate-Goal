package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp(name = "MoterTest", group = "HardwareTest")
public class MotorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        this.shooter0 = hardwareMap.get(DcMotor.class, "shooter0");

        this.shooter0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.shooter0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        super.waitForStart();

        this.shooter0.setPower(1.0);
        super.telemetry.addData("Status: ","STARTING MOTORS");
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime <= 5000) {
            super.telemetry.addData("Time: ", "%5.2fs", (System.currentTimeMillis() - startTime) / 1000f);
            super.telemetry.update();
        }

        super.telemetry.addData("Status: ","STARTED");

        startTime = System.currentTimeMillis();
        int startEncoder = this.shooter0.getCurrentPosition();

        long endTime = System.currentTimeMillis();
        int endEncoder = this.shooter0.getCurrentPosition();

        while(endTime - startTime <= runtime * 1000) {
            endTime = System.currentTimeMillis();
            endEncoder = this.shooter0.getCurrentPosition();
            super.telemetry.addData("Status: ","STARTED");
            super.telemetry.addData("Test Time: ", "%5.2fs", (endTime - startTime) / 1000f);
            super.telemetry.addData("Encoder Value: ", "%d", endEncoder);
            super.telemetry.update();
        }

        double timeInterval = (double)endTime/1000 - (double)startTime/1000;

        double result = ((endEncoder - startEncoder)/this.tpr)/timeInterval;

        super.telemetry.addData("Status: ","STOPPED");
        super.telemetry.addData("RPM:", "%.5f", result * 60);
        super.telemetry.update();

        startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime > 10000)
            startTime = System.currentTimeMillis();

        while(super.opModeIsActive());
    }

    private DcMotor shooter0;
    private float power = 1.0f;
    private double tpr = 103.6;
    private int runtime = 10;
}
