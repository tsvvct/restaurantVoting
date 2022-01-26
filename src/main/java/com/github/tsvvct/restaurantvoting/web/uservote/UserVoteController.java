package com.github.tsvvct.restaurantvoting.web.uservote;

import com.github.tsvvct.restaurantvoting.repository.UserVoteRepository;
import com.github.tsvvct.restaurantvoting.service.UserVoteService;
import com.github.tsvvct.restaurantvoting.to.UserVoteTo;
import com.github.tsvvct.restaurantvoting.web.AuthUser;
import com.github.tsvvct.restaurantvoting.web.validation.RestaurantValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserVoteController {

    static final String REST_URL = "/api/profile/votes";

    @Autowired
    private UserVoteRepository repository;

    @Autowired
    private UserVoteService service;

    @Autowired
    private RestaurantValidator restaurantValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(restaurantValidator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserVoteTo> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return ResponseEntity.of(service.get(id, authUser.id()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete vote {} for user {}", id, authUser.id());
        service.delete(id, authUser.id());
    }

    @GetMapping("/for-date")
    public ResponseEntity<UserVoteTo> getForDate(@AuthenticationPrincipal AuthUser authUser,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDate) {
        LocalDate voteDateForQuery = Objects.requireNonNullElse(voteDate, LocalDate.now());
        log.info("get vote for {} for user {}", voteDateForQuery, authUser.id());
        Optional<UserVoteTo> userVoteTo = service.getForUserAndForDate(authUser.id(), voteDateForQuery);
        return ResponseEntity.of(userVoteTo);
    }

    @GetMapping
    public List<UserVoteTo> getUserVotes(@AuthenticationPrincipal AuthUser authUser,
             @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDateFrom,
             @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDateTo) {
        log.info("get votes from {} to {} for user {}", voteDateFrom, voteDateTo, authUser.id());
        return service.getUserVotesToFiltered(authUser.id(), voteDateFrom, voteDateTo);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserVoteTo> createWithLocation(@AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserVoteTo voteTo) {
        log.info("register vote for user {}", authUser.id());
        UserVoteTo created = service.create(voteTo, authUser.getUser());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserVoteTo enable(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id,
            @Valid @RequestBody UserVoteTo voteTo) {
        int userId = authUser.id();
        log.info("change vote with id={} for user with id={}", id, userId);
        return service.patch(voteTo, id, userId);
    }
}
