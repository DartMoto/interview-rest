package com.syniverse.wdm.interview.armedforces.service;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.repository.ArmedForcesRepository;
import com.syniverse.wdm.interview.armedforces.repository.UnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.syniverse.wdm.interview.armedforces.repository.UnitRepository.combatPowerFloor;
import static com.syniverse.wdm.interview.armedforces.repository.UnitRepository.fromArmy;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ArmedForcesService {

    private final ArmedForcesRepository armedForcesRepository;
    private final UnitRepository unitRepository;

    public Long createArmy(final Army army) {

        if (armedForcesRepository.count() < 50) {
            return armedForcesRepository.saveAndFlush(army).getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cannot add more armies. You already have way too many to manage, Sir!");
        }
    }

    public List<Army> getArmies() {
        return armedForcesRepository.findAll();
    }

    public Army getArmyById(final Long armyId) {
        return armedForcesRepository.findById(armyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hmmm. That army does not seem to exist, Sir!"));
    }

    public Long recruitUnit(final Long armyId, final Unit unit) {
        Army army = armedForcesRepository.findById(armyId).orElse(null);
        if (unitRepository.countByArmy(army) < 100) {
            unit.setArmy(army);
            return unitRepository.saveAndFlush(unit).getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cannot add more units. You already have way too many to manage, Sir!");
        }
    }

    public List<Unit> getUnitsOfArmy(final Long armyId, Long combatPower, Sort sort) {
        Army army = getArmyById(armyId);
        return unitRepository.findAll(fromArmy(army).and(combatPowerFloor(combatPower)), sort);
    }

    public Unit getUnitById(Long unitId) {
        return unitRepository.findById(unitId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hmmm. That unit does not seem to exist, Sir!"));
    }

    public void deleteUnitById(Long unitId) {
        unitRepository.deleteById(unitId);
    }

    public void deleteTopUnit(Long armyId) {
        Army army = getArmyById(armyId);
        Unit unit = unitRepository.findTopByArmyOrderByCombatPowerDesc(army)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hmmm. That unit does not seem to exist, Sir!"));
        unitRepository.delete(unit);
    }
}
