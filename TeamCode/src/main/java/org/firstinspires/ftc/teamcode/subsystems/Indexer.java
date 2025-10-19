package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Config;

/*
    TODO: Fix issue where spamming bumpers makes current tick offset
 */

// can only move counter clockwise
// https://www.gobilda.com/5203-series-yellow-jacket-planetary-gear-motor-19-2-1-ratio-24mm-length-8mm-rex-shaft-312-rpm-3-3-5v-encoder
public class Indexer extends SubsystemBase {
    private final SpindexerMotor motor;
    private int intakeIndex = 0;
    private static int sixtyDegreeRevolutions = 0;
    private final IntakeIndex[] slots = {new IntakeIndex(), new IntakeIndex(), new IntakeIndex()};
    private Telemetry telemetry;
    public Indexer(HardwareMap hw, Telemetry telemetry) {
        motor = new SpindexerMotor(hw);
        this.telemetry = telemetry;
    }

    // rotates the spindexer motor once and updates the intake index

    @Override
    public void periodic() {
        motor.rotate();
        telemetry.addData("CurrentTick", motor.currentTick);
        telemetry.addData("target", motor.targetTick);
    }

    public void rotate120(boolean clockwise) {
        motor.rotate120(clockwise);
        if (clockwise) {
            intakeIndex = Util.modPlusOne(intakeIndex);
        } else {
            intakeIndex = Util.modMinusOne(intakeIndex);
        }
    }

    public void rotate60(boolean clockwise) {
        motor.rotate60(clockwise);
        if (clockwise) {
            intakeIndex = Util.modPlusOne(intakeIndex);
            sixtyDegreeRevolutions++;
        } else {
            intakeIndex = Util.modMinusOne(intakeIndex);
            sixtyDegreeRevolutions--;
        }
        if (sixtyDegreeRevolutions == 2) {
            sixtyDegreeRevolutions = 0;
            intakeIndex++;
        } else if (sixtyDegreeRevolutions == -2) {
            sixtyDegreeRevolutions = 0;
            intakeIndex--;
        }
    }

    public CommandBase rotate120Cmd(boolean clockwise){
        return new InstantCommand(()->this.rotate120(clockwise));
    }

    public  CommandBase rotate60Cmd(boolean clockwise) {
        return new InstantCommand(()->this.rotate60(clockwise));
    }

    public CommandBase nearTarget() {
        return new WaitUntilCommand(() -> motor.currentTick > motor.getTargetTick() - 50 && motor.getCurrentTick() < motor.getTargetTick() + 50);
    }


    // Returns the index of the closest available spindexer slot
    public int findNextAvailableSlot() throws RuntimeException {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isEmpty()) {
                return i;
            }
        }
        throw new RuntimeException("No available slots");
    }
    // Rotates spindexer to the closest available slot
    public void moveToSlot(int slot) {
        if (slot == intakeIndex) {
            // already filled
        } else{
            motor.rotate120(Util.modPlusOne(intakeIndex) == slot);
        }
    }
    // Finds the next available spindexer slot and rotates to it
    public void findEmptyAndMove() {
        int slot = findNextAvailableSlot();
        moveToSlot(slot);
    }

    public char getStateLetter(SlotState slotState){
        if(slotState.equals(SlotState.GREEN)){
            return 'G';
        }
        if(slotState.equals(SlotState.PURPLE)){
            return 'P';
        }
        return 'E';

    }

    public int getBestStartingLocation(){
        char[] balls = {getStateLetter(slots[0].getState()),
                getStateLetter(slots[1].getState()),
                getStateLetter(slots[2].getState()),
                getStateLetter(slots[0].getState()),
                getStateLetter(slots[1].getState()),
                getStateLetter(slots[2].getState()),
        };

        char[] motif = Config.getMotif();
        for(int i = 0; i<balls.length; i++){
            if(i == 4)
                return 0;

            if(balls[i] == motif[0]&&balls[i+1] == motif[1]&&balls[i+2] == motif[2])
                return i;
        }
        return 0;
    }


    // Ejects all three balls from the spindexer
    public void ejectAll() {
        int bestStartingLocation = getBestStartingLocation();
        moveToSlot(bestStartingLocation);
        for (int i = 0; i < 3; i++) {
            //eject();
            //rotate(true);
        }
    }

    public enum SlotState {EMPTY, GREEN, PURPLE}

    public static class IntakeIndex {
        private SlotState state;

        public SlotState getState() {
            return state;
        }

        public boolean isEmpty() {
            return (state == SlotState.EMPTY);
        }
    }

    public static class SpindexerMotor {
        private int currentTick;
        private int targetTick;
        private final DcMotorEx motor;
        final static float encoderResolution = 4096f;
        final static float encoderResolution120 = encoderResolution / 3;
        final static float encoderResolution60 = encoderResolution120 / 2;
        final static float kP = (float) 1 / 2000;

        /*
        float err = encoderResolution120 - motor.getCurrentPosition();
        float power = kP * err;
        int currentRevolutions = motor.getRevolutions();
        int startDeg = (int) (encoderResolution * currentRevolutions);
        int end120 = startDeg + (int) encoderResolution120;
        int end60 = startDeg + (int) encoderResolution60;

        */
        public SpindexerMotor(HardwareMap hw) {
            motor = hw.get(DcMotorEx.class, "spindexerMotor");
        }

        // Returns true if the motor is busy
        public boolean isBusy() {
            return motor.isBusy();
        }

        // Rotates toward the target position
        public void rotate() {
            currentTick = -motor.getCurrentPosition();
            float err = targetTick - currentTick;
            float power = kP * err;

            motor.setPower(power);
        }

        /**
         * Rotates the motor 120 degrees clockwise or counter-clockwise
         * @param clockwise Turn direction
         */
        public void rotate120(boolean clockwise) {
            if (motor.isBusy()) {
                throw new RuntimeException("Motor is busy");
            }
            if (clockwise) {
                targetTick = (int) (targetTick + encoderResolution120);
            } else {
                targetTick = (int) (targetTick - encoderResolution120);
            }
        }
        /**
         * Rotates the motor 60 degrees clockwise or counter-clockwise
         * @param clockwise Turn direction
         */
        public void rotate60(boolean clockwise) {
            if (motor.isBusy()) {
                return;
            }
            int rev = (int) (currentTick / encoderResolution);
            float rotStart = currentTick + (encoderResolution * rev);
            if (clockwise) {
                targetTick = (int) (targetTick + encoderResolution60);
            } else {
                targetTick = (int) (targetTick - encoderResolution60);
            }
        }
        /**
         *
         * @return int (the number of revolutions the motor has made)
         */
        public int getRevolutions() {
            return (int) (-motor.getCurrentPosition() / encoderResolution);
        }

        public int getCurrentTick(){
            return this.currentTick;
        }
        public int getTargetTick(){
            return  this.targetTick;
        }
    }
}
