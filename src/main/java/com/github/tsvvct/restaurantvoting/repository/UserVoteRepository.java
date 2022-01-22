package com.github.tsvvct.restaurantvoting.repository;

import com.github.tsvvct.restaurantvoting.error.DataConflictException;
import com.github.tsvvct.restaurantvoting.model.UserVote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserVoteRepository extends BaseRepository<UserVote> {

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("FROM UserVote uv LEFT JOIN uv.user LEFT JOIN uv.restaurant WHERE uv.id = :id and uv.user.id = :userId")
    Optional<UserVote> get(int id, int userId);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("FROM UserVote uv LEFT JOIN uv.user LEFT JOIN uv.restaurant " +
            "where (uv.voteDate = :vote_date) and uv.user.id = :user_id")
    Optional<UserVote> getVoteForUserAndVoteDate(@Param("user_id") int userId, @Param("vote_date") LocalDate menuDate);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("FROM UserVote uv LEFT JOIN uv.user LEFT JOIN uv.restaurant " +
            "where uv.user.id = :user_id and (coalesce(:voted_from, null) is null or uv.voteDate >= :voted_from) " +
            "and (coalesce(:voted_to, null) is null or uv.voteDate <= :voted_to)")
    List<UserVote> getUserVotesFiltered(@Param("user_id") int userId, @Param("voted_from") LocalDate votedFrom,
                                        @Param("voted_to") LocalDate votedTo);

    default UserVote checkBelong(int id, int userId) {
        UserVote vote = get(id, userId).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + " doesn't belong to User id=" + userId));
        if (!vote.getVoteDate().equals(LocalDate.now())) {
            throw new DataConflictException("Can't modify old votes.");
        }
        return vote;
    }
}
