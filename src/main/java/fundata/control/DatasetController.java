package fundata.control;

import fundata.model.*;
import fundata.repository.DataFileRepository;
import fundata.repository.DatasetRepository;
import fundata.repository.FileProperties;
import fundata.service.*;
import fundata.viewmodel.DSCommentView;
import fundata.viewmodel.DatasetContent;
import fundata.viewmodel.MyDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
    private FileProperties fileProperties;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private DSCommentService dsCommentService;

    @Autowired
    DataFileRepository dataFileRepository;

    @Autowired
    DatasetTitleService datasetTitleService;

    @Autowired
    DatasetRepository datasetRepository;

    @RequestMapping("/getDataset")
    public Map getDataset() {
        List<Dataset> datasets = datasetRepository.findAll();

        Map map = new HashMap();

        List<Map> dataset = new ArrayList<>();
        for (Dataset d : datasets) {
            Map temp = new HashMap();
            temp.put("url", "http://4493bz.1985t.com/uploads/allimg/150127/4-15012G52133.jpg");
            temp.put("name", d.getName());
            temp.put("filenum", d.getPullRequests().size());

            Set<PullRequest> pullRequests = d.getPullRequests();

            if (pullRequests.size() != 0) {
                PullRequest pullRequest = Collections.max(pullRequests, new Comparator<PullRequest>() {
                    @Override
                    public int compare(PullRequest o1, PullRequest o2) {
                        return o1.getUpdatetime().compareTo(o2.getUpdatetime());
                    }
                });
                temp.put("time", pullRequest.getUpdatetime());
                temp.put("username", pullRequest.getDataer().getName());
            } else {
                temp.put("time", "");
                temp.put("username", "");
            }
            dataset.add(temp);
        }

        map.put("datasets", dataset);
        return map;
    }

    @RequestMapping("/createDataset")
    public boolean createDataset(@RequestParam(value = "username") String username,
                                 @RequestParam(value = "datasetname") String datasetname,
                                 @RequestParam(value = "desc") String desc) {
        Dataset dataset = datasetService.findByDatasetName(datasetname);
        if (dataset != null)
            return false;

        datasetService.addDataset(username, datasetname, desc);
        return true;
    }

    @RequestMapping("/confirmTitle")
    public boolean confirmDatasetTitle(@RequestParam(value = "datasetname") String datasetName,
                                       @RequestParam(value = "username") String username,
                                       @RequestParam(value = "key") String key,
                                       @RequestParam(value = "description") String desc) {

        System.out.println("===============");
        System.out.println("DataFileTitle "+key+" is confirmed");
        System.out.println("===============");

        Long id = Long.parseLong(key.substring(0, key.lastIndexOf('.')));
        DataFile dataFile = dataFileRepository.findById(id);

        Dataer dataer = dataerService.findByDataerName(username);
        Dataset dataset = datasetService.findByDatasetName(datasetName);

        if (dataer == null || dataset == null || dataFile == null)
            return false;

        dataset.setTitleFile(dataFile);
        datasetService.save(dataset);

        qiniuService.downloadFile(dataFile, fileProperties.getTitlePath());
        String url = fileProperties.getTitlePath() + dataFile.getName();

        datasetTitleService.addTitleInfo(url, dataer, dataset);

        return true;
    }

    @Autowired
    private PullRequestService pullRequestService;

    @RequestMapping("/confirmFile")
    public boolean confirmDatasetFile(@RequestParam(value = "datasetname") String datasetName,
                                      @RequestParam(value = "username") String username,
                                      @RequestParam(value = "key") String key,
                                      @RequestParam(value = "description") String desc) {

        System.out.println("===============");
        System.out.println("DataFile "+key+" is confirmed");
        System.out.println("===============");

        Long id = Long.parseLong(key.substring(0, key.lastIndexOf('.')));
        DataFile dataFile = dataFileRepository.findById(id);

        Dataer dataer = dataerService.findByDataerName(username);
        Dataset dataset = datasetService.findByDatasetName(datasetName);

        if (dataer == null || dataset == null || dataFile == null)
            return false;

        PullRequest pullRequest = pullRequestService.newPullRequest(username, datasetName);
        pullRequest.setDataFile(dataFile);
        pullRequest.setDescription(desc);
        pullRequestService.save(pullRequest);

        return true;
    }

//    @ResponseBody
//    @RequestMapping(value = "/getHotProject", method = RequestMethod.POST)
//    public Map getHotProject(){
//        try{
//            Map map = new HashMap<>();
//
//            List<Dataset> datasetList = new ArrayList<>();
//
////            Dataer dataer =
//
//            return map;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    @RequestMapping("/getMyContribute")
    public Map getMyContribute(@RequestParam("username") String username) {
        Map map = new HashMap();

        List<Map> dataset = new ArrayList<>();

        Dataer dataer = dataerService.findByDataerName(username);
        Set<PullRequest> pullRequests = dataer.getPullRequests();
        for (PullRequest pullRequest : pullRequests) {
            Map temp = new HashMap();
            temp.put("datasetname", pullRequest.getDataset().getName());
            temp.put("updatetime", pullRequest.getUpdatetime().toString());
            temp.put("type", pullRequest.getStatus());

            dataset.add(temp);
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

    @RequestMapping("/getDemoContent")
    public DatasetContent getDatasetTitle(@RequestParam(value = "datasetname") String datesetName,
//                                          @RequestParam(value = "page") Integer page,
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
        datasetContent.setContribute(0);
        datasetContent.setDescription(dataset.getDescription());

        Set<DatasetTitle> datasetTitles = dataset.getDatasetTitles();
        for (DatasetTitle datasetTitle : datasetTitles) {
            datasetContent.addContent(datasetTitle.getTitleName(),
                    datasetTitle.getTitleType(), datasetTitle.getMeaning());
        }
        return datasetContent;
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