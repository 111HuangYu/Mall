var category_arr=[];
var goodsMap = {};
sessionStorage = window.sessionStorage;
// 初始化
$(document).ready(function(){
	isLogin();
	queryCategory();
	queryBanner();
	
})

function isLogin(){
	$.ajax({
		type: "get",
		url: "/user/isLogin",// /user/isLogin ./testjson/isLogin.json
		data: {},
		async: false,//设置同步请求
		dataType: "json",
		success: function(res){
			if(res.code == 200){
				//已登录
				$("#loName").html("你好!"+ res.data.nickName);
				//浏览器缓存,重启删除
				sessionStorage.setItem("userId",res.data.id);
				cartNum(1,10);
			}
		}
	});
}

function cartNum(pn,pz){
	let id =sessionStorage.getItem("userId");
	$.ajax({
		type: "get",
		url: "/cart/list",///cart/list ./testjson/shopcart.json
		data:{
			userId:id,
			pageNum:pn,
			pageSize:pz
		},
		dataType:"json",
		success:function (res){
			let count = 0;
			console.log(res.data)
			for (let record of res.data){
				count += record.num;
			}
			console.log(count)
			$("#cartnum").html(count);

		}
	})
}

function queryCategory(){
	$.ajax({
		type:"get",
		url:"/category/list",///category/list ./testjson/category.json
		data:"",
		dataType:"json",
		success:function(res){
			if(res.code == '200'){
				console.log(res)
				category_arr = res.data;
				let s1="";
				let s2=""; 
				for(let val of category_arr){
					s1 +=
					`<div class='category' cgId='`+val.id+`'>
						<div class='cg_a'><a href='Javascript:;'>`+ val.name +`</a></div> 
					</div>`;
					s2 +=
					`<div class="cc" cgdid='`+ val.id +`'>
						<div >
							<h3 class="cc-title">`+ val.name +`</h3>					
						</div>
					</div>`
				}
				$("#cg_p").append(s1);
				$("#goodlist").append(s2);
				for(let val of category_arr){					
					queryGoodByCategoryId(val.id,val.name,1,60);
				}
			}
		}
	})
}

function queryGoodByCategoryId(cgId,cgName,pn,pz){
	$.ajax({
		type:"get",
		url:"/product/list",///product/list ./testjson/goods.json
		data:{
			categoryId: cgId,
			pageNum:pn,
			pageSize:pz
		},
		dataType:"json",
		success:function(res){
			if(res.code == '200'){
				goodsMap[cgName] = res.data;//60
				let s1 = "";
				s1 += 
				`<div class="cg_div">
					<div class="category_detail">`;
				let i = 0;
				for(let val of goodsMap[cgName]){
					if(i == 0){
						s1 += "<ul>";
					}
					s1 += `<li class="cd_li">
								<a href="/goods.html?id=`+val.id+`">`+ val.title +`</a>
							</li>`;
					i++;
					if(i==6){
						s1 += "<ul>";
						i == 0;
					}
				}
				if(i != 0){
					s1 += "<ul>";
				}
				s1 +=
					`</div>
					</div>`;		
				
				$("div[cgId='"+ cgId +"']").append(s1);							
				$("div[cgId='"+ cgId +"']").on("mouseover",function(){
					$("div[cgId='"+ cgId +"'] .cg_a").css("background-color","#e9e9e9");
					$("div[cgId='"+ cgId +"'] .cg_div").show();
				});
				
				$("div[cgId='"+ cgId +"']").on("mouseleave",function(){
					$("div[cgId='"+ cgId +"'] .cg_a").css("background-color","white");
					$("div[cgId='"+ cgId +"'] .cg_div").hide();
				});
				listGoods(cgId, cgName);
			}
		}
	})
}

function queryBanner(){
	$.ajax({
			type:"get",
			url:"/banner/list",//   /banner/list  ./testjson/banner.json
			data:"",
			dataType:"json",
			success:function(res){
				if(res.code == '200'){
					console.log(res);
				}
				let count = 0;
				let s1 = "";
				let s2 = "";
				for(let val of res.data){
					if(count == 0 ){
						s1 += `<li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>`;
						s2 += `<div class="carousel-item active">
						<a href="`+ val.url +`" target="_blank">
							<img src="`+ val.img +`" class="imgfull">
						</a>
				    </div>`
					}else{
						s1 += `<li data-target="#carouselExampleCaptions" data-slide-to="`+ count +`"></li>`;
						s2 += `<div class="carousel-item">
							<a href="`+ val.url +`" target="_blank">
								<img src="`+ val.img +`" class="imgfull">
							</a>
						</div>`
					}
					count++;					
				}
				$("#carouselExampleCaptions .carousel-indicators").append(s1)
				$("#carouselExampleCaptions .carousel-inner").append(s2)
				$('#carouselExampleCaptions').carousel({
				  interval: 2000,
				  ride: 'carousel'
				});
			}
	})
}	

function listGoods(categoryId, categoryName){
	let s="";
	let goodsArr = goodsMap[categoryName];
	
	s += 
	`<div>
		<ul>`;
	let count = 0 ;	
	for(let good of goodsArr){
		if(count == 0){
			s += `<li class="glifirst">`
		}else{
			s += `<li class="gli">`
		}
		count++;
		if(count == 5){
			count = 0;
		}
		s += 		
			`<div class="glid1">
				<div class="glid2">
					<a href="./good.html>id=`+ good.id +` " target="_blank">
						<img src="`+ good.img +`">
					</a>
				</div>
				<a href="./good.html>id=`+ good.id +` "target="_blank" class="ga">
					<div>
						<div>
							<p class="gtitle">`+ good.title +`</p>
						</div>
						<div>
							<p class="gdetail">`+good.description+`</p>
						</div>
					</div>
				</a>
				<div>
					<p class="gmoney">
						<i>¥</i>`+good.price+`
					</p>
				</div>
			</div>
		</li>`
	}
	s +=
			`<li class="cls"></li>
		</ul>
	</div>`
	
	$("div[cgdid='"+ categoryId +"']").append(s)
}




