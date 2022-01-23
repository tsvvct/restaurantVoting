package com.github.tsvvct.restaurantvoting.web.uservote;

import com.github.tsvvct.restaurantvoting.model.UserVote;
import com.github.tsvvct.restaurantvoting.repository.UserVoteRepository;
import com.github.tsvvct.restaurantvoting.service.UserVoteService;
import com.github.tsvvct.restaurantvoting.web.AuthUser;
import com.github.tsvvct.restaurantvoting.web.validation.RestaurantHasMenuValidator;
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
    private RestaurantHasMenuValidator restaurantValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.
                setValidator(restaurantValidator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserVote> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.get(id, authUser.id()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete vote {} for user {}", id, authUser.id());
        service.delete(id, authUser.id());
    }

    @GetMapping("/for-date")
    public ResponseEntity<UserVote> getForDate(@AuthenticationPrincipal AuthUser authUser,
                                               @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDate) {
        LocalDate voteDateForQuery = Objects.requireNonNullElse(voteDate, LocalDate.now());
        log.info("get vote for {} for user {}", voteDateForQuery, authUser.id());
        Optional<UserVote> userVote = repository.getVoteForUserAndVoteDate(authUser.id(), voteDateForQuery);
        return ResponseEntity.of(userVote);
    }

    @GetMapping
    public List<UserVote> getUserVotes(@AuthenticationPrincipal AuthUser authUser,
                                       @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDateFrom,
                                       @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDateTo) {
        log.info("get votes from {} to {} for user {}", voteDateFrom, voteDateTo, authUser.id());
        return repository.getUserVotesFiltered(authUser.id(), voteDateFrom, voteDateTo);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserVote> createWithLocation(@AuthenticationPrincipal AuthUser authUser,
                                                       @Valid @RequestBody UserVote vote) {
        log.info("register vote for user {}", authUser.id());
        UserVote created = service.create(vote, authUser.getUser());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @Valid @RequestBody UserVote vote, @PathVariable int id) {
        int userId = authUser.id();
        log.info("update vote id {} for user {}", id, userId);
        service.update(vote, id, userId);
    }
}
