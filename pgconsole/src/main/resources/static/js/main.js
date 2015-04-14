var editorPreviewSave = ace.edit("editor-preview-save");
editorPreviewSave.setShowPrintMargin( false );  
editorPreviewSave.setTheme("ace/theme/textmate");
editorPreviewSave.getSession().setMode("ace/mode/pgsql");
editorPreviewSave.setReadOnly(true); 



var editor = ace.edit("editor");
editor.setShowPrintMargin( false );  
editor.setTheme("ace/theme/textmate");
editor.getSession().setMode("ace/mode/pgsql");
editor.setOptions({enableBasicAutocompletion: true});
var langTools = ace.require("ace/ext/language_tools");
 

var rhymeCompleter = {
    getCompletions: function(editor, session, pos, prefix, callback) {
        if (prefix.length === 0) { callback(null, []); return }
        
        $.getJSON(
        	"/console/autocomplete", {token :  prefix , idConnection : $( "#select_server" ).val() })
        	.done (function(wordList) {  
                // wordList like [{"word":"flow","freq":24,"score":300,"flags":"bc","syllables":"1"}]
                callback(null, wordList.map(function(ea) {
                    return {name: ea.word, value: ea.word, score: ea.score, meta: ea.flags}
                }));
            });
    }
};
langTools.addCompleter(rhymeCompleter);

var sourceFancyTree = {
	 url: "/serverExplorer",
     data: {mode: "children", connection:  $( "#select_server" ).val() },
}


var executeSqlHandler = function(){
	$('input[id="execute-sql"]').prop('disabled', true);
	$('#ajax_loading').show();
	
	selectionRange = editor.getSelectionRange(); 		
	querystring = editor.session.getTextRange(selectionRange); 
	connection = $( "#select_server" ).val()
	
	$.ajax({
        url : '/console/run',
        data : { queries :  querystring, idConnection: connection } ,
        type:"post",
        dataType: 'html',
        success : function(data) {			            
        	console.log(data); 
            $('#result').html(data);
            

            $('input[id="execute-sql"]').prop('disabled', false);
            $('#ajax_loading').hide(); 
            if ( $("*[id^='table_resultset']").length ){
            	$("*[id^='table_resultset']").DataTable();
            }
                               
            return false;
        },
        error: function(xhr){ 
            console.log(xhr.responseText);
            $('input[id="execute-sql"]').prop('disabled', false);
            $('#ajax_loading').hide();
            
        }
    })
   
	$('#editor').focus();
}

var saveSqlHandler = function(){
	selectionRange = editor.getSelectionRange(); 		
	querystring = editor.session.getTextRange(selectionRange); 			
	editorPreviewSave.setValue(querystring)
	editorPreviewSave.gotoLine(1, 0, false)
	$("#save-modal-title").text( $("#select_server option:selected").text() )	
	
	$("#title-save-sql").val("")
	$("#description").val("")
	$("#tags").val("")

	$('#title-save-sql').focus();
	$("#save-sql").modal("show")
}

editor.commands.addCommand({
    name: "execute-sql-f5",
    bindKey: {win: "F5", mac: "Command-Option-F5"},
    exec: function(editor) {		       
       executeSqlHandler()
       return;
    }
});

editor.commands.addCommand({
    name: "execute-sql-alt-enter",
    bindKey: {win: "Ctrl-Enter"},
    exec: function(editor) {		       
       executeSqlHandler()
       return;
    }
});


editor.commands.addCommand({
    name: "overide-save",
    bindKey: {win: "Ctrl-S", mac: "Command-S"},
    exec: function(editor) {		       
    	saveSqlHandler()
       return;
    }
}); 


$('#editor-preview-save, #editor').css("font-size",'16px');

// Create a server connection
var selectServer = function(options) {
	var settings = options;
	$.ajax({
        url : '/console/changeConnection',
        data : { idConnection : settings.idConnection , code: settings.editor.session.getValue() } ,
        type:"post",
        dataType: 'json',
        success : function(json) {
        	settings.editor.setValue( json.code );	 	
        	settings.editor.gotoLine(1, 0, false)
        	$.getJSON( sourceFancyTree.url , {mode: "children", connection:  settings.idConnection } )
        	 .done(function( json ) {
        		console.log( "JSON Data: " + JSON.stringify(json) );
        		$("#tree").fancytree("getTree").reload( json );
        		swal( {title: "changed connection!", text:  json.message , type: "success"});
        	}).fail(function( jqxhr, textStatus, error ) {
            	var err = textStatus + ", " + error;
            	console.log( "Request Failed: " + err );
            	swal( {title: "changed connection failed!", text:  json.message , type: "error"});
        	});

        	return false;	                		                    
        },
        error: function(xhr){ 
            console.log(xhr.responseText);
        }
    });
}

