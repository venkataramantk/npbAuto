package tests.web.reports;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.web.initializer.BaseTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by skonda on 11/25/2016.
 */
public class ReportReader extends BaseTest{

    private static final Logger logger = Logger.getLogger(ReportReader.class);
    String excelFileName;
    File file;
    Workbook workbook;
    FileOutputStream fileOut;
    String yesterdaysDate=null;
    String dayBeforeYesterdayData =null;

    @BeforeClass
    public void setUp() throws Exception {
        initializeDriver();
        driver = getDriver();
        String int3Regression="http://10.18.3.192:8080/jenkins/view/UATLIVE1/job/Phase2_Regression_UATLIVE1_Firefox/HTML_Report/Results/Run_1/CurrentRun.html";
        driver.get(int3Regression);
        initializePages(driver);
        yesterdaysDate = homePageActions.getDateTimeByAddingDays("dd-MMM-yy", 0);
        dayBeforeYesterdayData =homePageActions.getDateTimeByAddingDays("dd-MMM-yy", -1);
        excelFileName = System.getProperty("user.dir") + "\\" + "target\\" + "Report.xls";
        file = new File(excelFileName);
        if (file.exists())
            file.delete();

        file.getParentFile().mkdir();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        workbook = new HSSFWorkbook();
    }


