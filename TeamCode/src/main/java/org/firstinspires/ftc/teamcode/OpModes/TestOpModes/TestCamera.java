package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drivers.Camera;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.DoubleBuffer;

import Drivers.RobotCamera;

//@Disabled
@TeleOp(name = "Camera Test")
public class TestCamera extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotCamera camera = new Camera();
        double image [][];

        super.waitForStart();

        while(super.opModeIsActive()) {
            if(super.gamepad1.right_bumper) {
                image = camera.takeImage(null);
                Bitmap bitmap =  Bitmap.createBitmap(image.length, image[0].length, Bitmap.Config.ARGB_8888);

                double[] linearImage = new double[image.length * image[0].length];
                for(int a = 0; a < image.length; a++) {
                    for(int b = 0; b < image[0].length; b++)
                        linearImage[a * image.length + b] = image[a][b];
                }
                bitmap.copyPixelsFromBuffer(DoubleBuffer.wrap(linearImage));

                try {
                    FileOutputStream out = new FileOutputStream("image");
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                }
                catch (IOException io) {
                    return;
                }
                break;
            }
        }


    }
}
