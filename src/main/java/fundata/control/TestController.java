package fundata.control;

import com.csvreader.CsvReader;
import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.repository.DataerRepository;
import fundata.repository.DatasetRepository;
import fundata.service.DatasetTitleService;
import fundata.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ocean on 16-12-1.
 */
@RestController
public class TestController {
    @Autowired
    DataerRepository dataerRepository;

    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    @Qualifier("datasetTitleServiceImpl")
    DatasetTitleService datasetTitleService;

    @RequestMapping(value = "/test")
    public boolean Test() throws IOException {
//        Map<Integer, Integer> ans = new HashMap<>();
//        qiniuService.downloadFile("http://oagx7gq4u.bkt.clouddn.com/test.csv", "test.csv", "/home/ocean/tmp");
//        try {
//            CsvReader csvReader = new CsvReader("/home/ocean/tmp/test.csv", ',', Charset.forName("UTF-8"));
//            csvReader.readHeaders();
//            while (csvReader.readRecord()) {
//                Integer index = Integer.parseInt(csvReader.get(0));
//                Integer classify = Integer.parseInt(csvReader.get(1));
//                ans.put(index, classify);
//            }
//            csvReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ans;
        Dataer dataer = dataerRepository.findById(Long.valueOf(1));
        Dataset dataset = datasetRepository.findOne(Long.valueOf(61));

        datasetTitleService.addTitleInfo("/home/ocean/tmp/title.csv", dataer, dataset);

        return true;
    }

}
