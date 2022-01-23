package com.github.tsvvct.restaurantvoting.service;

import com.github.tsvvct.restaurantvoting.model.User;
import com.github.tsvvct.restaurantvoting.model.UserVote;
import com.github.tsvvct.restaurantvoting.repository.RestaurantRepository;
import com.github.tsvvct.restaurantvoting.repository.UserRepository;
import com.github.tsvvct.restaurantvoting.repository.UserVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil.*;

@Service
@RequiredArgsConstructor
public class UserVoteService {
    private final UserVoteRepository repository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public UserVote update(UserVote userVote, int voteId, int userId) {
        checkVotingIsOver();
        userVote.setId(voteId);
        UserVote voteFromDb = repository.get(voteId, userId, LocalDate.now()).orElse(null);
        checkNotFoundWithId(voteFromDb, voteId);
        userVote.setUser(voteFromDb.getUser());
        return repository.save(userVote);
    }

    public UserVote create(UserVote userVote, User user) {
        checkNew(userVote);
        userVote.setUser(user);
        return repository.save(userVote);
    }

    public void delete(int voteId, int userId) {
        checkVotingIsOver();
        checkNotFoundWithId(repository.delete(voteId, userId, LocalDate.now()) != 0, voteId);
    }
}
