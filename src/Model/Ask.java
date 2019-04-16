package Model;

public class Ask {
    private int ask_id;
    private int belong_class_id;
    private int answer_count;
    private int visits_count;
    private String ask_title;
    private String ask_time;
    private String class_title;
    private String cover_address;
    private String new_answerer;
    private String new_answer;

    public int getAsk_id() {
        return ask_id;
    }

    public void setAsk_id(int ask_id) {
        this.ask_id = ask_id;
    }

    public int getBelong_class_id() {
        return belong_class_id;
    }

    public void setBelong_class_id(int belong_class_id) {
        this.belong_class_id = belong_class_id;
    }

    public int getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    public int getVisits_count() {
        return visits_count;
    }

    public void setVisits_count(int visits_count) {
        this.visits_count = visits_count;
    }

    public String getAsk_title() {
        return ask_title;
    }

    public void setAsk_title(String ask_title) {
        this.ask_title = ask_title;
    }

    public String getAsk_time() {
        return ask_time;
    }

    public void setAsk_time(String ask_time) {
        this.ask_time = ask_time;
    }

    public String getClass_title() {
        return class_title;
    }

    public void setClass_title(String class_title) {
        this.class_title = class_title;
    }

    public String getCover_address() {
        return cover_address;
    }

    public void setCover_address(String cover_address) {
        this.cover_address = cover_address;
    }

    public String getNew_answerer() {
        return new_answerer;
    }

    public void setNew_answerer(String new_answerer) {
        this.new_answerer = new_answerer;
    }

    public String getNew_answer() {
        return new_answer;
    }

    public void setNew_answer(String new_answer) {
        this.new_answer = new_answer;
    }
}
