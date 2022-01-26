package com.github.tsvvct.restaurantvoting.web.uservote;

import com.github.tsvvct.restaurantvoting.service.UserVoteService;
import com.github.tsvvct.restaurantvoting.to.UserVoteTo;
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
public class AdminUserVoteController {
    static final String REST_URL = "/api/admin/votes";

    @Autowired
    private UserVoteService service;

    @GetMapping
    public List<UserVoteTo> get(@RequestParam @Nullable Integer restaurantId,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDate) {
        LocalDate voteDateForQuery = Objects.requireNonNullElse(voteDate, LocalDate.now());
        log.info("get all votes for date {}", voteDateForQuery);
        return service.getAllFiltered(voteDateForQuery, restaurantId);
    }
}
