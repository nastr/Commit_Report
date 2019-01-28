package com.nastrsoft.commitChecks.repository;

import com.nastrsoft.commitChecks.entity.CredentialsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.StreamSupport;


@Repository
public interface CredentialsRepository extends CrudRepository<CredentialsEntity, Long> {
    /*default CredentialsEntity findByName(String name) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(f -> StringUtils.equalsIgnoreCase(f.getName(), name))
                .findFirst().get();
    }*/

    @Query("from CredentialsEntity r where r.name = :name")
    Iterable<CredentialsEntity> findAllByName(@Param("name") String name);

    default String findByName(String name) {
//        CredentialsEntity c = StreamSupport.stream(findAllByName(name).spliterator(), false)
//                .findFirst().get();
        return StreamSupport.stream(findAllByName(name).spliterator(), false)
                .findFirst().get().getValue();
    }
}
