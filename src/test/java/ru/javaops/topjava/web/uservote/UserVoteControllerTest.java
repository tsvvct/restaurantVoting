package ru.javaops.topjava.web.uservote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava.repository.UserVoteRepository;
import ru.javaops.topjava.to.UserVoteTo;
import ru.javaops.topjava.util.DateTimeUtil;
import ru.javaops.topjava.util.JsonUtil;
import ru.javaops.topjava.web.AbstractControllerTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava.util.TestUtil.getUserVoteToFromUser;
import static ru.javaops.topjava.web.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.topjava.web.user.UserTestData.USER_MAIL;
import static ru.javaops.topjava.web.uservote.UserVoteTestData.*;

class UserVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserVoteController.REST_URL + '/';

    @Autowired
    private UserVoteRepository userVoteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_VOTE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USERTO_VOTE_MATCHER.contentJson(userVote1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_VOTE1_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + (USER_VOTE1_ID + 1)))
                .andExpect(status().isNoContent());
        assertFalse(userVoteRepository.findById(USER_VOTE1_ID + 1).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deletePreviousVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_VOTE1_ID))
                .andExpect(status().is(409));
        assertTrue(userVoteRepository.findById(USER_VOTE1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/for-date")
                .param("voteDate", UserVoteTestData.testDate.minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USERTO_VOTE_MATCHER.contentJson(userVote1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUserVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USERTO_VOTE_MATCHER.contentJson(userVote1, userVote2));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getFilteredUserVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("voteDateFrom", testDate.minusDays(1).toString())
                .param("voteDateTo", testDate.minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USERTO_VOTE_MATCHER.contentJson(List.of(userVote1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        UserVoteTo newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        UserVoteTo created = USERTO_VOTE_MATCHER.readFromJson(action);
        int newId = created.getId();
        newVote.setId(newId);
        USERTO_VOTE_MATCHER.assertMatch(created, newVote);
        USERTO_VOTE_MATCHER.assertMatch(getUserVoteToFromUser(userVoteRepository.getById(newId)), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createDuplicated() throws Exception {
        UserVoteTo newVote = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().is(409));

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        UserVoteTo updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + (USER_VOTE1_ID + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        USERTO_VOTE_MATCHER.assertMatch(getUserVoteToFromUser(userVoteRepository.getById(USER_VOTE1_ID + 1)), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createVotingIsOver() throws Exception {
        DateTimeUtil.moveClock(2);
        UserVoteTo newVote = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().is(422));

    }
}
