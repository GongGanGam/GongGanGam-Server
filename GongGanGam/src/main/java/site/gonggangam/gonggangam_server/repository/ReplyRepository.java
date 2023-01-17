package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.reply.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<Reply> findByReplyIdAndIsVisible(Long replyId, Boolean isVisible);
    Optional<Reply> findByReplyIdAndRejectedAndIsVisible(Long replyId, Boolean rejected, Boolean isVisible);
    List<Reply> findAllByWriter_UserIdAndRejectedAndIsVisible(Long userId, Boolean rejected, Boolean isVisible);

}
