<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SimpleFileUpload</title>
    <script type="text/javascript" src="js/swfobject.js"></script>
    <script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.uploadify.v2.1.0.min.js"></script>
    <link rel="stylesheet" href="css/uploadify.css" type="text/css" media="screen"/>

    <script type="text/javascript">
        $(function () {
            $('#file_upload').uploadify({
                'uploader': 'swf/uploadify.swf',
                'script': 'rest/file/upload',
                'fileDataName': 'file',
                'cancelImg': 'img/cancel.png',
                'multi': true
            });
        });
    </script>
</head>
<body>
<h1>MonkeyBook-File-Upload</h1>

<h3>Uploadify-Test</h3>

<div id="file_upload"></div>
<br/>
<input type="button" value="Clear Queue" onclick="$('#file_upload').uploadifyClearQueue();"/>
<input type="button" value="Submit Queue" onclick="$('#file_upload').uploadifyUpload();"/>

<br/>
<br/>
<hr>

<h3>Form-Test-1</h3>

<form action="rest/file/upload" method="post" enctype="multipart/form-data">

    <p>
        Select a file : <input type="file" name="file" size="45"/>
    </p>

    <input type="submit" value="Upload It"/>
</form>

<br/>
<br/>
<hr>

<h3>Form-Test-2</h3>

<form action="rest/upload/multipleFiles" method="POST"
      enctype="multipart/form-data">
    <div id="fileSection">
        <h1>Multiple File Upload</h1>
        <table>
            <tr>
                <td>Candidate Name:</td>
                <td><input type="text" name="candidateName" size="45"/>
                </td>
            </tr>
            <tr>
                <td>Candidate Info File:</td>
                <td><input type="file" name="infoFile" size="45"/>
                </td>
            </tr>
            <tr>
                <td>Candidate's Photo:</td>
                <td><input type="file" name="imgFile" size="45"/>
                </td>
            </tr>
        </table>
    </div>
    <p/>
    <input type="submit" value="Upload"/>
</form>

</body>
</html>
