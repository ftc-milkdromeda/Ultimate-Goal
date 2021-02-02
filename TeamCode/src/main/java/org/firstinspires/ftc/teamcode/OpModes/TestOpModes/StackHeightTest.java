/*package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drivers.Camera;
import org.firstinspires.ftc.teamcode.Tasks.Auto.StackHeightTask;

import Milkdromeda.Drivers.DriverManager;
import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.ThreadManager;

//@Disabled
@Autonomous(name = "Height Test")
public class StackHeightTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Camera camera = new Camera();
        Clock clock = new Clock(100);
        StackHeightTask task = new StackHeightTask(clock, camera);

        super.waitForStart();
        task.setImage(camera.takeImage(task));
        task.start();

        while(super.opModeIsActive()) {
            super.telemetry.addData("Rings", "%d", task.getRingHeight());
            super.telemetry.update();
        }

        DriverManager.stopAllProcess();
        ThreadManager.stopAllProcess();
    }
}
*/