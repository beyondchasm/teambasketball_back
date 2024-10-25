package site.beyondchasm.teambasketball.match.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.beyondchasm.teambasketball.auth.enums.MatchMemberStatus;
import site.beyondchasm.teambasketball.auth.enums.MatchOverallStatus;
import site.beyondchasm.teambasketball.auth.model.UserPrincipal;
import site.beyondchasm.teambasketball.match.command.MatchCreateCommand;
import site.beyondchasm.teambasketball.match.command.MatchFilterCommand;
import site.beyondchasm.teambasketball.match.command.MatchMemberCommand;
import site.beyondchasm.teambasketball.match.command.MatchUpdateCommand;
import site.beyondchasm.teambasketball.match.mapper.MatchMapper;
import site.beyondchasm.teambasketball.match.model.MatchDto;
import site.beyondchasm.teambasketball.match.model.MatchMemberDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {
    private final MatchMapper matchMapper;

    public List<MatchDto> getMatchList(MatchFilterCommand filterCommand) {
        List<MatchDto> teams = matchMapper.getMatchList(filterCommand);
        long totalElements = matchMapper.getMatchListCount(filterCommand); // 전체 요소 개수 조회

        return teams;
    }

    public MatchDto getMatchDetail(Long match_id) {
        return matchMapper.getMatchDetail(match_id);
    }

    @Transactional
    public MatchDto createMatch(MatchCreateCommand matchCreateCommand) {



        // 현재 로그인한 사용자 정보 얻기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = null;
        if (principal instanceof UserPrincipal) {
            userId = ((UserPrincipal) principal).getId();
        }
        matchCreateCommand.setHost_user_id(userId);
        matchCreateCommand.setStatus(MatchOverallStatus.RECRUITING.getStatus());
        matchMapper.createMatch(matchCreateCommand); // MatchCommand를 이용해 팀 생성
        Long createdMatchId = matchCreateCommand.getMatch_id(); // 생성된 팀의 ID 가져오기

        if (userId != null) {
            MatchMemberCommand matchMemberCommand = new MatchMemberCommand();
            matchMemberCommand.setUser_id(userId);
            matchMemberCommand.setMatch_id(createdMatchId);
            matchMemberCommand.setStatus(MatchMemberStatus.APPROVED.getStatus());

            // 팀 유저 테이블에 사용자 추가
            matchMapper.addMatchMember(matchMemberCommand);
        }

        return getMatchDetail(createdMatchId); // 생성된 팀의 상세 정보 조회
    }

    public MatchDto editMatch(MatchUpdateCommand teamUpdateCommand) {
        matchMapper.editMatch(teamUpdateCommand); // MatchCommand를 이용해 팀 생성


        return getMatchDetail(teamUpdateCommand.getMatch_id());
    }

    public List<MatchMemberDto> getMatchMembrs(Long match_id) {
        return matchMapper.getMatchMembers(match_id);
    }


    public Boolean applyMatch(Long match_id) {
        try {
            // 현재 로그인한 사용자 정보 얻기
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = null;
            if (principal instanceof UserPrincipal) {
                userId = ((UserPrincipal) principal).getId();
            }

            if (userId != null) {
                MatchMemberCommand matchMemberCommand = new MatchMemberCommand();
                matchMemberCommand.setMatch_id(match_id);
                matchMemberCommand.setUser_id(userId);
                matchMemberCommand.setStatus(MatchMemberStatus.APPLIED.getStatus()); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요

                // 팀 유저 테이블에 사용자 추가
                matchMapper.addMatchMember(matchMemberCommand);
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error while applying to match: " + e.getMessage());
            return false;
        }
    }

    // 매치 멤버 삭제
    @Transactional
    public void removeMatchMember(Long match_id, Long user_id) {
        matchMapper.removeMatchMember(match_id, user_id);
    }

    // 매치 멤버 상태 변경
    @Transactional
    public void updateMatchMemberStatus(Long match_id, Long user_id, String status) {
        matchMapper.updateMatchMemberStatus(match_id, user_id, status);
    }
}
