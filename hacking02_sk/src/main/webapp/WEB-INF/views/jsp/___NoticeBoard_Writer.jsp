
<%@page import="org.example.hacking02_sk.model.User"%>
<%@
	page 
		import="java.net.URLDecoder"    
		import="java.sql.*"
%>



<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<script src="jquery.form.min.js"></script>
	<script type="text/javascript" src="../js/jquery-3.4.1.min.js"></script>
	  
	<script charset="UTF-8" src="../js/MyLibrary.js"></script>	
	<link rel="stylesheet" href="../css/MyGUI.css" />
	<style type="text/css">
		label{
			margin-left: 8cm; 
			font-family: 궁서체; 
			font-weight: bold;
		}
	</style>
	
</head>
<body>
	<jsp:include page="header.jsp" />
	<script>
	<%if( session.getAttribute("user") == null ){%> 
		alert('로그인 후 이용해주세요. 로그인 페이지로 이동합니다.');
		location.href='/member/login';  
	<%}%>
	
	
	window.param1 = {
		method: "POST",  
		url: "/NoticeBoard",      //protocol://hostname:port/pathname?name=value&searchparam#hash     
		setRequestHeader: '', //["요청-헤더","값"] setRequestHeader 는 for(var 키 in setRequestHeader) { xhr.setRequestHeader(키 , setRequestHeader[키]) } 
		async: "false",  //true<비동기>또는false<동기>  
		withCredentials: "true", //true<cors 일 ? 쿠키 보냄>또는false<안보냄> 
		//responseType : "text", //text또는document등..   
	}	
	
	
	</script>


	<!-- /FileUploadServlet -->
		   
	<form id="formmyid" action="/FileUploadServlet"  method="post" enctype="multipart/form-data"  > <!-- 다중 파일업로드  -->
		
		<div id = "divmyid_제목">
			<label>제목 * </label>
			<input id='inputmyidmysubject'name='mysubject' type="text" placeholder="최소 5글자 최대 50글자 미만으로 입력해주세요." required min="5" max="50" title="최소 5글자 최대 50글자 미만으로 입력해주세요."
				   style=" width: 12cm; background-color: rgba(246,246,246,0.8); height: 1cm; border: thin;" /> 
				
		    <br/> 		
		</div>
		
		<div id = "divmyid_파일업로드">
		    <label>파일 업로드 * </label>
		    <input id="inputmyid_file" name="myfilepath" type="file" class="input_file_cls" multiple/>
		    <br/>		
		</div>
		
		<div id = "divmyid_유형">
		    <label>유형 * </label>
			<select name="mycontent" id="mycontentmyid" style="border: thin; background-color: rgba(246,246,246,0.8); height: 1cm;">
				<option label="웹 취약점 분석" value="웹 취약점 분석"/>
				<option label="앱 취약점 분석" value="앱 취약점 분석"/>
				<option label="시스템 취약점 분석" value="시스템 취약점 분석"/>
				<option label="공격 시나리오" value="공격 시나리오"/>
				<option label="고객센터" value="고객센터"/>
				
			</select>		
		</div>
	    <br/>
	    <br/>
	    <div id="divmyid" style="overflow: scroll;">
	    	<textarea id="textareamyidmytext" name=mytext value="mytext" id = "mytext" class="mytextarea_cls" required  rows="40%" cols="170%" maxlength="65534" title="65535 글자 미만으로 입력해주세요."
	    		  style="margin-left: 10%; background-color: rgba(217,229,255,0.3)">
	        </textarea> 
	    </div>
	      
	    <br/>
	    <br/> 
	    
	    <input type="submit" id="btnSubmit" value="작성하기" 
	    	   style="margin-left: 15%; background-color: rgb(103,153,255,1.0); color: white; height: 1cm; border: thin;"/>
	 	 <input type="button" id="NoticeBoard_List" value="목록" 
	    	   style="margin-left: 1%; background-color: rgb(103,153,255,1.0); color: white; height: 1cm; border: thin;"/>
	    <br/>
	</form> 


	<script>  
		<%! 
		String _rewriter ,mysubject , mypriority , myid , myfilepath , mycontent , mytext , myreadcount;
		Connection connection; Statement statement; ResultSet resultSet;
		%>
		
		
		
		<%
		try{
		    Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/myhacking",
					"myhack",
					"1234"
			);
			statement = connection.createStatement();		
			mypriority = request.getParameter("mypriority");
		%>
		
		mypriority=<%=mypriority%>  
		
		
		<%
			_rewriter = request.getParameter("_rewriter");
			if(_rewriter != null && _rewriter.equals("true")){
				
				resultSet = statement.executeQuery("select * from myboard where mypriority=" + mypriority);
				
				while(resultSet.next()){
					mysubject = resultSet.getString("mysubject");
					mypriority = resultSet.getString("mypriority");
					myid = resultSet.getString("myid");
					myfilepath = resultSet.getString("myfilepath");
					mycontent = resultSet.getString("mycontent");
					mytext = resultSet.getString("mytext");
					myreadcount = resultSet.getString("myreadcount"); 
		%>

					//script 쪽임.
					_rewriter = <%=_rewriter%>
					mypriority = <%=mypriority%>   
					mysubject = '<%=mysubject%>'  
					mypriority = '<%=mypriority%>'
					myid = '<%=myid%>'
					myfilepath = '<%=myfilepath%>'   
					mycontent = '<%=mycontent%>'
					mytext = '<%=mytext%>'
					myreadcount = '<%=myreadcount%>'
		
		<%
				} 
			}else{
				mysubject="";mypriority="";myid="";myfilepath="";mycontent="";mytext="";myreadcount="";  
			}  
			
		}catch(Exception e){
			out.println(e.getMessage());
			e.printStackTrace();
		}
		%> 
		
  
							 
		if(typeof (mysubject) !== 'undefined' && MyLibrary.f_check_valid(mysubject)){     
			inputmyidmysubject.value = mysubject  		 	  
		}
      
		
		if(typeof(myfilepath) !== 'undefined' && myfilepath != ''){
			myfilepath.split(";").forEach((v,i,arr)=>{
				if(v !== undefined){
					divmyid_파일업로드.innerHTML+='<a download=' + '"' + v.split("/fileupload/")[1] + '"' + ' href=' + '"' + v + '"' + '>' + v.split("/fileupload/")[1] + '</a>' + '<br/>'  
				} 
			})
			
			
			divmyid_파일업로드.querySelectorAll('a').forEach((a)=>{
				if(a.innerText === 'undefined'){
					a.remove();    
				}				
			})
			

			
			
		}
		

		if(typeof(mycontent)  !== 'undefined' && mycontent != ''){
			option = document.querySelector('option[value=' + '"' + mycontent + '"' + ']')
			option.selected=true   
		}
		
		if(typeof(mytext) !== 'undefined' && mytext != ''){
			textareamyidmytext.value = mytext    
		}  
		     
		
		
		let file_el=undefined;
		var inputmyid_file = document.querySelector('#inputmyid_file');
		var divmyid = document.querySelector('#divmyid');
		divmyid.scrollTop = divmyid.scrollHeight;  
		var divmyid2_count , mytextmyid; 
		var count=0,result;  
		inputmyid_file.onchange=function(e){
			for(var file of inputmyid_file.files){
				var fileReader = new FileReader();
				fileReader.readAsText(file, 'UTF-8'); 
				fileReader.onload=function(e){
					console.log(fileReader.result + "\n");
					file_real_type=file.name.split(/\./)[1];
					var media_filter=["jpeg","jpg","img","png","mp3","mp4","avi"];
					var text_plain_filter = ["txt","sh","ph","conf","cnf","cfg"]; 
					var client_filter = ["html","css","js"];
					var server_filter = ["jsp","php","asp","java","py","c","cpp" ,"sql"];
					textareamyid = document.querySelector('#mytextmyid');
					/* divmyid = document.querySelector('#divmyid');
					divmyid.scrollTop = divmyid.scrollHeight; 
					*/
					 if( ["txt"].includes(file_real_type) ){   
						if(fileReader.result != window.undefined ) {
							result = fileReader.result + result ;
							textareamyid.value = result + textareamyid.value;
						}	
					}
				}	
			} 
		}
		
		
		$('#btnSubmit').on('click',function(click){
			if(typeof mypriority !== 'undefined' && MyLibrary.f_check_valid(mypriority)){ 
				
				input_mypriority = document.createElement('input')
				input_mypriority.id='inputmyid_mypriority'; input_mypriority.name='mypriority'; input_mypriority.type = 'hidden';
				input_mypriority.value = mypriority        
				
				formmyid.appendChild(input_mypriority);
			}
			
			datas = {}
			datas.mysubject = encodeURI( $('#inputmyidmysubject').val() )
			datas.mycontent = encodeURI( $('#mycontentmyid').val() )
			datas.mytext = encodeURI( $('#textareamyidmytext').val() )

			datas.mypriority = encodeURI(mypriority)  
			
			if(typeof(_rewriter) !== 'undefined' && _rewriter !== '' ) {
				datas._rewriter = encodeURI( _rewriter )
			}else{
				datas._rewriter = 'false'
			}
			
			
			
			//alert('mysubject='+$('#inputmyidmysubject').val()+'&mycontent='+$('#mycontentmyid').val()+'&mytext='+$('#mytextmyid').val());
			$.ajax({
				type: 'GET',
				dataType: 'text',     
				url: '/FileUploadServlet',       
				async: false,
				data: datas,
				success: function(data,status){
					//alert("success => "+data,status);
					location.href = '/jsp/___NoticeBoard_List' 
				},
				error: function(data,status){
					//alert("error => "+data,status); 
					location.href = '/jsp/___NoticeBoard_List'
				},
				complete: function(data,status){   
					//alert("complete => "+data,status);     
					location.href = '/jsp/___NoticeBoard_List' 
				}
			});
		});
		
		
		$('#btnSubmit').on('mouseover', function(click) {
			$('#btnSubmit').css('background-color', 'black');
			$('#btnSubmit').css('color', 'white');
		})
		$('#btnSubmit').on('mouseout', function(click) {
				$('#btnSubmit').css('background-color', 'rgb(103,153,255,1.0)');
				$('#btnSubmit').css('color', 'white');
		})
		
		
		
		$('#NoticeBoard_List').on('click',function(click){
			window.open('/jsp/___NoticeBoard_List','_self');
		});
		
		
		$('#NoticeBoard_List').on('mouseover', function(mouseover) {
			$('#NoticeBoard_List').css('background-color', 'black');
			$('#NoticeBoard_List').css('color', 'white');
		})
		$('#NoticeBoard_List').on('mouseout', function(mouseout) {
				$('#NoticeBoard_List').css('background-color', 'rgb(103,153,255,1.0)');
				$('#NoticeBoard_List').css('color', 'white');
		})
		
	</script>
	<jsp:include page="footer.jsp" />
</body>
</html>