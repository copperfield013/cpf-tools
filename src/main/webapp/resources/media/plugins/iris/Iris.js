/**
 * 封装虹膜插件
 * 插件可用于虹膜注册，将虹膜数据上传到服务器后，并绑定一个32位uuid。
 * 此外，还提供了虹膜识别的功能，即传入已经已经注册过的uuid，根据该uuid识别虹膜，判断是否匹配。
 * 
 * 使用插件之前，客户端必须使用IE浏览器，并且安装ActiveXIrisConEC_Htmlv控件
 * 控件需要依赖jQuery和json2(json2依赖是针对于低版本IE中没有JSON.stringify方法的解决)
 * 
 * 
 * 插件初始化方法
 * var iris = new Iris(param);
 * 初始化参数param包括以下几个属性
 * 	id			: 创建的控件object元素的id，也是创建后在全局可以获得控件对象的句柄。默认irisInstance
 * 	classid		: 创建的activeX控件的id，与安装的控件有关
 *  width		: 控件的宽度，单位px。默认550px
 *  height		: 控件的高度，单位px。默认400px
 *  container	: 控件的所在容器，HTMLDom对象或者jQuery。如果没有传入，那么必须在初始化后调用appendTo方法将控件加载到容器中
 *  overtime	: 识别的超时时间，单位秒。当识别的总时间超过这个时间时，会判断当前识别失败
 *  excessCount	: 识别的最大有效帧。当识别的有效帧数超过这个数字时，会判断当前识别失败
 *  
 *  初始化并且放置控件之后，就可以调用控件的以下几个封装方法
 *  appendTo	: {function(Dom)}
 *  	将控件放置到指定容器中
 * 	startEnroll	: {function(callback)}
 * 		开始注册，参数是注册成功时的回调函数，回调函数有一个参数，表示注册的uuid
 * 	startMatch	: {function(uuid, callback)}
 * 		开始匹配。参数1是要匹配对应虹膜的uuid。参数2是匹配的回调函数，回调函数有两个参数，分别是匹配成功或者失败的布尔值，以及匹配提示
 * 	stop		: {function()}
 * 		停止注册或者匹配
 * 
 * @author Copperfield Zhang
 */
