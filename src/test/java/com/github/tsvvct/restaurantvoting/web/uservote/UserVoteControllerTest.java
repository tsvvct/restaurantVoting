package com.github.tsvvct.restaurantvoting.web.uservote;

import com.github.tsvvct.restaurantvoting.util.DateTimeUtil;
import com.github.tsvvct.restaurantvoting.web.AbstractControllerTest;
import com.github.tsvvct.restaurantvoting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.tsvvct.restaurantvoting.repository.UserVoteRepository;
import com.github.tsvvct.restaurantvoting.to.UserVoteTo;
import com.github.tsvvct.restaurantvoting.util.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.tsvvct.restaurantvoting.util.TestUtil.getUserVoteToFromUser;

class UserVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserVoteController.REST_URL + '/';

    @Autowired
    private UserVoteRepository userVoteRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + UserVoteTestData.USER_VOTE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserVoteTestData.USERTO_VOTE_MATCHER.contentJson(UserVoteTestData.userVote1));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + UserVoteTestData.USER_VOTE1_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + (UserVoteTestData.USER_VOTE1_ID + 1)))
                .andExpect(status().isNoContent());
        assertFalse(userVoteRepository.findById(UserVoteTestData.USER_VOTE1_ID + 1).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void deletePreviousVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + UserVoteTestData.USER_VOTE1_ID))
                .andExpect(status().is(409));
        assertTrue(userVoteRepository.findById(UserVoteTestData.USER_VOTE1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/for-date")
                .param("voteDate", UserVoteTestData.testDate.minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserVoteTestData.USERTO_VOTE_MATCHER.contentJson(UserVoteTestData.userVote1));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getUserVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserVoteTestData.USERTO_VOTE_MATCHER.contentJson(UserVoteTestData.userVote1, UserVoteTestData.userVote2));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getFilteredUserVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("voteDateFrom", UserVoteTestData.testDate.minusDays(1).toString())
                .param("voteDateTo", UserVoteTestData.testDate.minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserVoteTestData.USERTO_VOTE_MATCHER.contentJson(List.of(UserVoteTestData.userVote1)));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createWithLocation() throws Exception {
        UserVoteTo newVote = UserVoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        UserVoteTo created = UserVoteTestData.USERTO_VOTE_MATCHER.readFromJson(action);
        int newId = created.getId();
        newVote.setId(newId);
        UserVoteTestData.USERTO_VOTE_MATCHER.assertMatch(created, newVote);
        UserVoteTestData.USERTO_VOTE_MATCHER.assertMatch(getUserVoteToFromUser(userVoteRepository.getById(newId)), newVote);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void createDuplicated() throws Exception {
        UserVoteTo newVote = UserVoteTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().is(409));

    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void update() throws Exception {
        UserVoteTo updated = UserVoteTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + (UserVoteTestData.USER_VOTE1_ID + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        UserVoteTestData.USERTO_VOTE_MATCHER.assertMatch(getUserVoteToFromUser(userVoteRepository.getById(UserVoteTestData.USER_VOTE1_ID + 1)), updated);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createVotingIsOver() throws Exception {
        DateTimeUtil.moveClock(2);
        UserVoteTo newVote = UserVoteTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().is(422));

    }
}
