package com.syniverse.wdm.interview.armedforces.repository;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.Unit;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {
    long countByArmy(Army army);

    Optional<Unit> findTopByArmyOrderByCombatPowerDesc(Army army);
    static Specification<Unit> fromArmy(Army armyId) {
        return (unit, cq, cb) -> cb.equal(unit.get("army"), armyId);
    }

    static Specification<Unit> combatPowerFloor(Long combatPower) {
        return (unit, cq, cb) -> cb.ge(unit.get("combatPower"), combatPower);
    }


}
