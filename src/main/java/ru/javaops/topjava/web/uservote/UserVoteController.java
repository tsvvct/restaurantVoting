package ru.javaops.topjava.web.uservote;

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
import ru.javaops.topjava.model.UserVote;
import ru.javaops.topjava.repository.UserVoteRepository;
import ru.javaops.topjava.service.UserVoteService;
import ru.javaops.topjava.util.validation.ValidationUtil;
import ru.javaops.topjava.web.AuthUser;
import ru.javaops.topjava.web.validation.RestaurantHasMenuValidator;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.javaops.topjava.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserVoteController {

    static final String REST_URL = "/api/profile/votes";

    @Autowired
    protected UserVoteRepository repository;

    @Autowired
    protected UserVoteService service;

    @Autowired
    private RestaurantHasMenuValidator restaurantValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(restaurantValidator);
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
        ValidationUtil.checkVotingIsOver();
        service.delete(id, authUser.id());
    }

    @GetMapping("/for-date")
    public ResponseEntity<UserVote> getForDate(@AuthenticationPrincipal AuthUser authUser,
                                               @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDate) {
        log.info("get vote for {} for user {}", Objects.requireNonNullElse(voteDate, LocalDate.now()), authUser.id());
        Optional<UserVote> userVote = repository.getVoteForUserAndVoteDate(authUser.id(),
                Objects.requireNonNullElse(voteDate, LocalDate.now()));
        return ResponseEntity.of(userVote);
    }

    @GetMapping
    public List<UserVote> getUserVotes(@AuthenticationPrincipal AuthUser authUser,
                                       @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDateFrom,
                                       @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDateTo) {
        return repository.getUserVotesFiltered(authUser.id(), voteDateFrom, voteDateTo);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    protected ResponseEntity<UserVote> createWithLocation(@AuthenticationPrincipal AuthUser authUser,
                                                          @Valid @RequestBody UserVote vote) {
        log.info("register vote for user {}", authUser.id());
        ValidationUtil.checkVotingIsOver();
        ValidationUtil.checkNew(vote);
        vote.setUser(authUser.getUser());
        UserVote created = service.create(vote);
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
        log.info("update vote {} for user {}", vote, userId);
        ValidationUtil.checkVotingIsOver();
        assureIdConsistent(vote, id);
        service.save(vote, id, authUser.getUser());
    }
}
