package fundata.service;

import fundata.model.DataFile;

import java.util.Map;

/**
 * Created by ocean on 16-12-12.
 */
public abstract class Evaluator {
    public Evaluator(String path) {
        this.init(path);
    }

    public abstract boolean init(String path);

    public abstract double evaluate(String path);
}
