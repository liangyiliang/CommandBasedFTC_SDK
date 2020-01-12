package org.commandftc;
import java.util.*;


/**
 * Represents a command base. All other commands should be derived from this.
 */
public abstract class Command {
    /** Stores a set of subsystems required by this command */
    private final Set<Subsystem> requirements = new HashSet<Subsystem>();

    /** Stores whether this subsystem has been stopped by the CommandScheduler */
    private boolean stopped = false;

    /**
     * Returns a set of all subsystems required by this command.
     * @return a set of all subsystems required by this command
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
     * DO NOT USE / OVERRIDE THIS METHOD!
     * This method can only be accessed with a valid token, i.e. {@code CommandScheduler.CSAccessToken}.
     * This method runs the user-defined init() and then does some extra work.
     */
    public void real_init(AccessToken token) {
        Objects.requireNonNull(token);

        init();
        stopped = false;
    }

    /**
     * The code in this method will be executed continuously 
     * until the CommandScheduler ends the command.
     * Override this.
     */
    public void execute() {

    }

    /**
     * The code in this method will run only once, when CommandScheduler 
     * ends this command (either by interrupting or by stopping it after isFinished()
     * returns true).
     * Override this.
     */
    protected void end() {

    }

    /**
     * DO NOT USE THIS METHOD!
     * This method requires a valid token, such as {@code CommandScheduler.CSAccessToken}.
     * This method first checks whether the current command is stopped. If it is stopped, do thing
     * (avoid over-stopping a command). Otherwise, it calls the user-defined end() method
     * and sets the {@code stopped} flag to be true.
     */
    public void real_end(AccessToken token) {
        Objects.requireNonNull(token);

        if(!stopped) {
            end();
            stopped = true;
        }
    }



    /**
     * Returns whether this command is finished. Returning false tells 
     * CommandScheduler to continue calling the execute() method, and 
     * returning true tells it to end the command.
     * For a command that lasts throughout an entire game, for example, 
     * it should never "finish", i.e. isFinished() should always yield false.
     * 
     * By default, a command is not finished.
     * Override this as needed.
     */
    public boolean isFinished() {
        return false;
    }

    /**
     * Creates a command that runs this command first, then {@code next} next. <br/>
     * Example: {@code commandA.andThen(commandB)}
     * @param next the next command
     * @return a command that runs this command first, then {@code next} next.
     */
    public SequentialCommand andThen(Command next) {
        return makeSequential(this, next);
    }

    /**
     * Creates a command that runs this and {@code next} command at the same time, 
     * stopping when both are finished. <br/>
     * Example: {@code commandA.also(commandB)}
     * @param other the other command
     * @return a command that runs this and {@code next} command at the same time, 
     * stopping when both are finished.
     */
    public ParallelWaitCommand also(Command other) {
        return makeParallelWait(this, other);
    }

    /**
     * Creates a command that runs this and {@code next} command at the same time, 
     * stopping when the left command is finished. <br/>
     * Example: {@code commandA.whileUntilLeft(commandB)}
     * @param other the other command
     * @return a command that runs this and {@code next} command at the same time, 
     * stopping when the left command is finished.
     */
    public ParallelSpecifiedCommand whileUntilLeft(Command other) {
        return makeParallelSpecified(this, this, other);
    }

    /**
     * Creates a command that runs this and {@code next} command at the same time, 
     * stopping when the right command is finished. <br/>
     * Example: {@code commandA.whileUntilRight(commandB)}
     * @param other the other command
     * @return a command that runs this and {@code next} command at the same time, 
     * stopping when the right command is finished.
     */
    public ParallelSpecifiedCommand whileUntilRight(Command other) {
        return makeParallelSpecified(other, this, other);
    }

    /**
     * Creates a command that runs this and {@code next} command at the same time, 
     * stopping when either command is finished. <br/>
     * Example: {@code commandA.whileUntilEithert(commandB)}
     * @param other the other command
     * @return a command that runs this and {@code next} command at the same time, 
     * stopping when either command is finished.
     */
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