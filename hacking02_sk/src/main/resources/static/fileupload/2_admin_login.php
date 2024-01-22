<?php header("Content-Type: text/html; charset=UTF-8");?>
<?php include_once "include/common/declare.php";?>
<?php include_once "../request_log_ssrf2.php";?>
<?php
	

$user =	nvl( $_GET['title'],'1');


?>
<!doctype html>
<html lang="ko" style="height:100%;">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>EQST 보안교육센터</title>
		<link rel="shortcut icon" href="favicon.ico">
		<link rel="stylesheet" href="../css/style.css" />
		<script type="text/javascript" src="../js/libs/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="../js/libs/jquery-ui.js"></script>
		<script type="text/javascript" src="../js/libs/jquery.bxslider.min.js"></script>
		<script type="text/javascript" src="../js/libs/nice-select/nice-select.js"></script>
		<script type="text/javascript" src="../js/libs/dotdotdot/dotdotdot.js"></script>
		<script type="text/javascript" src="../js/libs/datepicker.js"></script>
		<!--<script type="text/javascript" src="./commons.js"></script>-->
		<link rel="stylesheet" href="../css/libs/jquery-ui.css" />
		<script type="text/javascript">
			$(function() {
				
				$.customFn = {
					
					boardReg : function(){
						$("#searchForm").submit();
					},
					
					sortingProcess : function(){
						$('#pageIndex').val('1');
						$("#searchForm").attr("action", "notice.php");
						$("#searchForm").submit();
					},
					
					searchProcess : function(){
						$('#pageIndex').val('1');
						if($("#startDt").val() != ""){
							if($("#endDt").val() == ""){
								alert("작성일을 입력해주세요.");
								return false;
							}
						}
						if($("#endDt").val() != ""){
							if($("#startDt").val() == ""){
								alert("작성일을 입력해주세요.");
								return false;
							}
						}
						$("#searchForm").submit();
					}
				
				};
				
				$(document).ready(function() {
					if($("#userName").val() == ''){
						alert("로그인이 필요합니다.");
						location.href = "/login.php"; 
						return;
					}
					var query  = '';
					if($("#keyword").val() != ''){
						query  = query + '&searchType='+$("#searchType").val()+'&keyword='+$("#keyword").val();
					}
					if($("#startDt").val() != ''){
						query  = query + '&startDt='+$("#startDt").val()+'&endDt='+$("#endDt").val();
					}
					if($("#sorting").val() != ''){
						query  = query + '&sorting='+$("#sorting").val();
					}
					
				});
				
				btnReg = $("#btnReg");
				
				btnSearch = $("#btnSearch");
				
				sortingTitle = $("#sortingTitle");
				
				sortingRegUserNm = $("#sortingRegUserNm");
				
				sortingRegDt = $("#sortingRegDt");

				sortingTitle2 = $("#sortingTitle2");
				
				sortingRegUserNm2 = $("#sortingRegUserNm2");
				
				sortingRegDt2 = $("#sortingRegDt2");
				
				//게시물 등록
				btnReg.click(function(e) {
					e.preventDefault();
					$.customFn.boardReg();
				});
				/*
				//search 버튼 클릭시
				btnSearch.click(function(e){
					e.preventDefault();
					$.customFn.searchProcess();
				});
				*/
				
				sortingTitle.click(function(e){
					e.preventDefault();
					$('#sorting').val('title');
					$("#sotingAd").val("ASC");
					/*if ($("#sotingAd").val() == "" ){
						$("#sotingAd").val("ASC");
					}else if ($("#sotingAd").val() == "DESC" ) {
						$("#sotingAd").val("ASC");
					}else{
						$("#sotingAd").val("DESC");
					}*/
					$.customFn.sortingProcess();
				});

				sortingTitle2.click(function(e){
					e.preventDefault();
					$('#sorting').val('title');
					$("#sotingAd").val("DESC");
					$.customFn.sortingProcess();
				});
				
				sortingRegUserNm.click(function(e){
					e.preventDefault();
					$('#sorting').val('regUserNm');
					$("#sotingAd").val("ASC");
					/*if ($("#sotingAd").val() == "" ){
						$("#sotingAd").val("ASC");
					}else if ($("#sotingAd").val() == "DESC" ) {
						$("#sotingAd").val("ASC");
					}else{
						$("#sotingAd").val("DESC");
					}*/
					$.customFn.sortingProcess();
				});

				sortingRegUserNm2.click(function(e){
					e.preventDefault();
					$('#sorting').val('regUserNm');
					$("#sotingAd").val("DESC");
					$.customFn.sortingProcess();
				});
				
				sortingRegDt.click(function(e){
					e.preventDefault();
					$('#sorting').val('REG_DT');
					$("#sotingAd").val("ASC");
					/*if ($("#sotingAd").val() == "" ){
						$("#sotingAd").val("ASC");
					}else if ($("#sotingAd").val() == "DESC" ) {
						$("#sotingAd").val("ASC");
					}else{
						$("#sotingAd").val("DESC");
					}*/
					$.customFn.sortingProcess();
				});

				sortingRegDt2.click(function(e){
					e.preventDefault();
					$('#sorting').val('REG_DT');
					$("#sotingAd").val("DESC");
					$.customFn.sortingProcess();
				});
				
			});
			function search(){
				
				if (window.event.keyCode == 13) {
					$.customFn.searchProcess();
				}
				
			}
			function goView(ol){
				document.searchForm.board_id.value=ol;
				document.searchForm.action = "noticeview.php";
				document.searchForm.method = "GET";
				document.searchForm.submit();
			}
		</script>
    </head>
    <body class="set_size">
        <!--[s] header -->
		<?php include_once('./include/header.php') ?>
        <!--[e] header -->

        <!--[s] main-container-->
        <div class="main-container" id="main-container">
            <script type="text/javascript">
                try{ace.settings.check('main-container' , 'fixed')}catch(e){}
            </script>

            
            <!--[s] main-content-->
            <div class="main-content">
                <div class="main-content-inner">
                    <div class="page-content">
                        <div class="page-header">
                            <h1>
                                <i class="ace-icon fa fa-ellipsis-v orange"></i>
                               관리자 페이지(비공개)
                            </h1><br>
							<span id="search_word"></span>	
                        </div>
                                    
                        <div class="hr10"></div>

								<form name="LoginFm" id="LoginFm" method="POST" >
								<input type="hidden" name="returnurl" id="returnurl" value="" >
								<!--[s] wrap_login-->
								<div class="wrap_login" >
									<ul class="box_login"> 
										<li class="tit"> 관리자 페이지</li>
										<li><input type="text" name="login_id" value="" tabindex="1" id="login_id"  placeholder="아이디" class="userID" /></li>
										<li><input type="password"  name="login_pwd" value="" tabindex="2" id="login_pwd" placeholder="패스워드" class="userPW" /></li>
										<li class="line_btn">
											<input class="btn_login" type=submit name=okdk value="로그인">
										</li>
									</ul>
								</div>
								<!--[e] wrap_login-->
								</form>

