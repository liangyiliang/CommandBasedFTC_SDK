package org.commandftc;

/**
 * All subsystems should be derived from this class.
 * This class represents a Subsystem. In it, you should define all the hardwares
 * and actions of the hardwares.
 */
public abstract class Subsystem {
    public Subsystem() {
        CommandScheduler.registerSubsystem(this);
    }

    /**
     * This is equivalent to a default command, in which the CommandScheduler will run code in this method continuously.
     * @see setDefaultCommand
     * @see getDefaultCommand
     */
    public void periodic () {

    }

    /**
     * A default command is executed whenever no other scheduled command is
     * using this subsystem.
     * This function sets the default command.
     * @param command the command that needs to be the default command.
     */
    public void setDefaultCommand(Command command) {
        CommandScheduler.setDefaultCommand(this, command);
    }

    /**
     * 
     * @return the default command.
     */
    public Command getDefaultCommand() {
        return CommandScheduler.getDefaultCommand(this);
    }
}