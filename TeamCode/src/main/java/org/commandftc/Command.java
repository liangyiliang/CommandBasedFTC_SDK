package org.commandftc;
import java.util.*;

public abstract class Command {
    private final Set<Subsystem> requirements = new HashSet<Subsystem>();
    private boolean stopped = false;

    /**
     * Returns a list of all subsystems required by this command.
     * @return a list of all subsystems required by this command
     */
    public Set<Subsystem> getRequirements() {
        return requirements;
    }

    /**
     * Adds an arbitrary number of subsystems to the requirements of this command.
     */
    protected void addRequirements(final Subsystem ... sss) {
        for(final Subsystem ss : sss) {
            requirements.add(ss);
        }
    }

    /**
     * The code in this method will only run once, when the command is scheduled.
     * Override this.
     */
    public void init() {

    }

    /**
     * The code in this method will be executed continuously by the CommandScheduler until isFinished() returns true or the CommandScheduler ends the command.
     * Override this.
     */
    public void execute() {

    }

    /**
     * The code in this method will run only once, when CommandScheduler ends this command (either by interrupting or by stopping it after isFinished() returns true).
     * Override this.
     */
    protected void end() {

    }

    /**
     * DO NOT USE THIS METHOD!
     * This method serves to eliminate ending an ended command.
     */
    public void real_end(AccessToken token) {
        Objects.requireNonNull(token);

        if(!stopped) {
            end();
            stopped = true;
        }
    }

    /**
     * DO NOT USE THIS METHOD!
     * This method runs init and sets stopped to false
     */
    public void real_init(AccessToken token) {
        Objects.requireNonNull(token);

        init();
        stopped = false;
    }

    /**
     * Returns whether this command is finished. Returning false tells CommandScheduler to continue calling the execute() method, and returning true tells it to end the command.
     * For a command that lasts throughout an entire game, for example, it should never "finish", i.e. isFinished() should always yield false.
     * Override this.
     */
    public boolean isFinished() {
        return false;
    }

    public SequentialCommand andThen(Command next) {
        return makeSequential(this, next);
    }

    public ParallelWaitCommand also(Command other) {
        return makeParallelWait(this, other);
    }

    public ParallelSpecifiedCommand whileUntilLeft(Command other) {
        return makeParallelSpecified(this, this, other);
    }

    public ParallelSpecifiedCommand whileUntilRight(Command other) {
        return makeParallelSpecified(other, this, other);
    }

    public ParallelFirstCommand whileUntilEither(Command other) {
        return makeParallelFirst(other);
    }

    public static SequentialCommand makeSequential(Command ... commands) {
        return new SequentialCommand(commands);
    }

    public static ParallelWaitCommand makeParallelWait(Command ... commands) {
        return new ParallelWaitCommand(commands);
    }

    public static ParallelFirstCommand makeParallelFirst(Command ... commands) {
        return new ParallelFirstCommand(commands);
    }

    public static ParallelSpecifiedCommand makeParallelSpecified(Command specifiedCommand, Command ... commands) {
        return new ParallelSpecifiedCommand(specifiedCommand, commands);
    }
}