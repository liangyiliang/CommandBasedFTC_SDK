// LowerRobotCommand.java
package org.firstinspires.ftc.teamcode.commands;
import org.commandftc.Command;
import org.firstinspires.ftc.teamcode.subsystems.Elevator;
public class LowerRobotCommand extends Command {
    private Elevator elevator;
    private final int targetPosition = 10000; // DEPENDS.

    public LowerRobotCommand(Elevator elev) {
        elevator = elev;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.lower();
    }

    @Override
    public void end() {
        elevator.stop();
    }

    @Override
    public boolean isFinished() {
        return elevator.getEncoderPosition() > targetPosition;
    }
}
