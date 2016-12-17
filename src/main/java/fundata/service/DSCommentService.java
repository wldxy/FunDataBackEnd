package fundata.service;

import fundata.model.DSComment;
import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.viewmodel.DSCommentView;

/**
 * Created by ocean on 16-12-12.
 */
public interface DSCommentService {
    public DSComment addComment(String dataerName, String datasetName, String content);

    public DSCommentView getComment(String datasetName);
}
