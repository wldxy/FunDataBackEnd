package fundata.control;

import fundata.annotation.Authorization;
import fundata.configure.Constants;
import fundata.message.Producer;
import fundata.model.*;
import fundata.configure.FileProperties;
import fundata.service.*;
import fundata.viewmodel.DSCommentView;
import fundata.viewmodel.DatasetContent;
import fundata.viewmodel.DatasetDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by ocean on 16-12-1.
 */

@RestController
@RequestMapping("/dataset")
public class DatasetController {
    @Autowired
    private DatasetService datasetService;

    @Autowired
    private DSCommentService dsCommentService;

    @Autowired
    private PullRequestService pullRequestService;

    @RequestMapping("/createDataset")
    @Authorization
    public Map<String, String> createDataset(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                                             @RequestParam(value = "ds_name") String datasetName,
                                             @RequestParam(value = "ds_desc") String dsDesc,
                                             @RequestParam(value = "cover_url") String coverUrl,
                                             @RequestParam(value = "format_desc") String formatDesc,
                                             @RequestParam(value = "tables") String tablesString) {
        Map<String, String> map = new HashMap<>();
        Dataset dataset = datasetService.findByDatasetName(datasetName);
        if (dataset != null) {
            map.put("code", "-1");
            return map;
        }

        datasetService.createNewDataset(userId, datasetName, dsDesc, formatDesc, tablesString, coverUrl);
        map.put("code", "200");
        return map;
    }


    @RequestMapping("/getMyDatasets")
    @Authorization
    public Map<String, Object> getMyDatasets(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                                            @RequestParam(value = "curPage") short curPage) {
        PagedListHolder<DataerDataset> result = datasetService.getUserDatasetsByPage(userId, curPage);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("datasets", datasetService.assembleDatasetInfos(result));
        map.put("total", result.getNrOfElements());
        return map;
    }

    @RequestMapping("/getDatasetDetail")
    @Authorization
    public Map<String, Object> getDatasetDetail(@RequestParam(value = "datasetId") Long datasetId) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("detail", datasetService.getDatasetDetail(datasetId));
        return map;
    }

    @RequestMapping("/toTerminal")
    public Map enterTerminal(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                             @RequestParam(value = "dataset_id") Long dataset_id) {
        boolean isSuccess = datasetService.enterJupyter(userId, dataset_id);
        Map<String, Object> map = new HashMap<>();
        if (isSuccess) {
            map.put("code", "200");
        }
        else {
            map.put("code", "-1");
        }
        return map;
    }

    @RequestMapping("/getAllDatasets")
    public Map getAllDatasets(@RequestParam(value = "curPage") short curPage) {
        PagedListHolder<DataerDataset> result = datasetService.getAllDatasetsByPage(curPage);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("datasets", datasetService.assembleDatasetInfos(result));
        map.put("total", result.getNrOfElements());
        return map;
    }

    @RequestMapping("/getMyContribute")
    public Map getMyContribute(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                               @RequestParam(value = "curPage") short curPage) {
        Map<String, Object> map = new HashMap<>();
        PagedListHolder<PullRequest> result = pullRequestService.getUserPullRequestsByPage(userId, curPage);
        map.put("code", "200");
        map.put("pullrequests", pullRequestService.assemblePullRequestInfos(result));
        map.put("total", result.getNrOfElements());
        return map;
    }

    @RequestMapping("/addExpressions")
    public Map<String, Object> addTableRestricts(@RequestParam(value = "datasetId") Long datasetId,
                                                 @RequestParam(value = "expressions") String expressions) {
        datasetService.addTableExpressions(datasetId, expressions);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        return map;
    }


    @RequestMapping("/getDemoContent")
    public DatasetContent getDatasetTitle(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                                          @RequestParam(value = "datasetId") Long datasetId,
                                          @RequestParam(value = "curPage") short curPage) {

        return datasetService.getDatasetContent(datasetId, userId);
    }

    @RequestMapping("/getComment")
    public DSCommentView getComment(@RequestParam(name = "datasetname") String datasetname) {

        return dsCommentService.getComment(datasetname);
    }

    @RequestMapping("/comment")
    public DSCommentView comment(@RequestParam(name = "username") String username,
                                 @RequestParam(name = "datasetname") String datasetname,
                                 @RequestParam(name = "content") String content) {
        DSComment dsComment = dsCommentService.addComment(username, datasetname, content);

        DSCommentView dsCommentView = new DSCommentView();
        dsCommentView.addDSComment(dsComment);
        return dsCommentView;
    }
}