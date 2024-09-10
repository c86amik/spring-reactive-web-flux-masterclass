package com.springcavaj.reactive.utility;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.springcavaj.reactive.dto.ReportFileTrackerDTO;
import com.springcavaj.reactive.dto.ReportProcessFileTrackerDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ReportProcessFilePDFGenerator {

    public byte[] createPDF(List<ReportProcessFileTrackerDTO> reportProcessFileTrackerDTOS) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Report Process - File Tracker Summary").setBold().setFontSize(20));
            for(ReportProcessFileTrackerDTO reportProcessFileTrackerDTO : reportProcessFileTrackerDTOS) {
                document.add(new Paragraph("Report Trace Id: " + reportProcessFileTrackerDTO.getTraceId()).setFontSize(14));
                document.add(new Paragraph("Report Service Name: " + reportProcessFileTrackerDTO.getServiceName()).setFontSize(14));
                document.add(new Paragraph("Report Process Name: " + reportProcessFileTrackerDTO.getProcessName()).setFontSize(14));
                document.add(new Paragraph("Report Path: " + reportProcessFileTrackerDTO.getPath()).setFontSize(14));
                document.add(new Paragraph("Report Created By: " + reportProcessFileTrackerDTO.getCreatedBy()).setFontSize(14));
                document.add(new Paragraph("Report Created Time: " + reportProcessFileTrackerDTO.getCreatedTime()).setFontSize(14));

                List<ReportFileTrackerDTO> reportFileTrackerDTOS = reportProcessFileTrackerDTO.getReportFileTrackers();
                if(!CollectionUtils.isEmpty(reportFileTrackerDTOS)) {
                    document.add(new Paragraph("Child File Trackers").setBold().setFontSize(12).setItalic());
                    int i = 1;
                    for (ReportFileTrackerDTO reportFileTrackerDTO : reportFileTrackerDTOS) {
                        document.add(new Paragraph("Serial No. : " + i).setBold());
                        document.add(new Paragraph("Report Process Id: " + reportFileTrackerDTO.getReportProcessId()).setFontSize(10));
                        document.add(new Paragraph("File Trace Id: " + reportFileTrackerDTO.getTraceId()).setFontSize(10));
                        document.add(new Paragraph("File Name: " + reportFileTrackerDTO.getFileName()).setFontSize(10));
                        document.add(new Paragraph("File Type: " + reportFileTrackerDTO.getFileType()).setFontSize(10));
                        document.add(new Paragraph("File Created By: " + reportFileTrackerDTO.getCreatedBy()).setFontSize(10));
                        document.add(new Paragraph("File Creation Time: " + reportFileTrackerDTO.getCreatedTime()).setFontSize(10));
                        i++;
                    }
                } else {
                    document.add(new Paragraph("No Child File Trackers are there...").setFontSize(12));
                }
                document.add(new Paragraph("\n"));
            }
            document.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
