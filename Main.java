

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		if(args.length<2)
			return;
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
class AddressBasic {
	public static String addrConvert(String addr) throws Exception {
		String degree=addr.substring(0, 1);
		addr=addr.substring(2, addr.length());
		String regexPhone="\\d{11}";
		Pattern p=Pattern.compile(regexPhone);
		Matcher m=p.matcher(addr);
		String phoneNumber=null;
		if(m.find()) {
			phoneNumber=m.group();
		}
		addr=addr.replaceAll(regexPhone, "");
		String[] nameDivide=addr.split("[,]");
		String name=nameDivide[0];
		String address=nameDivide[1];
		String province=null;
		String city=null;
		String county=null;
		String town=null;
		String road=null;
		String number=null;
		int start=0;
		
		m=Pattern.compile("ʡ|������").matcher(address);
		Matcher a=Pattern.compile("����|���|����|�Ϻ�").matcher(address);
		if(m.find()) {
			province="\""+address.substring(0,m.start())+m.group()+"\",";
			start=m.end();
		}
		else if(a.find()) {
			province="\""+address.substring(0,2)+"\",";
		}
		else {
			String judge=AddressJudge.addrFill(address.substring(0,2));
			if(judge!=null) {
				province="\""+judge+"\",";
				m=Pattern.compile("ʡ|������").matcher(judge);
				if(m.find())
					start=m.start();
			}
			else
				province="\"\",";
		}
		
		m=Pattern.compile("��|������|��").matcher(address);
		if(m.find()) {
			city="\""+address.substring(start,m.start())+m.group()+"\",";
			start=m.end();
		}
		else {
			String judge=AddressJudge.addrFill(address.substring(start,start+2));
			if(judge!=null) {
				city="\""+judge+"\",";
				m=Pattern.compile("��|������|��").matcher(judge);
				if(m.find())
					start+=m.start();
			}
			else
				city="\"\",";
		}
		
		m=Pattern.compile("��|��|��|������|��|������|����|����").matcher(address);
		a=Pattern.compile("������").matcher(address);
		Matcher b=Pattern.compile("������|��").matcher(address);
		boolean ok=m.find();
		if(ok&&m.group().equals("��")&&a.find())ok=m.find();
		if(ok&&m.group().equals("��")&&!b.find())ok=m.find();
		if(ok) {
			county="\""+address.substring(start,m.start())+m.group()+"\",";
			start=m.end();
		}
		else county="\"\",";
		
		m=Pattern.compile("�ֵ�|��|��|������|��ľ|������ľ|��Ͻ��").matcher(address);
		if(m.find()) {
			town="\""+address.substring(start,m.start())+m.group()+"\",";
			start=m.end();
		}
		else town="\"\",";
		
		addr="{\"����\":\""+name+"\",\"�ֻ�\":\""+phoneNumber+"\",\"��ַ\":["+province+city+county+town;
		if(degree.equals("1")) {
			if(start!=address.length()-1)
				addr+="\""+address.substring(start,address.length()-1)+"\"]}";
			else
				addr+="\""+"\"]}";
		}
		else if(degree.equals("2")||degree.equals("3")) {
			m=Pattern.compile("·|��|��|��ͬ|��").matcher(address);
			a=Pattern.compile("�ֵ�").matcher(address);
			boolean ok2=m.find();
			if(ok2&&m.group().equals("��")&&a.find())ok2=m.find();
			if(ok2) {
				road="\""+address.substring(start,m.start())+m.group()+"\",";
				start=m.end();
			}
			else road="\"\",";
			
			m=Pattern.compile("��").matcher(address);
			if(m.find()) {
				number="\""+address.substring(start,m.start())+m.group()+"\",";
				start=m.end();
			}
			else number="\"\",";
			
			if(start!=address.length()-1)
				addr+=road+number+"\""+address.substring(start,address.length()-1)+"\"]}";
			else 
				addr+=road+number+"\""+"\"]}";
		}
		return addr;
	}
}
class AddressJudge {
	public static String addrFill(String addr){
		String ans=null;
		AddressJudge hello = new AddressJudge();
		InputStream is=null;
		InputStreamReader reader=null;
		BufferedReader br=null;
		try {
			is = hello.getClass().getResourceAsStream("address.txt");
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

