package org.commandftc;

/**
 * This command ends when one specific sub-command ends. 
 */
public class ParallelSpecifiedCommand extends ParallelCommand {
    private Command specifiedCommand;

    /**
     * @param specifiedCommand The specified command that, once it ends, the entire
     *                         parallel command ends. 
     *                         Obviously this cannot run forever.
     */
    public ParallelSpecifiedCommand(Command specifiedCommand, Command ... commands) {
        super(commands);
        this.specifiedCommand = specifiedCommand;
    }

    /**
     * Overrides the whileUntilLeft method for better efficiency. 
     */
    @Override
    public ParallelSpecifiedCommand whileUntilLeft(Command other) {
        addCommand(other);
        return this;
    }

    @Override
    public boolean isFinished() {
        return specifiedCommand.isFinished();
    }
}