package com.github.tsvvct.restaurantvoting.service;

import com.github.tsvvct.restaurantvoting.model.Restaurant;
import com.github.tsvvct.restaurantvoting.model.User;
import com.github.tsvvct.restaurantvoting.model.UserVote;
import com.github.tsvvct.restaurantvoting.repository.RestaurantRepository;
import com.github.tsvvct.restaurantvoting.repository.UserVoteRepository;
import com.github.tsvvct.restaurantvoting.to.UserVoteTo;
import com.github.tsvvct.restaurantvoting.util.UserVoteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;
import static com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil.checkVotingIsOver;

@Service
@RequiredArgsConstructor
public class UserVoteService {
    private final UserVoteRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public UserVoteTo patch(UserVoteTo userVoteTo, int voteId, int userId) {
        checkVotingIsOver();
        UserVote userVote = repository.get(voteId, userId, LocalDate.now()).orElse(null);
        checkNotFoundWithId(userVote, voteId);
        userVote.setRestaurant(getRestaurantById(userVoteTo.getRestaurantId()));
        return UserVoteUtil.createToFromVote(userVote);
    }

    @Transactional
    public UserVoteTo create(UserVoteTo userVoteTo, User user) {
        userVoteTo.setVoteDate(LocalDate.now());
        UserVote userVote = UserVoteUtil.createVoteFromTo(userVoteTo, user, getRestaurantById(userVoteTo.getRestaurantId()));
        return UserVoteUtil.createToFromVote(repository.save(userVote));
    }

    public void delete(int voteId, int userId) {
        checkVotingIsOver();
        checkNotFoundWithId(repository.delete(voteId, userId, LocalDate.now()) != 0, voteId);
    }

    public List<UserVoteTo> getUserVotesToFiltered(int userId, LocalDate voteDateFrom, LocalDate voteDateTo) {
        return UserVoteUtil.getListToFromListOfVote(repository.getUserVotesFiltered(userId, voteDateFrom, voteDateTo));
    }

    public Optional<UserVoteTo> getForUserAndForDate(int userId, LocalDate voteDateForQuery) {
        return Optional.ofNullable(UserVoteUtil.createToFromVote(repository.getVoteForUserAndVoteDate(userId, voteDateForQuery)
                .orElse(null)));
    }

    public Optional<UserVoteTo> get(int id, int userId) {
        return Optional.ofNullable(UserVoteUtil.createToFromVote(repository.get(id, userId).orElse(null)));
    }

    public List<UserVoteTo> getAllFiltered(LocalDate voteDateForQuery, Integer restaurantId) {
        return UserVoteUtil.getListToFromListOfVote(repository.getAllFiltered(voteDateForQuery, restaurantId));
    }

    private Restaurant getRestaurantById(Integer id) {
        return restaurantRepository.getById(id);
    }
}
