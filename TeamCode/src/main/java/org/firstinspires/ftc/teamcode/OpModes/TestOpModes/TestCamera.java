package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drivers.Camera;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.DoubleBuffer;

import Milkdromeda.Drivers.RobotCamera;
import Milkdromeda.Image.Image;

//@Disabled
@TeleOp(name = "Camera Test")
public class TestCamera extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotCamera camera = new Camera();
        Image image;

        super.waitForStart();

        while(super.opModeIsActive()) {
            if(super.gamepad1.right_bumper) {
                image = camera.takeImage(null);
            }
        }


    }
}
