package com.akvasoft.NIH_Enterprise_Directory.config;

import com.akvasoft.NIH_Enterprise_Directory.model.NIH;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class BrowseNIH {
    private static FirefoxDriver driver = null;
    private static String url[] = {"https://ned.nih.gov/search/?fbclid=IwAR37ECfOdZxDluGqqCQNUhikmzK87IIBlNfdNaZbMw-PE5pd0LK0N6wNdFM"};
    private static String codes[] = {"NIH"};
    private static HashMap<String, String> handlers = new HashMap<>();

    public static void initialise() throws Exception {
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        FirefoxOptions options = new FirefoxOptions(dc);
        options.setHeadless(true);

        driver = new FirefoxDriver();

        for (int i = 0; i < url.length - 1; i++) {
            driver.executeScript("window.open()");
        }

        ArrayList<String> windowsHandles = new ArrayList<>(driver.getWindowHandles());

        for (int i = 0; i < url.length; i++) {
            handlers.put(codes[i], windowsHandles.get(i));
        }

        scrape("https://ned.nih.gov/search/?fbclid=IwAR37ECfOdZxDluGqqCQNUhikmzK87IIBlNfdNaZbMw-PE5pd0LK0N6wNdFM");

//        Instant start = Instant.now();
//        List<SiteRow> calcio = scrape("https://www.betwin360.it/sport.php", 3, "Calcio", "Esito finale 1x2", calcioList);
//        List<SiteRow> tennis = scrape("https://www.betwin360.it/sport.php", 5, "tennis", "Vincitore", tennisList);
//        List<SiteRow> basket = scrape("https://www.betwin360.it/sport.php", 6, "pallac", "Vincitore", basketList);
//        List<SiteRow> volly = scrape("https://www.betwin360.it/sport.php", 7, "Pallavolo", "Vincitore", vollyList);

//        Instant end = Instant.now();
//        ScrapeApplication.ai.decrementAndGet();

//        try {
//            driver.quit();
//        } catch (Exception e) {
//        }

//        while (ScrapeApplication.ai.get() > 0) {
//            Thread.sleep(1000);
//            System.out.println("waiting betwin bet" + Duration.between(start, end));
//        }

//        synchronized (ScrapeApplication.lock) {
//            System.out.println("finished betwin bet" + Duration.between(start, end));
//            calcio.sort(Comparator.comparing(SiteRow::getDescrizione_1));
//            tennis.sort(Comparator.comparing(SiteRow::getDescrizione_1));
//            basket.sort(Comparator.comparing(SiteRow::getDescrizione_1));
//            volly.sort(Comparator.comparing(SiteRow::getDescrizione_1));

//            Workbook workbook = new XSSFWorkbook();
//            createXlsFile(calcio, "Calcio", workbook, true);
//            createXlsFile(tennis, "Tennis", workbook, false);
//            createXlsFile(basket, "Basket", workbook, false);
//            createXlsFile(volly, "Pallavolo", workbook, false);
//            new File("./current").mkdir();
//            new File("./history/betwin").mkdirs();
//
//            Calendar cal = Calendar.getInstance();
//            FileOutputStream fileOut = new FileOutputStream("./current/betwin.xlsx");
//            workbook.write(fileOut);
//            fileOut.close();
//
//            fileOut = new FileOutputStream("./history/betwin/betwin" + DateTimeUtil.getCurrentTimeStamp() + ".xlsx");
//            workbook.write(fileOut);
//            fileOut.close();
//            workbook.close();
//        }
    }

    public static List<NIH> scrape(String link) throws InterruptedException, IOException, AWTException {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        List<NIH> list = new ArrayList<>();
        NIH nih = null;

        String legalName = "";
        String preferredName = "";
        String eMail = "";
        String location = "";
        String mailStop = "";
        String phone = "";
        String fax = "";
        String ic = "";
        String organization = "";
        String classification = "";
        String tty = "";

        driver.get(link);
        System.out.println("RUNNING " + link);

        WebElement inputLastName = driver.findElementById("ContentPlaceHolder_txtLastName");
        jse.executeScript("arguments[0].setAttribute('value', 'aa')", inputLastName);
        WebElement searchButton = driver.findElementById("ContentPlaceHolder_btnSearchName");
        jse.executeScript("arguments[0].scrollIntoView();", searchButton);
        searchButton.click();
        Thread.sleep(10000);

        List<String> dataUrls = new ArrayList<>();
        int i = 1;
        WebElement pageNumberTab = driver.findElementByXPath("/html/body/div[1]/div[3]/div[1]/table/tbody/tr/td/form/table/tbody/tr[3]/td/table/tbody/tr[1]/td/table/tbody/tr[4]/td/div/table/tbody/tr[3]/td/div/table/tbody/tr[1]/td/table/tbody/tr");
        for (WebElement td : pageNumberTab.findElements(By.tagName("td"))) {
//            jse.executeScript("arguments[0].scrollIntoView();", td);
            try {
                td.click();
            } catch (StaleElementReferenceException w) {
//                driver.navigate().refresh();
                Thread.sleep(10000);
                try {
                    td.click();
                } catch (UnhandledAlertException f) {
                    try {
                        Alert alert = driver.switchTo().alert();
                        String alertText = alert.getText();
                        System.out.println("Alert data: " + alertText);
                        alert.accept();
                    } catch (NoAlertPresentException e) {
                        e.printStackTrace();
                    }
                } catch (StaleElementReferenceException j) {
                    Thread.sleep(10000);
                    td.click();
                }
            }

            Thread.sleep(10000);

//            WebElement dataTable = driver.findElementById("ContentPlaceHolder_gvSearchResults");
            WebElement dataTable = driver.findElementByXPath("/html/body/div[1]/div[3]/div[1]/table/tbody/tr/td/form/table/tbody/tr[3]/td/table/tbody/tr[1]/td/table/tbody/tr[4]/td/div/table/tbody/tr[3]/td/div/table/tbody");
            for (WebElement tr : dataTable.findElements(By.tagName("tr"))) {//
                String dataRow = tr.getAttribute("class");
                if (dataRow.equalsIgnoreCase("GVRow") || dataRow.equalsIgnoreCase("GVAltRow")) {
//                    jse.executeScript("arguments[0].scrollIntoView();", tr);
//                    tr.findElement(By.tagName("a")).click();
//                String dataLink = tr.findElement(By.tagName("a")).getAttribute("href");
                    dataUrls.add(tr.findElement(By.tagName("a")).getAttribute("href"));

                }
            }
            System.out.println("**********" + dataUrls.size());
            for (String dataUrl : dataUrls) {
                System.out.println(dataUrl);
            }

            Thread.sleep(10000);


        }

//        WebElement contentTable = driver.findElementById("ContentPlaceHolder_dvPerson");
//        for (WebElement element : contentTable.findElements(By.tagName("tr"))) {
//            element.findElements(By.tagName("td")).get(1).getAttribute("innerText");
//            System.out.println(element.findElements(By.tagName("td")).get(0).getAttribute("innerText") + "  " + element.findElements(By.tagName("td")).get(1).getAttribute("innerText"));
//        }


        return list;

    }

//    private static void createXlsFile(List<SiteRow> siteRowList, String sheetName, Workbook workbook, boolean extra) {
//        CreationHelper createHelper = workbook.getCreationHelper();
//        Sheet sheet = workbook.createSheet(sheetName);
//        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerFont.setFontHeightInPoints((short) 14);
//        headerFont.setColor(IndexedColors.RED.getIndex());
//
//        CellStyle headerCellStyle = workbook.createCellStyle();
//        headerCellStyle.setFont(headerFont);
//
//        Row headerRow = sheet.createRow(0);
//
//        ArrayList<String> list = new ArrayList();
//
//        for (int i = 0; i < 6; i++) {
//            list.add("Descrizione" + (i + 1));
//        }
//
//        list.add("Tiposcommessa1");
//        list.add("Tiposcommessa2");
//        list.add("Data");
//        list.add("Giorno");
//        list.add("Orario");
//        list.add("Squadra 1");
//        list.add("Squadra 2");
//        list.add("Uno");
//        list.add("Uno Links");
//        list.add("x");
//        list.add("x Links");
//        list.add("Due");
//        list.add("Due Links");
//        if (extra) {
//            list.add("Under 2.5");
//            list.add("Over 2.5");
//        }
//
//        // Create cells
//        for (int i = 0; i < list.size(); i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(list.get(i));
//            cell.setCellStyle(headerCellStyle);
//        }
//
//
//        // Create Cell Style for formatting Date
//        CellStyle dateCellStyle = workbook.createCellStyle();
//        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
//
//        // Create Other rows and cells with employees data
//        int rowNum = 1;
//        int max = 6;
//        for (SiteRow siteRow : siteRowList) {
//            Row row = sheet.createRow(rowNum++);
//            int i = 0;
//            if (checkEmptyRow(siteRow)) {
//                row.createCell(0).setCellValue(siteRow.getDescrizione_1());
//                row.createCell(1).setCellValue(siteRow.getDescrizione_2());
//                row.createCell(2).setCellValue(siteRow.getDescrizione_3());
//                row.createCell(max).setCellValue(siteRow.getTipo_Scommessa_1());
//                row.createCell(max + 1).setCellValue(siteRow.getTipo_Scommessa_2());
//                row.createCell(max + 1 + 1).setCellValue(siteRow.getDate());
//                row.createCell(max + 1 + 2).setCellValue(siteRow.getGiorno());
//                row.createCell(max + 1 + 3).setCellValue(siteRow.getOrario());
//                row.createCell(max + 1 + 4).setCellValue(siteRow.getSquadra_1());
//                row.createCell(max + 1 + 5).setCellValue(siteRow.getSquadra_2());
//                row.createCell(max + 1 + 6).setCellValue(siteRow.getUnoListAsString());
//                row.createCell(max + 1 + 7).setCellValue(siteRow.getUnoLinkListAsString());
//                row.createCell(max + 1 + 8).setCellValue(siteRow.getXListAsString());
//                row.createCell(max + 1 + 9).setCellValue(siteRow.getXLinkListAsString());
//                row.createCell(max + 1 + 10).setCellValue(siteRow.getDueListAsString());
//                row.createCell(max + 1 + 11).setCellValue(siteRow.getDueLinkListAsString());
//                row.createCell(max + 1 + 12).setCellValue(siteRow.getPercListAsString());
//                if (extra) {
//                    row.createCell(max + 1 + 12).setCellValue(siteRow.getExtra1AsString());
//                    row.createCell(max + 1 + 13).setCellValue(siteRow.getExtra2AsString());
//                }
//            }
//        }
//
//        for (int i = 0; i < list.size(); i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//    }

//    private static boolean checkEmptyRow(SiteRow siteRow) {
//        if (siteRow.getDate() == null && siteRow.getSquadra_1() == null && siteRow.getTipo_Scommessa_1() == null) {
//            return false;
//        } else {
//            return true;
//        }
//    }

}
