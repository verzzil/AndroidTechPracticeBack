package ru.itis.androidtechpractice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.androidtechpractice.dto.GroupActDto;
import ru.itis.androidtechpractice.dto.GroupActProofDto;
import ru.itis.androidtechpractice.dto.GroupDto;
import ru.itis.androidtechpractice.dto.ModeratorDecisionDto;
import ru.itis.androidtechpractice.services.GroupsService;

@RestController
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @GetMapping("/group/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable("groupId") Integer groupId) {
        return ResponseEntity.ok(
                groupsService.findGroupById(groupId)
        );
    }

    @PostMapping("/group/create")
    public ResponseEntity<Integer> createGroup(@RequestBody GroupDto groupDto) {
        groupsService.createGroup(groupDto);
        return ResponseEntity.ok(1);
    }

    @PostMapping("/group/act/create")
    public ResponseEntity<String> createAct(@RequestBody GroupActDto groupActDto) {
        groupsService.createAct(groupActDto);
        return ResponseEntity.ok("str");
    }

    @PostMapping("/group/act/proof/create")
    public ResponseEntity<String> createProof(@RequestBody GroupActProofDto groupActProofDto) {
        groupsService.createProof(groupActProofDto);
        return ResponseEntity.ok("str");
    }

    @PostMapping("/group/act/proof/moderator-decision")
    public ResponseEntity moderatorDecision(@RequestBody ModeratorDecisionDto moderatorDecisionDto) {
        try {
            groupsService.proofDecision(moderatorDecisionDto);
        } catch (IllegalAccessException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok("str");

    }

}
