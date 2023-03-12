<template>
  <el-cascader
    v-model="select"
    :options="dataList"
    :props="props"
    clearable
  />
</template>

<script>
import { getProvince, getCity, getArea } from '@/api/cascader/index'
export default {
  props: {
    value: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      select: [],
      dataList: [],
      props: {
        value: 'ID',
        label: 'AddName',
        lazy: true,
        multiple: true,
        lazyLoad: (node, resolve) => {
          if (node.level === 1) {
            getCity({ TopID: node.value }).then((res) => {
              if (res.code === 20000) {
                // 如果需要修改 label
                // resolve(res.data.map((item) => {
                //     item.AddName = item.floorName
                //     return item
                //   })
                // )
                resolve(res.data)
              } else {
                resolve()
              }
            })
          } else if (node.level === 2) {
            getArea({ TopID: node.value }).then((res) => {
              resolve(res.data)
            })
          } else {
            resolve()
          }
        }
      }
    }
  },
  watch: {
    select(val) {
      this.$emit('input', val)
    }
  },
  created() {
    getProvince().then((res) => {
      if (res.code === 20000) {
        this.dataList = res.data
      } else {
        this.$message({
          message: '网络异常，请求省信息没返回',
          type: 'warning'
        })
      }
    })
  }
}
</script>
