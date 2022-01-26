package com.github.tsvvct.restaurantvoting.web.uservote;

import com.github.tsvvct.restaurantvoting.service.UserVoteService;
import com.github.tsvvct.restaurantvoting.to.UserVoteTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = AdminUserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Getting votes of all users", description = "Obtain information about all voted votes")
public class AdminUserVoteController {
    static final String REST_URL = "/api/admin/votes";

    @Autowired
    private UserVoteService service;

    @GetMapping
    @Operation(
            summary = "Return all users votes",
            description = "Returns all users votes filtered by restaurant id, vote date."
    )
    public List<UserVoteTo> get(
            @Parameter(description = "Restaurant id to get votes for. If empty votes for all restaurants will be returned.")
            @RequestParam @Nullable Integer restaurantId,
            @Parameter(description = "Date to get votes for. If empty current date is used.")
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDate) {
        LocalDate voteDateForQuery = Objects.requireNonNullElse(voteDate, LocalDate.now());
        log.info("get all votes for date {}", voteDateForQuery);
        return service.getAllFiltered(voteDateForQuery, restaurantId);
    }
}
