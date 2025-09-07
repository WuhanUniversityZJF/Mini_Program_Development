import upload from '@/utils/upload'
import request from '@/utils/request'

// 新增poi信息
export function addPoi(data) {
  return request({
    url: '/poi/addPoi',
    method: 'post',
    data: data
  })
}

// 获取poi信息
export function getPoi() {
  return request({
    'url': '/poi/queryPoi',
    'method': 'get'
  })
}

// 获取类型poi信息
export function typePoi(data) {
  return request({
    url: '/poi/typePoi',
    method: 'post',
    data: data
  })
}

// 更新poi状态为是
export function flagTrue(poiId) {
  return request({
    url: '/poi/flagTrue',
    method: 'post',
    data:poiId
  })
}

// 更新poi状态为fou
export function flagFalse(poiId) {
  return request({
    url: '/poi/flagFalse',
    method: 'post',
    data:poiId
  })
}