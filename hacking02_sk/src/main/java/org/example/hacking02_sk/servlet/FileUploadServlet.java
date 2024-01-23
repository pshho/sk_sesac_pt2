package org.example.hacking02_sk.servlet;



/* 파일 업로드 시 이미지일 경우 사진보여줌

 * 저장경로 파일이름등을 보여줌.
 * 
 * */
import java.io.IOException;



import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;
import org.example.hacking02_sk.model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;  

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection connection; 
    //PreparedStatement preparedStatement;
    Statement statement;
    //ResultSet resultSet;   
    Board board = new Board();
    String db_file_upload_path="";
    int 중복카운트 = 0;
	String 프젝이름 = "School_Project";
	String 루트디렉 = "E:/C_drive_backup/tool/my_eclipse/eclipse-workstation/"+프젝이름+"/src/main/webapp";
    String 빌드경로 = 루트디렉 + "/fileupload/";  
    String param_mypriority;
    ServletContext servletContext;

    @Override
    public void init() throws ServletException {
    	super.init();
    }
      
    public boolean f_check_valid(String string) {
    	if(string != null && !string.equals("null") && !string.equals("")) return true;  
    	return false;
    }
    
    @SuppressWarnings("all")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//    multipart/form-data 는  post 로 보냄 (파람값은 못보냄. getParameter 불가.) 때문에 <form action=servlet?name=value> 로 보내줘야됨.
    	servletContext = getServletContext();
    	request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		
		PrintWriter printWriter = response.getWriter();
		
		Enumeration<String> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()) {
			String name속값 = enumeration.nextElement();
			System.out.println(name속값 + " = " + URLDecoder.decode(request.getParameter(name속값),"UTF-8"));
		}
		param_mypriority = request.getParameter("mypriority");
		
		if(f_check_valid(param_mypriority)) {  
			
			int int_mypriority = Integer.parseInt(  param_mypriority ) + 1;    // 
			
			servletContext.setAttribute("mypriority", String.format("%d", int_mypriority)  ); // 아이디 같을 시 update 때 게시판 중복쓰면 파일경로 업데이트 중첩됨. 			
		}  
		  
		try {
			
			response.getWriter().println("get수행됨");  
			if(request.isRequestedSessionIdValid()) {	 
				 board.setMyip( new String(request.getRemoteAddr().getBytes("utf-8")) );
				 HttpSession httpSession = request.getSession();  
				 board.setMyid(    ((User)httpSession.getAttribute("user")).getMyid()  ); 
				 board.setMysubject(new String(URLDecoder.decode(request.getParameter("mysubject"),"UTF-8").getBytes("utf-8")));
				 board.setMycontent(new String( URLDecoder.decode(request.getParameter("mycontent"),"UTF-8").getBytes("utf-8")));
				 
				 //null
				 board.setMytext(new String( URLDecoder.decode(request.getParameter("mytext"),"UTF-8").getBytes("utf-8") ));
				 
				 
				response.getWriter().println(
						board.getMyip()+board.getMyid()+board.getMysubject()+board.getMycontent()+board.getMytext()
				);  
			    System.out.println("user 값 들어갔는지 확인 => "+board.getMyip()+board.getMyid()+board.getMysubject()+board.getMycontent()+board.getMytext());
			    
			    Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://mydatabase.coysatc2jipz.ap-northeast-2.rds.amazonaws.com:3306/myhacking",
						"myhack",
						"1234"
				);
				
				/* 보안함수 적용
				 * preparedStatement = connection.
				 * prepareStatement("insert into myboard(mydate,mypriority,myreadcount,mycontent,myip,myid,mysubject,myfilepath,mytext) values(?,?,?,?,?,?,?,?,?)"
				 * ); preparedStatement.setString(1, null); preparedStatement.setObject(2, 0);
				 * preparedStatement.setInt(3, 0); //myreadcount
				 * 
				 * preparedStatement.setString(4, board.getContent());
				 * preparedStatement.setString(5, board.getIp());
				 * preparedStatement.setString(6, board.getMyid());
				 * preparedStatement.setString(7, board.getMysubject());
				 * preparedStatement.setString(8, null); preparedStatement.setString(9,
				 * board.getMytext()); preparedStatement.executeUpdate();
				 * preparedStatement.close();
				 */  
				this.statement = connection.createStatement();
				String _rewriter = request.getParameter("_rewriter"); 
				  
				String updates="";
				
				if(f_check_valid(_rewriter) && !_rewriter.equals("true")) {   
					updates="insert into myboard(mydate,mypriority,myreadcount,mycontent,myip,myid,mysubject,myfilepath,mytext) values("
							+ "now()" +", " + 0 + "," + 0 + ",'" + board.getMycontent()+ "', '" + board.getMyip()+"', '"+board.getMyid() + "', '"+ board.getMysubject()+"', " + null+ ", '" + board.getMytext()+"'); ";
					if(this.statement.executeUpdate(updates) >= 1) {
						System.out.println("insert into 성공함!! => "+updates); 
						
					}else {  
						System.out.println(updates);
					}					
				}
				
				if(f_check_valid(_rewriter) && _rewriter.equals("true") && f_check_valid(param_mypriority)) { // 게시판 수정
					updates = "update myboard"
							+ " set mycontent=" + "'" + board.getMycontent()+"'"+ ", " + "myip=" + "'" + board.getMyip() + "'" + ", " + "mysubject=" + "'" + board.getMysubject() + "'" + ", " + "mytext=" + "'" + board.getMytext() + "'"
							+ " where mypriority="+param_mypriority+";";  
					if(this.statement.executeUpdate(updates) >= 1) {
						System.out.println("update 성공함!! => "+updates);   
						printWriter.println("true");
					}else {  
						System.out.println(updates);
						printWriter.println("false");
					}
				}
				
    		    connection.close();
			} else {
				response.getWriter().println("<script>");
				response.getWriter().println("alert('로그인 후 이용해주세요.');"); 
				// /School_Project/___ClientPage/loginWindow.jsp       
				response.getWriter().println("location.href='/School_Project/___ClientPage/loginWindow.jsp';");
				response.getWriter().println("</script>");
				response.getWriter().flush();    
				
			} 
			
		} catch (Exception e) {
			System.out.println("예외 발생 코드 :: \n\t");
			e.printStackTrace();
			
			response.getWriter().println(e.getMessage()+e.toString()); 
			
			
		} 
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NullPointerException {
    	//클라한테 요청올 시 get post 둘다 수행됨. 때믄에 user 에 담은것임.
    	servletContext = getServletContext(); 
    	request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8"); 
		
        Enumeration<String> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()) {
			String name속값 = enumeration.nextElement();
			System.out.println(URLDecoder.decode(request.getParameter(name속값),"UTF-8"));
		}
		
		
		
        PrintWriter printWriter = response.getWriter();
        if(request.isRequestedSessionIdValid()) {
        	 try {
        		 param_mypriority = request.getParameter("mypriority");
                 
        		 빌드경로 = request.getSession().getServletContext().getRealPath("/").replace("webapp\\", "") + "resources\\static\\fileupload\\";
                 System.out.println("경로 : \n\n\t\t"+빌드경로);           		 
                 // 파일업로드 경로를 웹 퍼블릭 경로가 아닌 로컬경로로 지정하여 확장자 취약점 보안해야 함. 일부러 취약하게 퍼브
                     // -"C://Users/leehyunho/Desktop/VMwareSharedFolder/webFileUpload/"
                 //1. 메모리나 파일로 업로드 파일 보관하는 FileItem의 Factory 설정
                 DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory(); //디스크 파일 아이템 펙토리 생성자 호출
                 diskFileItemFactory.setSizeThreshold(4096 * 5); //업로드시 사용할 임시 메모리 사이즈 지정
                 diskFileItemFactory.setRepository( // 셑 저장소를
                 		new File(빌드경로) //"C://Users/leehyunho/Desktop/VMwareSharedFolder/webFileUpload/"
                 ); //임시저장폴더
                 
                 
                   
                 
                 //2. 업로드 요청을 처리하는 ServletFileUpload생성
                 ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
                 servletFileUpload.setSizeMax(8 * 1024 * 1024); //8MB : 전체 최대 업로드 파일 크기 
                 
                 @SuppressWarnings("unchecked") // 경고무시
                 //3. 업로드 요청파싱해서 FileItem 목록구함​​
                 List<FileItem> list = servletFileUpload.parseRequest(request); 

                 
                 for(Iterator iterator=list.iterator(); iterator.hasNext();) {
                 	FileItem fileItem = (FileItem) iterator.next(); //아이템 얻기
                     //4. FileItem이 폼 입력 항목인지 여부에 따라 알맞은 처리
                    if(fileItem.isFormField()){ //파일이 아닌경우
                    	System.out.println(" 필드 = " + fileItem.getFieldName() + " \n 파일아이템_키 = " + fileItem.getString("UTF-8"));    
                    } else { //파일인 경우
                        file_upload(printWriter, fileItem, 빌드경로);
                    }
                 }
                 
                 //resultSet.close();    
     		     //preparedStatement.close();  
     		     connection.close();    
     		     System.out.println("처리완료됨.");  
     		     
     		     response.getWriter().println("<script>location.href='/jsp/___NoticeBoard_List';</script>");    
     		    
             } catch(Exception e) { // 무시 ㄱ      
             	 System.out.println(e.getMessage()); 
             	 response.getWriter().println(e.getMessage()); 
             	 e.printStackTrace();
             }
        	
        }
       
    }
    

    //업로드한 정보가 파일인경우 처리
    private void file_upload(PrintWriter printWriter, FileItem fileItem, String 빌드경로 ) throws Exception {
        String 필드이름 = fileItem.getFieldName(); //파일의 필드 이름 얻기 즉 name속값임.
        String 파일이름 = fileItem.getName(); //파일명 얻기
        long 사이즈 = fileItem.getSize(); //파일의 크기 얻기
        
        System.out.println("필드 = "+필드이름+"\n 파일 = "+파일이름+"\n 사이즈="+사이즈);
        
        /* 보안적용(1)
        
        String 확장자=".확장자";  
        if( 파일이름 != null && !파일이름.equals("")) {
        	if(파일이름.split("\\.").length <= 2) {
        		확장자="."+파일이름.split("\\.")[1];
        	}else {
        		확장자="."+파일이름.split("\\.")[ 파일이름.split("\\.").length - 1];
        	}
        }        
        System.out.println("확장자 = "+확장자); 
        
        //업로드 파일명을 현재시간으로 변경후 저장
        
        String 년월일시분초=new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.KOREA).format(System.currentTimeMillis()).toString();
        
        String 년월일시분초_아이디_확장자 = 년월일시분초 +"_"+board.getMyid()+"_"+확장자;    
        System.out.println("변경된파일이름 = "+년월일시분초_아이디_확장자);   
        */
        
        //저장할 절대 경로로 파일 객체 생성 
        //웹에 노출되는 디렉토리가 아닌 로컬 디렉토리에 파일을 업로드 시켜 취약점 막음.
         
        /* 보안적용(1)
        String 업다운경로_년월일시분초_아이디_확장자 = 업다운경로 + 년월일시분초_아이디_확장자; // 파일 이름을 변경시켜 취약점 막음.
        
        System.out.println("절대경로 = "+업다운경로_년월일시분초_아이디_확장자);  
        
        board.setMyfilepath(업다운경로_년월일시분초_아이디_확장자);
        File file = new File(업다운경로_년월일시분초_아이디_확장자);         
        */
        board.setMyfilepath(빌드경로 + 파일이름);
        File file = new File(빌드경로 + 파일이름);  
        
        
        /* 보안적용(1)
        if(file.exists()) { 
        	file = new File(업다운경로_년월일시분초_아이디_확장자 + "_" + this.중복카운트++);   
        }
        */
        if(file.exists()) { 
        	file = new File( 빌드경로 + 파일이름.replace(파일이름 , (this.중복카운트++) + "_" + 파일이름) );         
        }        
        
        if(!file.exists() || !file.canRead() || !file.canWrite() || !file.canExecute()) {
        	file.createNewFile();  
        	file.setExecutable(true); file.setReadable(true); file.setWritable(true);
        	
        	fileItem.write(file); //(1)file 에 씀.        저장
        	
        }else {
        	System.out.println(" !file.exists() || !file.canRead() || !file.canWrite() || !file.canExecute() ");
        	System.out.println("!!!!!!!!!!!!!!!!! 파일없음 !!!!!!!!!!!!!!!!!");
        	
        	fileItem.write(file); //(1)file 에 씀.        저장 
        }

    	
    	
        Class.forName("com.mysql.jdbc.Driver");   
		connection = DriverManager.getConnection(
			"jdbc:mysql://mydatabase.coysatc2jipz.ap-northeast-2.rds.amazonaws.com:3306/myhacking",
			"myhack",
			"1234"
		);
		//preparedStatement = connection.prepareStatement("update myboard set myfilepath=? where myid=?");
		Statement statement = connection.createStatement();
		 
		
		ResultSet resultSet = statement.executeQuery("select myfilepath from myboard where myid=" + "'"+board.getMyid()+"'" + " and " + "mypriority=" + servletContext.getAttribute("mypriority") + ";");
		
		String getmyfilepath = "",getmyfilepath2="";
		while(resultSet.next()) {
			getmyfilepath=resultSet.getString("myfilepath"); 
		}
		if(getmyfilepath != null) {getmyfilepath2 += getmyfilepath; }
		
		
		if(파일이름 != null && !파일이름.equals("")) {   
			this.db_file_upload_path = getServletContext().getInitParameter("upload.path")+파일이름+";";          
			//일부러 취약하게 설정. 원래는 change_filename 이어야 함. 그래야 ldap가 취약하지 않음.  
			  
			//preparedStatement.setString(1, board.getMyfilepath());      
			 
			System.out.println("servletContext.getAttribute(\"mypriority\") = "+servletContext.getAttribute("mypriority"));
			 
			
			System.out.println("update myboard set myfilepath=" +  getmyfilepath2 + this.db_file_upload_path + " where myid=" + board.getMyid() + " and " + "mypriority=" + servletContext.getAttribute("mypriority") + ";"); 
			
			if(statement.executeUpdate("update myboard set myfilepath=" + ("'" + getmyfilepath2 + this.db_file_upload_path + "'") + " where myid=" + "'" + board.getMyid() + "'" + " and " + "mypriority=" + "'" + servletContext.getAttribute("mypriority") + "'" + ";") >= 1) {
				board.setMyfilepath(this.db_file_upload_path);
				System.out.println("디비 파일 업로드된 경로 = " + board.getMyfilepath());     
			};     
			 
			
		}else {
			if(statement.executeUpdate("update myboard set myfilepath=" + "'"+this.db_file_upload_path+"'" + " where " + "mypriority=" + servletContext.getAttribute("mypriority") + ";") >= 1) {        
				board.setMyfilepath(this.db_file_upload_path);      
				System.out.println("디비 파일 업로드된 경로 = " + board.getMyfilepath()); 
			} 
			//preparedStatement.setString(1, filename); // null 업로드가능
		}
		
		//preparedStatement.setString(2, board.getMyid());     
	    //preparedStatement.executeUpdate();
        
	    //preparedStatement.close();
		
		
		
		statement.close();
	    connection.close();
	    
	    /* 보안적용(1)
        System.out.println("myfilepath => "+업다운경로_년월일시분초_아이디_확장자);
        System.out.println("\n 업로드된 파일 = "+업다운경로_년월일시분초_아이디_확장자);  
	    */
        System.out.println("myfilepath => "+파일이름);
        System.out.println("\n 업로드된 파일 = "+파일이름);      
        
        
        
    }
    
}