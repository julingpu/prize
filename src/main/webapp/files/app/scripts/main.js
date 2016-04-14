define( ['jquery'], function( $ ) {
	var exports = {};
	/**
	 * 文档中的文件上传控件添加监听事件
	 *  
	 */	 
	var attachInputEvent = function() {
		var $fileInputs = $('input[type="file"].hidden.null');
		$fileInputs.off()
		.on( 'change', function( event ) {
			$(this).removeClass('null');			
			$fileWrapper = $(this)
				              .parent()
							  .parent()
							  .find('div.fileWrapper');
			middleDom = null;
			middleDom = document.createElement('DIV');
			middleDom.className = 'image icon';
			middleDom.title = this.value;
			$fileWrapper.append(middleDom);
			deleteImg();
		});
	}
	
	/**
	 * 文件添加的交互控制
	 * 按钮控制添加和删除图片的交互
	 */
	var addFile = function() {
		var $targetBtns = $('button.addFile'),
		    $fileWrapper,
			middleDom,
			$fileInputs = $('input.hidden.null');
					
		// 添加图片控件的事件，当内容变化时，去除.null类
		attachInputEvent();
			
		$targetBtns.on( 'click', function( event ) {
			event.preventDefault();
		    // 如果图片数量等于5，表示已达到最大图片数量了
			if ( 
				$(this)
				.parent()
				.find('div.fileContent')
				    .find('input:not(".null")')
					.length 
				== 5 ) {
				$('#notification').animate({
					'right': '0'
				}, 800)
				.text('图片数量已达最大值，无法添加更多的图片.');
				setTimeout(function() {
				    $('#notification').animate({
						'right': '-240px'
					}, 1000)
					.text('');
				}, 3000);
				return ;
			} else {				
				// 添加图片选择的动态添加效果
				// 首先点击隐藏的文件控件
				$(
					$(this)
					.parent()
					    .find('div.fileContent')
						    .find('input.null')[0]
				).click();										
				return ;
			}
		});
		
	};
	
	// 当鼠标欲删除待上传图片时
	var deleteImg = function() {
	    // 添加鼠标移入移出的删除按钮的效果
		var middleDom, $fileWrapper, $fileContent, $fileInputs;		
		$('div.image.icon').off()
		.on( 'mouseenter', function() {
			$fileWrapper = $(this).parent().parent();
			$fileContent = $(this).parent().parent().parent().find('div.fileContent');			
			middleDom = null;
			middleDom = document.createElement('SPAN');
		    middleDom.className = 'icon delete';			
			$(this).append(middleDom);			
			middleDom = null;
			
			// 添加删除按钮的事件			
			$(this).find('span.delete.icon').slideDown()
			.on( 'click', function( event ) {
				$($fileContent.find('input')[
				    $fileWrapper.find('div.image.icon').index($(this).parent())					
				]).remove();
				middleDom = document.createElement('INPUT');
				middleDom.type = 'file';
				middleDom.name = 'file';
				middleDom.className = 'hidden null';
				$fileContent.append(middleDom);				
				// 添加图片控件的事件，当内容变化时，去除.null类
				attachInputEvent();
				$(this).parent().remove();
			});
		}).on( 'mouseleave', function( event ) {
			$(this).find('span.delete.icon').remove();			
		});				
	};
	
	// 添加分页按钮的点击事件的绑定
	var attachPagenation = function( totalPage ) {
	    var $target = $('#pagenationContent'), 
		    currentPage;
		$target.off().on( 'click', function( event ) {
			if ( event.target.nodeName === 'LI' && !$(event.target).hasClass('active') ) {
				currentPage = parseInt( $target.find('li.active').text().trim() ); 
				// console.log( 'get currentPage successful: ' + currentPage );
				if ( $(event.target).hasClass('prev') && currentPage > 1 ) {
					currentPage--;
				} else if ( $(event.target).hasClass('next') && currentPage < totalPage ) {
					currentPage++;
				} else if ( !$(event.target).hasClass('prev') && !$(event.target).hasClass('next') && !$(event.target).hasClass('active') ) {
					currentPage = $(event.target).text().trim();
				} else {
					return;
				}
				$.ajax({
					url: 'http://localhost:8082/PrizeServer/getPrizeByTerm',
					method: 'get',
					dataType: 'json',
					data: {
						currentPage: currentPage,
						term_name: $('#term').find('option:checked').text().trim(),
						prize_type: $('#type').find('option:checked').text().trim()
					},
					success: function( data ) {
					    var info = data;
                        var bt = baidu.template;
                        var html = baidu.template( 'apply', info ),
                            pagenationHtml = baidu.template( 'pageNation', info );
                        $('#applyContent').html( html );
                        
                        // 渲染分页
                        $('#pagenationContent').html( pagenationHtml );
                        
                        // 渲染完成之后添加事件
                        attachPagenation( info.totalPage );
					},
					error: function( data ) {
						alert('网络请求失败！');
					}
				});
			} else {
				return;
			}
		});
	};
	
	exports.addFile = addFile;	
	exports.deleteImg = deleteImg;
	exports.attachPagenation = attachPagenation;
	
	return exports;
});