    @Test
    public void generateReportWithAuthorNames() throws Exception {
        long startTime = System.currentTimeMillis();
        String sheetName = "TodaysReport";
        int rowCount = 0;
        Sheet sheet = workbook.createSheet(sheetName);
        CreationHelper createHelper =  sheet.getWorkbook()
                .getCreationHelper();

        Hyperlink url_link ;
        CellStyle hlink_style=getHyperLinkStyle(sheet);
        CellStyle redFont=getBoldAndRedColorCellStyle(sheet);
        CellStyle blueFont=getBoldAndLightBlueColorCellStyle(sheet);
        Row headerRow = sheet.createRow(rowCount++);
        createHeaderColumn(sheet, 0,"Sl.No", headerRow);

        List<WebElement> packageNamesList = driver.findElements(By.cssSelector(".all.pass td:nth-child(1), .all.fail td:nth-child(1),.all.skip td:nth-child(1)"));
        List<String> packages= new ArrayList<String>();
        for(int i=0;i<packageNamesList.size();i++)
        {
            String fullPackageName=packageNamesList.get(i).getText();
            String packageNames[]=fullPackageName.split("\\.");
            String packageName=packageNames[packageNames.length-1];
            packageName=packageName.substring(0, 1).toUpperCase() + packageName.substring(1);
            packages.add(packageName.trim());

        }

        Set<String> hs = new HashSet<>();
        hs.addAll(packages);
        packages.clear();
        packages.addAll(hs);
        Collections.sort(packages);
        for(int j=0;j<packages.size();j++) {
            try {
                WebElement tbody = driver.findElement(By.cssSelector("#tableStyle>tbody"));
                List<WebElement> passRows = tbody.findElements(By.cssSelector(".all.pass, .all.fail,.all.skip"));
                int rowStart=rowCount;
                for (int i = 0; i < passRows.size(); i++) {
                    waitUntilElementDisplayed("#tableStyle>tbody");
                    WebElement tbody1 = driver.findElement(By.cssSelector("#tableStyle>tbody"));
                    List<WebElement> passRows1 = tbody1.findElements(By.cssSelector(".all.pass, .all.fail,.all.skip"));
                    List<WebElement> td_collection = passRows1.get(i).findElements(By.xpath("td"));
                    String packageNames[]=td_collection.get(0).getText().split("\\.");
                    String packageName=packageNames[packageNames.length-1];
                    String actualPackageName=packageName.substring(0, 1).toUpperCase() + packageName.substring(1);

                    if (actualPackageName.toLowerCase().contains(packages.get(j).toLowerCase())) {
                        Row dataRow = sheet.createRow(rowCount++);
                        url_link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
                        url_link.setAddress(td_collection.get(3).findElement(By.tagName("a")).getAttribute("href").replace("\\", "/").replaceAll("%5C", "/"));
                        createHeaderColumnWithOutBold(sheet, 1, packages.get(j).toUpperCase(), dataRow);
                        createHeaderColumnWithOutBold(sheet, 2, td_collection.get(1).getText(), dataRow);
                        createHeaderColumnWithOutBold(sheet, 3, td_collection.get(3).getText(), dataRow, url_link, hlink_style);
                        if (passRows1.get(i).getAttribute("class").contains("pass"))
                            createHeaderColumnWithOutBold(sheet, 4, "Pass", dataRow);
                        else if (passRows1.get(i).getAttribute("class").contains("fail")) {
                            td_collection.get(3).findElement(By.tagName("a")).click();
                            WebElement authorName=driver.findElement(By.cssSelector("#content div:nth-child(5) tbody tr:nth-child(1) td:nth-child(3)"));
                            String authorNameText=authorName.getText();
                            driver.navigate().back();
                            createHeaderColumnWithOutBold(sheet, 4, "Fail", dataRow, redFont);
                            createHeaderColumnWithOutBold(sheet, 5, authorNameText, dataRow);
                        }
                        else
                            createHeaderColumnWithOutBold(sheet, 4, "Skip", dataRow, blueFont);
                    }
                }
                int rowEnd=rowCount;
                mergeCells(sheet, rowStart, rowEnd - 1, 1, 1);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        // sheet.setAutoFilter(CellRangeAddress.valueOf("A1:K200"));
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
            outputStream.close();
        }
        long totalTime= (System.currentTimeMillis()-startTime)/1000;
        String totalTimeTook= new Long(totalTime).toString()+" secs";
        System.out.println(sheetName + "  test time took"+ totalTimeTook);
    }

    public CellStyle getHyperLinkStyle(Sheet sheet) {
        CellStyle hlink_style = sheet.getWorkbook().createCellStyle();
        CreationHelper createHelper =  sheet.getWorkbook()
                .getCreationHelper();
        Font hlink_font = sheet.getWorkbook().createFont();
        hlink_font.setUnderline(Font.U_SINGLE);
        hlink_font.setColor(HSSFColor.BLUE.index);
        hlink_style.setFont(hlink_font);

        return hlink_style;
    }

    public CellStyle getBoldAndRedColorCellStyle(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.RED.index);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public CellStyle getBoldAndLightBlueColorCellStyle(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLUE.index);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public void createHeaderColumn(Sheet sheet,int cellNo,String cellValue, Row row) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        Cell cell = row.createCell(cellNo);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cellValue);
        sheet.autoSizeColumn(cellNo);
    }

    public Boolean waitUntilElementDisplayed(String cssSelector)
    {
        try {

            (new WebDriverWait(driver, 60))

                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    public void createHeaderColumnWithOutBold(Sheet sheet,int cellNo,String cellValue, Row row) {
        Cell cell = row.createCell(cellNo);
        cell.setCellValue(cellValue);
        sheet.autoSizeColumn(cellNo);
    }

    public void createHeaderColumnWithOutBold(Sheet sheet,int cellNo,String cellValue, Row row,Hyperlink hssfHyperlink,CellStyle hlink_style) {
        Cell cell = row.createCell(cellNo);
        cell.setCellValue(cellValue);
        cell.setHyperlink(hssfHyperlink);
        cell.setCellStyle(hlink_style);
        sheet.autoSizeColumn(cellNo);
    }

    public void createHeaderColumnWithOutBold(Sheet sheet,int cellNo,String cellValue, Row row,CellStyle cellStyle) {
        Cell cell = row.createCell(cellNo);
        cell.setCellValue(cellValue);
        cell.setCellStyle(cellStyle);
        sheet.autoSizeColumn(cellNo);
    }

    public void mergeCells(Sheet sheet, int rowStart, int rowEnd, int colStart, int colEnd) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
        sheet.addMergedRegion(cellRangeAddress);
    }
}
