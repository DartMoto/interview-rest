// $Id: CompanyResource.java 6699 2018-04-18 14:58:06Z g801797 $
// $URL: https://am1p-gen-ias-vcs001.syniverse.com/svn-am/obf/obf-rest/branches/obf_dev_Victor/src/main/java/com/syniverse/obf/company/ui/CompanyResource.java $
package com.syniverse.wdm.interview.armedforces.api;

import com.syniverse.wdm.interview.armedforces.service.ArmedForcesService;
import com.syniverse.wdm.interview.armedforces.view.ArmyDetailsView;
import com.syniverse.wdm.interview.armedforces.view.ArmyInputView;
import com.syniverse.wdm.interview.armedforces.view.UnitDetailsView;
import com.syniverse.wdm.interview.armedforces.view.UnitInputView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/armed-forces/v1/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ArmedForcesController {

    private final ArmedForcesService armedForcesService;

    @ApiOperation(value = "Create an army", notes = "Returns the ID of the newly created army")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Long.class)})
    @PostMapping("/armies")
    public Long createArmy(@RequestBody final ArmyInputView army) {
        return this.armedForcesService.createArmy(Optional.ofNullable(army).map(ArmyInputView::toArmy).orElse(null));
    }

    @ApiOperation(value = "List the summary of all the armies", notes = "Returns a list of all armies")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class, responseContainer = "List")})
    @GetMapping("/armies")
    public List<ArmyDetailsView> getArmies() {
        return this.armedForcesService.getArmies().stream().map(ArmyDetailsView::fromArmy).collect(Collectors.toList());
    }

    @ApiOperation(value = "List armies of a given type", notes = "Returns a list of armies of a given type")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class, responseContainer = "List")})
    @GetMapping("/armies/type/{armyType}")
    public List<ArmyDetailsView> getArmiesByType(@PathVariable(name = "armyType") final String armyType) {
        return this.armedForcesService.getArmies().stream().filter(army -> armyType.equalsIgnoreCase(army.getType().toString())).map(ArmyDetailsView::fromArmy).collect(Collectors.toList());
    }

    @ApiOperation(value = "Fetch the army’ details", notes = "Returns army's details")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class),
            @ApiResponse(code = 404, message = "Not found", response = ArmyDetailsView.class)})
    @GetMapping("/armies/{armyId:[\\d]+}")
    public ArmyDetailsView getArmyDetails(@PathVariable(name = "armyId") final Long armyId) {
        return ArmyDetailsView.fromArmy(this.armedForcesService.getArmyById(armyId));
    }

    @ApiOperation(value = "Recruit a unit to the army", notes = "Returns the ID of the newly recruited unit")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Long.class)})
    @PostMapping("/armies/{armyId:[\\d]+}/units")
    public Long recruitUnit(@PathVariable(name = "armyId") final Long armyId, @RequestBody final UnitInputView unit) {
        return this.armedForcesService.recruitUnit(armyId, Optional.ofNullable(unit).map(UnitInputView::toUnit).orElse(null));
    }

    @ApiOperation(value = "Fetch all units of the army", notes = "Returns a list of all units of the army")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UnitDetailsView.class, responseContainer = "List")})
    @GetMapping("/armies/{armyId:[\\d]+}/units")
    public List<UnitDetailsView> getUnitsOfArmy(@PathVariable(name = "armyId") final Long armyId,
                                                @RequestParam(value = "combatPowerFloor", required = false, defaultValue = "0") Long combatPowerFloor,
                                                @RequestParam(value = "order", required = false, defaultValue = "desc") final String order) {
        Sort sort= "desc".equalsIgnoreCase(order)?Sort.by("combatPower").descending():Sort.by("combatPower").ascending();
        return this.armedForcesService.getUnitsOfArmy(armyId, combatPowerFloor, sort).stream().map(UnitDetailsView::fromUnit).collect(Collectors.toList());
    }

    @ApiOperation(value = "Fetch the unit details", notes = "Returns the unit details")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UnitDetailsView.class),
            @ApiResponse(code = 404, message = "Not found", response = UnitDetailsView.class)})
    @GetMapping("/units/{unitId:[\\d]+}")
    public UnitDetailsView getUnitDetails(@PathVariable(name = "unitId") final Long unitId) {
        return UnitDetailsView.fromUnit(this.armedForcesService.getUnitById(unitId));
    }

    @ApiOperation(value = "Delete given unit by Id", notes = "The given unit killed/destroyed (removed from the army)")
    @ApiResponses({@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")})
    @DeleteMapping("/units/{unitId:[\\d]+}")
    public void deleteUnit(@PathVariable(name = "unitId") final Long unitId) {
         this.armedForcesService.deleteUnitById(unitId);
    }

    @ApiOperation(value = "Delete top unit of army", notes = "The strongest unit (by combat power) killed/destroyed (removed from the army)")
    @ApiResponses({@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")})
    @DeleteMapping("/armies/{armyId:[\\d]+}/units")
    public void deleteTopUnit(@PathVariable(name = "armyId") final Long armyId) {
        this.armedForcesService.deleteTopUnit(armyId);
    }
}
