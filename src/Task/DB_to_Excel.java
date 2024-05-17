package Task;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DB_to_Excel {

	private static Logger logger = Logger.getLogger(DB_to_Excel.class);

	static {
		try {
			PropertyConfigurator.configure(
					"C:\\Users\\SKTS_Admin_02\\eclipse-workspace\\Log4j_Task\\Log4j Properties Files\\Loggers.properties");
		} catch (Exception e) {
			
			logger.debug("Exception Occurred....");
		}
	}

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);

	//	File file = new File("./DBdetails.properties");
		File file = new File("C:\\Users\\SKTS_Admin_02\\eclipse-workspace\\Log4j_Task\\DBConnections\\DBdetails.properties");

		FileInputStream fileInput = new FileInputStream(file);

		Properties properties = new Properties();
		properties.load(fileInput);
		String url = properties.getProperty("url");
		String userName = properties.getProperty("username");
		String passWord = properties.getProperty("password");

		try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
			String sql = "SELECT * FROM stdDetails";

			Statement statement = connection.createStatement();

			ResultSet result = statement.executeQuery(sql); // Passing the data reference inside ResultSet

			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Data");   // Creating the WorkSheet 

			Row headerRow = sheet.createRow(0); // Heading of the Columns  in Excel Sheet
			for (int i = 1; i <= columnCount; i++) {
				headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
			}

			int rowNum = 1;
			System.out.println("How Many Rows You Want..");
			int userChoice = scanner.nextInt();
			while (result.next()) {
				Row row = sheet.createRow(rowNum++);
				for (int i = 1; i <= columnCount; i++) {
					row.createCell(i - 1).setCellValue(result.getString(i));

				}
				if (rowNum >= userChoice) {
					break;
				}

			}

			FileOutputStream outputStream = new FileOutputStream(
					"C:\\Users\\SKTS_Admin_02\\eclipse-workspace\\Log4j_Task\\Documents\\DB to Excel.xlsx");
			workbook.write(outputStream);

			// Closing statements

			workbook.close();
			statement.close();
			scanner.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Data Inserted into Excel Sheet Successfully.....................");
	}

}