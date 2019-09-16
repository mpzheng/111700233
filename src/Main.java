package addressbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	public static void main(String[] args) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fis = new FileInputStream(args[0]);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			fos = new FileOutputStream(args[1]);
			osw = new OutputStreamWriter(fos, "UTF-8");
			bw = new BufferedWriter(osw);
			String line=br.readLine();
			bw.write("["+AddressBasic.addrConvert(line));
			while ((line = br.readLine()) != null)
			{
				bw.write(",");
				bw.newLine();
				bw.write(AddressBasic.addrConvert(line));
			}
			bw.write("]");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

}
