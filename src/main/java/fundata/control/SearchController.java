package fundata.control;

import fundata.service.CompetitionService;
import fundata.service.CourseService;
import fundata.service.DataerService;
import fundata.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huang on 16-12-19.
 */
@RestController
public class SearchController {

    @Autowired
    @Qualifier("datasetServiceImpl")
    private DatasetService datasetService;

    @Autowired
    @Qualifier("courseServiceImpl")
    private CourseService courseService;

    @Autowired
    @Qualifier("competitionServiceImpl")
    private CompetitionService competitionService;

    @Autowired
    @Qualifier("dataerServiceImpl")
    private DataerService dataerService;

    @RequestMapping("/search/{name}")
    public Map searchAllInfo(@PathVariable String name){
        Map map = new HashMap();
        map.put("datasets", datasetService.findLikeName(name));
        map.put("competitions", competitionService.findLikeName(name));
        map.put("dataers", dataerService.findLikeName(name));
        map.put("courses", courseService.findLikeName(name));
        return map;
    }
}
