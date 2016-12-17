package fundata.control;

import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.model.DatasetTitle;
import fundata.model.PullRequest;
import fundata.service.DataerService;
import fundata.service.DatasetService;
import fundata.service.QiniuService;
import fundata.viewmodel.DatasetContent;
import fundata.viewmodel.MyDataset;
import fundata.viewmodel.UpFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by ocean on 16-12-1.
 */
@RestController
@RequestMapping("/dataset")
public class DatasetController {
    @Autowired
    @Qualifier("datasetServiceImpl")
    private DatasetService datasetService;

    @Autowired
    private DataerService dataerService;

    @Autowired
    private QiniuService qiniuService;

    @RequestMapping("/createDataset")
    public UpFileInfo createDataset(@RequestParam(value = "username") String username,
                                    @RequestParam(value = "datasetname") String datasetname,
                                    @RequestParam(value = "desc") String desc) {
        datasetService.addDataset(username, datasetname, desc);
        String fileName = "desc" + datasetname + ".csv";
        UpFileInfo upFileInfo = new UpFileInfo();
        upFileInfo.setUptoken(qiniuService.createUploadToken(fileName));
        upFileInfo.setKey(fileName);
        return upFileInfo;
    }

    @RequestMapping("/confirmDatasetInit")
    public boolean confirmDatasetInit(@RequestParam(value = "datasetname") String datasetName) {
        

        return true;
    }

    @RequestMapping("/getMyContribute")
    public Map getMyContribute(@RequestParam("username") String username) {
        Map map = new HashMap();

        List<Map> dataset = new ArrayList<>();

        Dataer dataer = dataerService.findByDataerName(username);
        Set<PullRequest> pullRequests = dataer.getPullRequests();
        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getStatus() == 1) {
                Map temp = new HashMap();
                temp.put("datasetname", pullRequest.getDataset().getName());
                temp.put("updatetime", pullRequest.getUpdatetime().toString());

                dataset.add(temp);
            }
        }

        map.put("dataset", dataset);
        return map;
    }

    @RequestMapping("/getMyDataset")
    public MyDataset getMyDataset(@RequestParam(value = "username") String username) {
        Set<Dataset> datasetList;
        datasetList = datasetService.findByUserName(username);
        MyDataset myDataset = new MyDataset(datasetList);
        return myDataset;
    }

    @RequestMapping("/datasetFileConfirm")
    public void confirmDatasetTitleDesc(@RequestParam(value = "status") Integer status) {

    }

    @RequestMapping("/getDemoContent")
    public DatasetContent getDatasetTitle(@RequestParam(value = "datasetname") String datesetName,
                                          @RequestParam(value = "page") Integer page,
                                          @RequestParam(value = "username") String username) {
        DatasetContent datasetContent = new DatasetContent();

        Dataset dataset = datasetService.findByDatasetName(datesetName);
        Set<Dataer> dataers = dataset.getDataers();
        Dataer dataer = dataerService.findByDataerName(username);
        if (dataers.contains(dataer)) {
            datasetContent.setAdmin(1);
        } else {
            datasetContent.setAdmin(0);
        }
        datasetContent.setDescription(dataset.getDescription());

        Set<DatasetTitle> datasetTitles = dataset.getDatasetTitles();
        for (DatasetTitle datasetTitle : datasetTitles) {

        }
        return null;
    }
}