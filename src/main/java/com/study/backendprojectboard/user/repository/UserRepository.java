package com.study.backendprojectboard.user.repository;

import com.study.backendprojectboard.user.model.BoardVO;
import com.study.backendprojectboard.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByName(String name); // <-> select * from t_user where name='?';
    User findByEmailAndPassword(String email, String password); // <-> select * from t_user where email='?' and password='?'

    // board, table
    // user_id = 1 인 게시글 전체 -> user_id
    // select * from t_board where user_id =1;
    // user_id=1 인 게시글 전체와 사용자 정보를 갖고 오는 SQL, inner join
    // select a.board_id as boardId, b.user_id as userId, a.title as title, a.content as content, b.name as name
    // from t_board a inner join t_user b on a.user_id=b.user_id where b.user_id=1

    // user_id=1 인 게시글 전체와 그 게시글 마다의 댓글 전체를 갖고 오는 SQL
    // user > board > comment ->
    // select * from t_comment c inner join
    // (select from t_board a inner join t_user b on a.user_id=b.user_id where b.user_id=1) d
    // c.board_id = d.board_i
    // comment.comment_id, comment.user_id, comment.content, comment.reg_date, comment.upt_date
    // board.board_id, board.title, board.content, board.reg_date, board.upt_date, board.user_id,
    // user.user_id, user.name, user.password
    // 사용자1이 게시글 1,2,3 3개를 작성했고 작성된 게시글에 댓글이 10개씩 달려 있다.
    // 10 * 3 * 1 = 30
    // 댓글 1~10 - 똑같은 게시글 1의 정보
    // 댓글 11~20 - 똑같은 게시글 2의 정보
    // 댓글 21~30 - 똑같은 게시글 3의 정보
    // master -> usengmin/jpa-relation-study
}
