package DAO;

import java.util.List;

public interface askDAO {
    //获取该课程问答数量
    int get_ask_count(String SQL);

    //获取问答
    List get_ask(int page,String SQL);
}
