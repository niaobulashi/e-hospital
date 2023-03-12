export default {
  // bind：只调用一次，指令第一次绑定到元素时调用，在这里可以进行一次性的初始化设置
    bind(el, binding, vnode) {
      // 当前元素的事件处理器
      const handler = () => {
        // 找到当前元素的父元素，切换它的 open 类
        el.parentElement.classList.toggle('open')
      }

      // document 的事件处理器
      const documentHandler = (e) => {
        // 当前点击的目标
        const target = e.target

        // 如果点击的是当前元素，或者是当前元素的后代节点，就不处理
        if (target.isSameNode(el) || el.contains(target)) return
        // 找到当前元素的父元素，移除 open 类
        el.parentElement.classList.remove('open')
      }

      el.addEventListener('click', handler, false)
      document.addEventListener('click', documentHandler, false)

      el.destroy = () => {
        el.removeEventListener('click', handler, false)
        document.removeEventListener('click', documentHandler, false)
        el.destroy = null
      }
    },
    // unbind：只调用一次，指令与元素解绑时调用，在这里可以移除绑定的事件和其他数据
    unbind(el) {
      el.destroy()
    }
  }