$(function(){

	$(window).bind('beforeunload', function(){
		  return true;
	});
	
	var $loading = $('#ajax_loading').hide();
  
	 $("#tree").fancytree({ 
			icons: true,
			selectMode: 1,				
			source: sourceFancyTree,
			activate: function(event, data) {
		        
		        var node = data.node;
		        $.ajax({
	                url : '/serverExplorer/objectDescription',
	                data: {mode: "children", parent: node.key, type: node.data.type, database: node.data.database , schema: node.data.schema, table: node.data.table, view: node.data.view , sequence: node.data.sequence },
	                type:"post",
	                dataType: 'text',
	                success : function(data) {			 
		                          	                	
	            		$('#objectDescription').html( data );
	           		 	$('pre code').each(function(i, block) {
	        		    	hljs.highlightBlock(block);
	        		  	});
		                
	                    return false;
	                },
	                error: function(xhr){ 
	                    console.log(xhr.responseText);
	                }
	            })
		      },
		      select: function(event, data) {
		        // Display list of selected nodes
		        var s = data.tree.getSelectedNodes().join(", ");
		       // $("#echoSelection1").text(s + " - " + data.node.data.type);
	
			    
	            
		      },
		      dblclick: function(event, data) {
		        data.node.toggleSelected();
		      },
		      keydown: function(event, data) {
		        if( event.which === 32 ) {
		          data.node.toggleSelected();
		          return false;
		        }
		      },
		      lazyLoad: function(event, data){
		          var node = data.node;
		         
		          data.result = {
		            url: "/serverExplorer/lazyLoading",
		            data: {mode: "children", parent: node.key, type: node.data.type, database: node.data.database , schema: node.data.schema, table: node.data.table },
		            cache: false
		          };
		      }
		 });
	 
	setInterval(function(){ 
		connection = $( "#select_server" ).val()				 	
		querystring = editor.session.getValue() 
		$.ajax({
	        url :  '/console/autoSave', 
	        data : { code :  querystring , idConnection: connection } ,
	        type:"post",
	        dataType: 'json',
	        success : function(data) {			            	                	
	        	$('#save_status').html( '<span class="glyphicon glyphicon-ok"></span> ' + data.message );
	            return false;
	        },
	        error: function(xhr){ 
	            console.log(xhr.responseText);
	        }
	    })
	    
		$('#editor').focus();
		
	}, 60000); // 1 min
	
	// Get selected text 
	$('#execute-sql').click(executeSqlHandler);
	
	$( "#select_server" ).change(function(){
		selectServer({
			editor: editor,
			idConnection: $(this).val()
		});
		if ( $( "#select_server option:selected").val() > 0 ) {
			var connection_color = $( "#select_server option:selected" ).attr("style");
			$( "#select_server" ).attr("style", connection_color);
		} else {
			$( "#select_server" ).attr("style", "#ffffff");
		}
	});
	
	$("#link_reload_history").click(function(){
		//$('#history').html('<asset:image src="ajax-loading.gif"/>');
		$.ajax({
	        url : '/console/history',
	        data : { idConnection: $( "#select_server" ).val() } ,
	        type:"get",
	        dataType: 'html',
	        success : function(data) {			            	                	
	        	$('#history').html( data );
	        	 /* $('pre code').each(function(i, block) {
	        		    hljs.highlightBlock(block);
	        		  });
	   		   */
	            return false;
	        },
	        error: function(xhr){ 
	            console.log(xhr.responseText);
	        }
	    })
	});
	
	$('#format-sql').click(function() {
		connection = $( "#select_server" ).val()				 	
		selectionRange = editor.getSelectionRange(); 		
		querystring = editor.session.getTextRange(selectionRange);
		
		$.ajax({
	        url : '/console/sqlformatter',  
	        data : { code :  querystring , connectionId: connection } ,
	        type:"post",
	        dataType: 'json',
	        success : function(data) {	                
	        	editor.session.replace( selectionRange , data.code );	                	 
	            return false;
	        },
	        error: function(xhr){ 
	            console.log(xhr.responseText);
	        }
	    })
	    
		$('#editor').focus();
	});
	
	// TO TOP
	  $('body').append('<div id="toTop" class="btn btn-info"><span class="glyphicon glyphicon-chevron-up"></span> Back to Top</div>');
		$(window).scroll(function () {
			if ($(this).scrollTop() != 0) {
				$('#toTop').fadeIn();
			} else {
				$('#toTop').fadeOut();
			}
		}); 
	$('#toTop').click(function(){
	    $("html, body").animate({ scrollTop: 0 }, 600);
	    return false;
	});
	
	
	$("#logoff").click(function(){
		swal({
		  title: "Are you sure?",
		  text: "Do you really want to leave the system? Not saved queries will be lost!",
		  type: "warning",
		  showCancelButton: true,
		  confirmButtonColor: "#DD6B55",
		  confirmButtonText: "Yes!",
		  cancelButtonText: "No, cancel plx!",
		  closeOnConfirm: false
		},function(isConfirm){
			if (isConfirm) { 
				var jqxhr = $.get( "/logout", function() {
				 location.reload();
			}) 
			.fail(function() {
				swal("Failed", "could not perform the operation", "error");
			});
			} else {
				return false;
			}
		});
	});
	
	$("#link_reload_saved_sqls").click(function(){
		//$('#history').html('<asset:image src="ajax-loading.gif"/>');
		$.ajax({
	        url : '/savedsql/search',
	        data : { idConnection: $( "#select_server" ).val() } ,
	        type:"get",
	        dataType: 'html',
	        success : function(data) {			            	                	
	        	$('#savedsql').html( data );	        	 
	            return false;
	        },
	        error: function(xhr){ 
	            console.log(xhr.responseText);
	        }
	    })
	});
	
	
	$("#link_save_sqls").click(function(){
	 
		$.ajax({
	        url : '/console/savesql',
	        data : { 
	        	idConnection: $( "#select_server" ).val(),
	        	title: $("#title-save-sql").val(),
	        	description: $("#description").val(),
	        	tags: $("#tags").val(),
	        	code: editorPreviewSave.getSession().getValue()
	        },
	        type:"post",
	        dataType: 'json',
	        success : function(data) {			            	                	
	        	console.log(data);	     
	        	  
	        	 var $modal = $('#new-connection');

	        	//when hidden
	        	 $modal.on('hidden.bs.modal', function(e) { 
	        	  return this.render(); //DOM destroyer
	        	});

	        	$modal.modal('hide'); //start hiding
	        	
	        	 swal( {title: "SQL saved!", text:  "Your sql was saved with success!" , type: "success"});
	            return false;
	        },
	        error: function(xhr){ 
	            console.log(xhr.responseText);
	        }
	    })
	});
	  
	
  });