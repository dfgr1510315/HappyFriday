package Tool;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyExcelTest {
 /*   public static void main(String[] args) {
        String filepath ="web\\ExcelWrite\\withHead.xlsx";
        List<String> randomSel = read(filepath,4,1);
        for (String s : randomSel) {
            System.out.println(s);
        }
    }*/
    public List<String> read(String filepath) {
        List<String> sheetContent = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filepath)) {
            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, new AnalysisEventListener<List<String>>() {
                        @Override
                        public void invoke(List<String> object, AnalysisContext context) {
                            //System.out.println(object);
                            StringBuilder stringBuffer = new StringBuilder();
                            if(object != null && !StringUtils.isEmpty(object.get(0))){
                                for(String s: object){
                                    if(null!=s){
                                        stringBuffer.append(s).append("~~");
                                    }
                                }
                                //System.out.println("行=="+context.getCurrentRowNum()+":  "+stringBuffer.toString());
                                sheetContent.add(stringBuffer.toString());

                            }
                            //System.out.println("当前sheet:" + context.getCurrentSheet().getSheetNo() + ",当前行:" + context.getCurrentRowNum());
                        }
                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    });
            excelReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheetContent;
    }

    public List<String> read(String filepath,int sel,int cal){
        List<String> selContent = new ArrayList<>();
        List<String> calContent = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filepath)) {
            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, new AnalysisEventListener<List<String>>() {
                @Override
                public void invoke(List<String> object, AnalysisContext context) {
                    StringBuilder stringBuffer = new StringBuilder();
                    if(object != null && !StringUtils.isEmpty(object.get(0))){
                        for(String s: object){
                            if(null!=s){
                                stringBuffer.append(s).append("~~");
                            }
                        }
                        switch (object.get(0)) {
                            case "选择题":
                                selContent.add(stringBuffer.toString());
                                break;
                            case "简答题":
                                calContent.add(stringBuffer.toString());
                                break;
                        }
                    }
                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    int selSize = selContent.size();
                    int calSize = calContent.size();
                    for (int j=0;j<selSize-sel;j++){
                        Random ran = new Random();
                        int i = ran.nextInt(selContent.size());
                        //RandomContent.add(sheetContent.get(i));
                        selContent.remove(i);
                    }
                    for (int j=0;j<calSize-cal;j++){
                        Random ran = new Random();
                        int i = ran.nextInt(calContent.size());
                        //RandomContent.add(sheetContent.get(i));
                        calContent.remove(i);
                    }
                }
            });
            excelReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        selContent.addAll(calContent);
        return selContent;
    }

    //获取题库行数
    public int[] getLine(String filepath){
        int[] line = {0,0};
        try (InputStream inputStream = new FileInputStream(filepath)) {
            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, new AnalysisEventListener<List<String>>() {
                @Override
                public void invoke(List<String> object, AnalysisContext context) {
                    //line = context.getCurrentRowNum();
                    if(object != null && !StringUtils.isEmpty(object.get(0))){
                        switch (object.get(0)) {
                            case "选择题":
                                line[0]++;
                                break;
                            case "简答题":
                                line[1]++;
                                break;
                        }
                    }

                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    //System.out.println("选择题行数："+line[0]);
                    //System.out.println("简答题行数："+line[1]);
                }
            });
            excelReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }


}
