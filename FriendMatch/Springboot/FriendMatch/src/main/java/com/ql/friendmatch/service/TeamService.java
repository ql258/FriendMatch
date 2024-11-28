package com.ql.friendmatch.service;

import com.ql.friendmatch.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ql.friendmatch.model.domain.User;
import com.ql.friendmatch.model.dto.TeamQuery;
import com.ql.friendmatch.model.request.TeamJoinRequest;
import com.ql.friendmatch.model.request.TeamQuitRequest;
import com.ql.friendmatch.model.request.TeamUpdateRequest;
import com.ql.friendmatch.model.vo.TeamUserVO;

import java.util.List;

/**
 *
 */
public interface TeamService extends IService<Team> {

    long addTeam(Team team, User loginUser);

    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

//    List<TeamUserVO> listTeam(TeamQuery teamQuery, boolean isAdmin);

    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    boolean deleteTeam(long id, User loginUser);

    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

}
