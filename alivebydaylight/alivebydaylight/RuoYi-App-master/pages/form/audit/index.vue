<template>
	<view>
		<view class="uni-container">
			<uni-table ref="table" :loading="loading" border stripe type="selection" emptyText="暂无更多数据" @selection-change="selectionChange">
				<uni-tr>
					<uni-th width="150" align="center">poiid</uni-th>
					<uni-th width="150" align="center">poi名称:地址</uni-th>
					<uni-th width="150" align="center">poi类型</uni-th>
					<uni-th width="150" align="center">poi图片</uni-th>
					<uni-th width="150" align="center">poi描述</uni-th>
					<uni-th width="150" align="center">poi经度</uni-th>
					<uni-th width="150" align="center">poi维度</uni-th>
					<uni-th align="center">是否标记</uni-th>
					<uni-th width="204" align="center">操作</uni-th>
				</uni-tr>
				<uni-tr v-for="(item, index) in tableData" :key="index">
					<uni-td>{{ item.poiId }}</uni-td>
					<uni-td>
						<view class="name">{{ item.name }}</view>
					</uni-td>
					<uni-td>{{ item.type }}</uni-td>
					<uni-td>{{ item.img }}</uni-td>
					<uni-td>{{ item.desc }}</uni-td>
					<uni-td>{{ item.latitude }}</uni-td>
					<uni-td>{{ item.longitude }}</uni-td>
					<uni-td align="center">{{ item.flag }}</uni-td>
					<uni-td>
						<view class="uni-group">
							<button class="uni-button" v-if="checkRole(['gatherer', 'common'])" size="mini" type="primary" @click="update(item.id)">更新</button>
							<button class="uni-button" v-if="checkRole(['gatherer'])" size="mini" type="warn" @click="defend(item.id,item.name)">驳回</button>
						</view>
					</uni-td>
				</uni-tr>
			</uni-table>
			<view class="uni-pagination-box"><uni-pagination show-icon :page-size="pageSize" :current="pageCurrent" :total="total" @change="change" /></view>
		</view>
	</view>
</template>

<script>
import { checkPermi, checkRole } from "@/utils/permission.js"; // 权限判断函数
import { getPoi,flagTrue,flagFalse,typePoi } from "@/api/poi";
import { queryIm,addIm } from "@/api/im";
export default {
	data() {
		return {
			poiID:'',
			searchVal: '',
			tableData: [],
			selectedIndexs:[],
			// 每页数据量
			pageSize: 10,
			// 当前页
			pageCurrent: 1,
			// 数据总量
			total: 0,
			loading: false
		}
	},
	onLoad() {
		this.selectedIndexs = []
		this.getData(1)
	},
	methods: {
		queryPoiId(id){
			var data={"value":id,"type":'id'};
			console.log(data);
			typePoi(data).then(response=>{
			console.log(response)
			response.data.forEach(item => {
				this.poiID=item.poi_id
			})
			console.log("queryPoiId----"+this.poiID)
			}
			);
		},
		message(message){
			var messagetype=''
			if(!checkRole(['gatherer'])){
				messagetype=0
			}else{
				messagetype=1
			}
			console.log('message'+message)
			let data = {
				"id":new Date().getTime(),
				"content":message,
				"type":messagetype,
				"pic":"/static/logo.png"
			}
			addIm(data).then(response=>{
				console.log("消息发送成功!")
				console.log(data)
			})
		},
		update(poiId){
			console.log("更新update"+poiId)
			this.queryPoiId(poiId)
			flagTrue(poiId).then(response => {
			if(!checkRole(['gatherer'])){
			  uni.navigateTo({
			    url:'/pages/form/commit/index'
			  })
			  this.message('poiid:'+this.poiID+'待审核' )
			  this.$modal.msgSuccess("提交审核成功")
			  }else{
			  this.message('poiid:'+this.poiID+'更新成功' )  
			  this.$modal.msgSuccess("更新成功")
			  }
			  this.getData()
			})
		},
		defend(poiId,name){
			this.queryPoiId(poiId)
			console.log("bohuidefend"+this.poiID)
			flagFalse(poiId).then(response => {
			  this.$modal.msgSuccess("驳回成功")
			  this.getData()
			})
			this.message('poiid:'+name+'驳回成功' )  
		},
		checkPermi,
		checkRole,
		// 多选处理
		selectedItems() {
			return this.selectedIndexs.map(i => this.tableData[i])
		},
		// 多选
		selectionChange(e) {
			console.log(e.detail.index)
			this.selectedIndexs = e.detail.index
		},
		//批量删除
		delTable() {
			console.log(this.selectedItems())
		},
		// 分页触发
		change(e) {
			this.$refs.table.clearSelection()
			this.selectedIndexs.length = 0
			this.getData(e.current)
		},
		// 搜索
		search() {
			this.getData(1, this.searchVal)
		},
		// 获取数据
		getData(pageCurrent, value = '') {
			this.loading = true
			this.pageCurrent = pageCurrent
			this.request({
				pageSize: this.pageSize,
				pageCurrent: pageCurrent,
				value: value,
				success: res => {
					// console.log('data', res);
					this.tableData = res.data
					this.total = res.total
					this.loading = false
				}
			})
		},
		// 伪request请求
		request(options) {
			getPoi().then(response=>{
				console.log(response)
				this.tableData=response.data
			})
			const { pageSize, pageCurrent, success, value } = options
			let total = this.tableData.length
			let data = this.tableData.filter((item, index) => {
				const idx = index - (pageCurrent - 1) * pageSize
				return idx < pageSize && idx >= 0
			})
			if (value) {
				data = []
				this.tableData.forEach(item => {
					if (item.name.indexOf(value) !== -1) {
						data.push(item)
					}
				})
				total = data.length
			}

			setTimeout(() => {
				typeof success === 'function' &&
					success({
						data: data,
						total: total
					})
			}, 10)
		}
	}
}
</script>
<style>
/* #ifndef H5 */
/* page {
	padding-top: 85px;
} */
/* #endif */
.uni-group {
	display: flex;
	align-items: center;
}
</style>