package com.example.api_Train.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_Train.DTO.Response.ChuyenTauResponse;
import com.example.api_Train.DTO.Response.ChuyenTauSearchResponse;
import com.example.api_Train.Service.impl.SearchChuyenTauService;
import com.example.api_Train.models.ChuyenTau;

@RestController
@RequestMapping("/api")
public class SearchChuyenTauController {

    private final SearchChuyenTauService searchService;

    @Autowired
    public SearchChuyenTauController(SearchChuyenTauService searchService) {
        this.searchService = searchService;
    }

    /**
     * Search train schedules by departure station, arrival station, and departure
     * date
     * 
     * @param gaDi         Departure station
     * @param gaDen        Arrival station
     * @param ngayKhoiHanh Departure date (ISO format)
     * @return ResponseEntity containing the search results or error details
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchChuyenTau(
            @RequestParam String gaDi,
            @RequestParam String gaDen,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayKhoiHanh) {

        try {
            // Fetch the train schedules from the service
            List<ChuyenTau> results = searchService.searchChuyenTau(gaDi, gaDen, ngayKhoiHanh);

            // Map the entities to DTO responses
            List<ChuyenTauSearchResponse> response = results.stream()
                    .map(chuyenTau -> ChuyenTauSearchResponse.ChuyenTauSearchResponse(chuyenTau))
                    .collect(Collectors.toList());

            // Return the response
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // Handle client-side errors (e.g., invalid input)
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage(),
                    "timestamp", LocalDateTime.now()));
        } catch (Exception e) {
            // Handle server-side errors
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Lỗi hệ thống khi tìm kiếm chuyến tàu",
                    "timestamp", LocalDateTime.now()));
        }
    }

    @GetMapping("/today")
    public ResponseEntity<?> getAllChuyenTauForToday() {
        try {
            // Fetch the top 8 train journeys for today
            List<ChuyenTau> results = searchService.getAllChuyenTauForToday();

            if (results.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không có chuyến tàu nào cho hôm nay.");
            }

            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi lấy chuyến tàu hôm nay: " + e.getMessage());
        }
    }
}
