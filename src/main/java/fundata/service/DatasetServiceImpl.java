package fundata.service;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.gson.Gson;
import fundata.configure.Constants;
import fundata.configure.FileProperties;
import fundata.document.Field;
import fundata.document.MetaData;
import fundata.model.*;
import fundata.repository.*;
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
    DatasetTitleRepository datasetTitleRepository;

    private List<DataerDataset> getDatasetOwner(List<DataerDataset> dataerDatasets) {
        Object[] datasets = dataerDatasets.stream().map(d -> {
            return d.getDataset();
        }).toArray();
        return datasets.length == 0 ? new ArrayList<>() : dataerDatasetRepository.findDatasetOwner(datasets);
    }

    private List<DataerDataset> getAllDatasets() {
        List<DataerDataset> dataerDatasets = dataerDatasetRepository.findAllDatasets();
        return getDatasetOwner(dataerDatasets);
    }

    @Override
    public List<DataerDataset> getAllUserDatasets(Long userId) {
        List<DataerDataset> dataerDatasets = dataerDatasetRepository.findAllDatasetsByUser(dataerRepository.findOne(userId));
        return getDatasetOwner(dataerDatasets);
    }

    @Override
    public List<Object> assembleDatasetInfo(PagedListHolder<DataerDataset> result) {
        return Arrays.asList(result.getPageList().stream().map(d -> {
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
        }).toArray());
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
    public Set<Dataset> findLikeName(String name) {
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

    @Autowired
    QiniuService qiniuService;

    @Autowired
    FileProperties fileProperties;

    @Autowired
    DataFileRepository dataFileRepository;

    @Override
    public void combineDataset(Long datasetId) {
//        DataFile dataFile = new DataFile();
//        dataFile.setName(datasetName + ".csv");
//        dataFile.setUpdateTime(new Date());
//        dataFileRepository.save(dataFile);
//
        Dataset dataset = datasetRepository.findOne(datasetId);

        DataFile dataFile = new DataFile();
        dataFile.setCreateTime(new Date());
        dataFileRepository.save(dataFile);

        dataset.setAllFile(dataFile);
        datasetRepository.save(dataset);

        List<String> files = new ArrayList<>();
        for (PullRequest pullRequest : dataset.getPullRequests()) {
            if (pullRequest.getStatus() == 1) {
                String url = qiniuService.downloadFile(pullRequest.getDataFile(), fileProperties.getDatasetPath());
                files.add(url);
            }
        }

        String writeUrl = fileProperties.getDatasetPath() + dataFile.getFileid() + ".csv";
        CsvWriter csvWriter = new CsvWriter(writeUrl, ',', Charset.forName("UTF-8"));

        Set<DatasetTitle> datasetTitles = dataset.getDatasetTitles();
        List<String> titles = new ArrayList<>();
        for (DatasetTitle datasetTitle : datasetTitles) {
            titles.add(datasetTitle.getTitleName());
        }
        String[] tt = new String[titles.size()];
        titles.toArray(tt);
        try {
            csvWriter.writeRecord(tt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String path : files) {
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
            if (csvReader != null) {
                csvReader.close();
            }
        }
        csvWriter.close();



        String qiniuUrl = dataFile.getFileid() + ".csv";

        qiniuService.uploadFile(writeUrl, qiniuUrl);

    }

}

