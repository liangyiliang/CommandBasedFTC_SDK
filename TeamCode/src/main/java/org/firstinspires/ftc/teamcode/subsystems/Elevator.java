// Elevator.java
package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.commandftc.Subsystem;

import static org.firstinspires.ftc.teamcode.RobotUniversal.hwMap;

public class Elevator extends Subsystem {
    private DcMotor _elevMotor;

    public Elevator() {
        _elevMotor = hwMap.get(DcMotor.class, "elev");
        _elevMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void lower() {
        _elevMotor.setPower(-1);
    }

    public void raise() {
        _elevMotor.setPower(1);
    }

    public void stop() {
        _elevMotor.setPower(0);
    }

    public int getEncoderPosition() {
        return _elevMotor.getCurrentPosition();
    }
}
