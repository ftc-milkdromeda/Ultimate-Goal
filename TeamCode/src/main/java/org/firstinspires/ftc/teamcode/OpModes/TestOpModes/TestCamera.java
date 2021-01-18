package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drivers.Camera;

import Milkdromeda.Drivers.RobotCamera;
import Milkdromeda.Image.Bitmap;

//@Disabled
@TeleOp(name = "Camera Test")
public class TestCamera extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotCamera camera = new Camera();
        Bitmap image;

        super.waitForStart();

        int counter = 0;

        while(super.opModeIsActive()) {
            if(super.gamepad1.right_bumper) {
                image = camera.takeImage(null);
                image.makeGrayscale();

                image.writeImage("/storage/self/primary/FIRST/bitmap" + counter++);
            }
        }
    }
}
