// Drivetrain.java
package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.commandftc.Subsystem;

import static org.commandftc.RobotUniversal.hwMap;

public class Drivetrain extends Subsystem {
    private DcMotor lDrive, rDrive;

    public Drivetrain() {
        lDrive = hwMap.get(DcMotor.class, "ld");
        rDrive = hwMap.get(DcMotor.class, "rd");
        rDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void tankDrive(double left, double right) {
        lDrive.setPower(left);
        rDrive.setPower(right);
    }
}
