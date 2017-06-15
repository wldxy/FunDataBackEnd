package fundata.control;

import fundata.configure.Constants;
import fundata.document.PullRequestStatistics;
import fundata.message.Producer;
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

    @RequestMapping(value = "/newPullRequest", method = RequestMethod.POST)
    public Map<String, String> createNewPullRequest(@RequestParam(value = "datasetId") Long datasetId,
                                                    @RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                                                    @RequestParam(value = "fileUrl") String fileUrl,
                                                    @RequestParam(value = "description") String description) {

        System.out.println("===============");
        System.out.println("DataFile "+fileUrl+" is confirmed");
        System.out.println("===============");

        Map<String, String> map = new HashMap<>();
        if (pullRequestService.createPullRequest(userId, datasetId, description, fileUrl)) {
            map.put("code", "200");
        }
        else {
            map.put("code", "-1");
        }
        return map;
    }

    @RequestMapping(value = "/getDatasetPullRequest")
    public Map<String, Object> getPullRequest(@RequestParam(name = "datasetId") Long datasetId,
                                          @RequestParam(value = "curPage") short curPage) {
        PagedListHolder<PullRequest> pullRequests = pullRequestService.getDatasetPullRequestsByPage(datasetId, curPage);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("pullrequests", pullRequestService.assemblePullRequestInfos(pullRequests));
        map.put("total", pullRequests.getNrOfElements());
        return map;
    }

    @RequestMapping(value = "/getPullRequestDetail")
    public Map<String, Object> getPullRequestDetail(@RequestParam(name = "pullRequestId") Long pullRequestId) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("detail", pullRequestService.getPullRequestDetail(pullRequestId));
        return map;
    }

    @RequestMapping(value = "/confirmRequest", method = RequestMethod.POST)
    public Map<String, String> confirmRequest(@RequestParam(name = "pullRequestId") Long pullRequestId,
                                              @RequestParam(name = "tag") String tag) {
        boolean flag = pullRequestService.mergePullRequest(pullRequestId, tag);
        Map<String, String> map = new HashMap<>();
        if (flag) {
            map.put("code", "200");
        }
        else {
            map.put("code", "-1");
        }
        return map;
    }

    @RequestMapping(value = "/rejectRequest", method = RequestMethod.POST)
    public Map<String, String> rejectRequest(@RequestParam(name = "pullRequestId") Long pullRequestId) {
        boolean flag = pullRequestService.rejectPullRequest(pullRequestId);
        Map<String, String> map = new HashMap<>();
        if (flag) {
            map.put("code", "200");
        }
        else {
            map.put("code", "-1");
        }
        return map;
    }

}

