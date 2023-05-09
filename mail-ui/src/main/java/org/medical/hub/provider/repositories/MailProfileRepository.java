package org.medical.hub.provider.repositories;

import org.medical.hub.provider.entities.MailProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
@Repository

public interface MailProfileRepository extends JpaRepository<MailProfile, Long> {

    MailProfile findByIsActiveProfile(boolean activeStatus);


    MailProfile findByProfileName(String profileName);
    @Query("SELECT e FROM MailProfile e WHERE e.profileName LIKE %:search%")
    Page<MailProfile> findAll(@Param("search") String search, Pageable pageable);

}
