// ExampleTeleOp.java
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.commandftc.Gp;
import org.commandftc.RunCommand;
import org.commandftc.opModes.CommandBasedTeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Elevator;
@TeleOp(name="CommandBasedTeleOpExample", group ="CBExample")
public class ExampleTeleOp extends CommandBasedTeleOp {
    private Drivetrain dt;
    private Elevator elev;
    private Gp gp1, gp2;

    @Override
    public void assign() {
        RobotUniversal.hwMap = hardwareMap;
        dt = new Drivetrain();
        elev = new Elevator();
        elev.setDefaultCommand(new RunCommand(() -> elev.stop()));
        dt.setDefaultCommand(new RunCommand(() -> dt.tankDrive(gp1.left_stick_y(), gp1.right_stick_y())));

        addSubsystems(dt, elev);

        gp1 = new Gp(gamepad1);
        gp2 = new Gp(gamepad2);

        gp1.a().whileHeldContinuous(new RunCommand(() -> elev.raise()));
        gp1.b().whileHeldContinuous(new RunCommand(() -> elev.lower()));
    }
}
