<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图片上传接口测试</title>
</head>

<body>
<form id="send_form" action="${ctx}/imageservice/uploadimg" class="form-horizontal" method="post" enctype="multipart/form-data">

    <div class="control-group">
        <label class="control-label" for="file">图片：</label>
        <div class="controls">
            <input class="span3" type="file" name="file"id="file"/>
        </div>

        <div class="form-actions">
            <button type="submit" id="btn_send">上传</button>
        </div>
  </div>
</form>
</body>
</html>