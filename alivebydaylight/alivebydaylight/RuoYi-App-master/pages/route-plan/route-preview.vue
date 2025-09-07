<template>
	<view class="page">
		<!-- 导航模式选择开始 -->
		<view class="mode-list">
			<view class="mode-item" :class="mode === 'driving' ? 'active' : ''" @click="switchMode('driving')">驾车</view>
			<view class="mode-item" :class="mode === 'walking' ? 'active' : ''" @click="switchMode('walking')">步行</view>
			<view class="mode-item" :class="mode === 'bicycling' ? 'active' : ''" @click="switchMode('bicycling')">骑行</view>
			<view class="mode-item" :class="mode === 'transit' ? 'active' : ''" @click="switchMode('transit')">公交</view>
		</view>
		<view v-if="mode === 'transit'" class="transit-plan-list" :style="transitPlanListstyleCom">
			<view v-for="(item,index) in routes" :key="index">
				<view class="transit-plan-item">
					<view class="transit-plan-item-left">
						<view> {{ formatDuration(item.duration) }} </view>
						<view> {{ formatDistance(item.distance) }} </view>
					</view>
					<view class="hr"></view>
					<view class="transit-plan-item-right">
						<uni-steps :options="formatSteps(item)" direction="column" :active="formatSteps(item).length-1" deactiveColor="#191919" activeColor="#2979FF"></uni-steps>
					</view>
				</view>
			</view>
		</view>
		<view v-else class="map-box">
			<map ref="map" :style="styleCom" :latitude="center.latitude" :longitude="center.longitude" :markers="markers" :polyline="polyline" :scale="13" :include-points="includePoints" :controls="controls" enable-scroll enable-zoom show-compass ></map>
			<view class="plan-list">
				<view class="plan-item" v-for="(item,index) in routes" :key="index" :style="currentPlanIndex === index ? 'color:'+colors[index] : ''" @click="selectPlan(index)">
					<view class="plan-title">{{ titleFn(index) }}</view>
					<view class="plan-duration">{{ formatDuration(item.duration) }}</view>
					<view class="plan-distance">{{ formatDistance(item.distance) }}</view>
				</view>
			</view>
			<view class="hr"></view>
			<view class="footer" v-if="routes && routes.length>0">
				<!-- <view class="left"><button type="default" size="mini">路线详情</button></view> -->
				<view class="right"><button type="primary" size="mini" @click="toRouter">开始导航</button></view>
			</view>
		</view>
	</view>
</template>

