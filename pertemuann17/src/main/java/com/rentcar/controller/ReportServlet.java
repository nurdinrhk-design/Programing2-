package com.rentcar.controller;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.rentcar.dao.RentalDAO;
import com.rentcar.model.Rental;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@WebServlet("/report/pdf")
public class ReportServlet extends HttpServlet {
    private RentalDAO rentalDAO;

    @Override
    public void init() throws ServletException {
        rentalDAO = new RentalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Query data
        List<Rental> rentals = rentalDAO.getAllRentals();
        
        // Setup response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=Rental_Transaction_Report.pdf");

        // Formatters
        Locale localeID = new Locale("in", "ID");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeID);
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = LocalDateTime.now().format(dtFormatter);

        // Document layout (A4 Landscape, margin 36pt)
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 36, 36);

        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Fonts configuration
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, new Color(11, 15, 25));
            Font fontSubtitle = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(100, 110, 120));
            Font fontStatsTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new Color(79, 172, 254));
            Font fontStatsValue = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(50, 50, 50));
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);
            Font fontCell = FontFactory.getFont(FontFactory.HELVETICA, 8, new Color(30, 30, 30));
            Font fontCellBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, new Color(30, 30, 30));

            // Header Section
            Paragraph title = new Paragraph("NurdinCar CAR RENTAL", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(4);
            document.add(title);

            Paragraph address = new Paragraph("Jl. Sukajadi No. 12, Bandung | Phone: +62 812-3456-789 | Email: info@NurdinCar.com", fontSubtitle);
            address.setAlignment(Element.ALIGN_CENTER);
            address.setSpacingAfter(4);
            document.add(address);

            Paragraph reportTitle = new Paragraph("TRANSACTION & RENTAL HISTORY REPORT", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(30, 41, 59)));
            reportTitle.setAlignment(Element.ALIGN_CENTER);
            reportTitle.setSpacingAfter(4);
            document.add(reportTitle);

            Paragraph timestamp = new Paragraph("Generated on: " + currentDateTime, fontSubtitle);
            timestamp.setAlignment(Element.ALIGN_CENTER);
            timestamp.setSpacingAfter(20);
            document.add(timestamp);

            // Statistics Summary Box
            int totalCount = rentals.size();
            int activeCount = 0;
            int completedCount = 0;
            double totalRevenue = 0.0;

            for (Rental r : rentals) {
                if ("Rented".equals(r.getStatus())) {
                    activeCount++;
                } else if ("Returned".equals(r.getStatus())) {
                    completedCount++;
                    totalRevenue += r.getTotalPrice();
                }
            }

            PdfPTable statsTable = new PdfPTable(4);
            statsTable.setWidthPercentage(100);
            statsTable.setSpacingAfter(25);
            statsTable.setWidths(new float[]{1, 1, 1, 1});

            addStatsCell(statsTable, "TOTAL TRANSACTIONS", String.valueOf(totalCount), fontStatsTitle, fontStatsValue);
            addStatsCell(statsTable, "ACTIVE RENTALS", String.valueOf(activeCount), fontStatsTitle, fontStatsValue);
            addStatsCell(statsTable, "COMPLETED RENTALS", String.valueOf(completedCount), fontStatsTitle, fontStatsValue);
            addStatsCell(statsTable, "TOTAL REVENUE", currencyFormat.format(totalRevenue), fontStatsTitle, fontStatsValue);

            document.add(statsTable);

            // Main Transactions Table
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setSpacingAfter(15);
            // Column widths: No(5%), Mobil(20%), Customer(15%), Tgl Sewa(10%), Tgl Kembali(10%), Realisasi(10%), Harga/Hari(10%), Denda(10%), Total(10%)
            table.setWidths(new float[]{0.5f, 2.0f, 1.5f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f});

            // Table Headers
            String[] headers = {"No", "Car Details", "Customer", "Rent Date", "Due Date", "Returned", "Price/Day", "Fine", "Total Price"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Paragraph(header, fontHeader));
                cell.setBackgroundColor(new Color(11, 15, 25)); // Deep dark headers
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                cell.setBorderColor(new Color(226, 232, 240));
                table.addCell(cell);
            }

            // Table Body Rows
            int index = 1;
            for (Rental r : rentals) {
                // Background color zebra striping
                Color bg = (index % 2 == 0) ? new Color(248, 250, 252) : Color.WHITE;

                // 1. No
                table.addCell(createCell(String.valueOf(index++), fontCell, bg, Element.ALIGN_CENTER));

                // 2. Mobil
                String carDetails = r.getCar().getBrand() + " " + r.getCar().getModel() + "\n(" + r.getCar().getPlateNumber() + ")";
                table.addCell(createCell(carDetails, fontCell, bg, Element.ALIGN_LEFT));

                // 3. Customer
                String customerDetails = r.getCustomer().getName() + "\n(" + r.getCustomer().getPhone() + ")";
                table.addCell(createCell(customerDetails, fontCell, bg, Element.ALIGN_LEFT));

                // 4. Rent Date
                table.addCell(createCell(r.getRentDate(), fontCell, bg, Element.ALIGN_CENTER));

                // 5. Due Date
                table.addCell(createCell(r.getDueDate(), fontCell, bg, Element.ALIGN_CENTER));

                // 6. Return Date
                String retDate = (r.getReturnDate() != null) ? r.getReturnDate() : "Still Rented";
                Font retFont = (r.getReturnDate() != null) ? fontCell : FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, new Color(239, 68, 68));
                table.addCell(createCell(retDate, retFont, bg, Element.ALIGN_CENTER));

                // 7. Price/Day
                table.addCell(createCell(currencyFormat.format(r.getPricePerDay()), fontCell, bg, Element.ALIGN_RIGHT));

                // 8. Fine
                table.addCell(createCell(currencyFormat.format(r.getFine()), fontCell, bg, Element.ALIGN_RIGHT));

                // 9. Total Price
                double total = "Returned".equals(r.getStatus()) ? r.getTotalPrice() : 0.0;
                String totalText = "Returned".equals(r.getStatus()) ? currencyFormat.format(total) : "-";
                table.addCell(createCell(totalText, fontCell, bg, Element.ALIGN_RIGHT));
            }

            // Summary Row at the bottom
            Color summaryBg = new Color(241, 245, 249);
            PdfPCell lblCell = new PdfPCell(new Paragraph("Total Settled Revenue", fontCellBold));
            lblCell.setColspan(8);
            lblCell.setBackgroundColor(summaryBg);
            lblCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            lblCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            lblCell.setPadding(8);
            lblCell.setBorderColor(new Color(226, 232, 240));
            table.addCell(lblCell);

            PdfPCell valCell = createCell(currencyFormat.format(totalRevenue), fontCellBold, summaryBg, Element.ALIGN_RIGHT);
            table.addCell(valCell);

            document.add(table);

            // Closing Notes
            Paragraph notes = new Paragraph("This report represents a dynamic reflection of all operations. Under standard licensing.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC, new Color(150, 150, 150)));
            notes.setAlignment(Element.ALIGN_CENTER);
            notes.setSpacingBefore(15);
            document.add(notes);

        } catch (DocumentException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        } finally {
            document.close();
        }
    }

    private void addStatsCell(PdfPTable table, String label, String value, Font labelFont, Font valFont) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(248, 250, 252));
        cell.setBorderColor(new Color(226, 232, 240));
        cell.setPadding(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        Paragraph p1 = new Paragraph(label, labelFont);
        p1.setAlignment(Element.ALIGN_CENTER);
        p1.setSpacingAfter(4);
        cell.addElement(p1);

        Paragraph p2 = new Paragraph(value, valFont);
        p2.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p2);

        table.addCell(cell);
    }

    private PdfPCell createCell(String text, Font font, Color bgColor, int alignment) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        cell.setBorderColor(new Color(226, 232, 240));
        return cell;
    }
}
