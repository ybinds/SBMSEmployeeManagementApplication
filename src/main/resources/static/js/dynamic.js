$(document).ready(function() {
	$("#dept").change(function() {
      var deptId = $(this).val();
      var s = '<option value=' + -1 + '>Select Employee</option>';
      if (deptId > 0) {
      	$.ajax({
        url : '/employee/getEmployees',
        data : {"deptId" : deptId},
        success : function(result) {
        	var result = JSON.parse(result);
        	for (var i = 0; i < result.length; i++) {
        		s += '<option value="' + result[i][0] + '">'+ result[i][1]+ '</option>';
        	}
        	$('#employees').html(s);
        }
       });
     }
     //reset data
     $('#employees').html(s);
  });
});