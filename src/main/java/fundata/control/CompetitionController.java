package fundata.control;

import fundata.model.*;
import fundata.repository.DataFileRepository;
import fundata.configure.FileProperties;
import fundata.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by stanforxc on 2016/12/12.
 */
@RestController
@RequestMapping("/competition")
public class CompetitionController {
    @Autowired
    CompetitionServiceImpl competitionServiceImpl;

    @Autowired
    DataerServiceImpl dataerServiceImpl;

    @Autowired
    CommentCompServiceImpl commentCompImpl;

    @Autowired
    AccurateServiveImpl accurateServiveImpl;

    public boolean isActive(Competition competition) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdf.parse(competition.getStarttime());
        Date endDate = sdf.parse(competition.getEndtime());
        Date now = new Date();
        return startDate.compareTo(now) <= 0 && now.compareTo(endDate) <= 0;
    }

    public boolean compareTime(String time1, String time2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data1 = sdf.parse(time1);
        Date data2 = sdf.parse(time2);

        if (data1.compareTo(data2) <= 0){
            return true;
        }else{
            return false;
        }
    }

    /*添加竞赛*/
    @ResponseBody
    @RequestMapping("/add")
    public boolean addCompetition(@RequestParam(name = "username") String username,
                                  @RequestParam(name = "compName") String comName,
                                  @RequestParam(name = "start") String start,
                                  @RequestParam(name = "end") String end,
                                  @RequestParam(name = "des") String des) {
        try {
//            Dataer dataer = dataerServiceImpl.getUserDatasetsByPage(userid);
            Dataer dataer = dataerServiceImpl.findByDataerName(username);
            Competition competition = new Competition();
            competition.setName(comName);
            competitionServiceImpl.save(competition);
            Long compId = competition.getId();

            /*建立dataer_competition*/
            Set<Competition> competitions = dataer.getHostCompetition();
            competitions.add(competition);

            dataer.setHostCompetition(competitions);
            competition.setHoster(dataer);

            competition.setStarttime(start);
            competition.setEndtime(end);
            competition.setDes(des);
            competition.setRegisterNum(0);
           // competition.setActive(true);
            /*
            * TODO:dataset
            * */
            competitionServiceImpl.save(competition);

            return true;
        }catch (Exception e){
            return false;
        }
    }


    /*参与竞赛*/
    @ResponseBody
    @RequestMapping("/register")
    public String registerCompetition(@RequestParam(name = "username") String username,
                                      @RequestParam(name = "comId") Long comId) {
        try{
            Competition competition = competitionServiceImpl.findById(comId);
            Dataer host = competition.getHoster();
            Set<Dataer> contesters = competition.getDataers();

            //是否是host
            if (host.getName().equals(username)){
                return "host";
            }

            //是否注册过竞赛
            Iterator<Dataer> iterDataer = contesters.iterator();
            while (iterDataer.hasNext()){
                Dataer temp = iterDataer.next();
                if(temp.getName().equals(username)){
                    return "registered";
                }
            }

            //注册竞赛
            if(isActive(competition)){
                Dataer participant = dataerServiceImpl.findByDataerName(username);

            /*竞赛添加竞赛者*/
                competition.setRegisterNum(competition.getRegisterNum() + 1);
                contesters.add(participant);
                competition.setDataers(contesters);
                competitionServiceImpl.save(competition);

            /*参赛者注册竞赛*/
                Set<Competition> competitionSet = participant.getCompetitions();
                competitionSet.add(competition);
                participant.setCompetitions(competitionSet);
                dataerServiceImpl.save(participant);
                return "true";
            }
            return "outdated";
        }catch (Exception e){
            return "false";
        }
    }

    @RequestMapping("/unregister")
    public boolean unregister(@RequestParam(name = "comId") Long comId,
                              @RequestParam(name = "username") String username) {
//        Dataer dataer = dataerServiceImpl.getUserDatasetsByPage(userId);
        Dataer dataer = dataerServiceImpl.findByDataerName(username);
        Competition competition = competitionServiceImpl.findById(comId);

        if (dataer == null || competition == null)
            return false;
        if (!dataer.getCompetitions().contains(competition)) {
            return false;
        }

        dataer.getCompetitions().remove(competition);
        dataerServiceImpl.save(dataer);

        competition.setRegisterNum(competition.getRegisterNum()-1);
        competitionServiceImpl.save(competition);
        return true;
    }

    @RequestMapping("/delete")
    public boolean delete(@RequestParam(name = "comId") Long comId,
                          @RequestParam(name = "username") String username) {
        Competition competition = competitionServiceImpl.findById(comId);

        if (competition.getHoster().getName().equals(username)) {
            competitionServiceImpl.deleteCompetition(competition);
            return true;
        } else {
            return false;
        }
    }

    @ResponseBody
    @RequestMapping("/get_competition")
    public Map getCompetition(@RequestParam(name = "page") int page,
                              @RequestParam(name = "username") String username) throws ParseException {
        Map competitions = getComps(page);
        Map Mycompetitions = getMyCompetition(username);
        competitions.put("My_competitions",Mycompetitions);
        return competitions;
    }

    @ResponseBody
    @RequestMapping("/show_competitions")
    public Map showCompetitions(@RequestParam(name = "page") int page){
        Map competitions = getComps(page);

        return competitions;
    }

    //分页找参加竞赛
    Map getComps(int page){
        try {
            Map info = new HashMap();
            List<Map> competitionList = new ArrayList<>();
            Pageable pageable = new PageRequest(page,100,new Sort(Sort.Direction.DESC,"registerNum"));
            Page<Competition> pages = competitionServiceImpl.findAll(pageable);
            for (Competition c: pages) {
                Map temp = new HashMap();
                temp.put("com_name",c.getName());
                temp.put("com_id",c.getId());
                temp.put("com_owner_id",c.getHoster().getId());
                temp.put("com_owner_name",c.getHoster().getName());
                temp.put("com_active_flag",isActive(c));
                temp.put("com_join_num",c.getRegisterNum());
                temp.put("com_des",c.getDes());
                competitionList.add(temp);
            }
            info.put("competitions",competitionList);
            return info;
        }catch (Exception e){
            return null;
        }
    }


    //我参加/举办的竞赛
    Map getMyCompetition(String username) throws ParseException {
        Dataer dataer = dataerServiceImpl.findByDataerName(username);
        Set<Competition> competitionSet = dataer.getCompetitions();
        Set<Competition> competitionnHost = dataer.getHostCompetition();
        Map presentMap = new HashMap();
        List<Map> hostList = new ArrayList<>();
        List<Map> participateList = new ArrayList<>();

        Iterator<Competition> competitionIterator = competitionSet.iterator();
        while (competitionIterator.hasNext()){
            Competition c = competitionIterator.next();
            Map temp = new HashMap();
            temp.put("com_name",c.getName());
            temp.put("com_id",c.getId());
            temp.put("com_active_flag",isActive(c));
            participateList.add(temp);
        }

        competitionIterator = competitionnHost.iterator();
        while (competitionIterator.hasNext()) {
            Competition competition = competitionIterator.next();
            Map temp = new HashMap();
            temp.put("com_name", competition.getName());
            temp.put("com_id", competition.getId());
            temp.put("com_active_flag",isActive(competition));
            hostList.add(temp);
        }

        presentMap.put("my_participate_com",participateList);
        presentMap.put("my_com",hostList);
        return presentMap;
    }


    //得到竞赛详情
    @ResponseBody
    @RequestMapping("/detail")
    public Map getCompetitionDetails(@RequestParam(name = "compId")Long id) throws ParseException {
        Map detail = new HashMap();
        Competition competition = competitionServiceImpl.findById(id);
        detail.put("com_id",competition.getId());
        detail.put("com_name",competition.getName());
        detail.put("com_des",competition.getDes());
        detail.put("com_owner_id",competition.getHoster().getId());
        detail.put("com_owner_name",competition.getHoster().getName());
        detail.put("com_start_time",competition.getStarttime());
        detail.put("com_end_time",competition.getEndtime());
        detail.put("com_active_flag", isActive(competition));

        Set<Commentcomp> commentcomps = competition.getCommentComps();

        List<Map> comment = new ArrayList<>();

        for (Commentcomp commentcomp : commentcomps) {
            Map temp = new HashMap();
            temp.put("username", commentcomp.getDataer().getName());
            temp.put("head-url", commentcomp.getDataer().getHead_href());
            temp.put("content", commentcomp.getContent());
            temp.put("time", commentcomp.getTime());
            comment.add(temp);
        }
        detail.put("comments", comment);

        Set<DataFile> dataFiles = competition.getDataFile();

        List<Map> download = new ArrayList<>();

        for (DataFile dataFile : dataFiles) {
            Map temp = new HashMap();
            temp.put("url", qiniuService.createDownloadUrl(dataFile));
            temp.put("time", dataFile.getCreateTime().toString());
            //temp.put("name", dataFile.getOldname());
            temp.put("size", qiniuService.getFileSize(dataFile));
            download.add(temp);
        }
        detail.put("download", download);

        if (competition.getAnsFile() != null){
            detail.put("ansfile", qiniuService.createDownloadUrl(competition.getAnsFile()));
        }else{
            detail.put("ansfile", null);
        }

        return detail;
    }

    @Autowired
    DataFileRepository dataFileRepository;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    FileProperties fileProperties;

    @RequestMapping("/confirmAnsFile")
    public boolean confirmAnsFile(@RequestParam(name = "key") String key,
                                  @RequestParam(name = "comId") Long comId) {

        System.out.println("===============");
        System.out.println("Competition Ans "+key+" is confirmed");
        System.out.println("===============");

        Long fileid = Long.parseLong(key.substring(0, key.lastIndexOf('.')));
        DataFile dataFile = dataFileRepository.findById(fileid);

        if (dataFile == null) {
            return false;
        }

        Competition competition = competitionServiceImpl.findById(comId);
        if (competition == null) {
            return false;
        }

        competition.setAnsFile(dataFile);
        competitionServiceImpl.save(competition);

        qiniuService.downloadFile(qiniuService.createDownloadUrl(dataFile), comId.toString() + ".csv", fileProperties.getAnsFilePath());

        return true;
    }

    @RequestMapping("/confirmDataFile")
    public boolean confirmDataFile(@RequestParam(name = "key") String key,
                                   @RequestParam(name = "comId") String comId) {

        System.out.println("===============");
        System.out.println("Competition DataFile "+key+" is confirmed");
        System.out.println("===============");

        Long fileid = Long.parseLong(key.substring(0, key.lastIndexOf('.')));
        DataFile dataFile = dataFileRepository.findById(fileid);

        if (dataFile == null) {
            return false;
        }

        Competition competition = competitionServiceImpl.findById(Long.valueOf(comId));
        if (competition == null) {
            return false;
        }

        competition.getDataFile().add(dataFile);
        competitionServiceImpl.save(competition);

        return true;
    }

    @RequestMapping("/confirmUserAns")
    public boolean confirmUserAns(@RequestParam(name = "username") String username,
                                  @RequestParam(name = "key") String key,
                                  @RequestParam(name = "comId") Long comId) {

        System.out.println("===============");
        System.out.println("UserAns "+key+" is confirmed");
        System.out.println("===============");

        Long fileid = Long.parseLong(key.substring(0, key.lastIndexOf('.')));
        DataFile dataFile = dataFileRepository.findById(fileid);

        if (dataFile == null) {
            return false;
        }

        Competition competition = competitionServiceImpl.findById(comId);
//        qiniuService.downloadFile(qiniuService.createDownloadUrl(dataFile), dataFile.getName(), fileProperties.getUserAnsPath());
//
//        String ansUrl = fileProperties.getAnsFilePath() + comId.toString() + ".csv";
//        String userAnsUrl = fileProperties.getUserAnsPath() + dataFile.getName();
//        Evaluator evaluator = new ClassifyEvaluator(ansUrl);
//        double accurate = evaluator.evaluate(userAnsUrl);

        try {
            Dataer dataer = dataerServiceImpl.findByDataerName(username);
            Set<Competition> competitions = dataer.getCompetitions();
            Set<Accurate> accurates = dataer.getAccurates();
            Iterator<Competition> competitionIterator = competitions.iterator();
            while (competitionIterator.hasNext()) {
                Competition temp = competitionIterator.next();
                if(isActive(temp) && temp.getId().equals(comId)){
                    Accurate a = new Accurate();
                    //.setValue(accurate);
                    a.setDataer(dataer);
                    a.setUploadDate(getCurrentTime());
                    accurateServiveImpl.save(a);
                    accurates.add(a);
                }
            }
            dataer.setAccurates(accurates);
            dataerServiceImpl.save(dataer);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //我的中心
    @ResponseBody
    @RequestMapping("/compCenter")
    public Map myCompetitionCenter(@RequestParam(name = "username") String username) throws ParseException {
//        Dataer dataer = dataerServiceImpl.getUserDatasetsByPage(userid);
        Dataer dataer = dataerServiceImpl.findByDataerName(username);
        Map total = new HashMap();
        List<Map> competitionList = new ArrayList<>();
        Set<Competition> competitions = dataer.getCompetitions();
        Iterator<Competition> competitionIterator = competitions.iterator();
        while (competitionIterator.hasNext()){
            Competition temp = competitionIterator.next();
            Map maptemp = new HashMap();
            maptemp.put("com_name",temp.getName());
            maptemp.put("com_id",temp.getId());
            maptemp.put("com_active_flag",isActive(temp));
            maptemp.put("com_des",temp.getDes());
            competitionList.add(maptemp);
        }
        total.put("competitions",competitionList);
        return total;
    }

    //修改比赛
    @ResponseBody
    @RequestMapping("/modifyCompetition")
    public boolean modify(@RequestParam(name = "compId")Long compId,
                          @RequestParam(name = "com_name")String comp_name,
                          @RequestParam(name = "com_des")String com_des,
                          @RequestParam(name = "com_start_time")String start_time,
                          @RequestParam(name = "com_end_time")String endtime/*TODO:com_download,answer*/){
        try{
            Competition competition = competitionServiceImpl.findById(compId);
            competition.setName(comp_name);
            competition.setDes(com_des);
            competition.setStarttime(start_time);
            competition.setEndtime(endtime);
            competitionServiceImpl.save(competition);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editname", method = RequestMethod.POST)
    public boolean editName(@RequestParam String name, @RequestParam Long id){
        try{
            Competition competition = competitionServiceImpl.findById(id);
            competition.setName(name);
            competitionServiceImpl.save(competition);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editdes", method = RequestMethod.POST)
    public boolean editDes(@RequestParam String des, @RequestParam Long id){
        try{
            Competition competition = competitionServiceImpl.findById(id);
            competition.setDes(des);
            competitionServiceImpl.save(competition);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editstarttime", method = RequestMethod.POST)
    public boolean editStartTime(@RequestParam String time, @RequestParam Long id){
        try{
            Competition competition = competitionServiceImpl.findById(id);
            competition.setStarttime(time);
            competitionServiceImpl.save(competition);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editendtime", method = RequestMethod.POST)
    public boolean editEndTime(@RequestParam String time, @RequestParam Long id){
        try{
            Competition competition = competitionServiceImpl.findById(id);
            competition.setEndtime(time);
            competitionServiceImpl.save(competition);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //加入评论
    @ResponseBody
    @RequestMapping("/comment/add")
    public boolean addComment(@RequestParam(name = "username") String username,
                              @RequestParam(name = "compId") Long compId,
                              @RequestParam(name = "content") String content){
        try {
            Commentcomp commentComp = new Commentcomp();
//            Dataer dataer = dataerServiceImpl.getUserDatasetsByPage(userid);
            Dataer dataer = dataerServiceImpl.findByDataerName(username);
            Competition competition = competitionServiceImpl.findById(compId);
            if(isActive(competition)){
                if(dataer == null){
                    return false;
                }
                commentComp.setDataer(dataer);

                if (competition == null){
                    return false;
                }
                commentComp.setCompetition(competition);
                commentComp.setContent(content);
                commentComp.setTime(getCurrentTime());

                Set<Commentcomp> commentcompSet = dataer.getCommentcompSet();
                commentcompSet.add(commentComp);
                dataer.setCommentcompSet(commentcompSet);

                dataerServiceImpl.save(dataer);
                commentCompImpl.save(commentComp);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    private String getCurrentTime(){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }


    //竞赛评论 参与的
    @ResponseBody
    @RequestMapping("/comment/host")
    public Map getCompComment(@RequestParam(name = "username") String username){
//        Dataer dataer = dataerServiceImpl.getUserDatasetsByPage(userid);
        Dataer dataer = dataerServiceImpl.findByDataerName(username);
        Set<Competition> competitionSet = dataer.getHostCompetition();
        Iterator<Competition> competitionIterator = competitionSet.iterator();
        Map totalComment = new HashMap();
        List<Map> totalMap = new ArrayList<>();
        while (competitionIterator.hasNext()){
            Competition temp = competitionIterator.next();
            Set<Commentcomp> commentComps = temp.getCommentComps();
            Iterator<Commentcomp> commentCompIterator = commentComps.iterator();
            List<Map> commentList = new ArrayList<>();
            while (commentCompIterator.hasNext()){
                Commentcomp tempComment = commentCompIterator.next();
                Map commentMap = new HashMap();
                commentMap.put("user_id",tempComment.getDataer().getId());
                commentMap.put("user_id",tempComment.getDataer().getId());
                commentMap.put("user_name",tempComment.getDataer().getName());
                commentMap.put("head_href",tempComment.getDataer().getHead_href());
                Map userMap = new HashMap();
                userMap.put("user",commentMap);
                userMap.put("content",tempComment.getContent());
                userMap.put("time",tempComment.getTime());
                commentList.add(userMap);
            }
            Map competitionMap = new HashMap();
            competitionMap.put("com_id",temp.getId());
            competitionMap.put("com_name",temp.getName());
            competitionMap.put("comments",commentList);
            totalMap.add(competitionMap);
        }
        totalComment.put("competitions",totalMap);
        return totalComment;
    }

//    //person->competition->accurate
//    @ResponseBody
//    @RequestMapping("/add/person/accurate")
//    public boolean add_person_accurate(@RequestParam(name = "userid") Long userid,
//                                       @RequestParam(name = "compId") Long comId,
//                                       @RequestParam(name = "accurate") Double accurate) {
//
//
//        try {
//            Dataer dataer = dataerServiceImpl.getUserDatasetsByPage(userid);
//            Set<Competition> competitions = dataer.getCompetitions();
//            Set<Accurate> accurates = dataer.getAccurates();
//            Iterator<Competition> competitionIterator = competitions.iterator();
//            while (competitionIterator.hasNext()) {
//                Competipption temp = competitionIterator.next();
//                if(isActive(temp) && temp.getId().equals(comId)){
//                    Accurate a = new Accurate();
//                    a.setValue(accurate);
//                    a.setDataer(dataer);
//                    a.setUploadDate(getCurrentTime());
//                    accurateServiveImpl.save(a);
//                    accurates.add(a);
//                }
//            }
//            dataer.setAccurates(accurates);
//            dataerServiceImpl.save(dataer);
//            return true;
//        }catch (Exception e){
//            return false;
//        }
//    }

    @RequestMapping("/applyIf")
    public boolean isRegister(@RequestParam(name = "username") String username,
                              @RequestParam(name = "comId") Long Id) {
        Dataer dataer = dataerServiceImpl.findByDataerName(username);
        Set<Competition> competitions = dataer.getCompetitions();

        Competition competition = competitionServiceImpl.findById(Id);
        if (competitions.contains(competition)) {
            return true;
        } else {
            return false;
        }
    }

    //getUserAllAccurate() (Sorted)
//    @ResponseBody
//    @RequestMapping("/person/accurate")
//    public Map getDataerAllAccurate(@RequestParam(name = "username")String username,
//                                    @RequestParam(name = "compId")Long compId) throws ParseException {
//        //Pageable pageable = new PageRequest(page,1,new Sort(Sort.Direction.DESC,"value"));
//        //Page<Accurate> accuratePage = accurateServiveImpl.findAll(pageable);
//        Dataer dataer = dataerServiceImpl.findByDataerName(username);
//        Set<Accurate> accuratePage = dataer.getAccurates();
//        ArrayList<Accurate> accurates = new ArrayList<>();
//        for (Accurate a : accuratePage){
//            accurates.add(a);
//        }
//
//        for (int i = 0; i < accurates.size() - 1; i++){
//            for (int k = i + 1; k < accurates.size(); k++){
//                if (compareTime(accurates.get(i).getUploadDate(), accurates.get(k).getUploadDate())){
//                    Accurate temp = accurates.get(i);
//                    accurates.set(i, accurates.get(k));
//                    accurates.set(k, temp);
//                }
//            }
//        }
//        List<Map> mapList = new ArrayList<>();
//        Competition competition = competitionServiceImpl.getUserDatasetsByPage(compId);
//        if(isActive(competition) && competition.getDataers().contains(dataer)){
//            for (Accurate a: dataer.getAccurates()) {
////                Set<Dataer> dataers = competition.getDataers();
////                for (Dataer d : dataers){
////                    d.getAccurates().contains(a);
//                    Map temp = new HashMap();
//                    temp.put("user_id",a.getId().getId());
//                    temp.put("value",a.getValue());
//                    temp.put("uploadtime",a.getUploadDate());
//                    mapList.add(temp);
//                }
//        }
//        Map total = new HashMap();
//        total.put("user_accurate",mapList);
//        return total;
//    }

    @ResponseBody
    @RequestMapping("/person/accurate")
    public Map getDataerAllAccurate(@RequestParam(name = "username")String username,
                                    @RequestParam(name = "compId")Long compId) throws ParseException {
        //Pageable pageable = new PageRequest(page,1,new Sort(Sort.Direction.DESC,"value"));
        //Page<Accurate> accuratePage = accurateServiveImpl.findAll(pageable);
        Dataer dataer = dataerServiceImpl.findByDataerName(username);
        Set<Accurate> accuratePage = dataer.getAccurates();
        List<Map> mapList = new ArrayList<>();
        Competition competition = competitionServiceImpl.findById(compId);
        if(isActive(competition) && competition.getDataers().contains(dataer)){
            for (Accurate a: accuratePage) {

                Map temp = new HashMap();
                temp.put("user_id",a.getDataer().getId());
                temp.put("value",a.getValue());
                temp.put("uploadtime",a.getUploadDate());
                mapList.add(temp);
            }
        }
        Map total = new HashMap();
        total.put("user_accurate",mapList);
        return total;
    }

    //得到某一竞赛下的排名
    @ResponseBody
    @RequestMapping("/accurate/rank")
    public Map getRank(@RequestParam(name = "compId")Long compId){
        Competition competition = competitionServiceImpl.findById(compId);
        Set<Dataer> dataers = competition.getDataers();
        List<Accurate> accurateSort = new ArrayList<>();
        for (Dataer dataer : dataers){
            accurateSort.addAll(dataer.getAccurates());
        }
        Collections.sort(accurateSort,new Comparator<Accurate>(){
            public int compare(Accurate arg0, Accurate arg1) {
                return arg1.getValue().compareTo(arg0.getValue());
            }
        });

        List<Accurate> list = new ArrayList<>();
        Map rank = new HashMap();
//        if(page*4 <= accurateSort.size() - 1){
//            list = accurateSort.subList(page*4,(page+1)*4-1);
//        }else {
//            rank.put("rank",list);
//            return rank;
//        }
        list = accurateSort;

        List<Map> totalMap = new ArrayList<>();
        for (Accurate a : list){
            Map tempMap = new HashMap();
            tempMap.put("user_id",a.getDataer().getId());
            tempMap.put("user_name",a.getDataer().getName());
            tempMap.put("head_url",a.getDataer().getHead_href());
            tempMap.put("accurate_id",a.getId());
            tempMap.put("uploadDate",a.getUploadDate());
            tempMap.put("value",a.getValue());
            totalMap.add(tempMap);
        }
        rank.put("rank",totalMap);
        return rank;
    }


}
