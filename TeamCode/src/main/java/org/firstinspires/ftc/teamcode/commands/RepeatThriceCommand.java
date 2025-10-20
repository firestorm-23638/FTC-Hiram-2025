package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import static com.arcrobotics.ftclib.command.CommandGroupBase.requireUngrouped;

public class RepeatThriceCommand extends CommandBase {
    private Command mCommand;
    private int count = 0;

    public RepeatThriceCommand(Command command) {
        this.mCommand = command;
        requireUngrouped(command);
        m_requirements.addAll(command.getRequirements());
    }

    @Override
    public void initialize() {
        mCommand.initialize();
    }

    @Override
    public void execute() {
        mCommand.execute();
        if (mCommand.isFinished()) {
            mCommand.end(false);
            count++;
            if (count != 3)
                mCommand.initialize();
        }
    }

    @Override
    public boolean isFinished() {
        return count == 3;
    }

    @Override
    public void end(boolean interrupted) {
        if (count != 3)
            mCommand.end(true);
    }

    @Override
    public boolean runsWhenDisabled() {
        return mCommand.runsWhenDisabled();
    }
}
