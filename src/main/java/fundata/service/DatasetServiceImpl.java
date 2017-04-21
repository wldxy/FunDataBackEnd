package fundata.service;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.gson.Gson;
import fundata.configure.Constants;
import fundata.document.Field;
import fundata.document.MetaData;
import fundata.model.*;
import fundata.repository.*;
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

    @Override
    public PagedListHolder<DataerDataset> getUserDatasetsByPage(Long userId, int curPage) {
        List<DataerDataset> datasets = getAllUserDatasets(userId);
        PagedListHolder<DataerDataset> datasetPage = new PagedListHolder<>(datasets);
        datasetPage.setSort(new MutableSortDefinition("name", true, true));
        datasetPage.resort();
        datasetPage.setPage(curPage);
        datasetPage.setPageSize(Constants.pageSize);
        return datasetPage;
    }

    @Override
    public List<DataerDataset> getAllUserDatasets(Long userId) {
        return dataerDatasetRepository.findDataerDatasetByUser(dataerRepository.findById(userId));
    }


    @Override
    public Set<Dataset> findLikeName(String name) {
        return datasetRepository.findLikeName(name);
    }


    @Override
    public void addDataset(Long id, String datasetName, String dsDesc, String formatDesc, String fieldsString) {
        Dataer dataer = dataerRepository.findById(id);
        System.out.println(dataer.getEmail());
        Dataset dataset = new Dataset(datasetName);
        dataset.setDsDescription(dsDesc);
        dataset.setFormatDescription(formatDesc);
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
        Set<PullRequest> pullRequests = dataset.getPullRequests();
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
    public void combineDataset(String datasetName) {
//        DataFile dataFile = new DataFile();
//        dataFile.setName(datasetName + ".csv");
//        dataFile.setUpdateTime(new Date());
//        dataFileRepository.save(dataFile);
//
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);

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

