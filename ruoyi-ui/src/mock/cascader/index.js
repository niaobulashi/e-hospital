const address = require('./address')

module.exports = [
  // mock get all routes form server
  {
    url: '/address/getProvince',
    type: 'get',
    response: _ => {
      return {
        code: 20000,
        data: address.filter((item) => {
            return item.TopID === 0
          }
        )
      }
    }
  },
  {
    url: '/address/getCity',
    type: 'get',
    response: config => {
      const { TopID } = config.query
      return {
        code: 20000,
        data: address.filter((item) => {
            return item.TopID == TopID
          }
        )
      }
    }
  },
  {
    url: '/address/getArea',
    type: 'get',
    response: config => {
      const { TopID } = config.query
      return {
        code: 20000,
        data: address.filter((item) => {
            return item.TopID == TopID
          }
        )
      }
    }
  }
]
