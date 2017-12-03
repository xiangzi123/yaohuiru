package com.core.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;

public class BaseValidate {
	public static boolean checkDate(String date,String format){
        DateFormat df = new SimpleDateFormat(format);
        Date d = null;
        try{
            d = df.parse(date);
        }catch(Exception e){
            //如果不能转换,肯定是错误格式
            return false;
        }
        String s1 = df.format(d);
        // 转换后的日期再转换回String,如果不等,逻辑错误.如format为"yyyy-MM-dd",date为
        // "2006-02-31",转换为日期后再转换回字符串为"2006-03-03",说明格式虽然对,但日期
        // 逻辑上不对.
        return date.equals(s1);
    }
    public static boolean checkEmail(String email){ 
    	Pattern pattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
    	//Pattern pattern = Pattern.compile("^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return true;
        }
        return false;
    }
    public static boolean checkPhone(String phone){
    	Pattern p = Pattern.compile("^((13[0-9])|(14[5-7])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
    	Matcher m = p.matcher(phone);
    	System.out.println(m.matches()+"---");
    	return m.matches();
}
    
    public static boolean checkContactNum(String str) {  
    	 Pattern p1 = null;  
         Matcher m = null;  
         boolean b = false;    
         p1 = Pattern.compile("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)");  // 验证带区号的  
         m = p1.matcher(str);  
         b = m.matches();             
         return b;  
    }  
    public static boolean IDCardValidate(String IDStr)
    {
	    String errorInfo = "";//记录错误信息
	    String[] ValCodeArr = {"1","0","X","9","8","7","6","5","4","3","2"};
	    String[] Wi = {"7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2"};
	    //String[] Checker = {"1","9","8","7","6","5","4","3","2","1","1"};
	    String Ai="";
	
	    //================ 号码的长度 15位或18位 ================
	    if(IDStr.length()!=15 && IDStr.length()!=18)
	    {
	    	//香港台湾身份证
	    	if(IDStr.length()==10){
	    		char first = IDStr.charAt(0);
	    		if((first>='a'&&first<='z')||(first>='A'&&first<='Z')){
	    			String rest = IDStr.substring(1);
	    			if(isNumeric(rest)) //剩下的都是数字 台湾身份证
	    			{
	    				if(isTWID(IDStr))
	    					return true;
	    				else
	    					return false;
	    			}
	    			else
	    			{
	    				if(isXGID(IDStr))//否则查看是不是香港身份证
	    					return true;
	    				else  
	    					return false;
	    			}
	    		}
	    		else
	    			return false;
	    	} 
	    	else{
			     errorInfo="号码长度应该为15位或18位。";
			     System.out.println(errorInfo);
			     return false;
			     }
	    }
	    //=======================(end)======================== 
	
	
	    //================ 数字 除最后以为都为数字 ================
	    if(IDStr.length()==18)
	    {
	     Ai=IDStr.substring(0,17);
	    }
	    else if(IDStr.length()==15)
	    {
	     Ai=IDStr.substring(0,6)+"19"+IDStr.substring(6,15);   
	    }
	    if(isNumeric(Ai)==false)
	    {
	     errorInfo="15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
	     System.out.println(errorInfo);
	     return false;
	    }
	    //=======================(end)========================


	    //================ 出生年月是否有效 ================
	    String strYear =Ai.substring(6 ,10);//年份
	    String strMonth=Ai.substring(10,12);//月份
	    String strDay        =Ai.substring(12,14);//月份
	
	    if(isDate(strYear+"-"+strMonth+"-"+strDay)==false)
	    {
	     errorInfo="生日无效。";
	     System.out.println(errorInfo);
	     return false;
	    }
	
	    GregorianCalendar gc=new GregorianCalendar();  
	    SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
	    try {
			if((gc.get(Calendar.YEAR)-Integer.parseInt(strYear))>150 || (gc.getTime().getTime()-s.parse(strYear+"-"+strMonth+"-"+strDay).getTime())<0)
			{
			 errorInfo="生日不在有效范围。";
			 System.out.println(errorInfo);
			 return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    if(Integer.parseInt(strMonth)>12 || Integer.parseInt(strMonth)==0)
	    {
	     errorInfo="月份无效";
	     System.out.println(errorInfo);
	     return false;
	    }
	    if(Integer.parseInt(strDay)>31 || Integer.parseInt(strDay)==0)
	    {
	     errorInfo="日期无效";
	     System.out.println(errorInfo);
	     return false;
	    }
	    //================ 地区码时候有效 ================
        Hashtable<String, String> h=GetAreaCode();
        if(h.get(Ai.substring(0,2))==null)
        {
         errorInfo="地区编码错误。";
         System.out.println(errorInfo);
         return false;
        }
        //==============================================
  
  
        //================ 判断最后一位的值 ================
        int TotalmulAiWi=0;
        for(int i=0 ; i<17 ; i++)
        {
         TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue=TotalmulAiWi % 11;
        String strVerifyCode=ValCodeArr[modValue];
        Ai=Ai+strVerifyCode;
  
        if(IDStr.length()==18)
        {   
         if(Ai.equalsIgnoreCase(IDStr)==false)
         {
          errorInfo="身份证无效，最后一位字母错误";
          System.out.println(errorInfo);
          return false;
         }
        }
        else
        { 
         System.out.println("所在地区:"+h.get(Ai.substring(0,2).toString()));
         System.out.println("新身份证号:"+Ai);
         return true;
        }
        //=====================(end)=====================
        System.out.println("所在地区:"+h.get(Ai.substring(0,2).toString()));
        return true;
    }
    
    private static Hashtable<String ,String> GetAreaCode()
    {
            Hashtable<String ,String> hashtable=new Hashtable<String ,String>();
            hashtable.put("11","北京");
            hashtable.put("12","天津");
            hashtable.put("13","河北");
            hashtable.put("14","山西");
            hashtable.put("15","内蒙古");
            hashtable.put("21","辽宁");
            hashtable.put("22","吉林");
            hashtable.put("23","黑龙江");
            hashtable.put("31","上海");
            hashtable.put("32","江苏");
            hashtable.put("33","浙江");
            hashtable.put("34","安徽");
            hashtable.put("35","福建");
            hashtable.put("36","江西");
            hashtable.put("37","山东");
            hashtable.put("41","河南");
            hashtable.put("42","湖北");
            hashtable.put("43","湖南");
            hashtable.put("44","广东");
            hashtable.put("45","广西");
            hashtable.put("46","海南");
            hashtable.put("50","重庆");
            hashtable.put("51","四川");
            hashtable.put("52","贵州");
            hashtable.put("53","云南");
            hashtable.put("54","西藏");
            hashtable.put("61","陕西");
            hashtable.put("62","甘肃");
            hashtable.put("63","青海");
            hashtable.put("64","宁夏");
            hashtable.put("65","新疆");
            hashtable.put("71","台湾");
            hashtable.put("81","香港");
            hashtable.put("82","澳门");
            hashtable.put("91","国外");
            return hashtable;
    }
    
    private static boolean isNumeric(String str)
    {
            Pattern pattern=Pattern.compile("[0-9]*");
            Matcher isNum=pattern.matcher(str);
            if(isNum.matches())
            {
             return true;
            }
            else
            {
             return false;
            } 
    }
    
    private static boolean isDate(String strDate)
    {
            Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
            Matcher m=pattern.matcher(strDate);
            if(m.matches())
            {
             return true;
            }
            else
            {
             return false;
            }
    }
    
    private static boolean isTWID(String IDStr) { 
		char first = IDStr.charAt(0);
		int charNum=10;
		if(first>='a'&&first<='z')
			charNum += first-'a';
		else
			charNum += first-'A';
		int left,right =0;	    				
		right = charNum%10; 
		left = charNum/10;
		char sex = IDStr.charAt(1);
		int sexn =Integer.parseInt(String.valueOf(sex));
		int add = left+right*9+sexn*8;
		for(int i=2;i<9;i++){
			int x=Integer.parseInt(String.valueOf( IDStr.charAt(i)));
			add+=x*(9-i); 
		}
		String result = String.valueOf(add);
		result = result.substring(result.length()-1,result.length());
		String validate;
		if(result.equals("0"))
			validate = "0";
		else
			validate = String.valueOf(10-Integer.parseInt(result));
		int last =Integer.parseInt(String.valueOf(IDStr.charAt(9)));
		if(validate.equals(String.valueOf(last))) 
			return true;
		else
			return false;
	} 
    
    private static boolean isXGID(String IDStr){
    	char first = IDStr.charAt(0);
		int charNum=26;
		if(first>='a'&&first<='z')
			charNum =charNum-('z'- first);
		else
			charNum =charNum-('Z'- first);
		int add =8*charNum;
		for(int i=7;i>=2;i--){
			int n =Integer.parseInt(String.valueOf(IDStr.charAt(8-i)));
			add += n*i;
		}
		
		String last;
		if(add%11==0)
			last=String.valueOf(0);
		else{
			if(11-add%11==10)
				last="A";
			else
				last =String.valueOf(11-add%11);
		}
		
		char left = IDStr.charAt(7);
		char right = IDStr.charAt(9);
		if(left=='('&&right==')'){
			String s = String.valueOf(IDStr.charAt(8)); 
			if(s.equals(last))
				return true;
			else
				return false;
		}
		else
			return false;
    }
}
