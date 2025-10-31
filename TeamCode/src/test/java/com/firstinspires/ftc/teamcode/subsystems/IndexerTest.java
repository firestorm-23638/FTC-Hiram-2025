package com.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Patterns;
import org.firstinspires.ftc.teamcode.util.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class IndexerTest {

    private Indexer indexer;
    private DcMotorEx indexerMotor;
    private CommandScheduler scheduler;

    @BeforeEach
    public void setup() {
        HardwareMap hardwareMap = mock(HardwareMap.class);
        indexerMotor = mock(DcMotorEx.class);

        when(hardwareMap.get(eq(DcMotorEx.class), eq("spindexerMotor"))).thenReturn(indexerMotor);
        indexer = new Indexer(hardwareMap, mock(Telemetry.class));
        scheduler = CommandScheduler.getInstance();
    }


    @Test
    public void whenRotate60Twice_IntakeIndexShouldIncrease() {
        indexer.rotate60(true);
        indexer.rotate60(true);

        assertEquals(1, indexer.getIntakeIndex());
    }

    @Test
    public void whenRotate60TwiceCC_IntakeIndexShouldWrap() {
        indexer.rotate60(false);
        indexer.rotate60(false);

        assertEquals(2, indexer.getIntakeIndex());
    }

    @Test
    public void whenBetweenSlots_IndexerShouldRotateToNearestSlot() {
        when(indexerMotor.getCurrentPosition()).thenReturn(4096/6);
        indexer.rotateToNearestIndex();
        assertEquals(0, indexer.getIntakeIndex());

        indexer.reset();

        indexer.rotateToNearestIndex();
        when(indexerMotor.getCurrentPosition()).thenReturn((4096/6) * 3);
        assertEquals(1, indexer.getIntakeIndex());

    }

    @Test
    public void afterShootingThreeTimes_ExpectIndexShouldBeZero() {
        indexer.rotate60(true);
        indexer.rotate60(true);
        indexer.rotate60(true);
        indexer.rotate60(true);
        indexer.rotate60(true);
        indexer.rotate60(true);

        assertEquals(0, indexer.getIntakeIndex());
        assertEquals(4092, indexer.getTarget());

        indexer.moveToSlot(2);
        assertEquals(2, indexer.getIntakeIndex());
        assertEquals(2726, indexer.getTarget());
    }

    @Test
    public void whenMotifIsPGP_ExpectIdealStartingSlotToBeTwo() {
        indexer.setSlots("GPP");
        Config.CURRENT_PATTERN = Patterns.PGP;
        assertEquals(2, indexer.getBestStartingLocation());

        indexer.moveToSlot(2);
        assertEquals(Indexer.SlotState.PURPLE, indexer.getCurrentSlot());

        indexer.rotate120(true);
        assertEquals(Indexer.SlotState.GREEN, indexer.getCurrentSlot());

        indexer.rotate120(true);
        assertEquals(Indexer.SlotState.PURPLE, indexer.getCurrentSlot());
    }

    @Test
    public void whenMotifIsGPP_ExpectIdealStartingSlotToBeZero() {
        indexer.setSlots("GPP");
        Config.CURRENT_PATTERN = Patterns.GPP;
        assertEquals(0, indexer.getBestStartingLocation());

        assertEquals(Indexer.SlotState.GREEN, indexer.getCurrentSlot());

        indexer.rotate120(true);
        assertEquals(Indexer.SlotState.PURPLE, indexer.getCurrentSlot());

        indexer.rotate120(true);
        assertEquals(Indexer.SlotState.PURPLE, indexer.getCurrentSlot());

    }

    @Test
    public void whenMotifIsPPG_ExpectIdealStartingSlotToBeOne() {
        indexer.setSlots("GPP");
        Config.CURRENT_PATTERN = Patterns.PPG;
        assertEquals(1, indexer.getBestStartingLocation());

        indexer.moveToSlot(1);
        assertEquals(Indexer.SlotState.PURPLE, indexer.getCurrentSlot());

        indexer.rotate120(true);
        assertEquals(Indexer.SlotState.PURPLE, indexer.getCurrentSlot());

        indexer.rotate120(true);
        assertEquals(Indexer.SlotState.GREEN, indexer.getCurrentSlot());

    }



}
