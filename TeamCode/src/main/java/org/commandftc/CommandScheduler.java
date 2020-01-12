package org.commandftc;
import java.util.*;

public final class CommandScheduler {
    /**
     * @see AccessToken
     */
    private static final class CSAccessToken extends AccessToken {
        private CSAccessToken() { }
    }

    /**
     * Stores an {@code AccessToken}
     */
    private static final CSAccessToken accessToken = new CSAccessToken();

    /**
     * Subsystem and Default Command
     */
    private static final Map<Subsystem, Command> mapSsDefCmd;

    /**
     * Stores a set of subsystems.
     */
    private static final Set<Subsystem> subsystems;

    /**
     * Stores a list of currently scheduled commands
     */
    private static final LinkedList<Command> scheduledCommands;

    /**
     * Stores a list of button "listeners".
     * @see Trigger
     */
    private static final Set<Runnable> buttons;

    private static boolean isOpModeActive = false;
    /**
     * Runs when the entire program starts.
     */
    static {
        mapSsDefCmd = new LinkedHashMap<> ();
        subsystems = new HashSet<Subsystem>();
        scheduledCommands = new LinkedList<Command>();
        buttons = new LinkedHashSet<Runnable>();
    }

    /**
     * Sets a default command of a subsystem.
     */
    public static void setDefaultCommand(Subsystem ss, Command cmd) {
        if(cmd.isFinished()) {
            throw new IllegalArgumentException("Default commands shouldn't be finished.");
        }
        if(!cmd.getRequirements().contains(ss)) {
            throw new IllegalArgumentException("Default commands must require the subsystem.");
        }
        mapSsDefCmd.put(ss, cmd);
    }

    /**
     * Returns the default command of a subsystem.
     */
    public static Command getDefaultCommand(Subsystem ss) {
        return mapSsDefCmd.get(ss);
    }

    /**
     * Registers a subsystem.
     */
    public static void registerSubsystem(Subsystem subsystem) {
        subsystems.add(subsystem);
    }

    /**
     * Schedule a command and init it. 
     * Checks whether all required subsystems are present.
     */
    public static void scheduleCommand(Command cmd) {
        if(!subsystems.containsAll(cmd.getRequirements())) {
            throw new IllegalArgumentException("Cannot schedule command because requirement not met.");
        }
        if(!scheduledCommands.contains(cmd)){
            scheduledCommands.add(cmd);
            cmd.real_init(accessToken);
        }
    }

    /**
     * Schedule a list of commands.
     * @param cmds
     */
    public static void scheduleCommands(Command ... cmds) {
        for(Command cmd : cmds) {
            scheduleCommand(cmd);
        }
    }

    /**
     * Registers a button "listener".
     * @see Trigger
     */
    public static void addButton(Runnable button) {
        buttons.add(button);
    }

    /**
     * Runs all commands once. 
     */
    public static void runOnce() {
        if(!isOpModeActive) return;
        Set<Subsystem> usedSs = new HashSet<Subsystem>();
        ArrayList<Command> toRemove = new ArrayList<Command>();

        for(Runnable button : buttons) {
            button.run();
        }

        for(Command cmd : scheduledCommands) {
            if(cmd.isFinished()) {
                cmd.real_end(accessToken);
                toRemove.add(cmd);
            } else {
                usedSs.addAll(cmd.getRequirements());
                cmd.execute();
            }
        }

        scheduledCommands.removeAll(toRemove);

        for(Subsystem ss : subsystems) {
            if(!usedSs.contains(ss)) {
                Command defaultCommand = mapSsDefCmd.get(ss);
                if(defaultCommand != null) {
                    defaultCommand.execute();
                } else {
                    ss.periodic();
                }
            }
        }
    }

    /**
     * Ends a command immediately and remove the command from the list.
     */
    public static void unscheduleCommand(Command command) {
        if(scheduledCommands.contains(command)) {
            command.real_end(accessToken);
            scheduledCommands.remove(command);
        }
    }

    /**
     * Returns whether a command has been scheduled.
     */
    public static boolean isScheduled(Command command) {
        return scheduledCommands.contains(command);
    }

    /**
     * Schedule all commands in the list.
     */
    public static void unscheduleAll() {
        while(!scheduledCommands.isEmpty()) {
            unscheduleCommand(scheduledCommands.get(0));
        }
    }

    public static void unregisterAllButtons() {
        buttons.clear();
    }

    public static void unregisterSubsystems(Subsystem ... sss) {
        for(Subsystem ss : sss) {
            subsystems.remove(ss);
        }
    }

    public static void unregisterAllSubsystems() {
        subsystems.clear();
    }

    public static void setOpModeActive(boolean isActive) {
        isOpModeActive = isActive;
    }
}