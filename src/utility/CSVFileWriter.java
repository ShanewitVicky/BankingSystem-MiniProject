package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.ResultSetMetaData;



public class CSVFileWriter {

	   

	  //Delimiter used in CSV file

	public void convertToCsv(ResultSet rs) throws SQLException, FileNotFoundException {
        PrintWriter csvWriter = new PrintWriter(new File("transaction"+rs.getString(2)+".csv")) ;
        ResultSetMetaData meta = (ResultSetMetaData) rs.getMetaData() ; 
        int numberOfColumns = meta.getColumnCount() ; 
        String dataHeaders = "\"" + meta.getColumnName(1) + "\"" ; 
        for (int i = 2 ; i < numberOfColumns + 1 ; i ++ ) { 
                dataHeaders += ",\"" + meta.getColumnName(i).replaceAll("\"","\\\"") + "\"" ;
        }
        csvWriter.println(dataHeaders) ;
        while (rs.next()) {
            String row = "\"" + rs.getString(1).replaceAll("\"","\\\"") + "\""  ; 
            for (int i = 2 ; i < numberOfColumns + 1 ; i ++ ) {
                row += ",\"" + rs.getString(i).replaceAll("\"","\\\"") + "\"" ;
            }
        csvWriter.println(row) ;
        }
        csvWriter.close();
    }

	}

