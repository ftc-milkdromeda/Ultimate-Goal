package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Drivers.GamePad;
import org.firstinspires.ftc.teamcode.OpModes.Templates.TeleOpTemplate;

import TaskManager.Clock;
import Drivers.Controller;
import TaskManager.KeyTask;
import TaskManager.ThreadManager;

@Disabled
@TeleOp(name="Toggle test")
public class ToggleTest extends TeleOpTemplate {
    @Override
    protected void initHardware() {
        this.task = new ToggleTask(new Clock(50), new GamePad(super.gamepad1), super.hardwareMap.dcMotor.get("motor"));
    }

    @Override
    protected void startSequence() {
        this.task.start();
    }

    @Override
    protected void main() {
        super.telemetry.addData("State: ", task.keyState() ? "True" : "False");
        super.telemetry.update();
    }

    @Override
    protected void finalizer() {
        ThreadManager.stopAllProcess();
    }

    private static class ToggleTask extends KeyTask {
        public ToggleTask(Clock clock, Controller controller, DcMotor motor) {
            super(clock, controller);

            this.key = new Key(super.controller, clock);
        }

        @Override
        protected void deconstructor() {
            key.terminate();
        }

        @Override
        public void run() {
            this.key.start();
            while(!super.isInterrupted());
        }

        @Override
        protected double[] keyMapping() {
            double returnArray[] = { this.key.getToggleState()};
            return returnArray;
        }

        public boolean keyState() {
            return this.keyMapping()[0] == 1.0;
        }

        public static class Key extends KeyTask.Toggle {
            public Key(Controller controller, Clock clock) {
                super(clock, controller);
            }

            @Override
            protected boolean testKey() {
                return super.controller.get_A() == 1.0;
            }

        }

        private Key key;
    }

    ToggleTask task;
    ToggleTask.Key key;
}