<script>
	const uniMapCo = uniCloud.importObject('uni-map-co')

	export default {
		data() {
			return {
				options: {},
				loading: false,
				mode: "driving",
				colors: ["#19b411", "#0ebfff", "#7f23ff"],
				colors2: ["#d6fddc", "#c1d4ff", "#c5b3ff"],
				routes:[],
				currentPlanIndex: 0,
				minDurationPlanIndex: "", // 时间最少的方案
				minDistancePlanIndex: "", // 距离最短的方案
				center:{
					latitude: "",
					longitude: "",
				},
				polyline:[],
				markers:[],
				includePoints: [],
				controls:[],
				fromInfo:{},
				toInfo:{}
			}
		},
		onLoad(options = {}) {
			console.log('options: ', options);
			this.options = JSON.parse(decodeURIComponent(decodeURIComponent(options.options)));
			this.getRoute();
		},
		methods: {
			
			// 获取导航数据
			async getRoute(data) {
				let options = JSON.parse(JSON.stringify(this.options));
				options = Object.assign(this.options, data);
				const res = await uniMapCo.route(options);
				this.drawRoute({
					fromInfo: options.fromInfo,
					toInfo: options.toInfo,
					data: res.result
				});
			},
			
			// 切换导航模式
			switchMode(mode) {
				this.mode = mode;
				this.getRoute({ 
					mode: mode
				});
			},
			// 绘制路线
			drawRoute(obj={}){
				let {
					fromInfo,
					toInfo,
					data = {}
				} = obj;
				console.log('data: ', data);
				let routes = data.result.routes;
				this.mode = data.result.mode;
				this.fromInfo = fromInfo;
				this.toInfo = toInfo;
				this.routes = routes;
				// 计算时间最少的方案
				let arr = routes.map((item, index) => {
					return item.duration;
				});
				let minNum = Math.min(...arr);
				this.minDurationPlanIndex = arr.indexOf(minNum);
				// 计算路程最短的方案
				arr = routes.map((item, index) => {
					return item.distance;
				});
				minNum = Math.min(...arr);
				this.minDistancePlanIndex = arr.indexOf(minNum);
				// 计算当前推荐的方案
				this.currentPlanIndex = this.calcBestPlanIndex();
				// 计算中心点
				this.center = {
					latitude: parseFloat(((fromInfo.latitude + toInfo.latitude) / 2).toFixed(6)),
					longitude: parseFloat(((fromInfo.longitude + toInfo.longitude) / 2).toFixed(6)),
				};
				// 绘制起点和终点
				this.markers = [
					{
						id: 1,
						latitude: fromInfo.latitude,
						longitude: fromInfo.longitude,
						title: fromInfo.address,
						iconPath: "/static/icon/from.png",
						width: 30,
						height: 30,
					},
					{
						id: 2,
						latitude: toInfo.latitude,
						longitude: toInfo.longitude,
						title: toInfo.address,
						iconPath: "/static/icon/to.png",
						width: 30,
						height: 30,
					}
				];
				// 如果不是公交，则绘制地图
				if (this.mode !== "transit") {
					// 开始绘制
					this.draw();
				}
				
			},
			calcBestPlanIndex(){
				if (this.minDurationPlanIndex === this.minDistancePlanIndex) {
					return this.minDurationPlanIndex;
				} else {
					return this.minDurationPlanIndex;
				} 
			},
			draw(){
				let { routes, colors, colors2 } = this;
				// 将当前的方案放最后画
				let _polyline = [];
				// 循环路径
				routes.map((item, index) => {
					let {
						steps = []
					} = item;
					let points = [];
					steps.map((step) => {
						let {
							polyline = ""
						} = step;
						//console.log('polyline: ', polyline)
						let arr = polyline.split(";");
						arr.map((item) => {
							let arr2 = item.split(",");
							points.push({
								latitude: arr2[0],
								longitude: arr2[1],
							});
						});
					});
					_polyline.push({
						points,
						color: index === this.currentPlanIndex ? colors[index] : colors2[index],
						width: 6,
						dottedLine: false,
						arrowLine: true,
						borderWidth: 1,
						borderColor: "#000000",
					});
				});
				// 将当前方案放最后
				let list = [];
				_polyline.map((item, index) => {
					if (index !== this.currentPlanIndex) {
						list.push(item);
					}
				});
				list.push(_polyline[this.currentPlanIndex]);
				this.polyline = list;
			},
			// 选择方案，重新绘制路线
			selectPlan(index){
				this.currentPlanIndex = index;
				this.draw();
			},
			titleFn(index){
				let { minDurationPlanIndex, minDistancePlanIndex } = this;
				if (index === minDurationPlanIndex && index === minDistancePlanIndex) {
					return "推荐方案";
				} else if (index === minDurationPlanIndex) {
					return "时间最少";
				} else if (index === minDistancePlanIndex) {
					return "距离最短";
				} else {
					return `方案${index+1 }`;
				}
			},
			toRouter(){
				let item = {
					latitude: this.toInfo.latitude,
					longitude: this.toInfo.longitude,
					name: this.toInfo.name,
					address: this.toInfo.address,
				};
				
				// #ifndef H5
				uni.openLocation(item);
				// #endif
				
				// #ifdef H5
				let list = ["腾讯地图","高德地图","百度地图"];
				uni.showActionSheet({
					itemList: list,
					success:  (res) => {
						let index = res.tapIndex;
						if (list[index] === "腾讯地图") {
							window.open(`https://apis.map.qq.com/uri/v1/marker?marker=coord:${item.latitude},${item.longitude};title:${item.name};addr:${item.address}&referer=1`);
						} else if (list[index] === "高德地图") {
							// uni.openLocation({
							// 	latitude: item.latitude,
							// 	longitude: item.longitude
							// });
							window.open(`https://uri.amap.com/navigation?to=${item.longitude},${item.latitude},${item.name},${item.address}&mode=car&policy=0&src=mypage&coordinate=gaode&callnative=1`)
						} else if (list[index] === "百度地图") {
							window.open(`https://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=${item.name}&content=${item.address}&output=html&src=webapp.baidu.openAPIdemo`);
						} 
					},
					fail: (res) => {
						console.log(res.errMsg);
					}
				});
				// #endif
				
			},
			formatDuration(minutes){
				const days = Math.floor(minutes / (24 * 60));
				const hours = Math.floor((minutes % (24 * 60)) / 60);
				const remainingMinutes = minutes % 60;
			
				let result = '';
				if (days > 0) {
					result += `${days}天`;
				}
				if (hours > 0) {
					result += `${hours}小时`;
				}
				result += `${remainingMinutes}分钟`;
			
				return result;
			},
			formatDistance(meters){
				if (meters < 1000) {
				  return `${meters}米`;
				} else {
					const kilometers = (meters / 1000).toFixed(1);
				  return `${kilometers}公里`;
				}
			},
			formatSteps(item){
				let list = [];
				
				item.steps.map((item2, index2) => {
					if (item2.mode == 'walking') {
						list.push({
							title: "步行",
							desc: `${this.formatDistance(item2.distance)},${this.formatDuration(item2.duration)}`,
							distance: this.formatDistance(item2.distance),
							duration: this.formatDuration(item2.duration),
						});
					} else if (item2.lines && item2.lines.length>0) {
						let item3 = item2.lines[0];
						list.push({
							title: item3.title,
							desc: `在${ item3.destination.title }上车（${this.formatDistance(item3.distance)},${this.formatDuration(item3.duration)}）`,
							distance: this.formatDistance(item3.distance),
							duration: this.formatDuration(item3.duration),
						});
					}
				});
				
				list.push({
					title: `到达终点`
				});
				
				return list;
			}
		},
		watch:{
			
		},
		computed:{
			styleCom(){
				let systemInfo = uni.getSystemInfoSync();
				let height = systemInfo.windowHeight - 150 - 50;
				let style = `width: 100%;height:${height}px`;
				return style;
			},
			transitPlanListstyleCom(){
				let systemInfo = uni.getSystemInfoSync();
				let height = systemInfo.windowHeight - 80;
				let style = `width: 100%;min-height:${height}px`;
				return style;
			}
		}
	}
