package fundata.service;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.gson.Gson;
import fundata.configure.Constants;
import fundata.configure.FileProperties;
import fundata.configure.QiniuProperties;
import fundata.document.Field;
import fundata.document.MetaData;
import fundata.model.*;
import fundata.repository.*;
import fundata.viewmodel.DatasetContent;
import fundata.viewmodel.DatasetDetail;
import fundata.viewmodel.DatasetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by ocean on 16-12-1.
 */
@Service
public class DatasetServiceImpl implements DatasetService {
    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    MetaDataRepository metaDataRepository;

    @Autowired
    DataerRepository dataerRepository;

    @Autowired
    DataerDatasetRepository dataerDatasetRepository;

    @Autowired
    QiniuProperties qiniuProperties;

    @Autowired
    DatasetTitleRepository datasetTitleRepository;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    FileProperties fileProperties;

    @Autowired
    DataFileRepository dataFileRepository;

    private String getFileUrl(Dataset dataset) {
        String url = "";
        if (dataset.getAllFile() != null) {
            url = "http://".concat(qiniuProperties.getDomain_private()).concat("/").concat(dataset.getAllFile().getUrl());
        }
        return url;
    }

    private List<DataerDataset> setDatasetOwner(List<DataerDataset> dataerDatasets) {
        Object[] datasets = dataerDatasets.stream().map(d -> {
            return d.getDataset();
        }).toArray();
        return datasets.length == 0 ? new ArrayList<>() : dataerDatasetRepository.findDatasetOwner(datasets);
    }

    private List<DataerDataset> getAllDatasets() {
        List<DataerDataset> dataerDatasets = dataerDatasetRepository.findAllDatasets();
        return setDatasetOwner(dataerDatasets);
    }

    @Override
    public List<DataerDataset> getAllUserDatasets(Long userId) {
        List<DataerDataset> dataerDatasets = dataerDatasetRepository.findAllDatasetsByUser(dataerRepository.findOne(userId));
        return setDatasetOwner(dataerDatasets);
    }

    private DatasetInfo assembleDatasetInfo(DataerDataset d) {
        DatasetInfo datasetInfo = new DatasetInfo();
        Dataset dataset = d.getDataset();
        Dataer dataer = d.getDataer();
        datasetInfo.setId(dataset.getId());
        datasetInfo.setCreateTime(dataset.getCreateTime());
        datasetInfo.setDsDescription(dataset.getDsDescription());
        datasetInfo.setFormatDescription(dataset.getFormatDescription());
        datasetInfo.setName(dataset.getName());
        datasetInfo.setOwnerName(dataer.getName());
        datasetInfo.setCoverUrl(dataset.getCoverUrl());
        datasetInfo.setContributeNum(dataset.getPullRequests().size());
        return datasetInfo;
    }

    @Override
    public List<Object> assembleDatasetInfos(PagedListHolder<DataerDataset> result) {
        return Arrays.asList(result.getPageList().stream().map(this::assembleDatasetInfo).toArray());
    }

    @Override
    public PagedListHolder<DataerDataset> getUserDatasetsByPage(Long userId, int curPage) {
        List<DataerDataset> datasets = getAllUserDatasets(userId);
        PagedListHolder<DataerDataset> datasetPage = new PagedListHolder<>(datasets);
        datasetPage.setSort(new MutableSortDefinition("dataset.name", true, true));
        datasetPage.resort();
        datasetPage.setPage(curPage);
        datasetPage.setPageSize(Constants.pageSize);
        return datasetPage;
    }

    @Override
    public PagedListHolder<DataerDataset> getAllDatasetsByPage(int curPage) {
        List<DataerDataset> datasets = getAllDatasets();
        PagedListHolder<DataerDataset> datasetPage = new PagedListHolder<>(datasets);
        datasetPage.setSort(new MutableSortDefinition("dataset.name", true, true));
        datasetPage.resort();
        datasetPage.setPage(curPage);
        datasetPage.setPageSize(Constants.pageSize);
        return datasetPage;
    }

    @Override
    public DatasetDetail getDatasetDetail(Long datasetId) {
        DataerDataset d = dataerDatasetRepository.findDatasetByDatasetId(datasetId);
        DatasetDetail datasetDetail = new DatasetDetail();
        datasetDetail.setUrl(getFileUrl(d.getDataset()));
        datasetDetail.setDatasetInfo(assembleDatasetInfo(d));
        MetaData meta = metaDataRepository.findByDatasetId(d.getDataset().getId());
        List<Field> fields = meta.getFields();
        datasetDetail.setColumns(fields.toArray(new Field[fields.size()]));
        return datasetDetail;
    }

