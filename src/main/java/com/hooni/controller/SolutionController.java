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
    public ResponseEntity<Map<String, Object>> postSolution() {
        return ResponseEntity.status(405).body(
                Map.of(
                        "status", "error",
                        "message", "method POST is not allowed for this endpoint"
                )
        );
    }

    @GetMapping(value = "/solution", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSolution(@RequestParam(value = "format", required = false) String format) {
        if (format == null) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "error",
                            "message", "format query parameter is required"
                    )
            );
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
        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", "error",
                        "message", "format must be either 'short' or 'full'"
                )
        );
    }
    @DeleteMapping(value = "/solution", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteSolution() {
        return ResponseEntity.status(405).body(
                Map.of(
                        "status", "error",
                        "message", "method DELETE is not allowed for this endpoint"
                )
        );
    }
    @PutMapping(value = "/solution", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> putSolution() {
        return ResponseEntity.status(405).body(
                Map.of(
                        "status", "error",
                        "message", "method PUT is not allowed for this endpoint"
                )
        );
    }

}
