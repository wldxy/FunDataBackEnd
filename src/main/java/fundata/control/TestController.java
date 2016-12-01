package fundata.control;

import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.repository.DataerRepository;
import fundata.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ocean on 16-12-1.
 */
@RestController
public class TestController {
    @Autowired
    DataerRepository dataerRepository;

    @Autowired
    DatasetRepository datasetRepository;

    @RequestMapping(value = "/test")
    public void Test() {
        String s1 = "a3";
        String s2 = "b3";

        Dataer dataer = new Dataer(s1, s1, s1);

        Dataset dataset = new Dataset(s2);

        datasetRepository.save(dataset);

        dataer.getDatasets().add(dataset);

        dataerRepository.save(dataer);
    }

}
