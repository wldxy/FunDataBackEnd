package fundata.viewmodel;

import fundata.model.DSComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ocean on 16-12-12.
 */
public class DSCommentView {
    List<CommentInfo> comments = new ArrayList<>();

    public DSCommentView() {

    }

    public List<CommentInfo> getComments() {
        return comments;
    }

    public void setComments(List<CommentInfo> comments) {
        this.comments = comments;
    }

    public void addDSComment(DSComment dsComment) {
        this.comments.add(new CommentInfo(dsComment.getDataer().getName(),
                dsComment.getContent(), dsComment.getTime().toString(), dsComment.getDataer().getHead_href()));
    }

}

class CommentInfo {
    CommentInfo(String username, String content, String updatetime, String head_href) {
        this.username = username;
        this.content = content;
        this.updatetime = updatetime;
        this.head_href = head_href;
    }

    String username;

    String content;

    String updatetime;

    String head_href;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setHead_href(String head_href) {
        this.head_href = head_href;
    }

    public String getHead_href() {
        return head_href;
    }
}