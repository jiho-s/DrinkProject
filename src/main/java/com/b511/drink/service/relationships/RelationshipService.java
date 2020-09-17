package com.b511.drink.service.relationships;

import com.b511.drink.domain.relationships.RelationshipRepository;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {
    private RelationshipRepository relationshipRepository;

    public RelationshipService(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }
}
