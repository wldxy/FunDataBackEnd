package fundata.control;

import fundata.annotation.Authorization;
import fundata.configure.Constants;
import fundata.model.*;
import fundata.repository.DataFileRepository;
import fundata.repository.DatasetRepository;
import fundata.repository.FileProperties;
import fundata.repository.QiniuProperties;
import fundata.service.*;
import fundata.viewmodel.DSCommentView;
import fundata.viewmodel.DatasetContent;
import fundata.viewmodel.DatasetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    @Autowired
    private PullRequestService pullRequestService;


    @RequestMapping(value = "/uploadCover", method = RequestMethod.POST)
    public Map<String, String> getUploadCSV(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {
        String name = multiReq.getFile("file").getOriginalFilename();
        FileOutputStream fos=new FileOutputStream(new File("files/".concat(name)));
        FileInputStream fs=(FileInputStream) multiReq.getFile("file").getInputStream();
        byte[] buffer=new byte[2048];
        int len=0;
        while((len=fs.read(buffer))!=-1){
            fos.write(buffer, 0, len);
        }
        fos.close();
        fs.close();
        Map<String, String> map = new HashMap<>();
        map.put("url", name);
        map.put("code", "200");
        return map;
    }

    @RequestMapping("/createDataset")
    @Authorization
    public Map<String, String> createDataset(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                                 @RequestParam(value = "ds_name") String datasetName,
                                 @RequestParam(value = "ds_desc") String dsDesc,
                                 @RequestParam(value = "cover_url") String coverUrl,
                                 @RequestParam(value = "format_desc") String formatDesc,
                                 @RequestParam(value = "columns") String fieldsString) {
        Map<String, String> map = new HashMap<>();
        Dataset dataset = datasetService.findByDatasetName(datasetName);
        if (dataset != null) {
            map.put("code", "-1");
            return map;
        }

        datasetService.createNewDataset(userId, datasetName, dsDesc, formatDesc, fieldsString, coverUrl);
        map.put("code", "200");
        return map;
    }


    @RequestMapping("/getMyDataset")
    @Authorization
    public Map<String, Object> getMyDataset(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                                  @RequestParam(value = "curPage") short curPage) {

        PagedListHolder<DataerDataset> result = datasetService.getUserDatasetsByPage(userId, curPage);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("datasets", Arrays.asList(datasetService.assembleDatasetInfo(result)));
        map.put("total", result.getNrOfElements());
        return map;
    }

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
                        return o1.getUpdateTime().compareTo(o2.getUpdateTime());
                    }
                });
                temp.put("time", pullRequest.getUpdateTime());
                temp.put("username", pullRequest.getDataer().getName());
                temp.put("type", pullRequest.getStatus());
            } else {
                temp.put("time", "");
                temp.put("username", "");
                temp.put("type", "");
            }
            dataset.add(temp);
        }

        map.put("datasets", dataset);
        return map;
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

    @RequestMapping("/getMyContribute")
    public Map getMyContribute(@RequestParam("username") String username) {
        Map map = new HashMap();
        List<Map> dataset = new ArrayList<>();

        Dataer dataer = dataerService.findByDataerName(username);
        Set<PullRequest> pullRequests = dataer.getPullRequests();
        for (PullRequest pullRequest : pullRequests) {
            Map temp = new HashMap();
            temp.put("datasetname", pullRequest.getDataset().getName());
            temp.put("updatetime", pullRequest.getUpdateTime().toString());
            temp.put("type", pullRequest.getStatus());

            dataset.add(temp);
        }

        map.put("dataset", dataset);
        return map;
    }



    @Autowired
    QiniuProperties qiniuProperties;

    @RequestMapping("/getDemoContent")
    public DatasetContent getDatasetTitle(@RequestParam(value = "datasetname") String datesetName,
//                                        @RequestParam(value = "page") Integer page,
                                          @RequestParam(value = "username") String username) {
        DatasetContent datasetContent = new DatasetContent();

        Dataset dataset = datasetService.findByDatasetName(datesetName);
        Set<DataerDataset> dataers = dataset.getDataers();
        Dataer dataer = dataerService.findByDataerName(username);
        if (dataers.contains(dataer)) {
            datasetContent.setAdmin(1);
        } else {
            datasetContent.setAdmin(0);
        }

        String url = "";
        if (dataset.getAllFile() != null) {
            url = "http://" + qiniuProperties.getDomain() + "/" + dataset.getAllFile().getFileid() + ".csv";
        }
        datasetContent.setUrl(url);

        int count = 0;
        Set<PullRequest> pullRequests = dataset.getPullRequests();
        if (pullRequests != null) {
            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getStatus() == 1) {
                    count++;
                }
            }
        }

        datasetContent.setContribute(count);
        datasetContent.setDescription(dataset.getDsDescription());

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