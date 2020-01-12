package org.commandftc;


/**
 * This ParallelCommand ends only when ALL sub-commands end.
 */
public class ParallelWaitCommand extends ParallelCommand {

    public ParallelWaitCommand(Command ... commands) {
        super(commands);
    }


    /**
     * Overrides the "also" method for efficiency.
     */
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
