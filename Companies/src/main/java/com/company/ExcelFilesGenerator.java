package com.company;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;


public class ExcelFilesGenerator {
    Workbook workbook = new XSSFWorkbook();
    CreationHelper createHelper = workbook.getCreationHelper();
    Sheet sheet;
    Font headerFont = workbook.createFont();

    private static String shop_columns[] = {"Id", "Nazwa", "Owner", "Powierzchnia", "Wolna powierzchnia"};
    private static String subject_columns[] = {"Id", "Nazwa", "Cena", "Powierzchnia"};

    ExcelFilesGenerator(Magazyn magazyn, File file) throws IOException {
        this.sheet = workbook.createSheet("Magazyny");
        fileGenerator(magazyn, file);
    }

    ExcelFilesGenerator(Przedmiot product, File file) throws IOException {
        this.sheet = workbook.createSheet("Product");
        fileGenerator(product, file);
    }


    public void fileGenerator(Magazyn magazyn, File file) throws IOException {
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < shop_columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(shop_columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;

        for(Magazyn listaMagazynow : Magazyn.listaMagazynow){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(listaMagazynow.id);
            row.createCell(1).setCellValue(listaMagazynow.nazwa);
            row.createCell(2).setCellValue(listaMagazynow.owner.username);
            row.createCell(3).setCellValue(listaMagazynow.powierzchnia);
            row.createCell(4).setCellValue(listaMagazynow.getFreeSpace(listaMagazynow));

        }

        for(int i = 0; i < shop_columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    public void fileGenerator(Przedmiot przedmiot, File file) throws IOException {
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < subject_columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(subject_columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;

        for(Przedmiot listaPrzedmiotuw : Przedmiot.listaRzerzej){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(listaPrzedmiotuw.id);
            row.createCell(1).setCellValue(listaPrzedmiotuw.nazwa);
            row.createCell(2).setCellValue(listaPrzedmiotuw.cena);
            row.createCell(3).setCellValue(listaPrzedmiotuw.powierzchnia);
        }

        for(int i = 0; i < subject_columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    public static void readFromExcel(Magazyn magazyn, File file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.rowIterator();
        int count = 0;
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            String string[] = new String[5];
            int i = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                string[i] = cellValue;
                i++;
            }
            if(count != 0){
                String nazwa = "";
                int powierzchnia = 0;
                User ownew = null;

                for (int j = 0; j < i; j++){
                    switch (j){
                        case 1:
                            nazwa = string[j];
                            System.out.println(nazwa);
                            break;
                        case 2:
                            ownew = User.getUserByUsername(string[j]);
                            break;
                        case 3:
                            powierzchnia = Integer.parseInt(string[j]);
                            System.out.println(powierzchnia);
                            break;
                    }
                }
                new Magazyn(nazwa, powierzchnia, ownew);
            }
            count++;
        }
    }


    public static void readFromExcel(Przedmiot przedmiot, File file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.rowIterator();
        int count = 0;
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            String string[] = new String[5];
            int i = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                string[i] = cellValue;
                i++;
            }

            if(count != 0){
                String nazwa = "";
                int powierzchnia = 0;
                int cena= 0;

                for (int j = 0; j < i; j++){
                    switch (j){
                        case 1:
                            nazwa = string[j];
                            break;
                        case 2:
                            cena = Integer.parseInt(string[j]);
                            break;
                        case 3:
                            powierzchnia = Integer.parseInt(string[j]);
                            break;
                    }
                }
                new Przedmiot(nazwa, powierzchnia, cena);
            }
            count++;
        }
    }
}
