package site.gonggangam.gonggangam_server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.gonggangam.gonggangam_server.domain.reply.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query(value = """
                        SELECT r
                        FROM Reply r
                        WHERE r.replyId = :replyId
                        AND r.isVisible = true
            """)
    Optional<Reply> getReplyById(@Param("replyId") Long replyId);

    @Query(value = """
                        SELECT r
                        FROM Reply r
                        WHERE r.replyId = :replyId
                        AND r.isVisible = true
                        AND r.rejected = false
            """)
    Optional<Reply> getNotRejectedReplyById(@Param("replyId") Long replyId);

    @Query(value = """
                        SELECT r
                        FROM Reply r
                        WHERE r.diary.writer.userId = :userId
                        AND r.isVisible = true
                        AND r.rejected = false
            """)
    List<Reply> getNotRejectedRepliesByReceiverId(@Param("userId") Long userId);

}
