package com.parjalRai.films.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.model.Blocked;
import com.parjalRai.films.model.dto.BlockedDTO;
import com.parjalRai.films.service.BlockedService;



@RestController
@RequestMapping("/api/v1/blocked")
@CrossOrigin(origins={"http://localhost:3000"})
public class BlockedController {
    


    @Autowired
    private BlockedService blockedService;
    
    @GetMapping("/user/username")
    public ResponseEntity<List<Blocked>> getAllBlockedByBlocker(@PathVariable String username) {
        return ResponseEntity.ok(blockedService.getByBlocker(username));
    }


    @PostMapping
    public ResponseEntity<Blocked> createBlocked(@RequestBody BlockedDTO blockedDTO) {
        try {
            Blocked blocked = blockedService.createBlocked(blockedDTO.blockedUsername(),
                    blockedDTO.blockerUsername());

            return ResponseEntity.ok(blocked);
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlocked(@PathVariable ObjectId id) {
        boolean deleted = blockedService.deleteBlocked(id);
        if (deleted) {
            return ResponseEntity.ok("You have unblocked a user");
        } else{
          return ResponseEntity.notFound().build();
        }
    } 

}
