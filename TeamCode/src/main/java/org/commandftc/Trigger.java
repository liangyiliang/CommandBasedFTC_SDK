package org.commandftc;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * Represents a button / trigger that can get activated.
 */
public class Trigger {
    private BooleanSupplier isActive;

    /**
     * Can take a lambda expression.
     */
    public Trigger(BooleanSupplier isActive) {
        this.isActive = isActive;
    }

    /**
     * Default trigger never gets triggered.
     */
    public Trigger() {
        this.isActive = () -> false;
    }

    /**
     * Returns the current state of the trigger.
     * @return the current state of the trigger.
     */
    public boolean get() {
        return isActive.getAsBoolean();
    }

    /**
     * When the trigger gets active, changing from unactive to active.
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger whenActive(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();

            @Override
            public void run() {
                boolean nowPressed = get();
                if(!lastPressed && nowPressed) {
                    CommandScheduler.scheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    /**
     * When the button gets pressed for the first time
     * changing from unpressed to pressed.
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger whenPressed(Command command) {
        return whenActive(command);
    }

    /**
     * When the trigger holds active. If the command ends before 
     * release of the trigger, reschedule it.
     * 
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger whileActiveContinuous(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();
            @Override 
            public void run() {
                boolean nowPressed = get();
                if(nowPressed) {
                    CommandScheduler.scheduleCommand(command);
                } else if(lastPressed) {
                    CommandScheduler.unscheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });

        return this;
    }

    /**
     * When the button holds active. If the command ends before 
     * release of the button, reschedule it.
     * 
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger whileHeld(Command command) { 
        return whileActiveContinuous(command); 
    }

    /**
     * When the trigger holds active. If the command ends before 
     * release of the trigger, do nothing.
     * 
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger whileActiveOnce(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();

            @Override
            public void run() {
                boolean nowPressed = get();
                if(!lastPressed && nowPressed){
                    CommandScheduler.scheduleCommand(command);
                } else if (lastPressed && !nowPressed) {
                    CommandScheduler.unscheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    /**
     * When the button holds active. If the command ends before 
     * release of the button, do nothing.
     * 
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger whenHeld(Command command) {
        return whileActiveOnce(command);
    }

    /**
     * When the trigger gets inactive, changing from active to unactive.
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger whenInactive(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();
            @Override
            public void run() {
                boolean nowPressed = get();
                if(lastPressed && !nowPressed) {
                    CommandScheduler.scheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    
    /**
     * When the trigger gets released, changing from pressed to released.
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger whenReleased(Command command) {
        return whenInactive(command);
    }

    /**
     * When the trigger becomes active from unactive,
     * it toggles the command on or off.
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger toggleWhenActive(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();

            @Override
            public void run() {
                boolean nowPressed = get();
                if(!lastPressed && nowPressed) {
                    if(CommandScheduler.isScheduled(command)) {
                        CommandScheduler.unscheduleCommand(command);
                    } else {
                        CommandScheduler.scheduleCommand(command);
                    }
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    /**
     * When the button becomes pressed from released,
     * it toggles the command on or off.
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger toggleWhenPressed(Command command) {
        return toggleWhenActive(command);
    }

    /**
     * When the trigger is active from unactive, unschedule a given command.
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger cancelWhenActive(final Command command) {
        Objects.requireNonNull(command);
        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();

            @Override
            public void run() {
                boolean nowPressed = get();
                if (!lastPressed && nowPressed) {
                    CommandScheduler.unscheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    /**
     * When a button is pressed from released, unschedule a given command.
     * @param command
     * @return this trigger so it can be chained.
     */
    public Trigger cancelWhenPressed(Command command) {
        return cancelWhenActive(command);
    }

    /**
     * Returns a trigger that only gets activated when both this
     * and other trigger are activated.
     * @param other
     * @return
     */
    public Trigger and(Trigger other) {
        return new Trigger(() -> (get() && other.get()));
    }

    /**
     * Returns a trigger that only gets activated when this
     * or other or both triggers are activated.
     * @param other
     * @return
     */
    public Trigger or(Trigger other) {
        return new Trigger(() -> (get() || other.get()));
    }

    /**
     * Returns a trigger that is activated when this is unactivated, 
     * and that is unactivated when this is activated.
     */
    public Trigger not() {
        return new Trigger(() -> !get());
    }
}