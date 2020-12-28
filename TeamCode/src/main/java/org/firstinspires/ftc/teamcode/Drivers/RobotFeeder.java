package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Servo;

public class RobotFeeder {
    private double normalizeDegrees(double angle) {

    }

    /**
     * @brief Class that operates on the robot feeder.
     * @param servo The servo that is going to be moved.
     * @param maxAngle The maximum angle that can be achieved by the servo in radians.
     */
    public RobotFeeder(Servo servo, double maxAngle) {
        this.maxAngle = maxAngle;
        this.servo = servo;
    }

    public void feed() {
        servo.setPosition(this.angle);
    }

    private final double angle = 25; //angle in degrees
    private double maxAngle;
    private Servo servo;
}
