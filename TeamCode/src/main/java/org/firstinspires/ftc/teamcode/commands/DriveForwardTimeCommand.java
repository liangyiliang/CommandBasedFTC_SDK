// DriveForwardTimeCommand.java
package org.firstinspires.ftc.teamcode.commands;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.commandftc.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
public class DriveForwardTimeCommand extends Command {
    private Drivetrain dt;
    private ElapsedTime timer;
    private int targetSeconds;
    private double power;
    public DriveForwardTimeCommand(Drivetrain drivetrain, int target, double power) {
        dt = drivetrain;
        addRequirements(dt);

        timer = new ElapsedTime();
        targetSeconds = target;
        this.power = power;
    }
    @Override
    public void init() {
        timer.reset();
    }
    @Override
    public void execute() {
        dt.tankDrive(power, power);
    }
    @Override
    public void end() {
        dt.tankDrive(0,0);
    }
    @Override
    public boolean isFinished() {
        return timer.seconds() >= targetSeconds;
    }
}
