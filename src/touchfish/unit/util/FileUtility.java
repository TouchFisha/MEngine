package touchfish.unit.util;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

public class FileUtility {

    public static String readText(File file){
        StringBuffer fStr = new StringBuffer();
        try {
            BufferedReader mReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while((line = mReader.readLine()) != null) {
                fStr.append(line + "\n");
            }
            mReader.close();
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        } catch (IOException var8) {
            var8.printStackTrace();
        }
        return fStr.toString();
    }
    public static <T> void ExportToLocal(List<T> data, Class clazz) {
        HSSFWorkbook excel = CreateExcel(data,clazz);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(clazz.getName() + ".xls");
            excel.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
    public static <T> void ExportToOutputStream(List<T> data,OutputStream outputStream,Class clazz) {
        HSSFWorkbook excel = CreateExcel(data,clazz);
        try {
            excel.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private static <T> HSSFWorkbook CreateExcel(List<T> data,Class clazz) {
        String[] fieldNames = fieldName(clazz);
        String[] headers = fieldPropertyValue(clazz);
        HSSFWorkbook excel = new HSSFWorkbook();
        HSSFSheet page = excel.createSheet();
        Row headRow = page.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headRow.createCell(i).setCellValue(headers[i]);
        }
        try {
            for (int x = 0; x < data.size(); x++) {
                Row r = page.createRow(x+1);
                for (int i = 0; i < headers.length; i++) {
                    T t = data.get(x);
                    for (int i1 = 0; i1 < fieldNames.length; i1++) {
                        String methodName = "get" + fieldNames[i].substring(0, 1).toUpperCase() + fieldNames[i].substring(1);
                        Method method = t.getClass().getMethod(methodName);
                        Object value = method.invoke(t);
                        r.createCell(i).setCellValue(value==null? "" : value.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excel;
    }

    private static String[] fieldName(Class clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        String[] fieldNames = new String[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            fieldNames[i] = declaredFields[i].getName(); //通过反射获取属性名
        }
        return fieldNames;
    }

    private static String[] fieldPropertyValue(Class clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        String[] res = new String[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            res[i] = (String) ReflectionUtility.getAnnotationParameterValue(declaredFields[i],"io.swagger.annotations.ApiModelProperty","value"); //通过反射获取属性名
        }
        return res;
    }

    public static EPageData[] ReadAll(File file){
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        EPageData data[] = new EPageData[wb.getNumberOfSheets()];
        for (int sheetIndex = 0; sheetIndex < data.length; sheetIndex++)
            data[sheetIndex] = ReadSheet(wb.getSheet(sheetIndex));
        wb.close();
        return data;
    }
    public static EPageData Read(File file,int sheetIndex){
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        EPageData data = ReadSheet(wb.getSheet(sheetIndex));
        wb.close();
        return data;
    }
    private static EPageData ReadSheet(Sheet sheet){
        EPageData data = null;
        String[] heads = new String[sheet.getColumns()];
        for (int i = 0; i < heads.length; i++) heads[i] = sheet.getCell(i,0).getContents();
        String[][] datas = new String[sheet.getRows()][heads.length];
        for (int i = 0; i < datas.length; i++)
            for (int j = 0; j < datas[i].length; j++)
                datas[i][j] = sheet.getCell(j,i).getContents();
        data = new EPageData(sheet.getName(), heads,datas);
        return data;
    }
    /*
    // 如果是SpringBoot项目，启用这些以使用自动导出功能
    public static String save(String path, MultipartFile ... files) throws IOException {
        StringBuilder filesNames = new StringBuilder();
        if (files.length>0) {
            for (MultipartFile file : files) {
                String format = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                String fileName = UUID.randomUUID().toString()+"."+format;
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdirs();
                }
                String fileDir = path + fileName;
                File saveFile = new File(fileDir);
                file.transferTo(saveFile);
                filesNames.append(fileName);
                filesNames.append(";");
            }
            return filesNames.toString();
        }else {
            throw new RuntimeException("err");
        }
    }
    public static File ToFile(MultipartFile mfile){
        String fileName = mfile.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        File file = null;
        try {
            file = File.createTempFile(fileName, prefix);
            mfile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    */
    public static class EPageData {
        public String pageName;
        public String[] header;
        public String[][] data;

        public EPageData(String pageName, String[] header, String[][] data) {
            this.pageName = pageName;
            this.header = header;
            this.data = data;
        }
        public EPageData(String pageName, int headCount, int dataL) {
            this.pageName = pageName;
            this.header = new String[headCount];
            this.data = new String[dataL][headCount];
        }
        public String toString(){
            String str = "";
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < header.length; j++)
                    str+=data[i][j]+"\t";
                str+="\n";
            }
            return str;
        }
    }
}
