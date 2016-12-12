package fundata.service;

import fundata.model.DSComment;
import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.repository.DSCommentRepository;
import fundata.repository.DataerRepository;
import fundata.repository.DatasetRepository;
import fundata.viewmodel.DSCommentView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by ocean on 16-12-12.
 */
public class DSCommentServiceImpl implements DSCommentService {
    @Autowired
    private DataerRepository dataerRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private DSCommentRepository dsCommentRepository;

    @Override
    public boolean addComment(String dataerName, String datasetName, String content) {
        Dataer dataer = dataerRepository.findByUserName(dataerName);
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);

        if (dataer == null || dataset == null) {
            return false;
        }

        DSComment dsComment = new DSComment();
        dsComment.setContent(content);
        dsComment.setDataer(dataer);
        dsComment.setDataset(dataset);
        dsComment.setTime(new Date());
        dsCommentRepository.save(dsComment);
        return true;
    }

    @Override
    public DSCommentView getComment(String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        DSCommentView dsCommentView = new DSCommentView();
        for (DSComment dsComment : dataset.getDsComments()) {
            dsCommentView.addDSComment(dsComment);
        }
        return dsCommentView;
    }
}
