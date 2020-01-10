package org.commandftc;


public class ParallelWaitCommand extends ParallelCommand {

    public ParallelWaitCommand(Command ... commands) {
        super(commands);
    }


    @Override
    public ParallelWaitCommand also(Command other) {
        addCommand(other);
        return this;
    }

    @Override
    public boolean isFinished() {
        for(Command cmd : commands) {
            if(!cmd.isFinished()) {
                return false;
            }
        }
        return true;
    }
}
