package utilities;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import domain.BoxInvoice;
import domain.BoxReserve;
import domain.Brother;
import domain.CostumeInvoice;
import domain.CostumeReserve;
import domain.Registration;
import domain.RegistrationInvoice;
import domain.Viewer;

public class PDFBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		Brother brother = (Brother) model.get("brother");
		Viewer viewer = (Viewer) model.get("viewer");
		CostumeInvoice costumeInvoice = (CostumeInvoice) model
				.get("costumeInvoice");
		RegistrationInvoice registrationInvoice = (RegistrationInvoice) model
				.get("registrationInvoice");
		BoxInvoice boxInvoice = (BoxInvoice) model.get("boxInvoice");

		// Fuente para el titulo del documento
		Font fontH = FontFactory.getFont(FontFactory.HELVETICA);
		fontH.setColor(BaseColor.BLACK);
		fontH.setSize(22);

		// Fuente para el titulo del documento
		Font fontH2 = FontFactory.getFont(FontFactory.HELVETICA);
		fontH.setColor(BaseColor.BLACK);
		fontH.setSize(18);

		// Fuente para todo el texto
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.BLACK);

		doc.add(new Phrase("Acme-Procession", fontH));

		// Tabla cabecera
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 4.0f, 1.5f, 4.5f });
		table.setSpacingBefore(20);
		table.setSpacingAfter(20);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setPadding(3);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);

		// Primera fila
		cell.setPhrase(new Phrase("", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);
		if (brother != null){
			cell.setPhrase(new Phrase(brother.getName(), font));
		} else if (viewer != null){
			cell.setPhrase(new Phrase(viewer.getName(), font));
		}
		table.addCell(cell);

		// Segunda fila
		if (costumeInvoice != null) {
			cell.setPhrase(new Phrase("Invoice Costume Reserve", fontH2));
		} else if (registrationInvoice != null) {
			cell.setPhrase(new Phrase("Invoice Registration", fontH2));
		} else if (boxInvoice != null) {
			cell.setPhrase(new Phrase("Invoice Box Reserve", fontH2));
		}
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPhrase(new Phrase("Surname", font));
		table.addCell(cell);
		if (brother != null){
			cell.setPhrase(new Phrase(brother.getSurname(), font));
		} else if (viewer != null){
			cell.setPhrase(new Phrase(viewer.getSurname(), font));
		}
		table.addCell(cell);

		// Tercera fila
		cell.setPhrase(new Phrase("", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		if (brother != null){
			cell.setPhrase(new Phrase(brother.getEmail(), font));
		} else if (viewer != null){
			cell.setPhrase(new Phrase(viewer.getEmail(), font));
		}
		table.addCell(cell);

		// Fila de Espacio
		cell.setPhrase(new Phrase("", font));
		cell.setColspan(3);
		table.addCell(cell);
		cell.setColspan(3);
		table.addCell(cell);
		cell.setColspan(3);
		table.addCell(cell);

		doc.add(table);

		if (costumeInvoice != null) {
			CostumeReserve costumeReserve = (CostumeReserve) model
					.get("costumeReserve");

			doc.add(new Paragraph("Name Brotherhood: "
					+ costumeReserve.getCostume().getBrotherhood().getName(),
					font));
			doc.add(new Paragraph("Creation Moment: "
					+ String.valueOf(costumeInvoice.getCreateMoment()), font));
			doc.add(new Paragraph("Type: " + costumeReserve.getType(), font));
			doc.add(new Paragraph("Paid Moment: "
					+ String.valueOf(costumeInvoice.getPaidMoment()), font));
			doc.add(new Paragraph(
					"Total Cost: "
							+ String.valueOf(costumeInvoice.getTotalCost()
									.getAmount()), font));

		} else if (registrationInvoice != null) {
			Registration registration = (Registration) model
					.get("registration");

			doc.add(new Paragraph("Name Brotherhood: "
					+ registration.getProcession().getBrotherhood().getName(),
					font));
			doc.add(new Paragraph("Creation Moment: "
					+ String.valueOf(registrationInvoice.getCreateMoment()),
					font));
			doc.add(new Paragraph("Paid Moment: "
					+ String.valueOf(registrationInvoice.getPaidMoment()), font));
			doc.add(new Paragraph("Total Cost: "
					+ String.valueOf(registrationInvoice.getTotalCost()
							.getAmount()), font));
		} else if (boxInvoice != null) {
			BoxReserve boxReserve = (BoxReserve) model.get("boxReserve");

			doc.add(new Paragraph("Name Box: "
					+ boxReserve.getBoxInstance().getBox().getName(), font));
			doc.add(new Paragraph("Number of chairs reserved: "
					+ boxReserve.getNumbersOfchairs(), font));
			doc.add(new Paragraph("Reserve code: "
					+ boxReserve.getReserveCode(), font));
			doc.add(new Paragraph("Creation Moment: "
					+ String.valueOf(boxInvoice.getCreateMoment()), font));
			doc.add(new Paragraph("Paid Moment: "
					+ String.valueOf(boxInvoice.getPaidMoment()), font));
			doc.add(new Paragraph("Total Cost: "
					+ String.valueOf(boxInvoice.getTotalCost().getAmount()),
					font));
		}

	}
}