(function($, JSON_PARSER){
	/**
	 * 虹膜识别工具对象
	 */
	function Iris(_param){
		var defaultParam = {
			id			: 'irisInstance',
			classid		: 'clsid:ED58FE24-056F-45F6-9BE9-3751BDCFB67C',
			width 		: 550,
			height		: 400,
			container	: null,
			irisServiceAPI	: 'http://122.224.76.26:8081/eyeCloud/v4/Template',
			overtime	: 15,
			excessCount	: 20
		};
		
		var param = $.extend({}, defaultParam, _param);
		if(window[param.id]){
			$.error('页面上已经存在id为' + param.id + '的元素。');
		}
		//控件元素对象
		var $axObj = $('<object classid="' + param.classid + '"></object>').appendTo(document.body);
		$axObj	.attr('id', param.id)
				.css({
					width	: param.width + 'px',
					height	: param.height + 'px',
					display	: 'none'
				})
				;
		//获得控件对象句柄
		var irisInstance = window[param.id];
		
		var _this  = this;
		
		
		/**
		 * 将识别框加载到指定容器下
		 */
		this.appendTo = function(dom){
			$axObj.show().appendTo(dom);
		}
		
		//初始化时将控件加载到容器中
		if(param.container != null){
			this.appendTo(param.container);
		}
		
		
		/**
		 * 判断当前识别框已经加载到某个容器下
		 */
		this.appended = function(){
			return !$axObj.is(':hidden');
		};
		
		var 
			//注册定时器句柄
			enrollInterval = null,
			//匹配定时器句柄
			matchInterval = null;
		
		/**
		 * 开始注册虹膜
		 * @param callback 注册结果回调 有一个参数uuid，如果为null，说明注册失败，否则注册成功
		 */
		this.startEnroll = function(callback){
			checkIrisAppended();
			callback = callback || $.noop;
			//设定定时器循环检测控件的状态
			enrollInterval = setInterval(function(){
				//获得控件的虹膜识别情况
				var temp = irisInstance.GetEnrollTemplates();
				//如果虹膜已经识别完成
				if(temp){
					//解除定时器
					clearInterval(enrollInterval);
					enrollInterval = null;
					//发送虹膜信息到服务器
					sendIrisTemplate(temp, function(uuid){
						//执行注册回调
						callback.apply(_this, [uuid]);
					});
				}
			}, 50);
			
			//打开摄像头开始注册
			irisInstance.StartEnroll('', '');
		};
		
		/**
		 * 停止注册虹膜
		 */
		this.stopEnroll = function(){
			if(enrollInterval !== null){
				clearInterval(enrollInterval);
				enrollInterval = null;
				irisInstance.EndEnroll();
			}
		};
		
		/**
		 * 开始匹配虹膜
		 * @param uuid 需要匹配虹膜的uuid
		 * @param callback 匹配结果回调函数，第一个参数为匹配成功或者失败，第二个参数为匹配信息
		 */
		this.startMatch = function(uuid, callback){
			checkIrisAppended();
			if(uuid){
				//发送uuid到服务器获得对应的虹膜数据
				$.get(param.irisServiceAPI + '/' + uuid, function(data){
					if(data.result){
						//有对应的虹膜数据
						var result = '';
						//组装数据
						for(var i in data.data.TempData[0].leftTempArray){
							result += data.data.TempData[0].leftTempArray[i];
						}
						for(var i in data.data.TempData[0].rightTempArray){
							result += data.data.TempData[0].rightTempArray[i];
						}
						//开启匹配定时器
						matchInterval = setInterval(function(){
							//从控件获得匹配结果
							var matchResult = irisInstance.GetResult;
							if(matchResult <= 0){
								var msg = 'success';
								if(matchResult === -1){
									//匹配超时
									msg = 'timeover';
								}else if(matchResult === -2){
									//超出有效帧
									msg = 'excess';
								}
								
								callback.apply(_this, [matchResult == 0, msg]);
							}else{
								return;
							}
							clearInterval(matchInterval);
							matchInterval = null;
						}, 50);
						//开始比对
						irisInstance.StartMatch(
								result, 
								data.data.TempData[0].leftTempArray.length, 
								data.data.TempData[0].rightTempArray.length,
								param.overtime,
								param.excessCount);
					}else{
						//根据uuid获取虹膜数据失败
						callback.apply(_this, [false, data.errorMessage])
					}
				});
			}
		};
		/**
		 * 停止匹配虹膜
		 */
		this.stopMatch = function(){
			clearInterval(matchInterval);
			matchInterval = null;
			irisInstance.EndMatch();
		};
		/**
		 * 停止所有获得
		 */
		this.stop = function(){
			var status = _this.getStatus();
			if(status === 'enrolling'){
				_this.stopEnroll();
			}else if(status === 'matching'){
				_this.stopMatch();
			}
		};
		
		/**
		 * 获得控件的当前状态
		 * @return 
		 * 	enrolling	正在注册
		 *  matching	正在识别
		 *  null		其他状态
		 */
		this.getStatus = function(){
			if(enrollInterval != null){
				return 'enrolling';
			}else if(matchInterval != null){
				return 'matching';
			}else{
				return null;
			}
		}
		
		/**
		 * 
		 */
		function checkIrisAppended(){
			if(!_this.appended()){
				$.error('控件还没有加载到页面当中，不能调用当前方法');
			}
		}
		
		/**
		* 将虹膜数据发送到服务器
		*/
		function sendIrisTemplate(temp, callback){
			//构造参数用于发起请求
			var tempStr = temp.toString();
			//构造虹膜信息
			var leftTempArray = tempStr.substr(0, 1368),
				rightTempArray = tempStr.substr(1368, 1368);
			//生成uuid
			var uuid = UUID();
			//构造请求对象
			var req = {
		            "uuid": uuid,
		            "leftTempArray": [leftTempArray],
		            "rightTempArray": [rightTempArray],
		            "groupInfoObj": 
					{
		               "province":"浙江",
		               "city":"杭州",
		               "district":"临安" 
		        	}  
		        };
			var reqStr = JSON_PARSER.stringify(req);
			//开启跨域访问				
			jQuery.support.cors=true;
			//发送异步请求到虹膜服务器，使其保存当前的虹膜信息
			$.post(
					param.irisServiceAPI,
					reqStr, 
					function(data){
						if(data.result){
							//保存成功
							callback.apply(data, [uuid, data.result]);
						}else{
							//保存失败
							callback.apply(data, [null]);
						}
			});
		}
	}
	/**
	 * 随机字符串
	 */
	function UUID() {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-";

        var uuid = s.join("");
        return uuid;
    }
	
	
	if(typeof define === 'function' && define.cmd){
		define(Iris);
	}else{
		window.Iris = Iris;
	}
	
})(jQuery, JSON);