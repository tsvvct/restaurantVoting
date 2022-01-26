package com.github.tsvvct.restaurantvoting.repository;

import com.github.tsvvct.restaurantvoting.model.UserVote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserVoteRepository extends BaseRepository<UserVote> {

    @Modifying
    @Query("DELETE FROM UserVote uv WHERE uv.id=:id AND uv.user.id=:userId AND uv.voteDate=:vote_date")
    int delete(@Param("id") int id, @Param("userId") int userId, @Param("vote_date") LocalDate voteDate);

    @Query("FROM UserVote uv WHERE uv.id = :id AND uv.user.id = :user_id")
    Optional<UserVote> get(@Param("id") int id, @Param("user_id") int userId);

    @Query("FROM UserVote uv WHERE uv.id = :id AND uv.user.id = :user_id AND uv.voteDate = :vote_date")
    Optional<UserVote> get(@Param("id") int id, @Param("user_id") int userId, @Param("vote_date") LocalDate voteDate);

    @Query("FROM UserVote uv WHERE uv.voteDate = :vote_date AND uv.user.id = :user_id")
    Optional<UserVote> getVoteForUserAndVoteDate(@Param("user_id") int userId, @Param("vote_date") LocalDate voteDate);

    @Query("FROM UserVote uv WHERE uv.user.id = :user_id AND (:voted_from IS NULL OR uv.voteDate >= :voted_from) " +
            "AND (:voted_to IS NULL OR uv.voteDate <= :voted_to)")
    List<UserVote> getUserVotesFiltered(@Param("user_id") int userId, @Param("voted_from") LocalDate votedFrom,
                                        @Param("voted_to") LocalDate votedTo);

    @Query("FROM UserVote uv WHERE uv.voteDate = :vote_date " +
            "AND (:restaurant_id IS NULL OR uv.restaurant.id = :restaurant_id)")
    List<UserVote> getAllFiltered(@Param("vote_date") LocalDate voteDate, @Param("restaurant_id") Integer userId);
}
