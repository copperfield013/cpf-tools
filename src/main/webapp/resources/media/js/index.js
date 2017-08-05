/**
 * 
 */
$(function(){
	function AsynFileUpload(name, url){
		var file = $('<input type="file" name="' + name + '" />');
		
		this.selectFile = function(){
			file.click();
		}
		this.upload = function(callback){
			var form = $('<form>');
			form.append(file);
			
			var formData = new FormData(form.get(0));
			$.ajax({
				url : url,
				type : "POST",
				data : formData,
				success : function(result) {
					callback(result);
				},
				contentType : false,
				processData : false
			});
		} 
	}
	var asyn = new AsynFileUpload('file', 'demo/upload.html')
	$('#select-file').click(function(){
		asyn.selectFile();
	});
	$('#upload-button').click(function(){
		asyn.upload();
	});
});