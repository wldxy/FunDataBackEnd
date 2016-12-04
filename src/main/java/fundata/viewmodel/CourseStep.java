package fundata.viewmodel;

/**
 * Created by stanforxc on 2016/12/4.
 */
public class CourseStep {
        private String step_header;
        private String step_url;
        private String step_pic_url;

    public CourseStep(String step_header, String step_url, String step_pic_url) {
        this.step_header = step_header;
        this.step_url = step_url;
        this.step_pic_url = step_pic_url;
    }


    public String getStep_url() {
        return step_url;
    }

    public void setStep_url(String step_url) {
        this.step_url = step_url;
    }

    public String getStep_pic_url() {
        return step_pic_url;
    }

    public void setStep_pic_url(String step_pic_url) {
        this.step_pic_url = step_pic_url;
    }

    public String getStep_header() {
        return step_header;
    }

    public void setStep_header(String step_header) {
        this.step_header = step_header;
    }
}
