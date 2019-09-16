package addressbook;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AddressJudge {
	public static String addrFill(String addr){
		String ans=null;
		AddressJudge hello = new AddressJudge();
		InputStream is=null;
		InputStreamReader reader=null;
		BufferedReader br=null;
		try {
			is = hello.getClass().getResourceAsStream("address2.txt");
			reader = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(reader);
			ArrayList<String> list=new ArrayList<String>();
			String s;
			while((s=br.readLine())!=null){
				s=s.substring(7,s.length()).trim();
				list.add(s);
			}
			for(int i=0;i<list.size();i++) {
				if(list.get(i).substring(0,2).equals(addr)) {
					ans=list.get(i);
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ans;
	}
}
