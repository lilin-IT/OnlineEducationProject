import request from '~/utils/request'

export default{

  getList() {
    return request({
      URL: '/api/edu/teacher/list',
      method: 'get'
    })
  },

  getById(id) {
    return request({
      URL: `/api/edu/teacher/get/${id}`,
      method: 'get'
    })
  }
}
