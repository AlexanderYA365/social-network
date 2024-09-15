package com.getjavajob.training.yakovleva.repository;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.GroupMembers;
import com.getjavajob.training.yakovleva.common.utilsEnum.GroupRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupMembersRepository extends CrudRepository<GroupMembers, Integer> {

    List<GroupMembers> getGroupByMemberId(int member);

    List<GroupMembers> getMembersByGroup(Group group);

    @Query("SELECT gm FROM GroupMembers gm")
    List<GroupMembers> getAll();

    @Transactional
    void deleteByGroup(Group group);

    @Transactional
    GroupMembers save(GroupMembers groupMembers);

    List<GroupMembers> findAllByMember(Account member, Pageable pageable);

    @Query("SELECT COUNT(g) FROM GroupMembers g WHERE g.member.id=:id")
    long getCountOfGroup(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("delete from GroupMembers b where b.member.id=:id and b.group.groupId=:groupId")
    void deleteMemberFromGroupMembers(@Param("id") int id, @Param("groupId") int groupId);

    @Transactional
    @Modifying
    @Query("update GroupMembers members set members.groupRole=:role WHERE members.id=:id")
    void updateRoleGroupMembers(@Param("role") GroupRole role, @Param("id") int id);

}