package com.el.canno.excel;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HSSF 解析xls格式
 * XSSF 解析xlsx格式
 * User Canno
 * Date 2018/3/21
 * Time 14:30
 */

public class ExcelUtils {
    public static void main(String[] args) {
        List<String[]> listXlsx = getExcel("d:/test.xlsx");
        List<String[]> listXls = getExcel("d:/test.xls");
        printList(listXlsx);
        System.out.println("----------------------------------------");
        printList(listXls);

    }

    private static void printList(List<String[]> list){
        for(int i=0; i<list.size(); i++){
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j< list.get(i).length; j ++){
                sb.append("\t").append(list.get(i)[j]);
                System.out.printf("%20s",list.get(i)[j]);
            }
            System.out.println();
        }
    }

    /**
     * 解析EXCEL
     * 每行内容解析到String[]数组内
     *
     * @param filePath
     * @return List<String[]>
     */
    public static List<String[]> getExcel(String filePath) {
        if (filePath.matches("^.+\\.(?i)(xls)$")) {
            return getExcel4Xls(filePath);
        } else {
            return getExcel4Xlsx(filePath);
        }
    }

    /**
     * 解析xls格式EXCEL表格
     *
     * @param filePath
     * @return
     */
    private static List<String[]> getExcel4Xls(String filePath) {
        List<String[]> list = new ArrayList<>();

        POIFSFileSystem fs = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HSSFSheet sheet = wb.getSheetAt(0);

        int trLength = sheet.getLastRowNum() + 1;
        int tdLength = sheet.getRow(0).getLastCellNum();

        for (int i = 0; i < trLength; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            String[] strings = new String[tdLength];

            for (int j = 0; j < tdLength; j++) {
                HSSFCell cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                cell.setCellType(CellType.STRING);
                strings[j] = cell.getStringCellValue();
            }
            list.add(strings);
        }
        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 读取xlsx 格式 Excel
     * 每一行的内容存入 String[] 数组中
     *
     * @param filePath xlsx 文件路径
     * @return List<String[]>
     */
    private static List<String[]> getExcel4Xlsx(String filePath) {
        List<String[]> list = new ArrayList<>();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workbook.getSheetAt(0);
        int trLength = sheet.getLastRowNum() + 1;
        int tdLength = sheet.getRow(0).getLastCellNum();

        //循环解析excel表格 先行后列
        for (int j = 0; j < trLength; j++) {
            XSSFRow row = sheet.getRow(j);
            if (row == null) {
                continue;
            }
            String[] strings = new String[row.getLastCellNum()];
            for (int i = 0; i < tdLength; i++) {
                XSSFCell cell = row.getCell(i);
                if (cell == null) {
                    continue;
                }
                cell.setCellType(CellType.STRING);
                strings[i] = cell.getStringCellValue();
            }
            list.add(strings);
        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
