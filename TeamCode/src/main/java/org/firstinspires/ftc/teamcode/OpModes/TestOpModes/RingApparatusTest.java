package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drivers.Feeder;
import org.firstinspires.ftc.teamcode.Drivers.Intake;
import org.firstinspires.ftc.teamcode.Drivers.Shooter;
import org.firstinspires.ftc.teamcode.Drivers.Storage;

//@Disabled
@TeleOp(name="Ring Apparatus Test")
public class RingApparatusTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //initialize robot functions
        //shooter = new Shooter(super.hardwareMap);
        storage = new Storage(super.hardwareMap);
        feeder = new Feeder(this.storage, super.hardwareMap);
        intake = new Intake(this.storage, super.hardwareMap);

        super.waitForStart();

        while(super.opModeIsActive()) {
           if(gamepad1.right_trigger >= .5)
               intake.runIntake();
           else if(gamepad1.left_trigger >= .5)
               intake.stopIntake();
        }

    }

    private Feeder feeder;
    private Shooter shooter;
    private Storage storage;
    private Intake intake;
}
