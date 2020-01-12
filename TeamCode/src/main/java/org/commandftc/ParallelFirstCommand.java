package org.commandftc;

/**
 * A ParallelCommand that ends when either of the sub-commands end.
 */
public class ParallelFirstCommand extends ParallelCommand {

    public ParallelFirstCommand(Command ... commands) {
        super(commands);
    }

    /**
     * Overrides the whileUntilEither method for efficiency.
     */
    @Override
    public ParallelFirstCommand whileUntilEither(Command other) {
        addCommand(other);
        return this;
    }

    @Override
    public boolean isFinished() {
        if(commands.isEmpty()) {
            return true;
        }
        for(Command cmd : commands) {
            if(cmd.isFinished()) return true;
        }
        return false;
    }
}