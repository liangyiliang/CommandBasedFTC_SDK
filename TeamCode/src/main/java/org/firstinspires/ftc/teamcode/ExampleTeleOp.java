// ExampleTeleOp.java
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.commandftc.Gp;
import org.commandftc.RobotUniversal;
import org.commandftc.RunCommand;
import org.commandftc.opModes.CommandBasedTeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Elevator;
@TeleOp(name="CommandBasedTeleOpExample", group ="CBExample")
public class ExampleTeleOp extends CommandBasedTeleOp {
    private Drivetrain dt;
    private Elevator elev;

    @Override
    public void assign() {
        dt = new Drivetrain();
        elev = new Elevator();
        elev.setDefaultCommand(new RunCommand(() -> elev.stop(), elev));
        dt.setDefaultCommand(new RunCommand(() -> dt.tankDrive(gp1.left_stick_y(), gp1.right_stick_y()), dt));

        addSubsystems(dt, elev);

        gp1.a().whileHeld(new RunCommand(() -> elev.raise(), elev));
        gp1.b().whileHeld(new RunCommand(() -> elev.lower(), elev));
    }
}
