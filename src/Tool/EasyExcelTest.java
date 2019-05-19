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


/**
 * @author somdip
 * @since 1.0 2018-09-28
 */
public class EasyExcelTest {

   /* public static void main(String[] args) {

        long act = System.currentTimeMillis();
        String filepath ="web\\ExcelWrite\\withHead.xlsx";
        List<String> sheetContent = read(filepath);
        //write();
        System.out.println("数据"+sheetContent);
        System.out.println("一共"+sheetContent.size()+"有效数据");
        long end = System.currentTimeMillis();
        System.out.println("耗时间=======:"+(end-act)+"毫秒");
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
                               /* for (int i=0;i<2;i++){
                                    if(null!=object.get(i)){
                                        stringBuffer.append(object.get(i)).append("~~");
                                    }
                                }*/
                                //System.out.println("行=="+context.getCurrentRowNum()+":  "+stringBuffer.toString());
                                sheetContent.add(stringBuffer.toString());
                                //System.out.println("当前sheet:" + context.getCurrentSheet().getSheetNo() + ",当前行:" + context.getCurrentRowNum());
                            }

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


}
