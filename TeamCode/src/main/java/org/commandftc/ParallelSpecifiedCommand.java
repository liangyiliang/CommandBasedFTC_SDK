package org.commandftc;


public class ParallelSpecifiedCommand extends ParallelCommand {
    private Command specifiedCommand;

    public ParallelSpecifiedCommand(Command specifiedCommand, Command ... commands) {
        super(commands);
        this.specifiedCommand = specifiedCommand;
    }

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