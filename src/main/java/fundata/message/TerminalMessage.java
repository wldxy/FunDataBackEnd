package fundata.message;

/**
 * Created by huang on 17-6-13.
 */
public class TerminalMessage {
    Long user_id;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getDataset_id() {
        return dataset_id;
    }

    public void setDataset_id(Long dataset_id) {
        this.dataset_id = dataset_id;
    }

    Long dataset_id;

    public TerminalMessage(Long user_id, Long dataset_id) {
        this.user_id = user_id;
        this.dataset_id = dataset_id;
    }
}
