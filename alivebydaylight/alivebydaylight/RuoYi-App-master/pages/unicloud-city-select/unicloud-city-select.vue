<template>
	<view class="page">
		<button type="primary" @click="open()">选择城市</button>
		<!-- #ifdef H5 -->
		<view style="margin-top: 20px;font-size: 14px;color: red;">注意：H5浏览器定位要求使用https的线上域名才能定位成功，建议使用微信小程序或APP测试此组件</view>
		<!-- #endif -->
		<view style="margin-top: 20px;">当前城市：{{ city.name }}</view>
		<view style="margin-top: 20px;">城市代码：{{ city.code }}</view>
		<unicloud-city-select ref="citySelect" :hot-city="hotCity" :location="true" @select="select"></unicloud-city-select>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 当前选择的城市
				city: {
					code: "",
					name: "定位中..."
				},
				// 热门城市
				hotCity: [
					{ code: "110100", name: "北京" },
					{ code: "310100", name: "上海" },
					{ code: "330100", name: "杭州" },
					{ code: "440100", name: "广州" },
					{ code: "440300", name: "深圳" },
					{ code: "320100", name: "南京" },
					{ code: "500100", name: "重庆" },
					{ code: "510100", name: "成都" }
				]
			}
		},
		onLoad() {},
		methods: {
			// 点击城市选项
			select(city) {
				this.city = city;
			},
			// 打开城市选择页面
			open() {
				this.$refs.citySelect.open();
			}
		}
	}
</script>

<style lang="scss" scoped>
	.page {
		padding: 15px;
	}
</style>