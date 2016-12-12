package fundata.service;

import com.csvreader.CsvReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ocean on 16-12-12.
 */
public class ClassifyEvaluator extends Evaluator {

    private Map<Integer, Integer> ans = new HashMap<>();

    public ClassifyEvaluator(String path) {
        super(path);
    }

    @Override
    public boolean init(String path) {
        try {
            CsvReader csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                Integer index = Integer.parseInt(csvReader.get(0));
                Integer classify = Integer.parseInt(csvReader.get(1));
                ans.put(index, classify);
            }
            csvReader.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double evaluate(String path) {
        try {
            CsvReader csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));
            csvReader.readHeaders();
            int count = 0;
            while (csvReader.readRecord()) {
                Integer index = Integer.parseInt(csvReader.get(0));
                Integer classify = Integer.parseInt(csvReader.get(1));
                if (ans.get(index) != null && ans.get(index).equals(classify)) {
                    count++;
                }
            }
            return (double) count / ans.size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
