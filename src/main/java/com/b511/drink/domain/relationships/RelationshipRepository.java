package com.b511.drink.domain.relationships;

import com.b511.drink.domain.accounts.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, UUID> {
    @EntityGraph(attributePaths = {"from", "to"})
    List<Relationship> findByFromAndStatus(Account account, RelationshipStatus relationshipStatus);
    @EntityGraph(attributePaths = {"from", "to"})
    List<Relationship> findByToAndStatus(Account account, RelationshipStatus relationshipStatus);

    boolean existsByFromAndTo(Account from, Account to);
}
