package com.hooni.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
public class SolutionController
{
    @PostMapping(value = "/solution", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> postSolution() {
        return ResponseEntity.status(405).build();
    }

    @GetMapping(value = "/solution", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSolution(@RequestParam(value = "format", required = false) String format) {
        if (format == null) {
            return ResponseEntity.status(400).build();
        }
        if (format.equalsIgnoreCase("short"))
        {
            return ResponseEntity.ok(Map.of("status", "ok"));
        }

        if (format.equalsIgnoreCase("full")) {
            return ResponseEntity.ok(
                    Map.of(
                            "status", "ok",
                            "currentTime", ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
                    )
            );
        }
        return ResponseEntity.status(400).build();
    }
    @DeleteMapping(value = "/solution", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deleteSolution() {
        return ResponseEntity.status(405).build();
    }
    @PutMapping(value = "/solution", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> putSolution() {
        return ResponseEntity.status(405).build();
    }

}
