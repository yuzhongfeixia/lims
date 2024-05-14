<%@ page contentType="text/html;charset=utf-8" language="java" %>

<%
String file = new String(request.getParameter("file").getBytes("ISO-8859-1"), "utf-8");
%>
<html>
<head>
  <title><%=file%></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
  <style type="text/css" media="screen">
    html, body	{ height:100%; }
    body { margin:0; padding:0; overflow:auto; }
    #flashContent { display:none; }
  </style>

  <link rel="stylesheet" type="text/css" href="css/flexpaper.css" />
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <script type="text/javascript" src="js/flexpaper.js"></script>
  <script type="text/javascript" src="js/flexpaper_handlers.js"></script>
</head>
<body>
<div id="loading" style="position:absolute;left: 50%;top: 50%;margin-left: -33px;margin-top: -33px;z-index: 1000;">
  <img src="css/loading.gif" style="width: 66px;height: 66px;" />
</div>
<div style="width: 100%; height: 100%;">
    <div id="documentViewer" class="flexpaper_viewer" style="width:100%;height:100%"></div>
    <script type="text/javascript">

        $(function(){
            function onDocumentLoaded() {
                $('#loading').fadeOut();
            }

            $.ajax({
                url: 'FileConvert2Swf.do?fileurl=' + encodeURIComponent('<%=file%>'),
                method: 'GET',
                success: function(response) {
                    res = eval("(" + response + ')');
                    if (res.err_msg) {
                        alert(res.err_msg);
                    }
                    else {
                        var docView = $('#documentViewer');
                        docView.FlexPaperViewer({
                            config : {
                                SWFFile : 'FileDownFile.do?fileurl=' + res.file,
                                Scale : 1,
                                ZoomTransition : 'easeOut',
                                ZoomTime : 0.5,
                                ZoomInterval : 0.2,
                                FitPageOnLoad : true,
                                FitWidthOnLoad : false,
                                FullScreenAsMaxWindow : false,
                                ProgressiveLoading : false,
                                MinZoomSize : 0.2,
                                MaxZoomSize : 5,
                                SearchMatchAll : false,
                                InitViewMode : 'Portrait',
                                RenderingOrder : 'flash',
                                StartAtPage : '',

                                ViewModeToolsVisible : true,
                                ZoomToolsVisible : true,
                                NavToolsVisible : true,
                                CursorToolsVisible : true,
                                SearchToolsVisible : true,
                                WMode : 'window',
                                localeChain: 'zh_CN'
                            }
                        });
                        docView.on('onDocumentLoaded', onDocumentLoaded);
                    }
                }
            });


        });

    </script>
</div>

</body>
</html>
