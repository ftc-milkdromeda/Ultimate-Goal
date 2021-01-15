package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import android.text.method.TimeKeyListener;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="Tick Tester")
public class TickTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor motors[] = new DcMotor[4];

        super.waitForStart();

        for(int a = 0; a < 4; a++) {
            motors[a] = super.hardwareMap.dcMotor.get("motor" + a);
            motors[a].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        motors[2].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[3].setDirection(DcMotorSimple.Direction.REVERSE);

        for(int a = 0; a < 4; a++) {
            motors[a].setTargetPosition(TickTester.ticks);
            motors[a].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        motors[0].setPower(0.5);
        motors[1].setPower(0.5);
        motors[2].setPower(0.5);
        motors[3].setPower(0.5);

        while(motors[0].isBusy() || motors[1].isBusy() || motors[2].isBusy() || motors[3].isBusy());
    }

    private final static int ticks = 2000;
}
