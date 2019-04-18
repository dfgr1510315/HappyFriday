package Model.SubModel;

import Model.Ask;

public class Ask_list extends Ask {
    private int ask_id;
    private String cover_address;
    private String new_answerer;
    private String new_answer;

    public int getAsk_id() {
        return ask_id;
    }

    public void setAsk_id(int ask_id) {
        this.ask_id = ask_id;
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
