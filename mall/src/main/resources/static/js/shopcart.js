sessionStorage = window.sessionStorage;
var cartArr = {} //key是cart的id,值是商品的记录
let currentPageNum = 1;
$(document).ready(function(){
	let is = isLogin();
	console.log(is)
	if(is){
		queryCart(1,10);
		bindPreNextPage();
	}

})

function isLogin(){
	let result = false;
	$.ajax({
		type: "get",
		url: "/user/isLogin",// /user/isLogin ./testjson/isLogin.json
		data: {},
		async: false,//设置同步请求
		dataType: "json",
		success: function(res){
			if(res.code == 200){
				//已登录
				$("#cartNoLogin").hide();
				$("#cartLogin").show();
				$("#nickname").html("你好!"+ res.data.nickName);
				//浏览器缓存,重启删除
				sessionStorage.setItem("userId",res.data.id);
				result = true;
			}else{
				$("#cartNoLogin").show();
				$("#cartLogin").hide();
				result = false;
			}
		}
	});
	return result;
}

function queryCart(pn, pz){
	let id =sessionStorage.getItem("userId");
	$.ajax({
		type:"GET",
		url:"/cart/list",// ./testjson/shopcart.json
		data:{
			userId:id,
			pageNum:pn,
			pageSize:pz
		},
		dataType:"json",
		success:function(res){
			if(res.code==200){
				console.log(res.data);
				let s="";
				cartArr = {};
				for(let record of res.data){
					record.ischecked = false;
					cartArr[record.id] = record;
					s +=
					` <div class="cartd4" id="cartgood`+ record.id +`">
					<ul class="cartul2">
					<li class="cartli1">
						<div class="cartd3">
							<input type="checkbox" onclick='checkOneGood(`+ record.id +`)' class="ipt">							
						</div>
					</li>
					<li class="cartli2">
						<div class="fl">
							<img src="`+ record.img +`">
						</div>
						<div class="fl cartd5">`+ record.prodName +`</div>
						<div class="cls"></div>
					</li>
					<li class="cartli3">¥`+ record.price +`</li>
					<li class="cartli4">
						<button onclick="sub(`+ record.id +`)">-</button>
						<input type="text" value="`+ record.num +`" id="iid`+ record.id +`">
						<button class="ml" onclick="add(`+ record.id +`)">+</button>
					</li>
					<li class="cartli5">¥<span id="gsum` +record.id +`">`+ (record.price*record.num) +`</span></li>
					<li class="cartli6">
						<a href="javascript:deleteCartGood(`+ record.id +`);" >删除</a>
					</li>
				</ul>
				</div>`
				}
				$(".cartd4").remove();
				$("#cartgoodlist").append(s);
				currentPageNum = pn;
				if(currentPageNum == 1){
					$("#prePage").prop("disabled",true);
				}else{
					$("#prePage").prop("disabled",false);
				}
				totalMoney();
				console.log(cartArr);
			}
		}
	})
}

function checkOneGood(id){
	cartArr[id].ischecked = !cartArr[id].ischecked;
	totalMoney();
}

function totalMoney(){
	let total = 0;
	let num = 0;
	for(let key in cartArr){
		if(cartArr.hasOwnProperty(key)){
			let good = cartArr[key];
			if(good.ischecked){
				total += good.price * good.num;
				num++;
			}
		}

	}

	$("#totalNum").html(num);
	$("#totalPrice").html(total);

}

//全选键
function checkAll(){
	let result =  $("#sela").is(":checked");
	$(".ipt").prop("checked",result);
	for(let key in cartArr){
		if(cartArr.hasOwnProperty(key)){
			cartArr[key].ischecked = result;
		}
	}
	totalMoney();
}


function sub(id){

	let snum =  $("#iid" + id).val();
	let num = parseInt(snum);
	if(num <= 1){
		showToast("不能更小了")
	$('#liveToast').toast('show')
	}else{
		num = num - 1 ;
		updateCart(id,num);
	}
}

function add(id){
	let snum =  $("#iid" + id).val();
	let num = parseInt(snum) + 1;
	updateCart(id,num);
}

function updateCart(_id,_num){

	$.ajax({
		type:"post",//post
		url:"/cart/admin/update",// /cart/admin/update ./testjson/updatecart.json
		contentType:"application/json",
		data:JSON.stringify({
			id:_id,
			num:_num
		}),
		dataType:"json",
		success:function(res){
			if(res.code == 200){
				$("#iid" + _id).val(_num);//界面更新
				cartArr[_id].num = _num;//更新内存中的商品数量
				$("#gsum" + _id).html(_num*cartArr[_id].price);//更新小计
				totalMoney();//更新总价
			}else{
				showToast("更新购物车失败" + res.message)
			}
		}
	})

}

function deleteCartGood(id){
	let dataArr = [id];
	$.ajax({
		type:"post",//post
		url:"/cart/admin/delete",///cart/admin/delete ./testjson/deletecart.json
		contentType:"application/json",
		data:JSON.stringify(dataArr),
		dataType:"json",
		success:function(res){
			if(res.code == 200){
				delete cartArr[id];//删除内存中的商品数量
				$("#cartgood" + id).remove();//更新界面->删除某个div
				totalMoney();//更新总价
			}else{
				showToast("删除购物车失败" + res.message)
			}
		}
	})
}

function bindPreNextPage(){
	$("#prePage").on("click",function(){
		if(currentPageNum <= 1){
			showToast("已经是第一页");
			return;
		}
		let PageNum = currentPageNum -1 ;
		queryCart(PageNum,10);

	})

	$("#nextPage").on("click",function(){
		let PageNum = currentPageNum +1 ;
		queryCart(PageNum,10);

	})
}

function showToast(message){
	$("#messagetoast").html(message)
	$('#liveToast').toast('show')
}