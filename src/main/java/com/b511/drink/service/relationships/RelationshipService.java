package com.b511.drink.service.relationships;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.relationships.Relationship;
import com.b511.drink.domain.relationships.RelationshipRepository;
import com.b511.drink.domain.relationships.RelationshipStatus;
import com.b511.drink.service.dtos.AccountSimpleDto;
import com.b511.drink.service.dtos.RelationshipResponseDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RelationshipService {
    final private RelationshipRepository relationshipRepository;

    public RelationshipService(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    // 친구 목록 조회하기
    public List<RelationshipResponseDto> queryAcceptedAccount(Account account) {
        //from에서 조회 하여 to 정보만 뻄
        Stream<RelationshipResponseDto> toStream = relationshipRepository.findByFromAndStatus(account, RelationshipStatus.Accepted).stream().map(relationship -> {
            Account to = relationship.getTo();
            AccountSimpleDto accountSimpleDto = AccountSimpleDto.builder()
                    .id(to.getId())
                    .modifiedDate(to.getModifiedDate())
                    .name(to.getName())
                    // .picture(to.getPicture())
                    .build();
            return RelationshipResponseDto.builder()
                    .id(relationship.getId())
                    .account(accountSimpleDto)
                    .isMine(true)
                    .build();

        });
        // to에서 조회하여 from 정보만 뺌
        Stream<RelationshipResponseDto> fromStream = relationshipRepository.findByToAndStatus(account, RelationshipStatus.Accepted).stream().map(relationship -> {
            Account from = relationship.getFrom();
            AccountSimpleDto accountSimpleDto = AccountSimpleDto.builder()
                    .id(from.getId())
                    .modifiedDate(from.getModifiedDate())
                    .name(from.getName())
                    // .picture(from.getPicture())
                    .build();
            return RelationshipResponseDto.builder()
                    .id(relationship.getId())
                    .account(accountSimpleDto)
                    .isMine(false)
                    .build();
        });

        // 합치면서 이름순으로 정렬 하면서 리턴
        return Stream.concat(toStream, fromStream).sorted(Comparator.comparing(a -> a.getAccount().getName())).collect(Collectors.toList());
    }

    // 친구 대기중인 친구 조회 시간 순으로 조회
    public List<RelationshipResponseDto> queryPendingAccount(Account account) {
        return relationshipRepository.findByToAndStatus(account, RelationshipStatus.Pending).stream().map(relationship -> {
            Account from = relationship.getFrom();
            AccountSimpleDto accountSimpleDto = AccountSimpleDto.builder()
                    .id(from.getId())
                    // .picture(from.getPicture())
                    .name(from.getName())
                    .build();
            return RelationshipResponseDto.builder()
                    .account(accountSimpleDto)
                    .createdDate(relationship.getCreatedDate())
                    .isMine(false)
                    .id(relationship.getId())
                    .build();
        }).sorted(Comparator.comparing(RelationshipResponseDto::getCreatedDate)).collect(Collectors.toList());
    }
    // 차단 중인 친구 조회하기
    public List<RelationshipResponseDto> queryBlockedAccount(Account account) {
        return relationshipRepository.findByFromAndStatus(account, RelationshipStatus.Blocked).stream().map(relationship -> {
            Account to = relationship.getTo();
            AccountSimpleDto accountSimpleDto = AccountSimpleDto.builder()
                    .id(to.getId())
                    .name(to.getName())
                    // .picture(to.getPicture())
                    .build();
            return RelationshipResponseDto.builder()
                    .id(relationship.getId())
                    .account(accountSimpleDto)
                    .isMine(false)
                    .build();

        }).sorted(Comparator.comparing(a -> a.getAccount().getName())).collect(Collectors.toList());

        // 합치면서 이름순으로 정렬 하면서 리턴
    }

    //친구 만들기
    public Optional<Relationship> createRelationship(Account from, Account to) {
        //이미 있는경우 null 리턴
        if (relationshipRepository.existsByFromAndTo(from, to) || relationshipRepository.existsByFromAndTo(to, from))
            return Optional.empty();
        Relationship relationship = Relationship.builder()
                .from(from)
                .to(to)
                .status(RelationshipStatus.Pending)
                .build();
        return Optional.of(relationshipRepository.save(relationship));
    }

    //친구 조회하기
    public Optional<Relationship> getRelationship(UUID id) {
        return relationshipRepository.findById(id);
    }

    //친구 상태 수정하기
    public Optional<Relationship> editRelationshipStatus(UUID id, RelationshipStatus status, Account toAccount) {
        Optional<Relationship> optionalRelationship = relationshipRepository.findById(id);

        if (optionalRelationship.isEmpty())
            return Optional.empty();

        Relationship relationship = optionalRelationship.get();

        // 받는 사람이 신청한게 아닌경우 나중에 스프링 시큐리티 추가시
//        if (!relationship.getTo().getId().equals(toAccount.getId()))
//            throw new AccessDeniedException;

        relationship.setStatus(status);
        relationshipRepository.save(relationship);

        return Optional.of(relationship);
    }

    //친구 삭제하기
    public Optional<UUID> deleteRelationship(UUID id, Account account) {
        Optional<Relationship> optionalRelationship = relationshipRepository.findById(id);

        if (optionalRelationship.isEmpty())
            return Optional.empty();

        Relationship relationship = optionalRelationship.get();

        //내가 추가한 친구가 아닌지 확인
//        if (!relationship.getFrom().getId().equals(account.getId()))
//            throw new AccessDeniedException

        relationshipRepository.delete(relationship);
        return Optional.of(id);
    }


}
