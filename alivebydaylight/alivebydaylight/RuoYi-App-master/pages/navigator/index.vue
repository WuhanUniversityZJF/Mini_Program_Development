<template>
	<view class="app">
		<!-- <button @click="test('districtSearch')">行政区划 - 查询</button> -->
		<!-- <text space="ensp" class="text">{{JSON.stringify(data, null, 2)}}</text> -->
		
		<button @click="chooseLocation">地图坐标拾取</button>
		<button @click="pageTo('/pages/route-plan/route-plan')">路径规划示例</button>
		<view class="tips">
			<text>以下功能需安装unicloud-map插件</text>
			<!-- #ifdef H5 -->
			<text class="a" @click="toUrl('https://ext.dcloud.net.cn/plugin?name=unicloud-map')">详情</text>
			<!-- #endif -->
		</view>
		<button @click="pageTo('/pages/unicloud-map/static-poi')">静态POI示例</button>
		<button @click="pageTo('/pages/unicloud-map/dynamics-poi')">动态POI示例</button>
		<view class="tips">
			<text>以下功能需安装unicloud-city-select插件</text>
			<!-- #ifdef H5 -->
			<text class="a" @click="toUrl('https://ext.dcloud.net.cn/plugin?name=unicloud-city-select')">详情</text>
			<!-- #endif -->
		</view>
		<button @click="pageTo('/pages/unicloud-city-select/unicloud-city-select')">城市选择示例</button>
		
	</view>
</template>

<script>
	const uniMapCo = uniCloud.importObject('uni-map-co')

	export default {
		data() {
			return {
				data:{},
			}
		},
		onLoad() {

		},
		methods: {
			pageTo(url){
				uni.navigateTo({
					url
				})
			},
			toUrl(url){
				// #ifdef H5
				window.open(url);
				// #endif
			},
			async test(name){
				const res = await uniMapCo[name]();
				this.data = res.result;
				console.log('res: ', res.result)
			},
			chooseLocation(){
				uni.chooseLocation({
					type: "gcj02", // 必须是gcj02
					keyword: "北京天安门",
					success: (res)=>{
						console.log('res: ', res)
					}
				});
			}
		}
	}
</script>

<style scoped>
	.app{
		padding: 15px;
	}
	button{
		margin-top: 10px;
		width: 100%;
	}
	
	.text{
		font-size: 12px;
	}
	.tips{
		font-size: 12px;
		color: #8b8b8b;
		margin-top: 5px;
	}
	.a{
		margin-left: 10px;
		font-size: 12px;
		color: #1774d6;
		text-decoration: underline;
	}
</style>
