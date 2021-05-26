package ru.itis.androidtechpractice.repositories.my;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.androidtechpractice.dto.ActDto;
import ru.itis.androidtechpractice.dto.ActProofDto;

import java.util.List;

@Component
public class UserActsMyRepositoryImpl implements UserActsMyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<ActDto> fromUserMapper = (resultSet, i) ->
            ActDto.builder()
                    .type(ActDto.Type.USER)
                    .actStatus(ActDto.State.valueOf(resultSet.getString("act_status")))
                    .id(resultSet.getInt("id"))
                    .dateOfEnding(resultSet.getLong("date_of_ending"))
                    .dateOfCreation(resultSet.getLong("date_of_creation"))
                    .title(resultSet.getString("title"))
                    .reward(resultSet.getInt("reward"))
                    .foreignId(resultSet.getInt("user_id"))
                    .build();

    private static final RowMapper<ActDto> fromGroupMapper = (resultSet, i) ->
            ActDto.builder()
                    .type(ActDto.Type.GROUP)
                    .actStatus(ActDto.State.valueOf(resultSet.getString("act_status")))
                    .id(resultSet.getInt("id"))
                    .dateOfEnding(resultSet.getLong("date_of_ending"))
                    .dateOfCreation(resultSet.getLong("date_of_creation"))
                    .title(resultSet.getString("title"))
                    .reward(resultSet.getInt("reward"))
                    .foreignId(resultSet.getInt("group_id"))
                    .build();

    private static final RowMapper<ActProofDto> proofs = (resultSet, i) ->
            ActProofDto.builder()
                    .sendDate(resultSet.getLong("send_date"))
                    .text(resultSet.getString("text"))
                    .id(resultSet.getInt("id"))
                    .moderatorId(resultSet.getInt("moderator_id"))
                    .latitude(resultSet.getDouble("latitude"))
                    .longitude(resultSet.getDouble("longitude"))
                    .build();

    @Override
    public List<ActDto> findAllUserActs(Integer userId) {
        List<ActDto> resultList = jdbcTemplate.query(
                "select * from user_act where user_id = " + userId,
                fromUserMapper
        );

        resultList.addAll(
                jdbcTemplate.query(
                        "select * from group_act where group_id in (select group_id from users_groups_rel where user_id = " + userId + ")",
                        fromGroupMapper
                )
        );

        return resultList;
    }

    @Override
    public List<ActDto> findAllContinueUserActs(Integer userId) {
        List<ActDto> resultList = jdbcTemplate.query(
                "select * from user_act where user_id = " + userId + " and act_status = 'CONTINUE'",
                fromUserMapper
        );

        resultList.addAll(
                jdbcTemplate.query(
                        "select * from group_act where group_id in (select group_id from users_groups_rel where user_id = " + userId + ") and act_status = 'CONTINUE'",
                        fromGroupMapper
                )
        );

        return resultList;
    }

    @Override
    public List<ActDto> findAllEndUserActs(Integer userId) {
        List<ActDto> resultList = jdbcTemplate.query(
                "select * from user_act where user_id = " + userId + " and act_status = 'END'",
                fromUserMapper
        );

        resultList.addAll(
                jdbcTemplate.query(
                        "select * from group_act where group_id in (select group_id from users_groups_rel where user_id = " + userId + ") and act_status = 'END'",
                        fromGroupMapper
                )
        );

        return resultList;
    }

    @Override
    public List<ActProofDto> findAllApprovedActs(Integer userId) {
        List<ActProofDto> resultList =
                jdbcTemplate.query(
                        "select * from group_act_proof where group_act_id in (select id from group_act where group_id in (select group_id from users_groups_rel where user_id = "+ userId +")) and state = 'APPROVED'",
                        proofs
                );

        resultList.addAll(
                jdbcTemplate.query(
                        "select * from user_act_proof where user_act_id in (select id from user_act where user_id = "+ userId +") and state = 'APPROVED'",
                        proofs
                )
        );

        return resultList;

    }


}
