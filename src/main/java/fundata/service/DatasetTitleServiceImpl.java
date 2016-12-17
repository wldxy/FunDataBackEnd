package fundata.service;

import com.csvreader.CsvReader;
import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.model.DatasetTitle;
import fundata.repository.DatasetTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

/**
 * Created by ocean on 16-12-17.
 */
@Service
public class DatasetTitleServiceImpl implements DatasetTitleService {

    @Autowired
    DatasetTitleRepository datasetTitleRepository;

    @Override
    public boolean addTitleInfo(String url, Dataer dataer, Dataset dataset) {
        try {
            CsvReader csvReader = new CsvReader(url, ',', Charset.forName("UTF-8"));
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                String name = csvReader.get(0);
                String type = csvReader.get(1);
                String meaning = csvReader.get(2);

                DatasetTitle datasetTitle = new DatasetTitle();
                datasetTitle.setDataset(dataset);
                datasetTitle.setTitleName(name);
                datasetTitle.setTitleType(type);
                datasetTitle.setMeaning(meaning);

                datasetTitleRepository.save(datasetTitle);
            }
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
