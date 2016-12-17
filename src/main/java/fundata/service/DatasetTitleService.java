package fundata.service;

import fundata.model.Course;
import fundata.model.Dataer;
import fundata.model.Dataset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-17.
 */

public interface DatasetTitleService {
    boolean addTitleInfo(String url, Dataer dataer, Dataset dataset);
}
