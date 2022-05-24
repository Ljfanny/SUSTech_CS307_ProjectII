import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Papa from '../package.json'

var axios = require('axios')
axios.defaults.baseURL = 'http://10.14.151.222:8088'
Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(Papa)

// eslint-disable-next-line no-new
new Vue({
  el: '#app',
  render: h => h(App),
  axios,
  router,
  components: { App },
  template: '<App/>'
})
