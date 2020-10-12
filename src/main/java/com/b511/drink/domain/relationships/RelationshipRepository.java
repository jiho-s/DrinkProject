package com.b511.drink.domain.relationships;

import com.b511.drink.domain.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, UUID> {
    List<Relationship> findByFromAndStatus(Account account, RelationshipStatus relationshipStatus);
    List<Relationship> findByToAndStatus(Account account, RelationshipStatus relationshipStatus);

    boolean existsByFromAndTo(Account from, Account to);
}
