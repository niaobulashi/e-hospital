// 指令的绑定值，这里是 { regex: /^[a-zA-Z]+\w*\s?\w*$/, error: '用户名要求以字母开头的单词字符' }
// binding.value：指令的绑定值，这里是 { regex: /^[a-zA-Z]+\w*\s?\w*$/, error: '用户名要求以字母开头的单词字符' }
// binding.arg：传给指令的参数，这里是 'input'
// binding.modifiers：一个包含修饰符的对象，这里是 { required: true }

// 具体的验证逻辑
function validate(el, modifiers, bindingValue) {
    bindingValue = bindingValue && typeof bindingValue === 'object' ? bindingValue : {}
    const value = typeof el.value === 'string' ? el.value.trim() : ''
    const { title = '该项', error } = bindingValue
    let defaultError = ''

    if (modifiers.required && value === '') {
      defaultError = `${title}不能为空`
    } else if (bindingValue.target) {
      const target = document.querySelector(bindingValue.target)
      const targetValue = target ? target.value : null

      if (targetValue !== value) {
        defaultError = `输入的${title}不匹配`
      }
    } else if (bindingValue.regex) {
      try {
        if (!bindingValue.regex.test(value)) {
          defaultError = `${title}格式不正确`
        }
      } catch (e) {}
    }

    if (defaultError) {
      if (error === undefined) {
        showError(el, defaultError)
      } else {
        showError(el, error)
      }
    } else {
      showError(el)
    }
  }

  // 显示或隐藏错误提示元素
  function showError(el, error) {
    const parentElement = el.parentElement
    const errorElement = getErrorElement(el)

    if (error === undefined) {
      errorElement.style.display = 'none'
      parentElement.classList.remove('has-error')
    } else {
      errorElement.textContent = error
      errorElement.style.display = 'block'
      parentElement.classList.add('has-error')
    }
  }

  // 创建或者返回一个错误提示元素
  function getErrorElement(el) {
    const parentElement = el.parentElement
    let errorElement = parentElement.querySelector('.help-block')

    if (!errorElement) {
      const tpl = `<span class="help-block"></span>`
      const fragment = document.createRange().createContextualFragment(tpl)

      parentElement.appendChild(fragment)
      errorElement = parentElement.querySelector('.help-block')
    }

    return errorElement
  }

  export default {
    // bind：只调用一次，指令第一次绑定到元素时调用，在这里可以进行一次性的初始化设置
    bind(el, binding, vnode) {
      // 使用解构赋值声明 value = binding.value,  arg = binding.arg,  modifiers = binding.modifiers
      const { value, arg, modifiers } = binding
      // 如果没传对应的事件名称参数，就默认使用 change 事件
      const eventType = ['change', 'blur', 'input'].indexOf(arg) !== -1 ? arg : 'change'
      // 默认处理器，当用户开始输入时，移除错误提示
      const defaultHandler = () => {
        showError(el)
      }
      // 验证处理器，当用户触发对应的事件时，验证用户输入的信息
      const handler = () => {
        validate(el, modifiers, value)
      }

      // 在 el 元素上的添加 input 事件监听
      el.addEventListener('input', defaultHandler, false)
      // 在 el 元素上的添加用户指定的事件监听
      el.addEventListener(eventType, handler, false)

      // 移除 el 元素上事件监听和数据绑定的方法
      el.destroy = () => {
        el.removeEventListener('input', defaultHandler, false)
        el.removeEventListener(eventType, handler, false)
        el.destroy = null
      }
    },
    // inserted：被绑定元素插入父节点时调用，该钩子保证了父节点的存在，因此可以在这里访问父节点 。
    inserted(el, binding, vnode) {
      const { value, modifiers } = binding
      // 指定当前一系列验证项的父级，我们这里指定为含 data-validator-form 的元素
      const form = el.closest('[data-validator-form]')
      // 指定一个按钮来检查所有验证项，我们这里指定为含 type=submit 的元素
      const submitBtn = form ? form.querySelector('[type=submit]') : null

      if (submitBtn) {
        // 提交处理器
        const submitHandler = () => {
          // 验证所有项
          validate(el, modifiers, value)

          // 获取错误信息
          const errors = form.querySelectorAll('.has-error')

          if (!errors.length) {
            // 没有错误信息时，在按钮上添加一个 canSubmit 属性，并指定为 true
            submitBtn.canSubmit = true
          } else {
            // 有错误信息时，在按钮上添加一个 canSubmit 属性，并指定为 false
            submitBtn.canSubmit = false
          }
        }

        // 在按钮上的添加 click 事件监听
        submitBtn.addEventListener('click', submitHandler, false)

        // 移除按钮上事件监听和数据绑定的方法
        el.destroySubmitBtn = () => {
          submitBtn.removeEventListener('click', submitHandler, false)
          el.destroySubmitBtn = null
        }
      }
    },
    // unbind：只调用一次，指令与元素解绑时调用，在这里可以移除绑定的事件和其他数据
    unbind(el) {
      // 移除事件监听和数据绑定
      el.destroy()
      if (el.destroySubmitBtn) el.destroySubmitBtn()
    }
  }
