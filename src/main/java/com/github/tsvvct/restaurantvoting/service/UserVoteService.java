package com.github.tsvvct.restaurantvoting.service;

import com.github.tsvvct.restaurantvoting.error.DataConflictException;
import com.github.tsvvct.restaurantvoting.model.User;
import com.github.tsvvct.restaurantvoting.model.UserVote;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.tsvvct.restaurantvoting.repository.UserVoteRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserVoteService {
    private final UserVoteRepository repository;

    @Transactional
    public UserVote save(UserVote userVote, int id, User user) {

        repository.checkBelong(id, user.getId());
        userVote.setUser(user);
        return repository.save(userVote);
    }

    @Transactional
    public UserVote create(UserVote userVote) {

        repository.getVoteForUserAndVoteDate(userVote.getUser().getId(), LocalDate.now())
                .ifPresent(vote -> {
                    throw new DataConflictException("User already voted today:" + vote);
                });
        return repository.save(userVote);
    }

    @Transactional
    public void delete(int voteId, int userId) {
        UserVote vote = repository.checkBelong(voteId, userId);
        repository.delete(vote);
    }
}
