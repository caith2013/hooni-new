package com.hooni.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class SolutionController
{
    @PostMapping("/solution")
    @ResponseBody
    public ResponseEntity postSolution() {
        return ResponseEntity.status(405).build();
    }
    @GetMapping("/solution")
    @ResponseBody
    public ResponseEntity<String> getSolution(@RequestParam(value = "format", required = false) String format) {
        if (format == null) {
            return ResponseEntity.status(400).build();
        }
        if (format.equalsIgnoreCase("short"))
        {
            return ResponseEntity.ok(Map.of("status", "ok").toString());
        } else if (format.equalsIgnoreCase("full")) {
            return ResponseEntity.ok(Map.of("status", "ok", "currentTime", System.currentTimeMillis()).toString());
        }
        return ResponseEntity.status(400).build();
    }
    @DeleteMapping("/solution")
    @ResponseBody
    public ResponseEntity deleteSolution() {
        return ResponseEntity.status(405).build();
    }
    @PutMapping("/solution")
    @ResponseBody
    public ResponseEntity putSolution() {
        return ResponseEntity.status(405).build();
    }

}