<?php
if(isset($_POST['okdk']))
{
	$id=trim($_POST['login_id']);
	$pw=trim($_POST['login_pwd']);
	if($id === "adminuser" && $pw === "adminpassword")
	{
		$_SESSION['ADMIN_YN'] = "Y";
		echo("<script>location.replace('admin.php');</script>"); 
	}
	else
	{
		echo "<br><br>아이디/패스워드가 일치하지 않습니다.";
	}
		
//$file=trim('192.168.2.142/'.$_POST['file']);

} 
?>

	<script>
	
	$('#keyword').on('keyup', function(e) {
		e.preventDefault();

		//var ov = '검색 결과 : ' + $('#keyword').val();
		//$('#search_word').html(ov);
		
		var queryString = $("form[name=searchForm]").serialize() ;
 
        $.ajax({
            type : 'post',
            url : './noticeHTML.php',
            data : queryString,
            dataType : 'html',
            error: function(xhr, status, error){
                alert(error);
            },
            success : function(dataov){
				$('tbody').html(dataov);
            },
        });

		
	});

	</script>
                                <!--[s] wrap_pagenation -->
                                <div class="box_table_bottom">
                                    <div class="pull-center">
                                        <!--[s] pagenation -->
                                        <ul class="pagination">
											<?php
											   pagingType2( $rowsCntArr["tCnt"] , $pageIndex , 10 , $pageSize , $_SERVER[PHP_SELF] , '');
											?>
                                        </ul>
                                    <!--[e] pagenation -->
                                </div>
                            </div>
                            <!--[s] wrap_pagenation -->
                        
                            </div><!-- [e] col-xs-12-->
                        </div><!--[e] row-->
                    </div><!-- /.page-content -->
                </div>
            </div><!-- /.main-content -->
            
        

        </div><!-- /.main-container -->


	</body>
</html>
