// ExampleAuto.java
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.commandftc.Command;
import org.commandftc.RobotUniversal;
import org.commandftc.opModes.CommandBasedAuto;
import org.firstinspires.ftc.teamcode.commands.DriveForwardTimeCommand;
import org.firstinspires.ftc.teamcode.commands.LowerRobotCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Elevator;
@Autonomous(name="CommandBasedAutoExample", group ="CBExample")
public class ExampleAuto extends CommandBasedAuto {
    private Drivetrain dt;
    private Elevator elev;

    @Override
    public void plan() {
        dt = new Drivetrain();
        elev = new Elevator();

        addSubsystems(dt, elev);
        setAutoCmd(Command.makeSequential(
                new LowerRobotCommand(elev),
                new DriveForwardTimeCommand(dt, 2, 1.0)
        ));
    }
}
