package de.ytendx.xac.notify.type;

import lombok.Getter;

@Getter
public enum CheckType {

    AIM_A("AimA", 5, false),
    AIM_B("AimB", 10, true),
    AIM_C("AimC", 3, false),
    AUTO_CLICKER_A("AutoClickerA", 10, false),
    AUTO_CLICKER_B("AutoClickerB", 3, false),
    AUTO_CLICKER_C("AutoClickerC", 15, true),
    KILLAURA_A("KillAuraA", 5, false),
    KILLAURA_B("KillAuraB", 5, false),
    KILLAURA_C("KillAuraC", 40, true),
    KILLAURA_D("KillAuraD", 5, false),
    KILLAURA_E("KillAuraE", 3, false),
    KILLAURA_F("KillAuraF", 3, false),
    KILLAURA_G("KillAuraG", 5, false),
    KILLAURA_H("KillAuraH", 5, false),
    KILLAURA_I("KillAuraI", 3, true),
    KILLAURA_J("KillAuraJ", 10, true),
    RANGE_A("RangeA", 5, false),
    RANGE_B("RangeB", 5, false),
    RANGE_C("RangeC", 5, false),
    VELOCITY_A("VelocityA", 5, true),
    VELOCITY_B("VelocityB", 10, true),
    VELOCITY_C("VelocityC", 5, true),
    FLY_A("FlyA", 1, false),
    FLY_B("FlyB", 3, true),
    FLY_C("FlyC", 1, false),
    FLY_D("FlyD", 40, true),
    FLY_E("FlyE", 3, false),
    FLY_F("FlyF", 5, true),
    INVENTORY_MOVE_A("InventoryMoveA", 3, false),
    IRREGULAR_MOVEMENT_A("IrregularMovementA", 3, false),
    JESUS_A("JesusA", 3, true),
    JESUS_B("JesusB", 3, true),
    JESUS_C("JesusC", 5, true),
    JESUS_D("JesusD", 40, true),
    JESUS_E("JesusE", 10, true),
    MOTION_A("MotionA", 3, false),
    MOTION_B("MotionB", 3, false),
    MOTION_C("MotionC", 20, true),
    MOTION_D("MotionD", 20, true),
    MOTION_E("MotionE", 5, false),
    MOTION_F("MotionF", 3, false),
    MOTION_G("MotionG", 3, false),
    MOTION_H("MotionH", 5, true),
    NOFALL_A("NoFallA", 3, true),
    NOWEB_A("NoWebA", 5, false),
    SPEED_A("SpeedA", 5, true),
    SPEED_B("SpeedB", 20, false),
    SPEED_C("SpeedC", 5, false),
    SPEED_D("SpeedD", 10, false),
    CHEST_STEALER_A("ChestStealerA", 3, false),
    CHEST_STEALER_B("ChestStealerB", 3, false),
    FAST_BREAK_A("FastBreakA", 3, false),
    IRREGULAR_BUILDING_A("IrregularBuildingA", 3, false),
    IRREGULAR_BUILDING_B("IrregularBuildingB", 3, false),
    REGEN_A("RegenA", 3, false);

    private String name;
    private int maxVL;
    private boolean experimental;

    CheckType(String name, int maxVL, boolean experimental) {
        this.name = name;
        this.maxVL = maxVL;
        this.experimental = experimental;
    }
}
