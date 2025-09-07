<template>
  <!-- 小程序主容器 -->
  <view class="app">
    <!-- 起始地和目的地 -->
    <view class="locations-container">
      <view class="location-item">
        <view class="location-icon">
          <image src="@/static/icon/from.png" mode="aspectFit" />
        </view>
        <input class="input" name="input" placeholder="请选择起始地"  @click="chooseFrom" v-model="fromInfo.address"/>
      </view>
      <view class="location-item">
        <view class="location-icon">
          <image src="@/static/icon/to.png" mode="aspectFit" />
        </view>
        <input class="input" name="input" placeholder="请选择目的地"  @click="chooseTo" v-model="toInfo.address"/>
      </view>
    </view>
    
    <!-- 执行路径规划按钮 -->
    <view class="button-wrapper">
      <button @click="preview()" type="primary">执行路径规划</button>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        // 起始地信息
        fromInfo:{
          "name": "天坛公园",
          "address": "北京市东城区天坛东路甲1号",
          "distance": 2551.15,
          "latitude": 39.881951,
          "longitude": 116.410891,
        },
        // 目的地信息
        toInfo: {
          "name": "天安门",
          "address": "北京市东城区东长安街",
          "distance": 947.75,
          "latitude": 39.908823,
          "longitude": 116.39747,
        }
      }
    },
    onLoad() {
      // 页面加载时执行的操作
    },
    methods: {
      // 点击“执行路径规划”按钮
      preview(url){
        if (!this.fromInfo.latitude) {
          uni.showToast({
            title: "请选择起始地",
            icon: "none"
          });
          return;
        }
        if (!this.toInfo.latitude) {
          uni.showToast({
            title: "请选择目的地",
            icon: "none"
          });
          return;
        }
        // 构造路径规划参数
        let options = {
          provider: "qqmap",
          mode: "driving",
          fromInfo: this.fromInfo,
          toInfo: this.toInfo,
          from: `${this.fromInfo.latitude},${this.fromInfo.longitude}`,
          to: `${this.toInfo.latitude},${this.toInfo.longitude}`,
          get_mp: 1
        };
        let str = encodeURIComponent(JSON.stringify(options));
        // 跳转到路径规划预览页面，并传递参数
        uni.navigateTo({
          url: `/pages/route-plan/route-preview?options=${str}`
        })
      },
      // 选择起始地
      chooseFrom(){
        uni.chooseLocation({
          success: (res)=>{
            console.log('res: ', res)
            // 更新起始地信息
            this.fromInfo = {
              latitude: res.latitude,
              longitude: res.longitude,
              name: res.name,
              address: res.address,
            };
          },
          fail: (err) => {
            console.log('err: ', err)
          }
        });
      },
      // 选择目的地
      chooseTo(){
        uni.chooseLocation({
          success: (res)=>{
            console.log('res: ', res)
            // 更新目的地信息
            this.toInfo = {
              latitude: res.latitude,
              longitude: res.longitude,
              name: res.name,
              address: res.address,
            };
          }
        });
      },  
    }
  }
</script>

<style lang="scss" scoped>
  .app {
    padding: 30px;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    align-items: center;
    background-color: #f7f7f7;
    height: 100vh;
  }

  .locations-container {
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    align-items: center;
    margin-bottom: 30px;
  }

  .location-item {
    width: 300px;
    height: 60px;
    border-radius: 10px;
    margin-bottom: 10px;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
		flex-direction: row;
    align-items: center;
    background-color: #fff;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    cursor: pointer;
  }

  .location-icon {
    width: 40px;
    height: 40px;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
		flex-direction: row;
    align-items: center;
    justify-content: center;
    margin-left: 10px;
  }

  .input {
    flex: 1;
    height: 100%;
    border: none;
    padding-left: 10px;
    font-size: 16px;
    color: #333;
    background-color: transparent;
    outline: none;
  }

  /* 执行路径规划按钮样式 */
  .button-wrapper {
    width: 200px;
    text-align: center;
  }

  button {
    width: 100%;
    height: 40px;
    border: none;
    border-radius: 20px;
    font-size: 16px;
  }

</style>
