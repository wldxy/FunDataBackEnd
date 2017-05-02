package fundata.control;

import fundata.configure.Constants;
import fundata.model.Dataset;
import fundata.model.PullRequest;
import fundata.service.DatasetService;
import fundata.service.PullRequestService;
import fundata.service.QiniuService;
import fundata.viewmodel.HotProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ocean on 16-12-6.
 */
@RestController
@RequestMapping(value = "/pullrequest")
public class PullRequestController {
    @Autowired
    private PullRequestService pullRequestService;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    DatasetService datasetService;

    @RequestMapping(value = "/getDatasetPullRequest", method = RequestMethod.POST)
    public Map<String, Object> getPullRequest(@RequestParam(name = "datasetId") Long datasetId,
                                          @RequestParam(value = "curPage") short curPage) {
        PagedListHolder<PullRequest> pullRequests = pullRequestService.getDatasetPullRequestsByPage(datasetId, curPage);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("pullrequests", pullRequestService.assemblePullRequestInfo(pullRequests));
        map.put("total", pullRequests.getNrOfElements());
        return map;
    }

    @RequestMapping(value = "/newPullRequest", method = RequestMethod.POST)
    public Map<String, String> newPullRequest(@RequestParam(value = "datasetId") Long datasetId,
                                  @RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                                  @RequestParam(value = "key") String key,
                                  @RequestParam(value = "description") String description) {

        System.out.println("===============");
        System.out.println("DataFile "+key+" is confirmed");
        System.out.println("===============");

        Map<String, String> map = new HashMap<>();

        if (pullRequestService.createPullRequest(userId, datasetId, description, key)) {
            map.put("code", "200");
        }
        else {
            map.put("code", "-1");
        }
        return map;
    }

    @RequestMapping(value = "/confirmRequest", method = RequestMethod.POST)
    public Map<String, String> confirmRequest(@RequestParam(name = "isConfirm") short isConfirm,
                                  @RequestParam(name = "requestId") Long requestId,
                                  @RequestParam(name = "datasetId") Long datasetId) {
        pullRequestService.setPullRequestStatus(requestId, isConfirm);
        Map<String, String> map = new HashMap<>();
        try {
            datasetService.combineDataset(datasetId);
            map.put("code", "200");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            map.put("code", "-1");
        }
        return map;
    }

    @RequestMapping(value = "/requestFileConfirm", method = RequestMethod.POST)
    public void confirmRequestFileUpload(@RequestParam(name = "confirm") Integer confirm,
                                         @RequestParam(name = "fileId") Integer fileId) {

    }

    @RequestMapping(value = "/getHotProject", method = RequestMethod.POST)
    public HotProject getFreshDataset(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                                      int curPage) {
        PagedListHolder<PullRequest> result = pullRequestService.findLatestPullRequest(userId, curPage);
        List<PullRequest> pullRequests = result.getPageList();
        HotProject hotProject = new HotProject();
        for (PullRequest pullRequest : pullRequests) {
            hotProject.addInfo(pullRequest.getDataset().getName(), pullRequest.getDataer().getName(), 1);
        }
        return hotProject;
    }
}

