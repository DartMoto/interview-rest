package com.syniverse.wdm.interview.armedforces.repository;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmedForcesRepository extends JpaRepository<Army, Long> {
}
