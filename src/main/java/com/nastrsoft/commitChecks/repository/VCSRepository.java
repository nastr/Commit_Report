package com.nastrsoft.commitChecks.repository;

import com.nastrsoft.commitChecks.entity.VCSEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface VCSRepository extends CrudRepository<VCSEntity, Long> {
    /*default Set<String> findByName(String name) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(f -> StringUtils.equalsIgnoreCase(f.getName(), name))
                .map(e -> e.getName())
                .collect(Collectors.toSet());
    }*/

    @Query("from VCSEntity v where v.name = :name")
    Iterable<VCSEntity> findAllByName(@Param("name") String name);

    default Set<String> findByName(String name) {
        return StreamSupport.stream(findAllByName(name).spliterator(), false)
                .map(VCSEntity::getName)
                .collect(Collectors.toSet());
    }
}