    @Override
    public void createNewDataset(Long id, String datasetName, String dsDesc, String formatDesc, String fieldsString, String coverUrl) {
        Dataer dataer = dataerRepository.findOne(id);
        System.out.println(dataer.getEmail());
        Dataset dataset = new Dataset(datasetName);
        dataset.setDsDescription(dsDesc);
        dataset.setCoverUrl(coverUrl);
        dataset.setFormatDescription(formatDesc);
        dataset.setCreateTime(new Date());
        datasetRepository.save(dataset);
        Gson gson = new Gson();
        Field[] fields = gson.fromJson(fieldsString, Field[].class);
        MetaData meta = new MetaData(dataset.getId(), Arrays.asList(fields));
        dataerDatasetRepository.save(new DataerDataset(dataer, dataset, (short)(0)));
        metaDataRepository.save(meta);
        dataerRepository.save(dataer);
        System.out.println(datasetName + " success");
    }

    @Override
    public DatasetContent getDatasetContent(Long datasetId, Long dataerId) {
        DatasetContent datasetContent = new DatasetContent();
        Dataset dataset = datasetRepository.findOne(datasetId);
        Set<DataerDataset> dataers = dataset.getDataers();
        Dataer dataer = dataerRepository.findOne(dataerId);
        boolean isAdmin = dataerDatasetRepository.findDatasetOwner(new Object[]{dataset}).get(0).getDataer() == dataer;
        if (isAdmin) {
            datasetContent.setAdmin(1);
        } else {
            datasetContent.setAdmin(0);
        }

        datasetContent.setUrl(getFileUrl(dataset));
        int count = 0;
        List<PullRequest> pullRequests = dataset.getPullRequests();
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

    @Override
    public Set<Dataset> searchDatasets(String name) {
        return datasetRepository.findLikeName(name);
    }

    @Override
    public Set<DatasetTitle> getDatasetTitle(String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        Set<DatasetTitle> datasetTitleSet = dataset.getDatasetTitles();
        return datasetTitleSet;
    }

    @Override
    public void addDatasetTitle(String datasetName, DatasetTitle datasetTitle) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        dataset.getDatasetTitles().add(datasetTitle);
        datasetRepository.save(dataset);
    }

    @Override
    public List<Dataer> getContribute(String datasetNsame) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetNsame);
        List<PullRequest> pullRequests = dataset.getPullRequests();
        List<Dataer> dataers = new ArrayList<>();
        for (PullRequest pullRequest : pullRequests) {
            dataers.add(pullRequest.getDataer());
        }
        return dataers;
    }

    @Override
    public List<DataFile> getDatasetFile(String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        Set<DataFile> dataFiles = dataset.getFiles();
        List<DataFile> dataFileList = new ArrayList<DataFile>(dataFiles);
        return dataFileList;
    }

    @Override
    public Dataset findByDatasetName(String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        return dataset;
    }

    @Override
    public void save(Dataset dataset) {
        datasetRepository.save(dataset);
    }

    @Override
    public void combineDataset(Long datasetId) {
//        DataFile dataFile = new DataFile();
//        dataFile.setName(datasetName + ".csv");
//        dataFile.setUpdateTime(new Date());
//        dataFileRepository.save(dataFile);
//
//        Dataset dataset = datasetRepository.findOne(datasetId);
//
//        DataFile dataFile = new DataFile();
//        dataFile.setCreateTime(new Date());
//        dataFileRepository.save(dataFile);
//
//        dataset.setAllFile(dataFile);
//        datasetRepository.save(dataset);
//
//        List<String> files = new ArrayList<>();
//        for (PullRequest pullRequest : dataset.getPullRequests()) {
//            if (pullRequest.getStatus() == 1) {
//                String url = qiniuService.downloadFile(pullRequest.getDataFile(), fileProperties.getDatasetPath());
//                files.add(url);
//            }
//        }
//
//        String writeUrl = fileProperties.getDatasetPath() + dataFile.getFileid() + ".csv";
//        CsvWriter csvWriter = new CsvWriter(writeUrl, ',', Charset.forName("UTF-8"));
//
//        Set<DatasetTitle> datasetTitles = dataset.getDatasetTitles();
//        List<String> titles = new ArrayList<>();
//        for (DatasetTitle datasetTitle : datasetTitles) {
//            titles.add(datasetTitle.getTitleName());
//        }
//        String[] tt = new String[titles.size()];
//        titles.toArray(tt);
//        try {
//            csvWriter.writeRecord(tt);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        for (String path : files) {
//            CsvReader csvReader = null;
//            try {
//                csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));
//                csvReader.readRecord();
//                while (csvReader.readRecord()) {
//                    String[] temp = csvReader.getValues();
//                    csvWriter.writeRecord(temp);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (csvReader != null) {
//                csvReader.close();
//            }
//        }
//        csvWriter.close();
//
//
//
//        String qiniuUrl = dataFile.getFileid() + ".csv";
//
//        qiniuService.uploadFile(writeUrl, qiniuUrl);

    }

}

