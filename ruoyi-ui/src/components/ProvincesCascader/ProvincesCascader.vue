<template>
  <el-cascader
    v-model="select"
    :options="dataList"
    :props="props"
    clearable
    @change="change"
  />
</template>

<script>
import { getProvince, getCity, getArea } from '@/api/cascader/index'
export default {
  props: {
    value: {
      type: Array,
      required: true
    },
    showBug: {
      type: Boolean,
      default: () => false
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
        checkStrictly: true,
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
  },
  methods: {
    change(e) {
      if (this.showBug) {
        return
      }
      // 如果第一次直接点击了单选框不会触发该方法
      // 选择到被点击的input节点
      const inputValue = e.join()
      const elRadioinput = document.querySelector(`input[value="${inputValue}"]`)
      if (elRadioinput != null) {
        // 根据关系触发span节点的click事件
        elRadioinput.parentNode.parentNode.nextElementSibling.click()
        // 只点击一次仍会有最后一级显示出暂无数据的问题，那咱再点一下
        elRadioinput.parentNode.parentNode.nextElementSibling.click()
      }
    }
  }
}
</script>
