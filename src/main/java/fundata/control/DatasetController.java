package fundata.control;

import fundata.model.Dataset;
import fundata.service.DatasetService;
import fundata.viewmodel.MyDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ocean on 16-12-1.
 */
@RestController
public class DatasetController {
    @Autowired
    @Qualifier("datasetServiceImpl")
    private DatasetService datasetService;

    @RequestMapping("/createDataset")
    public void createDataset(@RequestParam(value = "username") String username,
                              @RequestParam(value = "datasetname") String datasetname) {
        datasetService.addDataset(username, datasetname);
    }

    @RequestMapping("/getMyDataset")
    public MyDataset getMyDataset(@RequestParam(value = "username") String username) {
        List<Dataset> datasetList;
        datasetList = datasetService.findByUserName(username);
        MyDataset myDataset = new MyDataset(datasetList);
        return myDataset;
    }
}