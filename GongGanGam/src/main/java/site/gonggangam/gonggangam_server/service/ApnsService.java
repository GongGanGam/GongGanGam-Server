package site.gonggangam.gonggangam_server.service;

public interface ApnsService {

    void notifySharedDiary(String deviceToken);
    void notifyReply(String deviceToken);
    void notifyChat(String deviceToken);
}