</script>

<style lang="scss" scoped>
/* 导航模式选择开始 */
.mode-list {
	display: flex;
	margin: 10px 0;

	.mode-item {
		flex: 1;
		text-align: center;

		&.active {
			color: #3145ff;
			font-weight: bold;
		}
	}
}

/* 导航模式选择结束 */
.plan-list{
	display: flex;
	margin-top: 10px;
	.plan-item{
		flex: 1;
		text-align: center;
		&.active{
			color: #3145ff;
		}
	}
	.plan-title{
		font-size: 12px;
	}
	.plan-distance{
		font-size: 12px;
	}
	.plan-duration{
		font-size: 20px;
		font-weight: bold;
		margin: 5px 0;
	}
}

.map-box{
	.hr{
		border-top: 1px solid #f8f8f8;
		height: 1px;
		margin: 10px 15px;
	}
	.footer{
		display: flex;
		padding: 0 15px;
		.left{
			flex: 1;
			text-align: left;
		}
		.right{
			flex: 1;
			text-align: right;
		}
	}
}

.transit-plan-list{
	background-color: #f8f8f8;
	padding: 30rpx;
	box-sizing: border-box;
	.transit-plan-item{
		margin-top: 20rpx;
		font-size: 24rpx;
		display: flex;
		background-color: #ffffff;
		padding: 30rpx;
		border-radius: 10rpx;
		.transit-plan-item-left{
			width: 100rpx;
			text-align: right;
			margin: 10rpx 0;
		}
		.hr{
			border-right: 1px solid #ededed;
			width: 1px;
			margin: 0 10rpx;
		}
		.transit-plan-item-right{
			flex: 1;
			/* ::v-deep .uni-steps__column-desc{
				color: #B7BDC6 !important;
			} */
		}
	}
}
</style>