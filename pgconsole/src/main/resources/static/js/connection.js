$(function(){
	$("#alert_create_connection").hide()
	
    $("#alert_create_connection_success").hide();
	
	$("#pickColor").pickAColor({showHexInput : false});

    var useGroupId = true;

    $("#div_groupLabel").hide();
    $("#add_group_link").click(function(){
        $("#div_groupLabel").show();
        $("#div_groupId").hide();
        useGroupId = false;
    });

    $("#back_to_choose_group").click(function () {
        $("#div_groupLabel").hide();
        $("#div_groupLabel").val("");
        $("#div_groupId").show();
        useGroupId = true;
    });
	
    var function_update_url = function(){
    	var host = $( "#host" ).val() != "" ? $( "#host" ).val() : "{host}";
    	var database = $( "#database" ).val() != "" ? $( "#database" ).val() : "{database}";
    	var port = $( "#port" ).val() != "" ? $( "#port" ).val() : "{port}";
    	var url_jdbc =  "jdbc:postgresql://".concat(host , ":" , port ,"/" , database);
    	console.log(url_jdbc)
    	$("#url_jdbc").html( url_jdbc ); 
    }
    
    $("#host").on("blur keyup",function_update_url);
    $("#database").on("blur keyup",function_update_url);
    $("#port").on("blur keyup",function_update_url); 
    
    $("#url_jdbc").html( function_update_url() ); 
    
	$("#create_connection_button, #create_and_connect_button").click(function(){
		//$('#history').html('<asset:image src="ajax-loading.gif"/>');
		var id = $(this);
		$.ajax({
            url : '/connection/create',
            data : { host: $( "#host" ).val(),
            		port: $("#port").val(),
            		database: $("#database").val(),
            		username: $("#username").val(),
            		password: $("#password").val(),
            		groupId: useGroupId ? $("#groupId").val() : null ,
                    groupLabel: !useGroupId ? $("#groupLabel").val() : null } ,
            type:"post",
            dataType: 'json',
            success : function(data) {
            	console.log(data);
            	$("#select_server").append(new Option(data.result.description, data.result.id));
            	if(id.attr('id') == 'create_and_connect_button') {
					$("#alert_create_connection_success").hide().html('');
					$("#select_server").val(data.result.id);
					$("#new-connection").modal('hide')
					selectServer({
						idConnection: data.result.id,
						editor: ace.edit("editor")
					});
					$("#select_server").val(data.result.id);
            	} else {
            		$("#alert_create_connection_success").html(data.message);
            		$("#alert_create_connection_success").show();	                		
            	}
            	$("#new-connection").find('input:text, input:password').val('');
            	
            	
            	
            },
            error: function(xhr){ 
                
                var json = xhr.responseJSON
                console.log( json );
                if ( json.fieldErrors != null ){
                	  
                	$("#alert_create_connection_msg").text(json.message);
                	$("#alert_create_connection_msg").append("<ul>");
                	
                	$.each( json.fieldErrors, function( key, value ) {
                		$("#alert_create_connection_msg").append( "<b>" + value.field + "</b>: " + value.message + "<br/>" );
                	});
                	$("#alert_create_connection_msg").append("</ul>");	                    	 
                	
            		$("#alert_create_connection").show();
            	} else if ( json.error != null ){
            		$("#alert_create_connection_msg").text(json.message);
            		$("#alert_create_connection").show();
            	}
            }
        })
	}); 
});