package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import model.Transaction;
import model.User;


public class CreateReport {
	
	private Date dateforCell;
	private SimpleDateFormat format=new SimpleDateFormat("DD-MMM-YYY");
	private String dateString;

	/**
	 * 
	 */
	public CreateReport(ArrayList <Transaction> transactions,User user) {
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("Customer Activity");
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyymmddhhmmss");
		Date date=new Date();
		
		String dateValue=sdf.format(date);
		sheet.addMergedRegion(new CellRangeAddress(0,0,1,7));
		
		
		CellStyle headerStyle=workbook.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		Font headerFont=workbook.createFont();
		headerFont.setFontName(HSSFFont.FONT_ARIAL);
		headerFont.setBold(true);
		headerFont.setFontHeight((short)400);
		
		headerStyle.setFont(headerFont);
		
		
		Row reportTitle=sheet.createRow(0);
		
		reportTitle.createCell(1).setCellValue("Customer Activity Report");
		for(int i=0;i<=8;i++) {
			if(i!=1)
		reportTitle.createCell(i);		
		reportTitle.getCell(i).setCellStyle(headerStyle);
		}
		
		
		
		
		
		Row reportDetail=sheet.createRow(1);
		reportDetail.createCell(0).setCellValue("Run By:");
		reportDetail.createCell(1).setCellValue(user.getFirstName());
		reportDetail.createCell(2).setCellValue("Role:");
		reportDetail.createCell(3).setCellValue(user.getRole());
		reportDetail.createCell(6).setCellValue("Run On");
		reportDetail.createCell(7).setCellValue("Date:"+new SimpleDateFormat("DD-MMM-YY").format(new Date()));
		reportDetail.createCell(8).setCellValue("Time:"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
		
		
		CellStyle dtlStyle=workbook.createCellStyle();
		Font detailFont=workbook.createFont();
		detailFont.setFontName(HSSFFont.FONT_ARIAL);
		detailFont.setFontHeight((short)150);
		dtlStyle.setFont(detailFont);
		reportDetail.setRowStyle(dtlStyle);
		
		
		
		
		
		Row heading=sheet.createRow(2);
		heading.createCell(0).setCellValue("Transaction ID");
		heading.createCell(1).setCellValue("Account ID");
		heading.createCell(2).setCellValue("User ID");
		heading.createCell(3).setCellValue("Date Of Transaction");
		heading.createCell(4).setCellValue("Time of Transaction");
		heading.createCell(5).setCellValue("Transaction Description");
		heading.createCell(6).setCellValue("Transaction Type");
		heading.createCell(7).setCellValue("Amount");
		heading.createCell(8).setCellValue("Balance");
		
		int r=3;
		for (Transaction trans : transactions) {
			
			Row row=sheet.createRow(r);
			Cell transId=row.createCell(0);
			transId.setCellValue(trans.getTransactionId());
			
			
			Cell accountId=row.createCell(1);
			accountId.setCellValue(trans.getAccountId());

			Cell usr_id=row.createCell(2);
			usr_id.setCellValue(trans.getUsrId());
			
			
			dateforCell=trans.getDateOfTransaction();
			dateString=format.format(dateforCell);
			Cell dateOfTrans=row.createCell(3);
			dateOfTrans.setCellValue(dateString);
			
			
			Cell time=row.createCell(4);
			time.setCellValue(trans.getTimeOfTrans());
			
			Cell trans_desc=row.createCell(5);
			trans_desc.setCellValue(trans.getTransDesc());
			
			Cell trans_type=row.createCell(6);
			trans_type.setCellValue(trans.getTransType());
			
			Cell amount=row.createCell(7);
			amount.setCellValue(trans.getAmount());
			
			Cell balance=row.createCell(8);
			balance.setCellValue(trans.getBalance());
			
			CellStyle styleCell=workbook.createCellStyle();
			styleCell.setBorderBottom(BorderStyle.THIN);
			styleCell.setBorderTop(BorderStyle.THIN);
			styleCell.setBorderLeft(BorderStyle.THIN);
			styleCell.setBorderRight(BorderStyle.THIN);
			
			for(int i=0;i<=8;i++) {
			row.getCell(i).setCellStyle(styleCell);
			}
			r++;
		}
		
		
		for(int i=0;i<=8;i++) {
			
		
			
			
			CellStyle styleHeading=workbook.createCellStyle();
			styleHeading.setAlignment(HorizontalAlignment.CENTER);
			styleHeading.setBorderBottom(BorderStyle.THIN);
			styleHeading.setBorderTop(BorderStyle.THIN);
			styleHeading.setBorderLeft(BorderStyle.THIN);
			styleHeading.setBorderRight(BorderStyle.THIN);
			

			
			Font font=workbook.createFont();
			font.setBold(true);
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short)11);
			styleHeading.setFont(font);
			heading.getCell(i).setCellStyle(styleHeading);
			
			sheet.autoSizeColumn(i);
			
			//Write into File
		
			
		}
			
	
		
		
		try {
			FileOutputStream fo=new FileOutputStream(new File("C:\\Users\\Shane\\Desktop\\Study Materials\\Minor Project Java\\Reports\\Transactions_"+dateValue+".xls"));
			workbook.write(fo);
			fo.close();
			workbook.close();
			System.out.println("File Created Succesfully");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	/*public static void main(String[] args) {
		
		ArrayList<Transaction> transactions=new ArrayList<>();
		
		User user=new User(11, "Shane", "qwert", "Vignesh", "Sivakumar", "Vignesh200892@gmail.com", "927748387", "92647387", "Rouen", new Date(), "Customer");
		
		transactions.add(new Transaction(12, 4772236, 42, new Date(), "15:36:42", "Deposit", "Credit", 300, 1367));
		
		new CreateReport(transactions,user);
		
	}*/
	
	
	

}



