package com.github.tsvvct.restaurantvoting.web.uservote;

import com.github.tsvvct.restaurantvoting.model.UserVote;
import com.github.tsvvct.restaurantvoting.repository.UserVoteRepository;
import com.github.tsvvct.restaurantvoting.web.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = AdminUserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminUserVoteController {
    static final String REST_URL = "/api/admin/votes";

    @Autowired
    private UserVoteRepository repository;

    @GetMapping
    public List<UserVote> get(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDate,
                              @RequestParam @Nullable Integer restaurantId) {
        LocalDate voteDateForQuery = Objects.requireNonNullElse(voteDate, LocalDate.now());
        log.info("get all votes for date {}", voteDateForQuery);
        return repository.getAllFiltered(voteDateForQuery, restaurantId);
    }
}
