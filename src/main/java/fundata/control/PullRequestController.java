package fundata.control;

import fundata.model.PullRequest;
import fundata.repository.DataerRepository;
import fundata.service.PullRequestService;
import fundata.service.QiniuService;
import fundata.viewmodel.HotProject;
import fundata.viewmodel.PullRequestView;
import fundata.viewmodel.UpFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-6.
 */
@RestController
public class PullRequestController {
    @Autowired
    private PullRequestService pullRequestService;

    @Autowired
    private QiniuService qiniuService;

    @RequestMapping(value = "/getPullRequest", method = RequestMethod.POST)
    public PullRequestView getPullRequest(@RequestParam(name = "datasetname") String datasetName,
                                          @RequestParam(name = "page") int page) {
        Set<PullRequest> pullRequests = pullRequestService.findByDatasetName(datasetName);
        if (pullRequests != null) {
            return new PullRequestView(pullRequests);
        } else {
            return new PullRequestView(0);
        }
    }

    @RequestMapping(value = "/newPullRequest", method = RequestMethod.POST)
    public UpFileInfo newPullRequest(@RequestParam(name = "datasetname") String datasetname,
                                     @RequestParam(name = "username") String username) {
        PullRequest pullRequest = pullRequestService.newPullRequest(username, datasetname);
        UpFileInfo returnValue = new UpFileInfo();
//        returnValue.setKey(pullRequest.getDataFile().getFileName());
//        returnValue.setUptoken(qiniuService.createUploadToken(pullRequest.getDataFile()));
        return returnValue;
    }

    @RequestMapping(value = "/confirmRequest", method = RequestMethod.POST)
    public boolean confirmRequest(@RequestParam(name = "confirm") Integer confirm,
                                  @RequestParam(name = "id") Long requestId) {
        return pullRequestService.setPullRequest(requestId, confirm);
    }

    @RequestMapping(value = "/requestFileConfirm", method = RequestMethod.POST)
    public void confirmRequestFileUpload(@RequestParam(name = "confirm") Integer confirm,
                                         @RequestParam(name = "fileid") Integer fileid) {

    }

    @RequestMapping(value = "/getHotProject", method = RequestMethod.POST)
    public HotProject getFreshDataset(String userName, int page) {
        Page<PullRequest> pullRequestPage = pullRequestService.findLatestPullRequest(userName, page, 10);
        List<PullRequest> pullRequests = pullRequestPage.getContent();

        HotProject hotProject = new HotProject();
        for (PullRequest pullRequest : pullRequests) {
            hotProject.addInfo(pullRequest.getDataset().getName(), pullRequest.getDataer().getName(), 1);
        }
        return hotProject;
    }
}

