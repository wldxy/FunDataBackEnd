package fundata.control;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import fundata.repository.DataFileRepository;
import fundata.repository.DataerRepository;
import fundata.repository.DatasetRepository;
import fundata.configure.FileProperties;
import fundata.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

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
    DataFileRepository dataFileRepository;

    @Autowired
    FileProperties fileProperties;

    @RequestMapping(value = "/test")
    public boolean Test() throws IOException {

        List<String> list = new ArrayList<>();
        list.add("/home/ocean/tmp/t1.csv");
        list.add("/home/ocean/tmp/t2.csv");

        String writeUrl = fileProperties.getDatasetPath() + "haha" + ".csv";
        CsvWriter csvWriter = new CsvWriter(writeUrl, ',', Charset.forName("UTF-8"));

        List<String> titles = new ArrayList<>();
        titles.add("hhh");
        titles.add("hhhhh");

        String[] tt = new String[titles.size()];
        titles.toArray(tt);
        try {
            csvWriter.writeRecord(tt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String path : list) {
            CsvReader csvReader = null;
            try {
                csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));
                csvReader.readRecord();
                while (csvReader.readRecord()) {
                    String[] temp = csvReader.getValues();
                    csvWriter.writeRecord(temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            csvReader.close();
        }
        csvWriter.close();
        qiniuService.uploadFile(writeUrl, "haha" + ".csv");

        return true;
    }

}
