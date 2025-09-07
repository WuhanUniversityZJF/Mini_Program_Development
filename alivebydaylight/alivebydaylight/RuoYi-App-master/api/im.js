import upload from '@/utils/upload'
import request from '@/utils/request'

// 获取message
export function queryIm() {
  return request({
    'url': '/im/queryIm',
    'method': 'get'
  })
}

// 记录message
export function addIm(data) {
  return request({
    'url': '/im/addIm',
    'method': 'post',
	'data':data
  })
}