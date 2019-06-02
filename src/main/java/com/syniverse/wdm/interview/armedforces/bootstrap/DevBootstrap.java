package com.syniverse.wdm.interview.armedforces.bootstrap;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.ArmyType;
import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.dto.UnitType;
import com.syniverse.wdm.interview.armedforces.service.ArmedForcesService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {


    private ArmedForcesService armedForcesService;

    public DevBootstrap(ArmedForcesService armedForcesService) {
        this.armedForcesService = armedForcesService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        armedForcesService.createArmy(
                new Army("North navy", ArmyType.NAVY,
                        Unit.builder().combatPower(20L).type(UnitType.CORVETTE).build(),
                        Unit.builder().combatPower(80L).type(UnitType.AIRCRAFT_CARRIER).build(),
                        Unit.builder().combatPower(30L).type(UnitType.CORVETTE).build()));

        armedForcesService.createArmy(new Army("South navy", ArmyType.NAVY,
                Unit.builder().combatPower(25L).type(UnitType.CORVETTE).build(),
                Unit.builder().combatPower(55L).type(UnitType.AIRCRAFT_CARRIER).build(),
                Unit.builder().combatPower(45L).type(UnitType.AIRCRAFT_CARRIER).build(),
                Unit.builder().combatPower(65L).type(UnitType.AIRCRAFT_CARRIER).build(),
                Unit.builder().combatPower(35L).type(UnitType.CORVETTE).build(),
                Unit.builder().combatPower(45L).type(UnitType.AIRCRAFT_CARRIER).build(),
                Unit.builder().combatPower(55L).type(UnitType.CORVETTE).build(),
                Unit.builder().combatPower(65L).type(UnitType.AIRCRAFT_CARRIER).build(),
                Unit.builder().combatPower(95L).type(UnitType.AIRCRAFT_CARRIER).build(),
                Unit.builder().combatPower(25L).type(UnitType.CORVETTE).build()));


        armedForcesService.createArmy(new Army("Royal Air Force", ArmyType.AIR_FORCE,
                Unit.builder().combatPower(25L).type(UnitType.FIGHTER_JET).build(),
                Unit.builder().combatPower(55L).type(UnitType.BOMBER).build(),
                Unit.builder().combatPower(45L).type(UnitType.FIGHTER_JET).build(),
                Unit.builder().combatPower(65L).type(UnitType.BOMBER).build(),
                Unit.builder().combatPower(35L).type(UnitType.FIGHTER_JET).build(),
                Unit.builder().combatPower(45L).type(UnitType.FIGHTER_JET).build(),
                Unit.builder().combatPower(55L).type(UnitType.FIGHTER_JET).build(),
                Unit.builder().combatPower(65L).type(UnitType.BOMBER).build(),
                Unit.builder().combatPower(95L).type(UnitType.BOMBER).build(),
                Unit.builder().combatPower(25L).type(UnitType.FIGHTER_JET).build()));

        armedForcesService.createArmy(new Army("15th Army", ArmyType.INFANTRY,
                Unit.builder().combatPower(2L).type(UnitType.PARATROOPER).build(),
                Unit.builder().combatPower(5L).type(UnitType.PARATROOPER).build(),
                Unit.builder().combatPower(4L).type(UnitType.PARATROOPER).build(),
                Unit.builder().combatPower(6L).type(UnitType.PARATROOPER).build(),
                Unit.builder().combatPower(3L).type(UnitType.PARATROOPER).build(),
                Unit.builder().combatPower(5L).type(UnitType.PARATROOPER).build(),
                Unit.builder().combatPower(6L).type(UnitType.PARATROOPER).build(),
                Unit.builder().combatPower(9L).type(UnitType.PARATROOPER).build()));
    }
}
