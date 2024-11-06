package com.TTMSGislavedGummiLanka.TTMS_Backend.controller;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Mold;
import com.TTMSGislavedGummiLanka.TTMS_Backend.exception.MoldNotFoundException;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.MoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mold")
public class MoldController {

    @Autowired
    private MoldService moldService;

    @GetMapping("/shared")
    public List<Mold> getMolds() {
        return moldService.getMolds();
    }

    @PostMapping("/shared")
    public Mold insert(@RequestBody Mold mold) {
        return moldService.addMold(mold);
    }

    @PutMapping("/shared/{id}")
    public Mold update(@PathVariable String id, @RequestBody Mold mold) {
        return moldService.updateMold(id, mold);
    }

    @DeleteMapping("/shared/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            moldService.deleteMold(id);
            return ResponseEntity.ok("Mold deleted successfully");
        } catch (MoldNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}