<template>
	<view>
		<view class="page-body">
			<view class="page-section page-section-gap">
				<map style="width: 100%; height: 680px;" @markertap="tapMap" :show-location="true":latitude="latitude" :longitude="longitude" :markers="covers" :polyline="polyline">
				</map>
			</view>
		</view>
		<uni-popup ref="alertDialog" type="dialog">
			<uni-popup-dialog :type="msgType" cancelText="关闭" confirmText="确认" title="poi信息" @confirm="dialogConfirm"
				@close="dialogClose">
				<view class="poitext">
				      <text class="line">id: {{poiid}}</text>
					  <text class="line">name: {{poiname}}</text>
					  <text class="line">经度: {{latitude}}</text>
					  <text class="line">纬度: {{longitude}}</text>
					  <text class="line">类型: {{poitype}}</text>
					  <text class="line">描述: {{poidesc}}</text>
					  <text class="line">是否标记: {{poiflag}}</text>
					  <image style="width: 300px; height: 300px;" :src="poiimg"></image>
				</view>
				</uni-popup-dialog>
		</uni-popup>
		<uni-section title="模糊查询条件" type="line" padding style="height: calc(100vh - 100px);">
			<uni-data-picker placeholder="请选择查询条件" popup-title="模糊查询条件" :localdata="dataTree" v-model="classes"
				@change="onchange" @nodeclick="onnodeclick" @popupopened="onpopupopened" @popupclosed="onpopupclosed">
			</uni-data-picker>
		</uni-section>
		<view class="input-container">
			<uni-easyinput class="uni-mt-5" suffixIcon="search" v-model="inputValue" placeholder="请输入poi类别搜索" @iconClick="iconClick"></uni-easyinput>
		</view>
		<button type="primary" @click="navigator">导航</button>
	</view>
</template>
<script>
import { getPoi,typePoi } from "@/api/poi";
export default {
	data() {
		return {
			poiid:'',
			poiname:'',
			poitype:'',
			poidesc:'',
			poiflag:'',
			poiimg:'',
			classes: '1-2',
			dataTree: [{
				text: "poi_id",
				value: "1-0",
				children: []
			},{
				text: "name",
				value: "2-0",
				children: []
			},{
				text: "type",
				value: "2-0",
				children: []
			},{
				text: "flag",
				value: "2-0",
				children: []
			},{
				text: "descr",
				value: "2-0",
				children: []
			}],
			inputType:'',
			inputValue: '',
			id:0, // 使用 marker点击事件 需要填写id
			title: 'map',
			latitude: 39.909,
			longitude: 116.39742,
			beginlatitude:39.909,
			beginlongitude:116.39742,
			endlatitude:39.909,
			endllongitude:117.39742,
			covers: [],
			 polyline: [{
			          points: [
						  { longitude: this.beginlatitude, latitude: this.beginlongitude },
						 { longitude: this.endlatitude, latitude: this.endllongitude }
					  ],
			          color: "#c21238",
			          width: 2, // 线条宽度
			          dottedLine: false // 是否虚线
			        }]
		}
	},
	onLoad() {
		this.request()
	},
	methods: {
		open() {
			this.$refs.alertDialog.open()
		},
		/**
		 * 点击取消按钮触发
		 * @param {Object} done
		 */
		dialogClose() {
			console.log('点击关闭')
		},
		dialogConfirm() {
			console.log('点击确认')
		},
		tapMap(e){
			console.log("markertap--------"+e.markerId);
			var data={"value":e.markerId,"type":'id'};
			console.log(data);
			typePoi(data).then(response=>{
			console.log(response)
			response.data.forEach(item => {
				this.poiid=item.id
				this.latitude=item.latitude
				this.longitude=item.longitude
				this.poiname=item.name
				this.poitype=item.type
				this.poidesc=item.descr
				this.poiflag=item.flag
				this.poiimg=item.img
				console.log(this.latitude+this.longitude+this.poiname)
			})
			}
			);
			this.dialogcontent='';
			this.open();
		},
		onnodeclick(e) {
			console.log(e);
		},
		onpopupopened(e) {
			console.log('popupopened');
		},
		onpopupclosed(e) {
			console.log('popupclosed');
		},
		onchange(e) {
			console.log('onchange:', e);
			this.inputType=e.detail.value[0].text
			console.log(this.inputType+"inputtype--------")
		},
		navigator(){
			console.log("navigator------")
			uni.navigateTo({
			  url:'/pages/route-plan/route-plan'
			})
		},
		openLocation(){
			uni.onLocationChange(function (res) {
				console.log('纬度：' + res.latitude);
				console.log('经度：' + res.longitude);
			});
		},
		iconClick(type) {
			uni.showToast({
			title: '搜索类别'+this.inputType+':'+this.inputValue,
			icon: 'none'
			})
			this.typerequest(this.inputValue,this.inputType)
		},
		request() {
			getPoi().then(response=>{
			console.log(response)
			const data = []
			response.data.forEach(item => {
				const map={}
				map.id=item.id
				map.latitude=item.latitude
				map.longitude=item.longitudec
				map.iconPath='../../../static/location.png'
				map.width=40
				map.height=40
				map.rotate=0
				map.callout={
					color: '#ffffff', //文字颜色
					fontSize: 15, //文本大小
					borderRadius: 15, //边框圆角
					padding: '10',
					bgColor: '#406390', //背景颜色
					content: getApp().globalData.address,
				}
				data.push(map)
			})
			this.covers=data
			console.log(data)
			})
		},
		typerequest(value,type) {
			var data={"value":value,"type":type}
			console.log(data)
			typePoi(data).then(response=>{
			console.log(response)
			const data = []
			response.data.forEach(item => {
				const map={}
				map.id=item.id
				map.latitude=item.latitude
				map.longitude=item.longitude
				map.iconPath='../../../static/location.png'
				map.width=40
				map.height=40
				map.rotate=0
				map.callout={
					color: '#040404', //文字颜色
					fontSize: 15, //文本大小
					borderRadius: 15, //边框圆角
					padding: '10',
					bgColor: '#55aaff', //背景颜色
					content: 'hahahahah'
				}
				data.push(map)
			})
			this.covers=data
			console.log(data)
			})
		}
	}
}
</script>
<style lang="scss">
.input-container {
  display: flex;
  justify-content: space-between; /* 或者使用 center 来居中对齐 */
}
.uni-mt-5 {
	margin-top: 5px;
}
.title {
		font-size: 14px;
		font-weight: bold;
		margin: 20px 0 5px 0;
	}

	.data-pickerview {
		height: 400px;
		border: 1px #e5e5e5 solid;
	}

	 .popper__arrow {
    top: -6px;
    left: 50%;
    margin-right: 3px;
    border-top-width: 0;
    border-bottom-color: #EBEEF5;
}
 .popper__arrow {
    top: -6px;
    left: 50%;
    margin-right: 3px;
    border-top-width: 0;
    border-bottom-color: #EBEEF5;
}
.poitext .line{
	display: block;
}
</style>