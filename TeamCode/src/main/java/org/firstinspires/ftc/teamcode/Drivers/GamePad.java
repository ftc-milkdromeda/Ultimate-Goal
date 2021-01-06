package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import RobotFunctions.TaskManager.Controller;
import RobotFunctions.TaskManager.JoyStick;

public class GamePad implements Controller {
    public GamePad(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    @Override
    public double get_X() {
        return this.gamepad.x ? 1.0 : 0.0;
    }

    @Override
    public double get_Y() {
        return this.gamepad.y ? 1.0 : 0.0;
    }

    @Override
    public double get_A() {
        return this.gamepad.a ? 1.0 : 0.0;
    }

    @Override
    public double get_B() {
        return this.gamepad.b ? 1.0 : 0.0;
    }

    @Override
    public double get_LeftBumper() {
        return this.gamepad.left_bumper ? 1.0 : 0.0;
    }

    @Override
    public double get_RightBumper() {
        return this.gamepad.right_bumper ? 1.0 : 0.0;
    }

    @Override
    public double get_LeftStickButton() {
        return this.gamepad.left_stick_button ? 1.0 : 0.0;
    }

    @Override
    public double get_RightStickButton() {
        return this.gamepad.right_stick_button ? 1.0 : 0.0;
    }

    @Override
    public double get_DpadUp() {
        return this.gamepad.dpad_up ? 1.0 : 0.0;
    }

    @Override
    public double get_DpadDown() {
        return this.gamepad.dpad_down ? 1.0 : 0.0;
    }

    @Override
    public double get_DpadLeft() {
        return this.gamepad.dpad_left ? 1.0 : 0.0;
    }

    @Override
    public double get_DpadRight() {
        return this.gamepad.dpad_right ? 1.0 : 0.0;
    }

    @Override
    public double get_LeftTrigger() {
        return this.gamepad.left_trigger;
    }

    @Override
    public double get_RightTrigger() {
        return this.gamepad.right_trigger;
    }

    @Override
    public JoyStick get_LeftStick() {
        return new JoyStick(this.gamepad.left_stick_x, -this.gamepad.left_stick_y);
    }

    @Override
    public JoyStick get_RightStick() {
        return new JoyStick(this.gamepad.right_stick_x, -this.gamepad.right_stick_y);
    }

    private Gamepad gamepad;
}
